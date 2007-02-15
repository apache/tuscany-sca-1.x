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
package org.apache.tuscany.sca.test.exceptions;

import junit.framework.TestCase;

import org.apache.tuscany.sca.test.exceptions.impl.StockTraderSDO;
import org.apache.tuscany.sca.test.exceptions.impl.StockTraderSDOImpl;
import org.apache.tuscany.test.SCATestCase;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

public class IntraCompositeTest extends SCATestCase {
    private StockTraderSDO stockTrader;

    private CompositeContext context;

    public void testALL() {
        stockTrader.tradingTest();
 
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = CurrentCompositeContext.getContext();
        assertNotNull(context);
        stockTrader = context.locateService(StockTraderSDO.class, "stockTraderSDOComponent" );
             

        assertNotNull(context);
    }
}
