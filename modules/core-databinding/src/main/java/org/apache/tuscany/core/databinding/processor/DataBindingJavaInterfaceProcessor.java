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

package org.apache.tuscany.core.databinding.processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tuscany.databinding.annotation.DataBinding;
import org.apache.tuscany.interfacedef.DataType;
import org.apache.tuscany.interfacedef.InvalidInterfaceException;
import org.apache.tuscany.interfacedef.Operation;
import org.apache.tuscany.interfacedef.java.JavaInterface;
import org.apache.tuscany.interfacedef.java.introspect.JavaInterfaceIntrospectorExtension;
import org.apache.tuscany.spi.databinding.DataBindingRegistry;
import org.osoa.sca.annotations.Reference;

/**
 * The databinding annotation processor for java interfaces
 * 
 * @version $Rev$ $Date$
 */
public class DataBindingJavaInterfaceProcessor implements JavaInterfaceIntrospectorExtension {
    private DataBindingRegistry dataBindingRegistry;

    public DataBindingJavaInterfaceProcessor(@Reference
    DataBindingRegistry dataBindingRegistry) {
        super();
        this.dataBindingRegistry = dataBindingRegistry;
    }

    public void visitInterface(JavaInterface javaInterface) throws InvalidInterfaceException {
        if (!javaInterface.isRemotable()) {
            return;
        }
        List<Operation> operations = javaInterface.getOperations();
        processInterface(javaInterface, operations);
    }

    private void introspectWrapperStyle(Operation operation) {
        if (operation.isWrapperStyle()) {
            return;
        }
        DataType outputType = operation.getOutputType();
        DataType<List<DataType>> inputType = operation.getInputType();
        if (outputType != null || inputType == null) {
            return;
        }
        if (inputType.getLogical().size() != 1) {
            return;
        }
        DataType wrapperType = inputType.getLogical().get(0);
        if (outputType.getDataBinding().equals(wrapperType.getDataBinding())) {
            operation.setWrapperStyle(true);
            operation.setDataBinding(outputType.getDataBinding());
        }
    }

    private void processInterface(JavaInterface javaInterface, List<Operation> operations) {
        Class<?> clazz = javaInterface.getJavaClass();
        DataBinding dataBinding = clazz.getAnnotation(DataBinding.class);
        String dataBindingId = null;
        boolean wrapperStyle = false;
        if (dataBinding != null) {
            dataBindingId = dataBinding.value();
            wrapperStyle = dataBinding.wrapperStyle();
        }

        Map<String, Operation> opMap = new HashMap<String, Operation>();
        for (Operation op : javaInterface.getOperations()) {
            opMap.put(op.getName(), op);
            if (dataBindingId != null) {
                op.setDataBinding(dataBindingId);
                op.setWrapperStyle(wrapperStyle);
            }
        }
        for (Method method : clazz.getMethods()) {
            Operation operation = opMap.get(method.getName());
            dataBinding = clazz.getAnnotation(DataBinding.class);
            dataBindingId = null;
            wrapperStyle = false;
            if (dataBinding != null) {
                dataBindingId = dataBinding.value();
                wrapperStyle = dataBinding.wrapperStyle();
                operation.setDataBinding(dataBindingId);
                operation.setWrapperStyle(wrapperStyle);
            }            

            // FIXME: We need a better way to identify simple java types
            for (org.apache.tuscany.interfacedef.DataType<?> d : operation.getInputType().getLogical()) {
                dataBindingRegistry.introspectType(d, method.getAnnotations());
            }
            if (operation.getOutputType() != null) {
                dataBindingRegistry.introspectType(operation.getOutputType(), method.getAnnotations());
            }
            for (org.apache.tuscany.interfacedef.DataType<?> d : operation.getFaultTypes()) {
                dataBindingRegistry.introspectType(d, method.getAnnotations());
            }

            introspectWrapperStyle(operation);
        }
    }
}
