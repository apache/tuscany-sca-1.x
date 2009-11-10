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
package org.apache.tuscany.sca.contribution.jee.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.tuscany.sca.assembly.AssemblyFactory;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ComponentReference;
import org.apache.tuscany.sca.assembly.ComponentService;
import org.apache.tuscany.sca.assembly.ComponentType;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.CompositeReference;
import org.apache.tuscany.sca.assembly.CompositeService;
import org.apache.tuscany.sca.assembly.Multiplicity;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.jee.EJBImplementationGenerated;
import org.apache.tuscany.sca.contribution.jee.EjbInfo;
import org.apache.tuscany.sca.contribution.jee.EjbModuleInfo;
import org.apache.tuscany.sca.contribution.jee.EjbReferenceInfo;
import org.apache.tuscany.sca.contribution.jee.EnvEntryInfo;
import org.apache.tuscany.sca.contribution.jee.InjectionTarget;
import org.apache.tuscany.sca.contribution.jee.JavaEEApplicationInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEOptionalExtension;
import org.apache.tuscany.sca.contribution.jee.WebImplementationGenerated;
import org.apache.tuscany.sca.contribution.jee.WebModuleInfo;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.InvalidInterfaceException;
import org.apache.tuscany.sca.interfacedef.java.JavaInterfaceFactory;
import org.apache.tuscany.sca.policy.Intent;
import org.apache.tuscany.sca.policy.PolicyFactory;

public class JavaEEOptionalExtensionImpl implements JavaEEOptionalExtension {
    
    private AssemblyFactory assemblyFactory;
    private JavaInterfaceFactory javaInterfaceFactory;
    private PolicyFactory policyFactory;
    private Intent EJB_INTENT;
    
