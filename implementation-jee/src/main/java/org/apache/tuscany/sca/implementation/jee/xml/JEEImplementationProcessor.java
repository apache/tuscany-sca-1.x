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
package org.apache.tuscany.sca.implementation.jee.xml;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;

import java.net.URI;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.AssemblyFactory;
import org.apache.tuscany.sca.assembly.ComponentType;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.xml.Constants;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.jee.ModelObject;
import org.apache.tuscany.sca.contribution.jee.EjbModuleInfo;
import org.apache.tuscany.sca.contribution.jee.ExternalEarInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEApplicationInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEExtension;
import org.apache.tuscany.sca.contribution.jee.JavaEEOptionalExtension;
import org.apache.tuscany.sca.contribution.jee.WebModuleInfo;
import org.apache.tuscany.sca.contribution.jee.impl.ModelObjectImpl;
import org.apache.tuscany.sca.contribution.jee.impl.EjbModuleInfoImpl;
import org.apache.tuscany.sca.contribution.jee.impl.JavaEEApplicationInfoImpl;
import org.apache.tuscany.sca.contribution.jee.impl.WebModuleInfoImpl;
import org.apache.tuscany.sca.contribution.processor.BaseStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.implementation.jee.JEEImplementation;
import org.apache.tuscany.sca.implementation.jee.JEEImplementationFactory;
import org.apache.tuscany.sca.monitor.Monitor;

/**
 * Implements a StAX artifact processor for JEE implementations.
 * 
 * @version $Rev$ $Date$
 */
public class JEEImplementationProcessor extends BaseStAXArtifactProcessor implements StAXArtifactProcessor<JEEImplementation> {
    private static final QName IMPLEMENTATION_JEE = new QName(Constants.SCA10_NS, "implementation.jee");
    
    private AssemblyFactory assemblyFactory;
    private JEEImplementationFactory implementationFactory;
    private JavaEEExtension jeeExtension;
    private JavaEEOptionalExtension jeeOptionalExtension;
    private Monitor monitor;
    
    public JEEImplementationProcessor(ModelFactoryExtensionPoint modelFactories, Monitor monitor) {
        this.assemblyFactory = modelFactories.getFactory(AssemblyFactory.class);
        this.implementationFactory = modelFactories.getFactory(JEEImplementationFactory.class);
        this.jeeExtension = modelFactories.getFactory(JavaEEExtension.class);
        this.jeeOptionalExtension = modelFactories.getFactory(JavaEEOptionalExtension.class);
        this.monitor = monitor;
    }

    public QName getArtifactType() {
        // Returns the QName of the XML element processed by this processor
        return IMPLEMENTATION_JEE;
    }

    public Class<JEEImplementation> getModelType() {
        // Returns the type of model processed by this processor
        return JEEImplementation.class;
    }

    public JEEImplementation read(XMLStreamReader reader) throws ContributionReadException, XMLStreamException {
        
        // Read an <implementation.jee> element
        JEEImplementation implementation = implementationFactory.createJEEImplementation();
        implementation.setUnresolved(true);

        // Read the archive attribute
        String archive = getString(reader, "archive");
        if (archive != null) {
            implementation.setArchive(archive);

            // Set the URI of the component type 
            implementation.setURI(archive);
        }

        // Skip to end element
        while (reader.hasNext()) {
            if (reader.next() == END_ELEMENT && IMPLEMENTATION_JEE.equals(reader.getName())) {
                break;
            }
        }
        
        return implementation;
    }

