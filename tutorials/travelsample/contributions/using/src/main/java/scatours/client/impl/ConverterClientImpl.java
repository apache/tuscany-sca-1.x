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
package scatours.client.impl;

import java.math.BigDecimal;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import com.tuscanyscatours.CurrencyConverter;

@Service(Runnable.class)
public class ConverterClientImpl {

    @Reference
    protected CurrencyConverter eur2jpy;

    @Reference
    protected CurrencyConverter usd2gbp;

    public void run() {
        BigDecimal jpy = eur2jpy.convert(new BigDecimal("1000.00"));
        System.out.println("Converted EUR 1000.00 to JPY " + jpy);
        BigDecimal gbp = usd2gbp.convert(new BigDecimal("1000.00"));
        System.out.println("Converted USD 1000.00 to GBP " + gbp);
    }
}
