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

package com.tuscanyscatours.payment.creditcard.impl;

import org.osoa.sca.RequestContext;
import org.osoa.sca.annotations.Callback;
import org.osoa.sca.annotations.Context;
import org.osoa.sca.annotations.Service;

import com.tuscanyscatours.payment.creditcard.CreditCardDetailsType;
import com.tuscanyscatours.payment.creditcard.CreditCardPayment;
import com.tuscanyscatours.payment.creditcard.CreditCardSecurity;

@Service(CreditCardPayment.class)
public class CreditCardPaymentCallbackImpl implements CreditCardPayment {
	
    @Callback
    protected CreditCardSecurity ccSecurity;

    @Context
    protected RequestContext rqContext;

   
    public String authorize(CreditCardDetailsType card, float amount) {
		if (amount > 1000) {
			for (int i = 0; i < 3; i++) {
				String pwd = null;
				if (amount < 10000) {
					// using injected callback proxy
				    pwd = ccSecurity.checkSecurity("Enter password");
				} else {
					// using request context to get callback proxy
				    CreditCardSecurity ccSecurity = rqContext.getCallback();
				    pwd = ccSecurity.checkSecurity("Enter password");
				}

				if (verifyPassword(card, pwd)) {
					break;
				}
				if (i == 2) {
					return "BadPassword";
				}
			}
		}
		makePayment(card, amount);
		return "OK";
	}

	private boolean verifyPassword(CreditCardDetailsType card, String pw) {
		if (pw.equals("abcxyz")){
			return true;
		} else {
			return false;
		}
	}

	private void makePayment(CreditCardDetailsType card, float amount) {
        // payment processing
	}
    
}
