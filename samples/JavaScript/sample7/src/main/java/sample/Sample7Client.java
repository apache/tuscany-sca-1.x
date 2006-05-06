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
package sample;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.apache.tuscany.core.config.ConfigurationException;
import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;

/**
 * This client program shows how to create an SCA runtime, start it, locate a simple HelloWorld service component and invoke it.
 */
public class Sample7Client {

    public static final void main(String[] args) throws Exception {

        Sample7Client.invoke("world");

    }

    public static String invoke(String in) throws ConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        // Obtain Tuscany runtime
        TuscanyRuntime tuscany = new TuscanyRuntime("sample7", null);

        // Start the runtime
        tuscany.start();

        // Obtain SCA module context.
        ModuleContext moduleContext = CurrentModuleContext.getContext();

        // Locate the HelloWorld service component and invoke it
// TODO: TUSCANY-312
//        HelloWorld helloworldService = (HelloWorld) moduleContext.locateService("HelloWorldComponent");
//        String value = helloworldService.getGreetings(in);
        Object proxy = moduleContext.locateService("HelloWorldComponent");
        Method m = proxy.getClass().getDeclaredMethod("getGreetings", new Class[] {String.class});
        String value = (String) m.invoke(proxy, in);

        System.out.println(value);

        // Stop the runtime
        tuscany.stop();

        return value;
    }
}
