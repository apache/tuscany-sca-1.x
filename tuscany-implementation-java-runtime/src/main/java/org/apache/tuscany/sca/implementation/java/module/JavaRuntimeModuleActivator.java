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

package org.apache.tuscany.sca.implementation.java.module;

import java.util.List;
import java.util.Map;

import org.apache.tuscany.sca.assembly.AssemblyFactory;
import org.apache.tuscany.sca.context.ComponentContextFactory;
import org.apache.tuscany.sca.context.ContextFactoryExtensionPoint;
import org.apache.tuscany.sca.context.RequestContextFactory;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.ModuleActivator;
import org.apache.tuscany.sca.core.invocation.CglibProxyFactory;
import org.apache.tuscany.sca.core.invocation.ProxyFactoryExtensionPoint;
import org.apache.tuscany.sca.databinding.DataBindingExtensionPoint;
import org.apache.tuscany.sca.databinding.DefaultDataBindingExtensionPoint;
import org.apache.tuscany.sca.databinding.DefaultTransformerExtensionPoint;
import org.apache.tuscany.sca.databinding.TransformerExtensionPoint;
import org.apache.tuscany.sca.databinding.impl.MediatorImpl;
import org.apache.tuscany.sca.implementation.java.DefaultJavaImplementationFactory;
import org.apache.tuscany.sca.implementation.java.JavaImplementationFactory;
import org.apache.tuscany.sca.implementation.java.injection.JavaPropertyValueObjectFactory;
import org.apache.tuscany.sca.implementation.java.introspect.JavaClassVisitor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.AllowsPassByReferenceProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.BaseJavaClassVisitor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ComponentNameProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ConstructorProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ContextProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ConversationIDProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ConversationProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.DestroyProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.EagerInitProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.HeuristicPojoProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.InitProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.PolicyProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.PropertyProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ReferenceProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ResourceProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ScopeProcessor;
import org.apache.tuscany.sca.implementation.java.introspect.impl.ServiceProcessor;
import org.apache.tuscany.sca.implementation.java.invocation.JavaCallbackRuntimeWireProcessor;
import org.apache.tuscany.sca.implementation.java.invocation.JavaImplementationProviderFactory;
import org.apache.tuscany.sca.interfacedef.InterfaceContractMapper;
import org.apache.tuscany.sca.interfacedef.java.JavaInterfaceFactory;
import org.apache.tuscany.sca.invocation.MessageFactory;
import org.apache.tuscany.sca.policy.PolicyFactory;
import org.apache.tuscany.sca.policy.util.PolicyHandlerDefinitionsLoader;
import org.apache.tuscany.sca.policy.util.PolicyHandlerTuple;
import org.apache.tuscany.sca.provider.DefaultProviderFactoryExtensionPoint;
import org.apache.tuscany.sca.provider.ProviderFactoryExtensionPoint;
import org.apache.tuscany.sca.runtime.DefaultWireProcessorExtensionPoint;
import org.apache.tuscany.sca.runtime.RuntimeWireProcessorExtensionPoint;

/**
 * @version $Rev: 641726 $ $Date: 2008-03-26 23:24:37 -0800 (Wed, 26 Mar 2008) $
 */
public class JavaRuntimeModuleActivator implements ModuleActivator {

    public JavaRuntimeModuleActivator() {
    }

