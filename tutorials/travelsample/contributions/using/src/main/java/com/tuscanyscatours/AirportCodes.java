package com.tuscanyscatours;

public class AirportCodes {
    public String getAirport(String code) {
        if ("AAA".equals(code)) return "Anaa";
        else if ("AAB".equals(code)) return "Arrabury";
        // other airport codes and cities would follow here
        else return null;
    }
}
