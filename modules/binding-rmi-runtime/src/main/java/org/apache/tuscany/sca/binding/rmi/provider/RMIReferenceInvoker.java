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
package org.apache.tuscany.sca.binding.rmi.provider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.ConnectException;
import java.rmi.Remote;
import java.rmi.UnexpectedException;

import org.apache.tuscany.sca.host.rmi.RMIHost;
import org.apache.tuscany.sca.invocation.DataExchangeSemantics;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;

/**
 * Invoker for RMI References.
 * 
 * @version $Rev$ $Date$
 */
public class RMIReferenceInvoker implements Invoker, DataExchangeSemantics {

    private RMIHost rmiHost;
    private String host;
    private String port;
    private String svcName;
    private Method remoteMethod;
    private Remote proxy;

    public RMIReferenceInvoker(RMIHost rmiHost, String host, String port, String svcName, Method remoteMethod) {
        this.rmiHost = rmiHost;
        this.remoteMethod = remoteMethod;
        this.host = host;
        this.port = port;
        this.svcName = svcName;
    }

    public Message invoke(Message msg) {
        try {

            Object[] args = msg.getBody();
            Object resp = invokeTarget(args);
            msg.setBody(resp);

        } catch (InvocationTargetException e) {
            msg.setFaultBody(e.getCause());
        } catch (Throwable e) {
            msg.setFaultBody(e);
        }

        return msg;
    }

    public Object invokeTarget(final Object payload) throws InvocationTargetException, SecurityException,
        NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
        // try cached proxy first
        if (proxy == null) {
            proxy = rmiHost.findService(host, port, svcName);
            // proxy = Naming.lookup(serviceURI);
        }

        Object invocationResult = null;
        InvocationTargetException rethrow = null;
        try {
            invocationResult = doInvokeTarget(payload);
        } catch (InvocationTargetException e) {
            // rethrow this exception from finally block unless it can be
            // handled
            rethrow = e;
            // try to diagnose the error condition: proxy may be out-of-date
            // (cf. TUSCANY-3850)
            Throwable cause = e.getCause();
            if (cause instanceof UndeclaredThrowableException) {
                cause = cause.getCause();
                if (cause instanceof UnexpectedException) {
                    cause = cause.getCause();
                    if (cause instanceof ConnectException) {
                        // retry invoke with a fresh proxy
                        rethrow = null;
                        proxy = rmiHost.findService(host, port, svcName);
                        invocationResult = doInvokeTarget(payload);
                    }
                }
            }
        } finally {
            if (rethrow != null) {
                throw rethrow;
            }
        }

        return invocationResult;
    }

    private Object doInvokeTarget(final Object payload) throws InvocationTargetException, SecurityException,
        NoSuchMethodException, IllegalArgumentException, IllegalAccessException {

        remoteMethod = proxy.getClass().getMethod(remoteMethod.getName(), remoteMethod.getParameterTypes());

        if (payload != null && !payload.getClass().isArray()) {
            return remoteMethod.invoke(proxy, payload);
        } else {
            return remoteMethod.invoke(proxy, (Object[])payload);
        }
    }

    public boolean allowsPassByReference() {
        // RMI always pass by value
        return true;
    }

}
