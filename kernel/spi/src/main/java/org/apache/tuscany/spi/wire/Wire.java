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
package org.apache.tuscany.spi.wire;

import java.net.URI;
import javax.xml.namespace.QName;

import org.apache.tuscany.spi.component.TargetResolutionException;
import org.apache.tuscany.spi.model.ServiceContract;

/**
 * The base wire type used to connect references and serviceBindings
 *
 * @version $$Rev$$ $$Date$$
 */
public interface Wire {
    QName LOCAL_BINDING = new QName("http://tuscany.apache.org/xmlns/sca/binding/1.0", "binding.local");

    /**
     * Returns the wire URI
     *
     * @return the wire URI
     */
    URI getUri();

    /**
     * Sets the wire URI
     *
     * @param uri the uri
     */
    void setUri(URI uri);

    /**
     * Returns the wire binding type
     *
     * @return the wire binding type
     */
    QName getBindingType();

    /**
     * Returns the non-proxied target instance for this wire
     */
    Object getTargetService() throws TargetResolutionException;

    /**
     * Returns the service contract associated with the wire
     *
     * @return the service contract associated with the wire
     */
    ServiceContract getServiceContract();

    /**
     * Sets the contract associated with the wire
     *
     * @param contract the contract associated with the wire
     */
    void setServiceContract(ServiceContract contract);

    /**
     * Returns true if its invocation chains may be bypassed
     */
    boolean isOptimizable();

    /**
     * Determines if the wire may be optimized
     *
     * @param optimizable true if the wire is optimizable
     */
    void setOptimizable(boolean optimizable);

}
