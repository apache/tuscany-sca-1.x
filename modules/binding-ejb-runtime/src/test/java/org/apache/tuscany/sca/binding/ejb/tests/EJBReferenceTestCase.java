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
package org.apache.tuscany.sca.binding.ejb.tests;

import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import account.Customer;

/**
 * Invokes the component which calls the reference using the EJB binding
 *
 * @version $Rev$ $Date$
 */
public class EJBReferenceTestCase {
    private static final int MOCK_PORT = 8085;
    private SCADomain scaDomain;

    @Before
    public void setUp() throws Exception {
        System.setProperty("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
        System.setProperty("java.naming.provider.url", "ejbd://localhost:" + MOCK_PORT);
        System.setProperty("managed", "false");

        scaDomain = SCADomain.newInstance("account/account.composite");

        // To capture the network traffic for the MockServer, uncomment the next two lines (A) and comment out B
        // int OPENEJB_PORT = 4201; // A
        // new Thread(new SocketTracer(MOCK_PORT, OPENEJB_PORT)).start(); // A

        // Start the mock server to simulate the remote EJB
        new Thread(new MockServer(MOCK_PORT)).start(); // B

        // Wait enough for the server to be started
        Thread.sleep(500);
    }

    @After
    public void tearDown() throws Exception {
        scaDomain.close();
    }

    @Test
    public void testCalculator() throws Exception {
        Customer customer = scaDomain.getService(Customer.class, "CustomerComponent");
        // This is one of the customer numbers in bank application running on Geronimo
        String accountNo = "1234567890";
        Double balance = customer.depositAmount(accountNo, new Double(100));
        // System.out.println("Balance amount for account " + accountNo + " is $" + balance);
        Assert.assertEquals(1200.0, balance, 0.0);
    }
}
