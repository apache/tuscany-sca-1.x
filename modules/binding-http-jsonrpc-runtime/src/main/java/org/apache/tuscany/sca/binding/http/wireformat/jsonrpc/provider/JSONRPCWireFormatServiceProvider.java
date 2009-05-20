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

package org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.provider;

import java.util.List;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.assembly.BindingRRB;
import org.apache.tuscany.sca.assembly.WireFormat;
import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.JSONRPCWireFormat;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.databinding.javabeans.SimpleJavaDataBinding;
import org.apache.tuscany.sca.databinding.json.JSONDataBinding;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.Interface;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.MessageFactory;
import org.apache.tuscany.sca.invocation.Phase;
import org.apache.tuscany.sca.provider.WireFormatProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;

/**
 * JSON-RPC Wire Format Service Provider
 * 
 * @version $Rev$ $Date$
 */
public class JSONRPCWireFormatServiceProvider implements WireFormatProvider {
    private MessageFactory messageFactory;
    
    private RuntimeComponent component;
    private RuntimeComponentService service;
    private InterfaceContract serviceContract;
    private Binding binding;

    public JSONRPCWireFormatServiceProvider(ExtensionPointRegistry extensionPoints,
                                            RuntimeComponent component, 
                                            RuntimeComponentService service, 
                                            Binding binding) {
        

        ModelFactoryExtensionPoint modelFactories = extensionPoints.getExtensionPoint(ModelFactoryExtensionPoint.class);
        messageFactory = modelFactories.getFactory(MessageFactory.class);
        
        this.component = component;
        this.service = service;
        this.serviceContract = service.getInterfaceContract();
        this.binding = binding;
    }
    
    public InterfaceContract configureWireFormatInterfaceContract(InterfaceContract interfaceContract) {
        this.serviceContract = interfaceContract;
        
        setDataBinding(serviceContract.getInterface());
        
        // Set default databinding to json
        serviceContract.getInterface().resetDataBinding(JSONDataBinding.NAME);
        
        return interfaceContract;
    }

    public Interceptor createInterceptor() {
        if(binding instanceof BindingRRB) {
            BindingRRB rrbBinding = (BindingRRB) binding;
            WireFormat wireFormat = rrbBinding.getRequestWireFormat();
            if(wireFormat != null && wireFormat instanceof JSONRPCWireFormat) {
                return new JSONRPCWireFormatInterceptor((HTTPBinding) binding, service.getRuntimeWire(binding), messageFactory);
            }
        }
        
        return null;
    }

    public String getPhase() {
        return Phase.SERVICE_BINDING_WIREFORMAT;
    }
    

    private void setDataBinding(Interface interfaze) {
        List<Operation> operations = interfaze.getOperations();
        for (Operation operation : operations) {
            operation.setDataBinding(JSONDataBinding.NAME);
            DataType<List<DataType>> inputType = operation.getInputType();
            if (inputType != null) {
                List<DataType> logical = inputType.getLogical();
                for (DataType inArg : logical) {
                    if (!SimpleJavaDataBinding.NAME.equals(inArg.getDataBinding())) {
                        inArg.setDataBinding(JSONDataBinding.NAME);
                    } 
                }
            }
            DataType outputType = operation.getOutputType();
            if (outputType != null) {
                if (!SimpleJavaDataBinding.NAME.equals(outputType.getDataBinding())) {
                    outputType.setDataBinding(JSONDataBinding.NAME);
                }
            }
        }
    }

}
