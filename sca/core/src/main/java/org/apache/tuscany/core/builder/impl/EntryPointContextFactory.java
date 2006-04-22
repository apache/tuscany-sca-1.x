/**
 * 
 * Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.tuscany.core.builder.impl;

import org.apache.tuscany.core.builder.ContextCreationException;
import org.apache.tuscany.core.builder.ContextFactory;
import org.apache.tuscany.core.context.CompositeContext;
import org.apache.tuscany.core.context.EntryPointContext;
import org.apache.tuscany.core.context.impl.EntryPointContextImpl;
import org.apache.tuscany.core.wire.SourceWireFactory;
import org.apache.tuscany.core.wire.TargetWireFactory;
import org.apache.tuscany.core.message.MessageFactory;
import org.apache.tuscany.model.assembly.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Default factory for contexts that represent entry points.
 * 
 * @version $Rev$ $Date$
 */
public class EntryPointContextFactory implements ContextFactory<EntryPointContext> {

    private String name;

    private SourceWireFactory proxyFactory;

    private MessageFactory msgFactory;

    private List<SourceWireFactory> sourceProxyFactories;

    public EntryPointContextFactory(String name, MessageFactory msgFactory) {
        assert (name != null) : "Entry point name was null";
        assert (msgFactory != null) : "Message factory was null";
        this.name = name;
        this.msgFactory = msgFactory;
    }

    public EntryPointContext createContext() throws ContextCreationException {
        return new EntryPointContextImpl(name, proxyFactory, msgFactory);
    }

    public Scope getScope() {
        return Scope.MODULE;
    }

    public String getName() {
        return name;
    }

    public void prepare() {
    }

    public void addTargetProxyFactory(String serviceName, TargetWireFactory factory) {
        // no wires to an entry point from with a composite
    }

    public TargetWireFactory getTargetProxyFactory(String serviceName) {
        // no wires to an entry point from with a composite
        return null;
    }

    public Map<String, TargetWireFactory> getTargetProxyFactories() {
        // no wires to an entry point from with a composite
        return Collections.emptyMap();
    }

    public void addSourceProxyFactory(String refName, SourceWireFactory factory) {
        assert (refName != null) : "No reference name specified";
        assert (factory != null) : "Proxy factory was null";
        this.proxyFactory = factory;
    }

    public List<SourceWireFactory> getSourceProxyFactories() {
        if (sourceProxyFactories == null) {
            sourceProxyFactories = new ArrayList<SourceWireFactory>(1);
            sourceProxyFactories.add(proxyFactory);
        }
        return sourceProxyFactories;
    }
    
    public void prepare(CompositeContext parent) {
    }
}
