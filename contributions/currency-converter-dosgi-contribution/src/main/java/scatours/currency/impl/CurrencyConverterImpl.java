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
package scatours.currency.impl;

import java.util.HashMap;
import java.util.Map;

import org.oasisopen.sca.annotation.Service;

import scatours.currency.CurrencyConverter;

/**
 * An implementation of the CurrencyConverter service
 */
@Service(CurrencyConverter.class)
public class CurrencyConverterImpl implements CurrencyConverter {

    // currency index
    private Map<String, Integer> currencyIndex = new HashMap<String, Integer>();

    // exchange rates
    private final float rates[][] = { {1.00f, 0.50f, 0.66f}, {2.00f, 1.00f, 1.33f}, {1.50f, 0.75f, 1.00f}};

    public CurrencyConverterImpl() {
        currencyIndex.put("USD", new Integer(0));
        currencyIndex.put("GBP", new Integer(1));
        currencyIndex.put("EUR", new Integer(2));
    }

    public float getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        return rates[currencyIndex.get(fromCurrencyCode).intValue()][currencyIndex.get(toCurrencyCode).intValue()];
    }

    public float convert(String fromCurrencyCode, String toCurrencyCode, float amount) {
        float converted = amount * getExchangeRate(fromCurrencyCode, toCurrencyCode);
        System.out.println(fromCurrencyCode + amount + "=" + toCurrencyCode + converted);
        return converted;
    }
}
