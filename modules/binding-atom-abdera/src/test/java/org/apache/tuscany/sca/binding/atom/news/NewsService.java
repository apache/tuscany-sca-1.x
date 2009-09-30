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

package org.apache.tuscany.sca.binding.atom.news;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.tuscany.sca.data.collection.Collection;
import org.apache.tuscany.sca.data.collection.Entry;
import org.apache.tuscany.sca.data.collection.NotFoundException;
import org.osoa.sca.annotations.Remotable;

@Remotable
//@XmlSeeAlso(Headline.class)
public interface NewsService /*extends Collection<String, Headline>*/{

    /**
     * Get the whole collection.
     * 
     * @return the whole collection.
     */
    Entry<String, Headline>[] getAll();

    /**
     * Returns a collection resulting from a query.
     * 
     * @return the collection.
     */
    Entry<String, Headline>[] query(String queryString);

    /**
     * Creates a new item.
     * 
     * @param key
     * @param item
     * @return
     */
    String post(String key, Headline item);

    /**
     * Retrieves an item.
     * 
     * @param key
     * @return
     */
    Headline get(String key) throws NotFoundException;

    /**
     * Updates an item.
     * 
     * @param key
     * @param item
     * @return
     */
    void put(String key, Headline item) throws NotFoundException;

    /**
     * Delete an item.
     * 
     * @param key
     */
    void delete(String key) throws NotFoundException;

    
}
