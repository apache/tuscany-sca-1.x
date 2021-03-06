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

package org.apache.tuscany.sca.binding.ws.wsdlgen;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.OperationType;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.PortType;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.databinding.DataBinding;
import org.apache.tuscany.sca.databinding.DataBindingExtensionPoint;
import org.apache.tuscany.sca.databinding.XMLTypeHelper;
import org.apache.tuscany.sca.databinding.jaxb.JAXBDataBinding;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.Interface;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.interfacedef.impl.DataTypeImpl;
import org.apache.tuscany.sca.interfacedef.java.JavaInterface;
import org.apache.tuscany.sca.interfacedef.java.JavaOperation;
import org.apache.tuscany.sca.interfacedef.util.ElementInfo;
import org.apache.tuscany.sca.interfacedef.util.JavaXMLMapper;
import org.apache.tuscany.sca.interfacedef.util.TypeInfo;
import org.apache.tuscany.sca.interfacedef.util.WrapperInfo;
import org.apache.tuscany.sca.interfacedef.util.XMLType;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLDefinition;
import org.apache.tuscany.sca.interfacedef.wsdl.WSDLInterface;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;
import org.apache.tuscany.sca.monitor.impl.ProblemImpl;
import org.apache.tuscany.sca.xsd.XSDFactory;
import org.apache.tuscany.sca.xsd.XSDefinition;
import org.apache.tuscany.sca.xsd.impl.XSDefinitionImpl;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.utils.NamespaceMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @version $Rev: 670103 $ $Date: 2008-06-21 01:35:00 +0100 (Sat, 21 Jun 2008) $
 */
public class Interface2WSDLGenerator {
    private static final Logger logger = Logger.getLogger(Interface2WSDLGenerator.class.getName());
    private static final String SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";
    private static final String SCHEMA_NAME = "schema";
    private static final QName SCHEMA_QNAME = new QName(SCHEMA_NS, SCHEMA_NAME);
    private static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
    
    private static final String ANYTYPE_NAME = "anyType";
    private static final QName ANYTYPE_QNAME = new QName(SCHEMA_NS, ANYTYPE_NAME);


    private WSDLFactory factory;
    private DataBindingExtensionPoint dataBindings;
    private WSDLDefinitionGenerator definitionGenerator;
    private boolean requiresSOAP12;
    private ModelResolver resolver;
    private XSDFactory xsdFactory;
    private Monitor monitor;

    public Interface2WSDLGenerator(boolean requiresSOAP12,
                                   ModelResolver resolver,
                                   DataBindingExtensionPoint dataBindings,
                                   XSDFactory xsdFactory,
                                   Monitor monitor) throws WSDLException {
        super();
        this.requiresSOAP12 = requiresSOAP12; 
        this.resolver = resolver; 
        definitionGenerator = new WSDLDefinitionGenerator(requiresSOAP12, false);
        this.dataBindings = dataBindings;
        this.xsdFactory = xsdFactory;
        this.monitor = monitor;
        this.factory = WSDLFactory.newInstance();
    }

    /**
     * Log a warning message.
     * @param problem
     */
    private static void logWarning(Problem problem) {
        Logger problemLogger = Logger.getLogger(problem.getSourceClassName(), problem.getBundleName());
        if (problemLogger != null){
            problemLogger.logp(Level.WARNING, problem.getSourceClassName(), null, problem.getMessageId(), problem.getMessageParams());
        } else {
            logger.severe("Can't get logger " + problem.getSourceClassName()+ " with bundle " + problem.getBundleName());
        }
    }

    /**
     * Report a warning.
     * @param message
     * @param binding
     * @param parameters
     */
    private void warning(String message, Interface interfaze, String... messageParameters) {
        Problem problem = new ProblemImpl(this.getClass().getName(), "wsdlgen-validation-messages", Severity.WARNING, interfaze, message, (Object[])messageParameters);
        if (monitor != null) {
            monitor.problem(problem);
        } else {
            logWarning(problem);
        }
    }

    /**
     * Report a fatal error.
     * @param message
     * @param binding
     * @param parameters
     */
    private void fatal(String message, Interface interfaze, String... messageParameters) {
        Problem problem = new ProblemImpl(this.getClass().getName(), "wsdlgen-validation-messages", Severity.ERROR, interfaze, message, (Object[])messageParameters);
        throw new WSDLGenerationException(problem.toString(), null, problem);
    }
    
    private XMLTypeHelper getTypeHelper(DataType type, Map<String, XMLTypeHelper> helpers) {
        if (type == null) {
            return null;
        }
        String db = type.getDataBinding();
        if (db == null) {
            return null;
        }

        // TUSCANY-3800
        while ("java:array".equals(db)) {
            type = (DataType)type.getLogical();
            db = type.getDataBinding();
        }
        return helpers.get(db);
    }
    
    private boolean inputTypesCompatible(DataType wrapperType, DataType<List<DataType>> inputType, Map<String, XMLTypeHelper> helpers) {
        XMLTypeHelper wrapperHelper = getTypeHelper(wrapperType, helpers);
        for (DataType dt : inputType.getLogical()) {
            if (getTypeHelper(dt, helpers) != wrapperHelper) {
                return false;
            }
        }
        return true;
    }
    
    private boolean outputTypeCompatible(DataType wrapperType, DataType outputType, Map<String, XMLTypeHelper> helpers) {
        if (getTypeHelper(outputType, helpers) != getTypeHelper(wrapperType, helpers)) {
            return false;
        } else {
            return true;
        }
    }
    
    private void addDataType(Map<XMLTypeHelper, List<DataType>> map, DataType type, Map<String, XMLTypeHelper> helpers) {
        if (type == null) {
            return;
        }
        String db = type.getDataBinding();
        if (db == null) {
            return;
        }
        if ("java:array".equals(db)) {
            DataType dt = (DataType)type.getLogical();
            db = dt.getDataBinding();
        }
        XMLTypeHelper helper = helpers.get(db);
        List<DataType> types = map.get(helper);
        if (types == null) {
            types = new ArrayList<DataType>();
            map.put(helper, types);
        }
        types.add(type);
    }
    
