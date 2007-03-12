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
package org.apache.tuscany.core.implementation.system.model;

import java.lang.reflect.Method;

import org.apache.tuscany.spi.model.physical.PhysicalComponentDefinition;

/**
 * @version $Rev$ $Date$
 * @param <T> the implementation class for the defined component
 */
public class SystemPhysicalComponentDefinition<T> extends PhysicalComponentDefinition {

    // we can use an actual class as system components cannot be marshalled
    private Class<T> implClass;

    private Method initMethod;

    private Method destroyMethod;

    private int initLevel;

    public Class<T> getImplClass() {
        return implClass;
    }

    public void setImplClass(Class<T> implClass) {
        this.implClass = implClass;
    }

    public Method getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(Method initMethod) {
        this.initMethod = initMethod;
    }

    public Method getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(Method destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    public int getInitLevel() {
        return initLevel;
    }

    public void setInitLevel(int initLevel) {
        this.initLevel = initLevel;
    }
}
