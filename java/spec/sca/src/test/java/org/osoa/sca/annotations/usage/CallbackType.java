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
package org.osoa.sca.annotations.usage;

import org.osoa.sca.annotations.Callback;

/**
 * Mock object for callback annotation tests.
 *
 * @version $Rev$ $Date$
 */
@Callback(Object.class)
public class CallbackType {
    @Callback
    private Object cbField;

    /**
     * Site for testing annotation of a public method.
     */
    @Callback
    public void cbMethod() {
        cbField = new Object();
    }

    /**
     * Mock method to make IDEs stop complaining.
     *
     * @return nothing
     */
    public Object getCbField() {
        return cbField;
    }
}
