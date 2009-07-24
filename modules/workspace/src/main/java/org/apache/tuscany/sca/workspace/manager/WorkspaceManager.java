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
import org.apache.tuscany.sca.workspace.ContributionConfiguration;
import org.apache.tuscany.sca.workspace.Workspace;

/**
 * An SPI for firing up the Tuscany runtime and for providing
 * access to the ExtensionPointRegsitry. With a reference to the
 * registry you can add new extension points and then start the
 * runtime. Once started you can create worksapces and use them
 * to read and resolve SCA contributions in the same way that 
 * Tuscany does internally. 
 */
public interface WorkspaceManager {
		
	/**
	 * If you want to add new extensions to the extension point
	 * registry and have the runtime take notice of them you need
	 * to do this before calling start
	 */
	public ExtensionPointRegistry getRegistry();
	
	/**
	 * Starting the runtime creates the extensible model processors and 
	 * resolvers based on the extension points currently found in the
	 * extension point registry. 
	 */
	public void start();

	/**
	 * Remove any resources being held by the runtime 
	 */
	public void stop();
	
    /**
     * Create an empty workspace
     * 
     * @return workspace
     */
	public Workspace createWorkspace();
		
	// should the following operations be on the manager or on the workspace itself
	
	/**
	 * Add a contribution to a workspace and create the contribution model in the process
	 * 
	 * @param workspace the workspace to be extended
	 * @param contribution the configuration information of the contribution to be added
	 */
	public Contribution addContributionToWorkspace(Workspace workspace, Contribution contribution);
	
	// Should we have an operation that allows a whole workspace to be created at once
	// programmatically from a configuration ?
	
	/**
	 * Resolve all of the contributions in the workspace
	 * 
	 * @param workspace
	 */
	public void resolveWorkspace(Workspace workspace);
	
	/**
	 * Remove a contribution from the workspace
	 * 
	 * @param workspace the workspace to be changes
	 * @param contribution the contribution to be removed
	 */
	public void removeContributionFromWorkspace(Workspace workspace, Contribution contribution);
}
