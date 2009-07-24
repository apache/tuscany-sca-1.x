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

import org.osoa.sca.ServiceRuntimeException;


/**
 * A factory for creating workspace managers. A workspace manager has an instance
 * of the Tuscany runtime and creates workspaces for collecting and processing
 * contributions 
 */
public abstract class WorkspaceManagerFactory {
	
	/**
	 * Get a new instance of the WorkspaceManagerFactory. Each call will create a 
	 * new factory instance
	 * @return workspace manager factory
	 */
	public static WorkspaceManagerFactory newInstance(){
		try {
	        Class<?> workspaceManagerFactoryImplClass = Class.forName("org.apache.tuscany.sca.workspace.manager.impl.WorkspaceManagerFactoryImpl");
	        return (WorkspaceManagerFactory)workspaceManagerFactoryImplClass.newInstance();
		} catch(Exception ex){
			throw new ServiceRuntimeException(ex);
		}
	}
	
	public abstract WorkspaceManager createWorkspaceManager();
}
