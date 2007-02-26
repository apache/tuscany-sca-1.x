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
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Test case for using references in script components
 */
public class PropertiesTestCase extends SCATestCase {

     private CompositeContext compositeContext;

    public void testJavaScriptDefault() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldJSDefaultComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("jsKia Ora Petra", msg);
    }

    public void testJavaScriptOverride() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldJSOverrideComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("jsNamaskaar Petra", msg);
    }

    public void testJavaScriptDynDefault() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldJSDynDefaultComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("jsYo! Petra", msg);
    }

    public void testJavaScriptDynOverride() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldJSDynOverrideComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("jsNamaste Petra", msg);
    }

    public void testPythonDefault() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldPythonDefaultComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("pyKia Ora Petra", msg);
    }
    public void testPythonOverride() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldPythonOverrideComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("pyNamaskaar Petra", msg);
    }

    public void testPythonDynDefault() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldPythonDynDefaultComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("pyYo! Petra", msg);
    }

    public void testPythonDynOverride() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldPythonDynOverrideComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("pyNamaste Petra", msg);
    }

    public void testRubyDefault() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldRubyDefaultComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("rbKia Ora Petra", msg);
    }
    public void testRubyOverride() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldRubyOverrideComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("rbNamaskaar Petra", msg);
    }

    public void testRubyDynDefault() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldRubyDynDefaultComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("rbYo! Petra", msg);
    }
    public void testRubyDynOverride() throws Exception {
        HelloWorldService helloWorldService =
            compositeContext.locateService(HelloWorldService.class, "HelloWorldRubyDynOverrideComponent");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("rbNamaste Petra", msg);
    }

    @Override
    protected void setUp() throws Exception {
        setApplicationSCDL(getClass().getResource("/META-INF/sca/properties.composite"));
        super.setUp();
        this.compositeContext = CurrentCompositeContext.getContext();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
