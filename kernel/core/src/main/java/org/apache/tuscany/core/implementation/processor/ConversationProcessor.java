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
package org.apache.tuscany.core.implementation.processor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.osoa.sca.annotations.Conversation;
import org.osoa.sca.annotations.ConversationID;
import org.osoa.sca.annotations.Scope;

import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.implementation.java.ImplementationProcessorExtension;
import org.apache.tuscany.spi.implementation.java.JavaMappedProperty;
import org.apache.tuscany.spi.implementation.java.JavaMappedReference;
import org.apache.tuscany.spi.implementation.java.JavaMappedService;
import org.apache.tuscany.spi.implementation.java.PojoComponentType;
import org.apache.tuscany.spi.implementation.java.ProcessingException;

/**
 * @version $Rev$ $Date$
 */
public class ConversationProcessor extends ImplementationProcessorExtension {
    private static final String SECONDS = " SECONDS";
    private static final String MINUTES = " MINUTES";
    private static final String HOURS = " HOURS";
    private static final String DAYS = " DAYS";
    private static final String YEARS = " YEARS";

    public <T> void visitClass(CompositeComponent parent,
                               Class<T> clazz,
                               PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>> type,
                               DeploymentContext context) throws ProcessingException {

        Conversation conversation = clazz.getAnnotation(Conversation.class);
        if (conversation == null) {
            return;
        }
        Scope scope = clazz.getAnnotation(Scope.class);
        if (scope == null) {
            // implicitly assume conversation
            type.setImplementationScope(org.apache.tuscany.spi.model.Scope.CONVERSATION);
        } else if (scope != null && !"CONVERSATION".equals(scope.value().toUpperCase())) {
            throw new InvalidConversationalImplementation(
                "Service is marked with @Conversation but the scope is not @Scope(\"CONVERSATION\")", clazz.getName());
        } else if (conversation != null) {
            long maxAge;
            long maxIdleTime;
            String maxAgeVal = conversation.maxAge();
            String maxIdleTimeVal = conversation.maxIdleTime();
            if (maxAgeVal.length() > 0 && maxIdleTimeVal.length() > 0) {
                throw new InvalidConversationalImplementation("Max idle time and age both specified", clazz.getName());
            }
            try {
                if (maxAgeVal.length() > 0) {
                    maxAge = convertTimeMillis(maxAgeVal);
                    type.setMaxAge(maxAge);
                }
            } catch (NumberFormatException e) {
                throw new InvalidConversationalImplementation("Invalid maximum age", clazz.getName(), e);
            }
            try {
                if (maxIdleTimeVal.length() > 0) {
                    maxIdleTime = convertTimeMillis(maxIdleTimeVal);
                    type.setMaxIdleTime(maxIdleTime);
                }
            } catch (NumberFormatException e) {
                throw new InvalidConversationalImplementation("Invalid maximum idle time", clazz.getName(), e);
            }
        }

    }

    public void visitMethod(CompositeComponent parent, Method method,
                            PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>> type,
                            DeploymentContext context)
        throws ProcessingException {
        ConversationID conversationID = method.getAnnotation(ConversationID.class);
        if (conversationID == null) {
            return;
        }
        type.setConversationIDMember(method);
    }

    public void visitField(CompositeComponent parent, Field field,
                           PojoComponentType<JavaMappedService, JavaMappedReference, JavaMappedProperty<?>> type,
                           DeploymentContext context) throws ProcessingException {
        ConversationID conversationID = field.getAnnotation(ConversationID.class);
        if (conversationID == null) {
            return;
        }
        type.setConversationIDMember(field);
    }

    protected long convertTimeMillis(String expr) throws NumberFormatException {
        expr = expr.trim().toUpperCase();
        int i = expr.lastIndexOf(SECONDS);
        if (i >= 0) {
            String units = expr.substring(0, i);
            return Long.parseLong(units) * 1000;
        }
        i = expr.lastIndexOf(MINUTES);
        if (i >= 0) {
            String units = expr.substring(0, i);
            return Long.parseLong(units) * 60000;
        }

        i = expr.lastIndexOf(HOURS);
        if (i >= 0) {
            String units = expr.substring(0, i);
            return Long.parseLong(units) * 3600000;
        }
        i = expr.lastIndexOf(DAYS);
        if (i >= 0) {
            String units = expr.substring(0, i);
            return Long.parseLong(units) * 86400000;
        }
        i = expr.lastIndexOf(YEARS);
        if (i >= 0) {
            String units = expr.substring(0, i);
            return Long.parseLong(units) * 31556926000L;
        }
        return Long.parseLong(expr) * 1000;  // assume seconds if no suffix specified
    }
}
