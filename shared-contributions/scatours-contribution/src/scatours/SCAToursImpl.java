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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.osoa.sca.ComponentContext;
import org.osoa.sca.ServiceReference;
import org.osoa.sca.annotations.Context;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

import scatours.common.TripItem;
import scatours.common.TripLeg;
//import scatours.paymentprocess.PaymentProcess;
import scatours.shoppingcart.ShoppingCart;
import scatours.travelcatalog.TravelCatalogSearch;
import scatours.tripbooking.TripBooking;

/**
 * An implementation of the Trip service
 */
@Scope("COMPOSITE")
@Service(interfaces={SCAToursSearch.class, SCAToursBooking.class, SCAToursCart.class})
public class SCAToursImpl implements SCAToursSearch, SCAToursBooking, SCAToursCart{
    
    @Reference
    protected TravelCatalogSearch travelCatalogSearch;
    
    @Reference 
    protected TripBooking tripBooking;
    
    @Reference 
    protected ShoppingCart shoppingCart; 
    
    @Context
    protected ComponentContext componentContext;  
    
    private Map<String,ShoppingCart> carts = new HashMap<String,ShoppingCart>();
    private Map<String,TripBooking> trips = new HashMap<String,TripBooking>();
    //private Map<String, TripItem> searchItemsCache = new HashMap<String, TripItem>();
    
    // SCAToursSearch methods
    
    public TripItem[] search(TripLeg tripLeg) {
        
        TripItem[] searchItems = travelCatalogSearch.search(tripLeg);
        
        //for (int i =0; i< searchItems.length; i++){
        //    searchItemsCache.put(searchItems[i].getId(), searchItems[i]);
        //}
        return searchItems;
    } 

    // SCAToursBooking methods
    
    public String bookTrip(String cartId, TripItem trip){
        TripItem bookedTrip = tripBooking.bookTrip(cartId, trip);
        carts.get(cartId).addTrip(bookedTrip);
        return bookedTrip.getBookingCode();
    }
    
    // SCAToursCart methods
    
    public String newCart(){
        String cartId = UUID.randomUUID().toString();
        ServiceReference<ShoppingCart> shoppingCart = componentContext.getServiceReference(ShoppingCart.class, 
                                                                                           "shoppingCart");
        shoppingCart.setConversationID(cartId);
        carts.put(cartId, shoppingCart.getService());
        
        return cartId;
    }  
    
    public TripItem[] getTrips(String cartId){
        return carts.get(cartId).getTrips();
    }
    
    public void checkout(String cartId){
        shoppingCart.checkout("Fred");
    }
    
/*    
    public String addTrip(String cartId){
        String tripId = UUID.randomUUID().toString();
        ServiceReference<TripBooking> tripReference = componentContext.getServiceReference(TripBooking.class, 
                                                                                          "tripBooking");
        tripReference.setConversationID(tripId);
        trips.put(tripId, tripReference.getService());
        
        carts.get(cartId).addItem(tripId);
        return tripId;
    }
    
    public void removeTrip(String cartId, String tripId) {
        carts.get(cartId).removeItem(tripId);
    }
    
    public void addTripItem(String cartId, String tripId, String tripItemId){
        TripItem item = searchItemsCache.get(tripItemId);
        TripItem itemCopy = new TripItem(item);
        itemCopy.setTripId(tripId);
        trips.get(tripId).addTripItem(itemCopy);
    }
    
    public void removeTripItem(String cartId, String tripId, String tripItemId){
        trips.get(tripId).removeTripItem(tripItemId);
    } 
    
    public TripItem[] getTripItems(String cartId) {
        List<TripItem> returnTripItems = new ArrayList<TripItem>();
        
        for( String tripId : carts.get(cartId).getItems()){
            returnTripItems.addAll(Arrays.asList(trips.get(tripId).getTripItems()));
        }

        return returnTripItems.toArray(new TripItem[returnTripItems.size()]);
    }
    
    public double getTotalPrice(String cartId){ 
        double total = 0.0;
        
        for( String tripId : carts.get(cartId).getItems()){
            total += trips.get(tripId).getTripPrice();
        }

        return total;
    }
    
    public void checkout(String cartId){ 
        // get users credentials. Hard coded for now but should
        // come from the security context
        String customerId = "Fred Bloggs";
        float amount = (float)getTotalPrice(cartId);
        
        paymentProcess.makePayment(customerId, amount);
    }
    
*/    
}
