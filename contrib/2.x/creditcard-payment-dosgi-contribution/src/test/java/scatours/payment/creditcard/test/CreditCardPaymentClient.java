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

package scatours.payment.creditcard.test;

import scatours.payment.creditcard.CreditCardDetailsType;
import scatours.payment.creditcard.CreditCardPayment;
import scatours.payment.creditcard.CreditCardPaymentService;
import scatours.payment.creditcard.CreditCardTypeType;
import scatours.payment.creditcard.ObjectFactory;
import scatours.payment.creditcard.PayerType;

/**
 *
 */
public class CreditCardPaymentClient {
    static String invoke() {
        CreditCardPaymentService service = new CreditCardPaymentService();
        ObjectFactory objectFactory = new ObjectFactory();
        CreditCardDetailsType ccDetails = objectFactory.createCreditCardDetailsType();
        ccDetails.setCreditCardType(CreditCardTypeType.fromValue("Visa"));
        ccDetails.setCreditCardNumber("1234-5678-1234-5678");
        ccDetails.setCVV2("123");
        ccDetails.setExpMonth(12);
        ccDetails.setExpYear(2011);
        PayerType ccOwner = objectFactory.createPayerType();
        ccOwner.setName("Fred");
        ccDetails.setCardOwner(ccOwner);
        CreditCardPayment cc = service.getCreditCardPaymentPort();
        String status = cc.authorize(ccDetails, 100.00f);
        System.out.println("Status of the transaction: " + status);
        return status;
    }

    public static void main(String[] args) {
        invoke();
    }
}
