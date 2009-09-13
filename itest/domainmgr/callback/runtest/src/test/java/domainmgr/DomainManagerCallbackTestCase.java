/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package domainmgr;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.junit.Test;
import callbacktest.TestService;

import static org.junit.Assert.*;

/**
 * Starts the Romain Manager with a test configuration, then starts
 * a test node that reads a deployed composite.  The domain manager
 * should read and configure the composite and send it to the node
 * without detecting or reporting any errors.
 */
public class DomainManagerCallbackTestCase {

    private String home = System.getProperty("java.home");
    private String classpath = System.getProperty("java.class.path");
    private Runtime runtime = Runtime.getRuntime();
    private BufferedReader domainErrReader;
    private BufferedReader domainOutReader;

    @Test
    public void test() throws Exception {

        Process domainMgr = null;
        TestNode otherNode = null;

        try {
            // Start the domain manager process in its own working directory of
            // "target/test-classes/domain/" to ensure no interference with the
            // node processes.  The predefined domain config is copied by mvn
            // from the "src/test/resources/domain/" directory.
            System.out.println("Starting domain manager");
            String[] domainCommand = {
                "" + home + "/bin/java",
                "-cp",
                "" + classpath + "",
                "org.apache.tuscany.sca.node.launcher.DomainManagerLauncher"};
            String userdir = System.getProperty("user.dir");
            domainMgr = runtime.exec(domainCommand, null, new File(userdir + "/target/test-classes/domain/"));

            // Get the new process's stdin, stdout and stderr streams so that we
            // can monitor and control execution of the domain manager process.
            InputStream domainErr = domainMgr.getErrorStream();
            domainErrReader = new BufferedReader(new InputStreamReader(domainErr));
            InputStream domainOut = domainMgr.getInputStream();
            domainOutReader = new BufferedReader(new InputStreamReader(domainOut));
            OutputStream domainIn = domainMgr.getOutputStream();

            // Read startup log output from the domain manager process.  The log
            // output is written to the process's stderr.
            while (true) {
                checkDomainOut();
                String line = domainErrReader.readLine();
                if (line == null) {
                    // reached end of stderr stream before startup completed
                    throw new IllegalStateException("Error starting Domain Manager process");
                }
                System.out.println("DomainMgr.e: " + line);
                if (line.contains("SEVERE:")) {
                    // startup error logged by domain manager process
                    throw new IllegalStateException("Domain manager reported error: " + line);
                }
                if (line.contains("INFO: Press 'q' to quit, 'r' to restart.")) {
                    // domain manager started successfully
                    System.out.println("Domain manager started successfully");
                    break;
                }
            }
            checkDomainOut();
            
            // Start the test nodes.  Each node runs in its own "target" directory
            // to ensure no interference with other processes.
            otherNode = new TestNode("ServiceNode", userdir + "/../service/target/");
            otherNode.start();
            
            // Start the client node.  This runs in the current process so that
            // we can use the SCAClient API to invoke its services.  The current
            // working directory is "runtest" which means there is no interference
            // other processes.
            SCANode thisNode = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/ClientNode");
            checkDomainErr();
            checkDomainOut();
            thisNode.start();

            // Run the client test code.
            TestService tester = ((SCAClient)thisNode).getService(TestService.class, "MyClientComponent/TestService");
            tester.runTest();
            otherNode.checkNodeErr();
            otherNode.checkNodeOut();
            System.out.println("Sleeping ...");
            Thread.sleep(4000);
            otherNode.checkNodeErr();
            otherNode.checkNodeOut();
            assertEquals("-> someMethod -> receiveResult", tester.getResult());
            
            // Stop the client node.
            thisNode.stop();

            // Stop the test nodes.
            otherNode.stopNode();
            otherNode = null;

            // Stop the domain manager by sending 'q' to stdin.
            System.out.println("Stopping domain manager");
            domainIn.write('q');
            domainIn.flush();
            while (true) {
                checkDomainOut();
                String line = domainErrReader.readLine();
                if (line == null) {
                    // reached end of stderr stream before shutdown completed
                    throw new IllegalStateException("Error stopping Domain Manager process");
                }
                System.out.println("DomainMgr.e: " + line);
                if (line.contains("SEVERE:")) {
                    // shutdown error logged by domain manager process
                    throw new IllegalStateException("Domain manager reported error: " + line);
                }
                if (line.contains("INFO: SCA Domain Manager is now stopped.")) {
                    // domain manager stopped successfully
                    System.out.println("Domain manager stopped successfully");
                    break;
                }
            }
            checkDomainOut();

            // Wait for domain manager process to end, and check its exit value.
            int value = domainMgr.waitFor();
            if (value != 0) {
                throw new IllegalStateException("Domain Manager process exit value " + value);
            }
            domainMgr = null;

        } finally {
            // If the test nodes did not shut down cleanly, destroy the test
            // node processes.
            if (otherNode != null) {
                otherNode.destroyNode();
            }

            // If the domain manager did not shut down cleanly, destroy the
            // domain manager process.
            if (domainMgr != null) {
                System.out.println("Destroying domain");
                checkDomainErr(true);
                checkDomainOut();
                try {
                    domainMgr.destroy();
                } catch (Exception e) {
                }
            }
        }
    }

