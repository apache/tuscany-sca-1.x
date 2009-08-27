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

public class FullAppLauncher {

    public static void main(String[] args) throws Exception {
        SCANode node = SCANodeFactory.newInstance().createSCANode(null,
            new SCAContribution("common", "../../contributions/common/target/classes"),
            new SCAContribution("currency", "../../contributions/currency/target/classes"),
            new SCAContribution("hotel", "../../contributions/hotel/target/classes"),
            new SCAContribution("flight", "../../contributions/flight/target/classes"),
            new SCAContribution("car", "../../contributions/car/target/classes"),
            new SCAContribution("trip", "../../contributions/trip/target/classes"),
            new SCAContribution("tripbooking", "../../contributions/tripbooking/target/classes"),
            new SCAContribution("travelcatalog", "../../contributions/travelcatalog/target/classes"),
            new SCAContribution("payment", "../../contributions/payment-java/target/classes"),
            //new SCAContribution("payment", "../../contributions/payment-spring/target/classes"),
            new SCAContribution("creditcard", "../../contributions/creditcard-payment-jaxb/target/classes"),
            new SCAContribution("shoppingcart", "../../contributions/shoppingcart/target/classes"),
            new SCAContribution("scatours", "../../contributions/scatours/target/classes"),
            new SCAContribution("fullapp-ui", "../../contributions/fullapp-ui/target/classes"),
            new SCAContribution("fullapp-frontend", "../../contributions/fullapp-frontend/target/classes"),
            new SCAContribution("fullapp-currency", "../../contributions/fullapp-currency/target/classes"),
            new SCAContribution("fullapp-packagedtrip", "../../contributions/fullapp-packagedtrip/target/classes"),
            new SCAContribution("fullapp-bespoketrip", "../../contributions/fullapp-bespoketrip/target/classes"),
            new SCAContribution("fullapp-shoppingcart", "../../contributions/fullapp-shoppingcart/target/classes"));

        node.start();

        System.out.println("Point your browser at - http://localhost:8080/scatours/ ");
        System.out.println("Node started - Press enter to shutdown.");

        try {
            System.in.read();
        } catch (IOException e) {}

        node.stop();
    }
}
