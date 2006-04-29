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
package interop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.apache.tuscany.core.config.ConfigurationException;
import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;

/**
 * This client program shows how to create an SCA runtime, start it, locate a simple HelloWorld service component and invoke it.
 */
public class ClientTestCase extends TestCase {

    public void testGetQuote() throws ConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {

        TuscanyRuntime tuscany = new TuscanyRuntime("getQuote", null);
        tuscany.start();
        ModuleContext moduleContext = CurrentModuleContext.getContext();

        Object serviceProxy = moduleContext.locateService("webserviceXSQ");
        Method m = serviceProxy.getClass().getMethod("getQuote", new Class[] { Object.class }); // TODO: yesterday this worked with arg type String.class

        String sqResponse = (String) m.invoke(serviceProxy, "IBM");

        assertTrue(sqResponse.startsWith("<bla, fix when service is going>"));

        tuscany.stop();
    }
}
