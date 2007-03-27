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
package org.apache.tuscany.spi.loader;

/**
 * Used when a reference promoted by a composite is not found in any of the components defined within
 * the composite
 */
public class InvalidPromotedReferenceException extends LoaderException {
    private static final long serialVersionUID = -2056138384524620064L;

    public InvalidPromotedReferenceException(String message) {
        super(message);
    }

    public InvalidPromotedReferenceException(String message, String identifier) {
        super(message, identifier);
    }

    public InvalidPromotedReferenceException(Throwable cause) {
        super(cause);
    }
}
