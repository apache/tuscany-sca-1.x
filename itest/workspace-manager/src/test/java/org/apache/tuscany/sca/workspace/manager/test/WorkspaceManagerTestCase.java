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
package org.apache.tuscany.sca.workspace.manager.test;

import static org.junit.Assert.assertEquals;


import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.ValidationSchemaExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.implementation.xyz.ImplementationXYZFactoryImpl;
import org.apache.tuscany.sca.implementation.xyz.ImplementationXYZProcessor;
import org.apache.tuscany.sca.imprt.xyz.DefaultImportExportXYZFactory;
import org.apache.tuscany.sca.imprt.xyz.ImportXYZProcessor;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.workspace.Workspace;
import org.apache.tuscany.sca.workspace.manager.WorkspaceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WorkspaceManagerTestCase {

    private WorkspaceManager workspaceManager;

    @Before
    public void init() {
        workspaceManager = WorkspaceManager.newInstance();
        addExtensions(workspaceManager);
        workspaceManager.start();
    }

    @Test
    public void testHelloWorldCreate() throws Exception {
        Workspace workspace = loadWorkspace(workspaceManager);
        assertEquals(1, workspace.getContributions().get(0).getDeployables().size());
    }
    
    private void addExtensions(WorkspaceManager workspaceManager) {
        try {
            System.out.println("Add extensions");           
            ExtensionPointRegistry registry = workspaceManager.getRegistry();
            
            // get monitor
            UtilityExtensionPoint utilities = registry.getExtensionPoint(UtilityExtensionPoint.class);
            MonitorFactory monitorFactory = utilities.getUtility(MonitorFactory.class);
            Monitor monitor = monitorFactory.createMonitor();
                    
            // create validation schema
            // are schema required
            ValidationSchemaExtensionPoint schemas = registry.getExtensionPoint(ValidationSchemaExtensionPoint.class);
            schemas.addSchema(WorkspaceManagerTestCase.class.getClassLoader().getResource("implementation-xyz.xsd").toString());
            schemas.addSchema(WorkspaceManagerTestCase.class.getClassLoader().getResource("import-xyz.xsd").toString());
            
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
        
    private Workspace loadWorkspace(WorkspaceManager workspaceManager){
        try {
            System.out.println("Process contribution");
            
            Workspace workspace = workspaceManager.createWorkspace();
            Contribution contribution = workspaceManager.readContribution("contrib1", "./target/classes/contrib1");
            workspaceManager.addContributionToWorkspace(workspace, contribution);
            workspaceManager.resolveWorkspace(workspace);   
            
            return workspace;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }    

    @After
    public void end() {
        workspaceManager.stop();
    }
}
