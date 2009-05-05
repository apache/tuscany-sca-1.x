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

package org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.provider;

import java.util.List;

import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.databinding.javabeans.SimpleJavaDataBinding;
import org.apache.tuscany.sca.databinding.json.JSONDataBinding;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.Interface;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.runtime.RuntimeWire;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osoa.sca.ServiceRuntimeException;

import com.metaparadigm.jsonrpc.JSONRPCResult;

public class JSONRPCWireFormatInterceptor implements Interceptor {
   private Invoker next;
    
    private RuntimeWire runtimeWire;
    private HTTPBinding binding;
    
    //TODO: Pass messageFactory to create fault messages when error occur
    public JSONRPCWireFormatInterceptor(HTTPBinding binding, RuntimeWire runtimeWire) {
        this.binding = binding;
        this.runtimeWire = runtimeWire;
        
    }

    public Invoker getNext() {
        return next;
    }

    public void setNext(Invoker next) {
        this.next = next;
        
    }

    public Message invoke(Message msg) {
        
        // Configure JSON Databding
        setDataBinding(runtimeWire.getTarget().getInterfaceContract().getInterface());
        
        JSONObject jsonReq = (JSONObject) msg.getBody();
        String method = null;
        Object[] args = null;
        Object id = null;
        try {
            // Extract the method
            method = jsonReq.getString("method");
            if ((method != null) && (method.indexOf('.') < 0)) {
                jsonReq.putOpt("method", "Service" + "." + method);
            }
            
            // Extract the arguments
            JSONArray array = jsonReq.getJSONArray("params");
            args = new Object[array.length()];
            for (int i = 0; i < args.length; i++) {
                args[i] = array.get(i);
            }
            id = jsonReq.get("id");

        } catch (Exception e) {
            throw new RuntimeException("Unable to find json method name", e);
        }
        
        Message responseMessage = null;
        try {
            msg.setBody(args);
            responseMessage = runtimeWire.getInvocationChain(msg.getOperation()).getHeadInvoker().invoke(msg);
        } catch (RuntimeException re) {
            //FIXME Exceptions are not handled correctly here 
            // They should be reported to the client JavaScript as proper
            // JavaScript exceptions.

            throw new RuntimeException("Error invoking service :" + re.getMessage(), re);

            //FIXME should create a fault message and stuff the JSON Result in the body of the message
            //JSONRPCResult errorResult = new JSONRPCResult(JSONRPCResult.CODE_REMOTE_EXCEPTION, id, re);
            //return errorResult.toString().getBytes("UTF-8");
        }

        Object result = null;
        if (!responseMessage.isFault()) {
            //successful execution of the invocation
            try {
                result = responseMessage.getBody();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("result", result);
                jsonResponse.putOpt("id", id);
                //get response to send to client
                responseMessage.setBody(jsonResponse);
                return responseMessage;
            } catch (Exception e) {
                throw new ServiceRuntimeException("Unable to create JSON response", e);
            }
        } else {
            //exception thrown while executing the invocation
            //FIXME should create a fault message and stuff the JSON Result in the body of the message
            Throwable exception = (Throwable)responseMessage.getBody();
            //JSONRPCResult errorResult = new JSONRPCResult(JSONRPCResult.CODE_REMOTE_EXCEPTION, id, exception );
            //return errorResult.toString().getBytes("UTF-8");
        }
        
        
        return responseMessage;
    }

    
    private void setDataBinding(Interface interfaze) {
        List<Operation> operations = interfaze.getOperations();
        for (Operation operation : operations) {
            operation.setDataBinding(JSONDataBinding.NAME);
            DataType<List<DataType>> inputType = operation.getInputType();
            if (inputType != null) {
                List<DataType> logical = inputType.getLogical();
                for (DataType inArg : logical) {
                    if (!SimpleJavaDataBinding.NAME.equals(inArg.getDataBinding())) {
                        inArg.setDataBinding(JSONDataBinding.NAME);
                    } 
                }
            }
            DataType outputType = operation.getOutputType();
            if (outputType != null) {
                if (!SimpleJavaDataBinding.NAME.equals(outputType.getDataBinding())) {
                    outputType.setDataBinding(JSONDataBinding.NAME);
                }
            }
        }
    }
}
