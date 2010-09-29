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

package org.apache.tuscany.sca.binding.jsonp.runtime;

import java.util.List;

import org.apache.tuscany.sca.binding.jsonp.JSONPBinding;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.provider.ReferenceBindingProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentReference;

public class JSONPReferenceBindingProvider implements ReferenceBindingProvider {

    private RuntimeComponentReference reference;
    private JSONPBinding binding;
    private InterfaceContract contract;

    public JSONPReferenceBindingProvider(RuntimeComponent component,
                                         RuntimeComponentReference reference,
                                         JSONPBinding binding) {
        this.reference = reference;
        this.binding = binding;
        
        try {
            contract = (InterfaceContract)reference.getInterfaceContract().clone();
        } catch (Exception ex){
            // we know this supports clone
        }
        
        contract.getInterface().resetDataBinding("JSON2x");
        
        // force array types to map to JSON also
        for (Operation operation : contract.getInterface().getOperations()){
        	DataType<List<DataType>> inputTypes = operation.getInputType();
        	for (DataType inputType : inputTypes.getLogical()){
        		if ("java:array".equals(inputType.getDataBinding())){
        			inputType.setDataBinding("JSON2x");
        		}
        	}
            DataType outputType = operation.getOutputType();
            if (outputType != null){
                if ("java:array".equals(outputType.getDataBinding())){
                    outputType.setDataBinding("JSON2x");
                }
            }
        }
    }
    public Invoker createInvoker(Operation operation) {
        return new JSONPInvoker(operation, binding);
    }

    public void start() {
    }

    public void stop() {
    }

    public InterfaceContract getBindingInterfaceContract() {
        return contract;
    }

    public boolean supportsOneWayInvocation() {
        return false;
    }

}
