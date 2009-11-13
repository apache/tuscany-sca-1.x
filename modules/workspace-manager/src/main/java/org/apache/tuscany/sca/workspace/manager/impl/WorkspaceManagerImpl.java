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

package org.apache.tuscany.sca.workspace.manager.impl;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.URLArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.URLArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.contribution.resolver.ExtensibleModelResolver;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.host.embedded.impl.ReallySmallRuntime;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;
import org.apache.tuscany.sca.workspace.Workspace;
import org.apache.tuscany.sca.workspace.WorkspaceFactory;
import org.apache.tuscany.sca.workspace.builder.ContributionDependencyBuilder;
import org.apache.tuscany.sca.workspace.builder.impl.ContributionDependencyBuilderImpl;
import org.apache.tuscany.sca.workspace.manager.WorkspaceManager;
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
public class WorkspaceManagerImpl extends WorkspaceManager {
    
    private ReallySmallRuntime runtime;
    
    private ModelFactoryExtensionPoint modelFactories;
    private URLArtifactProcessorExtensionPoint artifactProcessorExtensions;
    private UtilityExtensionPoint utilities;
    
    private WorkspaceFactory workspaceFactory;
    private URLArtifactProcessor<Contribution> contributionProcessor;
    private ContributionDependencyBuilder contributionDependencyBuilder;
    private MonitorFactory monitorFactory;
    private Monitor monitor;
		
	public WorkspaceManagerImpl() throws ServiceRuntimeException{
        try {
            runtime = new ReallySmallRuntime(Thread.currentThread().getContextClassLoader());  
            
            ExtensionPointRegistry registry = getRegistry();
            
            modelFactories = registry.getExtensionPoint(ModelFactoryExtensionPoint.class);  
            artifactProcessorExtensions = registry.getExtensionPoint(URLArtifactProcessorExtensionPoint.class);
            utilities = registry.getExtensionPoint(UtilityExtensionPoint.class);
            
            workspaceFactory = modelFactories.getFactory(WorkspaceFactory.class);
            contributionProcessor = artifactProcessorExtensions.getProcessor(Contribution.class);
            monitorFactory = utilities.getUtility(MonitorFactory.class);
            monitor = monitorFactory.createMonitor(); 
            
            contributionDependencyBuilder = new ContributionDependencyBuilderImpl(monitor);
            
        } catch(Exception ex) {
            throw new ServiceRuntimeException(ex);
        }	    
	}
	
	public ExtensionPointRegistry getRegistry(){
	    return runtime.getExtensionPointRegistry();
	}
	
	public void start() throws ServiceRuntimeException {
       try {
            runtime.start();
        } catch(Exception ex) {
            throw new ServiceRuntimeException(ex);
        }
	}

	public void stop() throws ServiceRuntimeException{
        try {
            runtime.stop();
        } catch(Exception ex) {
            throw new ServiceRuntimeException(ex);
        }	    
	}
	
	public Workspace createWorkspace(){
        Workspace workspace = workspaceFactory.createWorkspace();
        
        workspace.setModelResolver(new ExtensibleModelResolver(workspace, getRegistry()));
        
        return workspace;	    
	}
	

    public Contribution readContribution(String name, String location) throws ServiceRuntimeException{
        try {
            Contribution returnContribution = 
                contributionProcessor.read(null, 
                                           URI.create(name), 
                                           new File(location).toURI().toURL());
            
            analyzeProblems();
            
            return returnContribution;
            
        } catch (Exception ex) {
            throw new ServiceRuntimeException(ex);
        }
    }
	    
    public void addContributionToWorkspace(Workspace workspace, Contribution contribution) throws ServiceRuntimeException{
        try {
            
            workspace.getContributions().add(contribution);

            
        } catch (Exception ex) {
            throw new ServiceRuntimeException(ex);
        }
    }
    
    public void resolveWorkspace(Workspace workspace) throws ServiceRuntimeException {
        try {
            // an algorithm to resolve contributions given their dependencies
            // need to look at the one from 2.x as this one expects contributions
            // to be presented in the right order
            Set<Contribution> resolved = new HashSet<Contribution>();
            for (Contribution contribution: workspace.getContributions()) {
                List<Contribution> dependencies = contributionDependencyBuilder.buildContributionDependencies(contribution, workspace);
                
                // Resolve contributions
                for (Contribution dependency: dependencies) {
                    if (!resolved.contains(dependency)) {
                        resolved.add(dependency);
                        contributionProcessor.resolve(contribution, workspace.getModelResolver());
                    }
                }
            }
            
            analyzeProblems();
            
        } catch (Exception ex) {
            throw new ServiceRuntimeException(ex);
        }
    }
    
    private void analyzeProblems() throws Exception {

        for (Problem problem : monitor.getProblems()) {
            if ((problem.getSeverity() == Severity.ERROR) && (!problem.getMessageId().equals("SchemaError"))) {
                if (problem.getCause() != null) {
                    throw problem.getCause();
                } else {
                    throw new ServiceRuntimeException(problem.toString());
                }
            }
        }
    }

}
