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
package com.tuscanyscatours;

import org.osoa.sca.annotations.Reference;

public class TripBooking implements Bookings {
    @Reference
    protected com.goodvaluetrips.Trips mytrips;

    @Reference
    protected Updates cart;
 
    public String newBooking(String trip, int people) {
        String resCode = mytrips.checkAvailability(trip, people);
        cart.addTrip(resCode);
        return "GV" + resCode;
    }
}
