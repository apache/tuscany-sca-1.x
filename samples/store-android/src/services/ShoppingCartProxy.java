package services;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import services.json.rpc.JSONRpc;

public class ShoppingCartProxy {
	private static final String jsonRPCTotalServiceURI = "http://192.168.1.102:8080/ShoppingCart/Total";
    private static final String jsonRPCTotalRequest = "{\"id\": 4, \"method\": \"Service.getTotal\", \"params\": []}";

	public Item[] getItems() {
		return null;
	}
	
	public void addItem(Item item) {
		
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
