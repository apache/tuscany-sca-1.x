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

package org.apache.tuscany.sca.implementation.spring.runtime.context;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.tuscany.sca.implementation.spring.processor.ComponentNameAnnotationProcessor;
import org.apache.tuscany.sca.implementation.spring.processor.ComponentStub;
import org.apache.tuscany.sca.implementation.spring.processor.ConstructorAnnotationProcessor;
import org.apache.tuscany.sca.implementation.spring.processor.InitDestroyAnnotationProcessor;
import org.apache.tuscany.sca.implementation.spring.processor.PropertyAnnotationProcessor;
import org.apache.tuscany.sca.implementation.spring.processor.PropertyValueStub;
import org.apache.tuscany.sca.implementation.spring.processor.ReferenceAnnotationProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.UrlResource;
import org.springframework.core.SpringVersion;

/**
 * This is the runtime side tie for the corresponding tuscany side stub class.
 * It enables the Tuscany code to invoke methods on a Spring context without
 * needing to know about any Spring classes. See the SpringContextStub class
 * in the implementation-spring module for what the stub does. 
 */
public class SpringContextTie {

    private AbstractApplicationContext springContext;
    private SpringImplementationStub implementation;
    private boolean isAnnotationSupported;
    private String versionSupported;
    private boolean isMultipleContextSupport;
    
    // TUSCANY-3128
    // extension of the generic application context just to force the classloader
    // on the bean factory to stay set to the contribution classloader
    // instead of being set back to the application classloader
    private class LocalGenericApplicationContext extends GenericApplicationContext{
        
        ClassLoader classloader = null;
        
        public LocalGenericApplicationContext(DefaultListableBeanFactory beanFactory, 
                                              ApplicationContext parent,
                                              ClassLoader classloader) {
            super(beanFactory, parent);
            this.classloader = classloader;
        }
        
        @Override
        protected void postProcessBeanFactory(
                ConfigurableListableBeanFactory beanFactory) {
            beanFactory.setBeanClassLoader(classloader);
        }
    }
    
    public SpringContextTie(SpringImplementationStub implementation, URL resource, boolean annotationSupport, String versionSupported, boolean multipleContextSupport) throws Exception {
        this.implementation = implementation;
        this.isAnnotationSupported = annotationSupport;
        this.versionSupported = versionSupported;
        this.isMultipleContextSupport = multipleContextSupport;
        if (! this.versionSupported.equals("ANY")) {
        	if ((SpringVersion.getVersion()!= null) && (! SpringVersion.getVersion().equals(versionSupported)))
        		throw new RuntimeException("Unsupported version: Use only Spring Framework Version " + versionSupported);
        }        
        SCAParentApplicationContext scaParentContext = new SCAParentApplicationContext(implementation);
        springContext = createApplicationContext(scaParentContext, resource);  
    }

    public void start() {
        // Do refresh here to ensure that Spring Beans are not touched before 
    	// the SCA config process is complete...
        springContext.refresh();
        springContext.start();
    }

    public void close() {
        springContext.close();
        if (springContext instanceof GenericApplicationContext) {
            springContext.stop();
        }
    }

    /**
     * Create appropriate ApplicationContext by reading the bean definitions.
     */
    private AbstractApplicationContext createApplicationContext(SCAParentApplicationContext scaParentContext, URL resource) {

        XmlBeanFactory beanFactory = new XmlBeanFactory(new UrlResource(resource));
        beanFactory.setBeanClassLoader(implementation.getClassLoader());
        AbstractApplicationContext appContext = null;
        
        if (isMultipleContextSupport) {
	        for (String bean : beanFactory.getBeanDefinitionNames()) {
	            String beanClassName = (beanFactory.getType(bean)).getName();
	            // Using FileSystemXmlApplicationContext is not supported, as the 
	            // SCA runtime does not support paths relative to current VM working directory.
	            /*if (beanClassName.indexOf(".FileSystemXmlApplicationContext") != -1) {
	            	throw new RuntimeException("Usage of FileSystemXmlApplicationContext Bean is not supported");
	            }*/
	            	
	            if (beanClassName.indexOf(".ClassPathXmlApplicationContext") != -1) {
	            	BeanDefinition beanDef = beanFactory.getBeanDefinition(bean);                           
	                String[] configLocations = null;
	                List<ConstructorArgumentValues.ValueHolder> conArgs = 
	                        beanDef.getConstructorArgumentValues().getGenericArgumentValues();
	                for (ConstructorArgumentValues.ValueHolder conArg : conArgs) {
	                	if (conArg.getValue() instanceof TypedStringValue) {
	                        TypedStringValue value = (TypedStringValue) conArg.getValue();
	                        if (value.getValue().indexOf(".xml") != -1)
	                        	configLocations = new String[]{value.getValue()};
	                	}
	                    if (conArg.getValue() instanceof ManagedList) {
	                        Iterator itml = ((ManagedList)conArg.getValue()).iterator();
	                        StringBuffer values = new StringBuffer();
	                        while (itml.hasNext()) {
	                            TypedStringValue next = (TypedStringValue)itml.next();
	                            if (next.getValue().indexOf(".xml") != -1) {
	                            	values.append(implementation.getClassLoader().getResource(next.getValue()).toString());
	                                values.append("~");
	                            }
	                        }
	                        configLocations = (values.toString()).split("~");                                    
	                    }
	                }
	                
		            appContext = new ClassPathXmlApplicationContext(configLocations, true, scaParentContext);	            
		            if (isAnnotationSupported)
		            	includeAnnotationProcessors(appContext.getBeanFactory());
		            return appContext;
	            }               
	        }
        }
        
        // use the generic application context as default 
        if (isAnnotationSupported)
        {            
        	includeAnnotationProcessors(beanFactory);
        }
        
        appContext = new LocalGenericApplicationContext(beanFactory, 
                                                        scaParentContext,
                                                        implementation.getClassLoader());
        return appContext;
    }

    public Object getBean(String id) throws BeansException {
        return springContext.getBean(id);
    }

    /**
     * Include BeanPostProcessor to deal with SCA Annotations in Spring Bean
     */
    private void includeAnnotationProcessors(ConfigurableListableBeanFactory beanFactory) {
        
        // Processor to deal with @Init and @Destroy SCA Annotations
        BeanPostProcessor initDestroyProcessor = new InitDestroyAnnotationProcessor();
        beanFactory.addBeanPostProcessor(initDestroyProcessor);

        // Processor to deal with @Reference SCA Annotations
        ComponentStub component = new ComponentStub(implementation.getComponentTie());
        BeanPostProcessor referenceProcessor = new ReferenceAnnotationProcessor(component);
        beanFactory.addBeanPostProcessor(referenceProcessor);
        
        // Processor to deal with @Property SCA Annotations
        PropertyValueStub pvs = new PropertyValueStub(implementation.getPropertyValueTie());
        BeanPostProcessor propertyProcessor = new PropertyAnnotationProcessor(pvs);
        beanFactory.addBeanPostProcessor(propertyProcessor);
        
        // Processor to deal with @ComponentName SCA Annotations
        BeanPostProcessor componentNameProcessor = new ComponentNameAnnotationProcessor(implementation.getComponentName());
        beanFactory.addBeanPostProcessor(componentNameProcessor);
        
        // Processor to deal with @Constructor SCA Annotations
        BeanPostProcessor constructorProcessor = new ConstructorAnnotationProcessor();
        beanFactory.addBeanPostProcessor(constructorProcessor);         
    }

}
