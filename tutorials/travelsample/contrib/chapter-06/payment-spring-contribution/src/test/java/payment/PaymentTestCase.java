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

package payment;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class PaymentTestCase {
    private static SCANode paymentNode;
    private static SCANode creditCardNode;
    private static SCANode emailGatewayNode;    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {  
        try {
            creditCardNode = SCANodeFactory.newInstance().createSCANode("creditcard.composite",
                new SCAContribution("creditcard", "../../shared-contributions/creditcard-payment-jaxb-contribution/target/classes"),
                new SCAContribution("creditcard-test", "../../shared-contributions/creditcard-payment-jaxb-contribution/target/test-classes"));        
            emailGatewayNode = SCANodeFactory.newInstance().createSCANode("emailgateway.composite", 
                new SCAContribution("creditcard", "../../shared-contributions/emailgateway-contribution/target/classes"),
                new SCAContribution("creditcard-test", "../../shared-contributions/emailgateway-contribution/target/test-classes"));
            paymentNode = SCANodeFactory.newInstance().createSCANode("payment.composite", 
                new SCAContribution("payment-spring", "./target/classes"),
                new SCAContribution("payment-spring-test", "./target/test-classes"));
    
            creditCardNode.start();
            emailGatewayNode.start();
            paymentNode.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void testPayment() {
        SCAClient client = (SCAClient) paymentNode;
        Payment payment = client.getService(Payment.class, "PaymentClient");
        System.out.println("Result = " + payment.makePaymentMember("Fred", 100.00f));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        paymentNode.stop();
        creditCardNode.stop();
        emailGatewayNode.stop();
    }

}
