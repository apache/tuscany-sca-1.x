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

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import calendar.Calendar;

import scatours.common.TripLeg;
import scatours.currencyconverter.CurrencyConverter;

@Service(Runnable.class)
public class InteractionRequestResponseClient implements Runnable {
	
    @Reference
    protected CurrencyConverter currencyConverterRequestResponse;

    public void run() {   	
    	System.out.println("\nCalling currency converter component using request response pattern");
    	double convertedAmount = currencyConverterRequestResponse.convert("GBP", "USD", 10.0);
    	System.out.println("10 GBP = " + convertedAmount + " USD");
    }
    
}
