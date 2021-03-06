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
package org.apache.tuscany.sca.workspace.processor.impl;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.ContributionFactory;
import org.apache.tuscany.sca.contribution.ContributionMetadata;
import org.apache.tuscany.sca.contribution.DefaultExport;
import org.apache.tuscany.sca.contribution.DefaultImport;
import org.apache.tuscany.sca.contribution.Export;
import org.apache.tuscany.sca.contribution.Import;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.ExtensibleStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.ExtensibleURLArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.URLArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.URLArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.contribution.resolver.ClassReference;
import org.apache.tuscany.sca.contribution.resolver.DefaultModelResolver;
import org.apache.tuscany.sca.contribution.resolver.ExtensibleModelResolver;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.resolver.ModelResolverExtensionPoint;
import org.apache.tuscany.sca.contribution.scanner.ContributionScanner;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.definitions.SCADefinitions;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.policy.Intent;
import org.apache.tuscany.sca.policy.IntentAttachPointType;
import org.apache.tuscany.sca.policy.PolicySet;
import org.apache.tuscany.sca.workspace.scanner.impl.DirectoryContributionScanner;
import org.apache.tuscany.sca.workspace.scanner.impl.JarContributionScanner;

/**
 * URLArtifactProcessor that handles contribution files and the artifacts they contain
 * and returns a contribution model.
 *
 * @version $Rev$ $Date$
 */
public class ContributionContentProcessor implements URLArtifactProcessor<Contribution>{
    private ExtensionPointRegistry extensionPoints;
    private ContributionFactory contributionFactory;
    private ModelResolverExtensionPoint modelResolvers;
    private ModelFactoryExtensionPoint modelFactories;
    private URLArtifactProcessor<Object> artifactProcessor;
    private StAXArtifactProcessor<Object> extensionProcessor;
    private UtilityExtensionPoint utilities;
    private Monitor monitor = null;
    private ModelResolver policyDefinitionsResolver = null;
    private List<SCADefinitions> policyDefinitions = null;

    private static final String COMPOSITE_FILE_EXTN = ".composite";

    public ContributionContentProcessor(ExtensionPointRegistry extensionPoints, Monitor monitor) {
        this.extensionPoints = extensionPoints;
        this.modelFactories = extensionPoints.getExtensionPoint(ModelFactoryExtensionPoint.class);
        this.modelResolvers = extensionPoints.getExtensionPoint(ModelResolverExtensionPoint.class);
        hackResolvers(modelResolvers);
        this.monitor = monitor;
        URLArtifactProcessorExtensionPoint artifactProcessors = extensionPoints.getExtensionPoint(URLArtifactProcessorExtensionPoint.class);
        this.artifactProcessor = new ExtensibleURLArtifactProcessor(artifactProcessors, this.monitor);

        // Get and initialize artifact processors
        StAXArtifactProcessorExtensionPoint staxProcessors = extensionPoints.getExtensionPoint(StAXArtifactProcessorExtensionPoint.class);
        XMLInputFactory inputFactory = modelFactories.getFactory(XMLInputFactory.class);
        XMLOutputFactory outputFactory = modelFactories.getFactory(XMLOutputFactory.class);
        this.extensionProcessor = new ExtensibleStAXArtifactProcessor(staxProcessors, inputFactory, outputFactory, monitor);
        this.contributionFactory = modelFactories.getFactory(ContributionFactory.class);
    }

    public ContributionContentProcessor(ExtensionPointRegistry extensionPoints, Monitor monitor,
                                        ModelResolver policyDefinitionsResolver, List<SCADefinitions> policyDefinitions) {
        this(extensionPoints, monitor);
        this.policyDefinitionsResolver = policyDefinitionsResolver;
        this.policyDefinitions = policyDefinitions;
    }

    /*
    public ContributionContentProcessor(ModelFactoryExtensionPoint modelFactories, ModelResolverExtensionPoint modelResolvers,
                                        URLArtifactProcessor<Object> artifactProcessor, StAXArtifactProcessor<Object> extensionProcessor, Monitor monitor) {
        this.modelFactories = modelFactories;
        this.modelResolvers = modelResolvers;
        hackResolvers(modelResolvers);
        this.artifactProcessor = artifactProcessor;
        this.extensionProcessor = extensionProcessor;
        this.contributionFactory = modelFactories.getFactory(ContributionFactory.class);
        this.monitor = monitor;
    }
    */

