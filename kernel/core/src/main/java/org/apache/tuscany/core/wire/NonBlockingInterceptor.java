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
package org.apache.tuscany.core.wire;

import java.net.URI;
import java.util.LinkedList;

import org.osoa.sca.ServiceRuntimeException;

import org.apache.tuscany.spi.component.WorkContext;
import org.apache.tuscany.spi.model.Scope;
import org.apache.tuscany.spi.services.work.WorkScheduler;
import org.apache.tuscany.spi.wire.Interceptor;
import org.apache.tuscany.spi.wire.Message;
import org.apache.tuscany.spi.wire.TargetInvoker;
import org.apache.tuscany.spi.wire.Wire;

/**
 * Adds non-blocking behavior to an invocation chain
 *
 * @version $$Rev$$ $$Date$$
 */
public class NonBlockingInterceptor implements Interceptor {

    private static final Message RESPONSE = new ImmutableMessage();

    private WorkScheduler workScheduler;
    private WorkContext workContext;
    private Interceptor next;

    public NonBlockingInterceptor(WorkScheduler workScheduler, WorkContext workContext) {
        this.workScheduler = workScheduler;
        this.workContext = workContext;
    }

    public NonBlockingInterceptor(WorkScheduler workScheduler, WorkContext workContext, Interceptor next) {
        this.workScheduler = workScheduler;
        this.workContext = workContext;
        this.next = next;
    }

    public Message invoke(final Message msg) {
        // Retrieve conversation id to transfer to new thread
        // Notice that we cannot clear the conversation id from the current thread
        final Object conversationID = workContext.getIdentifier(Scope.CONVERSATION);
        // Schedule the invocation of the next interceptor in a new Work instance
        try {
            workScheduler.scheduleWork(new Runnable() {
                public void run() {
                    workContext.setCorrelationId(null);
                    // if we got a conversation id, transfer it to new thread
                    if (conversationID != null) {
                        workContext.setIdentifier(Scope.CONVERSATION, conversationID);
                    }
                    next.invoke(msg);
                }
            });
        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        }
        return RESPONSE;
    }

    public Interceptor getNext() {
        return next;
    }

    public void setNext(Interceptor next) {
        this.next = next;
    }

    public boolean isOptimizable() {
        return false;
    }

    /**
     * A dummy message passed back on an invocation
     */
    private static class ImmutableMessage implements Message {

        public Object getBody() {
            return null;
        }

        public void setBody(Object body) {
            if (body != null) {
                throw new UnsupportedOperationException();
            }
        }

        public void setTargetInvoker(TargetInvoker invoker) {
            throw new UnsupportedOperationException();
        }

        public TargetInvoker getTargetInvoker() {
            return null;
        }

        public Message getRelatedCallbackMessage() {
            return null;
        }

        public URI getFromAddress() {
            return null;
        }

        public void setFromAddress(URI fromAddress) {
            throw new UnsupportedOperationException();
        }

        public void pushCallbackUri(URI fromAddress) {
            throw new UnsupportedOperationException();
        }

        public LinkedList<URI> getCallbackUris() {
            return null;
        }

        public void setCallbackUris(LinkedList<URI> uris) {
            throw new UnsupportedOperationException();
        }

        public void pushCallbackWire(Wire wire) {

        }

        public LinkedList<Wire> getCallbackWires() {
            return null;
        }

        public void setCallbackWires(LinkedList<Wire> wires) {

        }

        public Object getMessageId() {
            return null;
        }

        public void setMessageId(Object messageId) {
            throw new UnsupportedOperationException();
        }

        public Object getCorrelationId() {
            return null;
        }

        public void setCorrelationId(Object correlationId) {
            throw new UnsupportedOperationException();
        }

        public boolean isFault() {
            return false;
        }

        public void setBodyWithFault(Object fault) {
            throw new UnsupportedOperationException();
        }

        public short getConversationSequence() {
            return TargetInvoker.NONE;
        }

        public void setConversationSequence(short sequence) {

        }

    }

}
