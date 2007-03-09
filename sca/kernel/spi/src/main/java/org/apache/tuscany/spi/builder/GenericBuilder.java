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
package org.apache.tuscany.spi.builder;

import org.apache.tuscany.spi.component.SCAObject;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.model.ModelObject;

/**
 * Responsible for building a {@link SCAObject} from an extensibility element in
 * the SCDL
 * 
 * @version $Rev$ $Date$
 */
public interface GenericBuilder<S extends SCAObject, M extends ModelObject> {
    /**
     * Build a SCAObject from an extensibility element in the SCDL
     * 
     * @param parent The parent SCAObject
     * @param modelObject The model object
     * @param deploymentContext The deployment context
     * @return A SCAObject representing the runtime metdata for the extension
     * @throws BuilderException
     */
    S build(SCAObject parent, M modelObject, DeploymentContext deploymentContext) throws BuilderException;
}