    /*
     * Check stderr for the domain manager process.  Called periodically
     * to ensure that anything written to stderr is displayed in the test log
     * and abort execution if the domain manager has logged any errors.
     */
    private void checkDomainErr(boolean nothrow) throws Exception {
        while (domainErrReader.ready()) {
            String line = domainErrReader.readLine();
            System.out.println("DomainMgr.e: " + line);
            if (line.contains("SEVERE:") && !nothrow) {
                throw new IllegalStateException("Domain manager reported error: " + line);
            }
        }
    }

    private void checkDomainErr() throws Exception {
        checkDomainErr(false);
    }

    /*
     * Check stdout for the domain manager process.  Called periodically
     * to ensure that anything written to stdout is displayed in the test log. 
     */
    private void checkDomainOut() throws Exception {
        while (domainOutReader.ready()) {
            String line = domainOutReader.readLine();
            System.out.println("DomainMgr.o: " + line);
        }
    }

    /*
     * Internal class representing a test node.
     */
    private class TestNode {

        String nodeName;
        String nodeDir;
        BufferedReader nodeErrReader;
        BufferedReader nodeOutReader;
        OutputStream nodeIn;
        Process nodeProcess;

        TestNode(String nodeName, String nodeDir) {
            this.nodeName = nodeName;
            this.nodeDir = nodeDir;
        }

        /*
         * Start the test node.
         */
        void start() throws Exception {
            System.out.println("Starting node " + nodeName);
            String[] nodeCommand = {
                "" + home + "/bin/java",
                "-cp",
                "" + classpath + "",
                "org.apache.tuscany.sca.node.launcher.NodeLauncher",
                "http://localhost:9990/node-config/" + nodeName};
            nodeProcess = runtime.exec(nodeCommand, null, new File(nodeDir));

            // Get the new process's stdin, stdout and stderr streams so that we
            // can monitor and control execution of the test node process.
            InputStream nodeErr = nodeProcess.getErrorStream();
            nodeErrReader = new BufferedReader(new InputStreamReader(nodeErr));
            InputStream nodeOut = nodeProcess.getInputStream();
            nodeOutReader = new BufferedReader(new InputStreamReader(nodeOut));
            nodeIn = nodeProcess.getOutputStream();

            // Read startup log output from the test node process.  The log
            // output is written to the process's stderr.
            while (true) {
                Thread.sleep(100);
                checkDomainErr();
                checkDomainOut();
                checkNodeOut();
                String line = nodeErrReader.readLine();
                if (line == null) {
                    // reached end of stderr stream before startup completed
                    throw new IllegalStateException("Error starting node " + nodeName);
                }
                System.out.println(nodeName + ".e: " + line);
                if (line.contains("SEVERE:")) {
                    // startup error logged by test node process
                    throw new IllegalStateException("Node " + nodeName + " reported error: " + line);
                }
                if (line.contains("INFO: Press 'q' to quit, 'r' to restart.")) {
                    // test node started successfully
                    System.out.println("Node " + nodeName + " started successfully");
                    break;
                }
            }
            checkDomainErr();
            checkDomainOut();
            checkNodeOut();
        }

        /*
         * Check stderr for the test node process.  Called periodically to
         * ensure that anything written to stderr is displayed in the test log
         * and abort execution if the test node has logged any errors.
         */
        void checkNodeErr(boolean nothrow) throws Exception {
            while (nodeErrReader.ready()) {
                String line = nodeErrReader.readLine();
                System.out.println(nodeName + ".e: " + line);
                if (line.contains("SEVERE:") && !nothrow) {
                    throw new IllegalStateException("Node " + nodeName + " reported error: " + line);
                }
            }
        }

        void checkNodeErr() throws Exception {
            checkNodeErr(false);
        }

        /*
         * Check stdout for the test node process.  Called periodically to
         * ensure that anything written to stdout is displayed in the test log.
         */
        void checkNodeOut() throws Exception {
            while (nodeOutReader.ready()) {
                String line = nodeOutReader.readLine();
                System.out.println(nodeName + ".o: " + line);
            }
        }

        /*
         * Stop the test node process.
         */
        void stopNode() throws Exception {
            System.out.println("Stopping node " + nodeName);
            nodeIn.write('q');
            nodeIn.flush();
            while (true) {
                checkNodeOut();
                String line = nodeErrReader.readLine();
                if (line == null) {
                    // reached end of stderr stream before shutdown completed
                    throw new IllegalStateException("Error stopping node " + nodeName);
                }
                System.out.println(nodeName + ".e: " + line);
                if (line.contains("SEVERE:")) {
                    // shutdown error logged by test node process
                    throw new IllegalStateException("Node " + nodeName + " reported error: " + line);
                }
                if (line.contains("INFO: SCA Node is now stopped.")) {
                    // test node stopped successfully
                    System.out.println("Node " + nodeName + " stopped successfully");
                    break;
                }
            }
            checkNodeOut();

            // Wait for test node process to end, and check its exit value.
            int value = nodeProcess.waitFor();
            if (value != 0) {
                throw new IllegalStateException("Node " + nodeName + " exit value " + value);
            }
        }

        /*
         * Destroy the test node process.
         */
        void destroyNode() throws Exception {
            if (nodeProcess != null) {
                System.out.println("Destroying node " + nodeName);
                checkNodeErr(true);
                checkNodeOut();
                try {
                    nodeProcess.destroy();
                } catch (Exception e) {
                }
            }
        }
    }

}
