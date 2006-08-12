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
package org.apache.tuscany.core.component.scope;

import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.WorkContext;

import org.apache.tuscany.core.component.WorkContextImpl;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @version $Rev$ $Date$
 */
public class WorkContextTestCase extends MockObjectTestCase {

    public void testRemoteComponent() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        Mock mock = mock(CompositeComponent.class);
        CompositeComponent component = (CompositeComponent) mock.proxy();
        Mock mock2 = mock(CompositeComponent.class);
        CompositeComponent component2 = (CompositeComponent) mock2.proxy();
        ctx.setRemoteComponent(component);
        assertEquals(component, ctx.getRemoteComponent());
        ctx.setRemoteComponent(component2);
        assertEquals(component2, ctx.getRemoteComponent());
    }

    public void testNonSetRemoteComponent() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        assertNull(ctx.getRemoteComponent());
    }

    public void testIndentifier() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        Object id = new Object();
        ctx.setIdentifier(this, id);
        assertEquals(id, ctx.getIdentifier(this));
    }

    public void testClearIndentifier() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        Object id = new Object();
        ctx.setIdentifier(this, id);
        ctx.clearIdentifier(this);
        assertNull(ctx.getIdentifier(this));
    }

    public void testClearIndentifiers() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        Object id = new Object();
        Object id2 = new Object();
        ctx.setIdentifier(id, id);
        ctx.setIdentifier(id2, id2);
        ctx.clearIdentifiers();
        assertNull(ctx.getIdentifier(id));
        assertNull(ctx.getIdentifier(id2));
    }

    public void testClearNonExistentIndentifier() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        ctx.clearIdentifier(this);
    }

    public void testNullIndentifier() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        Object id = new Object();
        ctx.setIdentifier(this, id);
        ctx.clearIdentifier(null);
        assertEquals(id, ctx.getIdentifier(this));
    }

    public void testNoIndentifier() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        assertNull(ctx.getIdentifier(this));
    }


}
