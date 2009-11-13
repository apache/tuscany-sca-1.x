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
 * A map that holds a collection of {@link Document}s. Each {@link Document} key
 * should ideally be something that is unique across the entire contribution
 * documents. This class overrides {@link HashMap#get(Object)} method, so it
 * adds an empty {@link Document} object if the specified key is not found in
 * the map.
 * 
 * @version $Rev$ $Date$
 */
public class DocumentMap extends HashMap<Object, Document> {

    private static final long serialVersionUID = -2402910290314939928L;

    /**
     * Returns a {@link Document} object mapped from the specified key. If no
     * {@link Document} object is found from the specified key, a empty
     * {@link Document} object is created and added to the map.
     * 
     * @param key the {@link Document} key
     * @return the {@link Document} object mapped from the key
     */
    @Override
    public Document get(Object key) {
        Document doc = super.get(key);

        if (doc == null) {
            doc = new Document();
            put(key, doc);

        }

        return doc;

    }

}
