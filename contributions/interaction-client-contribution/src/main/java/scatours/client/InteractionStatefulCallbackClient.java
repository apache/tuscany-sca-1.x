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

import java.util.concurrent.CountDownLatch;

import org.osoa.sca.ComponentContext;
import org.osoa.sca.ServiceReference;
import org.osoa.sca.annotations.Context;
import org.osoa.sca.annotations.ConversationID;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

import scatours.common.Search;
import scatours.common.SearchCallback;
import scatours.common.TripItem;
import scatours.common.TripLeg;

@Scope("CONVERSATION")
@Service(Runnable.class)
public class InteractionStatefulCallbackClient implements Runnable, SearchCallback{
	
    @Reference
    protected Search flightSearchStatefulCallback;
    
    @Context
    protected ComponentContext componentContext;
    
    CountDownLatch resultsReceivedCountdown;
    
    public void run() {
    	System.out.println("\nCalling flight component using stateful callback interation pattern");
    	resultsReceivedCountdown = new CountDownLatch(1);
    	TripLeg tripLeg = getTestTripLeg();
    	
        ServiceReference<Search> dynamicFlightSearchStatefulCallback = 
            componentContext.getServiceReference(Search.class, "flightSearchStatefulCallback");
        dynamicFlightSearchStatefulCallback.setConversationID("SomeUniqeID"); 
        Search flightSearch = dynamicFlightSearchStatefulCallback.getService();
       
        flightSearch.searchAsynch(tripLeg);
    	       
        // wait for a while and see how the flight search is getting one
        try {
            Thread.sleep(1200);
        } catch(Exception ex){
            // do nothing
        }
        System.out.println("Flight search is " + flightSearch.getPercentComplete() + "% complete");
        	
    	// wait for responses to come back
        try {
            resultsReceivedCountdown.await();
        } catch (InterruptedException ex){
        }
    }
    
    public void searchResults(TripItem[] items){
        System.out.println("Received results in conversation - " + componentContext.getRequestContext().getServiceReference().getConversation().getConversationID());
        for (TripItem tripItem : items){
            System.out.println("Found flight - " + tripItem.getName());
        }
        resultsReceivedCountdown.countDown();
    }      
    
    public void setPercentComplete(String searchComponent, int percentComplete){
        System.out.println(searchComponent + " search is " + percentComplete + "% complete");
    }    
    
    private TripLeg getTestTripLeg(){
    	TripLeg tripLeg = new TripLeg();
    	tripLeg.setFromLocation("LGW");
    	tripLeg.setToLocation("FLR");
    	tripLeg.setFromDate("06/12/09");
    	tripLeg.setToDate("13/12/09");
    	tripLeg.setNoOfPeople("1");
    	tripLeg.setId("TRIP27");
    	return tripLeg;
    }    
}
