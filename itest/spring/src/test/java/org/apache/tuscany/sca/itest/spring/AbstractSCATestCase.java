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

package org.apache.tuscany.sca.itest.spring;

import junit.framework.TestCase;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.tuscany.sca.host.embedded.SCADomain;

public abstract class AbstractSCATestCase<T> extends TestCase {

    protected SCADomain domain;
    protected String compositeName;
    protected String contributionLocation;
    protected T service;
    
    public AbstractSCATestCase(String compositeName, String contributionLocation) {
        super();
        this.compositeName = compositeName;
        this.contributionLocation = contributionLocation;
        try {
            if (contributionLocation != null) {
                File f = new File("target/classes/" + contributionLocation);
                this.contributionLocation = f.toURL().toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() throws Exception {
    	domain = SCADomain.newInstance("http://localhost", contributionLocation, compositeName);
        service = domain.getService(getServiceClass(), "ClientComponent");
    }

    abstract protected Class<T> getServiceClass();

    @Override
    protected void tearDown() throws Exception {
        domain.close();
    }

    protected String getCompositeName() {
        String className = this.getClass().getName();
        String compositeName = className.substring(0, className.length() - 8).replace('.', '/') + ".composite";
        System.out.println("Using composite: " + compositeName);
        return compositeName;
    }

}
