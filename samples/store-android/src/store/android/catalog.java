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
import java.util.List;

import services.Catalog;
import services.CatalogProxy;
import services.Item;
import services.ShoppingCartProxy;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class catalog  extends ListActivity {
	private Catalog catalogProxy = new CatalogProxy();
	private ShoppingCartProxy shoppingCartProxy = new ShoppingCartProxy(); 
		
	private String[] getCatalogItems() {
		List<String> catalog = new ArrayList<String>();
		
        for(Item item : catalogProxy.get()) {
        	catalog.add(item.getName() + " - " + item.getPrice());
        }	
        
        String[] catalogArray = new String[catalog.size()];
        catalog.toArray(catalogArray);
        
        return catalogArray;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, this.getCatalogItems()));

        Button addToCart = (Button) findViewById(1);
        /*
        addToCart.setOnClickListener(new View.OnClickListener() {
        	public void onCLick(View v) {
        		this.
        	}
        });
        */
        
        
        final ListView listView = getListView();

        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String item = this.getCatalogItems()[position];
		System.out.println("Item clicked : " + item);
		
		System.out.println("Selected items:");
		SparseBooleanArray checkedItems = l.getCheckedItemPositions();
		for(int i = 0; i < checkedItems.size(); i++) {
			if(checkedItems.valueAt(i) == true) {
				System.out.println(">>>" + this.getCatalogItems()[checkedItems.keyAt(i)]);
			}
		}
		System.out.println("Total: "  + shoppingCartProxy.getTotal());

		//l.getCheckedItemPosition();
		//Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://192.168.1.104:8080/ShoppingCart/Cart"));
		//startActivity(webIntent);
	} 
    
    
}