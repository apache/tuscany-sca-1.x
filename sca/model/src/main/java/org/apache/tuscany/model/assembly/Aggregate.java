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
package org.apache.tuscany.model.assembly;

import java.util.List;

/**
 * A representation of the model object '<em><b>Fragment</b></em>'.
 */
public interface Aggregate extends ExtensibleModelObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     */
    String getName();

    /**
     * Sets the value of the '{@link org.osoa.sca.model.ModuleFragment#getName <em>Name</em>}' attribute.
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Entry Points</b></em>' containment reference list.
     */
    List<EntryPoint> getEntryPoints();

    /**
     * Returns the named entry point
     *
     * @param name
     * @return
     */
    EntryPoint getEntryPoint(String name);

    /**
     * Returns the value of the '<em><b>Components</b></em>' containment reference list.
     */
    List<Component> getComponents();

    /**
     * Returns the named component.
     *
     * @return
     */
    Component getComponent(String name);

    /**
     * Returns the value of the '<em><b>External Services</b></em>' containment reference list.
     */
    List<ExternalService> getExternalServices();

    /**
     * Returns the named external service
     *
     * @param name
     * @return
     */
    ExternalService getExternalService(String name);

    /**
     * Returns the configured service at the given address.
     *
     * @param address
     * @return
     */
    ConfiguredService getConfiguredService(ServiceURI address);

    /**
     * Returns the parts in this aggregate
     *
     * @return
     */
    List<Part> getParts();

} // TAggregate
