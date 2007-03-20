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

import org.apache.tuscany.assembly.model.Component;
import org.apache.tuscany.assembly.model.ComponentProperty;
import org.apache.tuscany.assembly.model.ComponentReference;
import org.apache.tuscany.assembly.model.ComponentService;
import org.apache.tuscany.assembly.model.Composite;
import org.apache.tuscany.assembly.model.CompositeReference;
import org.apache.tuscany.assembly.model.CompositeService;
import org.apache.tuscany.assembly.model.Property;
import org.apache.tuscany.assembly.model.Reference;
import org.apache.tuscany.assembly.model.Service;
import org.apache.tuscany.assembly.writer.util.Attr;
import org.apache.tuscany.assembly.writer.util.BaseWriter;
import org.xml.sax.SAXException;

/**
 * A test handler to test the usability of the assembly model API when writing SCDL
 * 
 * @version $Rev$ $Date$
 */
public class CompositeWriter extends BaseWriter {

    private Composite composite;

    public CompositeWriter(Composite composite) {
        this.composite = composite;
    }
    
    protected void write() throws SAXException {
    	
    	start("composite", new Attr("constrainingType", getConstrainingType(composite)));
    	
    	for (Service service: composite.getServices()) {
    		CompositeService compositeService = (CompositeService)service;
    		ComponentService promotedService = compositeService.getPromotedService();
    		String promote = promotedService != null? promotedService.getName():null;
    		start("service",
    			new Attr("name", service.getName()),
    			new Attr("promote", promote));
    		if (service.getCallback() != null) {
    			start("callback");
    			end("callback");
    		}
    		end("service");
    	}
    	
    	for (Component component: composite.getComponents()) {
    		start("component",
    			new Attr("name", component.getName()));

    		for (ComponentService service: component.getServices()) {
        		start("service",
        			new Attr("name", service.getName()));
        		end("service");
        		if (service.getCallback() != null) {
        			start("callback");
        			end("callback");
        		}
        	}
    		
        	for (ComponentReference reference: component.getReferences()) {
        		//TODO handle multivalued target attribute
        		String target = reference.getTargets().isEmpty()? null: reference.getTargets().get(0).getName();
        		start("reference", 
        			new Attr("name", reference.getName()),
        			new Attr("target", target));
        		if (reference.getCallback() != null) {
        			start("callback");
        			end("callback");
        		}
        		end("reference");
        	}
        	
        	for (ComponentProperty property: component.getProperties()) {
        		start("property", new Attr("name", property.getName()));
        		end("property");
        	}
        	
    		end("component");
    	}
    	
    	for (Reference reference: composite.getReferences()) {
    		//TODO handle multivalued promote attribute
    		CompositeReference compositeReference = (CompositeReference)reference;
    		String promote;
    		if (!compositeReference.getPromotedReferences().isEmpty())
        		promote = compositeReference.getPromotedReferences().get(0).getName();
    		else
    			promote = null;
    		start("reference", 
    			new Attr("name", reference.getName()),
    			new Attr("promote", promote));
    		if (reference.getCallback() != null) {
    			start("callback");
    			end("callback");
    		}
    		end("reference");
    	}
    	
    	for (Property property: composite.getProperties()) {
    		start("property", new Attr("name", property.getName()));
    		end("property");
    	}
    	
    	end("composite");
    }
    
}
