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

package scatours.client;

import org.osoa.sca.RequestContext;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import calendar.Calendar;

import scatours.common.Search;
import scatours.common.SearchCallback;
import scatours.common.TripItem;
import scatours.common.TripLeg;
import scatours.hotel.HotelInfo;
import scatours.hotel.HotelManagement;

@Service(Runnable.class)
public class TestClient implements SearchCallback {
    @Reference
    protected Search hotelSearchRemoteRequestResponse;
    
    @Reference
    protected Calendar calendarLocalRequestResponse;

    public TestClient() {
    }

    // Runnable method
    
    public void run() {
    	System.out.println("Calling hotel component over remote binding");
    	runRemoteRequestResponse();
    	
    	System.out.println("Calling calendar component over local binding");
    	runLocalRequestReponse();
    }
    
    private void runRemoteRequestResponse() {
    	TripLeg tripLeg = getTestTripLeg();
    	TripItem[] tripItems = hotelSearchRemoteRequestResponse.searchSynch(tripLeg);
    	for (TripItem tripItem : tripItems){
    		System.out.println("Found hotel - " + tripItem.getName());
    	}
    }
    
    private void runLocalRequestReponse() {
    	TripLeg tripLeg = getTestTripLeg();
    	String toDate = calendarLocalRequestResponse.getEndDate(tripLeg.getFromDate(), 10);
    	tripLeg.setToDate(toDate);
    	System.out.println("Calculated trip end date - " + toDate);
    }
    
    private void runRemoteOneWay() {
    	
    }
    
    private void runConversational() {
    	
    }
    
    private void runCallbac() {
    	
    }
    
    private void runStatefulCallback() {
    	
    }
    
    private TripLeg getTestTripLeg(){
    	TripLeg tripLeg = new TripLeg();
    	tripLeg.setFromLocation("LGW");
    	tripLeg.setToLocation("FLR");
    	tripLeg.setFromDate("06/12/09 00:00");
    	tripLeg.setToDate("13/12/09 00:00");
    	tripLeg.setNoOfPeople("1");
    	tripLeg.setId("TRIP27");
    	
    	return tripLeg;
    }
    
    // SearchCallback methods
    
    public void searchResults(TripItem[] items){
/*    	
        RequestContext requestContext = componentContext.getRequestContext();
        Object callbackID = requestContext.getServiceReference().getCallbackID();
        System.out.println(callbackID);
        
        if (items != null) {
            for(int i = 0; i < items.length; i++ ){
                searchResults.add(items[i]);
            }
        }
        
        responsesReceived++;
        try {
            synchronized (this) {
                this.notifyAll();
            }
        } catch (Exception ex) {
        }
*/        
    }     
}