    public String getArtifactType() {
        return ".contribution/content";
    }

    public Class<Contribution> getModelType() {
        return Contribution.class;
    }

    public Contribution read(URL parentURL, URI contributionURI, URL contributionURL) throws ContributionReadException {

        // Create contribution model
        Contribution contribution = contributionFactory.createContribution();
        contribution.setURI(contributionURI.toString());
        contribution.setLocation(contributionURL.toString());
        ModelResolver modelResolver;
        if (policyDefinitionsResolver != null) {
            modelResolver = new ExtensibleModelResolver(contribution, extensionPoints, modelResolvers, modelFactories, policyDefinitionsResolver);
        } else { 
            modelResolver = new ExtensibleModelResolver(contribution, extensionPoints);
        }
        contribution.setModelResolver(modelResolver);
        contribution.setUnresolved(true);

        // Create a contribution scanner
        ContributionScanner scanner;
        if ("file".equals(contributionURL.getProtocol()) && new File(contributionURL.getFile()).isDirectory()) {
            scanner = new DirectoryContributionScanner();
        } else {
            scanner = new JarContributionScanner();
        }

        // Scan the contribution and list the artifacts contained in it
        List<Artifact> artifacts = contribution.getArtifacts();
        boolean contributionMetadata = false;
        List<String> compositeURIs = new ArrayList<String>();
        List<String> artifactURIs = scanner.getArtifacts(contributionURL);
        for (String artifactURI: artifactURIs) {
            if (artifactURI.endsWith(COMPOSITE_FILE_EXTN)) {
			    // TUSCANY-3561: need to process the composites last
                compositeURIs.add(artifactURI);
            } else {
                URL artifactURL = scanner.getArtifactURL(contributionURL, artifactURI);

                // Add the deployed artifact model to the contribution
                Artifact artifact = this.contributionFactory.createArtifact();
                artifact.setURI(artifactURI);
                artifact.setLocation(artifactURL.toString());
                artifacts.add(artifact);
                modelResolver.addModel(artifact);

                // Read each artifact
                Object model = artifactProcessor.read(contributionURL, URI.create(artifactURI), artifactURL);
                if (model != null) {
                    artifact.setModel(model);

                    // Add the loaded model to the model resolver
                    modelResolver.addModel(model);

                    // Add policy definitions to the list of policy definitions
                    if (policyDefinitionsResolver != null) {
                        addPolicyDefinitions(model);
                    }

                    // Merge contribution metadata into the contribution model
                    if (model instanceof ContributionMetadata) {
                        contributionMetadata = true;
                        ContributionMetadata c = (ContributionMetadata)model;
                        contribution.getImports().addAll(c.getImports());
                        contribution.getExports().addAll(c.getExports());
                        contribution.getDeployables().addAll(c.getDeployables());
                        contribution.getExtensions().addAll(c.getExtensions());
                        contribution.getAttributeExtensions().addAll(c.getAttributeExtensions());
                    }
                }
            }
        }

        // TUSCANY-3561: process the composites last
        for (String artifactURI : compositeURIs) {
            URL artifactURL = scanner.getArtifactURL(contributionURL, artifactURI);

            // Add the deployed artifact model to the contribution
            Artifact artifact = this.contributionFactory.createArtifact();
            artifact.setURI(artifactURI);
            artifact.setLocation(artifactURL.toString());
            artifacts.add(artifact);
            modelResolver.addModel(artifact);

            // Read each artifact
            Object model = artifactProcessor.read(contributionURL, URI.create(artifactURI), artifactURL);
            if (model != null) {
                artifact.setModel(model);

                // Add the loaded model to the model resolver
                modelResolver.addModel(model);
            }
		}

        // If no sca-contribution.xml file was provided then just consider
        // all composites in the contribution as deployables
        if (!contributionMetadata) {
            for (Artifact artifact: artifacts) {
                if (artifact.getModel() instanceof Composite) {
                    contribution.getDeployables().add((Composite)artifact.getModel());
                }
            }

            // Add default contribution import and export
            DefaultImport defaultImport = contributionFactory.createDefaultImport();
            defaultImport.setModelResolver(new DefaultModelResolver());
            contribution.getImports().add(defaultImport);
            DefaultExport defaultExport = contributionFactory.createDefaultExport();
            contribution.getExports().add(defaultExport);
        }

        return contribution;
    }

