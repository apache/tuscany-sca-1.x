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
package client;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

import currencyconverter.CurrencyConverter;

/**
 * This shows how to run the CurrencyConverter component.
 */
public class CurrencyConverterClient {
 
    public  final static void main(String[] args) throws Exception {
        SCANodeFactory factory = SCANodeFactory.newInstance();
        SCANode node = factory.createSCANodeFromClassLoader("currencyconverter.composite", 
                                                            null);
        node.start();
        
        CurrencyConverter currencyConverter = 
            ((SCAClient)node).getService(CurrencyConverter.class, 
                                         "CurrencyConverterComponent");

        System.out.println(currencyConverter.convert("GBP", "USD", 10.00));

        node.stop();        
    }    
}
