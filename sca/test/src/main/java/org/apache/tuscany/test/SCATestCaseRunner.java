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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * A helper class that can be used to run an SCA JUnit test case. The test case
 * will run in an isolated class loader.
 * 
 * @version $Rev$ $Date$
 */
public class SCATestCaseRunner {

    private ClassLoader classLoader;
    private Class testSuiteClass;
    private Object testSuite;
    private Class testResultClass;
    private Class testCaseClass;
    private Object testCase;

    /**
     * Constructs a new TestCase runner.
     * 
     * @param testClass
     */
    public SCATestCaseRunner(Class testClass) {
        try {
            classLoader = (URLClassLoader)testClass.getClassLoader();
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader)classLoader).getURLs();
                classLoader = new URLClassLoader(urls, classLoader.getParent());
            } else {
                classLoader = new URLClassLoader(new URL[0], classLoader);
            }

            ClassLoader tccl = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(classLoader);

                testCaseClass = Class.forName(testClass.getName(), true, classLoader);
                testCase = testCaseClass.newInstance();

                testSuiteClass = Class.forName(TestSuite.class.getName(), true, classLoader);
                Constructor testSuiteConstructor = testSuiteClass.getConstructor(Class.class);
                testSuite = testSuiteConstructor.newInstance(testCaseClass);

                testResultClass = Class.forName(TestResult.class.getName(), true, classLoader);

            } finally {
                Thread.currentThread().setContextClassLoader(tccl);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a new TestCase runner passing in the composite name.
     * 
     * @param testClass
     */
    public SCATestCaseRunner(Class testClass, String compositeName) {
        try {
            classLoader = (URLClassLoader)testClass.getClassLoader();
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader)classLoader).getURLs();
                classLoader = new URLClassLoader(urls, classLoader.getParent());
            } else {
                classLoader = new URLClassLoader(new URL[0], classLoader);
            }

            ClassLoader tccl = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(classLoader);

                testCaseClass = Class.forName(testClass.getName(), true, classLoader);
                Constructor constructor = testCaseClass.getConstructor(new Class[] {String.class});
                testCase = constructor.newInstance(new Object[] {compositeName});

                testSuiteClass = Class.forName(TestSuite.class.getName(), true, classLoader);
                Constructor testSuiteConstructor = testSuiteClass.getConstructor(Class.class);
                testSuite = testSuiteConstructor.newInstance(testCaseClass);

                testResultClass = Class.forName(TestResult.class.getName(), true, classLoader);

            } finally {
                Thread.currentThread().setContextClassLoader(tccl);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run the test case
     */
    public void run() {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object testResult = testResultClass.newInstance();
            Method runMethod = testSuiteClass.getMethod("run", testResultClass);
            runMethod.invoke(testSuite, testResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }

    /**
     * Invoke the setUp method
     */
    public void setUp() {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Method setUpMethod = testCaseClass.getDeclaredMethod("setUp");
            setUpMethod.setAccessible(true);
            setUpMethod.invoke(testCase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }

    /**
     * Invoke the tearDown method
     */
    public void tearDown() {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Method tearDownMethod = testCaseClass.getDeclaredMethod("tearDown");
            tearDownMethod.setAccessible(true);
            tearDownMethod.invoke(testCase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }

    /**
     * Invoke the specified test method.
     */
    public void run(String methodName) {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Method testMethod = testCaseClass.getMethod(methodName);
            testMethod.invoke(testCase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }

}