    public void resolve(Contribution contribution, ModelResolver resolver) throws ContributionResolveException {

        // Resolve the contribution model itself
        ModelResolver contributionResolver = contribution.getModelResolver();
        contribution.setUnresolved(false);
        contributionResolver.addModel(contribution);

        // Resolve imports and exports
        for (Export export: contribution.getExports()) {
            if (export instanceof DefaultExport) {

                // Initialize the default export's resolver
                export.setModelResolver(contributionResolver);

            } else {
                extensionProcessor.resolve(export, contributionResolver);
            }
        }
        for (Import import_: contribution.getImports()) {
            extensionProcessor.resolve(import_, contributionResolver);
        }

        // Resolve all artifact models
        List<Artifact> composites = new ArrayList<Artifact>();
        for (Artifact artifact : contribution.getArtifacts()) {
			// TUSCANY-3561: need to process the composites last
            if (artifact.getURI().endsWith(COMPOSITE_FILE_EXTN)) {
                composites.add(artifact);
            } else {
                Object model = artifact.getModel();
                if (model != null) {
                    try {
                       artifactProcessor.resolve(model, contributionResolver);
                    } catch (ContributionResolveException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new ContributionResolveException(e);
                    }
                }
			}
        }

        // TUSCANY-3561: process the composites last
        for (Artifact artifact : composites) {
            Object model = artifact.getModel();
            if (model != null) {
                try {
                   artifactProcessor.resolve(model, contributionResolver);
                } catch (ContributionResolveException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ContributionResolveException(e);
                }
            }
        }

        // Resolve deployable composites
        List<Composite> deployables = contribution.getDeployables();
        for (int i = 0, n = deployables.size(); i < n; i++) {
            Composite deployable = deployables.get(i);
            Composite resolved = (Composite)contributionResolver.resolveModel(Composite.class, deployable);
            if (resolved != deployable) {
                deployables.set(i, resolved);
            }
        }
    }

    /**
     * FIXME Temporary hack for testing the ClassLoaderModelResolver.
     *
     * @param modelResolvers
     */
    private static void hackResolvers(ModelResolverExtensionPoint modelResolvers) {
        Class<?> resolverClass = modelResolvers.getResolver(ClassReference.class);
        if (resolverClass==null || !resolverClass.getName().equals("org.apache.tuscany.sca.contribution.java.impl.ClassLoaderModelResolver")) {
            try {
                Class<?> loaderResolverClass = Class.forName("org.apache.tuscany.sca.contribution.java.impl.ClassLoaderModelResolver", true, ContributionContentProcessor.class.getClassLoader());
                modelResolvers.addResolver(ClassReference.class, (Class<? extends ModelResolver>)loaderResolverClass);
            } catch (ClassNotFoundException e) {
            }
        }
    }

    /**
     * The following code was copied from ContributionServiceImpl to fix TUSCANY-3171
     *
     * @param model
     */
    private void addPolicyDefinitions(Object model) {

        // Add policy definitions to the list of policy definitions
        if (model instanceof SCADefinitions) {
            policyDefinitions.add((SCADefinitions)model);

            SCADefinitions definitions = (SCADefinitions)model;
            for (Intent intent : definitions.getPolicyIntents() ) {
                policyDefinitionsResolver.addModel(intent);
            }

            for (PolicySet policySet : definitions.getPolicySets() ) {
                policyDefinitionsResolver.addModel(policySet);
            }

            for (IntentAttachPointType attachPointType : definitions.getBindingTypes() ) {
                policyDefinitionsResolver.addModel(attachPointType);
            }

            for (IntentAttachPointType attachPointType : definitions.getImplementationTypes() ) {
                policyDefinitionsResolver.addModel(attachPointType);
            }
            for (Object binding : definitions.getBindings() ) {
                policyDefinitionsResolver.addModel(binding);
            }
        }
    }

}
