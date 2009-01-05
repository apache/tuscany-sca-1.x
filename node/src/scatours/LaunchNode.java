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
import org.apache.tuscany.sca.node.launcher.NodeLauncher;

public class LaunchNode {
    public static void main(String[] args) throws Exception {
        LaunchNode.launchFromFileSystemDir();
    }

    // OK for development but you must launch the node from this module 
    public static void launchFromFileSystemJar(){
        SCANode node = null; 
        
        try {
            node = SCANodeFactory.newInstance().createSCANode("scatours.composite", 
                                                               new SCAContribution("common", "../common-contribution/target/scatours-common-contribution.jar"),
                                                               new SCAContribution("currency", "../currency-contribution/target/scatours-currency-contribution.jar"),
                                                               new SCAContribution("hotel", "../hotel-contribution/target/scatours-hotel-contribution.jar"),
                                                               new SCAContribution("flight", "../flight-contribution/target/scatours-flight-contribution.jar"),
                                                               new SCAContribution("car", "../car-contribution/target/scatours-car-contribution.jar"),
                                                               new SCAContribution("tripbooking", "../tripbooking-contribution/target/scatours-tripbooking-contribution.jar"),
                                                               new SCAContribution("travelcatalog", "../travelcatalog-contribution/target/scatours-travelcatalog-contribution.jar"),
                                                               new SCAContribution("scatours", "../scatours-contribution/target/scatours-scatours-contribution.jar"),
                                                               new SCAContribution("ui", "../ui-contribution/target/scatours-ui-contribution.jar"));

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
    
    // OK for development but you must launch the node from this module 
    public static void launchFromFileSystemDir(){
        SCANode node = null; 
        
        try {
            node = SCANodeFactory.newInstance().createSCANode("scatours.composite", 
                                                               new SCAContribution("common", "../common-contribution/target/classes"),
                                                               new SCAContribution("currency", "../currency-contribution/target/classes"),
                                                               new SCAContribution("hotel", "../hotel-contribution/target/classes"),
                                                               new SCAContribution("flight", "../flight-contribution/target/classes"),
                                                               new SCAContribution("car", "../car-contribution/target/classes"),
                                                               new SCAContribution("tripbooking", "../tripbooking-contribution/target/classes"),
                                                               new SCAContribution("travelcatalog", "../travelcatalog-contribution/target/classes"),
                                                               new SCAContribution("payment", "../payment-contribution/target/classes"),
                                                               new SCAContribution("emailgateway", "../emailgateway-contribution/target/classes"),
                                                               new SCAContribution("paymentprocess", "../paymentprocess-contribution/target/classes"),
                                                               new SCAContribution("shoppingcart", "../shoppingcart-contribution/target/classes"),
                                                               new SCAContribution("scatours", "../scatours-contribution/target/classes"),
                                                               new SCAContribution("ui", "../ui-contribution/target/classes"));
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
    
    // OK for samples but you can only load one contribution 
    public static void launchFromClasspath(){
        SCANode node = null; 
        
        try {
            node = SCANodeFactory.newInstance().createSCANodeFromClassLoader("scatours.composite", null);
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
    
    // have to be running the domain in this case
    public static void launchFromDomain()throws Exception {  
        NodeLauncher.main(new String[] {"http://localhost:9990/node-config/SCAToursNode"});
    }
}
