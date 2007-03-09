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
import junit.framework.TestCase;

import org.apache.tuscany.api.SCARuntime;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Test case for using references in script components
 */
public class PythonPropertiesTestCase extends TestCase {

     private CompositeContext compositeContext;

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

    @Override
    protected void setUp() throws Exception {
        SCARuntime.start("META-INF/sca/properties.composite");
        this.compositeContext = CurrentCompositeContext.getContext();
    }

    @Override
    protected void tearDown() throws Exception {
        SCARuntime.stop();
    }

}
