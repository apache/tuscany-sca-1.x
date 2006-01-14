/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.samples.helloworldweb;

import junit.framework.TestCase;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;

/**
 * 
 */
public class HelloWorldServiceComponentTestCase extends TestCase {
    public boolean verbose = false;

    public void testGeetings() throws Exception {
        // todo should use SCA ;-)
        final boolean viaSCA = false;
        TuscanyRuntime tuscany;
        ModuleContext moduleContext = null;
        if (verbose)
            System.out.println("starting test..");
        System.out.flush();
        HelloWorldServiceComponent helloworldService = null;

        if (viaSCA) {
            tuscany = new TuscanyRuntime("test", null);
            tuscany.start();
            moduleContext = CurrentModuleContext.getContext();

            assertNotNull(moduleContext);
            System.out.println("module context name '"
                    + moduleContext.getName() + "'");
            System.out.println("module context uri '" + moduleContext.getURI()
                    + "'");
            helloworldService = (HelloWorldServiceComponent) moduleContext
                    .locateService("HelloWorldServiceComponent");

            assertNotNull(helloworldService);
        } else
            helloworldService = new HelloWorldServiceComponentImpl();

        String value = helloworldService
                .getGreetings("World");

        if (verbose)
            System.out.println("Value = '" + value + "'");
        assertEquals("Hello World", value);
        if (viaSCA)
            tuscany.stop();

    }

    public final static void main(String[] args) throws Exception {
        HelloWorldServiceComponentTestCase hwc = new HelloWorldServiceComponentTestCase();
        hwc.setVerbose(true);
        hwc.testGeetings();

    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}
