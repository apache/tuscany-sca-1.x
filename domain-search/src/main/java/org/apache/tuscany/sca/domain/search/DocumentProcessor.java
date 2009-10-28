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

import org.apache.tuscany.sca.domain.search.impl.Document;

/**
 * A {@link DocumentProcessor} implementations knows how to extract data from a
 * contribution {@link Object}. The extract data is added to a {@link Document},
 * which is stored in a {@link DocumentMap}.
 * 
 * @version $Rev$ $Date$
 */
public interface DocumentProcessor {

    final public static Document FAKE_DOCUMENT = new Document();

    /**
     * Process a contribution {@link Object}, extracting from it data that
     * should be indexed. The data should be add to a {@link Document} object,
     * which can be found accessing the {@link DocumentMap} if it's not passed
     * as an argument. The key used to find the {@link Document} object should
     * be the one returned by {@link #getDocumentKey(Object)} method.
     * 
     * @param parentProcessor the processor that invoked this processor, if any
     * @param documents the {@link DocumentMap} object
     * @param object the object where data should be extracted from
     * @param document the {@link Document} object to store the extracted data
     * @param parent string that represent the object's parent path in the
     *            contribution
     * @return the resulted {@link Document} object
     */
    Document process(DocumentProcessor parentProcessor,
                     DocumentMap documents,
                     Object object,
                     Document document,
                     String parent);

    /**
     * Returns a object key generated from object passed as argument. The key
     * should be unique in a contribution.
     * 
     * @param object the object
     * @return a key generated from the object
     */
    Object getDocumentKey(Object object);

}