    private Map<XMLTypeHelper, List<DataType>> getDataTypes(Interface intf, boolean useWrapper, Map<String, XMLTypeHelper> helpers) {
        Map<XMLTypeHelper, List<DataType>> dataTypes = new HashMap<XMLTypeHelper, List<DataType>>();
        for (Operation op : intf.getOperations()) {
            WrapperInfo inputWrapper = op.getInputWrapper();
            DataType dt1 = null;
            boolean useInputWrapper = useWrapper & inputWrapper != null;
            if (useInputWrapper) {
                dt1 = inputWrapper.getWrapperType();
                useInputWrapper &= inputTypesCompatible(dt1, op.getInputType(), helpers);
            }
            if (useInputWrapper) {
                addDataType(dataTypes, dt1, helpers);
            } else {
                for (DataType dt : op.getInputType().getLogical()) {
                    addDataType(dataTypes, dt, helpers);
                }
            }
            
            DataType dt2 = null;
            WrapperInfo outputWrapper = op.getOutputWrapper();
            boolean useOutputWrapper = useWrapper & outputWrapper != null;
            if (useOutputWrapper) {
                dt2 = outputWrapper.getWrapperType();
                useOutputWrapper &= outputTypeCompatible(dt2, op.getOutputType(), helpers);
            }
            if (useOutputWrapper) {
                addDataType(dataTypes, dt2, helpers);
            } else {
                dt2 = op.getOutputType();
                addDataType(dataTypes, dt2, helpers);
            }
            
            for (DataType<DataType> dt3 : op.getFaultTypes()) {
                DataType dt4 = dt3.getLogical();
                addDataType(dataTypes, dt4, helpers);
            }
        }
        // Adding classes referenced by @XmlSeeAlso in the java interface
        if (intf instanceof JavaInterface) {
            JavaInterface javaInterface = (JavaInterface)intf;
            Class<?>[] seeAlso = getSeeAlso(javaInterface.getJavaClass());
            if (seeAlso != null) {
                for (Class<?> cls : seeAlso) {
                    DataType dt = new DataTypeImpl<XMLType>(JAXBDataBinding.NAME, cls, XMLType.UNKNOWN);
                    addDataType(dataTypes, dt, helpers);
                }
            }
            seeAlso = getSeeAlso(javaInterface.getCallbackClass());
            if (seeAlso != null) {
                for (Class<?> cls : seeAlso) {
                    DataType dt = new DataTypeImpl<XMLType>(JAXBDataBinding.NAME, cls, XMLType.UNKNOWN);
                    addDataType(dataTypes, dt, helpers);
                }
            }
        }
        return dataTypes;
    }
    
    private static Class<?>[] getSeeAlso(Class<?> interfaze) {
        if (interfaze == null) {
            return null;
        }
        XmlSeeAlso seeAlso = interfaze.getAnnotation(XmlSeeAlso.class);
        if (seeAlso == null) {
            return null;
        } else {
            return seeAlso.value();
        }
    }


    public Definition generate(Interface interfaze, WSDLDefinition wsdlDefinition) throws WSDLException {
        if (interfaze == null) {
            return null;
        }
        if (interfaze instanceof WSDLInterface) {
            return ((WSDLInterface)interfaze).getWsdlDefinition().getDefinition();
        }
        JavaInterface iface = (JavaInterface)interfaze;
        if (!interfaze.isRemotable()) {
            fatal("InterfaceNotRemotable", interfaze, iface.getName());
        }
        QName name = getQName(iface);
        Definition definition = factory.newDefinition();
        if (requiresSOAP12) {
            definition.addNamespace("SOAP12", "http://schemas.xmlsoap.org/wsdl/soap12/");
        } else {
            definition.addNamespace("SOAP", "http://schemas.xmlsoap.org/wsdl/soap/");
        }
        definition.addNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/");
        definition.addNamespace("xs", SCHEMA_NS);

        String namespaceURI = name.getNamespaceURI();
        definition.setTargetNamespace(namespaceURI);
        definition.setQName(new QName(namespaceURI, name.getLocalPart() + "Service", name.getPrefix()));
        definition.addNamespace(name.getPrefix(), namespaceURI);

        PortType portType = definition.createPortType();
        portType.setQName(name);
        Binding binding = definitionGenerator.createBinding(definition, portType);
        Map<String, XMLTypeHelper> helpers = new HashMap<String, XMLTypeHelper>();
        Map<QName, List<ElementInfo>> wrappers = new HashMap<QName, List<ElementInfo>>();
        for (Operation op : interfaze.getOperations()) {
            javax.wsdl.Operation operation = generateOperation(definition, op, helpers, wrappers);
            portType.addOperation(operation);
            String action = ((JavaOperation)op).getAction();
            if ((action == null || "".equals(action)) && !op.isInputWrapperStyle() && op.getInputWrapper() == null) {
                // Bare style
                action = "urn:" + op.getName();
            }
            BindingOperation bindingOp = definitionGenerator.createBindingOperation(definition, operation, action);
            binding.addBindingOperation(bindingOp);
        }
        portType.setUndefined(false);
        definition.addPortType(portType);
        binding.setUndefined(false);
        definition.addBinding(binding);
        wsdlDefinition.setBinding(binding);
        
        // call each helper in turn to populate the wsdl.types element
        XmlSchemaCollection schemaCollection = new XmlSchemaCollection(); 

        // TUSCANY-3298: enable JAXB wrapper generation
        for (Map.Entry<XMLTypeHelper, List<DataType>> en: getDataTypes(interfaze, true, helpers).entrySet()) {
            XMLTypeHelper helper = en.getKey();
            if (helper == null) {
                continue;
            }
            List<XSDefinition> xsDefinitions = helper.getSchemaDefinitions(xsdFactory, resolver, en.getValue());
            
            for (XSDefinition xsDef: xsDefinitions) {

                Document doc = xsDef.getDocument();
                if (doc != null) {                    
                    // TUSCANY-2757 and TUSCANY-3267 - flip global wrapper elements with nillable 
                    // set true to be set to false.  The JAXB RI seems to be generating this setting
                    // incorrectly according to the JAXB spec.                    
                    NodeList nodes = doc.getFirstChild().getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Node aNode = nodes.item(i);
                        if (aNode.getLocalName() != null && aNode.getLocalName().equals("element")) {
                            NamedNodeMap attributes = aNode.getAttributes();
                            Node nameAttr = attributes.getNamedItem("name");
                            if (nameAttr != null) { 
                                QName elementName = new QName(xsDef.getNamespace(), nameAttr.getNodeValue());
                                if (wrappers.containsKey(elementName)) {
                                    Node nillable = attributes.getNamedItem("nillable");
                                    if (nillable != null) {
                                        nillable.setNodeValue("false");
                                    }
                                }
                            }
                        }
                    }
                }
                
                loadXSD(schemaCollection, xsDef);
                wsdlDefinition.getXmlSchemas().add(xsDef);
            }
        }

