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

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * The object that implements this interface carries a search's result data.
 * </p>
 * <p>
 * A result contains a value, which is the value displayed to the user. It also
 * contains a field name, which is the field the value is related to.
 * </p>
 * <p>
 * A search result may be split into different {@link Result} objects, each one
 * contained inside other, representing a complex result hierarchy. For example:
 * a result structure may represent as result a folder and files/folders
 * contained in it.
 * </p>
 * 
 * @see ResultProcessor
 * @see ResultFactory
 * 
 * @version $Rev$ $Date$
 */
public interface Result extends Serializable {

    /**
     * Returns the value held by this object.
     * 
     * @return the value held by this object
     */
    String getValue();

    /**
     * Sets the result value.
     * 
     * @param value the result value
     */
    void setValue(String value);

    /**
     * Returns the {@link Result} object that contains this object or
     * <code>null</code> if it's not contained in any object.
     * 
     * @return the {@link Result} object that contains this object or
     *         <code>null</code> if it's not contained in any object
     */
    Result getContainer();

    /**
     * A {@link Map} containing {@link Result} objects contained in this
     * {@link Object}. The contents are mapped from a key, which may be any kind
     * of identifier, the identifier is chosen by the {@link Result}
     * implementation.
     * 
     * @return the contents map
     */
    Map<String, Result> getContents();

    /**
     * Adds a {@link Result} content to this result.
     * 
     * @param artifactResult the {@link Result} content
     * @throw {@link UnsupportedOperationException} if the {@link Result}
     *        implementations does not support this operation
     * @see #getContents()
     */
    void addContent(Result artifactResult);

    /**
     * Removes a {@link Result} content from this result.
     * 
     * @param artifactResult the {@link Result} content
     * @throw {@link UnsupportedOperationException} if the {@link Result}
     *        implementations does not support this operation
     * @see #getContents()
     */
    void removeContent(Result artifactResult);

    /**
     * Sets this object's container.
     * 
     * @param container the object's container
     * @see #getContainer()
     */
    void setContainer(Result container);

    /**
     * Returns the field related to the result's value.
     * 
     * @return the field name
     */
    String getField();

    /**
     * Sets the field related to the result's value.
     * 
     * @param field the field name
     */
    void setField(String field);

}