    public void resolve(JEEImplementation implementation, ModelResolver resolver) throws ContributionResolveException {
        // Resolve the component type
        String uri = implementation.getURI();
        String archive = implementation.getArchive();
        if (uri != null) {
            Object moduleInfo = null;
            ExternalEarInfo extEar = null;            
            if(uri.equals("")) {
                if(moduleInfo == null) {
                    WebModuleInfo unresolved = new WebModuleInfoImpl();
                    unresolved.setUri(URI.create(archive));
                    WebModuleInfo resolved = resolver.resolveModel(WebModuleInfo.class, unresolved);
                    if(unresolved != resolved) {
                        moduleInfo = resolved;
                    }
                }
                if(moduleInfo == null) {
                    EjbModuleInfo unresolved = new EjbModuleInfoImpl();
                    unresolved.setUri(URI.create(archive));
                    EjbModuleInfo resolved = resolver.resolveModel(EjbModuleInfo.class, unresolved);
                    if(unresolved != resolved) {
                        moduleInfo = resolved;
                    }
                }
                if(moduleInfo == null) {
                    JavaEEApplicationInfo unresolved = new JavaEEApplicationInfoImpl();
                    unresolved.setUri(URI.create(archive));
                    JavaEEApplicationInfo resolved = resolver.resolveModel(JavaEEApplicationInfo.class, unresolved);
                    if(unresolved != resolved) {
                        moduleInfo = resolved;
                    }
                }
            } else if(uri.endsWith(".war")) {
                WebModuleInfo webModuleInfo = new WebModuleInfoImpl();
                webModuleInfo.setUri(URI.create(archive));
                webModuleInfo = resolver.resolveModel(WebModuleInfo.class, webModuleInfo);
                moduleInfo = webModuleInfo;
            } else if(uri.endsWith(".jar")) {
                EjbModuleInfo ejbModuleInfo = new EjbModuleInfoImpl();
                ejbModuleInfo.setUri(URI.create(archive));
                ejbModuleInfo = resolver.resolveModel(EjbModuleInfo.class, ejbModuleInfo);
                moduleInfo = ejbModuleInfo;
            } else if(uri.endsWith(".ear")) {
                final JavaEEApplicationInfo appInfo = new JavaEEApplicationInfoImpl();
                appInfo.setUri(URI.create(archive));
                ExternalEarInfo unresolved = new ExternalEarInfo() {
                    public JavaEEApplicationInfo getAppInfo() {
                        return appInfo;
                    }
                    public Composite getAppComposite() {
                        return null;
                    }};

                ExternalEarInfo resolved = resolver.resolveModel(ExternalEarInfo.class, unresolved);
                if(resolved != unresolved) {
                    extEar = resolved;
                }
                moduleInfo = resolved.getAppInfo();                
            }
            
            if(moduleInfo instanceof WebModuleInfo) {
                // Check for web composite
                ModelObject unresolved = new ModelObjectImpl();
                unresolved.setUri(URI.create("WEB-INF/web.composite"));
                ModelObject resolved = resolver.resolveModel(ModelObject.class, unresolved);
                if(resolved != unresolved) {
                    // Found web composite
                    Composite appComposite = (Composite)resolved.getObject();
                    implementation.getServices().addAll(appComposite.getServices());
                    implementation.getReferences().addAll(appComposite.getReferences());
                    implementation.getProperties().addAll(appComposite.getProperties());
                }

                // TODO: Obtain includeDefaults value from the composite
                boolean includeDefaults = false;
                
                if(includeDefaults || resolved == unresolved) {
                    if(jeeOptionalExtension != null) {
                        ComponentType ct = jeeOptionalExtension.createImplementationJeeComponentType((WebModuleInfo)moduleInfo);
                        implementation.getReferences().addAll(ct.getReferences());
                        implementation.getProperties().addAll(ct.getProperties());
                    }
                }
            } else if(moduleInfo instanceof EjbModuleInfo) {
                // Check for ejb-jar composite
                ModelObject unresolved = new ModelObjectImpl();
                unresolved.setUri(URI.create("META-INF/ejb-jar.composite"));
                ModelObject resolved = resolver.resolveModel(ModelObject.class, unresolved);
                if(resolved != unresolved) {
                    // Found ejb-jar composite
                    Composite appComposite = (Composite)resolved.getObject();
                    implementation.getServices().addAll(appComposite.getServices());
                    implementation.getReferences().addAll(appComposite.getReferences());
                    implementation.getProperties().addAll(appComposite.getProperties());
                }

                // TODO: Obtain includeDefaults value from the composite
                boolean includeDefaults = false;
                
                if(includeDefaults || resolved == unresolved) {
                    if(jeeExtension != null) {
                        ComponentType ct = jeeExtension.createImplementationJeeComponentType((EjbModuleInfo)moduleInfo);
                        implementation.getServices().addAll(ct.getServices());
                    }
                    if(jeeOptionalExtension != null) {
                        ComponentType ct = jeeOptionalExtension.createImplementationJeeComponentType((EjbModuleInfo)moduleInfo);
                        implementation.getServices().addAll(ct.getServices());
                        implementation.getReferences().addAll(ct.getReferences());
                        implementation.getProperties().addAll(ct.getProperties());
                    }
                }
            } else if(moduleInfo instanceof JavaEEApplicationInfo) {
                // Check for application composite
                Composite appComposite = null;
                if(extEar != null) { 
                    appComposite = extEar.getAppComposite();
                } else {
                    ModelObject unresolved = new ModelObjectImpl();
                    unresolved.setUri(URI.create("META-INF/application.composite"));
                    ModelObject resolved = resolver.resolveModel(ModelObject.class, unresolved);
                    if(resolved != unresolved) {
                       // Found application composite
                       appComposite = (Composite)resolved.getObject();
                    }
                }
                
                if(appComposite != null) {
                    // Found application composite
                    implementation.getServices().addAll(appComposite.getServices());
                    implementation.getReferences().addAll(appComposite.getReferences());
                    implementation.getProperties().addAll(appComposite.getProperties());
                }                    
                
                // TODO: Obtain includeDefaults value from the composite
                boolean includeDefaults = false;
                
                if(includeDefaults || appComposite == null) {
                    if(jeeExtension != null) {
                        ComponentType ct = jeeExtension.createImplementationJeeComponentType((JavaEEApplicationInfo)moduleInfo);
                        implementation.getServices().addAll(ct.getServices());
                    }
                    if(jeeOptionalExtension != null) {
                        ComponentType ct = jeeOptionalExtension.createImplementationJeeComponentType((JavaEEApplicationInfo)moduleInfo);
                        implementation.getServices().addAll(ct.getServices());
                        implementation.getReferences().addAll(ct.getReferences());
                        implementation.getProperties().addAll(ct.getProperties());
                    }
                }
            }
        }
        implementation.setUnresolved(false);
    }

    public void write(JEEImplementation implementation, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {
        
        // Write <implementation.jee>
        writeStart(writer, IMPLEMENTATION_JEE.getNamespaceURI(), IMPLEMENTATION_JEE.getLocalPart(),
                   new XAttr("archive", implementation.getArchive()));
        
        writeEnd(writer);
    }
}
