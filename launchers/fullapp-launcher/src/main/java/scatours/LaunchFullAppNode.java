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

public class LaunchFullAppNode {
    public static void main(String[] args) throws Exception {
        LaunchFullAppNode.launchFromFileSystemDir();
    }

    // OK for development but you must launch the node from this module
    public static void launchFromFileSystemDir(){

        try {
        	SCANode node1 = SCANodeFactory.newInstance().createSCANode(null,
	           new SCAContribution("common", "../../contributions/common-contribution/target/classes"),
	           new SCAContribution("currency", "../../contributions/currency-contribution/target/classes"),
	           new SCAContribution("hotel", "../../contributions/hotel-contribution/target/classes"),
	           new SCAContribution("flight", "../../contributions/flight-contribution/target/classes"),
	           new SCAContribution("car", "../../contributions/car-contribution/target/classes"),
	           new SCAContribution("trip", "../../contributions/trip-contribution/target/classes"),
	           new SCAContribution("tripbooking", "../../contributions/tripbooking-contribution/target/classes"),
	           new SCAContribution("travelcatalog", "../../contributions/travelcatalog-contribution/target/classes"),
	           new SCAContribution("payment", "../../contributions/payment-java-contribution/target/classes"),
	           new SCAContribution("creditcard", "../../contributions/creditcard-payment-jaxb-contribution/target/classes"),
	           new SCAContribution("shoppingcart", "../../contributions/shoppingcart-contribution/target/classes"),
	           new SCAContribution("scatours", "../../contributions/scatours-contribution/target/classes"),
	           new SCAContribution("fullapp-ui", "../../contributions/fullapp-ui-contribution/target/classes"),
	           new SCAContribution("fullapp-frontend", "../../contributions/fullapp-frontend-contribution/target/classes"),
	           new SCAContribution("fullapp-currency", "../../contributions/fullapp-currency-contribution/target/classes"),
	           new SCAContribution("fullapp-packagedtrip", "../../contributions/fullapp-packagedtrip-contribution/target/classes"),
	           new SCAContribution("fullapp-bespoketrip", "../../contributions/fullapp-bespoketrip-contribution/target/classes"),
	           new SCAContribution("fullapp-shoppingcart", "../../contributions/fullapp-shoppingcart-contribution/target/classes"));

            node1.start();

            System.out.println("Point your browser at - http://localhost:8080/scatours/ ");
            System.out.println("Node started - Press enter to shutdown.");

            try {
                System.in.read();
            } catch (IOException e) {}

            node1.stop();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
