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

package org.apache.tuscany.core.databinding.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.component.ReferenceBinding;
import org.apache.tuscany.spi.component.ServiceBinding;
import org.apache.tuscany.spi.databinding.DataBinding;
import org.apache.tuscany.spi.databinding.DataBindingRegistry;
import org.apache.tuscany.spi.extension.AtomicComponentExtension;
import org.apache.tuscany.spi.model.DataType;
import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.wire.InboundInvocationChain;
import org.apache.tuscany.spi.wire.InboundWire;
import org.apache.tuscany.spi.wire.OutboundWire;
import org.apache.tuscany.spi.wire.WirePostProcessorExtension;

/**
 * This processor is responsible for enforcing the pass-by-value semantics
 * required of Remotable interfaces. This is done by adding a pass-by-value
 * interceptor to the inbound invocation chain of a target if the target
 * interface is Remotable.
 * 
 * @version $Rev$ $Date$
 */
public class PassByValueWirePostProcessor extends WirePostProcessorExtension {

    private DataBindingRegistry dataBindingRegistry;

    public PassByValueWirePostProcessor() {
        super();
    }

    /**
     * @param dataBindingRegistry the dataBindingRegistry to set
     */
    @Autowire
    public void setDataBindingRegistry(DataBindingRegistry dataBindingRegistry) {
        this.dataBindingRegistry = dataBindingRegistry;
    }

    public void process(OutboundWire source, InboundWire target) {
        PassByValueInterceptor interceptor;
        Operation<?> targetOperation;
        Operation<?> sourceOperation;
        DataBinding[] argsDataBindings;
        DataBinding resultDataBinding;

        boolean implAllowsPBR = false;
        boolean methodAllowsPBR = false;

        // if the source is a service binding or the target is a reference
        // binding do no
        // add interceptor since the bindings will ensure passbyvalue semantics
        if (!(source.getContainer() instanceof ServiceBinding || target.getContainer() instanceof ReferenceBinding)) {

            if (target.getContainer() instanceof AtomicComponentExtension) {
                implAllowsPBR = ((AtomicComponentExtension)target.getContainer()).isAllowsPassByReference();
            }

            Map<Operation<?>, InboundInvocationChain> chains = target.getInvocationChains();
            for (Map.Entry<Operation<?>, InboundInvocationChain> entry : chains.entrySet()) {
                targetOperation = entry.getKey();
                methodAllowsPBR = false;

                if (target.getContainer() instanceof AtomicComponentExtension) {
                    methodAllowsPBR =
                        ((AtomicComponentExtension)target.getContainer()).getPassByReferenceMethods()
                            .contains(targetOperation.getName());
                }

                if (target.getServiceContract().isRemotable() && (!implAllowsPBR && !methodAllowsPBR)) {
                    sourceOperation =
                        getSourceOperation(source.getInvocationChains().keySet(), targetOperation.getName());

                    if (null != sourceOperation) {
                        argsDataBindings = getParameterDataBindings(sourceOperation);
                        resultDataBinding = getResultDataBinding(sourceOperation);
                        interceptor = new PassByValueInterceptor(dataBindingRegistry);
                        interceptor.setParameterDatabindings(argsDataBindings);
                        interceptor.setResultDataBinding(resultDataBinding);
                        source.getInvocationChains().get(sourceOperation).addInterceptor(0, interceptor);
                        /*
                         * tailInterceptor =
                         * source.getInvocationChains().get(sourceOperation).getTailInterceptor();
                         * if (tailInterceptor != null) {
                         * tailInterceptor.setNext(passByValueInterceptor); }
                         */
                    }
                }
            }

            // Check if there's a callback
            Map callbackOperations = source.getServiceContract().getCallbackOperations();
            implAllowsPBR = false;

            if (callbackOperations != null && !callbackOperations.isEmpty()) {
                if (source.getContainer() instanceof AtomicComponentExtension) {
                    implAllowsPBR = ((AtomicComponentExtension)source.getContainer()).isAllowsPassByReference();
                }

                Object targetAddress = source.getContainer().getName();
                Map<Operation<?>, InboundInvocationChain> callbackChains = source.getTargetCallbackInvocationChains();
                for (Map.Entry<Operation<?>, InboundInvocationChain> entry : callbackChains.entrySet()) {
                    targetOperation = entry.getKey();
                    methodAllowsPBR = false;

                    if (source.getContainer() instanceof AtomicComponentExtension) {
                        methodAllowsPBR =
                            ((AtomicComponentExtension)source.getContainer()).getPassByReferenceMethods()
                                .contains(targetOperation.getName());
                    }

                    if (source.getServiceContract().isRemotable() && (!implAllowsPBR && !methodAllowsPBR)) {
                        sourceOperation =
                            getSourceOperation(target.getSourceCallbackInvocationChains(targetAddress).keySet(),
                                               targetOperation.getName());

                        argsDataBindings = getParameterDataBindings(targetOperation);
                        resultDataBinding = getResultDataBinding(targetOperation);

                        interceptor = new PassByValueInterceptor(dataBindingRegistry);
                        interceptor.setParameterDatabindings(argsDataBindings);
                        interceptor.setResultDataBinding(resultDataBinding);

                        entry.getValue().addInterceptor(0, interceptor);
                        /*
                         * tailInterceptor =
                         * target.getSourceCallbackInvocationChains(targetAddress).get(sourceOperation)
                         * .getTailInterceptor(); if (tailInterceptor != null) {
                         * tailInterceptor.setNext(passByValueInterceptor); }
                         */
                    }
                }
            }
        }
    }

    public void process(InboundWire source, OutboundWire target) {
        // to be done if required..
    }

    private Operation getSourceOperation(Set<Operation<?>> operations, String operationName) {
        for (Operation<?> op : operations) {
            if (op.getName().equals(operationName)) {
                return op;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private DataBinding[] getParameterDataBindings(Operation operation) {
        List<DataType<?>> argumentTypes = (List<DataType<?>>)operation.getInputType().getLogical();
        DataBinding[] argDataBindings = new DataBinding[argumentTypes.size()];
        int count = 0;
        for (DataType argType : argumentTypes) {
            argDataBindings[count++] = dataBindingRegistry.getDataBinding(argType.getDataBinding());
        }
        return argDataBindings;
    }

    private DataBinding getResultDataBinding(Operation operation) {
        DataType<?> resultType = (DataType<?>)operation.getOutputType();
        return dataBindingRegistry.getDataBinding(resultType.getDataBinding());
    }
}
