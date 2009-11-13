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

package org.apache.tuscany.sca.contribution.java;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.PackageProcessor;
import org.apache.tuscany.sca.contribution.service.ContributionException;
import org.apache.tuscany.sca.extensibility.ServiceDeclaration;
import org.apache.tuscany.sca.extensibility.ServiceDiscovery;


/**
 * Default implementation of a contribution classloader provider extension point.
 *
 * @version $Rev$ $Date$
 */
public class DefaultContributionClassloaderProviderExtensionPoint implements ContributionClassloaderProviderExtensionPoint {
    
    private HashMap<String, ContributionClassLoaderProvider> providers = new HashMap<String, ContributionClassLoaderProvider>();
    private boolean loaded;
    
    /**
     * Constructs a new DefaultModelFactoryExtensionPoint.
     */
    public DefaultContributionClassloaderProviderExtensionPoint() {
    }

    /**
     * Add a contribution classloader provider extension.
     * 
     * @param provider The provider to add
     */
    public void addProvider(ContributionClassLoaderProvider provider){
        providers.put(provider.getContributionType(), provider);
    }
    
    /**
     * Remove a contribution classloader provider extension.
     *  
     * @param provider The provider to remove
     */
    public void removeProvider(ContributionClassLoaderProvider provider){
        providers.remove(provider.getContributionType());
    }
    
    /**
     * Get a contribution classloader provider for the given contribution type.
     * @param contributionType the lookup key 
     * @return The provider
     */
    public ContributionClassLoaderProvider getProvider(String contributionType){
        loadProviders();
        return providers.get(contributionType);
    }

    private synchronized void loadProviders() {
        if (loaded)
            return;

        // Get the processor service declarations
        Set<ServiceDeclaration> processorDeclarations; 
        try {
            processorDeclarations = ServiceDiscovery.getInstance().getServiceDeclarations(ContributionClassLoaderProvider.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        
        for (ServiceDeclaration processorDeclaration: processorDeclarations) {
            Map<String, String> attributes = processorDeclaration.getAttributes();
            
            // Load a URL artifact processor
            String contributionType = attributes.get("type");
            
            // Create a processor wrapper and register it
            ContributionClassLoaderProvider provider = new LazyContributionClassLoaderProvider(contributionType, processorDeclaration);
            addProvider(provider);
        }
        
        loaded = true;
    }

    /**
     * A facade for package processors.
     */
    private static class LazyContributionClassLoaderProvider implements ContributionClassLoaderProvider {
        
        private ServiceDeclaration processorDeclaration;
        private String contributionType;
        private ContributionClassLoaderProvider provider;
        
        private LazyContributionClassLoaderProvider(String contributionType, ServiceDeclaration processorDeclaration) {
            this.processorDeclaration = processorDeclaration;
            this.contributionType = contributionType;
        }

        public String getContributionType() {
            return contributionType;
        }
        
        @SuppressWarnings("unchecked")
        public ClassLoader getClassLoader(Contribution contribution, ClassLoader parent){
            if (provider == null) {
                try {
                    Class<ContributionClassLoaderProvider> providerClass = (Class<ContributionClassLoaderProvider>)processorDeclaration.loadClass();
                    Constructor<ContributionClassLoaderProvider> constructor = providerClass.getConstructor();
                    provider = constructor.newInstance();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return provider.getClassLoader(contribution, parent);
        }
    }

}
