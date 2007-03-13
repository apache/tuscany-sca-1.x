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
package org.apache.tuscany.assembly.util;



/**
 * Represents a model object that can be marked undefined. This can be used to represent
 * pointers to missing elements (e.g. an Include of a missing composite) or temporarily mark
 * pointed objects as undefined until they are actually loaded and initialized.
 */
public interface Undefinable {
    
    /**
     * Returns true if the implementation is undefined.
     * @return true if the implementation is undefined.
     */
    boolean isUndefined();
    
    /**
     * Sets whether the implementation is undefined.
     * @param undefined whether the implementation is undefined
     */
    void setUndefined(boolean undefined);

}
