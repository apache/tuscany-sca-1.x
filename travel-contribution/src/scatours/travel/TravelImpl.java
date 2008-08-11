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
package scatours.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tuscany.sca.data.collection.Entry;
import org.apache.tuscany.sca.data.collection.NotFoundException;
import org.osoa.sca.ComponentContext;
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
import scatours.trip.Trip;

/**
 * An implementation of the Trip service
 */
@Scope("COMPOSITE")
@Service(interfaces={TravelSearch.class, TravelBooking.class})
public class TravelImpl implements TravelSearch, SearchCallback, TravelBooking{
    
    @Reference
    protected CurrencyConverter currencyConverter;
    
    @Reference 
    protected Search hotelSearch;
    
    @Reference 
    protected Search flightSearch;
    
    @Reference 
    protected Search carSearch;
    
    @Reference
    protected Trip trip;
        
    @Property
    public String quoteCurrencyCode = "USD";
    
    @Context
    protected ComponentContext componentContext;    
    
    private int responsesReceived = 0;
    
    private List<TripItem> searchResults = new ArrayList<TripItem>();
    private Map<String,Trip> trips = new HashMap<String,Trip>();
    
    // TravelSearch methods
    
    public TripItem[] search(TripLeg tripLeg) {
        
        searchResults.clear();
        responsesReceived = 0;
        
        hotelSearch.searchAsynch(tripLeg);
        flightSearch.searchAsynch(tripLeg);
        carSearch.searchAsynch(tripLeg);
        
        while (responsesReceived < 3){
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ex){
                // do nothing
            }
        }
        
        for (TripItem tripItem : searchResults){
            tripItem.setId(UUID.randomUUID().toString());
            tripItem.setTripId(tripLeg.getId());
            tripItem.setPrice(currencyConverter.convert(tripItem.getCurrency(), 
                                                        quoteCurrencyCode, 
                                                        tripItem.getPrice()));
            tripItem.setCurrency(quoteCurrencyCode);
        }
        
        return searchResults.toArray(new TripItem[searchResults.size()]);
    }
    
    // SearchCallback methods
    
    public void searchResults(TripItem[] items){
        for(int i = 0; i < items.length; i++ ){
            searchResults.add(items[i]);
        }
        
        responsesReceived++;
        try {
            synchronized (this) {
                this.notifyAll();
            }
        } catch (Exception ex) {
        }
    }    

    // TravelBooking methods
    
    public String newTrip(){
        String tripId = UUID.randomUUID().toString();
        ServiceReference<Trip> tripReference = componentContext.getServiceReference(Trip.class, 
                                                                                    "trip");
        tripReference.setConversationID(tripId);
        trips.put(tripId, tripReference.getService());
        return tripId;
    }
    
    public void addTripItem(String tripId, String id){
        for (TripItem tripItem : searchResults) {
            if (tripItem.getId().equals(id)){
                trips.get(tripId).addTripItem(tripItem);
            }
        }
    }
    
    public void removeTripItem(String tripId, String id){
        trips.get(tripId).removeTripItem(id);
    } 
    
    public TripItem[] getTripItems(String tripId) {
        return trips.get(tripId).getTripItems();
    }
    
    public double getTotalPrice(String tripId){ 
        return trips.get(tripId).getTripPrice();
    }
    
    public void purchaseTrip(String tripId) {
        trips.get(tripId).purchaseTrip();
    }
}
