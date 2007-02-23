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

package org.apache.tuscany.core.property;

import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.spi.ObjectCreationException;
import org.apache.tuscany.spi.ObjectFactory;
import org.apache.tuscany.spi.databinding.extension.SimpleTypeMapperExtension;
import org.apache.tuscany.spi.idl.TypeInfo;
import org.apache.tuscany.spi.model.Property;
import org.w3c.dom.Document;

public class SimpleMultivaluedPropertyObjectFactory<P> implements ObjectFactory<List<P>> {
    private SimpleTypeMapperExtension typeMapper;
    private Property<P> property;
    private List<Document> values;
    private List<P> instance;

    public SimpleMultivaluedPropertyObjectFactory(Property<P> property, List<Document> value) {
        super();
        
        this.property = property;
        this.values = (value == null) ? property.getDefaultValues() : value;
        this.typeMapper = new SimpleTypeMapperExtension();
    }

    @SuppressWarnings("unchecked")
    public List<P> getInstance() throws ObjectCreationException {
        if (values == null) {
            return null;
        }
        
        TypeInfo xmlType = null;
        String text = null;
        
        if (instance == null) {
            instance = new ArrayList<P>();
            for (int count = 0 ; count < values.size() ; ++count) {
                text = values.get(count).getDocumentElement().getTextContent();
            
                if (property.getJavaType() == null) {
                    xmlType = new TypeInfo(property.getXmlType(), true, null);
                } else {
                    xmlType = typeMapper.getXMLType(property.getJavaType());
                }
                if (xmlType == null) {
                    throw new IllegalArgumentException("Complex property is not supported.");
                }
                instance.add((P)typeMapper.toJavaObject(xmlType.getQName(), text, null));
            }
        }
        return instance;
    }

}
