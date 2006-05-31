/**
 *
 * Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.tuscany.core.wire;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.tuscany.core.util.MethodHashMap;
import org.apache.tuscany.spi.wire.JDKOutboundInvocationHandler;
import org.apache.tuscany.spi.QualifiedName;
import org.apache.tuscany.spi.context.TargetException;
import org.apache.tuscany.spi.wire.InboundWire;
import org.apache.tuscany.spi.wire.Interceptor;
import org.apache.tuscany.spi.wire.MessageHandler;
import org.apache.tuscany.spi.wire.OutboundInvocationChain;
import org.apache.tuscany.spi.wire.OutboundWire;

/**
 * Default implementation of an outbound wire
 *
 * @version $Rev: 394431 $ $Date: 2006-04-15 21:27:44 -0700 (Sat, 15 Apr 2006) $
 */
public class OutboundWireImpl<T> implements OutboundWire<T> {

    private Class<T>[] businessInterfaces;
    private Map<Method, OutboundInvocationChain> invocationChains = new MethodHashMap<OutboundInvocationChain>();
    private String referenceName;
    private QualifiedName targetName;
    private InboundWire<T> targetWire;

    @SuppressWarnings("unchecked")
    public T getTargetService() throws TargetException {
        if (targetWire != null) {
            // optimized, no interceptors or handlers on either end
            return targetWire.getTargetService();
        }
        JDKOutboundInvocationHandler handler = new JDKOutboundInvocationHandler(invocationChains);
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), businessInterfaces, handler);
    }

    @SuppressWarnings("unchecked")
    public void setBusinessInterface(Class<T> interfaze) {
        businessInterfaces = new Class[]{interfaze};
    }

    public Class<T> getBusinessInterface() {
        return businessInterfaces[0];
    }

    public void addInterface(Class<?> claz) {
        throw new UnsupportedOperationException("Additional proxy interfaces not yet supported");
    }

    public Class[] getImplementedInterfaces() {
        return businessInterfaces;
    }

    public void setTargetWire(InboundWire<T> wire) {
        this.targetWire = wire;
    }

    public Map<Method, OutboundInvocationChain> getInvocationChains() {
        return invocationChains;
    }

    public void addInvocationChains(Map<Method, OutboundInvocationChain> chains) {
        invocationChains.putAll(chains);
    }

    public void addInvocationChain(Method method, OutboundInvocationChain chain) {
        invocationChains.put(method, chain);
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public QualifiedName getTargetName() {
        return targetName;
    }

    public void setTargetName(QualifiedName targetName) {
        this.targetName = targetName;
    }

    public boolean isOptimizable() {
        for (OutboundInvocationChain chain : invocationChains.values()) {
            if (chain.getHeadInterceptor() != null || !chain.getRequestHandlers().isEmpty()
                    || !chain.getResponseHandlers().isEmpty()) {
                Interceptor current = chain.getHeadInterceptor();
                while (current != null && current != chain.getTargetInterceptor()) {
                    if (!current.isOptimizable()) {
                        return false;
                    }
                    current = current.getNext();
                }
                if (chain.getRequestHandlers() != null) {
                    for (MessageHandler handler : chain.getRequestHandlers()) {
                        if (!handler.isOptimizable()) {
                            return false;
                        }
                    }
                }
                if (chain.getResponseHandlers() != null) {
                    for (MessageHandler handler : chain.getResponseHandlers()) {
                        if (!handler.isOptimizable()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
