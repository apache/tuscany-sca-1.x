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

package org.apache.tuscany.sca.interfacedef.wsdl.xml;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;

import java.util.List;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.Extension;
import org.apache.tuscany.sca.assembly.ExtensionFactory;
import org.apache.tuscany.sca.assembly.xml.Constants;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.BaseStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXAttributeProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionRuntimeException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.interfacedef.InvalidInterfaceException;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLDefinition;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLFactory;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLInterface;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLInterfaceContract;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLObject;
import org.apache.tuscany.sca.interfacedef.wsdl.impl.WSDLOperationImpl;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;
import org.apache.tuscany.sca.monitor.impl.ProblemImpl;

import com.ibm.wsdl.OperationImpl;

/**
 *
 * @version $Rev$ $Date$
 */
public class WSDLInterfaceProcessor extends BaseStAXArtifactProcessor implements StAXArtifactProcessor<WSDLInterfaceContract>, WSDLConstants {

    private WSDLFactory wsdlFactory;
    private ExtensionFactory extensionFactory;
    private StAXAttributeProcessor<Object> extensionAttributeProcessor;    
    private Monitor monitor;

    public WSDLInterfaceProcessor(ModelFactoryExtensionPoint modelFactories, 
                                  StAXArtifactProcessor extensionProcessor,
                                  StAXAttributeProcessor extensionAttributeProcessor,
                                  Monitor monitor) {
        this.wsdlFactory = modelFactories.getFactory(WSDLFactory.class);
        this.extensionFactory = modelFactories.getFactory(ExtensionFactory.class);
        this.extensionAttributeProcessor = extensionAttributeProcessor;        
        this.monitor = monitor;
    }
    
    /**
     * Report a warning.
     * 
     * @param problem
     * @param model
     * @param message data
     */
    private void warning(String message, Object model, Object... messageParameters) {
        if (monitor != null) {
            Problem problem = new ProblemImpl(this.getClass().getName(), "interface-wsdlxml-validation-messages", Severity.WARNING, model, message, (Object[])messageParameters);
            monitor.problem(problem);
        }
     }
    
    /**
     * Report an error.
     * 
     * @param problem
     * @param model
     * @param message data
     */
    private void error(String message, Object model, Object... messageParameters) {
        if (monitor != null) {
            Problem problem = new ProblemImpl(this.getClass().getName(), "interface-wsdlxml-validation-messages", Severity.ERROR, model, message, (Object[])messageParameters);
            monitor.problem(problem);
        }
     }
   
   /**
    * Report an exception.
    * 
    * @param problem
    * @param model
    * @param exception
    */
    private void error(String message, Object model, Exception ex) {
        if (monitor != null) {
            Problem problem = new ProblemImpl(this.getClass().getName(), "interface-wsdlxml-validation-messages", Severity.ERROR, model, message, ex);
            monitor.problem(problem);
        }        
    }
    
    /**
     * Create a WSDL interface from a URI.
     * @param uri
     * @return
     * @throws ContributionReadException
     */
    private WSDLInterface createWSDLInterface(String uri) throws ContributionReadException {
        
    	WSDLInterface wsdlInterface = null;        

        // Read a QName in the form:
        // namespace#wsdl.interface(name)
        int index = uri.indexOf('#');
        if (index == -1) {
        	error("InvalidWSDLInterfaceAttr", wsdlFactory, uri);
            //throw new ContributionReadException("Invalid WSDL interface attribute: " + uri);
        } else {
        	wsdlInterface = wsdlFactory.createWSDLInterface();
            wsdlInterface.setUnresolved(true);
            String namespace = uri.substring(0, index);
            String name = uri.substring(index + 1);
            name = name.substring("wsdl.interface(".length(), name.length() - 1);
            wsdlInterface.setName(new QName(namespace, name));
        }       
        
        return wsdlInterface;
    }

