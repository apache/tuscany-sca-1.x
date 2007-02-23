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
package org.apache.tuscany.spi.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;

import org.apache.tuscany.spi.ObjectFactory;

/**
 * A component property
 *
 * @version $Rev$ $Date$
 */
public class Property<T> extends ModelObject {
    private String name;
    private ObjectFactory<?> defaultValueFactory;
    private QName xmlType;
    private QName xmlElement;
    private Class<T> javaType;
    private boolean many;
    private boolean mustSupply;
    private List<Document> defaultValues;
    

    public Property() {
        mustSupply = false;
        many = false;
    }

    public Property(String name, QName xmlType, Class<T> javaType) {
        this.name = name;
        this.xmlType = xmlType;
        this.javaType = javaType;
        mustSupply = false;
        many = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectFactory<?> getDefaultValueFactory() {
        return defaultValueFactory;
    }

    public void setDefaultValueFactory(ObjectFactory<T> factory) {
        this.defaultValueFactory = factory;
    }
    
    public void setDefaultMultiValueFactory(ObjectFactory<List<T>> factory) {
        this.defaultValueFactory = factory;
    }

    public QName getXmlType() {
        return xmlType;
    }

    public void setXmlType(QName xmlType) {
        this.xmlType = xmlType;
    }

    public Class<T> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<T> javaType) {
        this.javaType = javaType;
    }

    public boolean isMany() {
        return many;
    }

    public void setMany(boolean many) {
        this.many = many;
    }

    public boolean isMustSupply() {
        return mustSupply;
    }

    public void setMustSupply(boolean mustSupply) {
        this.mustSupply = mustSupply;
    }

    /*public Document getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Document defaultValue) {
        this.defaultValue = defaultValue;
    }*/

    public QName getXmlElement() {
        return xmlElement;
    }

    public void setXmlElement(QName xmlElement) {
        this.xmlElement = xmlElement;
    }

    public List<Document> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(List<Document> defaultValues) {
        this.defaultValues = defaultValues;
    }
}
