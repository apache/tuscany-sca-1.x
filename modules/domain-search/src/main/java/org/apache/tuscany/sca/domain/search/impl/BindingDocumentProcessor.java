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

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

/**
 * 
 * @version $Rev$ $Date$
 */
public class BindingDocumentProcessor implements DocumentProcessor {

    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document document,
                            String parent) {

        if (object instanceof Binding) {
            Binding binding = (Binding)object;
            String uri = binding.getURI();

            if (uri != null && uri.length() == 0) {
                uri = null;
            }

            if (uri != null) {

                if (document == null) {
                    document = documents.get(uri);
                }

                document.add(new Field(SearchFields.BINDING_FIELD, uri, Field.Store.YES, Field.Index.ANALYZED));

            }

            return document == null ? FAKE_DOCUMENT : document;

        }

        throw new IllegalArgumentException();

    }

    public Object getDocumentKey(Object obj) {

        if (obj instanceof Binding) {
            Binding binding = (Binding)obj;
            String uri = binding.getURI();

            if (uri != null && uri.length() == 0) {
                return null;
            }

            return uri;

        }

        throw new IllegalArgumentException();

    }

}
