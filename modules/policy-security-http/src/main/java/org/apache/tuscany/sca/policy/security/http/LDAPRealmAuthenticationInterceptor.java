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

import java.security.AccessControlContext;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.jacc.WebRoleRefPermission;
import javax.servlet.http.HttpServletRequest;

import org.apache.geronimo.security.ContextManager;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.policy.authorization.AuthorizationPolicy;
import org.apache.tuscany.sca.policy.security.http.util.HttpSecurityUtil;
import org.osoa.sca.ServiceRuntimeException;

/**
 * @version $Rev$ $Date$
 */
public class LDAPRealmAuthenticationInterceptor  implements Interceptor {
    private List<LDAPRealmAuthenticationPolicy> authenticationPolicies;
    private List<AuthorizationPolicy> authorizationPolicies;
    private Invoker next;

    public LDAPRealmAuthenticationInterceptor(List<LDAPRealmAuthenticationPolicy> authenticationPolicies,
                                              List<AuthorizationPolicy> authorizationPolicies) {
        super();
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
        Subject subject = null;
        Subject authenticatedSubject = null;

        try {
            // Perform user authentication    
            LDAPRealmAuthenticationPolicy authenticationPolicy = authenticationPolicies.get(0);
            if( authenticationPolicy != null) {
                subject = HttpSecurityUtil.getSubject(msg);
                CallbackHandler callbackHandler = new LDAPRealmAuthenticationCallbackHandler(subject);

                /* This bypass Java EE */
                LoginContext lc = new LoginContext(authenticationPolicy.getRealmConfigurationName(), callbackHandler);
                lc.login();


                /* Uses Geronimo to login */
                /*
                LoginContext geronimoLoginContext = ContextManager.login(authenticationPolicy.getRealmConfigurationName(), callbackHandler);

                authenticatedSubject = geronimoLoginContext.getSubject();
                if (authenticatedSubject != null) {
                    //TODO: add authenticated subject to the msg header ?
                }
                */
            }

            AuthorizationPolicy authorizationPolicy = authorizationPolicies.get(0);
            if(authorizationPolicy != null) {
                if(authorizationPolicy.getAccessControl() == AuthorizationPolicy.AcessControl.allow) {
                    /* Geronimo Specific code */
                    /*
                    boolean isAllowed = false;
                    for (String requiredRole : authorizationPolicy.getRoleNames()) {
                        isAllowed = isUserInRole(authenticatedSubject, requiredRole);
                    }
                    
                    if(! isAllowed ) {
                        throw new javax.security.auth.login.LoginException("Insufficient access rights !");
                    }
                    */
                }

            }

        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        }
        return getNext().invoke(msg);
    }
    
    public boolean isUserInRole(Subject subject, String role) {
        /* Geronimo Specific code */
        /*
        AccessControlContext acc = ContextManager.getCurrentContext();

        try {
            acc.checkPermission(new WebRoleRefPermission("", role));
        } catch (Exception e) {
            return false;
        }

        return true;
        */

        return false;
    }


}