        // remove global wrapper elements with schema definitions from generation list
        for (QName wrapperName: new HashSet<QName>(wrappers.keySet())) {
            if (wsdlDefinition.getXmlSchemaElement(wrapperName) != null) {
                wrappers.remove(wrapperName);
            }
        }
        
        schemaCollection = new XmlSchemaCollection(); 

        // generate schema elements for wrappers that aren't defined in the schemas
        if (wrappers.size() > 0) {
            int i = 0;
            int index = 0;
            Map<String, XSDefinition> wrapperXSDs = new HashMap<String, XSDefinition>();
            Map<Element, Map<String, String>> prefixMaps = new HashMap<Element, Map<String, String>>();
            for (Map.Entry<QName, List<ElementInfo>> entry: wrappers.entrySet()) {
                String targetNS = entry.getKey().getNamespaceURI();
                Document schemaDoc = null;
                Element schema = null;
                XSDefinition xsDef = wrapperXSDs.get(targetNS);
                if (xsDef != null) {
                    schemaDoc = xsDef.getDocument();
                    schema = schemaDoc.getDocumentElement();
                } else {
                    xsDef = wsdlDefinition.getSchema(targetNS);
                    if (xsDef != null) {
                        schemaDoc = xsDef.getDocument();
                        schema = schemaDoc.getDocumentElement();
                        wrapperXSDs.put(targetNS, xsDef);
                        Map<String, String> prefixMap = prefixMaps.get(schema);
                        if (prefixMap == null){
                            prefixMap = new HashMap<String, String>();
                            prefixMaps.put(schema, prefixMap);
                            String [] prefixes = xsDef.getSchema().getNamespaceContext().getDeclaredPrefixes();
                            for (int j = 0; j < prefixes.length; j++){
                                prefixMap.put(xsDef.getSchema().getNamespaceContext().getNamespaceURI(prefixes[j]),
                                              prefixes[j]);
                            }
                        }                        
                    } else {
                        schemaDoc = createDocument();
                        schema = schemaDoc.createElementNS(SCHEMA_NS, "xs:schema");
                        // The elementFormDefault should be set to unqualified, see TUSCANY-2388
                        schema.setAttribute("elementFormDefault", "unqualified");
                        schema.setAttribute("attributeFormDefault", "qualified");
                        schema.setAttribute("targetNamespace", targetNS);
                        schema.setAttributeNS(XMLNS_NS, "xmlns:xs", SCHEMA_NS);
                        schema.setAttributeNS(XMLNS_NS, "xmlns:tns", targetNS);
                        schemaDoc.appendChild(schema);
                        prefixMaps.put(schema, new HashMap<String, String>());
                        xsDef = xsdFactory.createXSDefinition();
                        xsDef.setUnresolved(true);
                        xsDef.setNamespace(targetNS);
                        xsDef.setDocument(schemaDoc);
                        // TUSCANY-2465: Set the system id to avoid schema conflict
                        xsDef.setLocation(URI.create("xsd_" + index + ".xsd"));
                        index++;
                        wrapperXSDs.put(targetNS, xsDef);
                    }  
                }
                Element wrapper = schemaDoc.createElementNS(SCHEMA_NS, "xs:element");
                schema.appendChild(wrapper);
                wrapper.setAttribute("name", entry.getKey().getLocalPart());
                if (entry.getValue().size() == 1 && entry.getValue().get(0).getQName() == null) {
                    // special case for global fault element
                    QName typeName = entry.getValue().get(0).getType().getQName();
                    String nsURI = typeName.getNamespaceURI();
                    if ("".equals(nsURI)) {
                        wrapper.setAttribute("type", typeName.getLocalPart());
                        addSchemaImport(schema, "", schemaDoc);
                    } else if (targetNS.equals(nsURI)) {
                        wrapper.setAttribute("type", typeName.getLocalPart());
                    } else if (SCHEMA_NS.equals(nsURI)) {
                        wrapper.setAttribute("type", "xs:" + typeName.getLocalPart());
                    } else {
                        Map<String, String> prefixMap = prefixMaps.get(schema);
                        String prefix = prefixMap.get(nsURI);
                        if (prefix == null) {
                            prefix = "ns" + i++;
                            while(prefixMap.containsValue(prefix)){
                                prefix = "ns" + i++;
                            }
                            prefixMap.put(nsURI, prefix);
                            schema.setAttributeNS(XMLNS_NS, "xmlns:" + prefix, nsURI);
                            addSchemaImport(schema, nsURI, schemaDoc);
                        }
                        wrapper.setAttribute("type", prefix + ":" + typeName.getLocalPart());
                    }                    
                } else {
                    // normal wrapper containing type definition inline
                    Element complexType = schemaDoc.createElementNS(SCHEMA_NS, "xs:complexType");
                    wrapper.appendChild(complexType);
                    if (entry.getValue().size() > 0) {
                        Element sequence = schemaDoc.createElementNS(SCHEMA_NS, "xs:sequence");
                        complexType.appendChild(sequence);
                        for (ElementInfo element: entry.getValue()) {
                            Element xsElement = schemaDoc.createElementNS(SCHEMA_NS, "xs:element"); 
                            if (element.isMany()) {
                                xsElement.setAttribute("maxOccurs", "unbounded");
                            }
                            xsElement.setAttribute("minOccurs", "0");
                            xsElement.setAttribute("name", element.getQName().getLocalPart());
                            if (element.isNillable()) {
                                xsElement.setAttribute("nillable", "true");
                            }
                            QName typeName = element.getType().getQName();
                            String nsURI = typeName.getNamespaceURI();
                            if ("".equals(nsURI)) {
                                xsElement.setAttribute("type", typeName.getLocalPart());
                                addSchemaImport(schema, "", schemaDoc);
                            } else if (SCHEMA_NS.equals(nsURI)) {
                                xsElement.setAttribute("type", "xs:" + typeName.getLocalPart());
                            } else {
                                Map<String, String> prefixMap = prefixMaps.get(schema);
                                String prefix = prefixMap.get(nsURI);
                                if (prefix == null) {
                                    if (targetNS.equals(nsURI)) {
									    prefix = "tns";
									} else {
                                        prefix = "ns" + i++;
                                        while(prefixMap.containsValue(prefix)){
                                            prefix = "ns" + i++;
                                        }
                                        addSchemaImport(schema, nsURI, schemaDoc);
									}
                                    prefixMap.put(nsURI, prefix);
                                    schema.setAttributeNS(XMLNS_NS, "xmlns:" + prefix, nsURI);
                                }
                                xsElement.setAttribute("type", prefix + ":" + typeName.getLocalPart());
                            }
                            sequence.appendChild(xsElement);
                        }
                    }
                }
            }
 
            // resolve XSDefinitions containing generated wrappers
            for (XSDefinition xsDef: wrapperXSDs.values()) {
                if (wsdlDefinition.getSchema(xsDef.getNamespace()) == null) {
                     wsdlDefinition.getXmlSchemas().add(xsDef);
                }
                
                xsDef.setSchema(null);
                xsDef.setSchemaCollection(null);
                loadXSD(schemaCollection, xsDef);
            }
        }
        
