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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;

import org.apache.tuscany.core.deployer.RootDeploymentContext;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.model.BindingDefinition;
import org.apache.tuscany.spi.model.ComponentType;
import org.apache.tuscany.spi.model.ComponentTypeReferenceDefinition;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.model.ServiceContract;
import org.apache.tuscany.spi.model.ServiceDefinition;
import org.easymock.EasyMock;
import org.osoa.sca.Constants;

/**
 * Verifies loading of a reference definition from an XML-based assembly
 *
 * @version $Rev$ $Date$
 */
public class ReferenceLoaderTestCase extends TestCase {
    private static final QName REFERENCE = new QName(Constants.SCA_NS, "reference");

    private ReferenceLoader loader;
    private DeploymentContext deploymentContext;
    private XMLStreamReader mockReader;
    private LoaderRegistry mockRegistry;

    public void testWithNoInterface() throws LoaderException, XMLStreamException {
        String name = "referenceDefinition";
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.expect(mockReader.getAttributeValue(null, "name")).andReturn(name);
        EasyMock.expect(mockReader.getAttributeValue(null, "multiplicity")).andReturn("0..1");
        EasyMock.expect(mockReader.getAttributeValue(null, "wiredByImpl")).andReturn("false");
        EasyMock.expect(mockReader.getAttributeValue(null, "target")).andReturn(null);
        EasyMock.expect(mockReader.next()).andReturn(XMLStreamConstants.END_ELEMENT);
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.replay(mockReader);
        ComponentTypeReferenceDefinition referenceDefinition = 
            loader.load(null, new ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>>(), mockReader, null);
        assertNotNull(referenceDefinition);
        assertEquals(name, referenceDefinition.getName());
    }

    public void testComponentTypeService() throws LoaderException, XMLStreamException {
        String name = "reference";
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.expect(mockReader.getAttributeValue(null, "name")).andReturn(name);
        EasyMock.expect(mockReader.getAttributeValue(null, "multiplicity")).andReturn("0..1");
        EasyMock.expect(mockReader.getAttributeValue(null, "wiredByImpl")).andReturn("false");
        EasyMock.expect(mockReader.getAttributeValue(null, "target")).andReturn(null);
        EasyMock.expect(mockReader.next()).andReturn(XMLStreamConstants.END_ELEMENT);
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.replay(mockReader);
        ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>> type =
            new ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>>();
        ComponentTypeReferenceDefinition referenceDefinition = loader.load(null, type, mockReader, null);
        assertTrue(ComponentTypeReferenceDefinition.class.equals(referenceDefinition.getClass()));
    }

    public void testMultipleBindings() throws LoaderException, XMLStreamException {
        String name = "referenceDefinition";
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.expect(mockReader.getAttributeValue(null, "name")).andReturn(name);
        EasyMock.expect(mockReader.getAttributeValue(null, "multiplicity")).andReturn("0..1");
        EasyMock.expect(mockReader.getAttributeValue(null, "wiredByImpl")).andReturn("false");
        EasyMock.expect(mockReader.getAttributeValue(null, "target")).andReturn(null);
        EasyMock.expect(mockReader.next()).andReturn(XMLStreamConstants.START_ELEMENT).times(2);
        EasyMock.expect(mockReader.next()).andReturn(XMLStreamConstants.END_ELEMENT);
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.replay(mockReader);

        BindingDefinition binding = new BindingDefinition() {
            public Object clone() {
               return null;
            }
        };
        EasyMock.expect(mockRegistry.load(null, null, mockReader, null)).andReturn(binding).times(2);
        EasyMock.replay(mockRegistry);

        ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>> type =
            new ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>>();
        ComponentTypeReferenceDefinition referenceDefinition = loader.load(null, type, mockReader, null);
        assertEquals(2, referenceDefinition.getBindings().size());
    }

    public void testWithInterface() throws LoaderException, XMLStreamException {
        String name = "referenceDefinition";
        ServiceContract sc = new ServiceContract() {
        };
        EasyMock.expect(mockReader.getName()).andReturn(REFERENCE).anyTimes();
        EasyMock.expect(mockReader.getAttributeValue(null, "name")).andReturn(name);
        EasyMock.expect(mockReader.getAttributeValue(null, "multiplicity")).andReturn("0..1");
        EasyMock.expect(mockReader.getAttributeValue(null, "wiredByImpl")).andReturn("false");
        EasyMock.expect(mockReader.getAttributeValue(null, "target")).andReturn(null);
        EasyMock.expect(mockReader.next()).andReturn(XMLStreamConstants.START_ELEMENT);
        EasyMock.expect(mockRegistry.load(null, null, mockReader, deploymentContext)).andReturn(sc);
        EasyMock.expect(mockReader.next()).andReturn(XMLStreamConstants.END_ELEMENT);

        EasyMock.replay(mockReader);
        EasyMock.replay(mockRegistry);
        
        ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>> type =
            new ComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>>();
        ComponentTypeReferenceDefinition referenceDefinition = loader.load(null, type, mockReader, deploymentContext);
        assertNotNull(referenceDefinition);
        assertEquals(name, referenceDefinition.getName());
        assertSame(sc, referenceDefinition.getServiceContract());
    }

    protected void setUp() throws Exception {
        super.setUp();
        mockReader = EasyMock.createStrictMock(XMLStreamReader.class);
        mockRegistry = EasyMock.createMock(LoaderRegistry.class);
        loader = new ReferenceLoader(mockRegistry);
        deploymentContext = new RootDeploymentContext(null, null, null, null);
    }
}
