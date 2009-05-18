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

package store.android;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import services.Catalog;
import services.CatalogProxy;
import services.Item;
import services.ShoppingCartProxy;
import android.app.TabActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class catalog  extends TabActivity {
	private Catalog catalogProxy = new CatalogProxy();
	private ShoppingCartProxy shoppingCartProxy = new ShoppingCartProxy(); 
	TabHost mTabHost;
	ListView itemsList, cartList;
	TextView txtTotal;
	TabSpec catalogTab, cartTab;
	String[] items;
	Set<String> cartItems=new HashSet<String>();
	
	
	private String[] getCatalogItems() {
		List<String> catalog = new ArrayList<String>();
		
        for(Item item : catalogProxy.get()) {
        	catalog.add(item.toString());
        }	
        
        String[] catalogArray = new String[catalog.size()];
        catalog.toArray(catalogArray);
        Log.e("Tuscany", "Catalog GET!!");
        
        return catalogArray;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.e("Tuscany", "Started");
        //Load UI from layout file
        setContentView(R.layout.main);
        
        findViews();
	    
	    //Get data to be loaded to UI
	    items=getCatalogItems();
	    
	    //Load UI with data
	    itemsList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,items));
	    itemsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    
	    //Set Listeners
	    listen();
        
    }
    
    /**
     *  Retrieve UI Content
     */
    public void findViews()
    {
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("catalog_tab").setIndicator("Catalog Items").setContent(R.id.ListView01));
	    mTabHost.addTab(mTabHost.newTabSpec("shopping_cart_tab").setIndicator("Shopping Cart").setContent(R.id.tab02));
	    mTabHost.setCurrentTab(0);
	    itemsList=(ListView)findViewById(R.id.ListView01);
	    cartList=(ListView)findViewById(R.id.ListView02);
	    txtTotal=(TextView)findViewById(R.id.txtTotal);
	    txtTotal.setTextSize((float) 25.0);
	    txtTotal.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    /**
     * Implements all needed listeners for the UI
     */
    public void listen()
    {
	    //Handles total display between tab switching
	    mTabHost.setOnTabChangedListener(new OnTabChangeListener(){

			public void onTabChanged(String tabId) {
				if(tabId.compareTo("shopping_cart_tab")==0)
				{
					if(!cartList.getAdapter().isEmpty())
					{
						txtTotal.setVisibility(TextView.VISIBLE);
					}
					else
						txtTotal.setVisibility(TextView.INVISIBLE);
					txtTotal.setText("Total: "+shoppingCartProxy.getTotal());
					
				}	
			}
	    });
	    
	    
	    //CatalogList Listener
	    itemsList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String item=itemsList.getAdapter().getItem((int)arg3).toString();
				
				//add item to shopping cart. For now, item can be added only once.
				//Items Quantity will be supported in coming versions. 
				if(cartItems.add(item))
				{
					if(shoppingCartProxy.addItem(Item.parseItem(item)))
						Log.i("Tuscany", "Entry to be added: "+item);
					else
						Log.e("Tuscany", "Unable to add entry: "+item);
					
				}
				else
				{
					shoppingCartProxy.removeItem(Item.parseItem(item));
					cartItems.remove(item);
					Log.i("Tuscany", "Entry to be removed: "+item);
				}
				reloadShoppingCart();
				
			}
	    });
    }
    
    /**
     * Refreshes the Shopping cart list when the adapter behind is updated
     */
    public void reloadShoppingCart()
	{
    	String[] cartArray=new String[cartItems.size()];
    	cartList.setChoiceMode(ListView.CHOICE_MODE_NONE);
		cartList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cartItems.toArray(cartArray)));
	}

    
    
}