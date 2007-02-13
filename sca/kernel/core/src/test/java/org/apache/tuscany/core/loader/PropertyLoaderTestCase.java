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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import junit.framework.TestCase;

import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.util.stax.StaxUtil;
import org.easymock.EasyMock;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @version $Rev: 502055 $ $Date: 2007-02-01 05:37:32 +0530 (Thu, 01 Feb 2007) $
 */
public class PropertyLoaderTestCase extends TestCase {
    private PropertyLoader propertyLoader;
    private XMLInputFactory xmlFactory;
    
    public void testPropertyLoading() throws Exception {
        String xml = "<tus:property xmlns:foo='http://foo.com' "
        	+ "xmlns:tus='http://www.osoa.org/xmlns/sca/1.0'"
            + " name='TestProperty' many='true' type='foo:TestType'>"
            + "<foo:a>aValue</foo:a>"
            + "<foo:b>InterestingURI</foo:b>"
            + "</tus:property>";

        XMLStreamReader reader = getReader(xml);
        Property<?> aProperty = propertyLoader.load(null, null, reader, null);
        
        assertEquals("TestProperty", aProperty.getName());
        assertEquals(new QName("http://foo.com", "TestType", "foo"), aProperty.getXmlType());
        assertEquals(false, aProperty.isNoDefault());
        assertEquals(true, aProperty.isMany());
        
        Element root = aProperty.getDefaultValue().getDocumentElement();
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
            + " name='TestProperty' many='true' type='foo:TestType' noDefault='true'>"
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
}
