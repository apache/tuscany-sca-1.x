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
package org.apache.tuscany.sca.domain.search.impl;

import java.lang.reflect.Array;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

/**
 * 
 * @version $Rev$ $Date$
 */
public class PropertyDocumentProcessor implements DocumentProcessor {

    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document doc,
                            String parent) {

        if (object instanceof Property) {
            Property property = (Property)object;
            String name = property.getName();

            if (name != null && name.length() > 0) {

                if (doc == null) {
                    doc = documents.get(name);
                }

                Object value = property.getValue();

                if (value.getClass().isArray()) {
                    int arraySize = Array.getLength(value);

                    for (int i = 0; i < arraySize; i++) {
                        Object arrayValue = Array.get(value, i);

                        doc.add(new Field(SearchFields.VALUE_FIELD, arrayValue.toString(), Field.Store.YES,
                                          Field.Index.ANALYZED));

                    }

                } else {

                    doc
                        .add(new Field(SearchFields.VALUE_FIELD, value.toString(), Field.Store.YES,
                                       Field.Index.ANALYZED));

                }

                return doc == null ? FAKE_DOCUMENT : doc;

            } else {
                return FAKE_DOCUMENT;
            }

        }

        throw new IllegalArgumentException();

    }

    public Object getDocumentKey(Object object) {
        // TODO Auto-generated method stub
        return null;
    }

}
