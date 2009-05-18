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

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import services.atom.xml.AtomXML;
import services.json.rpc.JSONRpc;

public class ShoppingCartProxy {
	private static final String jsonRPCTotalServiceURI = "http://192.168.1.102:8080/ShoppingCart/Total";
    private static final String jsonRPCTotalRequest = "{\"id\": 4, \"method\": \"Service.getTotal\", \"params\": []}";
    private static final String atomXMLCartServiceURI="http://192.168.1.102:8080/ShoppingCart/Cart";

	public Item[] getItems() {
		return null;
	}
	
	public boolean addItem(Item item) {
		return AtomXML.postItem(atomXMLCartServiceURI, item);
	}
	
	public void removeItem(Item item) {
		
	}
	
	public void checkOut() {
		
	}
	
	public String getTotal() {
		String total = "";
		JSONObject json = null; 
		Log.e("TUSC", "getting total");
		
		try {
			json = JSONRpc.invoke(jsonRPCTotalServiceURI, jsonRPCTotalRequest);
			Log.e("TUSC", "Request OK");
			
			if(json != null) {
				total = json.getString("result");
				Log.e("TUSC", "Total: "+total);
			}
		} catch (JSONException e) {
			Log.e("TUSC",e.getMessage());
		}
		return total;
	}
}
