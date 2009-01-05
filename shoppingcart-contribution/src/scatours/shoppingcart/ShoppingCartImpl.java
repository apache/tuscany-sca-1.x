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
package scatours.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.osoa.sca.annotations.ConversationID;
import org.osoa.sca.annotations.Destroy;
import org.osoa.sca.annotations.Init;

import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;


import scatours.common.TripItem;

/**
 * An implementation of the Trip service
 */
@Scope("CONVERSATION")
@Service(interfaces={ShoppingCart.class})
public class ShoppingCartImpl implements ShoppingCart{

    @ConversationID
    protected String conversationId;
    
    private List<String> itemIds = new ArrayList<String>();
     
    // Trip methods
    
    @Init
    public void initTrip() {
        System.out.println("Cart init for id: " + conversationId);
    }
    
    @Destroy
    public void destroyTrip() {
        System.out.println("Cart destroy for id: " + conversationId);
    }
    
    
    public void addItem(String itemId){
        itemIds.add(itemId);
    }
    
    public void removeItem(String itemId){
        itemIds.remove(itemId);
    }  
    
    public String[] getItems() {
        return itemIds.toArray(new String[itemIds.size()]);
    }
}
