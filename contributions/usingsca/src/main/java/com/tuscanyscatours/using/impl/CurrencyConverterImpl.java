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
package com.tuscanyscatours.using.impl;

import java.math.BigDecimal;
import org.osoa.sca.annotations.Property;
import com.tuscanyscatours.CurrencyConverter;

public class CurrencyConverterImpl implements CurrencyConverter {

    @Property
    protected String fromCurrency;

    @Property
    protected String toCurrency;

    public BigDecimal convert(BigDecimal amount) {
        return amount.multiply(getRate(toCurrency))
                     .divide(getRate(fromCurrency), 2, 0);
    }

    private BigDecimal getRate(String currency) {
        int rate = 0; 
        for (int i = 0; i < currency.length(); i++) {
            rate += currency.codePointAt(i);
        }
        return new BigDecimal(rate).divide(new BigDecimal(100));
    }
}
