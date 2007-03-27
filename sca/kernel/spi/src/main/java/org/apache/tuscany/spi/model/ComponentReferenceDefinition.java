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
package org.apache.tuscany.spi.model;

import java.net.URI;
import java.util.List;

/**
 * Represents reference defintion that is part of a Component definition
 * 
 */
public class ComponentReferenceDefinition extends AbstractReferenceDefinition  {
    private ComponentTypeReferenceDefinition associatedCompTypeRefDefn;
    
    public ComponentReferenceDefinition(ComponentTypeReferenceDefinition ctRefDefn) {
        super();
        setMultiplicity(null);
        this.associatedCompTypeRefDefn = ctRefDefn;
    }

    public ComponentReferenceDefinition(String name, ServiceContract serviceContract, ComponentTypeReferenceDefinition ctRefDefn) {
        super(name, serviceContract, null);
        this.associatedCompTypeRefDefn = ctRefDefn;
    }

    public ComponentReferenceDefinition(String name, ServiceContract serviceContract, Multiplicity multiplicity, ComponentTypeReferenceDefinition ctRefDefn) {
        super(name, serviceContract, multiplicity);
        this.associatedCompTypeRefDefn = ctRefDefn;
    }

    @Override
    public String getName() {
        return associatedCompTypeRefDefn.getName();
    }

    public ComponentTypeReferenceDefinition getAssociatedCompTypeRefDefn() {
        return associatedCompTypeRefDefn;
    }

    public void setAssociatedCompTypeRefDefn(ComponentTypeReferenceDefinition associatedCompTypeRefDefn) {
        this.associatedCompTypeRefDefn = associatedCompTypeRefDefn;
    }

    @Override
    public Multiplicity getMultiplicity() {
        if (super.getMultiplicity() != null) {
            return super.getMultiplicity();
        } else {
            return associatedCompTypeRefDefn.getMultiplicity();
        }
    }

    @Override
    public List<URI> getTargets() {
        if (super.getTargets() == null || super.getTargets().isEmpty()) {
            return associatedCompTypeRefDefn.getTargets();
        } else {
            return super.getTargets();
        }
    }

    @Override
    public ServiceContract<?> getServiceContract() {
        if (super.getServiceContract() == null) {
            return associatedCompTypeRefDefn.getServiceContract();
        } else {
            return super.getServiceContract();
        }
    }

    @Override
    public List<BindingDefinition> getBindings() {
        if (super.getBindings() == null || super.getBindings().size() == 0) {
            return associatedCompTypeRefDefn.getBindings();
        } else {
            return super.getBindings();
        }
    }
}
