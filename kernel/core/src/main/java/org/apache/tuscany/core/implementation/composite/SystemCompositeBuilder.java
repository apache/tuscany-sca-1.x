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

import org.apache.tuscany.spi.builder.BuilderException;
import org.apache.tuscany.spi.builder.BuilderRegistry;
import org.apache.tuscany.spi.builder.Connector;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.services.management.TuscanyManagementService;

import org.apache.tuscany.core.implementation.system.model.SystemCompositeImplementation;

/**
 * Produces system composite components by evaluating an assembly.
 *
 * @version $Rev$ $Date$
 */
public class SystemCompositeBuilder extends AbstractCompositeBuilder<SystemCompositeImplementation> {
    private TuscanyManagementService managementService;

    public SystemCompositeBuilder() {
    }

    public SystemCompositeBuilder(BuilderRegistry builderRegistry,
                                  Connector connector,
                                  TuscanyManagementService managementService) {
        this.builderRegistry = builderRegistry;
        this.connector = connector;
        this.managementService = managementService;
    }

    public CompositeComponent build(CompositeComponent parent,
                                    ComponentDefinition<SystemCompositeImplementation> componentDefinition,
                                    DeploymentContext deploymentContext) throws BuilderException {
        SystemCompositeImplementation impl = componentDefinition.getImplementation();
        CompositeComponentType<?, ?, ?> componentType = impl.getComponentType();
        String name = componentDefinition.getName();
        CompositeComponent component = new CompositeComponentImpl(name, parent, connector, true);
        component.setManagementService(managementService);
        build(parent, component, componentType, deploymentContext);
        return component;
    }

    protected Class<SystemCompositeImplementation> getImplementationType() {
        return SystemCompositeImplementation.class;
    }

}