    public WSDLInterfaceContract read(XMLStreamReader reader) throws ContributionReadException, XMLStreamException {
        // Read an <interface.wsdl>
        WSDLInterfaceContract wsdlInterfaceContract = wsdlFactory.createWSDLInterfaceContract();
        
        // Read wsdlLocation
        String location = reader.getAttributeValue(WSDLI_NS, WSDL_LOCATION);
        wsdlInterfaceContract.setLocation(location);
        
        String uri = getURIString(reader, INTERFACE);
        if (uri != null) {
            WSDLInterface wsdlInterface = createWSDLInterface(uri);
            if (wsdlInterface != null)
                wsdlInterfaceContract.setInterface(wsdlInterface);
        }
        
        uri = getURIString(reader, CALLBACK_INTERFACE);
        if (uri != null) {
            WSDLInterface wsdlCallbackInterface = createWSDLInterface(uri);
            if (wsdlCallbackInterface != null)
                wsdlInterfaceContract.setCallbackInterface(wsdlCallbackInterface);
        }
        
        // Handle extended attributes
        for (int a = 0; a < reader.getAttributeCount(); a++) {
            QName attributeName = reader.getAttributeName(a);
            if( attributeName.getNamespaceURI() != null && attributeName.getNamespaceURI().length() > 0) {
                if( (! Constants.SCA10_NS.equals(attributeName.getNamespaceURI()) && 
                    (! Constants.SCA10_TUSCANY_NS.equals(attributeName.getNamespaceURI()) ))) {
                    Object attributeValue = extensionAttributeProcessor.read(attributeName, reader);
                    Extension attributeExtension;
                    if (attributeValue instanceof Extension) {
                        attributeExtension = (Extension) attributeValue;
                    } else {
                        attributeExtension = extensionFactory.createExtension(attributeName, attributeValue, true);
                    }
                    wsdlInterfaceContract.getAttributeExtensions().add(attributeExtension);
                }
            }
        }                
            
        // Skip to end element
        while (reader.hasNext()) {
            if (reader.next() == END_ELEMENT && INTERFACE_WSDL_QNAME.equals(reader.getName())) {
                break;
            }
        }
        return wsdlInterfaceContract;
    }
    
