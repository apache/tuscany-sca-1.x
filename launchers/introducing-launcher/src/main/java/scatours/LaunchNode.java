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

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

public class LaunchNode {

    public static void main(String[] args) throws Exception {
        LaunchNode.launchFromFileSystemDir();
    }
    
    // OK for development but you must launch the node from this module 
    public static void launchFromFileSystemDir(){
        try {
            SCANode node = SCANodeFactory.newInstance().createSCANode(null, 
                new SCAContribution("goodvaluetrips", "../../contributions/introducing-goodvaluetrips-contribution/target/classes"),
                new SCAContribution("tuscanyscatours", "../../contributions/introducing-tuscanyscatours-contribution/target/classes"));

            node.start();

            Bookings bookings = ((SCAClient)node).getService(Bookings.class, 
                                                             "TripBooking/Bookings");

            System.out.println("Trip boooking code = " + 
            		           bookings.newBooking("FS1APR4", 1));
            
            Checkout checkout = ((SCAClient)node).getService(Checkout.class, 
                                                             "ShoppingCart/Checkout");
            
            checkout.makePayment(new BigDecimal("1995.00"), "1234567843218765 10/10");
            
            
            node.stop();
            
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
