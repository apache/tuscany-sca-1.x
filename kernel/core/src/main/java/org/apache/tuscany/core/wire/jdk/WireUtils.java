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
package org.apache.tuscany.core.wire.jdk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tuscany.spi.idl.java.JavaIDLUtils.findMethod;
import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.wire.InboundInvocationChain;
import org.apache.tuscany.spi.wire.InboundWire;

/**
 * Utilities for operating on wires
 *
 * @version $Rev$ $Date$
 */
public final class WireUtils {

    private WireUtils() {
    }

    /**
     * Maps invocation chains on a wire to corresponding methods
     *
     * @param wire    the wire containing the invocation chains to map
     * @param methods the methods to map to
     * @return a collection containing the method to invocation chain mapping
     * @throws NoMethodForOperationException
     */
    public static Map<Method, InboundInvocationChain> createInboundMapping(InboundWire wire, Method[] methods)
        throws NoMethodForOperationException {
        Map<Method, InboundInvocationChain> chains = new HashMap<Method, InboundInvocationChain>();
        for (Map.Entry<Operation<?>, InboundInvocationChain> entry : wire.getInvocationChains().entrySet()) {
            Operation<?> operation = entry.getKey();
            InboundInvocationChain chain = entry.getValue();
            Method method = findMethod(operation, methods);
            if (method == null) {
                NoMethodForOperationException e = new NoMethodForOperationException();
                e.setIdentifier(operation.getName());
            }
            chains.put(method, chain);
        }
        return chains;
    }
}
