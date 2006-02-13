/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.core.invocation.mock;

import java.lang.reflect.Method;
import java.util.List;

import commonj.sdo.Type;

import org.apache.tuscany.model.assembly.AssemblyModelContext;
import org.apache.tuscany.model.assembly.AssemblyModelVisitor;
import org.apache.tuscany.model.types.java.JavaOperationType;

public class MockJavaOperationType implements JavaOperationType {

    private Method method;

    public MockJavaOperationType(Method m) {
        method = m;
    }

    public Method getJavaMethod() {
        return method;
    }

    public String getName() {
        return method.getName();
    }

    public void setName(String name) {
        throw new UnsupportedOperationException();
    }

    public Type getInputType() {
        throw new UnsupportedOperationException();
    }

    public Type getOutputType() {
        throw new UnsupportedOperationException();
    }

    public List<Type> getExceptionTypes() {
        throw new UnsupportedOperationException();
    }

    public void initialize(AssemblyModelContext modelContext) {
    }

    public void freeze() {
    }

    public boolean accept(AssemblyModelVisitor visitor) {
        throw new UnsupportedOperationException();
    }

}

