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

package org.apache.tuscany.sca.workspace.manager;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.workspace.Workspace;
import org.apache.tuscany.sca.workspace.manager.impl.WorkspaceManagerImpl;
import org.osoa.sca.ServiceRuntimeException;

/**
 * This workspace manager class provides an SPI for firing up the Tuscany runtime 
 * and for providing access to the Tuscany ExtensionPointRegsitry. With a reference to the
 * registry you can add new extension points programmatically before starting the runtime.
 * Once the runtime is started you can read contributions, create a workspace, populate it
 * and then resolve it. 
 * 
 * A workspace is a collection of contributions. A workspace populated
 * with one or more contribution models can be resolved to ensure
 * that all referenced artifacts are located. When more than one contribution
 * model is present resolution takes into account the import and export relationships 
 * between contributions.  
 */
public abstract class WorkspaceManager {
    
    /**
     * Get a new instance of the WorkspaceManager. Each call will create a 
     * distinct instance.
     * 
     * @return workspace manager 
     */
    public static WorkspaceManager newInstance() throws ServiceRuntimeException{
        try {
            // replace with service discovery lookup?
            return new WorkspaceManagerImpl();
        } catch(Exception ex){
            throw new ServiceRuntimeException(ex);
        }
    }    
		
	/**
	 * If you want to add new extensions to the extension point
	 * registry and have the runtime take notice of them you need
	 * to do this before calling start
	 */
	public abstract ExtensionPointRegistry getRegistry();
	
	/**
	 * Starting the runtime creates the extensible model processors and 
	 * resolvers based on the extension points currently found in the
	 * extension point registry. 
	 */
	public abstract void start() throws ServiceRuntimeException;

	/**
	 * Remove any resources being held by the runtime 
	 */
	public abstract void stop() throws ServiceRuntimeException;
	
    /**
     * Create an empty workspace
     * 
     * @return workspace
     */
	public abstract Workspace createWorkspace() throws ServiceRuntimeException;
			
	/**
	 * Create a contribution model by reading from the specified location URL
	 * 
	 * @param name the URI that's given to the contribution
	 * @param location the URL of the contribution to be read
	 */
	public abstract Contribution readContribution(String name, String location) throws ServiceRuntimeException;
	
	/**
	 * Add a contribution to a workspace
	 * 
	 * @param workspace the workspace to be extended
	 * @param contribution the contribution to be added
	 */
	public abstract void addContributionToWorkspace(Workspace workspace, Contribution contribution) throws ServiceRuntimeException;
	
	/**
	 * Resolve all of the contributions in the workspace
	 * 
	 * @param workspace
	 */
	public abstract void resolveWorkspace(Workspace workspace) throws ServiceRuntimeException;
}
