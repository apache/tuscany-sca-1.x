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

package org.apache.tuscany.assembly.writer;

import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.apache.tuscany.assembly.model.AssemblyFactory;
import org.apache.tuscany.assembly.model.impl.AssemblyFactoryImpl;
import org.apache.tuscany.assembly.reader.ComponentTypeHandler;
import org.apache.tuscany.assembly.reader.CompositeHandler;
import org.apache.tuscany.assembly.reader.ConstrainingTypeHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Test the usability of the assembly model API when loading SCDL
 * 
 * @version $Rev$ $Date$
 */
public class WriteTestCase extends TestCase {

    AssemblyFactory factory;
    XMLReader reader;
    Transformer transformer;

    public void setUp() throws Exception {
        factory = new AssemblyFactoryImpl();

        reader = XMLReaderFactory.createXMLReader();
        reader.setFeature("http://xml.org/sax/features/namespaces", true);
        reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
        
        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty("indent", "yes");
    }

    public void tearDown() throws Exception {
        factory = null;
        reader = null;
        transformer = null;
    }
    
    public static void main(String[] args) throws Exception {
    	WriteTestCase tc = new WriteTestCase();
    	tc.setUp();
		tc.testWriteComponentType();
	}

    public void testWriteComponentType() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("CalculatorImpl.componentType");
        ComponentTypeHandler handler = new ComponentTypeHandler(factory, reader);
        reader.setContentHandler(handler);
        reader.parse(new InputSource(is));
        assertNotNull(handler.getComponentType());

        ComponentTypeWriter writer = new ComponentTypeWriter(handler.getComponentType());
        System.out.println();
        transformer.transform(new SAXSource(writer, null), new StreamResult(System.out));
        System.out.println();
    }

    public void testWriteComposite() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("Calculator.composite");
        CompositeHandler handler = new CompositeHandler(factory, reader);
        reader.setContentHandler(handler);
        reader.parse(new InputSource(is));
        assertNotNull(handler.getComposite());

        CompositeWriter writer = new CompositeWriter(handler.getComposite());
        System.out.println();
        transformer.transform(new SAXSource(writer, null), new StreamResult(System.out));
        System.out.println();
    }

    public void testWriteConstrainingType() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("CalculatorComponent.constrainingType");
        ConstrainingTypeHandler handler = new ConstrainingTypeHandler(factory, reader);
        reader.setContentHandler(handler);
        reader.parse(new InputSource(is));
        assertNotNull(handler.getConstrainingType());

        ConstrainingTypeWriter writer = new ConstrainingTypeWriter(handler.getConstrainingType());
        System.out.println();
        transformer.transform(new SAXSource(writer, null), new StreamResult(System.out));
        System.out.println();
    }

}
