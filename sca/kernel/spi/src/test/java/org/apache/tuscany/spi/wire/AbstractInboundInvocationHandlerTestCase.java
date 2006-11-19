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
package org.apache.tuscany.spi.wire;

import java.lang.reflect.Array;

import junit.framework.TestCase;
import org.easymock.EasyMock;

/**
 * @version $Rev$ $Date$
 */
public class AbstractInboundInvocationHandlerTestCase extends TestCase {

    public void testInvocation() throws Throwable {
        InvocationHandler handler = new InvocationHandler();
        Interceptor interceptor = new MockInterceptor();
        TargetInvoker invoker = EasyMock.createMock(TargetInvoker.class);
        EasyMock.replay(invoker);
        InboundInvocationChain chain = EasyMock.createMock(InboundInvocationChain.class);
        EasyMock.expect(chain.getHeadInterceptor()).andReturn(interceptor);
        EasyMock.replay(chain);
        Object resp = handler.invoke(chain, invoker, new String[]{"foo"});
        assertEquals("response", resp);
    }


    private class InvocationHandler extends AbstractInboundInvocationHandler {

        protected Object getMessageId() {
            return new Object();
        }

        protected Object getCorrelationId() {
            return new Object();
        }
    }

    private class MockInterceptor implements Interceptor {

        public Message invoke(Message msg) {
            assertNotNull(msg.getCorrelationId());
            assertNotNull(msg.getTargetInvoker());
            assertNotNull(msg.getMessageId());
            assertEquals("foo", Array.get(msg.getBody(), 0));
            msg.setBody("response");
            return msg;
        }

        public void setNext(Interceptor next) {

        }

        public Interceptor getNext() {
            return null;
        }

        public boolean isOptimizable() {
            return false;
        }
    }


}