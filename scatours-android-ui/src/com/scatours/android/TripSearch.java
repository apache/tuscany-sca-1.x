package com.scatours.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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
        
        
        Button closeButton = (Button) this.findViewById(R.id.btnSearch);
        closeButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
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
                    .show();




            }
        });
        
    }

    static final String[] AIRPORT_CODES = new String[] {
                                                    "LGW - London Gatwick Airport", 
                                                    "FLR - Luigi Ridolfi Airport",
                                                    "SFO - San Francisco Airport"
                                                    };

}