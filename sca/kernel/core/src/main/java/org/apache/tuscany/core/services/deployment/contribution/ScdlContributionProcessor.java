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

package org.apache.tuscany.core.services.deployment.contribution;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.stream.XMLInputFactory;

import org.apache.tuscany.core.deployer.RootDeploymentContext;
import org.apache.tuscany.host.deployment.DeploymentException;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.deployer.CompositeClassLoader;
import org.apache.tuscany.spi.deployer.ContributionProcessor;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.ContributionProcessorExtension;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.CompositeImplementation;
import org.apache.tuscany.spi.model.Contribution;

public class ScdlContributionProcessor extends ContributionProcessorExtension implements ContributionProcessor {
    public static final String CONTENT_TYPE = "application/vnd.tuscany.scdl";

    protected XMLInputFactory xmlFactory;
    private final LoaderRegistry registry;

    public ScdlContributionProcessor(@Autowire LoaderRegistry registry) {
        super();
        this.registry = registry;
        this.xmlFactory = XMLInputFactory.newInstance("javax.xml.stream.XMLInputFactory", getClass().getClassLoader());
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    public void processContent(Contribution contribution, URI source, InputStream inputStream)
        throws DeploymentException, IOException {
        if (source == null) {
            throw new IllegalArgumentException("Invalid null source uri.");
        }

        if (inputStream == null) {
            throw new IllegalArgumentException("Invalid null source inputstream.");
        }

        try {
            CompositeClassLoader cl = new CompositeClassLoader(getClass().getClassLoader());
            cl.addURL(contribution.getLocation());
            DeploymentContext deploymentContext = new RootDeploymentContext(cl, this.xmlFactory, null, 
                    contribution.getArtifact(source).getLocation());

            CompositeComponentType componentType = this.registry.load(null, null, 
                    contribution.getArtifact(source).getLocation(), CompositeComponentType.class, deploymentContext);

            CompositeImplementation implementation = new CompositeImplementation();
            implementation.setComponentType(componentType);
            ComponentDefinition<CompositeImplementation> componentDefinition = 
                new ComponentDefinition<CompositeImplementation>(implementation);
            
            componentDefinition.setName(componentType.getName());

            contribution.getArtifact(source).addModelObject(CompositeComponentType.class, null, componentDefinition);

        } catch (LoaderException le) {
            throw new InvalidComponentDefinitionlException(contribution.getArtifact(source).getLocation()
                    .toExternalForm(), le);
        }
    }

    public void processModel(Contribution contribution, URI source, Object modelObject) throws DeploymentException,
            IOException {
    }

}
