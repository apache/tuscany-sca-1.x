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
package org.apache.tuscany.core.loader;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.apache.tuscany.core.databinding.javabeans.JavaBean2DOMNodeTransformer;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.model.Property;
import org.easymock.EasyMock;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @version $Rev: 502055 $ $Date: 2007-02-01 05:37:32 +0530 (Thu, 01 Feb 2007) $
 */
public class PropertyLoaderTestCase extends TestCase {
    private PropertyLoader propertyLoader;
    private XMLInputFactory xmlFactory;
    
    
    public void testPropertyLoading_SimpleType() throws Exception {
        String xml = "<tus:property xmlns:foo='http://foo.com' "
                + "xmlns:tus='http://www.osoa.org/xmlns/sca/1.0' "
                + "xmlns:xs='http://www.w3.org/2001/XMLSchema' "
            + " name='TestProperty' type='xs:string'>"
            + "TestPropertyValue"
            + "</tus:property>";

        XMLStreamReader reader = getReader(xml);
        Property<?> aProperty = propertyLoader.load(null, null, reader, null);
        
        assertEquals("TestProperty", aProperty.getName());
        assertEquals(new QName("http://www.w3.org/2001/XMLSchema", "string", "xs"), aProperty.getXmlType());
        assertEquals(false, aProperty.isMustSupply());
        assertEquals(false, aProperty.isMany());
        
        Element root = aProperty.getDefaultValues().get(0);
        
        NodeList childNodes = root.getChildNodes();
        assertEquals(1, childNodes.getLength());

        Text t = (Text) childNodes.item(0);
        assertEquals("TestPropertyValue", t.getTextContent());
    }
    
    
    public void testPropertyLoading_Type() throws Exception {
        String xml = "<tus:property xmlns:foo='http://foo.com' "
                        + "xmlns:tus='http://www.osoa.org/xmlns/sca/1.0' " 
                        + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
                        + " name='complexFoo' type='foo:MyComplexType'>"
                        + "<MyComplexPropertyValue1 xsi:type='foo:MyComplexType' attr='bar'>"
                            + "<foo:a>AValue</foo:a>" 
                            + "<foo:b>InterestingURI</foo:b>"
                        + "</MyComplexPropertyValue1>"
            + "</tus:property>";

        XMLStreamReader reader = getReader(xml);
        Property<?> aProperty = propertyLoader.load(null, null, reader, null);
        assertEquals("complexFoo", aProperty.getName());
        assertEquals(new QName("http://foo.com", "MyComplexType", "foo"), aProperty.getXmlType());
        assertEquals(false, aProperty.isMustSupply());
        assertEquals(false, aProperty.isMany());
        
        Element root = aProperty.getDefaultValues().get(0);
        
        NodeList childNodes = root.getChildNodes();
        assertEquals(2, childNodes.getLength());

        Element e = (Element) childNodes.item(0);
        assertEquals("http://foo.com", e.getNamespaceURI());
        assertEquals("a", e.getLocalName());
        assertEquals("AValue", e.getTextContent());
        e = (Element) childNodes.item(1);
        assertEquals("http://foo.com", e.getNamespaceURI());
        assertEquals("b", e.getLocalName());
        assertEquals("InterestingURI", e.getTextContent());
    }
    
    public void testPropertyLoading_Element() throws Exception {
        String xml = "<tus:property xmlns:foo='http://foo.com' "
                + "xmlns:tus='http://www.osoa.org/xmlns/sca/1.0'"
            + " name='TestProperty' element='foo:TestElement'>\n"
            + "<foo:TestElement>"
            + "<foo:a>aValue</foo:a>"
            + "<foo:b>InterestingURI</foo:b>"
            + "</foo:TestElement>"
            + "</tus:property>";
        XMLStreamReader reader = getReader(xml);
        Property<?> aProperty = propertyLoader.load(null, null, reader, null);
        
        Element root = aProperty.getDefaultValues().get(0);
        
        assertEquals("TestProperty", aProperty.getName());
        assertEquals(new QName("http://foo.com", "TestElement", "foo"), aProperty.getXmlElement());
        assertEquals(false, aProperty.isMustSupply());
        assertEquals(false, aProperty.isMany());
        
        NodeList childNodes = root.getChildNodes();
        assertEquals(2, childNodes.getLength());

        Element e = (Element) childNodes.item(0);
        assertEquals("http://foo.com", e.getNamespaceURI());
        assertEquals("a", e.getLocalName());
        assertEquals("aValue", e.getTextContent());
        e = (Element) childNodes.item(1);
        assertEquals("http://foo.com", e.getNamespaceURI());
        assertEquals("b", e.getLocalName());
        assertEquals("InterestingURI", e.getTextContent());
    }
    
    public void testPropertyLoadingNoDefault() throws Exception {
        String xml = "<tus:property xmlns:foo='http://foo.com' "
        	+ "xmlns:tus='http://www.osoa.org/xmlns/sca/1.0'"
            + " name='TestProperty' type='foo:TestType' mustSupply='true'>"
            + "<foo:a>aValue</foo:a>"
            + "<foo:b>InterestingURI</foo:b>"
            + "</tus:property>";

        XMLStreamReader reader = getReader(xml);
        
        try {
            propertyLoader.load(null, null, reader, null);
        } catch (Exception e) {
            assertTrue(e instanceof DefaultPropertyValueLoaderException);
        }
    }

    public XMLStreamReader getReader(String xml) throws XMLStreamException {
        XMLStreamReader reader = xmlFactory.createXMLStreamReader(new StringReader(xml));
        reader.next();
        return reader;
    }

    protected void setUp() throws Exception {
        super.setUp();
        xmlFactory = XMLInputFactory.newInstance();
        propertyLoader = new PropertyLoader(EasyMock.createMock(LoaderRegistry.class));
    }
    
    private void printNode(Node node) throws Exception {
        javax.xml.transform.Transformer transformer =
            TransformerFactory.newInstance().newTransformer();
        JavaBean2DOMNodeTransformer java2DomTransformer = new JavaBean2DOMNodeTransformer();
        StringWriter sw = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(sw));
        
        System.out.println(sw.toString());
    }
}
