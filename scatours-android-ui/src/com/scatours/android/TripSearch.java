package com.scatours.android;

import java.util.Calendar;

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
        
        //String currentDate = getCurrentDate();
        //AutoCompleteTextView textStartDate = (AutoCompleteTextView) findViewById(R.id.date_start);
        //textStartDate.setText(currentDate.toCharArray(), 0, currentDate.length());
        
    }
    
    private String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        
        return Integer.toString(mMonth) + "/" + Integer.toString(mDay) + "/" + Integer.toString(mYear); 
    }

    static final String[] AIRPORT_CODES = new String[] {
                                                    "LGW - London Gatwick Airport", 
                                                    "FLR - Luigi Ridolfi Airport",
                                                    "SFO - San Francisco Airport"
                                                    };

}