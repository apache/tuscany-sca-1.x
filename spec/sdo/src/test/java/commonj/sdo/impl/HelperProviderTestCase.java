/**
 *
 * Copyright 2005 International Business Machines Corporation
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
package commonj.sdo.impl;

import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;
import test.DefaultHelperProvider;
import test.TCCL1HelperProvider;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings({"ClassLoader2Instantiation"})
public class HelperProviderTestCase extends TestCase {
    private URL classes;
    private URL testClasses;
    private URL tccl1;

    public void testInstanceIsNullWithNoImplementation() throws Exception {
        ClassLoader cl = new URLClassLoader(new URL[]{classes}, null);
        Class<?> providerClass = cl.loadClass("commonj.sdo.impl.HelperProvider");
        assertNull(providerClass.getField("INSTANCE").get(null));
    }

    public void testDefaultInstance() {
        assertNotNull(HelperProvider.INSTANCE);
        assertEquals(DefaultHelperProvider.class, HelperProvider.INSTANCE.getClass());
    }

    public void testLocateFromClassLoader() throws Exception {
        ClassLoader cl = new URLClassLoader(new URL[]{classes, tccl1, testClasses}, null);
        Class<?> providerClass = cl.loadClass(HelperProvider.class.getName());
        Object provider = providerClass.getMethod("getInstance", ClassLoader.class).invoke(null, cl);
        assertNotNull(provider);
        assertEquals(TCCL1HelperProvider.class.getName(), provider.getClass().getName());
    }

    public void testThreadContextInstance() throws Exception {
        ClassLoader cl = new URLClassLoader(new URL[]{classes, tccl1, testClasses}, null);
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(cl);
            Class<?> providerClass = cl.loadClass(HelperProvider.class.getName());
            Object provider = providerClass.getMethod("getInstance").invoke(null);
            assertNotNull(provider);
            assertEquals(TCCL1HelperProvider.class.getName(), provider.getClass().getName());
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }

    }

    @SuppressWarnings({"AccessOfSystemProperties"})
    public void testSystemProperty() {
        System.setProperty("commonj.sdo.impl.HelperProvider", "test.TCCL1HelperProvider");
        try {
            HelperProvider provider = HelperProvider.getInstance();
            assertNotNull(provider);
            assertEquals(TCCL1HelperProvider.class, provider.getClass());
        } finally {
            System.getProperties().remove("commonj.sdo.impl.HelperProvider");
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
        classes = new URL(HelperProvider.class.getResource("HelperProvider.class"), "../../..");
        testClasses = new URL(HelperProviderTestCase.class.getResource("HelperProviderTestCase.class"), "../../..");
        tccl1 = new URL(testClasses, "tccl1/");
    }
}
