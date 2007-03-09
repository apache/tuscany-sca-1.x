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
package org.apache.tuscany.spi.extension;

import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.builder.BuilderRegistry;
import org.apache.tuscany.spi.builder.GenericBuilder;
import org.apache.tuscany.spi.component.SCAObject;
import org.apache.tuscany.spi.model.ModelObject;
import org.osoa.sca.annotations.EagerInit;
import org.osoa.sca.annotations.Init;

/**
 * An extension point for generic builders which can deal with SCDL
 * extensibility elements
 * 
 * @version $$Rev$$ $$Date$$
 */
@EagerInit
public abstract class GenericBuilderExtension<S extends SCAObject, M extends ModelObject> implements
    GenericBuilder<S, M> {

    protected BuilderRegistry builderRegistry;

    @Autowire
    public void setBuilderRegistry(BuilderRegistry registry) {
        this.builderRegistry = registry;
    }

    @Init
    public void init() {
        builderRegistry.register(getModelType(), this);
    }

    protected abstract Class<M> getModelType();
}
