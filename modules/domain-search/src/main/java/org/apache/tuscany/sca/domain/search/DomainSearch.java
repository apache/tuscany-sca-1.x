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

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.tuscany.sca.contribution.Contribution;
import org.osoa.sca.annotations.Remotable;

/**
 * This is the interface that must be implemented by the service that provides
 * the functionality index/search and search contribution data.
 * 
 * @version $Rev$ $Date$
 */
@Remotable
public interface DomainSearch {

    /**
     * Returns <code>true</code> if the index is somehow created already, otherwise <code>false</code>.
     * 
     * @return <code>true</code> if the index is somehow created already, otherwise <code>false</code>
     */
    boolean indexExists();

    /**
     * Parse a query string, execute it against the index and return the results.
     * 
     * @param searchQuery the string containing the search query
     * @param highlight <code>true</code> if the results should be highlighted
     * 
     * @return an array containing the search results
     * 
     * @throws CorruptIndexException
     * @throws IOException
     * @throws ParseException
     */
    Result[] parseAndSearch(String searchQuery, boolean highlight) throws IndexException, ParseException;

    /**
     * Executes a search query against the index and return the results.
     * 
     * @param searchQuery the search query
     * @param hightlight highlight <code>true</code> if the results should be highlighted
     * 
     * @return an array containing the search results
     * 
     * @throws CorruptIndexException
     * @throws IOException
     */
    Result[] search(Query searchQuery, boolean hightlight) throws IndexException;

    /**
     * Highlights in the specified text strings that matches the specified search query. The matches can be filtered by field. 
     * 
     * @param field the field to filter
     * @param text the text to be highlighted
     * @param searchQuery the search query
     * 
     * @return the text highlighted
     * 
     * @throws ParseException
     * @throws IOException
     */
    String highlight(String field, String text, String searchQuery) throws ParseException;

    /**
     * Adds a contribution to the index.
     * 
     * @param contribution the contribution to be added
     */
    void addContribution(Contribution contribution) throws IndexException;

    /**
     * Removes a contribution from the index.
     * 
     * @param contribution the contribution to be removed
     */
    void removeContribution(Contribution contribution) throws IndexException;

    /**
     * Updates a contribution in the index.
     * 
     * @param oldContribution the old contribution state
     * @param contribution the new contribution state
     */
    void updateContribution(Contribution oldContribution, Contribution contribution) throws IndexException;

}
