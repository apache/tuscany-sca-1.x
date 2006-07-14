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
package org.osoa.sca;


/**
 * Interface that can be used by SCA Components to access information about the
 * Module that contains them.
 *
 * @version $Rev: 388784 $ $Date: 2006-03-25 08:34:51 -0800 (Sat, 25 Mar 2006) $
 */
public interface CompositeContext {

    /**
     * Returns an object implementing the interface defined for the named service.
     *
     * @param serviceName the name of another service in the current module
     * @return an object that implements the service's interface
     */
    <T> T locateService(Class<T> serviceInterface, String serviceName);

}
