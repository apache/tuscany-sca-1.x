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

package org.apache.tuscany.spi.extension;

import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.deployer.ContributionProcessor;
import org.apache.tuscany.spi.deployer.ContributionProcessorRegistry;
import org.osoa.sca.annotations.Destroy;
import org.osoa.sca.annotations.EagerInit;
import org.osoa.sca.annotations.Init;

@EagerInit
public abstract class ContributionProcessorExtension implements ContributionProcessor {
    /**
     * The ContributionProcessorRegistry that this processor should register with; usually set by injection. This registry may also be used to process
     * other sub-artifacts.
     */
    protected ContributionProcessorRegistry registry;

    /**
     * Constructor specifies the registry to register with.
     * 
     * @param registry
     *            the LoaderRegistry this loader should register with
     */
    protected ContributionProcessorExtension(@Autowire ContributionProcessorRegistry registry) {
        this.registry = registry;
    }

    /**
     * Initialize the processor. The base implementation registers this loader with the registry as a handler for the XML type returned by
     * {@link #getXMLType()}. Implementations may override this to register the loader as a handler for multiple XML types.
     */
    @Init
    public void start() {
        registry.register(this.getContentType(), this);
    }

    /**
     * Destroy the loader. The base implementation unregisters the loader from the regsitry based on the type returned by {@link #getXMLType()}.
     */
    @Destroy
    public void stop() {
        registry.unregister(this.getContentType());
    }

    /**
     * Returns the content type that this implementation can handle.
     * 
     * @return the content type that this implementation can handle
     */
    public abstract String getContentType();

}
