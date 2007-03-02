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
package org.apache.tuscany.sca.itest;


import helloworld.HelloWorldService;
import junit.framework.Assert;

import org.apache.tuscany.test.SCATestCase;
import org.apache.tuscany.test.SCATestCaseRunner;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Test case for binding.ws references 
 */
@SuppressWarnings("deprecation")
public class WSReferencesTestCase extends SCATestCase {

    private SCATestCaseRunner server;
    private CompositeContext compositeContext;

    @Override
    protected void setUp() throws Exception {
    	setApplicationSCDL("WSBindingTest.composite");
        super.setUp();
        
        compositeContext = CurrentCompositeContext.getContext();

        server =  new SCATestCaseRunner(HelloWorldServerTestCase.class);
        server.setUp();
    }
    
//    public void testWSClient() throws Exception {
//        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientOld");
//        String msg = helloWorldService.getGreetings("Petra");
//        Assert.assertEquals("Hi Petra", msg);
//    }
//    
//    public void testWSClientSimplest() throws Exception {
//        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientSimplest");
//        String msg = helloWorldService.getGreetings("Petra");
//        Assert.assertEquals("Hi Petra", msg);
//    }
//
//    public void testWSClientURI() throws Exception {
//        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientURI");
//        String msg = helloWorldService.getGreetings("Petra");
//        Assert.assertEquals("Hi Petra", msg);
//    }

    public void testWSClientServiceSimplest() throws Exception {
        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientServiceSimplest");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("Hi Petra", msg);
    }

    public void testWSClientServiceDefaultName() throws Exception {
        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientServiceDefaultName");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("Hi Petra", msg);
    }

    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();
    	server.tearDown();
    }

}
