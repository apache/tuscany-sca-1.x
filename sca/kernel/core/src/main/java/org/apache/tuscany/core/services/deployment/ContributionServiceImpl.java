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
package org.apache.tuscany.core.services.deployment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.core.util.IOHelper;
import org.apache.tuscany.host.deployment.ContributionService;
import org.apache.tuscany.host.deployment.DeploymentException;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.deployer.ArtifactResolverRegistry;
import org.apache.tuscany.spi.deployer.ContributionProcessorRegistry;
import org.apache.tuscany.spi.deployer.ContributionRepository;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.Contribution;
import org.apache.tuscany.spi.model.DeployedArtifact;

/**
 * @version $Rev$ $Date$
 */
public class ContributionServiceImpl implements ContributionService {
    /**
     * Repository where contributions are stored. Usually set by injection.
     */
    protected ContributionRepository contributionRepository;

    /**
     * Registry of available processors. Usually set by injection.
     */
    protected ContributionProcessorRegistry processorRegistry;

    /**
     * Contribution registry This is a registry of processed Contributios index
     * by URI
     */
    protected Map<URI, Contribution> contributionRegistry = new HashMap<URI, Contribution>();

    protected ArtifactResolverRegistry resolverRegistry;

    public ContributionServiceImpl(@Autowire
    ContributionRepository repository, @Autowire
    ContributionProcessorRegistry processorRegistry, @Autowire
    ArtifactResolverRegistry resolverRegistry) {
        super();
        this.contributionRepository = repository;
        this.processorRegistry = processorRegistry;
        this.resolverRegistry = resolverRegistry;
    }

    public void contribute(URI contributionURI, URL sourceURL, boolean storeInRepository) throws DeploymentException,
        IOException {
        if (sourceURL == null) {
            throw new IllegalArgumentException("Source URL for the contribution is null");
        }

        Contribution contribution = new Contribution(contributionURI);
        contribution.setLocation(sourceURL);
        InputStream is = IOHelper.getInputStream(sourceURL);
        try {
            addContribution(contribution, is, storeInRepository);
        } finally {
            IOHelper.closeQuietly(is);
        }
    }

    public void contribute(URI contributionURI, InputStream input) throws DeploymentException, IOException {
        Contribution contribution = new Contribution(contributionURI);
        addContribution(contribution, input, true);
    }

    private void addContribution(Contribution contribution, InputStream contributionStream, boolean storeInRepository)
        throws IOException, MalformedURLException, DeploymentException {
        if (contributionStream == null && contribution.getLocation() == null) {
            throw new IllegalArgumentException("The content of the contribution is null");
        }

        // store the contribution in the contribution repository
        if (storeInRepository) {
            URL locationURL = null;
            if (contribution.getLocation() != null) {
                locationURL = contributionRepository.store(contribution.getUri(), contribution.getLocation());
            } else {
                locationURL = contributionRepository.store(contribution.getUri(), contributionStream);
            }
            contribution.setLocation(locationURL);
        }

        // process the contribution
        this.processorRegistry.processContent(contribution, contribution.getUri(), contributionStream);

        // store the contribution on the registry
        this.contributionRegistry.put(contribution.getUri(), contribution);
    }

    public Object getContribution(URI id) {
        return this.contributionRegistry.get(id);
    }

    public void remove(URI contribution) throws DeploymentException {
        // remove from repository
        this.contributionRegistry.remove(contribution);
    }

    public void addDeploymentComposite(URI contribution, Object composite) {
        CompositeComponentType model = (CompositeComponentType)composite;
        URI compositeURI = contribution.resolve(model.getName() + ".composite");
        DeployedArtifact artifact = new DeployedArtifact(compositeURI);
        // FIXME: the namespace should be from the CompositeComponentType model
        artifact.addModelObject(composite.getClass(), null, composite);
        Contribution contributionObject = (Contribution)getContribution(contribution);
        contributionObject.addArtifact(artifact);
    }

    public <T> T resolve(URI contribution, Class<T> definitionType, String namespace, String name) {
        Contribution contributionObject = (Contribution)getContribution(contribution);
        return resolverRegistry.resolve(contributionObject, definitionType, namespace, name, null, null);
    }

    public URL resolve(URI contribution, String namespace, URI uri, URI baseURI) {
        Contribution contributionObject = (Contribution)getContribution(contribution);
        return resolverRegistry.resolve(contributionObject, namespace, uri.toString(), baseURI.toString());
    }

}
