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
package org.apache.tuscany.sca.implementation.java.introspect.impl;

import java.lang.reflect.Member;

import org.apache.tuscany.sca.implementation.java.IntrospectionException;

/**
 * Denotes an illegal resource definition in a component type
 *
 * @version $Rev$ $Date$
 */
public class IllegalResourceException extends IntrospectionException {
    private static final long serialVersionUID = -1100936539412435579L;

    public IllegalResourceException(String message) {
        super(message);
    }
    
    public IllegalResourceException(String message, Member member) {
        super(message, member);
    }
}
