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

import org.apache.lucene.document.Document;

/**
 * This interface should be implemented by classes that process a
 * {@link Document} object resulted from a search and create a {@link Result}
 * object from it.
 * 
 * @version $Rev$ $Date$
 */
public interface ResultProcessor {

    /**
     * <p>
     * Process the {@link Document} object and returns a {@link Result} object
     * containing the processed data.
     * </p>
     * <p>
     * A {@link Result} object may be specified to hold the processed data. If
     * no object is specified, the implementation must create a new object a
     * return it.
     * </p>
     * 
     * @param document the {@link Document} object resulted from a search
     * @param result the {@link Result} object that will hold the processed data
     *            or <code>null</code> a new object should be returned.
     * @return a {@link Result} object containing the processed data
     */
    Result process(Document document, Result result);

}
