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

package org.apache.tuscany.assembly.reader.util;

import javax.xml.namespace.QName;

import org.apache.tuscany.assembly.model.AbstractProperty;
import org.apache.tuscany.assembly.model.AssemblyFactory;
import org.apache.tuscany.assembly.model.ConstrainingType;
import org.apache.tuscany.assembly.model.Property;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A test handler to test the usability of the assembly model API when loading SCDL
 * 
 * @version $Rev$ $Date$
 */
public abstract class BaseHandler extends DefaultHandler implements ContentHandler {

    protected final static String sca10 = "http://www.osoa.org/xmlns/sca/1.0";

    protected NamespaceStack nsStack = new NamespaceStack();
    protected AssemblyFactory factory;
    protected XMLReader reader;

    public BaseHandler(AssemblyFactory factory, XMLReader reader) {
        this.factory = factory;
        this.reader = reader;
    }

    protected String getString(Attributes attr, String name) {
        return attr.getValue(name);
    }

    protected QName getQName(Attributes attr, String name) {
        String qName = attr.getValue(name);
        int index = qName.indexOf(':');
        String prefix = index == -1 ? "" : qName.substring(0, index);
        String localName = index == -1 ? qName : qName.substring(index);
        String ns = nsStack.getNamespaceURI(prefix);
        if (ns == null) {
            ns = "";
        }
        return new QName(ns, localName, prefix);
    }

    protected boolean getBoolean(Attributes attr, String name) {
        return Boolean.valueOf(attr.getValue(name));
    }

    protected ConstrainingType getConstrainingType(Attributes attr) {
        String constrainingTypeName = attr.getValue(sca10, "constrainingType");
        if (constrainingTypeName != null) {
            ConstrainingType constrainingType = factory.createConstrainingType();
            constrainingType.setName(new QName(constrainingTypeName));
            constrainingType.setUndefined(true);
            return constrainingType;
        } else {
            return null;
        }
    }

    protected void readAbstractProperty(AbstractProperty prop, Attributes attr) {
        prop.setName(getString(attr, "name"));
        prop.setMany(getBoolean(attr, "many"));
        prop.setMustSupply(getBoolean(attr, "mustSupply"));
        String xsdElement = getString(attr, "element");
        if (xsdElement != null) {
            prop.setXSDElement(new QName(xsdElement));
        }
        String xsdType = getString(attr, "type");
        if (xsdType != null) {
            prop.setXSDType(new QName(xsdType));
        }
        // TODO handle default value
    }

    protected void readProperty(Property prop, Attributes attr) {
        readAbstractProperty(prop, attr);
        // TODO handle property value
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        nsStack.endPrefixMapping(prefix);
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        nsStack.startPrefixMapping(prefix, uri);
    }

}
