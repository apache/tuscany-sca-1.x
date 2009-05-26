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
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.view.LayoutInflater;



public class catalog  extends TabActivity {
	private Catalog catalogProxy = new CatalogProxy();
	private ShoppingCartProxy shoppingCartProxy = new ShoppingCartProxy(); 
	TabHost mTabHost;
	ListView itemsList, cartList;
	TextView txtTotal,txtEmpty;
	TabSpec catalogTab, cartTab;
	Button btnClean;
	Item[] items;
	List<Item> cartItems=new ArrayList<Item>();
	
	
	private void getCatalogItems() {
		items=catalogProxy.get();
	}
	
	private void getCartItems()
	{
		Item[] i=shoppingCartProxy.getItems();
		if(i!=null)
		{
			for(Item item:i)
			{
				cartItems.add(item);
				
			}
		}
		Log.e(getString(R.string.app_tag), String.valueOf(cartItems.size())+" cart items retrieved");
	}
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.e(getString(R.string.app_tag), getString(R.string.start_tag));
        //Load UI from layout file
        setContentView(R.layout.main);
        
	    
        
        findViews();
	    
	    //Get data to be loaded to UI
        getCatalogItems();
	    getCartItems();
	    
	    
	    //Load UI with data
	    itemsList.setAdapter(new ArrayAdapter<Item>(this,
                R.layout.cat_row,R.id.txtItemC,items));
	    itemsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	    
	    itemsList.setClickable(true);
	    reloadShoppingCart();
	    
	    
	    //Set Listeners
	    listen();
	    
	    
	    
        
    }
    
   
    
    /**
     *  Retrieve UI Content
     */
    public void findViews()
    {
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.tab_catalog)).setIndicator(getString(R.string.title_catalog)).setContent(R.id.ListView01));
	    mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.tab_shop)).setIndicator(getString(R.string.title_shop)).setContent(R.id.tab02));
	    mTabHost.setCurrentTab(0);
	    itemsList=(ListView)findViewById(R.id.ListView01);
	    cartList=(ListView)findViewById(R.id.ListView02);
	    btnClean=(Button)findViewById(R.id.btnClean);
	    btnClean.setText(R.string.btn_clean);
	    txtTotal=(TextView)findViewById(R.id.txtTotal);
	    txtTotal.setTextSize((float) 15.0);
	    txtTotal.setTypeface(Typeface.DEFAULT_BOLD);
	    txtEmpty=(TextView)findViewById(R.id.txtEmpty);
	    txtEmpty.setTypeface(null, Typeface.ITALIC);
	    
    }
    
    /**
     * Implements all needed listeners for the UI
     */
    public void listen()
    {
	    //Handles total display between tab switching
	    mTabHost.setOnTabChangedListener(new OnTabChangeListener(){

			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.compareTo("shopping_cart_tab")==0)			
					reloadShoppingCart();		
					
			}
	    });
	    
	    
	    
	    
	    
	    btnClean.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(catalog.this)
                .setTitle("Tuscany Android Store")
                .setMessage("You're about to erase all items. Proceed ?")
                .setIcon(R.drawable.icon)
                .setPositiveButton(R.string.alert_yes,
                                new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	if(shoppingCartProxy.clearCartContent())
    				{
    					cartItems.clear();
    					Log.i(getString(R.string.app_tag), getString(R.string.del_all_ok));
    					reloadShoppingCart();
    				}
                }})
                .setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener(){

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					} 	
                })
                .show();

				
				
			}
	    	
	    });
	    
	    itemsList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				addItemAction(items[(int)arg3]);
				
			}
	    	
	    });
	    
	    cartList.setOnItemClickListener(new OnItemClickListener(){


			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				removeItemAction(cartItems.get((int)arg3));
			}
	    	
	    });
	    
	    
	    
    }
    
    public void addItemAction(Item item)
    {
    	//add item to shopping cart. 
		Item tmp=new Item(item.getName(), item.getPrice());
		if(shoppingCartProxy.addItem(tmp))
		{
			cartItems.add(tmp);
			Log.i(getString(R.string.app_tag), getString(R.string.add_entry_ok)+item);
		}
			
		else
			Log.e(getString(R.string.app_tag), getString(R.string.add_entry_ko)+item);
			
    }
    
    public void removeItemAction(Item item)
    {
    	if(shoppingCartProxy.removeItem(item) && cartItems.remove(item))
		{
			Log.i(getString(R.string.app_tag), getString(R.string.del_entry_ok)+item);						
		}
		else
			Log.i(getString(R.string.app_tag), getString(R.string.del_entry_ko)+item);
    	reloadShoppingCart();
		
    }
    
    /**
     * Refreshes the Shopping cart list when the adapter behind is updated
     */
    public void reloadShoppingCart()
	{
    	Item[] cartArray=new Item[cartItems.size()];
    	cartList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	cartList.setClickable(true);
		cartList.setAdapter(new ArrayAdapter<Item>(this,
                R.layout.shop_row, R.id.txtItemS, cartItems.toArray(cartArray)));
		if(!cartList.getAdapter().isEmpty())
		{
			txtTotal.setVisibility(TextView.VISIBLE);
			txtEmpty.setText("Click on an item below to remove it");
			btnClean.setVisibility(Button.VISIBLE);
		}
		else
		{
			txtTotal.setVisibility(TextView.INVISIBLE);
			txtEmpty.setVisibility(TextView.VISIBLE);
			txtEmpty.setText(R.string.txt_empty);
			btnClean.setVisibility(Button.INVISIBLE);				
		}
		String tt=shoppingCartProxy.getTotal();	
		txtTotal.setText(getString(R.string.title_order)+": "+(tt.length()>5?tt.substring(0,5):tt));
		
	}
    
    
    
}