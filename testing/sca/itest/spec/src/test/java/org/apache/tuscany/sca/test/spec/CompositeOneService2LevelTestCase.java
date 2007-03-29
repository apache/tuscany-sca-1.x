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
package org.apache.tuscany.sca.test.spec;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.tuscany.api.SCARuntime;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;
import org.osoa.sca.RequestContext;

@SuppressWarnings("deprecation")
public class CompositeOneService2LevelTestCase extends TestCase {
    private static MyService myService;
    private static MyService myServiceDefault;
    private static MyService myServiceNo;
    private static MyService myServiceMay;
    private static MyService myServiceMust;

    static private CompositeContext context;

    public static Test suite() {

        TestSuite suite = new TestSuite();

        suite.addTestSuite(CompositeOneService2LevelTestCase.class);
        TestSetup wrapper = new TestSetup(suite) {
            public void setUp() {
                setupStatic();
            }

            public void tearDown() {
                tearDownStatic();
            }

        };
        return wrapper;
    }

    public static void setupStatic() {
        if (null != context)
            return;
        SCARuntime.start("CompositeTest.composite");
        context = CurrentCompositeContext.getContext();
        assertNotNull(context);
        myService = context.locateService(MyService.class, "MySimpleServiceInRecursiveComponent/MySimpleService");
        myServiceDefault = context.locateService(MyService.class, "MySimpleServiceDefault");
        myServiceNo = context.locateService(MyService.class, "MySimpleServiceNo");
        myServiceMay = context.locateService(MyService.class, "MySimpleServiceMay");
        myServiceMust = context.locateService(MyService.class, "MySimpleServiceMust");

    }

    public static void tearDownStatic() {
        if (context != null) {
          
            context = null;
            SCARuntime.stop();
        }
    }

    public void setUp() {
        setupStatic();
    }

    static Object foo = new Object() {
        public void finalize() throws Throwable {
            foo = null;
            tearDownStatic();
        }

    };

    public void testPropertyFromComponent() {

        assertNotNull(myService);
        assertEquals("CARY", myService.getLocation());
        assertEquals("2007", myService.getYear());

    }

    public void testPropertyFromServiceDefault() {

        assertNotNull(myServiceDefault);
        assertEquals("CARY", myServiceDefault.getLocation());
        assertEquals("2007", myServiceDefault.getYear());

    }

    public void testServiceDefault() {

        assertNotNull(myService);
        assertEquals(myService.nextHoliday(), myServiceDefault.nextHoliday());
    }

    public void testPropertyFromServiceNo() {

        assertNotNull(myServiceNo);
        assertEquals("CARY", myServiceNo.getLocation());
        assertEquals("2007", myServiceNo.getYear());

    }

    public void testServiceNo() {

        assertNotNull(myService);
        assertEquals(myService.nextHoliday(), myServiceNo.nextHoliday());
    }

    public void testPropertyFromServiceMay() {

        assertNotNull(myServiceMay);
        assertEquals("CARY", myServiceMay.getLocation());
        assertEquals("2007", myServiceMay.getYear());

    }

    public void testServiceMay() {

        assertNotNull(myService);
        assertEquals(myService.nextHoliday(), myServiceMay.nextHoliday());
    }

    public void testPropertyFromServiceMust() {

        assertNotNull(myServiceMust);
        assertEquals("CARY", myServiceMust.getLocation());
        assertEquals("2007", myServiceMust.getYear());

    }

    public void testServiceMust() {

        assertNotNull(myService);
        assertEquals(myService.nextHoliday(), myServiceMust.nextHoliday());
    }

    public void testContext() {
        // FIXME TUSCANY-1174 - Need support for @ComponentName
        /*
         * assertNotNull("Service component name is null",
         * myService.getComponentName()); assertNotNull("service context is
         * null", myService.getContext()); System.out.println("Service component
         * name :" + myService.getComponentName()); System.out.println("service
         * context :" + myService.getContext()); test(context);
         */
    }

    private void test(CompositeContext context) {

        assertNotNull("composite name is null", context.getName());
        assertNotNull("composite URI is null", context.getURI());

        System.out.println("composite name :" + context.getName());
        System.out.println("composite URI:" + context.getURI());

        if (context.getRequestContext() == null)
            System.out.println("Request context:" + context.getRequestContext());
        else
            display(context.getRequestContext());
    }

    private void display(RequestContext context) {
        System.out.println("\tService name:" + context.getServiceName());
        System.out.println("\tSecurity subject:" + context.getSecuritySubject());
        // System.out.println("\tService reference:" +
        // (Object)context.getServiceReference());

    }
 

}
