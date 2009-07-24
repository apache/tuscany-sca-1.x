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
import org.apache.tuscany.sca.workspace.ContributionConfiguration;
import org.apache.tuscany.sca.workspace.Workspace;
import org.apache.tuscany.sca.workspace.WorkspaceFactory;
import org.apache.tuscany.sca.workspace.builder.ContributionDependencyBuilder;
import org.apache.tuscany.sca.workspace.builder.impl.ContributionDependencyBuilderImpl;
import org.apache.tuscany.sca.workspace.manager.WorkspaceManager;
import org.osoa.sca.ServiceRuntimeException;

/**
 * An SPI for firing up the Tuscany runtime and for providing
 * access to the ExtensionPointRegsitry. With a reference to the
 * registry you can add new extension points and then start the
 * runtime. Once started you can create worksapces and use them
 * to read and resolve SCA contributions in the same way that 
 * Tuscany does internally. 
 */
public class WorkspaceManagerImpl implements WorkspaceManager {
    
    private ReallySmallRuntime runtime;
    
    private ModelFactoryExtensionPoint modelFactories;
    private URLArtifactProcessorExtensionPoint artifactProcessorExtensions;
    private UtilityExtensionPoint utilities;
    
    private WorkspaceFactory workspaceFactory;
    private URLArtifactProcessor<Contribution> contributionProcessor;
    private ContributionDependencyBuilder contributionDependencyBuilder;
    private MonitorFactory monitorFactory;
    private Monitor monitor;
		
	public WorkspaceManagerImpl(){
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
	
	public void start(){
       try {
            runtime.start();
        } catch(Exception ex) {
            throw new ServiceRuntimeException(ex);
        }
	}

	public void stop(){
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
	    
    public Contribution addContributionToWorkspace(Workspace workspace, Contribution contribution){
        try {
            Contribution returnContribution = contributionProcessor.read(null, 
                    URI.create(contribution.getURI()), 
                    new File(contribution.getLocation()).toURI().toURL());
            
            workspace.getContributions().add(returnContribution);
            
            analyzeProblems();
            
            return returnContribution;
            
        } catch (Exception ex) {
            throw new ServiceRuntimeException(ex);
        }
    }
    
    public void resolveWorkspace(Workspace workspace){
        try {
            // some algorithm to resolve contributions given their dependencies
            // need to look at the one from 2.x as this one expects contributions
            // presented in the right order
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
    
    public void removeContributionFromWorkspace(Workspace workspace, Contribution contribution){
        workspace.getContributions().remove(contribution);
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
