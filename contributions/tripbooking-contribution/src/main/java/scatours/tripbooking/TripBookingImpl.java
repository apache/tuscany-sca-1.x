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
package scatours.tripbooking;

import org.osoa.sca.ComponentContext;
import org.osoa.sca.annotations.Context;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import scatours.common.Book;
import scatours.common.TripItem;
import scatours.shoppingcart.ShoppingCart;

/**
 * An implementation of the Trip service
 */
@Service(interfaces={TripBooking.class})
public class TripBookingImpl implements TripBooking{

    @Reference 
    protected Book hotelBook;
    
    @Reference 
    protected Book flightBook;
    
    @Reference 
    protected Book carBook;
    
    @Reference 
    protected Book tripBook;   
    
    @Reference 
    protected ShoppingCart shoppingCart;
    
    @Context
    protected ComponentContext componentContext;     
    
    public TripItem bookTrip(String cartId, TripItem trip) {
        
        String bookingCode = "";
        
        // book any nested items
        TripItem[] nestedItems = trip.getTripItems();
        if (nestedItems != null){
            for(int i = 0; i < nestedItems.length; i++ ){
                TripItem tripItem = nestedItems[i];
                if (tripItem.getType().equals(TripItem.CAR)){
                    tripItem.setBookingCode(carBook.book(tripItem));
                } else if (tripItem.getType().equals(TripItem.FLIGHT)){
                    tripItem.setBookingCode(flightBook.book(tripItem));
                } else if (tripItem.getType().equals(TripItem.HOTEL)){
                    tripItem.setBookingCode(hotelBook.book(tripItem));
                } else {
                    tripItem.setBookingCode(tripItem.getType() + " is invalid");
                }
            }
        }
        
        // book the top level item if it's a packaged trip
        if (trip.getType().equals(TripItem.TRIP)){
            bookingCode = tripBook.book(trip);
            trip.setBookingCode(bookingCode);
        }
        
        // add trip to the shopping cart
        //ServiceReference<ShoppingCart> cart = componentContext.getServiceReference(ShoppingCart.class, 
        //                                                                           "shoppingCart");
        //cart.setConversationID(cartId);
        //cart.getService().addTrip(trip);
        
        return trip;
    }
 
    /*
    @ConversationID
    protected String conversationId;
    
    private Map<String, TripItem> tripItems = new HashMap<String, TripItem>();
     
    // Trip methods
    
    @Init
    public void initTrip() {
        System.out.println("Trip init for id: " + conversationId);
    }
    
    @Destroy
    public void destroyTrip() {
        System.out.println("Trip destroy for id: " + conversationId);
    }
    
    
    public void addTripItem(TripItem tripItem){
        tripItems.put(tripItem.getId(), tripItem);
    }
    
    public void removeTripItem(String id){
        tripItems.remove(id);
    }  
    
    public TripItem[] getTripItems() {
        return tripItems.values().toArray(new TripItem[tripItems.size()]);
    }
    
    public double getTripPrice(){ 
        double totalPrice = 0.0;
        
        for (TripItem tripItem : tripItems.values()){
            totalPrice += tripItem.getPrice();
        }
        
        return totalPrice;
    }
    
    public void bookTrip() {
        // TODO
    }
    */
}
