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

package org.apache.tuscany.databinding.sdo;

import org.apache.tuscany.spi.builder.BuilderException;
import org.apache.tuscany.spi.component.ComponentRegistrationException;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.SCAObject;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.GenericBuilderExtension;

import commonj.sdo.helper.HelperContext;

/**
 * @version $Rev$ $Date$
 */
public class ImportSDOBuilder extends GenericBuilderExtension<SCAObject, ImportSDO> {

    @Override
    protected Class<ImportSDO> getModelType() {
        return ImportSDO.class;
    }

    public SCAObject build(SCAObject parent, ImportSDO modelObject, DeploymentContext deploymentContext)
        throws BuilderException {
        if (parent instanceof CompositeComponent) {
            CompositeComponent component = (CompositeComponent)parent;
            SDOHelperContext obj = new SDOHelperContext(component, modelObject.getHelperContext());
            try {
                component.registerJavaObject(obj.getName(), HelperContext.class, obj.getHelperContext());
            } catch (ComponentRegistrationException e) {
                throw new IllegalArgumentException(e);
            }
            return obj;
        } else {
            return null;
        }
    }

}
