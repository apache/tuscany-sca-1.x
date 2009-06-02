package com.scatours.android;

import scatours.SCAToursSearch;
import scatours.SCAToursSearchProxy;
import scatours.common.TripItem;
import scatours.common.TripLeg;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class TripSearch extends Activity {

    static final String[] AIRPORT_CODES = new String[] {
                                                    "LGW - London Gatwick Airport", 
                                                    "FLR - Luigi Ridolfi Airport",
                                                    "SFO - San Francisco Airport",
                                                    "GRU - Sao Paulo Airport",
                                                    "GIG - Rio de Janeiro Airport"
                                                    };

    
    private AutoCompleteTextView txtFromLocation, 
                                 txtToLocation, 
                                 txtDateStart, 
                                 txtDateEnd, 
                                 txtNumberOfPeople;
    private Button btnSearch;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, AIRPORT_CODES);
        
        txtFromLocation = (AutoCompleteTextView) findViewById(R.id.edit_fromLocation);
        //ArrayAdapter adapterFrom = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, AIRPORT_CODES);
        txtFromLocation.setAdapter(adapter);
        
        txtToLocation = (AutoCompleteTextView) findViewById(R.id.edit_toLocation);
        //ArrayAdapter adapterTo = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, AIRPORT_CODES);
        txtToLocation.setAdapter(adapter);
        
        txtDateStart = (AutoCompleteTextView) findViewById(R.id.edit_date_start);
        txtDateEnd = (AutoCompleteTextView) findViewById(R.id.edit_date_end);
        txtNumberOfPeople = (AutoCompleteTextView) findViewById(R.id.edit_NumberOfPeople);
        
        btnSearch = (Button) this.findViewById(R.id.btnSearch);
        
        doListen();
    }
    
    private void doListen() {
        
        txtFromLocation.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(getString(R.string.app_name),">>Item Clicked: " +AIRPORT_CODES[position]);
            }
            
        });
        
        txtFromLocation.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(getString(R.string.app_name),">>Item Selected: " +AIRPORT_CODES[position]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(getString(R.string.app_name),">>Selection cleared");
            }
            
        });
        

        //closeButton
        btnSearch.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
                
                doTripSearch(v);
                /*
                //Search trips here
                new AlertDialog.Builder(TripSearch.this)
                .setTitle("SCATour")
                .setMessage("You're about to search for trips !")
                .setIcon(R.drawable.icon)
                .setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        
                    }})
                    .setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            
                        }       
                    })
                    .show();*/
            }
        });
        
    }
    
    
    private void doTripSearch(View view) {
        SCAToursSearch searchProxy = new SCAToursSearchProxy();
        TripLeg tripLeg = new TripLeg();
        
        Log.i(getString(R.string.app_name),"From : " + txtFromLocation.getText().toString() );
        Log.i(getString(R.string.app_name),"To : " + txtToLocation.getText().toString() );
        Log.i(getString(R.string.app_name),"Start Date : " + txtDateStart.getText().toString() );
        Log.i(getString(R.string.app_name),"End Date : " + txtDateEnd.getText().toString() );
        Log.i(getString(R.string.app_name),"NumberOfPeople : " + txtNumberOfPeople.getText().toString() );
        
        tripLeg.setFromLocation("LGW");
        tripLeg.setToLocation("FLR");
        tripLeg.setFromDate("06/12/09");
        tripLeg.setToDate("13/12/09");
        tripLeg.setNoOfPeople("2");
        
        TripItem[] tripsAvailable = searchProxy.search(tripLeg);
        
        Log.i(getString(R.string.app_name),"Found " + tripsAvailable.length + " trips");
    }

}