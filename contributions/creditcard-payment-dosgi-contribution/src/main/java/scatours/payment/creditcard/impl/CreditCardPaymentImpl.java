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

package scatours.payment.creditcard.impl;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import scatours.currency.CurrencyConverter;
import scatours.payment.creditcard.CreditCardDetailsType;
import scatours.payment.creditcard.CreditCardPayment;

public class CreditCardPaymentImpl implements CreditCardPayment {
    private BundleContext context;

    public CreditCardPaymentImpl(BundleContext context) {
        super();
        this.context = context;
    }

    public String authorize(CreditCardDetailsType creditCard, float amount) {
        float charge = amount;
        if (creditCard != null) {
            try {
                print(creditCard);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Credit card: name = " + creditCard.getCardOwner().getName()
                + " number = "
                + creditCard.getCreditCardNumber()
                + " for amount "
                + amount
                + " EUR");
            charge = convertCurrency(amount);
        } else {
            System.out.println("Credit card is null");
            return "FAILURE: Invalid Credit Card Number";
        }

        return "SUCCESS: Auth Code=" + UUID.randomUUID() + " (EUR"+amount+"->USD" + charge + ")";
    }

    private void print(CreditCardDetailsType creditCard) throws JAXBException, PropertyException {
        JAXBContext context = JAXBContext.newInstance(CreditCardDetailsType.class);
        QName name = new QName("http://ns1", "creditCard");
        JAXBElement<Object> element = new JAXBElement<Object>(name, Object.class, creditCard);
        StringWriter writer = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        context.createMarshaller().marshal(element, writer);
        writer.flush();
        System.out.println("[CreditCard]: ");
        System.out.println(writer.toString());
    }

    private float convertCurrency(float amount) {
        ServiceReference ref = context.getServiceReference(CurrencyConverter.class.getName());
        if (ref != null) {
            CurrencyConverter converter = (CurrencyConverter)context.getService(ref);
            if (converter != null) {
                amount = converter.convert("EUR", "USD", amount);
                context.ungetService(ref);
                return amount;
            }
        }
        throw new IllegalArgumentException("Currency cannot be converted");
    }
}
