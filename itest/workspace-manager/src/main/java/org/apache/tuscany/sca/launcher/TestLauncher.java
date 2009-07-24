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

package org.apache.tuscany.sca.launcher;


import org.apache.tuscany.sca.implementation.xyz.ImplementationXYZFactoryImpl;
import org.apache.tuscany.sca.implementation.xyz.ImplementationXYZProcessor;
import org.apache.tuscany.sca.imprt.xyz.DefaultImportExportXYZFactory;
import org.apache.tuscany.sca.imprt.xyz.ImportXYZProcessor;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.ContributionFactory;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.ValidationSchemaExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;
import org.apache.tuscany.sca.workspace.Workspace;
import org.apache.tuscany.sca.workspace.manager.WorkspaceManager;
import org.apache.tuscany.sca.workspace.manager.WorkspaceManagerFactory;
import org.osoa.sca.ServiceRuntimeException;

public class TestLauncher {
	
	public  final static void main(String[] args) {

	    WorkspaceManagerFactory workspaceManagerFactory = WorkspaceManagerFactory.newInstance();
		WorkspaceManager workspaceManager = workspaceManagerFactory.createWorkspaceManager();
		
		addExtensions(workspaceManager);
		workspaceManager.start();
		processContributions(workspaceManager);
		workspaceManager.stop();
	}
	
	private static void addExtensions(WorkspaceManager workspaceManager) {
    	try {
            System.out.println("Adding extensions");    	    
    		ExtensionPointRegistry registry = workspaceManager.getRegistry();
    		
    		// get monitor
            UtilityExtensionPoint utilities = registry.getExtensionPoint(UtilityExtensionPoint.class);
            MonitorFactory monitorFactory = utilities.getUtility(MonitorFactory.class);
            Monitor monitor = monitorFactory.createMonitor();
    		   		
    		// create validation schema
            // are schema required
            ValidationSchemaExtensionPoint schemas = registry.getExtensionPoint(ValidationSchemaExtensionPoint.class);
            schemas.addSchema(TestLauncher.class.getClassLoader().getResource("implementation-xyz.xsd").toString());
            schemas.addSchema(TestLauncher.class.getClassLoader().getResource("import-xyz.xsd").toString());
    		
    		// create model factories
    		ModelFactoryExtensionPoint modelFactories = registry.getExtensionPoint(ModelFactoryExtensionPoint.class);

    		modelFactories.addFactory(new ImplementationXYZFactoryImpl());
    		modelFactories.addFactory(new DefaultImportExportXYZFactory());
    		
            // Create artifact processors
    		StAXArtifactProcessorExtensionPoint artifactProcessors = 
    			registry.getExtensionPoint(StAXArtifactProcessorExtensionPoint.class);
    		
    		artifactProcessors.addArtifactProcessor(new ImplementationXYZProcessor(modelFactories, 
    				monitor));
    		artifactProcessors.addArtifactProcessor(new ImportXYZProcessor(modelFactories,
    				monitor));
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}	 
	}
		
	private static void processContributions(WorkspaceManager workspaceManager){
		try {
			System.out.println("Process contribution");
			ExtensionPointRegistry registry = workspaceManager.getRegistry();
			ModelFactoryExtensionPoint modelFactories = registry.getExtensionPoint(ModelFactoryExtensionPoint.class);
			ContributionFactory contributionFactory = modelFactories.getFactory(ContributionFactory.class);
			
			Workspace workspace = workspaceManager.createWorkspace();
		
			Contribution contribution = contributionFactory.createContribution();
            contribution.setURI("contrib1");
            contribution.setLocation("./target/classes/contrib1");
            contribution.setUnresolved(true);
            
            contribution = workspaceManager.addContributionToWorkspace(workspace, contribution);
			
            workspaceManager.resolveWorkspace(workspace);	
            
            // do whatever you need to do with workspace and its resolved contributions
            
            workspaceManager.removeContributionFromWorkspace(workspace, contribution);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
