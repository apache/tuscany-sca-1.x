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
package org.apache.tuscany.core.implementation.java;

import java.net.URL;
import java.util.Iterator;

import org.apache.tuscany.core.util.JavaIntrospectionHelper;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.ComponentTypeLoaderExtension;
import org.apache.tuscany.spi.implementation.java.IntrospectionRegistry;
import org.apache.tuscany.spi.implementation.java.Introspector;
import org.apache.tuscany.spi.implementation.java.JavaMappedProperty;
import org.apache.tuscany.spi.implementation.java.JavaMappedReference;
import org.apache.tuscany.spi.implementation.java.JavaMappedService;
import org.apache.tuscany.spi.implementation.java.PojoComponentType;
import org.apache.tuscany.spi.implementation.java.ProcessingException;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.model.ServiceContract;
import org.apache.tuscany.spi.model.ServiceDefinition;
import org.osoa.sca.annotations.Constructor;

/**
 * @version $Rev$ $Date$
 */
public class JavaComponentTypeLoader extends ComponentTypeLoaderExtension<JavaImplementation> {
    private Introspector introspector;

    @Constructor({"registry", "introspector"})
    public JavaComponentTypeLoader(@Autowire LoaderRegistry loaderRegistry,
                                   @Autowire IntrospectionRegistry introspector) {
        super(loaderRegistry);
        this.introspector = introspector;
    }

    @Override
    protected Class<JavaImplementation> getImplementationClass() {
        return JavaImplementation.class;
    }

    public void load(CompositeComponent parent,
                     JavaImplementation implementation,
                     DeploymentContext deploymentContext) throws LoaderException {
        Class<?> implClass = implementation.getImplementationClass();
        PojoComponentType componentType = loadByIntrospection(parent, implementation, deploymentContext);
        URL resource = implClass.getResource(JavaIntrospectionHelper.getBaseName(implClass) + ".componentType");
        if (resource != null) {
            // TODO: TUSCANY-1111, How to merge the component type loaded from the file into the PojoComponentType 
            PojoComponentType sideFileCT = loadFromSidefile(parent, resource, deploymentContext);

            // TODO: TUSCANY-1111, hack to get the sidefile defined WSDLServiceContract used
            // only works with a single service
            Iterator it = componentType.getServices().values().iterator();
            for (Object o : sideFileCT.getServices().values()) {
                ServiceDefinition sideFileSD = (ServiceDefinition) o;
                ServiceDefinition actualSD = (ServiceDefinition)it.next();
                ServiceContract<?> newServiceContract = sideFileSD.getServiceContract();
                // TODO: TUSCANY-1111, runtime requires interfaceClass but currently there's no way of gen'ing that from WSDL
                newServiceContract.setInterfaceClass(actualSD.getServiceContract().getInterfaceClass());
                newServiceContract.setDataBinding(actualSD.getServiceContract().getDataBinding());
                actualSD.setServiceContract(newServiceContract);
            }
        }
        implementation.setComponentType(componentType);
    }

    protected PojoComponentType loadByIntrospection(CompositeComponent parent,
                                                    JavaImplementation implementation,
                                                    DeploymentContext deploymentContext) throws ProcessingException {
        PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>> componentType =
            new PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>>();
        Class<?> implClass = implementation.getImplementationClass();
        introspector.introspect(parent, implClass, componentType, deploymentContext);
        return componentType;
    }

    protected PojoComponentType loadFromSidefile(CompositeComponent parent,
                                                 URL url,
                                                 DeploymentContext deploymentContext) throws LoaderException {
        PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>> componentType =
            new PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>>();
        return loaderRegistry.load(parent, componentType, url, PojoComponentType.class, deploymentContext);
    }
}
