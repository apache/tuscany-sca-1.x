/**
 *
 * Copyright 2006 The Apache Software Foundation or its licensors, as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.tuscany.core.implementation.processor;

import org.apache.tuscany.core.implementation.ProcessingException;

/**
 * Thrown when an implementation has more than one reference injection site with the same name
 *
 * @version $Rev$ $Date$
 */
public class DuplicateReferenceException extends ProcessingException {
    public DuplicateReferenceException() {
    }

    public DuplicateReferenceException(String message) {
        super(message);
    }

    public DuplicateReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateReferenceException(Throwable cause) {
        super(cause);
    }
}
