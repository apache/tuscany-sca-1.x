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

package org.apache.tuscany.sca.policy.security.http;

import java.util.List;

import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.policy.authorization.AuthorizationPolicy;
import org.apache.tuscany.sca.policy.security.http.extensibility.LDAPSecurityHandler;
import org.osoa.sca.ServiceRuntimeException;

/**
 * @version $Rev$ $Date$
 */
public class LDAPRealmAuthenticationInterceptor  implements Interceptor {
    private LDAPSecurityHandler securityHandler;
    private List<LDAPRealmAuthenticationPolicy> authenticationPolicies;
    private List<AuthorizationPolicy> authorizationPolicies;
    private Invoker next;

    public LDAPRealmAuthenticationInterceptor(LDAPSecurityHandler securityHandler,
                                              List<LDAPRealmAuthenticationPolicy> authenticationPolicies,
                                              List<AuthorizationPolicy> authorizationPolicies) {
        super();
        this.securityHandler = securityHandler;
        this.authenticationPolicies = authenticationPolicies;
        this.authorizationPolicies = authorizationPolicies;
    }

    public Invoker getNext() {
        return next;
    }

    public void setNext(Invoker next) {
        this.next = next;
    }

    public Message invoke(Message msg) {
        try {
            securityHandler.handleSecurity(msg, authenticationPolicies, authorizationPolicies);

        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        }
        return getNext().invoke(msg);
    }
}
