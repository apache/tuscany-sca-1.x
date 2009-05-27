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

import services.atom.xml.AtomXML;
import services.json.rpc.JSONRpc;
import android.util.Log;

public class ShoppingCartProxy {
    private static final String jsonRPCTotalServiceURI = "http://10.0.2.2:8080/ShoppingCart/Total";
    private static final String jsonRPCTotalRequest = "{\"id\": 4, \"method\": \"Service.getTotal\", \"params\": []}";
    private static final String atomXMLCartServiceURI="http://10.0.2.2:8080/ShoppingCart/Cart";


    public Item[] getItems() {
        return AtomXML.getItems(atomXMLCartServiceURI);
    }

    public boolean addItem(Item item) {
        String content="<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
        "<title>item</title>" +
        "<content type=\"text/xml\">" +
        "<Item xmlns=\"http://services/\">" +
        "<name xmlns=\"\">" + item.getName()+ "</name>" +
        "<price xmlns=\"\">" +item.getPrice()+"</price>" +
        "</Item></content></entry>";
        
        String key =AtomXML.postItem(atomXMLCartServiceURI, content);
        if(key==null) {
            return false;
        }
        item.setKey(key);
        Log.i("TUSCANY shopping cart proxy", key);
        return true;
    }

    public boolean removeItem(Item item) {
        Log.e("Sent key",item.getKey());
        String uri=atomXMLCartServiceURI+"/"+item.getKey();
        return AtomXML.performdelete(uri);		
    }

    public boolean clearCartContent() {
        return AtomXML.performdelete(atomXMLCartServiceURI);
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

            if (json != null) {
                total = json.getString("result");
                Log.e("TUSC", "Total: " + total);
            }
        } catch (JSONException e) {
            Log.e("TUSC", e.getMessage());
        }
        return total;
    }
}
