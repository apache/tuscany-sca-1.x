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
package org.apache.tuscany.sca.implementation.xyz;

import java.lang.reflect.Method;

import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.provider.ImplementationProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;
import org.osoa.sca.ServiceRuntimeException;

class ImplementationXYZProvider implements ImplementationProvider {
    
    private ImplementationXYZ implementation;
    private Object instance;

    /**
     * Constructs a new CRUD implementation.
     */
    ImplementationXYZProvider(RuntimeComponent component, ImplementationXYZ implementation) {
        this.implementation = implementation;
        
        // Create a new instance of the POJO
        try {
            //pojoInstance = implementation.getPOJOClass().newInstance();
        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        }
    }

    public void start() {
        try {
            // Invoke the POJO's init method
            Method initMethod = implementation.getMethods().get("init");
            if (initMethod != null) {
                //initMethod.invoke(pojoInstance);
            }
        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        }
    }

    public void stop() {
        try {
            // Invoke the POJO's destroy method
            Method destroyMethod = implementation.getMethods().get("destroy");
            if (destroyMethod != null) {
                //destroyMethod.invoke(pojoInstance);
            }
        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        } finally {
            instance = null;
        }
    }
    
    public Invoker createInvoker(RuntimeComponentService service, Operation operation) {
        Method method = implementation.getMethods().get(operation.getName()); 
        ImplementationXYZInvoker invoker = new ImplementationXYZInvoker(instance, operation, method);
        return invoker;
    }
    
    public boolean supportsOneWayInvocation() {
        return false;
    }

}
