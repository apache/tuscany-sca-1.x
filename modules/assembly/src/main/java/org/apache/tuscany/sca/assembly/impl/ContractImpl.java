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
package org.apache.tuscany.sca.assembly.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.sca.assembly.AbstractContract;
import org.apache.tuscany.sca.assembly.ConfiguredOperation;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.policy.Intent;
import org.apache.tuscany.sca.policy.IntentAttachPointType;

/**
 * Implementation of a Contract.
 *
 * @version $Rev$ $Date$
 */
public class ContractImpl extends ExtensibleImpl implements AbstractContract {
    private InterfaceContract interfaceContract;
    private String name;
    private List<Intent> requiredIntents = new ArrayList<Intent>();
    private List<ConfiguredOperation>  configuredOperations = new ArrayList<ConfiguredOperation>();
    
    private boolean isCallback = false;
    
    /**
     * Constructs a new contract.
     */
    protected ContractImpl() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InterfaceContract getInterfaceContract() {
        return interfaceContract;
    }
    
    public void setInterfaceContract(InterfaceContract interfaceContract) {
        this.interfaceContract = interfaceContract;
    }
    
    public List<Intent> getRequiredIntents() {
        return requiredIntents;
    }

    public boolean isCallback() {
        return isCallback;
    }

    public void setIsCallback(boolean isCallback) {
        this.isCallback = isCallback;
    }

    public IntentAttachPointType getType() {
        return null;
    }

    public void setType(IntentAttachPointType type) {
    }
    
    public void setRequiredIntents(List<Intent> intents) {
        this.requiredIntents = intents;
    }
    
    public List<ConfiguredOperation> getConfiguredOperations() {
        return configuredOperations;
    }

    public void setConfiguredOperations(List<ConfiguredOperation> configuredOperations) {
        this.configuredOperations = configuredOperations;
    }

}
