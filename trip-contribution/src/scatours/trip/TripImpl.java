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
package scatours.trip;

import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

import scatours.common.Search;
import scatours.common.SearchCallback;
import scatours.common.TripItem;
import scatours.common.TripLeg;
import scatours.currencyconverter.CurrencyConverter;

/**
 * An implementation of the Trip service
 */
@Scope("COMPOSITE")
@Service(interfaces={Trip.class})
public class TripImpl implements Trip, SearchCallback {
    
    @Reference
    protected CurrencyConverter currencyConverter;
    
    @Reference 
    protected Search hotelSearch;
        
    @Property
    public String quoteCurrencyCode = "USD";
    
    private List<TripItem> searchResults = new ArrayList<TripItem>();
    
    public TripItem[] search(TripLeg tripLeg) {
        
        hotelSearch.searchAsynch(tripLeg);
        //flightSearch.searchAsynch(tripLeg);
        //carSearch.searchAsynch(tripLeg);
        
        // TODO - extend this to have the three searches run in parallel
        
        TripItem[] tripItemArray = searchResults.toArray(new TripItem[searchResults.size()]);
        searchResults.clear();
        
        return tripItemArray;
    }
       
    public double getTotalPrice(){
        String supplierCurrencyCode = "USD";
        double price = 100.00;
       
        return currencyConverter.convert(supplierCurrencyCode, 
                                         quoteCurrencyCode, 
                                         price);
    }
    
    public void searchResults(TripItem[] items){
        for(int i = 0; i < items.length; i++ ){
            searchResults.add(items[i]);
        }
    }
}
