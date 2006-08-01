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
package org.apache.tuscany.container.javascript.function;

import helloworld.HelloWorldService;

import org.apache.tuscany.test.SCATestCase;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * This shows how to test the HelloWorld service component.
 */
public class HelloWorldTestCase extends SCATestCase {

    private HelloWorldService helloWorldService;

    private HelloWorldService introspectableService;

    protected void setUp() throws Exception {
        addExtension("JavaScriptContainer", getClass().getClassLoader().getResource("META-INF/sca/default.scdl"));
        setApplicationSCDL("org/apache/tuscany/container/javascript/function/helloworld.scdl");
        super.setUp();

        CompositeContext context = CurrentCompositeContext.getContext();
        helloWorldService = context.locateService(HelloWorldService.class, "HelloWorldComponent");
        introspectableService = context.locateService(HelloWorldService.class, "IntrospectableHelloWorldComponent");
    }

    public void testHelloWorld() throws Exception {
        assertEquals(helloWorldService.sayHello("petra"), "Hello petra");
    }

    public void testIntrospectedHelloWorld() throws Exception {
        assertEquals(introspectableService.sayHello("petra"), "Hello petra");
    }
}