        XSDefinition noNamespaceSchema = null;
        XSDefinition defaultNamespaceSchema = null;     

        for (XSDefinition xsDef: wsdlDefinition.getXmlSchemas()) {
            // only apply these changes if we have a DOM to work with
            // this will generally come from the JAXB generation. SDO
            // just goes straight to generating XmlSchema structures
            Document doc = xsDef.getDocument();
            if (doc != null) {
                // TUSCANY-3283 find any schema that has been generated without a namespace
                //              and any schema using the default WSDL schema so that we can
                //              merge the two together              
                if (xsDef.getNamespace().equals("")){
                    noNamespaceSchema = xsDef;
                }
                if (xsDef.getNamespace().equals(namespaceURI)){
                    defaultNamespaceSchema = xsDef;
                } 
            }
        }    
        
        // useful for debugging DOM issues
        //System.out.println("======================= Schema DOM Pre Merge=======================" );
        //for (XSDefinition xsDef : wsdlDefinition.getXmlSchemas()) {
        //    if (xsDef.getDocument() != null) {
        //        printDOM(xsDef.getDocument());
        //    }
        //}          
        
        // TUSCANY-3283 merge the no namespace schema into the default namespace schema
        if (noNamespaceSchema != null && defaultNamespaceSchema != null){
            // remove the no namespace schema from our list of schema
            wsdlDefinition.getXmlSchemas().remove(noNamespaceSchema);
            // merge the schema with no namespace into the schema with the default namspace for this WSDL
            mergeSchema(noNamespaceSchema, defaultNamespaceSchema, wsdlDefinition.getXmlSchemas());
            
            // useful for debugging DOM issues
            //System.out.println("======================= Schema DOM Post Merge=======================" );
            //for (XSDefinition xsDef : wsdlDefinition.getXmlSchemas()) {
            //    if (xsDef.getDocument() != null) {
            //        printDOM(xsDef.getDocument());
            //    }
            //}            
            
            schemaCollection = new XmlSchemaCollection();
            defaultNamespaceSchema.setSchema(null);
            defaultNamespaceSchema.setSchemaCollection(null);
            loadXSD(schemaCollection, defaultNamespaceSchema);
        }         
        
        // push the schema into the WSDL 
        for (XSDefinition xsDef: wsdlDefinition.getXmlSchemas()){
            addSchemaExtension(xsDef, schemaCollection, wsdlDefinition, definition);
        }
        
        // useful for debugging DOM issues
        //System.out.println("======================= Schema DOM Process End =======================" );
        //for (XSDefinition xsDef : wsdlDefinition.getXmlSchemas()) {
        //    if (xsDef.getDocument() != null) {
        //        printDOM(xsDef.getDocument());
        //    }
        //}        
        
