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

//import org.apache.activemq.broker.BrokerService;
import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

import static scatours.launcher.LauncherUtil.locate;

public class UsingSCALauncher {

    public static void main(String[] args) throws Exception {
        runAirportCodes();
        runBindings();
        runCarAutowire();
        runCarPartner();
        runCarWireElement();
        runComplexPropertyElement();
        runComplexPropertyType();
        runCurrencyConverter();
        runMultiDomain();
        runTripAutowire();
        runTripBooking();
        runTripWireElement();
    }

    private static void runAirportCodes() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/airportcodes-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "AirportCodesClient");
        client.run();

        node.stop();
    }

    private static void runBindings() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/bookings4-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "Bookings4Client");
        client.run();

        node.stop();
    }

    private static void runCarAutowire() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/carbookings3-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "CarBookings3Client");
        client.run();

        node.stop();
    }

    private static void runCarPartner() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/carbookings1-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "CarBookings1Client");
        client.run();

        node.stop();
    }

    private static void runCarWireElement() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/carbookings2-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "CarBookings2Client");
        client.run();

        node.stop();
    }

    private static void runComplexPropertyElement() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/orders1-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "Orders1Client");
        client.run();

        node.stop();
    }

    private static void runComplexPropertyType() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/orders2-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "Orders2Client");
        client.run();

        node.stop();
    }

    private static void runCurrencyConverter() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/converter-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "ConverterClient");
        client.run();

        node.stop();
    }

    private static void runMultiDomain() throws Exception {
        SCANode hotelsNode =
            SCANodeFactory.newInstance().createSCANode("test-clients/hotelsdomain-client.composite",
                                                       locate("usingsca"));
        SCANode toursNode =
            SCANodeFactory.newInstance().createSCANode("test-clients/toursdomain-client.composite",
                                                       locate("usingsca"));

        hotelsNode.start();
        toursNode.start();

        Runnable hotelsClient = ((SCAClient)hotelsNode).getService(Runnable.class, "HotelsDomainClient");
        hotelsClient.run();
        Runnable toursClient = ((SCAClient)toursNode).getService(Runnable.class, "ToursDomainClient");
        toursClient.run();

        toursNode.stop();
        hotelsNode.stop();
    }

    private static void runTripAutowire() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/bookings3-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "Bookings3Client");
        client.run();

        node.stop();
    }

    private static void runTripBooking() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/bookings1-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "Bookings1Client");
        client.run();

        node.stop();
    }

    private static void runTripWireElement() throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANode("test-clients/bookings2-client.composite",
                                                       locate("usingsca"));

        node.start();

        Runnable client = ((SCAClient)node).getService(Runnable.class, "Bookings2Client");
        client.run();

        node.stop();
    }
}
