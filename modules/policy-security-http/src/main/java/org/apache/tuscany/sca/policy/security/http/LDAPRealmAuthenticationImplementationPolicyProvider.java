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

import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.sca.assembly.ConfiguredOperation;
import org.apache.tuscany.sca.assembly.Implementation;
import org.apache.tuscany.sca.assembly.OperationsConfigurator;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Phase;
import org.apache.tuscany.sca.policy.PolicySet;
import org.apache.tuscany.sca.policy.authorization.AuthorizationPolicy;
import org.apache.tuscany.sca.provider.PolicyProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;

public class LDAPRealmAuthenticationImplementationPolicyProvider implements PolicyProvider {
    private RuntimeComponent component;
    private Implementation implementation;

    public LDAPRealmAuthenticationImplementationPolicyProvider(RuntimeComponent component, Implementation implementation) {
        super();
        this.component = component;
        this.implementation = implementation;
    }
    

    public String getPhase() {
        return Phase.IMPLEMENTATION_POLICY;
    }

    public Interceptor createInterceptor(Operation operation) {
        List<LDAPRealmAuthenticationPolicy> policies = findAuthenticationPolicies(operation);
        if (policies == null || policies.isEmpty()) {
            return null;
        } else {
            return new LDAPRealmAuthenticationInterceptor(findAuthenticationPolicies(operation), findAuthorizationPolicies(operation));
        }
    }

    /**
     * Internal utility methods
     */
    
    /**
     * 
     * @param op
     * @return
     */
    private List<LDAPRealmAuthenticationPolicy> findAuthenticationPolicies(Operation op) {
        List<LDAPRealmAuthenticationPolicy> polices = new ArrayList<LDAPRealmAuthenticationPolicy>();
        

        // check explicity added policies first
        ConfiguredOperation configuredOperation = findOperation(op);
        if (configuredOperation!= null && configuredOperation.getPolicySets().size() > 0) {
            for ( PolicySet ps : configuredOperation.getPolicySets()) {
                for (Object p : ps.getPolicies()) {
                    if (p instanceof LDAPRealmAuthenticationPolicy) {
                        polices.add((LDAPRealmAuthenticationPolicy)p);
                    }
                }
            }
        }
        
        // otherwise find applicable policySets
        if ( polices.size() == 0) {

            if (implementation instanceof OperationsConfigurator) {
                OperationsConfigurator operationsConfigurator = (OperationsConfigurator)implementation;
                for (ConfiguredOperation cop : operationsConfigurator.getConfiguredOperations()) {
                    if (cop != null && cop.getName() != null && cop.getName().equals(op.getName())) {
                        for (PolicySet ps : cop.getPolicySets()) {
                            for (Object p : ps.getPolicies()) {
                                if (LDAPRealmAuthenticationPolicy.class.isInstance(p)) {
                                    polices.add((LDAPRealmAuthenticationPolicy)p);
                                }
                            }
                        }
                    }
                }
            }
            
            List<PolicySet> policySets = component.getPolicySets();
            for (PolicySet ps : policySets) {
                for (Object p : ps.getPolicies()) {
                    if (LDAPRealmAuthenticationPolicy.class.isInstance(p)) {
                        polices.add((LDAPRealmAuthenticationPolicy)p);
                    }
                }
            }    
        }
        
        return polices;
    }
    
    private List<AuthorizationPolicy> findAuthorizationPolicies(Operation op) {
        List<AuthorizationPolicy> polices = new ArrayList<AuthorizationPolicy>();
        

        // check explicity added policies first
        ConfiguredOperation configuredOperation = findOperation(op);
        if (configuredOperation!= null && configuredOperation.getPolicySets().size() > 0) {
            for ( PolicySet ps : configuredOperation.getPolicySets()) {
                for (Object p : ps.getPolicies()) {
                    if (p instanceof AuthorizationPolicy) {
                        polices.add((AuthorizationPolicy)p);
                    }
                }
            }
        }
        
        // otherwise find applicable policySets
        if ( polices.size() == 0) {

            if (implementation instanceof OperationsConfigurator) {
                OperationsConfigurator operationsConfigurator = (OperationsConfigurator)implementation;
                for (ConfiguredOperation cop : operationsConfigurator.getConfiguredOperations()) {
                    if (cop != null && cop.getName() != null && cop.getName().equals(op.getName())) {
                        for (PolicySet ps : cop.getPolicySets()) {
                            for (Object p : ps.getPolicies()) {
                                if (AuthorizationPolicy.class.isInstance(p)) {
                                    polices.add((AuthorizationPolicy)p);
                                }
                            }
                        }
                    }
                }
            }
            
            List<PolicySet> policySets = component.getPolicySets();
            for (PolicySet ps : policySets) {
                for (Object p : ps.getPolicies()) {
                    if (AuthorizationPolicy.class.isInstance(p)) {
                        polices.add((AuthorizationPolicy)p);
                    }
                }
            }
    
        }
                
        return polices;
    }
    

    private ConfiguredOperation findOperation(Operation operation) {
        ConfiguredOperation configuredOperation = null;
        
        for (ConfiguredOperation cOperation : ((OperationsConfigurator)component).getConfiguredOperations()) {
            if(cOperation.getName().equals(operation.getName())) {
                configuredOperation = cOperation;
                break;
            }
        }
        
        return configuredOperation;
    }

}
