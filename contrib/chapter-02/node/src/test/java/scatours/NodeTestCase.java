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
package scatours;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all the components and services in chapter 2
 */
public class NodeTestCase {

    private SCANode node;

    @Before
    public void startServer() throws Exception {
        try {
            node = SCANodeFactory.newInstance().createSCANode("scatours.composite", 
                new SCAContribution("goodvaluetrips", "../goodvaluetrips-contribution/target/classes"),
                new SCAContribution("tuscanyscatours", "../tuscanyscatours-contribution/target/classes"),
                new SCAContribution("client", "../client-contribution/target/classes"),
                new SCAContribution("node", "./target/classes"));
            node.start();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Test
    public void testClient() throws Exception {
        Runnable runner = ((SCAClient)node).getService(Runnable.class, "TestClient/Runnable");
        runner.run();
    }

    @After
    public void stopServer() throws Exception {
        node.stop();
    }
}
