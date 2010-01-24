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

import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

public class LaunchBPELExampleNode {
    public static void main(String[] args) throws Exception {
        LaunchBPELExampleNode.launchFromFileSystemDir();
    }
    
    // OK for development but you must launch the node from this module 
    public static void launchFromFileSystemDir(){
        SCANode node = null; 
        
        try {
            node = SCANodeFactory.newInstance().createSCANode("scatours.composite", 
                                                               new SCAContribution("common", "../../shared-contributions/common-contribution/target/classes"),
                                                               new SCAContribution("currency", "../../shared-contributions/currency-contribution/target/classes"),
                                                               new SCAContribution("hotel", "../../shared-contributions/hotel-contribution/target/classes"),
                                                               new SCAContribution("flight", "../../shared-contributions/flight-contribution/target/classes"),
                                                               new SCAContribution("car", "../../shared-contributions/car-contribution/target/classes"),
                                                               new SCAContribution("trip", "../../shared-contributions/trip-contribution/target/classes"),
                                                               new SCAContribution("tripbooking", "../../shared-contributions/tripbooking-contribution/target/classes"),
                                                               new SCAContribution("travelcatalog", "../../shared-contributions/travelcatalog-contribution/target/classes"),
                                                               new SCAContribution("creditcardpayment", "../../shared-contributions/creditcard-payment-jaxb-contribution/target/classes"),
                                                               new SCAContribution("emailgateway", "../../shared-contributions/emailgateway-contribution/target/classes"),
                                                               new SCAContribution("shoppingcart", "../../shared-contributions/shoppingcart-contribution/target/classes"),
                                                               new SCAContribution("scatours", "../../shared-contributions/scatours-contribution/target/classes"),
                                                               new SCAContribution("payment", "../payment-bpel-contribution/target/classes"),
                                                               new SCAContribution("ui", "../ui-bpel-contribution/target/classes"));
            node.start();
            
            System.out.println("Node started - Press enter to shutdown.");
            try {
                System.in.read();
            } catch (IOException e) {}
            
            node.stop();
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }    
}
