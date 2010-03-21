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

package com.tuscanyscatours.trip.impl;

public class TripInfo {

    private String name;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String fromDate;
    private String toDate;
    private String spaces;
    private double pricePerPerson;
    private String currency;
    private String link;

    public TripInfo() {
    }

    public TripInfo(String name,
                    String description,
                    String fromLocation,
                    String toLocation,
                    String fromDate,
                    String toDate,
                    String spaces,
                    double pricePerPerson,
                    String currency,
                    String link) {

        this.name = name;
        this.description = description;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.spaces = spaces;
        this.pricePerPerson = pricePerPerson;
        this.currency = currency;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
