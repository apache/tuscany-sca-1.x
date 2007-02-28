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

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.spi.deployer.ArtifactResolver;
import org.apache.tuscany.spi.deployer.ArtifactResolverRegistry;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.model.Contribution;
import org.osoa.sca.annotations.EagerInit;

/**
 * @version $Rev$ $Date$
 */
@EagerInit
public class ArtifactResolverRegistryImpl implements ArtifactResolverRegistry {
    private Map<Class, ArtifactResolver> registry = new HashMap<Class, ArtifactResolver>();

    public void registerResolver(Class<?> modelClass, ArtifactResolver resolver) {
        registry.put(modelClass, resolver);
    }

    public void unregisterResolver(Class<?> modelClass) {
        registry.remove(modelClass);
    }

    public <T> T resolve(Contribution contribution,
                         Class<T> modelClass,
                         String namespace,
                         String name,
                         Map attributes,
                         DeploymentContext context) {
        ArtifactResolver resolver = registry.get(modelClass);
        if (resolver == null) {
            return null;
        }
        return resolver.resolve(contribution, modelClass, namespace, name, attributes, context);
    }

    public URL resolve(Contribution contribution, String targetNamespace, String location, String baseURI) {
        // FIXME: What's a URI resolver?
        ArtifactResolver resolver = registry.get(URI.class);
        if (resolver == null) {
            return null;
        }
        return resolver.resolve(contribution, targetNamespace, location, baseURI);
    }

}