    public static final Map<String, QName> ALLOWED_ENV_ENTRY_TYPES;
    static {
        ALLOWED_ENV_ENTRY_TYPES = new HashMap<String, QName>();
        ALLOWED_ENV_ENTRY_TYPES.put(String.class.getName(), new QName("http://www.w3.org/2001/XMLSchema", "string",
                                                                      "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Character.class.getName(), new QName("http://www.w3.org/2001/XMLSchema", "string",
                                                                         "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Byte.class.getName(), new QName("http://www.w3.org/2001/XMLSchema", "byte", "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Short.class.getName(),
                                    new QName("http://www.w3.org/2001/XMLSchema", "short", "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Integer.class.getName(),
                                    new QName("http://www.w3.org/2001/XMLSchema", "int", "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Long.class.getName(), new QName("http://www.w3.org/2001/XMLSchema", "long", "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Boolean.class.getName(), new QName("http://www.w3.org/2001/XMLSchema", "boolean",
                                                                       "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Double.class.getName(), new QName("http://www.w3.org/2001/XMLSchema", "double",
                                                                      "xsd"));
        ALLOWED_ENV_ENTRY_TYPES.put(Float.class.getName(),
                                    new QName("http://www.w3.org/2001/XMLSchema", "float", "xsd"));
    }

    public JavaEEOptionalExtensionImpl(ModelFactoryExtensionPoint modelFactories) {
        this.assemblyFactory = modelFactories.getFactory(AssemblyFactory.class);
        this.javaInterfaceFactory = modelFactories.getFactory(JavaInterfaceFactory.class);
        this.policyFactory = modelFactories.getFactory(PolicyFactory.class);
        
        EJB_INTENT = policyFactory.createIntent();
        EJB_INTENT.setName(new QName("http://www.osoa.org/xmlns/sca/1.0", "ejb"));
    }
    
    public ComponentType createImplementationWebComponentType(WebModuleInfo webModule) {
        ComponentType componentType = assemblyFactory.createComponentType();
        
        // Process Remote EJB References
        for(Map.Entry<String, EjbReferenceInfo> entry : webModule.getEjbReferences().entrySet()) {
            EjbReferenceInfo ejbRef = entry.getValue();
            // If the EJB reference has @Reference SCA annotation, then skip that reference
            if(!hasReferenceAnnotation(ejbRef.injectionTarget)) {
                continue;
            }
            String referenceName = entry.getKey();
            referenceName = referenceName.replace("/", "_");
            Reference reference = assemblyFactory.createComponentReference();
            reference.setName(referenceName);
            InterfaceContract ic = javaInterfaceFactory.createJavaInterfaceContract();
            try {
                ic.setInterface(javaInterfaceFactory.createJavaInterface(ejbRef.businessInterface));
            } catch (InvalidInterfaceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            reference.setInterfaceContract(ic);
            reference.getRequiredIntents().add(EJB_INTENT);
            reference.setMultiplicity(Multiplicity.ZERO_ONE);
            componentType.getReferences().add(reference);
        }
        
        // Process env-entries to compute properties
        for (Map.Entry<String, EnvEntryInfo> entry : webModule.getEnvEntries().entrySet()) {
            EnvEntryInfo envEntry = entry.getValue();
            String type = envEntry.type;
            if (!ALLOWED_ENV_ENTRY_TYPES.containsKey(type)) {
                continue;
            }
            String propertyName = envEntry.name;
            propertyName = propertyName.replace("/", "_");
            String value = envEntry.value;
            Property property = assemblyFactory.createComponentProperty();
            property.setName(propertyName);
            property.setXSDType(ALLOWED_ENV_ENTRY_TYPES.get(type));
            property.setValue(value);
            componentType.getProperties().add(property);
        }
        
        return componentType;
    }

    public ComponentType createImplementationEjbComponentType(EjbModuleInfo ejbModule, String ejbName) {
        ComponentType componentType = assemblyFactory.createComponentType();
        EjbInfo ejbInfo = ejbModule.getEjbInfo(ejbName);
        if(ejbInfo == null) {
            return null;
        }

        // Process Remote EJB References
        for(Map.Entry<String, EjbReferenceInfo> entry : ejbInfo.ejbReferences.entrySet()) {
            EjbReferenceInfo ejbRef = entry.getValue();
            String referenceName = entry.getKey();
            referenceName = referenceName.replace("/", "_");
            Reference reference = assemblyFactory.createComponentReference();
            reference.setName(referenceName);
            InterfaceContract ic = javaInterfaceFactory.createJavaInterfaceContract();
            try {
                ic.setInterface(javaInterfaceFactory.createJavaInterface(ejbRef.businessInterface));
            } catch (InvalidInterfaceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            reference.setInterfaceContract(ic);
            reference.getRequiredIntents().add(EJB_INTENT);
            reference.setMultiplicity(Multiplicity.ZERO_ONE);
            componentType.getReferences().add(reference);
        }
        
        // Process env-entries to compute properties
        for (Map.Entry<String, EnvEntryInfo> entry : ejbInfo.envEntries.entrySet()) {
            EnvEntryInfo envEntry = entry.getValue();
            String type = envEntry.type;
            if (!ALLOWED_ENV_ENTRY_TYPES.containsKey(type)) {
                continue;
            }
            String propertyName = envEntry.name;
            propertyName = propertyName.replace("/", "_");
            String value = envEntry.value;
            Property property = assemblyFactory.createComponentProperty();
            property.setName(propertyName);
            property.setXSDType(ALLOWED_ENV_ENTRY_TYPES.get(type));
            property.setValue(value);
            componentType.getProperties().add(property);
        }
        return componentType;
    }
    
    public void createImplementationJeeComposite(WebModuleInfo webModule, Composite composite) {
        
        Component component = findComponent(composite, webModule);
        
        // Process Remote EJB References
        for(Map.Entry<String, EjbReferenceInfo> entry : webModule.getEjbReferences().entrySet()) {
            EjbReferenceInfo ejbRef = entry.getValue();
            String referenceName = entry.getKey();
            referenceName = referenceName.replace("/", "_");
            ComponentReference reference = assemblyFactory.createComponentReference();
            reference.setName(referenceName);
            InterfaceContract ic = javaInterfaceFactory.createJavaInterfaceContract();
            try {
                ic.setInterface(javaInterfaceFactory.createJavaInterface(ejbRef.businessInterface));
            } catch (InvalidInterfaceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            reference.setInterfaceContract(ic);
            reference.getRequiredIntents().add(EJB_INTENT);
            reference.setMultiplicity(Multiplicity.ZERO_ONE);
            
            addComponentReference(composite, component, reference);
        }
    }

    public void createImplementationJeeComposite(EjbModuleInfo ejbModule, Composite composite) {
        
        for(Map.Entry<String, EjbInfo> entry : ejbModule.getEjbInfos().entrySet()) {
            EjbInfo ejbInfo = entry.getValue();
            
            Component component = findComponent(composite, ejbInfo, ejbModule);
            
            // Process Remote EJB References
            for(Map.Entry<String, EjbReferenceInfo> entry1 : ejbInfo.ejbReferences.entrySet()) {
                EjbReferenceInfo ejbRef = entry1.getValue();
                String referenceName = ejbRef.referenceName;
                referenceName = referenceName.replace("/", "_");
                referenceName = ejbInfo.beanName + "_" + referenceName;
                ComponentReference reference = assemblyFactory.createComponentReference();
                reference.setName(referenceName);
                InterfaceContract ic = javaInterfaceFactory.createJavaInterfaceContract();
                try {
                    ic.setInterface(javaInterfaceFactory.createJavaInterface(ejbRef.businessInterface));
                } catch (InvalidInterfaceException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                reference.setInterfaceContract(ic);
                reference.getRequiredIntents().add(EJB_INTENT);
                reference.setMultiplicity(Multiplicity.ZERO_ONE);
                
                addComponentReference(composite, component, reference);
            }
        }
    }

    
    public void createImplementationJeeComposite(JavaEEApplicationInfo appInfo, Composite composite) {
        
        for(Map.Entry<String, EjbModuleInfo> entry0 : appInfo.getEjbModuleInfos().entrySet()) {
            EjbModuleInfo ejbModule = entry0.getValue();
            
            for(Map.Entry<String, EjbInfo> entry : ejbModule.getEjbInfos().entrySet()) {
                EjbInfo ejbInfo = entry.getValue();
                
                Component component = findComponent(composite, ejbInfo, ejbModule);
                
                // Process Remote EJB References
                for(Map.Entry<String, EjbReferenceInfo> entry1 : ejbInfo.ejbReferences.entrySet()) {
                    EjbReferenceInfo ejbRef = entry1.getValue();
                    String referenceName = ejbRef.referenceName;
                    referenceName = referenceName.replace("/", "_");
                    referenceName = ejbInfo.beanName + "_" + referenceName;
                    ComponentReference reference = assemblyFactory.createComponentReference();
                    reference.setName(referenceName);
                    InterfaceContract ic = javaInterfaceFactory.createJavaInterfaceContract();
                    try {
                        ic.setInterface(javaInterfaceFactory.createJavaInterface(ejbRef.businessInterface));
                    } catch (InvalidInterfaceException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    reference.setInterfaceContract(ic);
                    reference.getRequiredIntents().add(EJB_INTENT);
                    reference.setMultiplicity(Multiplicity.ZERO_ONE);
                    
                    addComponentReference(composite, component, reference);
                }
            }
        }
    }   
    
    /**
     * We are fluffing up the JEEImplemention composite to represent the components
     * in the JEE archive. Given the JEEimplemenation composite find a named component 
     * it if already exists or create it if it doesn't. 
     * 
     * @param composite
     * @param ejbInfo
     * @return
     */
    private Component findComponent(Composite composite, EjbInfo ejbInfo, EjbModuleInfo ejbmoduleInfo){
        String componentName = ejbInfo.beanName;
        Component component = null;
        
        for (Component tmpComponent : composite.getComponents()){
            if (tmpComponent.getName().equals(componentName)){
               component = tmpComponent;
               break;
            }
        }
         
        if (component == null){
            component = assemblyFactory.createComponent();
            component.setName(componentName);
            component.setUnresolved(true);
            composite.getComponents().add(component);
            
            EJBImplementationGenerated implementation = new EJBImplementationGeneratedImpl();
            implementation.setUnresolved(true);
            implementation.setEJBInfo(ejbInfo);
            implementation.setEjbModuleInfo(ejbmoduleInfo);
            component.setImplementation(implementation);
        }
        
        return component;
    } 
    
    /**
     * We are fluffing up the JEEImplemention composite to represent the components
     * in the JEE archive. Given the JEEimplemenation composite find a named component 
     * it if already exists or create it if it doesn't. 
     * 
     * @param composite
     * @param ejbInfo
     * @return
     */
    private Component findComponent(Composite composite, WebModuleInfo webInfo){
        String componentName = webInfo.getModuleName();
        Component component = null;
        
        for (Component tmpComponent : composite.getComponents()){
            if (tmpComponent.getName().equals(componentName)){
               component = tmpComponent;
               break;
            }
        }
         
        if (component == null){
            component = assemblyFactory.createComponent();
            component.setName(componentName);
            component.setUnresolved(true);
            composite.getComponents().add(component);
            
            WebImplementationGenerated implementation = new WebImplementationGeneratedImpl();
            implementation.setUnresolved(true);
            // need generated impl to represent web modules
            implementation.setWebInfo(webInfo);
            component.setImplementation(implementation);
        }
        
        return component;
    }     
    
    /**
     * Add a component reference and fluff up a composite reference to match
     * 
     * @param composite
     * @param component
     * @param service
     */
    private void addComponentReference(Composite composite, Component component, ComponentReference reference){        
        component.getImplementation().getReferences().add(reference);
        
        CompositeReference compositeReference = assemblyFactory.createCompositeReference();
        composite.getReferences().add(compositeReference);
        
        compositeReference.setName(reference.getName());
        compositeReference.getPromotedReferences().add(reference);        
    }    

    private boolean hasReferenceAnnotation(InjectionTarget injectionTarget) {
        if(injectionTarget.targetClass == null || injectionTarget.targetClass.equals("")) {
            return false;
        }
        try {
            Class<?> clazz = Class.forName(injectionTarget.targetClass);
            try {
                Method method = clazz.getDeclaredMethod("set"+injectionTarget.targetName);
                if(method.isAnnotationPresent(javax.ejb.EJB.class)) {
                    return method.isAnnotationPresent(org.osoa.sca.annotations.Reference.class);
                } else {
                    // The method does not have @EJB annotation. So, the method is not good for us.
                    throw new NoSuchMethodException("set"+injectionTarget.targetName);
                }
            } catch(NoSuchMethodException nsme) {
                try {
                    Field field = clazz.getDeclaredField(injectionTarget.targetName);
                    return field.isAnnotationPresent(org.osoa.sca.annotations.Reference.class);
                } catch(NoSuchFieldException nsfe) {
                    return false;
                }
            }
        } catch(ClassNotFoundException cnfe) {
            return false;
        }
    }
}
