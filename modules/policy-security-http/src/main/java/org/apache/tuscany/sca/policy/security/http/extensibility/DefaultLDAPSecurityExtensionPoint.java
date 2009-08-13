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

package org.apache.tuscany.sca.policy.security.http.extensibility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.tuscany.sca.assembly.builder.impl.ProblemImpl;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.extensibility.ServiceDeclaration;
import org.apache.tuscany.sca.extensibility.ServiceDiscovery;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;


/**
 * Default Extension point for LDAP Security Handlers
 * 
 * @version $Rev$ $Date$
 */

public class DefaultLDAPSecurityExtensionPoint implements LDAPSecurityHandlerExtensionPoint {
    private List<LDAPSecurityHandler> securityHandlers = new ArrayList<LDAPSecurityHandler>();
    
    private ExtensionPointRegistry extensionPoints;
    private Monitor monitor = null;

    private boolean loaded = false;
    
    public DefaultLDAPSecurityExtensionPoint(ExtensionPointRegistry extensionPoints) {
        this.extensionPoints = extensionPoints;
        
        UtilityExtensionPoint utilities = extensionPoints.getExtensionPoint(UtilityExtensionPoint.class);
        MonitorFactory monitorFactory = utilities.getUtility(MonitorFactory.class);
        if (monitorFactory != null) {
                this.monitor = monitorFactory.createMonitor();
        }
    }


    public void addLDAPSecurityHandler(LDAPSecurityHandler securityHandler) {
        securityHandlers.add(securityHandler);
    }

    public void removeLDAPSecurityHandler(LDAPSecurityHandler securityHandler) {
        securityHandlers.remove(securityHandler);
    }

    public List<LDAPSecurityHandler> getLDAPSecurityHandlers() {
        loadHandlers();
        return securityHandlers;
    }


    /**
     * Private Utility methods
     */
    
    /**
     * Report a exception.
     * 
     * @param problems
     * @param message
     * @param model
    */
    private void error(String message, Object model, Exception ex) {
        if (monitor != null) {
            Problem problem = new ProblemImpl(this.getClass().getName(), null, Severity.ERROR, model, message, ex);
            monitor.problem(problem);
        }        
    }
    
    /**
     * Lazily load artifact processors registered in the extension point.
     */
    @SuppressWarnings("unchecked")
    private synchronized void loadHandlers() {
        if (loaded) {
            return;
        }

        // Get the proxy factories declarations
        Set<ServiceDeclaration> handlerDeclarations = null;
        try {
            handlerDeclarations = ServiceDiscovery.getInstance().getServiceDeclarations(LDAPSecurityHandler.class);
        } catch (IOException e) {
            IllegalStateException ie = new IllegalStateException(e);
            error("IllegalStateException", handlerDeclarations, ie);
            throw ie;
        }

        for (ServiceDeclaration processorDeclaration : handlerDeclarations) {
            // Create a factory, and register it
            LDAPSecurityHandler securityHandler = null;
            try {
                Class<LDAPSecurityHandler> securityHandlerClass = (Class<LDAPSecurityHandler>) processorDeclaration.loadClass();
                
                securityHandler = securityHandlerClass.newInstance();
                
            } catch (Exception e) {
                IllegalStateException ie = new IllegalStateException(e);
                error("IllegalStateException", securityHandler, ie);
                throw ie;
            }

            addLDAPSecurityHandler(securityHandler);
        }

        loaded = true;
    }

}
