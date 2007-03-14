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

import org.apache.tuscany.assembly.model.AbstractProperty;
import org.apache.tuscany.assembly.model.AbstractReference;
import org.apache.tuscany.assembly.model.AbstractService;
import org.apache.tuscany.assembly.model.AssemblyFactory;
import org.apache.tuscany.assembly.model.ConstrainingType;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * A test handler to test the usability of the assembly model API when loading SCDL
 *
 *  @version $Rev$ $Date$
 */
public class ConstrainingTypeHandler extends BaseHandler implements ContentHandler {
	
	private ConstrainingType constrainingType;
	private AbstractService abstractService;
	private AbstractReference abstractReference;
	private AbstractProperty abstractProperty;
	
	public ConstrainingTypeHandler(AssemblyFactory factory, XMLReader reader) {
		super(factory, reader);
	}

	public void startElement(String uri, String name, String qname, Attributes attr) throws SAXException {
		if (sca10.equals(uri)) {

			if ("constrainingType".equals(name)) {
				constrainingType = factory.createConstrainingType();
				constrainingType.setName(getQName(attr, "name"));
				
			} else if ("service".equals(name)) {
				abstractService = factory.createAbstractService();
				abstractService.setName(getString(attr, "name"));

			} else if ("reference".equals(name)) {
				abstractReference = factory.createAbstractReference();
				abstractReference.setName(getString(attr, "name"));
				
			} else if ("property".equals(name)) {
				abstractProperty = factory.createAbstractProperty();
				initAbstractProperty(abstractProperty, attr);
			}
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (sca10.equals(uri)) {

			if ("service".equals(localName)) {
				constrainingType.getServices().add(abstractService);
				abstractService = null;
				
			} else if ("reference".equals(localName)) {
				constrainingType.getReferences().add(abstractReference);
				abstractReference = null;
				
			} else if ("property".equals(localName)) {
				constrainingType.getProperties().add(abstractProperty);
				abstractProperty = null;
			}
		}
	}

}
