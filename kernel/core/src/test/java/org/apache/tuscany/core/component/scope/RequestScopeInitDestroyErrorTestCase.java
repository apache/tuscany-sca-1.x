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

import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.component.ScopeContainerMonitor;
import org.apache.tuscany.spi.component.TargetDestructionException;
import org.apache.tuscany.spi.component.InstanceWrapper;
import org.apache.tuscany.spi.event.RuntimeEventListener;

import junit.framework.TestCase;
import org.apache.tuscany.core.component.WorkContextImpl;
import org.apache.tuscany.core.component.event.RequestEnd;
import org.apache.tuscany.core.component.event.RequestStart;
import org.easymock.EasyMock;

/**
 * @version $Rev$ $Date$
 */
public class RequestScopeInitDestroyErrorTestCase extends TestCase {

    public void testDestroyErrorMonitor() throws Exception {
        ScopeContainerMonitor monitor;
        monitor = EasyMock.createMock(ScopeContainerMonitor.class);
        monitor.destructionError(EasyMock.isA(TargetDestructionException.class));
        EasyMock.replay(monitor);

        InstanceWrapper wrapper = EasyMock.createMock(InstanceWrapper.class);
        wrapper.start();
        EasyMock.expect(wrapper.isStarted()).andReturn(true);
        EasyMock.expect(wrapper.getInstance()).andReturn(null);
        wrapper.stop();
        EasyMock.expectLastCall().andThrow(new TargetDestructionException("", ""));
        EasyMock.replay(wrapper);

        RequestScopeContainer scope = new RequestScopeContainer(new WorkContextImpl(), monitor);
        scope.start();

        AtomicComponent component = EasyMock.createMock(AtomicComponent.class);
        EasyMock.expect(component.createInstanceWrapper()).andReturn(wrapper);
        EasyMock.replay(component);

        scope.register(component);
        scope.onEvent(new RequestStart(this));
        scope.getInstance(component);
        scope.onEvent(new RequestEnd(this));
        EasyMock.verify(monitor);
        EasyMock.verify(component);
    }
}
