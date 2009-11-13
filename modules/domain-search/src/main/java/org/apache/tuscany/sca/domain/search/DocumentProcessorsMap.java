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
package org.apache.tuscany.sca.domain.search;

import java.util.HashMap;

import org.apache.tuscany.sca.domain.search.impl.Document;

/**
 * This class is a {@link DocumentProcessor} that holds a map from {@link Class}
 * -> {@link DocumentProcessor}, and based on this map this processor delegates
 * the object to be processed to its respective {@link DocumentProcessor}.
 * 
 * @version $Rev$ $Date$
 */
public class DocumentProcessorsMap extends HashMap<Class<?>, DocumentProcessor> implements DocumentProcessor {

    private static final long serialVersionUID = 3967390896890947159L;

    private DocumentProcessor findDocumentProcessor(Object object, Class<?> clazz) {
        DocumentProcessor processor = get(clazz);

        if (processor == null) {
            Class<?>[] interfaces = clazz.getInterfaces();

            for (Class<?> interfac : interfaces) {
                processor = findDocumentProcessor(object, interfac);

                if (processor != null) {
                    return processor;
                }

            }

            if (!clazz.isInterface()) {
                return findDocumentProcessor(object, clazz.getSuperclass());
            }

        }

        return processor;

    }

    /**
     * @see DocumentProcessor#getDocumentKey(Object)
     */
    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document document,
                            String parent) {

        DocumentProcessor processor = findDocumentProcessor(object, object.getClass());

        if (processor == null) {
            throw new IllegalArgumentException();
        }

        if (document == null) {
            document = documents.get(processor.getDocumentKey(object));

            if (document == null) {
                document = FAKE_DOCUMENT;
            }

        }

        processor.process(parentProcessor, documents, object, document, parent);

        return document;

    }

    /**
     * @see DocumentProcessor#getDocumentKey(Object)
     */
    public Object getDocumentKey(Object object) {
        DocumentProcessor processor = findDocumentProcessor(object, object.getClass());

        if (processor != null) {
            return processor.getDocumentKey(object);

        } else {
            return null;
        }

    }

}
