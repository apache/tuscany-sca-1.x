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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tuscany.sca.data.collection.Entry;
import org.apache.tuscany.sca.data.collection.NotFoundException;
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
@Service(interfaces={TripSearch.class, TripContents.class})
public class TripImpl implements TripSearch, SearchCallback, TripContents{
    
    @Reference
    protected CurrencyConverter currencyConverter;
    
    @Reference 
    protected Search hotelSearch;
    
    @Reference 
    protected Search flightSearch;
    
    @Reference 
    protected Search carSearch;
        
    @Property
    public String quoteCurrencyCode = "USD";
    
    private int responsesReceived = 0;
    
    private List<TripItem> searchResults = new ArrayList<TripItem>();
    private Map<String, TripItem> tripItems = new HashMap<String, TripItem>();
    
    // TripSearch methods
    
    public TripItem[] search(TripLeg tripLeg) {
        
        searchResults.clear();
        responsesReceived = 0;
        
        hotelSearch.searchAsynch(tripLeg);
        flightSearch.searchAsynch(tripLeg);
        carSearch.searchAsynch(tripLeg);
        
        while (responsesReceived < 3){
            try {
                this.wait();
            } catch (InterruptedException ex){
                // do nothing
            }
        }
        
        for (TripItem tripItem : searchResults){
            tripItem.setId(UUID.randomUUID().toString());
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
            this.notifyAll();
        } catch (Exception ex) {
        }
    }    

    // TripContents methods
    public void addTripItem(String id){
        for (TripItem tripItem : searchResults) {
            if (tripItem.getId().equals(id)){
                tripItems.put(id, tripItem);
            }
        }
    }
    
    public void removeTripItem(String id){
        tripItems.remove(id);
    }     
    
    // Not using the DataCollection iface yet as it seems like a 
    // likely attach vector to be passing complete tripItem records in
    // really need to look up the cached item based on id    
    public Entry<String, TripItem>[] getAll() {
        Entry<String, TripItem>[] entries = new Entry[tripItems.size()];
        int i = 0;
        for (Map.Entry<String, TripItem> e: tripItems.entrySet()) {
            entries[i++] = new Entry<String, TripItem>(e.getKey(), e.getValue());
        }
        return entries;
    }
    
    public TripItem get(String key) throws NotFoundException {
        TripItem item = tripItems.get(key);
        if (item == null) {
            throw new NotFoundException(key);
        } else {
            return item;
        }
    }

    public String post(String key, TripItem item) {
        tripItems.put(key, item);
        return key;
    }

    public void put(String key, TripItem item) throws NotFoundException {
        if (!tripItems.containsKey(key)) {
            throw new NotFoundException(key);
        }
        tripItems.put(key, item);
    }
    
    public void delete(String key) throws NotFoundException {
        if (key == null || key.equals("")) {
            tripItems.clear();
        } else {
            TripItem item = tripItems.remove(key);
            if (item == null)
                throw new NotFoundException(key);
        }
    }

    public Entry<String, TripItem>[] query(String queryString) {
        List<Entry<String, TripItem>> entries = new ArrayList<Entry<String,TripItem>>();
        if (queryString.startsWith("name=")) {
            String name = queryString.substring(5);
            for (Map.Entry<String, TripItem> e: tripItems.entrySet()) {
                TripItem item = e.getValue();
                if (item.getName().equals(name)) {
                    entries.add(new Entry<String, TripItem>(e.getKey(), e.getValue()));
                }
            }
        }
        return entries.toArray(new Entry[entries.size()]);
    }
   
    // TripTotal methods
    
    public double getTotalPrice(){ 
        double totalPrice = 0.0;
        
        for (TripItem tripItem : tripItems.values()){
            totalPrice += tripItem.getPrice();
        }
        
        return totalPrice;
    }
    

}
