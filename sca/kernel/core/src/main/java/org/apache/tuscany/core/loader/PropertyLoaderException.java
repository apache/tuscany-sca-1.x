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
package org.apache.tuscany.core.loader;

import org.apache.tuscany.spi.loader.LoaderException;

/**
 * Root unchecked exception for the injection package
 *
 * @version $Rev: 487057 $ $Date: 2006-12-14 12:50:44 +0530 (Thu, 14 Dec 2006) $
 */
public class PropertyLoaderException extends LoaderException {

    private String propertyName; 
    
    public PropertyLoaderException() {
        super();
    }

    public PropertyLoaderException(String message) {
        super(message);
    }


    protected PropertyLoaderException(String message, String identifier) {
        super(message, identifier);
    }

    public PropertyLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    protected PropertyLoaderException(String message, String identifier, Throwable cause) {
        super(message, identifier, cause);
    }

    public PropertyLoaderException(Throwable cause) {
        super(cause);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
