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

package scatours.payment;

import static scatours.launcher.LauncherUtil.locate;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

import com.tuscanyscatours.payment.Payment;

public class PaymentLauncher {

    public static void main(String[] args) throws Exception {
        SCANode node = SCANodeFactory.newInstance().createSCANode(null, 
        		                                                  locate("payment-java-callback"));
        node.start();
        
        SCAClient client = (SCAClient)node;
        Payment payment1 = client.getService(Payment.class, "Payment1");
        Payment payment2 = client.getService(Payment.class, "Payment2");
        Payment paymentConfirm = client.getService(Payment.class, "PaymentConfirm");
        Payment paymentCallbackID = client.getService(Payment.class, "PaymentCallbackID");
        Payment paymentCallbackRedirect = client.getService(Payment.class, "PaymentCallbackRedirect");
        
        System.out.println("===================================================");
        System.out.println("\n\nPayment1 under limit - Status = \n\n" + payment1.makePaymentMember("c-0", 100.00f));
        System.out.println("===================================================");
        System.out.println("\n\nPayment2 under limit - Status = \n\n" + payment2.makePaymentMember("c-0", 100.00f));        
        System.out.println("===================================================");
        System.out.println("\n\nPayment1 over limit - Status = \n\n" + payment1.makePaymentMember("c-0", 1500.00f));
        System.out.println("===================================================");
        System.out.println("\n\nPayment2 over limit - Status = \n\n" + payment2.makePaymentMember("c-0", 1500.00f));
        System.out.println("===================================================");
        System.out.println("\n\nPayment2 using request context - Status = \n\n" + payment2.makePaymentMember("c-0", 20000.00f));
        System.out.println("===================================================");
        System.out.println("\n\nPaymentConfirm  - Status = \n\n" + paymentConfirm.makePaymentMember("c-0", 20000.00f));
        System.out.println("===================================================");

        // wait for longer that the asynch payment processing waits.
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
            // do nothing
        }
        
        System.out.println("===================================================");        
        System.out.println("\n\nPaymentCallbackID  - Status = \n\n" + paymentCallbackID.makePaymentMember("c-0", 20000.00f));
        System.out.println("===================================================");
        System.out.println("\n\nPaymentCallbackRedirect  - Status = \n\n" + paymentCallbackRedirect.makePaymentMember("c-0", 20000.00f));
        System.out.println("===================================================");
        
        node.stop();
    }
}
