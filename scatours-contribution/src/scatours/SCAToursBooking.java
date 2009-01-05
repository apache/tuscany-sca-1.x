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


import org.osoa.sca.annotations.Remotable;

import scatours.common.TripItem;

/**
 * The SCA Tours trip booking service interface
 */
@Remotable
public interface SCAToursBooking {
    String addCart();
    String addTrip(String cartId);
    void removeTrip(String cartId, String tripId);
    void addTripItem(String cartId, String tripId, String tripItemId);
    void removeTripItem(String cartId, String tripId, String tripItemId);
    TripItem[] getTripItems(String cartId);
    double getTotalPrice(String cartId);
    void checkout(String name);
}
