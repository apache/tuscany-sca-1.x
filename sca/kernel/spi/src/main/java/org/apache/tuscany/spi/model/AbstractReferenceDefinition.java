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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

/**
 * Represents a component reference
 *
 * @version $Rev$ $Date$
 */
public abstract class AbstractReferenceDefinition extends ModelObject {
    private String name;
    private ServiceContract serviceContract;
    private Multiplicity multiplicity;
    private boolean autowire;
    private List<BindingDefinition> bindings;
    private List<URI> targets = new ArrayList<URI>();
    private boolean wiredByImpl;
    private List requiredIntents = new ArrayList<QName>();
    private List policySets = new ArrayList<QName>();

    public AbstractReferenceDefinition() {
        autowire = false;
        multiplicity = Multiplicity.ONE_ONE;
        bindings = new ArrayList<BindingDefinition>();
    }

    public AbstractReferenceDefinition(String name, ServiceContract serviceContract) {
        this.name = name;
        this.serviceContract = serviceContract;
        bindings = new ArrayList<BindingDefinition>();
        autowire = false;
        multiplicity = Multiplicity.ONE_ONE;
    }

    public AbstractReferenceDefinition(String name, ServiceContract serviceContract, Multiplicity multiplicity) {
        this.name = name;
        this.serviceContract = serviceContract;
        this.multiplicity = multiplicity;
        bindings = new ArrayList<BindingDefinition>();
        autowire = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceContract<?> getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(ServiceContract serviceContract) {
        this.serviceContract = serviceContract;
    }

    public Multiplicity getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(Multiplicity multiplicity) {
        this.multiplicity = multiplicity;
    }

    public boolean isAutowire() {
        return autowire;
    }

    public void setAutowire(boolean autowire) {
        this.autowire = autowire;
    }

    public List<BindingDefinition> getBindings() {
        return Collections.unmodifiableList(bindings);
    }

    public void addBinding(BindingDefinition binding) {
        this.bindings.add(binding);
    }
    
    public List<URI> getTargets() {
        return Collections.unmodifiableList(targets);
    }

    public void addTarget(URI targetURI) {
        this.targets.add(targetURI);
    }
    
    public boolean isWiredByImpl() {
        return wiredByImpl;
    }

    public void setWiredByImpl(boolean wiredByImpl) {
        this.wiredByImpl = wiredByImpl;
    }

    public List getPolicySets() {
        return policySets;
    }

    public void setPolicySets(List policySets) {
        this.policySets = policySets;
    }

    public List getRequiredIntents() {
        return requiredIntents;
    }

    public void setRequiredIntents(List requiredIntents) {
        this.requiredIntents = requiredIntents;
    }
}