        return definition;
    }        
   
    /**
     * TUSCANY-3283 
     * Merge the no namespace schema into the defualt namespace schema
     * Relies on being called just after the getSchemaDefinitions call when the XSDefinitions 
     * have only the DOM information set
     * 
     * @param noNamespaceSchema
     * @param defaultNamespaceSchema
     * @param xsDefinitions
     */
    private void mergeSchema(XSDefinition noNamespaceSchema, XSDefinition defaultNamespaceSchema, Collection<XSDefinition> xsDefinitions){
        Document fromDoc = noNamespaceSchema.getDocument();
        Document toDoc = defaultNamespaceSchema.getDocument();
          
        // merge namespace definitions from the nonamespace schema into the default namespace schema
        for(int i = 0; i < fromDoc.getDocumentElement().getAttributes().getLength(); i++){
            Attr attribute = (Attr)fromDoc.getDocumentElement().getAttributes().item(i);
            String attribName = attribute.getName();
            if (attribName.startsWith("xmlns:")){
                
                String fromPrefix = attribName.substring(attribName.indexOf(":") + 1);
                
                if (fromPrefix.equals("xs") != true){        
                    // create a new namespace prefix to prevent clashes
                    toDoc.getDocumentElement().setAttributeNS("http://www.w3.org/2000/xmlns/", 
                                                              "xmlns:__" + fromPrefix, 
                                                              attribute.getValue());
                    
                    // fix up any references to the existing namespace prefix
                    fixUpNoNamespaceNamespaces(fromDoc, fromPrefix);
                }
            }
        }
        
        Node toDocFirstChild = toDoc.getDocumentElement().getFirstChild();
        
        // merge types from the no namespace schema into the default namespace schema
        for(int i = 0; i < fromDoc.getDocumentElement().getChildNodes().getLength(); i++){
            // merge the DOM types
            Node node = fromDoc.getDocumentElement().getChildNodes().item(i);
            Node newNode = toDoc.importNode(node, true);
            
            if (newNode.getLocalName() != null && 
                (newNode.getLocalName().equals("import") || 
                 newNode.getLocalName().equals("include") ||
                 newNode.getLocalName().equals("redefine") ||
                 newNode.getLocalName().equals("annotation") )){
            	toDoc.getDocumentElement().insertBefore(newNode, toDocFirstChild);
            } else {
            	toDoc.getDocumentElement().appendChild(newNode);
            }
        }
        
        // fix up any references to the types we've just moved
        for(int i = 0; i < fromDoc.getDocumentElement().getChildNodes().getLength(); i++){
            Node node = fromDoc.getDocumentElement().getChildNodes().item(i);
            
            // correct any references to no name types in other schema
            if (node.getLocalName() != null && 
                node.getLocalName().equals("complexType")){
                Node typeName = node.getAttributes().getNamedItem("name");
                fixUpNoNamespaceReferences(xsDefinitions, typeName.getNodeValue(), defaultNamespaceSchema.getNamespace());
            }
        }
    }
    
    /**
     * TUSCANY-3283 
     * Correct any namespace prefixes in the no namespace schema to prevent them from 
     * clashing when the no namespace schema is merged with the default schema
     * 
     * @param fromSchema
     * @param fromPrefix
     * @param toPrefix
     */   
    private void fixUpNoNamespaceNamespaces(Document fromSchema, String fromPrefix){
        NodeList elements = fromSchema.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema","element");
        for (int k = 0; k < elements.getLength(); k++){
            Element element = (Element) elements.item(k);
            if (element != null && element.getAttributes() != null) {
                Node type = element.getAttributes().getNamedItem("type");
                
                if (type != null &&
                    type.getNodeValue().startsWith(fromPrefix)){
                    type.setNodeValue("__" + type.getNodeValue());
               }
            }
        }
    }

    /**
     * TUSCANY-3283 
     * Correct any references in the schema list that used to point to types in the
     * no namespace schema
     * 
     * @param fromSchema
     * @param toSchema
     */  
    private void fixUpNoNamespaceReferences(Collection<XSDefinition> xsDefinitions, String typeName, String defaultNamespace){
        
        // fix up any references in any other schema that points to this type
        for (XSDefinition xsDef: xsDefinitions) {

        	if (xsDef.getDocument() != null){
	            // look for any imports of the no namespace schema
	            Document refSchema = xsDef.getDocument();
	            NodeList imports = refSchema.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema","import");
	            List<Node> importsToDelete = new ArrayList<Node>();
	            
	            for (int j = 0; j < imports.getLength(); j++){
	                Element _import = (Element)imports.item(j);
	                
	                // Fix up the import
	                if (_import.getAttributes().getLength() == 0){
	                    if (xsDef.getNamespace().equals(defaultNamespace)){
	                        // remove the import
	                        //_import.getParentNode().removeChild(_import);
	                        importsToDelete.add(_import);
	                    } else {
	                        // update the import to refer to the default namespace
	                        _import.setAttribute("namespace", defaultNamespace);
	                    }
	                }
	            }
	            
	            for (Node _import : importsToDelete){
	                _import.getParentNode().removeChild(_import);
	            }
	
	            // TUSCANY-3859: Look for any attributes that refer to the node being merged
                fixUpNoNamespaceAttributes("element", "type", xsDef, typeName, defaultNamespace);
                fixUpNoNamespaceAttributes("extension", "base", xsDef, typeName, defaultNamespace);
        	}
        }
    }

    /**
     * TUSCANY-3859 
     * Correct any schema attributes that used to point to types in the no namespace schema
     * 
     * @param elementName
     * @param attributeName
     * @param xsDef
     * @param defaultNamespace
     */  
    private void fixUpNoNamespaceAttributes(String elementName, String attributeName,
                                            XSDefinition xsDef, String typeName, String defaultNamespace) {
        Document refSchema = xsDef.getDocument();
        NodeList elements = refSchema.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", elementName);
        for (int k = 0; k < elements.getLength(); k++){
            Element element = (Element) elements.item(k);
            if (element != null && element.getAttributes() != null) {
                Node type = element.getAttributes().getNamedItem(attributeName);
                
                if (type != null &&
                    type.getNodeValue().equals(typeName)){
                    if (xsDef.getNamespace().equals(defaultNamespace)){
                        // double check that there is a "tns" namespace shortname specified
                        String tnsNamespace = refSchema.getDocumentElement().getAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:tns");
                        
                        if (tnsNamespace == null || tnsNamespace.length() == 0) {
                            refSchema.getDocumentElement().setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:tns", defaultNamespace);
                        }
                        
                        // just add "tns" in front of the type name as
                        // we have merged the type into this schema
                        type.setNodeValue("tns:" + type.getNodeValue());
                    } else {
                        // add a namespace 
                        refSchema.getDocumentElement().setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:__nnns", defaultNamespace);
    
                        // prefix the type name with the namespace
                        type.setNodeValue("__nnns:" + type.getNodeValue());
                    }
                }
            }
        }
    }

    /*
     * Just used when debugging DOM problems
     */
    private void printDOM(Document document){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(document);
            Result result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (Exception ex){
            ex.toString();
        }
    }
    
    private static void addSchemaImport(Element schema, String nsURI, Document schemaDoc) {
        Element imp = schemaDoc.createElementNS(SCHEMA_NS, "xs:import");
        if (!"".equals(nsURI)) {
            imp.setAttribute("namespace", nsURI);
        }
        // Scan all xs:import elements to match namespace
        NodeList childNodes = schema.getElementsByTagNameNS(SCHEMA_NS, "import");
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                String ns = ((Element)childNode).getAttributeNS(SCHEMA_NS, "namespace");
                if (nsURI.equals(ns)) {
                    // The xs:import with the same namespace has been declared
                    return;
                }
            }
        }
        // Try to find the first node after the import elements
        Node firstNodeAfterImport = null;
        if (childNodes.getLength() > 0) {
            firstNodeAfterImport = childNodes.item(childNodes.getLength() - 1).getNextSibling();
        } else {
            firstNodeAfterImport = schema.getFirstChild();
        }

        if (firstNodeAfterImport == null) {
            schema.appendChild(imp);
        } else {
            schema.insertBefore(imp, firstNodeAfterImport);
        }
    }

    private void addSchemaExtension(XSDefinition xsDef,
                                    XmlSchemaCollection schemaCollection,
                                    WSDLDefinition wsdlDefinition,
                                    Definition definition) throws WSDLException {
        if (xsDef.getAggregatedDefinitions() != null) {
            for (XSDefinition xsd: xsDef.getAggregatedDefinitions()) {
                addSchemaExtension(xsd, schemaCollection, wsdlDefinition, definition);
            }
        } else {
            String nsURI = xsDef.getNamespace();
            Document document = xsDef.getDocument();
            if (document == null) {
                try {
                    NamespaceMap prefixMap = new NamespaceMap();
                    prefixMap.add("xs", SCHEMA_NS);
                    prefixMap.add("tns", nsURI);
                    XmlSchema schemaDef = xsDef.getSchema();
                    schemaDef.setNamespaceContext(prefixMap);
                    Document[] docs = schemaDef.getAllSchemas();
                    document = docs[docs.length-1];
                    document.setDocumentURI(xsDef.getLocation().toString());
                    xsDef.setDocument(document);
                } catch (XmlSchemaException e) {
                    throw new RuntimeException(e);
                }
            }
            //loadXSD(schemaCollection, xsDef);
            //wsdlDefinition.getXmlSchemas().add(xsDef);
            Element schema = document.getDocumentElement();
            Schema schemaExt = createSchemaExt(definition);
            schemaExt.setDocumentBaseURI(document.getDocumentURI());
            schemaExt.setElement(schema);
        }
    }

    private static void loadXSD(XmlSchemaCollection schemaCollection, XSDefinition definition) {
        if (definition.getSchema() != null) {
            return;
        }
        if (definition.getDocument() != null) {
            String uri = null;
            if (definition.getLocation() != null) {
                uri = definition.getLocation().toString();
            }
            XmlSchema schema = schemaCollection.read(definition.getDocument(), uri, null);
            if (definition.getSchemaCollection() == null) {
                definition.setSchemaCollection(schemaCollection);
            }
            if (definition.getSchema() == null) {
                definition.setSchema(schema);
            }
        }
    }

    public Schema createSchemaExt(Definition definition) throws WSDLException {
        Types types = definition.getTypes();
        if (types == null) {
            types = definition.createTypes();
            definition.setTypes(types);
        }

        Schema schemaExt = createSchema(definition);
        types.addExtensibilityElement(schemaExt);

        return schemaExt;
    }

    public Schema createSchema(Definition definition) throws WSDLException {
        return (Schema)definition.getExtensionRegistry().createExtension(Types.class, SCHEMA_QNAME);
    }

    private DocumentBuilderFactory documentBuilderFactory;

    public Document createDocument() {
        Document document;
        try {
            if (documentBuilderFactory == null) {
                documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilderFactory.setNamespaceAware(true);
            }
            document = documentBuilderFactory.newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            throw new WSDLGenerationException(ex);
         }
        // document.setDocumentURI("http://");
        return document;
    }

    protected QName getQName(Interface interfaze) {
        JavaInterface iface = (JavaInterface)interfaze;
        QName qname = iface.getQName();
        if (qname != null) {
            return qname;
        } else {
            Class<?> javaClass = iface.getJavaClass();
            return new QName(JavaXMLMapper.getNamespace(javaClass), javaClass.getSimpleName(), "tns");
        }
    }

    public javax.wsdl.Operation generateOperation(Definition definition,
                                                  Operation op,
                                                  Map<String, XMLTypeHelper> helpers,
                                                  Map<QName, List<ElementInfo>> wrappers)
                                              throws WSDLException {
        javax.wsdl.Operation operation = definition.createOperation();
        operation.setName(op.getName());
        operation.setUndefined(false);

        Input input = definition.createInput();
        Message inputMsg = definition.createMessage();
        String namespaceURI = definition.getQName().getNamespaceURI();
        QName inputMsgName = new QName(namespaceURI, op.getName());
        inputMsg.setQName(inputMsgName);
        inputMsg.setUndefined(false);
        definition.addMessage(inputMsg);

        List<ElementInfo> elements = null;
        // FIXME: By default, java interface is mapped to doc-lit-wrapper style WSDL
        if (op.getInputWrapper() != null) {
            // Generate doc-lit-wrapper style
            inputMsg.addPart(generateWrapperPart(definition, op, helpers, wrappers, true));
        } else {
            // Bare style
            int i = 0;
            for (DataType d : op.getInputType().getLogical()) {
                inputMsg.addPart(generatePart(definition, d, "arg" + i));
                elements = new ArrayList<ElementInfo>();
                ElementInfo element = getElementInfo(d.getPhysical(), d, null, helpers);
                elements.add(element);
                QName elementName = ((XMLType)d.getLogical()).getElementName();
                wrappers.put(elementName, elements);
                i++;
            }
        }
        input.setMessage(inputMsg);
        operation.setInput(input);

        if (!op.isNonBlocking()) {
            Output output = definition.createOutput();
            Message outputMsg = definition.createMessage();
            QName outputMsgName = new QName(namespaceURI, op.getName() + "Response");
            outputMsg.setQName(outputMsgName);
            outputMsg.setUndefined(false);
            definition.addMessage(outputMsg);

            if (op.getOutputWrapper() != null) {
                outputMsg.addPart(generateWrapperPart(definition, op, helpers, wrappers, false));
            } else {
                DataType outputType = op.getOutputType();
                if (outputType != null) {
                    outputMsg.addPart(generatePart(definition, outputType, "return"));
                    elements = new ArrayList<ElementInfo>();
                    ElementInfo element = getElementInfo(outputType.getPhysical(), outputType, null, helpers);
                    elements.add(element);
                    QName elementName = ((XMLType)outputType.getLogical()).getElementName();
                    wrappers.put(elementName, elements);
                }
            }
            output.setMessage(outputMsg);

            operation.setOutput(output);
            operation.setStyle(OperationType.REQUEST_RESPONSE);
        } else {
            operation.setStyle(OperationType.ONE_WAY);
        }

        for (DataType<DataType> faultType: op.getFaultTypes()) {
            Fault fault = definition.createFault();
            QName faultName = ((XMLType)faultType.getLogical().getLogical()).getElementName();
            QName faultMsgName = new QName(namespaceURI, faultName.getLocalPart());
            fault.setName(faultName.getLocalPart());
            Message faultMsg = definition.getMessage(faultMsgName);
            if (faultMsg == null) {
                faultMsg = definition.createMessage();
                faultMsg.setQName(faultMsgName);
                faultMsg.setUndefined(false);
                definition.addMessage(faultMsg);
                faultMsg.addPart(generatePart(definition, faultType.getLogical(), faultName.getLocalPart()));
            }
            fault.setMessage(faultMsg);
            operation.addFault(fault);
            if (faultType.getLogical().getPhysical() != faultType.getPhysical()) {
                // create special wrapper for type indirection to real fault bean
                DataType logical = faultType.getLogical();
                elements = new ArrayList<ElementInfo>();
                elements.add(getElementInfo(logical.getPhysical(), logical, null, helpers));
             } else {
                // convert synthesized fault bean to a wrapper type
                for (DataType<XMLType> propDT: op.getFaultBeans().get(faultName)) {
                    XMLType logical = propDT.getLogical();
                    elements = new ArrayList<ElementInfo>();
                    elements.add(getElementInfo(propDT.getPhysical(), propDT, logical.getElementName(), helpers));
                }
            }
            wrappers.put(faultName, elements);
        }

        operation.setUndefined(false);
        return operation;
    }

    public Part generatePart(Definition definition, DataType arg, String partName) {
        Part part = definition.createPart();
        part.setName(partName);
        if (arg != null && arg.getLogical() instanceof XMLType) {
            XMLType xmlType = (XMLType)arg.getLogical();
            QName elementName = xmlType.getElementName();
            part.setElementName(elementName);
            addNamespace(definition, elementName);
            if (xmlType.getElementName() == null) {
                QName typeName = xmlType.getTypeName();
                part.setTypeName(typeName);
                addNamespace(definition, typeName);
            }
        }
        return part;
    }

    public Part generateWrapperPart(Definition definition,
                                    Operation operation,
                                    Map<String, XMLTypeHelper> helpers, 
                                    Map<QName, List<ElementInfo>> wrappers,
                                    boolean input) throws WSDLException {
        Part part = definition.createPart();
        String partName = input ? operation.getName() : (operation.getName() + "Response");
        part.setName(partName);
        WrapperInfo inputWrapperInfo = operation.getInputWrapper();
        WrapperInfo outputWrapperInfo = operation.getOutputWrapper();
        if ((inputWrapperInfo != null) && (outputWrapperInfo != null)) {
            ElementInfo elementInfo =
                input ? inputWrapperInfo.getWrapperElement() : outputWrapperInfo.getWrapperElement();
            List<ElementInfo> elements =
                input ? inputWrapperInfo.getChildElements() : outputWrapperInfo.getChildElements();
            QName wrapperName = elementInfo.getQName();
            part.setElementName(wrapperName);
            addNamespace(definition, wrapperName);
            wrappers.put(wrapperName, elements);

            // FIXME: [rfeng] Ideally, we should try to register the wrappers only. But we are
            // expriencing the problem that we cannot handle XSD imports 
            /*
            Class<?> wrapperClass = input ? opWrapper.getInputWrapperClass() : opWrapper.getOutputWrapperClass();
            DataType wrapperDT = input ? opWrapper.getInputWrapperType() : opWrapper.getOutputWrapperType();
            if (wrapperClass != null) {
                getElementInfo(wrapperClass, wrapperDT, wrapperName, helpers);
                return part;
            }
            */

            Method method = ((JavaOperation)operation).getJavaMethod();
            if (input) {
                Class<?>[] paramTypes = method.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    DataType dataType = operation.getInputType().getLogical().get(i);
                    elements.set(i, getElementInfo(paramTypes[i], dataType, elements.get(i).getQName(), helpers));
                }
            } else {
                Class<?> returnType = method.getReturnType();
                if (returnType != Void.TYPE) {
                    DataType dataType = operation.getOutputType();
                    elements.set(0, getElementInfo(returnType, dataType, elements.get(0).getQName(), helpers));
                }
            }
        }
        return part;
    }

    private ElementInfo getElementInfo(Class javaType,
                                       DataType dataType,
                                       QName name,
                                       Map<String, XMLTypeHelper> helpers) {
        String db = dataType.getDataBinding();
        while ("java:array".equals(db)) {
            dataType = (DataType)dataType.getLogical();
            db = dataType.getDataBinding();
        }
        XMLTypeHelper helper = helpers.get(db);
        if (helper == null) {
            DataBinding dataBinding = dataBindings.getDataBinding(db);
            if (dataBinding == null) {
                QName element = name;
                if (element == null || dataType.getLogical() instanceof XMLType) {
                    XMLType xmlType = (XMLType)dataType.getLogical();
                    if (xmlType.getElementName() != null) {
                        element = xmlType.getElementName();
                    }
                }
                return new ElementInfo(element, new TypeInfo(ANYTYPE_QNAME, false, null));
                // throw new ServiceRuntimeException("No data binding for " + db);
            }

            helper = dataBinding.getXMLTypeHelper();
            if (helper == null) {
                // Default to JAXB
                helper = helpers.get(JAXBDataBinding.NAME);
                if (helper == null) {
                    helper = dataBindings.getDataBinding(JAXBDataBinding.NAME).getXMLTypeHelper();
                    helpers.put(JAXBDataBinding.NAME, helper);
                }
            }
            helpers.put(db, helper);
        }
        TypeInfo typeInfo = helper.getTypeInfo(byte[].class != javaType && javaType.isArray() ? javaType.getComponentType() : javaType,
                                               dataType.getLogical());
        ElementInfo element = new ElementInfo(name, typeInfo);
        element.setMany(byte[].class != javaType && javaType.isArray());
        // TUSCANY-3298: Check the "many" flag set by databinding introspection
        Object logical = dataType.getLogical();
        if (logical instanceof XMLType && ((XMLType)logical).isMany()) {
            element.setMany(true);
        }
        element.setNillable(!javaType.isPrimitive());
        return element;
    }

    private static void addNamespace(Definition definition, QName name) {
        String namespace = name.getNamespaceURI();
        if (definition.getPrefix(namespace) == null) {
            definition.addNamespace("ns" + definition.getNamespaces().size(), namespace);
        }
    }

    /*
    // currently not using the next three methods
    public XmlSchemaType getXmlSchemaType(DataType type) {
        return null;
    }

    // FIXME: WE need to add databinding-specific Java2XSD generation
    public Element generateXSD(DataType dataType) {
        DataBinding dataBinding = dataBindings.getDataBinding(dataType.getDataBinding());
        if (dataBinding != null) {
            // return dataBinding.generateSchema(dataType);
        }
        return null;
    }

    public void generateWrapperElements(Operation op) {
        XmlSchemaCollection collection = new XmlSchemaCollection();
        String ns = getQName(op.getInterface()).getNamespaceURI();
        XmlSchema schema = new XmlSchema(ns, collection);
        schema.setAttributeFormDefault(new XmlSchemaForm(XmlSchemaForm.QUALIFIED));
        schema.setElementFormDefault(new XmlSchemaForm(XmlSchemaForm.QUALIFIED));

        XmlSchemaElement inputElement = new XmlSchemaElement();
        inputElement.setQName(new QName(ns, op.getName()));
        XmlSchemaComplexType inputType = new XmlSchemaComplexType(schema);
        inputType.setName("");
        XmlSchemaSequence inputSeq = new XmlSchemaSequence();
        inputType.setParticle(inputSeq);
        List<DataType> argTypes = op.getInputType().getLogical();
        for (DataType argType : argTypes) {
            XmlSchemaElement child = new XmlSchemaElement();
            Object logical = argType.getLogical();
            if (logical instanceof XMLType) {
                child.setName(((XMLType)logical).getElementName().getLocalPart());
                XmlSchemaType type = getXmlSchemaType(argType);
                child.setType(type);
            }
            inputSeq.getItems().add(child);
        }
        inputElement.setType(inputType);

        XmlSchemaElement outputElement = new XmlSchemaElement();
        outputElement.setQName(new QName(ns, op.getName() + "Response"));
        XmlSchemaComplexType outputType = new XmlSchemaComplexType(schema);
        outputType.setName("");
        XmlSchemaSequence outputSeq = new XmlSchemaSequence();
        outputType.setParticle(outputSeq);
        DataType returnType = op.getOutputType();
        XmlSchemaElement child = new XmlSchemaElement();
        Object logical = returnType.getLogical();
        if (logical instanceof XMLType) {
            child.setName(((XMLType)logical).getElementName().getLocalPart());
            XmlSchemaType type = getXmlSchemaType(returnType);
            child.setType(type);
        }
        outputSeq.getItems().add(child);
        outputElement.setType(outputType);

        schema.getElements().add(inputElement.getQName(), inputElement);
        schema.getElements().add(outputElement.getQName(), outputElement);

    }
    */

    public WSDLFactory getFactory() {
        return factory;
    }

    public void setFactory(WSDLFactory factory) {
        this.factory = factory;
    }

}
