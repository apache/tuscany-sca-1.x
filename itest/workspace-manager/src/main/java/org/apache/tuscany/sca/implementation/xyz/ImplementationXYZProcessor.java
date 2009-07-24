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
package org.apache.tuscany.sca.implementation.xyz;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.AssemblyFactory;
import org.apache.tuscany.sca.assembly.ComponentType;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.assembly.xml.PolicyAttachPointProcessor;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ClassReference;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.interfacedef.InvalidInterfaceException;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.policy.PolicyFactory;



public class ImplementationXYZProcessor implements StAXArtifactProcessor<ImplementationXYZ> {
    private static final QName IMPLEMENTATION_XYZ = new QName("http://someuri", "implementation.xyz");
    
    private AssemblyFactory assemblyFactory;
    private ImplementationXYZFactory implementationXYZFactory;
    private PolicyFactory policyFactory;
    private PolicyAttachPointProcessor policyProcessor;
    
    public ImplementationXYZProcessor(ModelFactoryExtensionPoint modelFactories, Monitor monitor) {
        
        // Get the assembly and Java interface factories as we'll need them to
        // create model objects 
        assemblyFactory = modelFactories.getFactory(AssemblyFactory.class);
        policyFactory = modelFactories.getFactory(PolicyFactory.class);
        implementationXYZFactory = modelFactories.getFactory(ImplementationXYZFactory.class);
        policyProcessor = new PolicyAttachPointProcessor(policyFactory);
    }

    public QName getArtifactType() {
        // Returns the qname of the XML element processed by this processor
        return IMPLEMENTATION_XYZ;
    }

    public Class<ImplementationXYZ> getModelType() {
        // Returns the type of model processed by this processor
        return ImplementationXYZ.class;
    }

    public ImplementationXYZ read(XMLStreamReader reader) throws ContributionReadException, XMLStreamException {
    
        // Read an <implementation.xyz> element
        ImplementationXYZ implementation = implementationXYZFactory.createImplementationXYZ();
        
        // Read policies
        policyProcessor.readPolicies(implementation, reader);
        
        // Read the module attribute.
        String anAttribute = reader.getAttributeValue(null, "anAttribute");
        implementation.setAnAttribute(anAttribute);
        
        implementation.setUnresolved(true);
        
        // Skip to end element
        while (reader.hasNext()) {
            if (reader.next() == END_ELEMENT && IMPLEMENTATION_XYZ.equals(reader.getName())) {
                break;
            }
        }
        
        return implementation;
    }

    public void resolve(ImplementationXYZ implementation, ModelResolver resolver) throws ContributionResolveException {
        
        // Resolve the xyz implementation
        
        // First resolve its module
        //resolver.resolveModel(ClassReference.class, null);
        
        // Check to see if we have a .componentType file describing the POJO class
        ComponentType componentType = assemblyFactory.createComponentType();
        componentType.setUnresolved(true);
        componentType.setURI(implementation.getURI() + ".componentType");
        componentType = resolver.resolveModel(ComponentType.class, componentType);
        if (!componentType.isUnresolved()) {
            
            // We have a component type description, merge it into the POJO model
            implementation.getServices().addAll(componentType.getServices());
            implementation.getReferences().addAll(componentType.getReferences());
            implementation.getProperties().addAll(componentType.getProperties());
            
        } else {
            
            // We have no component type description, simply introspect the POJO and
            // create a single Service for it
/*        	
            Service service = assemblyFactory.createService();
            service.setName(pojoClass.getSimpleName());
            JavaInterface javaInterface;
            try {
                javaInterface = javaFactory.createJavaInterface(pojoClass);
            } catch (InvalidInterfaceException e) {
                throw new ContributionResolveException(e);
            }
            JavaInterfaceContract interfaceContract = javaFactory.createJavaInterfaceContract();
            interfaceContract.setInterface(javaInterface);
            service.setInterfaceContract(interfaceContract);
            implementation.getServices().add(service);
*/            
        }
        
        // Mark the implementation resolved now
        implementation.setUnresolved(false);
    }

    public void write(ImplementationXYZ implementation, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {
        
        // Write <implementation.xyz> element
        policyProcessor.writePolicyPrefixes(implementation, writer);
        writer.writeStartElement(IMPLEMENTATION_XYZ.getNamespaceURI(), IMPLEMENTATION_XYZ.getLocalPart());
        policyProcessor.writePolicyAttributes(implementation, writer);
        
        if (implementation.getAnAttribute() != null) {
            writer.writeAttribute("anAttribute", implementation.getAnAttribute());
        }
        
        writer.writeEndElement();
    }
}
