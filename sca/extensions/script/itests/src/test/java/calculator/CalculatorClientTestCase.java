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

package calculator;

import junit.framework.Assert;

import org.apache.tuscany.test.SCATestCase;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Test case for caluclator service 
 */
public class CalculatorClientTestCase extends SCATestCase {

    private CompositeContext compositeContext;
    
    public void testHelloWorldRuby() throws Exception {
        CalculatorService calculatorService = compositeContext.locateService(CalculatorService.class, "CalculatorComponent");

        Assert.assertEquals(3.0, calculatorService.add(1,2));

        Assert.assertEquals(1.5, calculatorService.div(3,2));
    }

    @Override
    protected void setUp() throws Exception {
        setApplicationSCDL(getClass().getResource("/calculator/sample.calculator.composite"));
        super.setUp();
        this.compositeContext = CurrentCompositeContext.getContext();
    }
    
    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();
    }

}
