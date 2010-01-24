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

package scatours.android;

import scatours.common.TripItem;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TripSearchResults extends Activity {
    private TripItem[] results;
    
    private ListView listResults;

    public TripSearchResults( ) {
        super();
    }

    public void setResults(TripItem[] results) {
        Log.i(getString(R.string.app_name),"Setting results : " + results.length);
        this.results = results;
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        Log.i(getString(R.string.app_name),"Displaying " + results.length + " trips");
        
        listResults = (ListView) findViewById(R.id.listPackages);
        //listResults.setAdapter(new ArrayAdapter<TripItem>(this, R.id.listPackages, results));
        

    }

}
