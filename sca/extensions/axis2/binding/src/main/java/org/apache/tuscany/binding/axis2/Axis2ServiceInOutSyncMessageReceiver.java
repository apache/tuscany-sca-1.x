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
package org.apache.tuscany.binding.axis2;

import java.lang.reflect.InvocationTargetException;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver;
import org.apache.tuscany.spi.idl.ServiceFaultException;
import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.wire.InvocationRuntimeException;

public class Axis2ServiceInOutSyncMessageReceiver extends AbstractInOutSyncMessageReceiver {

    protected Operation<?> operation;

    private Axis2ServiceBinding axis2Service;

    public Axis2ServiceInOutSyncMessageReceiver(Axis2ServiceBinding service, Operation<?> operation) {
        this.axis2Service = service;
        this.operation = operation;
    }

    public Axis2ServiceInOutSyncMessageReceiver() {

    }

    @Override
    public void invokeBusinessLogic(MessageContext inMC, MessageContext outMC) throws AxisFault {
        QName envQName = null;
        try {
            envQName= inMC.getEnvelope().getQName();
            OMElement requestOM = inMC.getEnvelope().getBody().getFirstElement();
            Object[] args = new Object[] {requestOM};
            
            String conversationID = axis2Service.isConversational() ?  Axis2ServiceBinding.getConversationID(inMC) : null;

            OMElement responseOM = (OMElement)axis2Service.invokeTarget(operation, args, null, conversationID);

            
            SOAPEnvelope soapEnvelope = getSOAPFactory(inMC).getDefaultEnvelope();
            if(null != responseOM ){
                soapEnvelope.getBody().addChild(responseOM);
            }
            outMC.setEnvelope(soapEnvelope);
            outMC.getOperationContext().setProperty(Constants.RESPONSE_WRITTEN, Constants.VALUE_TRUE);

        } catch (InvocationTargetException e) {
            // e.printStackTrace();
            throw processMessageFault(envQName.getNamespaceURI(), e.getCause());
         } catch(InvocationRuntimeException e){
             // e.printStackTrace();
             throw processMessageFault(envQName.getNamespaceURI(), e.getCause());
         } catch (AxisFault e) {
             // e.printStackTrace();
             throw e;
         } catch (Exception e) {
             // e.printStackTrace();
             throw AxisFault.makeFault(e);
         }
    }

    /**
     * @param envQName
     * @param e
     * @throws AxisFault
     */
    private AxisFault processMessageFault(String nsURI, Throwable t)  {
        
        if (t instanceof ServiceFaultException) { //Business fault.
            OMElement faultdetail = null;
            String reason = "";

            ServiceFaultException sfe = (ServiceFaultException)t;
            reason= sfe.getMessage(); 
            reason = reason == null ? "" : reason;
            Object finfo = sfe.getFaultInfo();

            if (finfo instanceof OMElement) {
                faultdetail = (OMElement)finfo;

            }
            QName faultCode= new QName(nsURI ,
                                       org.apache.axiom.soap.SOAP12Constants.SOAP_FAULT_VALUE_SENDER);
            return new AxisFault(faultCode, reason, null, null, faultdetail);


        } else if ( t instanceof Exception) {
            return  AxisFault.makeFault((Exception) t);
        }
        

        return new AxisFault(t);
    }
}
