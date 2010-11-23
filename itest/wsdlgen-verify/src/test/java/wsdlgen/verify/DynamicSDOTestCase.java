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

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.wsdl.Operation;
import javax.wsdl.Output;
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

import junit.framework.TestCase;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * Test ?wsdl works and that the returned WSDL has the correct endpoint
 *
 * @version $Rev: 814373 $ $Date: 2009-09-13 19:06:29 +0100 (Sun, 13 Sep 2009) $
 */
public class DynamicSDOTestCase extends TestCase {
    private static final String SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";
    private static final String SCHEMA_NAME = "schema";
    private static final QName SCHEMA_QNAME = new QName(SCHEMA_NS, SCHEMA_NAME);

    private SCADomain domain;

    /**
     * Tests ?wsdl works and produces xs:anyType for commonj.sdo.DataObject
     */
    public void testDynamicSDO() throws Exception {
        InputStream inp = new URL("http://localhost:8085/DataService?wsdl").openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inp));
        String line;
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();

        WSDLReader wsdlReader = WSDLFactory.newInstance().newWSDLReader();
        wsdlReader.setFeature("javax.wsdl.verbose",false);
        wsdlReader.setFeature("javax.wsdl.importDocuments",true);

        Definition definition = wsdlReader.readWSDL("http://localhost:8085/DataService?wsdl");
        assertNotNull(definition);
        Service service = definition.getService(new QName("http://verify.wsdlgen/",
                                                          "GetDataServiceWithoutExceptionService"));
        Port port = service.getPort("GetDataServiceWithoutExceptionPort");
        Binding binding = port.getBinding();
        PortType portType = binding.getPortType();

        // find wrapper element for getMessageSDO return type
        Operation op = portType.getOperation("getMessageSDO", null, null);
        Output out = op.getOutput();
        Message msg = out.getMessage();
        Part part = msg.getPart(msg.getQName().getLocalPart());
        QName elementQName = part.getElementName();

        // find schema definition for wrapper element
        Types types = definition.getTypes();
        String elementNS = elementQName.getNamespaceURI();
        Element schema = null;
        for (Object ext : types.getExtensibilityElements()) {
            ExtensibilityElement extElement = (ExtensibilityElement)ext;
            if (SCHEMA_QNAME.equals(extElement.getElementType())) {
                if (extElement instanceof Schema) {
                    Element schemaElement = ((Schema)extElement).getElement();
                    if (elementNS.equals(schemaElement.getAttribute("targetNamespace"))) {
                        schema = schemaElement;
                        break;
                    }
                }
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

        // find xs:complexType child element
        childNodes = wrapper.getElementsByTagNameNS(SCHEMA_NS, "complexType");
        Element complexType = (Element)childNodes.item(0);

        // find xs:sequence child element
        childNodes = complexType.getElementsByTagNameNS(SCHEMA_NS, "sequence");
        Element sequence = (Element)childNodes.item(0);

        // find xs:element child element
        childNodes = sequence.getElementsByTagNameNS(SCHEMA_NS, "element");
        Element returnValue = (Element)childNodes.item(0);

        // verify that return type is xs:anyType
        assertEquals("xs:anyType", returnValue.getAttribute("type"));
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

    @Override
    protected void setUp() throws Exception {
        domain = SCADomain.newInstance("dynamic-sdo.composite");
    }

    @Override
    protected void tearDown() throws Exception {
        domain.close();
    }

}
