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

import org.apache.tuscany.assembly.model.AssemblyFactory;
import org.apache.tuscany.assembly.model.Callback;
import org.apache.tuscany.assembly.model.ComponentService;
import org.apache.tuscany.assembly.model.ComponentType;
import org.apache.tuscany.assembly.model.Property;
import org.apache.tuscany.assembly.model.Reference;
import org.apache.tuscany.assembly.model.Service;
import org.apache.tuscany.assembly.reader.util.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * A test handler to test the usability of the assembly model API when loading SCDL
 * 
 * @version $Rev$ $Date$
 */
public class ComponentTypeHandler extends BaseHandler implements ContentHandler {

    private ComponentType componentType;
    private Service service;
    private Reference reference;
    private Property property;
    private Callback callback;

    public ComponentTypeHandler(AssemblyFactory factory, XMLReader reader) {
        super(factory, reader);
    }

    public void startElement(String uri, String name, String qname, Attributes attr) throws SAXException {
        if (sca10.equals(uri)) {

            if ("componentType".equals(name)) {
                componentType = factory.createComponentType();
                componentType.setConstrainingType(getConstrainingType(attr));

            } else if ("service".equals(name)) {
                service = factory.createService();
                componentType.getServices().add(service);
                service.setName(getString(attr, "name"));

            } else if ("reference".equals(name)) {
                reference = factory.createReference();
                componentType.getReferences().add(reference);
                reference.setName(getString(attr, "name"));

                //TODO support multivalued attribute
            	ComponentService target = factory.createComponentService();
            	target.setUndefined(true);
            	target.setName(getString(attr, "target"));
            	reference.getTargets().add(target);
            	
            } else if ("property".equals(name)) {
                property = factory.createProperty();
                readProperty(property, attr);

            } else if ("callback".equals(name)) {
	            callback = factory.createCallback();
	            if (reference != null) {
	            	reference.setCallback(callback);
	            } else if (service != null) {
	            	service.setCallback(callback);
	            }
	        }
        }
    }

    public void endElement(String uri, String name, String qName) throws SAXException {
        if (sca10.equals(uri)) {
            if ("service".equals(name)) {
                service = null;
            } else if ("reference".equals(name)) {
                reference = null;
            } else if ("property".equals(name)) {
                property = null;
            } else if ("callback".equals(name))
            	callback = null;
        }
    }

	public ComponentType getComponentType() {
		return componentType;
	}

}
