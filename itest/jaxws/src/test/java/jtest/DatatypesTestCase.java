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

package jtest;

import org.apache.tuscany.sca.binding.ws.wsdlgen.WSDLServiceGenerator;
import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DatatypesTestCase {
    private static SCANode node;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    WSDLServiceGenerator.printWSDL = true;
    node = SCANodeFactory.newInstance().createSCANode("jtest.composite", 
                new SCAContribution("payment", "./target/classes"));
        node.start();
    }
    
    @Test
    public void runAbstractTypeTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runAbstractTypeTest();
    }

    @Test
    @Ignore
    public void runAbstractExceptionTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runAbstractExceptionTest();
    }

    @Test
    public void runListTypeTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runListTypeTest();
    }

    @Test
    public void runWrapMapTypeTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runWrapMapTypeTest();
    }

    @Test
    public void runMapTypeTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runMapTypeTest();
    }

    @Test
    public void runWildcardExtendsTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runWildcardExtendsTest();
    }

    @Test
    public void runWrapBeanTest() {
        SCAClient client = (SCAClient)node;
        TestClient testClient = client.getService(TestClient.class, "TestClient");
        testClient.runWrapBeanTest();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (node != null) {
            node.stop();
            node = null;
        }
    }
}
