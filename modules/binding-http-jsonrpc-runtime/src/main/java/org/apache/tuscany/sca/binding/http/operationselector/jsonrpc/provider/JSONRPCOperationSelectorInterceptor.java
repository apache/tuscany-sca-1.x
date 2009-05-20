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

package org.apache.tuscany.sca.binding.http.operationselector.jsonrpc.provider;

import java.io.CharArrayWriter;
import java.util.List;

import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.invocation.MessageFactory;
import org.apache.tuscany.sca.runtime.RuntimeWire;
import org.json.JSONObject;

import com.metaparadigm.jsonrpc.JSONRPCResult;

public class JSONRPCOperationSelectorInterceptor implements Interceptor {
    private Invoker next;
    
    private RuntimeWire runtimeWire;
    private HTTPBinding binding;
    
    private MessageFactory messageFactory;
    
    public JSONRPCOperationSelectorInterceptor(HTTPBinding binding, RuntimeWire runtimeWire, MessageFactory messageFactory) {
        this.binding = binding;
        this.runtimeWire = runtimeWire;
        this.messageFactory = messageFactory;
    }

    public Invoker getNext() {
        return this.next;
    }

    public void setNext(Invoker next) {
        this.next = next;
    }

    public Message invoke(Message msg) {
        
        JSONObject jsonReq = null;
        String method = null;
        try {
            Object[] args = msg.getBody();
            CharArrayWriter data = (CharArrayWriter) args[0];
            jsonReq = new JSONObject(data.toString());
            method = jsonReq.getString("method");
        } catch (Exception e) {
            Throwable exception = new RuntimeException("Unable to parse request", e);
        }
        
        Operation jsonOperation = findOperation(method);
        msg.setOperation(jsonOperation);
        msg.setBody(jsonReq);
        
        return getNext().invoke(msg);
    }
    
    /**
     * Find the operation from the component service contract
     * @param componentService
     * @param method
     * @return
     */
    private Operation findOperation(String method) {
        if (method.contains(".")) {
            method = method.substring(method.lastIndexOf(".") + 1);
        }

        List<Operation> operations = runtimeWire.getTarget().getInterfaceContract().getInterface().getOperations(); 

        Operation result = null;
        for (Operation o : operations) {
            if (o.getName().equalsIgnoreCase(method)) {
                result = o;
                break;
            }
        }

        return result;
    }
    


    /**
     * Create a Fault Message with a JSON representation of the current exception
     * @param throwable
     * @return
     */
    private Message createJSONFaultMessage(Throwable throwable) {
        Message jsonFaultMessage = messageFactory.createMessage();
        
        JSONRPCResult jsonFault = new JSONRPCResult(JSONRPCResult.CODE_REMOTE_EXCEPTION, null, throwable);
        jsonFaultMessage.setBody(jsonFault);
        
        return jsonFaultMessage;
    }

}
