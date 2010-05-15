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
 * Tests the multiple domains with bindings scenario
 */
public class MultiDomainTestCase {

    private SCANode hotelsNode;
    private SCANode toursNode;

    @Before
    public void startServer() throws Exception {
        hotelsNode = SCANodeFactory.newInstance().createSCANode("test-clients/hotelsdomain-client.composite",
                        new SCAContribution("using", "./target/classes"));
        hotelsNode.start();
        toursNode = SCANodeFactory.newInstance().createSCANode("test-clients/toursdomain-client.composite",
                       new SCAContribution("using", "./target/classes"));
        toursNode.start();
    }

    @Test
    public void testImpl() {
        Runnable hotelsClient = ((SCAClient)hotelsNode).getService(Runnable.class, "HotelsDomainClient");
        hotelsClient.run();
        Runnable toursClient = ((SCAClient)toursNode).getService(Runnable.class, "ToursDomainClient");
        toursClient.run();
    }

    @After
    public void stopServer() throws Exception {
        if (toursNode != null) {
            toursNode.stop();
        }
        if (hotelsNode != null) {
            hotelsNode.stop();
        }
    }
}
