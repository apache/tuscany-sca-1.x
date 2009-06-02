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
import org.apache.tuscany.sca.node.launcher.DomainManagerLauncher;

public class LaunchFullAppDomainNodes {
    public static void main(String[] args) throws Exception {
        LaunchFullAppDomainNodes.launchFromFileSystemDir();
    }

    // OK for development but you must launch the node from this module
    public static void launchFromFileSystemDir(){

        try {
        	// turn off validation 
            System.setProperty("org.apache.tuscany.sca.contribution.processor.ValidationSchemaExtensionPoint.enabled", "false");
      	
        	SCANode nodeCreditcard = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/creditcard");
        	nodeCreditcard.start();
        	
        	SCANode nodePayment = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/payment");
        	nodePayment.start();        	
        	
        	SCANode nodeShoppingcart = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/shoppingcart");
        	nodeShoppingcart.start();
        	
        	SCANode nodeCurrency = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/currency");
        	nodeCurrency.start();
        	
        	SCANode nodePackagedtrip = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/packagedtrip");
        	nodePackagedtrip.start();
        	
        	SCANode nodeBespoketrip = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/bespoketrip");
        	nodeBespoketrip.start();
        	
        	SCANode nodeFrontend = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/frontend");
        	nodeFrontend.start();
       	
        	SCANode nodeUI = SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/ui");
        	nodeUI.start();

            System.out.println("Node started - Press enter to shutdown.");

            try {
                System.in.read();
            } catch (IOException e) {}

            nodeCreditcard.stop();
            nodePayment.stop();
            nodeShoppingcart.stop();
            nodeCurrency.stop();
            nodePackagedtrip.stop();
            nodeBespoketrip.stop();             
            nodeFrontend.stop();
            nodeUI.stop();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
