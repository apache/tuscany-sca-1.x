/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.core.config;

/**
 * Exception that indicates an expected resource could not be found.
 *
 * @version $Rev$ $Date$
 */
public class MissingResourceException extends ConfigurationException {
    /**
     * Constructor that indicates which resource could not be found.
     * The supplied parameter is also returned as the message.
     *
     * @param resource the resource that could not be found
     */
    public MissingResourceException(String resource) {
        super(resource);
    }

    /**
     * Return the name of the expected resource.
     *
     * @return the name of the expected resource
     */
    public String getResource() {
        return getMessage();
    }
}
