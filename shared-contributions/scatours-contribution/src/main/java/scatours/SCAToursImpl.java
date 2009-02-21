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
 * An implementation of the SCA tours component. This component currently provides
 * a front end to the components that the UI communicated with. It allows a conversation
 * to be held with the shopping cart as javascript doesn't support conversations. 
 * Other than that it's just a pass through so we could look to remove it.
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
    
    // SCAToursSearch methods
    
    public TripItem[] search(TripLeg tripLeg) {
        
        return travelCatalogSearch.search(tripLeg);
 
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
        // need to get the user id from the context here
        carts.get(cartId).checkout("Fred");
    }   
}
