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
package org.apache.tuscany.sca.implementation.xyz;

import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.provider.ImplementationProvider;
import org.apache.tuscany.sca.provider.ImplementationProviderFactory;
import org.apache.tuscany.sca.runtime.RuntimeComponent;

/**
 * A factory for POJO implementation providers.
 */
public class ImplementationXYZProviderFactory implements ImplementationProviderFactory<ImplementationXYZ> {
    
    public ImplementationXYZProviderFactory(ExtensionPointRegistry registry) {
    }

    public Class<ImplementationXYZ> getModelType() {
        // Returns the type of model processed by this processor
        return ImplementationXYZ.class;
    }

    public ImplementationProvider createImplementationProvider(RuntimeComponent component, ImplementationXYZ implementation) {
        return new ImplementationXYZProvider(component, implementation);
    }
    
}
