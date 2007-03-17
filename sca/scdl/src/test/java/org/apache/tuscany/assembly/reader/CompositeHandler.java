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
import org.apache.tuscany.assembly.model.Component;
import org.apache.tuscany.assembly.model.ComponentProperty;
import org.apache.tuscany.assembly.model.ComponentReference;
import org.apache.tuscany.assembly.model.ComponentService;
import org.apache.tuscany.assembly.model.Composite;
import org.apache.tuscany.assembly.model.CompositeReference;
import org.apache.tuscany.assembly.model.CompositeService;
import org.apache.tuscany.assembly.model.Property;
import org.apache.tuscany.assembly.model.Wire;
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
public class CompositeHandler extends BaseHandler implements ContentHandler {

    private Composite composite;
    private Component component;
    private Property property;
    private ComponentService componentService;
    private ComponentReference componentReference;
    private ComponentProperty componentProperty;
    private CompositeService compositeService;
    private CompositeReference compositeReference;
    private Wire wire;
    private Callback callback;

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
                    component.getServices().add(componentService);
                    componentService.setName(getString(attr, "name"));
                } else {
                    compositeService = factory.createCompositeService();
                    composite.getServices().add(compositeService);
                    compositeService.setName(getString(attr, "name"));

                    ComponentService promoted = factory.createComponentService();
                	promoted.setUndefined(true);
                	promoted.setName(getString(attr, "promote"));
                	compositeService.setPromotedService(promoted);
                }

            } else if ("reference".equals(name)) {
                if (component != null) {
                    componentReference = factory.createComponentReference();
                    component.getReferences().add(componentReference);
                    componentReference.setName(getString(attr, "name"));

                    //TODO support multivalued attribute
                	ComponentService target = factory.createComponentService();
                	target.setUndefined(true);
                	target.setName(getString(attr, "target"));
                	componentReference.getTargets().add(target);
                    
                } else {
                    compositeReference = factory.createCompositeReference();
                    composite.getReferences().add(compositeReference);
                    compositeReference.setName(getString(attr, "name"));

                    //TODO support multivalued attribute
                    ComponentReference promoted = factory.createComponentReference();
                	promoted.setUndefined(true);
                	promoted.setName(getString(attr, "promote"));
                	compositeReference.getPromotedReferences().add(promoted);
                }

            } else if ("property".equals(name)) {
                if (component != null) {
                    componentProperty = factory.createComponentProperty();
                    component.getProperties().add(componentProperty);
                    readProperty(componentProperty, attr);
                } else {
                    property = factory.createProperty();
                    composite.getProperties().add(property);
                    readProperty(property, attr);
                }

            } else if ("component".equals(name)) {
                component = factory.createComponent();
                composite.getComponents().add(component);
                component.setName(getString(attr, "name"));
                component.setConstrainingType(getConstrainingType(attr));
                
            } else if ("wire".equals(name)) {
            	wire = factory.createWire();
                composite.getWires().add(wire);
            	
            	ComponentReference source = factory.createComponentReference();
            	source.setUndefined(true);
            	source.setName(getString(attr, "source"));
            	wire.setSource(source);
            	
            	ComponentService target = factory.createComponentService();
            	target.setUndefined(true);
            	target.setName(getString(attr, "target"));
            	wire.setTarget(target);
            	
	        } else if ("callback".equals(name)) {
                callback = factory.createCallback();
	            if (componentReference != null) {
	            	componentReference.setCallback(callback);
	            } else if (compositeReference != null) {
	            	compositeReference.setCallback(callback);
	            } else if (componentService != null) {
	            	componentService.setCallback(callback);
	            } else if (compositeService != null) {
	            	compositeService.setCallback(callback);
	            }
	        }
        }
    }

    public void endElement(String uri, String name, String qname) throws SAXException {
        if ("service".equals(name)) {
            componentService = null;
            compositeService = null;
        } else if ("reference".equals(name)) {
            componentReference = null;
            compositeReference = null;
        } else if ("property".equals(name)) {
            componentProperty = null;
            property = null;
        } else if ("component".equals(name)) {
            component = null;
        } else if ("wire".equals(name)) {
            wire= null;
        } else if ("callback".equals(name)) {
        	callback = null;
        }
    }

	public Composite getComposite() {
		return composite;
	}

}
