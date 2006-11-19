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
package org.apache.tuscany.test.binding;

import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.Reference;
import org.apache.tuscany.spi.component.Service;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.BindingBuilderExtension;
import org.apache.tuscany.spi.model.BoundReferenceDefinition;
import org.apache.tuscany.spi.model.BoundServiceDefinition;

/**
 * @version $Rev$ $Date$
 */
public class TestSocketBindingBuilder extends BindingBuilderExtension<TestSocketBinding> {

    public Service build(CompositeComponent parent,
                            BoundServiceDefinition<TestSocketBinding> definition,
                            DeploymentContext context) {
        Class<?> interfaze = definition.getServiceContract().getInterfaceClass();
        int port = definition.getBinding().getPort();
        return new TestSocketBindingService(definition.getName(), port, interfaze, parent, wireService);
    }

    public Reference build(CompositeComponent parent,
                              BoundReferenceDefinition<TestSocketBinding> definition,
                              DeploymentContext context) {
        Class<?> interfaze = definition.getServiceContract().getInterfaceClass();
        String name = definition.getName();
        int port = definition.getBinding().getPort();
        String host = definition.getBinding().getHost();
        return new TestSocketBindingReference(name, host, port, interfaze, parent, wireService);
    }

    protected Class<TestSocketBinding> getBindingType() {
        return TestSocketBinding.class;
    }
}
