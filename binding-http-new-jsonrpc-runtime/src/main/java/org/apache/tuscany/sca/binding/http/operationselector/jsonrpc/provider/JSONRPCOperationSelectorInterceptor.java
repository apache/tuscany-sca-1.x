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
import org.apache.tuscany.sca.runtime.RuntimeWire;
import org.json.JSONObject;

import com.metaparadigm.jsonrpc.JSONRPCResult;

public class JSONRPCOperationSelectorInterceptor implements Interceptor {
    private Invoker next;
    
    private RuntimeWire runtimeWire;
    private HTTPBinding binding;
    
    //TODO: Pass messageFactory to create fault messages when error occur
    public JSONRPCOperationSelectorInterceptor(HTTPBinding binding, RuntimeWire runtimeWire) {
        this.binding = binding;
        this.runtimeWire = runtimeWire;
        
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
            //FIXME Exceptions are not handled correctly here 
            // They should be reported to the client JavaScript as proper
            // JavaScript exceptions.
            throw new RuntimeException("Unable to parse request", e);
           
            //FIXME should create a fault message and stuff the JSON Result in the body of the message
            //JSONRPCResult errorResult = new JSONRPCResult(JSONRPCResult.CODE_ERR_PARSE, null, e);
            //return errorResult;
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
          //serviceContract.getInterface().getOperations();
          //componentService.getBindingProvider(binding).getBindingInterfaceContract().getInterface().getOperations();


        Operation result = null;
        for (Operation o : operations) {
            if (o.getName().equalsIgnoreCase(method)) {
                result = o;
                break;
            }
        }

        return result;
    }    

}
