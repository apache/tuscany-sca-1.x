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
import org.apache.tuscany.spi.component.TargetDestructionException;
import org.apache.tuscany.spi.component.TargetInitializationException;

/**
 * Default implementation of an <code>InstanceWrapper</code>
 *
 * @version $$Rev$$ $$Date$$
 */
public class InstanceWrapperImpl extends InstanceWrapperBase<Object> {
    private AtomicComponent component;

    public InstanceWrapperImpl(AtomicComponent component, Object instance) {
        super(instance);
        assert component != null;
        this.component = component;
    }

    public void start() throws TargetInitializationException {
        component.init(instance);
        super.start();
    }

    public void stop() throws TargetDestructionException {
        super.stop();
        component.destroy(instance);
    }
}
