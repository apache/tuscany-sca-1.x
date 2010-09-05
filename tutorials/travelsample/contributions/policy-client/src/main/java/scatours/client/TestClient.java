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

package scatours.client;


import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import com.tuscanyscatours.payment.Payment;

import com.tuscanyscatours.common.Book;
import com.tuscanyscatours.common.TripItem;

/**
 * A client for calling payment and trip components directly. The components in this
 * case are expected to have policy configuration associated with the in the 
 * composite files that define them
 *
 */
@Service(Runnable.class)
public class TestClient {
    @Reference
    protected Payment payment;
    
    @Reference
    protected Book tripBooking;

    public TestClient() {
    }

    public void run() {
    	System.out.println("===============================================");
    	System.out.println("Test the logging policy by calling the trip component");
        TripItem tripItem =
            new TripItem("1234", "5678", TripItem.TRIP, "FS1DEC06", "Florence and Siena pre-packaged tour", "FLR",
                         "06/12/09", "13/12/09", 450, "EUR", "http://localhost:8085/tbd");
        System.out.println("Result = " + tripBooking.book(tripItem));
        System.out.println("===============================================");
    	System.out.println("Test the basic authentication policy by calling the payment component");
        System.out.println("TestClient - Successful Payment - Status = " + payment.makePaymentMember("c-0", 100.00f));
        System.out.println("===============================================");
    }
}