    public void write(WSDLInterfaceContract wsdlInterfaceContract, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {
        // Write an <interface.wsdl>
        writer.writeStartElement(Constants.SCA10_NS, INTERFACE_WSDL);

        // Write interface name
        WSDLInterface wsdlInterface = (WSDLInterface)wsdlInterfaceContract.getInterface();
        if (wsdlInterface != null) {
            QName qname = wsdlInterface.getName();
            String uri = qname.getNamespaceURI() + "#wsdl.interface(" + qname.getLocalPart() + ")";
            writer.writeAttribute(INTERFACE, uri);
        }

        WSDLInterface wsdlCallbackInterface = (WSDLInterface)wsdlInterfaceContract.getCallbackInterface();
        if (wsdlCallbackInterface != null) {
            QName qname = wsdlCallbackInterface.getName();
            String uri = qname.getNamespaceURI() + "#wsdl.interface(" + qname.getLocalPart() + ")";
            writer.writeAttribute(CALLBACK_INTERFACE, uri);
        }
        
        // Write location
        if (wsdlInterfaceContract.getLocation() != null) {
            writer.writeAttribute(WSDLI_NS, WSDL_LOCATION, wsdlInterfaceContract.getLocation());
        }
        
        // Write extended attributes
        for(Extension extension : wsdlInterfaceContract.getAttributeExtensions()) {
            if(extension.isAttribute()) {
                extensionAttributeProcessor.write(extension, writer);
            }
        } 
                
        writer.writeEndElement();
    }
    
    private WSDLInterface resolveWSDLInterface(WSDLInterface wsdlInterface, ModelResolver resolver) throws ContributionResolveException {
        
        if (wsdlInterface != null && wsdlInterface.isUnresolved()) {

            // Resolve the WSDL interface
            wsdlInterface = resolver.resolveModel(WSDLInterface.class, wsdlInterface);
            if (wsdlInterface.isUnresolved()) {

                // If the WSDL interface has never been resolved yet, do it now
                // First, resolve the WSDL definition for the given namespace
                WSDLDefinition wsdlDefinition = wsdlFactory.createWSDLDefinition();
                wsdlDefinition.setUnresolved(true);
                wsdlDefinition.setNamespace(wsdlInterface.getName().getNamespaceURI());
                WSDLDefinition resolved = null;
                try {
                    resolved = resolver.resolveModel(WSDLDefinition.class, wsdlDefinition);
                } catch (ContributionRuntimeException e) {
                    ContributionResolveException ce = new ContributionResolveException(e.getCause());
                    error("ContributionResolveException", wsdlDefinition, ce);
                }
                if (resolved != null && !resolved.isUnresolved()) {
                    wsdlDefinition.setDefinition(resolved.getDefinition());
                    wsdlDefinition.setLocation(resolved.getLocation());
                    wsdlDefinition.setURI(resolved.getURI());
                    wsdlDefinition.getImportedDefinitions().addAll(resolved.getImportedDefinitions());
                    wsdlDefinition.getXmlSchemas().addAll(resolved.getXmlSchemas());
                    wsdlDefinition.setUnresolved(false);
                    WSDLObject<PortType> portType = wsdlDefinition.getWSDLObject(PortType.class, wsdlInterface.getName());
                    if (portType != null) {
                    	// Introspect the WSDL portType and validate 
                    	// the input/output messages.
                    	List<OperationImpl> operations = portType.getElement().getOperations();
                    	for (OperationImpl operation : operations) {
                    		if (operation.getInput() != null && operation.getInput().getMessage() == null) {
                    			ContributionResolveException ce = new ContributionResolveException("WSDL binding operation input name " + operation.getInput().getName() + " does not match with PortType Definition");
                                error("ContributionResolveException", wsdlDefinition, ce);
                    		}
                    		if (operation.getOutput() != null && operation.getOutput().getMessage() == null) {
                    			ContributionResolveException ce = new ContributionResolveException("WSDL binding operation output name " + operation.getOutput().getName() + " does not match with PortType Definition");
                                error("ContributionResolveException", wsdlDefinition, ce);
                    		}
                    	}
                    	
                        // Introspect the WSDL portType and add the resulting
                        // WSDLInterface to the resolver
                        try {
                            wsdlDefinition.setDefinition(portType.getDefinition());
                            wsdlInterface = wsdlFactory.createWSDLInterface(portType.getElement(), wsdlDefinition, resolver);
                            wsdlInterface.setWsdlDefinition(wsdlDefinition);
                            resolver.addModel(wsdlInterface);
                        } catch (ContributionRuntimeException e) {
                            ContributionResolveException ce = new ContributionResolveException(e.getCause());
                            error("ContributionResolveException", wsdlDefinition, ce);
                        } catch (InvalidInterfaceException e) {
                        	ContributionResolveException ce = new ContributionResolveException(e);
                        	error("ContributionResolveException", wsdlDefinition, ce);
                            //throw ce;
                        }                        
                    }
                    else {
                    	warning("WsdlInterfaceDoesNotMatch", wsdlDefinition, wsdlInterface.getName());
                    }
                }
            }
        }
        return wsdlInterface;
    }
    
    public void resolve(WSDLInterfaceContract wsdlInterfaceContract, ModelResolver resolver) throws ContributionResolveException {
        
        // Resolve the interface and callback interface
        WSDLInterface wsdlInterface = resolveWSDLInterface((WSDLInterface)wsdlInterfaceContract.getInterface(), resolver);
        wsdlInterfaceContract.setInterface(wsdlInterface);
        
        WSDLInterface wsdlCallbackInterface = resolveWSDLInterface((WSDLInterface)wsdlInterfaceContract.getCallbackInterface(), resolver);
        wsdlInterfaceContract.setCallbackInterface(wsdlCallbackInterface);
    }
    
    public QName getArtifactType() {
        return WSDLConstants.INTERFACE_WSDL_QNAME;
    }
    
    public Class<WSDLInterfaceContract> getModelType() {
        return WSDLInterfaceContract.class;
    }
}
