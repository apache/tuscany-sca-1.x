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
package org.apache.tuscany.container.script;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.V1_5;

import java.util.Arrays;

import org.apache.tuscany.spi.ObjectCreationException;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.TargetResolutionException;
import org.apache.tuscany.spi.component.WorkContext;
import org.apache.tuscany.spi.extension.AtomicComponentExtension;
import org.apache.tuscany.spi.extension.ExecutionMonitor;
import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.model.Scope;
import org.apache.tuscany.spi.model.ServiceContract;
import org.apache.tuscany.spi.services.work.WorkScheduler;
import org.apache.tuscany.spi.wire.InboundWire;
import org.apache.tuscany.spi.wire.OutboundWire;
import org.apache.tuscany.spi.wire.TargetInvoker;
import org.apache.tuscany.spi.wire.WireObjectFactory;
import org.apache.tuscany.spi.wire.WireService;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

/**
 * A component implementation for script languages.
 */
public class ScriptComponent extends AtomicComponentExtension {

    private ScriptInstanceFactory factory;

    public ScriptComponent(String name,
                           CompositeComponent parent,
                           WireService wireService,
                           WorkContext workContext,
                           WorkScheduler workScheduler,
                           ExecutionMonitor monitor,
                           int initLevel,
                           ScriptInstanceFactory factory,
                           Scope scope) {
        super(name, parent, wireService, workContext, workScheduler, monitor, initLevel);
        this.factory = factory;
        this.scope = scope;
        setPassByReferenceMethods(Arrays.asList(new String[]{}));
    }

    public Object createInstance() throws ObjectCreationException {
        return factory.getInstance(); 
    }

    public TargetInvoker createTargetInvoker(String targetName, Operation operation, InboundWire callbackWire) {
        return new ScriptTargetInvoker(operation.getName(), this);
    }

    @SuppressWarnings({"unchecked"})
    protected void onReferenceWire(OutboundWire wire) {
        Class<?> clazz = wire.getServiceContract().getInterfaceClass();
        if (clazz == null) {
            clazz = createInterfaceClass(wire.getServiceContract());
        }
        factory.addContextObjectFactory(wire.getReferenceName(), clazz, new WireObjectFactory(clazz, wire, wireService));
    }

    public Object getTargetInstance() throws TargetResolutionException {
        return scopeContainer.getInstance(this);
    }

    /**
     * Create an Java interface class for the WSDL ServiceContract
     * TODO: this should probably be moved to wsdl idl module
     */
    private Class createInterfaceClass(ServiceContract serviceContract) {
        ClassWriter cw = new ClassWriter(false);

        // Generate the interface
        String interfaceName = serviceContract.getInterfaceName();
        cw.visit(V1_5,
                 ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                 interfaceName,
                 null,
                 "java/lang/Object",
                 new String[0]);

        // Generate methods from the WSDL operations
        for (Object o : serviceContract.getOperations().values()) {
            Operation operation = (Operation)o;
            String inputType = Type.getDescriptor(Object.class);
            String outputType = Type.getDescriptor(Object.class);
            cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT,
                           operation.getName(),
                           "(" + inputType + ")" + outputType,
                           null,
                           null).visitEnd();
        }

        // Generate the bytecodes
        cw.visitEnd();
        byte[] bytes = cw.toByteArray();

        Class interfaceClass = new GeneratedClassLoader().defineClass(bytes);

        return interfaceClass;
    }

    private class GeneratedClassLoader extends ClassLoader {
        public Class defineClass(byte[] byteArray) {
            try {
                return defineClass(null, byteArray, 0, byteArray.length);
            } catch (Throwable e) {
                return null;
            }
        }
    }
}