    public void start(ExtensionPointRegistry registry) {

        ModelFactoryExtensionPoint factories = registry.getExtensionPoint(ModelFactoryExtensionPoint.class);
        AssemblyFactory assemblyFactory = factories.getFactory(AssemblyFactory.class);
        PolicyFactory policyFactory = factories.getFactory(PolicyFactory.class);

        MessageFactory messageFactory = factories.getFactory(MessageFactory.class);
        ProxyFactoryExtensionPoint proxyFactory = registry.getExtensionPoint(ProxyFactoryExtensionPoint.class);
        proxyFactory.setClassProxyFactory(new CglibProxyFactory(messageFactory, proxyFactory
            .getInterfaceContractMapper()));

        JavaInterfaceFactory javaFactory = factories.getFactory(JavaInterfaceFactory.class);
        JavaImplementationFactory javaImplementationFactory = factories.getFactory(JavaImplementationFactory.class);
        
        if (javaImplementationFactory == null) {
        	javaImplementationFactory = new DefaultJavaImplementationFactory();
        	factories.addFactory(javaImplementationFactory);
        	
        }
        
        BaseJavaClassVisitor[] extensions =
            new BaseJavaClassVisitor[] {new ConstructorProcessor(assemblyFactory),
        		
                                        // should not be loaded when running on Android platform, cause 
        								// this class uses the Class.isAnnotationPresent method, which is 
        								// not supported yet by the Android platform
        								new AllowsPassByReferenceProcessor(assemblyFactory),
                                        
                                        new ComponentNameProcessor(assemblyFactory),
                                        new ContextProcessor(assemblyFactory),
                                        new ConversationIDProcessor(assemblyFactory),
                                        
                                        // should not be loaded when running on Android platform, cause 
        								// this class uses the Class.getAnnotation method, which is 
        								// not supported yet by the Android platform
        								new ConversationProcessor(assemblyFactory),
                                        
                                        new DestroyProcessor(assemblyFactory),
                                        
                                        // should not be loaded when running on Android platform, cause 
        								// this class uses the Class.getAnnotation method, which is 
        								// not supported yet by the Android platform
                                        new EagerInitProcessor(assemblyFactory),
                                        
                                        new InitProcessor(assemblyFactory), 
                                        new PropertyProcessor(assemblyFactory),
                                        new ReferenceProcessor(assemblyFactory, javaFactory),
                                        new ResourceProcessor(assemblyFactory),
                                        
                                        // should not be loaded when running on Android platform, cause 
        								// this class uses the Class.getAnnotation method, which is 
        								// not supported yet by the Android platform
                                        new ScopeProcessor(assemblyFactory),
                                        
                                        // this class uses the org.osoa.sca.annotations.Service class, but
                                        // Android seems not being able to convert this class to its internal class
                                        // representation. The Android compiler is outputing the following message:
                                        
                                       /* [2008-04-25 19:36:22 - CalculatorAndroid] 
                                         trouble processing:
                                         [2008-04-25 19:36:22 - CalculatorAndroid] truncated annotation attribute
                                         ...while parsing AnnotationDefault attribute at offset 0000020b
                                         ...while parsing attributes[1]
                                         ...while parsing methods[0]
                                         ...while parsing org/osoa/sca/annotations/Service.class
                                         ...while processing org/osoa/sca/annotations/Service.class
                                         [2008-04-25 19:36:26 - CalculatorAndroid] 1 warning*/
                                         
                                        // So, the class will not be loaded
                                        new ServiceProcessor(assemblyFactory, javaFactory),
                                        
                                        // should not be loaded when running on Android platform, cause 
        								// this class uses the Class.getAnnotation method, which is 
        								// not supported yet by the Android platform
                                        new HeuristicPojoProcessor(assemblyFactory, javaFactory),
                                        
                                        // should not be loaded when running on Android platform, cause 
        								// this class uses the Class.getAnnotation method, which is 
        								// not supported yet by the Android platform
                                        new PolicyProcessor(assemblyFactory, policyFactory)
            };
        
        for (JavaClassVisitor extension : extensions) {
            javaImplementationFactory.addClassVisitor(extension);
        }

        registry.addExtensionPoint(new DefaultDataBindingExtensionPoint());
        registry.addExtensionPoint(new DefaultTransformerExtensionPoint());
        DataBindingExtensionPoint dataBindings = registry.getExtensionPoint(DataBindingExtensionPoint.class);
        TransformerExtensionPoint transformers = registry.getExtensionPoint(TransformerExtensionPoint.class);
        MediatorImpl mediator = new MediatorImpl(dataBindings, transformers);
        JavaPropertyValueObjectFactory factory = new JavaPropertyValueObjectFactory(mediator);

        ContextFactoryExtensionPoint contextFactories = registry.getExtensionPoint(ContextFactoryExtensionPoint.class);
        ComponentContextFactory componentContextFactory = contextFactories.getFactory(ComponentContextFactory.class);
        RequestContextFactory requestContextFactory = contextFactories.getFactory(RequestContextFactory.class);

        Map<ClassLoader, List<PolicyHandlerTuple>> policyHandlerClassNames = null;
        policyHandlerClassNames = PolicyHandlerDefinitionsLoader.loadPolicyHandlerClassnames();
        
         JavaImplementationProviderFactory javaImplementationProviderFactory =
            new JavaImplementationProviderFactory(proxyFactory, dataBindings, factory, componentContextFactory,
                                                  requestContextFactory, policyHandlerClassNames);
         
         ProviderFactoryExtensionPoint providerFactories =
             registry.getExtensionPoint(ProviderFactoryExtensionPoint.class);
         
         if (providerFactories == null) {
        	 providerFactories = new DefaultProviderFactoryExtensionPoint(registry);
        	 registry.addExtensionPoint(providerFactories);
        	 
         }
         
         providerFactories.addProviderFactory(javaImplementationProviderFactory);

        InterfaceContractMapper interfaceContractMapper = registry.getExtensionPoint(InterfaceContractMapper.class);
        RuntimeWireProcessorExtensionPoint wireProcessorExtensionPoint =
            registry.getExtensionPoint(RuntimeWireProcessorExtensionPoint.class);
        
        if (wireProcessorExtensionPoint == null) {
        	wireProcessorExtensionPoint = new DefaultWireProcessorExtensionPoint();
        	registry.addExtensionPoint(wireProcessorExtensionPoint);
        	
        }
        
        if (wireProcessorExtensionPoint != null) {
            wireProcessorExtensionPoint.addWireProcessor(new JavaCallbackRuntimeWireProcessor(interfaceContractMapper,
                                                                                              javaFactory));
            //wireProcessorExtensionPoint.addWireProcessor(new JavaPolicyHandlingRuntimeWireProcessor());
        }
    }

    public void stop(ExtensionPointRegistry registry) {
    }
}
