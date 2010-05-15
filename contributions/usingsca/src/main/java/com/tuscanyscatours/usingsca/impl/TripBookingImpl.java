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
package com.tuscanyscatours.usingsca.impl;

import java.text.DateFormat;
import java.util.Date;
import org.osoa.sca.annotations.Reference;

import com.tuscanyscatours.Bookings;
import com.tuscanyscatours.Cars;
import com.tuscanyscatours.Flights;
import com.tuscanyscatours.Hotels;

public class TripBookingImpl implements Bookings {

    public TripBookingImpl(@Reference(name="cars") Cars cars) {
        this.cars = cars;
    }
    
    protected Cars cars;

    @Reference
    protected Flights flights;

    private Hotels hotels;

    @Reference
    public void setHotels(Hotels hotels) {
        this.hotels = hotels;
    }

    public String newBooking(String trip, int people) {
        /* In a real implementation this method would look up the trip code
           in the travel catalog to get details of the car, flight and hotel
           bookings needed.  Instead these details are hard-wired here for
           test purposes. */   
        Date startDate = new Date(); //TEMP - need to fix
        /*
        try {
            startDate = DateFormat.getInstance().parse("7 Jul, 2012");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        if (people > 0) {
            cars.bookCar(startDate, 7, "B");
            flights.bookFlight("AA123", startDate, people, "Y");
            hotels.bookHotel("DBH", startDate, 7, "SUP");

            return "HW3546";

        } else {
            return "NONE";
        }
    }
}
