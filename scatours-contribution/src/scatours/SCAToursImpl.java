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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tuscany.sca.data.collection.Entry;
import org.apache.tuscany.sca.data.collection.NotFoundException;
import org.osoa.sca.CallableReference;
import org.osoa.sca.ComponentContext;
import org.osoa.sca.RequestContext;
import org.osoa.sca.ServiceReference;
import org.osoa.sca.annotations.Context;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

import scatours.common.Search;
import scatours.common.SearchCallback;
import scatours.common.TripItem;
import scatours.common.TripLeg;
import scatours.currencyconverter.CurrencyConverter;
import scatours.travelcatalog.TravelCatalogSearch;
import scatours.tripbooking.TripBooking;

/**
 * An implementation of the Trip service
 */
@Scope("COMPOSITE")
@Service(interfaces={TravelCatalogSearch.class, SCAToursBooking.class})
public class SCAToursImpl implements TravelCatalogSearch, SCAToursBooking{
    
    @Reference
    protected TravelCatalogSearch travelCatalogSearch;
    
    @Reference 
    protected TripBooking tripBooking;
    
    @Context
    protected ComponentContext componentContext;  
    
    private Map<String,TripBooking> trips = new HashMap<String,TripBooking>();
    private Map<String, TripItem> searchItemsCache = new HashMap<String, TripItem>();
    
    // TravelSearch methods
    
    public TripItem[] search(TripLeg tripLeg) {
        
        TripItem[] searchItems = travelCatalogSearch.search(tripLeg);
        
        for (int i =0; i< searchItems.length; i++){
            searchItemsCache.put(searchItems[i].getId(), searchItems[i]);
        }
        return searchItems;
    } 

    // TravelBooking methods
    
    public String newTrip(){
        String tripId = UUID.randomUUID().toString();
        ServiceReference<TripBooking> tripReference = componentContext.getServiceReference(TripBooking.class, 
                                                                                          "tripBooking");
        tripReference.setConversationID(tripId);
        trips.put(tripId, tripReference.getService());
        return tripId;
    }
    
    public void addTripItem(String tripId, String tripItemId){
        trips.get(tripId).addTripItem(searchItemsCache.get(tripItemId));
    }
    
    public void removeTripItem(String tripId, String tripItemId){
        trips.get(tripId).removeTripItem(tripItemId);
    } 
    
    public TripItem[] getTripItems(String tripId) {
        return trips.get(tripId).getTripItems();
    }
    
    public double getTotalPrice(String tripId){ 
        return trips.get(tripId).getTripPrice();
    }
    
    public void bookTrip(String tripId) {
        trips.get(tripId).bookTrip();
    }
}
