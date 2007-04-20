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

package org.apache.tuscany.implementation.script;

import java.net.URI;

import org.apache.tuscany.assembly.Component;
import org.apache.tuscany.databinding.DataBindingExtensionPoint;
import org.apache.tuscany.spi.builder.BuilderConfigException;
import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.ComponentBuilderExtension;

public class ScriptComponentBuilder extends ComponentBuilderExtension<ScriptImplementation> {
    private ScriptPropertyValueObjectFactory propertyValueObjectFactory = null;
    private DataBindingExtensionPoint dataBindingRegistry;
    
    public ScriptComponentBuilder() {
    }

    public AtomicComponent build(Component assemblyComponent, DeploymentContext context) throws BuilderConfigException {
        URI id = URI.create(context.getComponentId() + assemblyComponent.getName());
        ScriptImplementation scriptImplementation = (ScriptImplementation)assemblyComponent.getImplementation();
        ScriptComponent scriptComponent = new ScriptComponent(id, context.getGroupId(), scriptImplementation);
        scriptComponent.setPropertyValueObjectFactory(propertyValueObjectFactory);
        scriptComponent.setDataBindingRegistry(dataBindingRegistry);
        
        scriptComponent.initializePropertyValueFactories(assemblyComponent.getProperties());
        
        return scriptComponent;
    }

    @Override
    protected Class<ScriptImplementation> getImplementationType() {
        return ScriptImplementation.class;
    }

    public void setPropertyValueObjectFactory(ScriptPropertyValueObjectFactory propertyValueObjectFactory) {
        this.propertyValueObjectFactory = propertyValueObjectFactory;
    }

    public void setDataBindingRegistry(DataBindingExtensionPoint dataBindingRegistry) {
        this.dataBindingRegistry = dataBindingRegistry;
    }

}
