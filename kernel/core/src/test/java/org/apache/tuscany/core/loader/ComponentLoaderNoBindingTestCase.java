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
package org.apache.tuscany.core.loader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import static org.osoa.sca.Version.XML_NAMESPACE_1_0;

import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.implementation.java.PojoComponentType;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.model.BoundReferenceDefinition;
import org.apache.tuscany.spi.model.Implementation;
import org.apache.tuscany.spi.model.ModelObject;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.model.ReferenceDefinition;
import org.apache.tuscany.spi.model.ServiceDefinition;

import junit.framework.TestCase;
import org.apache.tuscany.core.implementation.java.JavaImplementation;
import org.easymock.EasyMock;

/**
 * @version $Rev$ $Date$
 */
public class ComponentLoaderNoBindingTestCase extends TestCase {
    private static final QName COMPONENT = new QName(XML_NAMESPACE_1_0, "component");
    private ComponentLoader loader;
    private XMLStreamReader reader;
    private ServiceDefinition service;
    private BoundReferenceDefinition reference;

    public void testNoServiceBinding() throws Exception {
        loader.load(null, null, reader, null);
        assert service.getBindings().isEmpty();
    }

    public void testNoReferenceBinding() throws Exception {
        loader.load(null, null, reader, null);
        assert reference.getBindings().isEmpty();
    }

    protected void setUp() throws Exception {
        super.setUp();
        service = new ServiceDefinition();
        reference = new BoundReferenceDefinition();
        PojoComponentType<ServiceDefinition, ReferenceDefinition, Property<?>> type =
            new PojoComponentType<ServiceDefinition, ReferenceDefinition, Property<?>>();
        type.add(service);
        type.add(reference);
        JavaImplementation impl = new JavaImplementation();
        impl.setComponentType(type);
        LoaderRegistry registry = EasyMock.createMock(LoaderRegistry.class);
        EasyMock.expect(registry.load((CompositeComponent) EasyMock.isNull(),
            (ModelObject) EasyMock.isNull(),
            EasyMock.isA(XMLStreamReader.class),
            (DeploymentContext) EasyMock.isNull())).andReturn(impl);
        registry.loadComponentType((CompositeComponent) EasyMock.isNull(),
            EasyMock.isA(Implementation.class),
            (DeploymentContext) EasyMock.isNull());
        EasyMock.replay(registry);
        loader = new ComponentLoader(registry, null);
        reader = EasyMock.createMock(XMLStreamReader.class);
        EasyMock.expect(reader.getName()).andReturn(COMPONENT).atLeastOnce();
        EasyMock.expect(reader.next()).andReturn(XMLStreamConstants.END_ELEMENT);
        EasyMock.expect(reader.getAttributeValue(null, "name")).andReturn("foo");
        EasyMock.expect(reader.getAttributeValue(null, "initLevel")).andReturn("0");
        EasyMock.expect(reader.nextTag()).andReturn(1);
        EasyMock.replay(reader);
    }

}
