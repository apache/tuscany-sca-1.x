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
package org.apache.tuscany.container.script;

import org.apache.tuscany.spi.builder.BuilderConfigException;
import org.apache.tuscany.spi.component.Component;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.ScopeContainer;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.ComponentBuilderExtension;
import org.apache.tuscany.spi.idl.java.JavaServiceContract;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.PropertyValue;
import org.apache.tuscany.spi.model.Scope;
import org.apache.tuscany.spi.model.ServiceDefinition;

/**
 * Extension point for creating {@link ScriptComponent}s from an assembly configuration
 *
 * @version $Rev$ $Date$
 */
public class ScriptComponentBuilder extends ComponentBuilderExtension<ScriptImplementation> {

    public ScriptComponentBuilder() {
    }

    protected Class<ScriptImplementation> getImplementationType() {
        return ScriptImplementation.class;
    }

    public Component build(CompositeComponent parent, ComponentDefinition<ScriptImplementation> componentDefinition,
                           DeploymentContext deploymentContext) throws BuilderConfigException {

        ScriptImplementation implementation = componentDefinition.getImplementation();
        
        for (ServiceDefinition service : implementation.getComponentType().getServices().values()) {
            // if its not a Java interface assume WSDL and want XML databinding
            if (!(service.getServiceContract() instanceof JavaServiceContract)) {
                service.getServiceContract().setDataBinding("org.mozilla.javascript.xml.XMLObject");
            }
        }

        ScriptInstanceFactory instanceFactory = createInstanceFactory(componentDefinition, implementation);

        String name = componentDefinition.getName();
        Scope scope = getScope(deploymentContext, implementation.getComponentType());

        return new ScriptComponent(name, parent, wireService, workContext, workScheduler, null, 0, instanceFactory, scope);
    }

    private ScriptInstanceFactory createInstanceFactory(ComponentDefinition<ScriptImplementation> componentDefinition, ScriptImplementation implementation) {

        String className = implementation.getClassName();
        String scriptSource = implementation.getScriptSource();
        String scriptName = implementation.getScriptName();
        ClassLoader cl = implementation.getClassLoader();

        ScriptInstanceFactory instanceFactory = new ScriptInstanceFactory(scriptName, className, scriptSource, cl);

        // add the properties for the component
        for (PropertyValue propertyValue : componentDefinition.getPropertyValues().values()) {
            instanceFactory.addContextObjectFactory(propertyValue.getName(), propertyValue.getValueFactory());
        }

        return instanceFactory;
    }

    protected Scope getScope(DeploymentContext deploymentContext, ScriptComponentType componentType) {
        ScopeContainer scopeContainer;
        Scope scope = componentType.getImplementationScope();
        if (Scope.COMPOSITE == scope) {
            scopeContainer = deploymentContext.getCompositeScope();
        } else {
            scopeContainer = scopeRegistry.getScopeContainer(scope);
        }
        return scopeContainer.getScope();
    }

}
