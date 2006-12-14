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
package org.apache.tuscany.core.implementation.java;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.apache.tuscany.spi.ObjectFactory;
import org.apache.tuscany.spi.component.TargetException;
import org.apache.tuscany.spi.component.TargetNotFoundException;
import static org.apache.tuscany.spi.idl.java.JavaIDLUtils.findMethod;
import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.model.ServiceContract;
import org.apache.tuscany.spi.wire.InboundWire;
import org.apache.tuscany.spi.wire.RuntimeWire;
import org.apache.tuscany.spi.wire.TargetInvoker;

import org.apache.tuscany.core.implementation.PojoAtomicComponent;
import org.apache.tuscany.core.implementation.PojoConfiguration;
import org.apache.tuscany.core.injection.CallbackWireObjectFactory;
import org.apache.tuscany.core.injection.FieldInjector;
import org.apache.tuscany.core.injection.Injector;
import org.apache.tuscany.core.injection.InvalidAccessorException;
import org.apache.tuscany.core.injection.MethodInjector;
import org.apache.tuscany.spi.wire.WireObjectFactory;

/**
 * The runtime instantiation of Java component implementations
 *
 * @version $Rev$ $Date$
 */
public class JavaAtomicComponent extends PojoAtomicComponent {

    public JavaAtomicComponent(PojoConfiguration configuration) {
        super(configuration);
        this.scope = configuration.getScopeContainer().getScope();
    }

    public Object getServiceInstance(String name) throws TargetException {
        InboundWire wire = serviceWires.get(name);
        if (wire == null) {
            TargetNotFoundException e = new TargetNotFoundException(name);
            e.addContextName(getName());
            throw e;
        }
        return wireService.createProxy(wire);
    }

    public Object getServiceInstance() throws TargetException {
        if (serviceInterfaces.size() == 0) {
            return getTargetInstance();
        } else if (serviceInterfaces.size() == 1) {
            return getTargetInstance();
        } else {
            throw new TargetException("Component must have exactly one service");
        }
    }

    public TargetInvoker createTargetInvoker(String targetName, Operation operation, InboundWire callbackWire) {
        Method[] methods;
        Class callbackClass = null;
        if (operation.isCallback()) {
            callbackClass = operation.getServiceContract().getCallbackClass(); 
            methods = callbackClass.getMethods();

        } else {
            methods = operation.getServiceContract().getInterfaceClass().getMethods();
        }
        Method method = findMethod(operation, methods);
        return new JavaTargetInvoker(method, this, callbackWire, callbackClass, workContext, monitor);
    }

    protected void onServiceWire(InboundWire wire) {
        String name = wire.getCallbackReferenceName();
        if (name == null) {
            // It's ok not to have one, we just do nothing
            return;
        }
        Member member = callbackSites.get(name);
        if (member != null) {
            injectors.add(createCallbackInjector(member, wire.getServiceContract(), wire));
        }
    }

    protected Injector<Object> createCallbackInjector(Member member, ServiceContract<?> contract,
                                                      InboundWire inboundWire) {
        if (member instanceof Field) {
            Field field = (Field) member;
            ObjectFactory<?> factory = new CallbackWireObjectFactory(contract, wireService, inboundWire);
            return new FieldInjector<Object>(field, factory);
        } else if (member instanceof Method) {
            Method method = (Method) member;
            ObjectFactory<?> factory = new CallbackWireObjectFactory(contract, wireService, inboundWire);
            return new MethodInjector<Object>(method, factory);
        } else {
            throw new InvalidAccessorException("Member must be a field or method", member.getName());
        }
    }

    protected ObjectFactory<?> createWireFactory(RuntimeWire wire) {
        return new WireObjectFactory(wire, wireService);
    }
}
