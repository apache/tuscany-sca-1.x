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
package org.apache.tuscany.core.component;

import java.net.URI;

import org.osoa.sca.CallableReference;
import org.osoa.sca.ComponentContext;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.RequestContext;
import org.osoa.sca.ServiceReference;
import org.osoa.sca.ServiceRuntimeException;

import org.apache.tuscany.api.TuscanyRuntimeException;
import org.apache.tuscany.spi.component.Component;

/**
 * 
 * @version $Rev$ $Date$
 */
public class ComponentContextImpl implements ComponentContext {
    private final CompositeContext context;
    private final Component component;

    public ComponentContextImpl(CompositeContext context, Component component) {
        this.component = component;
        this.context = context;
    }

    public String getURI() {
        try {
            return component.getName();
        } catch (TuscanyRuntimeException e) {
            throw new ServiceRuntimeException(e.getMessage(), e);
        }
    }

    public <B, R extends CallableReference<B>> R cast(B target) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    public <B> B getService(Class<B> businessInterface, String referenceName) {
        try {
            return context.locateService(businessInterface, component.getName() + "/" + referenceName);
        } catch (TuscanyRuntimeException e) {
            throw new ServiceRuntimeException(e.getMessage(), e);
        }
    }

    public <B> ServiceReference<B> getServiceReference(Class<B> businessInterface, String referenceName) {
        throw new UnsupportedOperationException();
    }

    public <B> B getProperty(Class<B> type, String propertyName) {
        throw new UnsupportedOperationException();
    }

    public <B> ServiceReference<B> createSelfReference(Class<B> businessInterface) {
        return null;
    }

    public <B> ServiceReference<B> createSelfReference(Class<B> businessInterface, String serviceName) {
        return null;
    }

    public RequestContext getRequestContext() {
        return context.getRequestContext();
    }
}
