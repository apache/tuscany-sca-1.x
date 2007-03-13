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
package org.apache.tuscany.assembly.model;

import java.util.List;

import org.apache.tuscany.assembly.util.Extensible;
import org.apache.tuscany.assembly.util.Undefinable;
import org.apache.tuscany.policy.model.IntentAttachPoint;



/**
 * A constrainingType provides the "shape" for a component and its implementation.
 * Any component configuration that points to a constrainingType is constrained by this shape.
 * The constrainingType specifies the services, references and properties that must be implemented.
 */
public interface ConstrainingType extends Extensible, IntentAttachPoint, Undefinable {

    /**
     * Returns a list of services that are offered.
     * @return a list of services that are offered
     */
    List<AbstractService> getServices();

    /**
     * Returns the list of references that are used.
     * @return the list of references that are used
     */
    List<AbstractReference> getReferences();

    /**
     * Returns the list of properties that can be set.
     * @return the list of properties that can be set
     */
    List<AbstractProperty> getProperties();

}
