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
package org.apache.tuscany.osgi.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.osgi.framework.Bundle;

public class ClassloaderHook {

    /**
     * Return the BundleContext Classloader for the specified bundle.
     *
     * @param bundle The bundle whose BundleContext is desired.
     * @return The BundleContext classloader for the specified bundle.
     */
    ClassLoader getClassLoader(final Bundle bundle) {
        if (System.getSecurityManager() == null) {
            Object bundleLoader = invokeMethod(bundle, "checkLoader", null, null);
            return (ClassLoader) invokeMethod(bundleLoader, "createClassLoader", null, null);
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                Object bundleLoader = invokeMethod(bundle, "checkLoader", null, null);
                return (ClassLoader) invokeMethod(bundleLoader, "createClassLoader", null, null);
            }
        });
    }

    /**
     * Throws an IllegalStateException if the reflection logic cannot find what it is looking for. This probably means
     * this class does not properly recognize the framework implementation.
     *
     * @param e Exception which indicates the reflection logic is confused.
     */
    protected void reflectionException(Exception e) {
        throw new IllegalStateException(
            "ClassLoaderHook does not recognize the framework implementation: " + e.getMessage());
    }

    private Object invokeMethod(Object target, String methodName, Class[] parms, Object[] args) {
        Method method;
        try {
            method = target.getClass().getMethod(methodName, parms);
            if (method != null) {
                return method.invoke(target, parms, args);
            }
        } catch (SecurityException e) {
            reflectionException(e);
        } catch (NoSuchMethodException e) {
            reflectionException(e);
        } catch (IllegalArgumentException e) {
            reflectionException(e);
        } catch (IllegalAccessException e) {
            reflectionException(e);
        } catch (InvocationTargetException e) {
            reflectionException(e);
        }
        return null;
    }


}
