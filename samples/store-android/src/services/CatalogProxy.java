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

package services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.json.rpc.JSONRpc;

public class CatalogProxy implements Catalog {
    // see http://developer.android.com/guide/developing/tools/emulator.html
    private static final String jsonRPCServiceURI = "http://10.0.2.2:8080/Catalog";
    private static final String jsonRPCRequest = "{\"id\": 3, \"method\": \"Service.get\", \"params\": []}";
	
    private List<Item> catalog = new ArrayList<Item>();
    
    public CatalogProxy() {
    	initialize();
    }
   
	public void initialize() {
		JSONObject json = null; 
			
		try {
			json = JSONRpc.invoke(jsonRPCServiceURI, jsonRPCRequest);
			
			if(json == null) {
				return; 
			}
			
			JSONArray result = json.getJSONArray("result");
			for(int i = 0; i < result.length(); i++) {
				Item item = new Item();
				item.setName(result.getJSONObject(i).getString("name"));
				item.setPrice(result.getJSONObject(i).getString("price"));
				
				catalog.add(item);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Item[] get() {
		Item[] catalogArray = new Item[catalog.size()];
		catalog.toArray(catalogArray);
		return catalogArray;
	}
	
}
