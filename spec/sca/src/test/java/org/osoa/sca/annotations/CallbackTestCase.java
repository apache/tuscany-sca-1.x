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
package org.osoa.sca.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;
import org.osoa.sca.annotations.usage.CallbackType;

/**
 * @version $Rev$ $Date$
 */
public class CallbackTestCase extends TestCase {
    private Class<?> type;
    private Field field;
    private Method method;

    public void testTypeDeclaration() {
        assertTrue(type.isAnnotationPresent(Callback.class));
        Callback callback = type.getAnnotation(Callback.class);
        assertEquals(Object.class, callback.value());
    }

    public void testField() {
        assertTrue(field.isAnnotationPresent(Callback.class));
        Callback callback = field.getAnnotation(Callback.class);
        assertEquals(Void.class, callback.value());
    }

    public void testMethod() {
        assertTrue(method.isAnnotationPresent(Callback.class));
        Callback callback = method.getAnnotation(Callback.class);
        assertEquals(Void.class, callback.value());
    }

    protected void setUp() throws Exception {
        super.setUp();
        type = CallbackType.class;
        field = type.getDeclaredField("cbField");
        method = type.getMethod("cbMethod");
    }
}