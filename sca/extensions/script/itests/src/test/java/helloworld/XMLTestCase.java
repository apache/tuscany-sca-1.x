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

package helloworld;

import junit.framework.Assert;

import org.apache.tuscany.test.SCATestCase;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Test case for using XML in script components
 */
public class XMLTestCase extends SCATestCase {

    private CompositeContext compositeContext;

    public void testHelloWorldJavaScript() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldJSComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("e4xHi Petra", msg);
    }

    public void testHelloWorldJavaScript2() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldJSViaProxyComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("e4xHi Petra", msg);
    }

    @Override
    protected void setUp() throws Exception {
        setApplicationSCDL(getClass().getResource("/META-INF/sca/xml.composite"));
        super.setUp();
        this.compositeContext = CurrentCompositeContext.getContext();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
