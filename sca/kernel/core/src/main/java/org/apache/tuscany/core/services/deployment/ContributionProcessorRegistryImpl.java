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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.host.deployment.DeploymentException;
import org.apache.tuscany.host.deployment.UnsupportedContentTypeException;
import org.apache.tuscany.spi.deployer.ContributionProcessor;
import org.apache.tuscany.spi.deployer.ContributionProcessorRegistry;
import org.apache.tuscany.spi.model.Contribution;

public class ContributionProcessorRegistryImpl implements ContributionProcessorRegistry{
    private Map<String, ContributionProcessor> registry = new HashMap<String, ContributionProcessor>();
    private ContributionContentTypeBuilder contentTypeBuilder = new ContributionContentTypeBuilder();
    
    /**
     * ContributionProcessorregistry impl
     */
    
    /* (non-Javadoc)
     * @see org.apache.tuscany.spi.deployer.ContributionProcessorRegistry#register(org.apache.tuscany.spi.deployer.ContributionProcessor)
     */
    public void register(String contentType, ContributionProcessor processor) {
        registry.put(contentType, processor);
    }

    /* (non-Javadoc)
     * @see org.apache.tuscany.spi.deployer.ContributionProcessorRegistry#unregister(java.lang.String)
     */
    public void unregister(String contentType) {
        registry.remove(contentType);
    }
    
    /* (non-Javadoc)
     * @see org.apache.tuscany.spi.deployer.ContributionProcessor#processContent(org.apache.tuscany.spi.model.Contribution, java.net.URI, java.io.InputStream)
     */
    public void processContent(Contribution contribution, URL source, InputStream inputStream) throws DeploymentException, IOException{
        String contentType = this.contentTypeBuilder.resolveContentType(source, null);
        if(contentType == null) {
            throw new UnsupportedContentTypeException("Invalid contentType: null");
        }
        
        if(! this.registry.containsKey(contentType)){
            throw new UnsupportedContentTypeException(contentType, source.getPath());
        }

        this.registry.get(contentType).processContent(contribution, source, inputStream);
        
    }

    /* (non-Javadoc)
     * @see org.apache.tuscany.spi.deployer.ContributionProcessor#processModel(org.apache.tuscany.spi.model.Contribution, java.net.URI, java.lang.Object)
     */
    public void processModel(Contribution contribution, URL source, Object modelObject) throws DeploymentException, IOException{
        
    }
}
