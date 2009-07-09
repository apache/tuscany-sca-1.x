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

package payment;

import payment.creditcard.CreditCardDetailsType;
import payment.creditcard.CreditCardPayment;
import payment.creditcard.CreditCardTypeType;
import payment.creditcard.ObjectFactory;
import payment.creditcard.PayerType;
import scatours.emailgateway.EmailGateway;
import scatours.emailgateway.EmailType;

//@Service(Payment.class)
public class PaymentImpl implements Payment {

    protected CreditCardPayment creditCardPayment;
    protected EmailGateway emailGateway;
    protected float transactionFee = 0;
    
    //@Reference
    public void setCreditCardPayment(CreditCardPayment creditCardPayment) {
        this.creditCardPayment = creditCardPayment;
    }
    
    public void setEmailGateway(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }
    
    //@Property
    public void setTransactionFee(Float transactionFee) {
        this.transactionFee = transactionFee;
    }
    
    public String makePaymentMember(String customerId, float amount) {
        
        ObjectFactory objectFactory = new ObjectFactory();
        CreditCardDetailsType ccDetails = objectFactory.createCreditCardDetailsType();
        ccDetails.setCreditCardType(CreditCardTypeType.fromValue("Visa"));
        PayerType ccOwner = objectFactory.createPayerType();
        ccOwner.setName(customerId);
        ccDetails.setCardOwner(ccOwner);
        
        amount += transactionFee;
        
        String status = creditCardPayment.authorize(ccDetails, amount);
        
        scatours.emailgateway.ObjectFactory emailFactory = new scatours.emailgateway.ObjectFactory();
        EmailType email = emailFactory.createEmailType();
        email.setTitle("Payment Received");
        email.setTo(customerId);
        
        emailGateway.sendEmail(email);
        
        return status;
    }

}
