package com.scatours.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class TripSearch extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, AIRPORT_CODES);
        
        AutoCompleteTextView textViewFrom = (AutoCompleteTextView) findViewById(R.id.edit_fromLocation);
        //ArrayAdapter adapterFrom = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, AIRPORT_CODES);
        textViewFrom.setAdapter(adapter);
        
        AutoCompleteTextView textViewTo = (AutoCompleteTextView) findViewById(R.id.edit_toLocation);
        //ArrayAdapter adapterTo = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, AIRPORT_CODES);
        textViewTo.setAdapter(adapter);
    }

    static final String[] AIRPORT_CODES = new String[] {
                                                    "LGW - London Gatwick Airport", 
                                                    "FLR - Luigi Ridolfi Airport",
                                                    "SFO - San Francisco Airport"
                                                    };

}