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

package wsdlgen.verify;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.Types;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * Test ?wsdl works and that the returned WSDL is correct
 *
 * @version $Rev: 814373 $ $Date: 2009-09-13 19:06:29 +0100 (Sun, 13 Sep 2009) $
 */
public class DataTypesTestCase {
    private static final String SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";
    private static final String SCHEMA_NAME = "schema";
    private static final QName SCHEMA_QNAME = new QName(SCHEMA_NS, SCHEMA_NAME);

    private static SCADomain domain;
    private static Map<String, Element> schemaMap;
    private static PortType portType;

    private Map<String, String> prefixMap;

    public DataTypesTestCase() {
        // create a new instance of this for each of the tests
        prefixMap = new HashMap<String, String>();
    }

    @Test
    public void testSimpleInt() throws Exception {
        assertEquals("xs:int", parameterType("testSimpleInt"));
    }

    @Test
    public void testSimpleArrayInt() throws Exception {
        Element paramElement = parameterElement("testSimpleArrayInt");
        assertEquals("xs:int", paramElement.getAttribute("type"));
        assertEquals("unbounded", paramElement.getAttribute("maxOccurs"));
    }

    @Test
    @Ignore("TUSCANY-3799")
    public void testSimpleListString() throws Exception {
        Element paramElement = parameterElement("testSimpleListString");
        assertEquals("xs:string", paramElement.getAttribute("type"));
        assertEquals("unbounded", paramElement.getAttribute("maxOccurs"));
    }

    @Test
    public void testComplex() throws Exception {
        String paramType = parameterType("testComplex");
        assertEquals("tns:complexNumber", paramType);
        assertEquals("xs:double", firstChild(typeDefinition(paramType)).getAttribute("type"));
    }

    @Test
    public void testByteArray() throws Exception {
        assertEquals("xs:base64Binary", parameterType("testByteArray"));
    }

    @Test
    public void testException() throws Exception {
        assertEquals("xs:string", faultType("testException", "Exception"));
    }

    @Test
    public void testDynamicSDO() throws Exception {
        assertEquals("xs:anyType", returnType("testDynamicSDO"));
    }

    private Element parameterElement(String methodName) {
        Operation op = portType.getOperation(methodName, null, null);
        Input in = op.getInput();
        Message msg = in.getMessage();
        Part part = msg.getPart(msg.getQName().getLocalPart());
        QName elementQName = part.getElementName();
        return schemaElement(elementQName);
    }

    private String parameterType(String methodName) {
        return parameterElement(methodName).getAttribute("type");
    }

    private Element returnElement(String methodName) {
        Operation op = portType.getOperation(methodName, null, null);
        Output out = op.getOutput();
        Message msg = out.getMessage();
        Part part = msg.getPart(msg.getQName().getLocalPart());
        QName elementQName = part.getElementName();
        return schemaElement(elementQName);
    }

    private String returnType(String methodName) {
        return returnElement(methodName).getAttribute("type");
    }

    private Element faultElement(String methodName, String faultName) {
        Operation op = portType.getOperation(methodName, null, null);
        Fault fault = op.getFault(faultName);
        Message msg = fault.getMessage();
        Part part = msg.getPart(msg.getQName().getLocalPart());
        QName elementQName = part.getElementName();
        return schemaElement(elementQName);
    }

    private String faultType(String methodName, String faultName) {
        return faultElement(methodName, faultName).getAttribute("type");
    }

    private Element schemaElement(QName elementQName) {
        // find schema definition for wrapper element
        Element schema = schemaMap.get(elementQName.getNamespaceURI());

        // find namespace prefixes for this schema definition
        NamedNodeMap attrNodes = schema.getAttributes();
        for (int i = 0; i < attrNodes.getLength(); i++) {
            Attr attr = (Attr)attrNodes.item(i);
            String attrName = attr.getName();
            if (attrName.startsWith("xmlns:")) {
                prefixMap.put(attrName.substring(6), attr.getValue());
            }
        }

        // find wrapper element definition in schema
        String elementName = elementQName.getLocalPart();
        Element wrapper = null;
        NodeList childNodes = schema.getElementsByTagNameNS(SCHEMA_NS, "element");
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                String name = ((Element)childNode).getAttribute("name");
                if (elementName.equals(name)) {
                    wrapper = (Element)childNode;
                    break;
                }
            }
        }

        // find type definition for wrapper element
        String typeName = wrapper.getAttribute("type");
        Element wrapperType = null;
        if ("".equals(typeName)) {
            childNodes = wrapper.getElementsByTagNameNS(SCHEMA_NS, "complexType");
            wrapperType = (Element)childNodes.item(0);
        } else {
            wrapperType = typeDefinition(typeName);
        }

        return firstChild(wrapperType);
    }

    private Element typeDefinition(String typeName) {
        String typePrefix = typeName.substring(0, typeName.indexOf(":"));
        String typeLocalName = typeName.substring(typeName.indexOf(":") + 1);
        Element typeSchema = schemaMap.get(prefixMap.get(typePrefix));
        Element typeElement = null;
        NodeList childNodes = typeSchema.getElementsByTagNameNS(SCHEMA_NS, "complexType");
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                String name = ((Element)childNode).getAttribute("name");
                if (typeLocalName.equals(name)) {
                    typeElement = (Element)childNode;
                    break;
                }
            }
        }
        return typeElement;
    }

    private Element firstChild(Element complexType) {
        // find xs:sequence child element
        NodeList childNodes = complexType.getElementsByTagNameNS(SCHEMA_NS, "sequence");
        Element sequence = (Element)childNodes.item(0);

        // find first xs:element child element
        childNodes = sequence.getElementsByTagNameNS(SCHEMA_NS, "element");
        return (Element)childNodes.item(0);
    }

    private static void readWSDL() throws Exception {
        if (portType == null) {
            InputStream inp = new URL("http://localhost:8085/DataTypes?wsdl").openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inp));
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();

            WSDLReader wsdlReader = WSDLFactory.newInstance().newWSDLReader();
            wsdlReader.setFeature("javax.wsdl.verbose",false);
            wsdlReader.setFeature("javax.wsdl.importDocuments",true);

            Definition definition = wsdlReader.readWSDL("http://localhost:8085/DataTypes?wsdl");
            assertNotNull(definition);

            // find portType
            Service service = definition.getService(new QName("http://verify.wsdlgen/", "DataTypesService"));
            Port port = service.getPort("DataTypesPort");
            Binding binding = port.getBinding();
            portType = binding.getPortType();

            // find schema definitions
            Types types = definition.getTypes();
            schemaMap = new HashMap<String, Element>();
            for (Object ext : types.getExtensibilityElements()) {
                ExtensibilityElement extElement = (ExtensibilityElement)ext;
                if (SCHEMA_QNAME.equals(extElement.getElementType())) {
                    if (extElement instanceof Schema) {
                        Element schemaElement = ((Schema)extElement).getElement();
                        schemaMap.put(schemaElement.getAttribute("targetNamespace"), schemaElement);
                    }
                }
            }
        }
    }

    /*
     * Used for debugging DOM problems
     */
    private void printDOM(Node node){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(node);
            Result result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        domain = SCADomain.newInstance("DataTypes.composite");
        readWSDL();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        domain.close();
    }
}
