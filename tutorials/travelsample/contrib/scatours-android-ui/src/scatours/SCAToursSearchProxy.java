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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scatours.common.TripItem;
import scatours.common.TripLeg;
import scatours.jsonrpc.JSONRpc;

public class SCAToursSearchProxy implements SCAToursSearch {
    // see http://developer.android.com/guide/developing/tools/emulator.html
    private static final String jsonRPCServiceURI = "http://10.0.2.2:8080/SCAToursComponent/SCAToursSearch";
    private static final String jsonRPCRequest = "{\"id\": 5, \"method\": \"Service.search\", \"params\": [{\"id\": \"5f9a10f2-527f-4d91-a13c-b1aa2baaedd8\", \"fromLocation\": \"LGW\", \"toLocation\": \"FLR\", \"fromDate\": \"06/12/09\", \"toDate\": \"13/12/09\", \"noOfPeople\": \"2\"}]}";

    private List<TripItem> tripCatalog = new ArrayList<TripItem>();
    
    public SCAToursSearchProxy() {
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
                TripItem item = new TripItem();
                
                item.setType(result.getJSONObject(i).getString("type"));
                item.setName(result.getJSONObject(i).getString("name"));
                item.setDescription(result.getJSONObject(i).getString("description"));
                item.setLocation(result.getJSONObject(i).getString("location"));
                item.setFromDate(result.getJSONObject(i).getString("fromDate"));
                item.setToDate(result.getJSONObject(i).getString("toDate"));
                item.setPrice(result.getJSONObject(i).getDouble("price"));
                item.setCurrency(result.getJSONObject(i).getString("currency"));

                tripCatalog.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    
    public TripItem[] search(TripLeg tripLeg) {
        TripItem[] catalogArray = new TripItem[tripCatalog.size()];
        tripCatalog.toArray(catalogArray);
        return catalogArray;
    }
}
