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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.core.services.deployment.ContributionLoader;
import org.apache.tuscany.host.deployment.DeploymentException;
import org.apache.tuscany.spi.deployer.ContentType;
import org.apache.tuscany.spi.deployer.ContributionProcessor;
import org.apache.tuscany.spi.extension.ContributionProcessorExtension;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.model.Contribution;

public class MetadataContributionProcessor extends ContributionProcessorExtension implements ContributionProcessor {
    /**
     * Content-type that this processor can handle
     */
    public static final String CONTENT_TYPE = ContentType.CONTRIBUTION_METADATA;

    protected XMLInputFactory xmlFactory;
    protected ContributionLoader contributionLoader;

    public MetadataContributionProcessor() {
        super();
        this.xmlFactory = XMLInputFactory.newInstance("javax.xml.stream.XMLInputFactory", getClass().getClassLoader());
        this.contributionLoader = new ContributionLoader(null);
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    public void processContent(Contribution contribution, URI artifactURI, InputStream inputStream)
        throws DeploymentException, IOException {
        if (artifactURI == null) {
            throw new IllegalArgumentException("Invalid null source uri.");
        }

        if (inputStream == null) {
            throw new IllegalArgumentException("Invalid null source inputstream.");
        }

        try {
            XMLStreamReader xmlReader = this.xmlFactory.createXMLStreamReader(inputStream);
            Contribution contributionMetadata = this.contributionLoader.load(null, null, xmlReader, null);

            for (QName deployable : contributionMetadata.getDeployables()) {
                System.out.println("Deployable : " + deployable.toString());
            }

        } catch (XMLStreamException xmle) {
            throw new InvalidComponentDefinitionlException(contribution.getArtifact(artifactURI).getLocation()
                    .toExternalForm(), xmle);
        } catch (LoaderException le){
            throw new InvalidComponentDefinitionlException(contribution.getArtifact(artifactURI).getLocation()
                                                           .toExternalForm(), le);
        }
    }

    public void processModel(Contribution contribution, URI source, Object modelObject) throws DeploymentException,
            IOException {
    }

}
