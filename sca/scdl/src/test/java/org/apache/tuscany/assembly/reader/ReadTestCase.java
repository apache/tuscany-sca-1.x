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

package org.apache.tuscany.assembly.reader;


import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.tuscany.assembly.model.AssemblyFactory;
import org.apache.tuscany.assembly.model.impl.AssemblyFactoryImpl;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Test the usability of the assembly model API when loading SCDL
 *
 *  @version $Rev$ $Date$
 */
public class ReadTestCase extends TestCase {
	
	AssemblyFactory factory;
	XMLReader reader;

	public void setUp() throws Exception {
		factory = new AssemblyFactoryImpl();

		reader = XMLReaderFactory.createXMLReader();
        reader.setFeature("http://xml.org/sax/features/namespaces", true);
        reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
	}

	public void tearDown() throws Exception {
		factory = null;
		reader = null;
	}
	
	public void testLoadComponentType() throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("CalculatorImpl.componentType");
		ContentHandler handler = new ComponentTypeHandler(factory, reader);
        reader.setContentHandler(handler);
        reader.parse(new InputSource(is));
	}
	
	public void testLoadConstrainingType() throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("CalculatorComponent.constrainingType");
		ContentHandler handler = new ConstrainingTypeHandler(factory, reader);
        reader.setContentHandler(handler);
        reader.parse(new InputSource(is));
	}

	public void testLoadComposite() throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("Calculator.composite");
		ContentHandler handler = new CompositeHandler(factory, reader);
        reader.setContentHandler(handler);
        reader.parse(new InputSource(is));
	}
	
}
