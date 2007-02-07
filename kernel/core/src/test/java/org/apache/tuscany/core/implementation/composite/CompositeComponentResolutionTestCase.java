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
package org.apache.tuscany.core.implementation.composite;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.wire.InboundWire;

import junit.framework.TestCase;
import org.apache.tuscany.core.implementation.TestUtils;
import org.apache.tuscany.core.mock.component.Source;
import org.easymock.EasyMock;

/**
 * Verifies an atomic component can be resolved from its parent
 *
 * @version $$Rev$$ $$Date$$
 */
public class CompositeComponentResolutionTestCase extends TestCase {

    public void testSystemComponentResolution() throws Exception {
        CompositeComponent parent = new CompositeComponentImpl(URI.create("foo"), null, null);
        parent.start();
        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        interfaces.add(Source.class);
        AtomicComponent component = EasyMock.createMock(AtomicComponent.class);
        EasyMock.expect(component.getUri()).andReturn(URI.create("source")).atLeastOnce();
        List<InboundWire> wires = TestUtils.createInboundWires(interfaces);
        TestUtils.populateInboundWires(component, wires);
        EasyMock.expect(component.getInboundWires()).andReturn(wires).atLeastOnce();
        EasyMock.replay(component);
        parent.register(component);
        assertTrue(parent.getChild("source") instanceof AtomicComponent);
        EasyMock.verify(component);
    }

    public void testComponentResolution() throws Exception {
        CompositeComponent parent = new CompositeComponentImpl(URI.create("foo"), null, null, null);
        parent.start();
        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        interfaces.add(Source.class);
        AtomicComponent component = EasyMock.createMock(AtomicComponent.class);
        EasyMock.expect(component.getUri()).andReturn(URI.create("source")).atLeastOnce();
        List<InboundWire> wires = TestUtils.createInboundWires(interfaces);
        TestUtils.populateInboundWires(component, wires);
        EasyMock.expect(component.getInboundWires()).andReturn(wires).atLeastOnce();
        EasyMock.replay(component);
        parent.register(component);
        assertTrue(parent.getChild("source") instanceof AtomicComponent);
        EasyMock.verify(component);
    }


    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
