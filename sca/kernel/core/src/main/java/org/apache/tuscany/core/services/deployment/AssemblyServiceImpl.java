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
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.host.deployment.AssemblyService;
import org.apache.tuscany.host.deployment.ContributionService;
import org.apache.tuscany.host.deployment.DeploymentException;
import org.apache.tuscany.host.deployment.UnsupportedContentTypeException;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.bootstrap.ComponentNames;
import org.apache.tuscany.spi.builder.BuilderException;
import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.component.Component;
import org.apache.tuscany.spi.component.ComponentException;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.SCAObject;
import org.apache.tuscany.spi.component.TargetResolutionException;
import org.apache.tuscany.spi.deployer.ChangeSetHandler;
import org.apache.tuscany.spi.deployer.ChangeSetHandlerRegistry;
import org.apache.tuscany.spi.deployer.Deployer;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.Contribution;
import org.apache.tuscany.spi.model.DeployedArtifact;
import org.osoa.sca.annotations.Constructor;

/**
 * @version $Rev$ $Date$
 */
public class AssemblyServiceImpl implements AssemblyService, ChangeSetHandlerRegistry {
    private final Map<String, ChangeSetHandler> registry = new HashMap<String, ChangeSetHandler>();

    private final ContributionService contributionService;

    private final CompositeComponent domain;

    @Constructor
    public AssemblyServiceImpl(@Autowire
    ContributionService contributionService, CompositeComponent domain) {
        this.contributionService = contributionService;
        this.domain = domain;
    }

    public Object addCompositeToDomain(URI contribution, URI composite) throws DeploymentException, IOException {

        Contribution contributionMetadata = (Contribution) this.contributionService
                .getContributionMetaData(contribution);
        DeployedArtifact scdlArtifact = contributionMetadata.getArtifacts().get(composite);
        ComponentDefinition model = (ComponentDefinition) scdlArtifact.getModelObject(CompositeComponentType.class,
                null);

        Component component = null;
        Deployer deployer = null;

        SCAObject child = this.domain.getSystemChild(ComponentNames.TUSCANY_DEPLOYER);
        assert (child instanceof AtomicComponent) : "Deployer must be an atomic component";

        try {

            deployer = (Deployer) ((AtomicComponent) child).getTargetInstance();
            component = deployer.deployFromContribution(this.domain, model);

        } catch (TargetResolutionException e) {
            throw new DomainUpdateException(e);
        } catch (BuilderException e) {
            throw new DomainUpdateException(e);
        } catch (ComponentException e) {
            throw new DomainUpdateException(e);
        }
        component.start();

        return component;
    }

    public void applyChanges(URL changeSet) throws DeploymentException, IOException {
        if (changeSet == null) {
            throw new IllegalArgumentException("changeSet is null");
        }

        URLConnection connection = changeSet.openConnection();
        String contentType = connection.getContentType();
        //todo try and figure out content type from the URL
        if (contentType == null) {
            throw new UnsupportedContentTypeException(null, changeSet.toString());
        }

        InputStream is = connection.getInputStream();
        try {
            applyChanges(is, contentType);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public void applyChanges(InputStream changeSet, String contentType) throws DeploymentException, IOException {
        if (changeSet == null) {
            throw new IllegalArgumentException("changeSet is null");
        }
        if (contentType == null) {
            throw new IllegalArgumentException("contentType is null");
        }

        ChangeSetHandler handler = registry.get(contentType);
        if (handler == null) {
            throw new UnsupportedContentTypeException(contentType);
        }

        handler.applyChanges(changeSet);
    }

    public void register(ChangeSetHandler handler) {
        registry.put(handler.getContentType(), handler);
    }
}
