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
 * Interface that provides access to other services.
 * Any ServiceReference can be cast to the business interface of the service to
 * which it refers.
 *
 * @version $Rev$ $Date$
 */
public interface ServiceReference {
    /**
     * Returns the session ID.
     *
     * @return the session ID
     */
    Object getSessionID();

    /**
     * End the client's session with the referenced service.
     */
    void endSession();

    /**
     * Returns the callback ID.
     *
     * @return the callback ID
     */
    Object getCallbackID();

    /**
     * Sets the callback ID.
     *
     * @param callbackID the callback ID
     */
    void setCallbackID(Object callbackID);

    /**
     * Returns the callback object.
     *
     * @return the callback object
     */
    Object getCallback();

    /**
     * Sets the callback object.
     *
     * @param callback the callback object
     */
    void setCallback(Object callback);
}
