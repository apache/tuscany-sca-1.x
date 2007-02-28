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
package org.apache.tuscany.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.tuscany.api.SCAContainer;
import org.apache.tuscany.core.bootstrap.DefaultSCAContainer;
import org.apache.tuscany.spi.component.CompositeComponent;

/**
 * Base class for JUnit tests that want to run in an SCA environment.
 * 
 * @version $Rev$ $Date$
 */
public abstract class SCATestCase extends TestCase {
    private Map<String, URL> extensions = new HashMap<String, URL>();
    private URL applicationSCDL;
    private URL systemSCDL;
    private String compositePath = "META-INF/sca/application.composite";
    
    protected CompositeComponent component;

    protected void setUp() throws Exception {
        super.setUp();
        
        SCAContainer.start(systemSCDL, extensions == null || extensions.isEmpty() ? null :
                               extensions.values().toArray(new URL[0]), applicationSCDL, compositePath);

        // FIXME: How to expose the composite component?
        this.component = ((DefaultSCAContainer)SCAContainer.getInstance()).getCompsiteComponent();
    }

    /**
     * A TestCase can use this to override the default SCDL location
     */
    protected void setApplicationSCDL(URL applicationSCDL) {
        this.applicationSCDL = applicationSCDL;
    }

    /**
     * A TestCase can use this to override the default SCDL location
     */
    protected void setSystemSCDL(URL systemSCDL) {
        this.systemSCDL = systemSCDL;
    }

    /**
     * A TestCase can use this to override the default SCDL location
     */
    protected void setApplicationSCDL(String applicationSCDL) {
        this.applicationSCDL = Thread.currentThread().getContextClassLoader().getResource(applicationSCDL);
        this.compositePath = applicationSCDL;
    }

    /**
     * A TestCase can use this to override the default SCDL location
     */
    protected void setSystemSCDL(String systemSCDL) {
        this.systemSCDL = Thread.currentThread().getContextClassLoader().getResource(systemSCDL);
    }

    /**
     * Set the application scdl based on the classpath entry for a class.
     * Normally this will be a class in the production code associated with this
     * test case.
     * 
     * @param aClass a Class from which to determine the resource base url
     * @param path location of the application SCDL relative to the base class
     * @throws MalformedURLException if the path is malformed
     */
    protected void setApplicationSCDL(Class<?> aClass, String path) throws MalformedURLException {
        URL root = getRoot(aClass);
        setApplicationSCDL(new URL(root, path));
    }

    /**
     * Set the system scdl based on the classpath entry for a class.
     * Normally this will be a class in the production code associated with this
     * test case.
     * 
     * @param aClass a Class from which to determine the resource base url
     * @param path location of the system SCDL relative to the base class
     * @throws MalformedURLException if the path is malformed
     */
    protected void setSystemSCDL(Class<?> aClass, String path) throws MalformedURLException {
        URL root = getRoot(aClass);
        setSystemSCDL(new URL(root, path));
    }

    /**
     * A TestCase can use this to add the SCDL location of an extention to be
     * deployed to the runtime
     */
    protected void addExtension(String extensionName, URL extentionSCDL) {
        extensions.put(extensionName, extentionSCDL);
    }

    protected static URL getRoot(Class<?> aClass) {
        String name = aClass.getName();
        String classPath = "/" + name.replace('.', '/') + ".class";
        URL classURL = aClass.getResource(classPath);
        assert classURL != null;
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '.') {
                prefix.append("../");
            }
        }
        try {
            return new URL(classURL, prefix.toString());
        } catch (MalformedURLException e) {
            throw new AssertionError();
        }
    }

    protected void tearDown() throws Exception {
        SCAContainer.stop();
        super.tearDown();
    }

}
