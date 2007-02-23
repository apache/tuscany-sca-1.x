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

import org.apache.tuscany.spi.ObjectFactory;
import org.w3c.dom.Document;

/**
 * Represents a configured component property
 * @version $Rev$ $Date$
 */
public class PropertyValue<T> extends ModelObject {
    private String name;
    private String source;
    private String file;
    private List<Document> value;
    private ObjectFactory<?> valueFactory;

    public PropertyValue() {
    }

    /**
     * Constructor specifying the name of a property and the XPath source expression.
     *
     * @param name the name of the property which this value is for
     * @param source an XPath expression whose result will be the actual value
     * @param file A URI that the property value can be loaded from
     */
    public PropertyValue(String name, String source, String file) {
        this.name = name;
        this.source = source;
        this.file = file;
    }

    /**
     * @param name
     * @param value
     */
    public PropertyValue(String name, List<Document> value) {
        this.name = name;
        this.value = value;
    }
    
    public PropertyValue(String name, ObjectFactory<?> valueFactory) {
        this.name = name;
        this.valueFactory = valueFactory;
    }

    /**
     * Returns the name of the property that this value is for.
     * @return the name of the property that this value is for
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the property that this value is for.
     * @param name the name of the property that this value is for
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns an XPath expression that should be evaluated to get the actual property value.
     *
     * @return an XPath expression that should be evaluated to get the actual property value
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets an XPath expression that should be evaluated to get the actual property value.
     * @param source an XPath expression that should be evaluated to get the actual property value
     */
    public void setSource(String source) {
        this.source = source;
    }

    public ObjectFactory<?> getValueFactory() {
        return valueFactory;
    }

    public void setValueFactory(ObjectFactory<?> valueFactory) {
        this.valueFactory = valueFactory;
    }
    
    public void setMultiValueFactory(ObjectFactory<List<T>> valueFactory) {
        this.valueFactory = valueFactory;
    }
    

    public List<Document> getValue() {
        return value;
    }

    public void setValue(List<Document> value) {
        this.value = value;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }
}
