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
import org.apache.tuscany.assembly.model.Component;
import org.apache.tuscany.assembly.model.ComponentProperty;
import org.apache.tuscany.assembly.model.ComponentReference;
import org.apache.tuscany.assembly.model.ComponentService;
import org.apache.tuscany.assembly.model.Composite;
import org.apache.tuscany.assembly.model.CompositeReference;
import org.apache.tuscany.assembly.model.CompositeService;
import org.apache.tuscany.assembly.model.Property;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * A test handler to test the usability of the assembly model API when loading SCDL
 * 
 * @version $Rev$ $Date$
 */
public class CompositeHandler extends BaseHandler implements ContentHandler {

    private Composite composite;
    private Component component;
    private Property property;
    private ComponentService componentService;
    private ComponentReference componentReference;
    private ComponentProperty componentProperty;
    private CompositeService compositeService;
    private CompositeReference compositeReference;

    public CompositeHandler(AssemblyFactory factory, XMLReader reader) {
        super(factory, reader);
    }

    public void startElement(String uri, String name, String qname, Attributes attr) throws SAXException {
        if (sca10.equals(uri)) {

            if ("composite".equals(name)) {
                composite = factory.createComposite();
                composite.setName(getQName(attr, "name"));
                composite.setAutowire(getBoolean(attr, "autowire"));
                composite.setLocal(getBoolean(attr, "local"));
                composite.setConstrainingType(getConstrainingType(attr));

            } else if ("service".equals(name)) {
                if (component != null) {
                    componentService = factory.createComponentService();
                    componentService.setName(getString(attr, "name"));
                } else {
                    compositeService = factory.createCompositeService();
                    compositeService.setName(getString(attr, "name"));
                }

            } else if ("reference".equals(name)) {
                if (component != null) {
                    componentReference = factory.createComponentReference();
                    componentReference.setName(getString(attr, "name"));
                } else {
                    compositeReference = factory.createCompositeReference();
                    compositeReference.setName(getString(attr, "name"));
                }

            } else if ("property".equals(name)) {
                if (component != null) {
                    componentProperty = factory.createComponentProperty();
                    readProperty(componentProperty, attr);
                } else {
                    property = factory.createProperty();
                    readProperty(property, attr);
                }

            } else if ("component".equals(name)) {
                component = factory.createComponent();
                component.setName(getString(attr, "name"));
                component.setConstrainingType(getConstrainingType(attr));
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("composite".equals(localName)) {

        } else if ("service".equals(localName)) {

            if (component != null) {
                component.getServices().add(componentService);
                componentService = null;
            } else {
                composite.getServices().add(compositeService);
                compositeService = null;
            }

        } else if ("reference".equals(localName)) {

            if (component != null) {
                component.getReferences().add(componentReference);
                componentReference = null;
            } else {
                composite.getReferences().add(compositeReference);
                compositeReference = null;
            }

        } else if ("property".equals(localName)) {

            if (component != null) {
                component.getProperties().add(componentProperty);
                componentProperty = null;
            } else {
                composite.getProperties().add(property);
                property = null;
            }

        } else if ("component".equals(localName)) {

            composite.getComponents().add(component);
            component = null;

        }
    }

	public Composite getComposite() {
		return composite;
	}

}
