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

package org.apache.tuscany.sca.policy.security.geronimo;

import java.security.AccessControlContext;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.jacc.WebRoleRefPermission;

import org.apache.geronimo.security.ContextManager;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.policy.authorization.AuthorizationPolicy;
import org.apache.tuscany.sca.policy.security.http.LDAPRealmAuthenticationCallbackHandler;
import org.apache.tuscany.sca.policy.security.http.LDAPRealmAuthenticationPolicy;
import org.apache.tuscany.sca.policy.security.http.extensibility.LDAPSecurityHandler;
import org.apache.tuscany.sca.policy.security.http.util.HttpSecurityUtil;

public class GeronimoLDAPSecurityHandler implements LDAPSecurityHandler {

    public GeronimoLDAPSecurityHandler() {
        
    }
    
    /**
     *  The Http Service calls this method prior to servicing the specified request. 
     *  This method controls whether the request is processed in the normal manner 
     *  or an error is returned.
     *
     *  If the request requires authentication and the Authorization header 
     *  in the request is missing or not acceptable, then this method should 
     *  set the WWW-Authenticate header in the response object, set the status 
     *  in the response object to Unauthorized(401) and return false. 
     *  See also RFC 2617: HTTP Authentication: Basic and Digest Access Authentication 
     *  (available at http://www.ietf.org/rfc/rfc2617.txt).
     *  
     *  If the request requires a secure connection and the getScheme method 
     *  in the request does not return 'https' or some other acceptable secure protocol, 
     *  then this method should set the status in the response object to Forbidden(403) 
     *  and return false.
     *  
     *  When this method returns false, the Http Service will send the response back to 
     *  the client, thereby completing the request. When this method returns true, the 
     *  Http Service will proceed with servicing the request.
     *  
     *  If the specified request has been authenticated, this method must set the 
     *  AUTHENTICATION_TYPE request attribute to the type of authentication used, 
     *  and the REMOTE_USER request attribute to the remote user 
     *  (request attributes are set using the setAttribute method on the request). 
     *  If this method does not perform any authentication, it must not set these attributes.
     *   
     * @param msg
     * @return
     */
    public void handleSecurity(Message msg, 
                               List<LDAPRealmAuthenticationPolicy> authenticationPolicies,
                               List<AuthorizationPolicy> authorizationPolicies) throws javax.security.auth.login.LoginException {
        Subject subject = null;
        Subject authenticatedSubject = null;


        // Perform user authentication    
        LDAPRealmAuthenticationPolicy authenticationPolicy = authenticationPolicies.get(0);
        if( authenticationPolicy != null) {
            subject = HttpSecurityUtil.getSubject(msg);
            CallbackHandler callbackHandler = new LDAPRealmAuthenticationCallbackHandler(subject);

            /* Uses Geronimo to login */
            try {
                LoginContext geronimoLoginContext = ContextManager.login(authenticationPolicy.getRealmConfigurationName(), callbackHandler);
                
                authenticatedSubject = geronimoLoginContext.getSubject();
                ContextManager.setCallers(authenticatedSubject, authenticatedSubject);
                if (authenticatedSubject != null) {
                    //TODO: add authenticated subject to the msg header ?
                }

            } catch(LoginException le) {
                throw new FailedLoginException("Login failed: " + le.getMessage());
            }

        }

        AuthorizationPolicy authorizationPolicy = authorizationPolicies.get(0);
        if(authorizationPolicy != null) {
            if(authorizationPolicy.getAccessControl() == AuthorizationPolicy.AcessControl.allow) {
                /* Geronimo Specific code */
                AccessControlContext acc = ContextManager.getCurrentContext();
                
                boolean isAllowed = false;
                for (String requiredRole : authorizationPolicy.getRoleNames()) {
                    isAllowed = isUserInRole(acc, requiredRole);
                    if(isAllowed) {
                        break;
                    }
                }
                
                if(! isAllowed ) {
                    throw new javax.security.auth.login.LoginException("Insufficient access rights !");
                }
            }

        }

    }
    
    

    
    public boolean isUserInRole(AccessControlContext acc, String role) {
        /* Geronimo Specific code */
        try {
            acc.checkPermission(new WebRoleRefPermission("", role));
        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
