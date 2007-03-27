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

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.Assert;

import org.apache.tuscany.core.binding.local.LocalBindingDefinition;
import org.apache.tuscany.core.implementation.java.JavaImplementation;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.idl.java.JavaServiceContract;
import org.apache.tuscany.spi.implementation.java.PojoComponentType;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.loader.ReferenceMultiplicityOverridingException;
import org.apache.tuscany.spi.model.BindingDefinition;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.ComponentTypeReferenceDefinition;
import org.apache.tuscany.spi.model.Implementation;
import org.apache.tuscany.spi.model.Multiplicity;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.model.ServiceDefinition;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Tests to verify overriding of aspects of componenttype reference defintions
 * by component definitions
 */
public class ComponentReferenceOverridingTestCase {
    private static XMLInputFactory xmlFactory = XMLInputFactory.newInstance();;
    private static LoaderRegistryImpl loaderRegistry;

    public XMLStreamReader getReader(String xml) throws XMLStreamException {
        XMLStreamReader reader = xmlFactory.createXMLStreamReader(new StringReader(xml));
        reader.next();
        return reader;
    }

 
    @Test
    public void testRefDefNoOverriding() throws LoaderException,
                                      XMLStreamException,
                                      URISyntaxException {
        String compDefXml =
            "<tus:component name='TestComponent' xmlns:tus='http://www.osoa.org/xmlns/sca/1.0'>" 
            + "<tus:implementation.java class='mockedup'/>"
            + "</tus:component>";

        TestComponentLoader compLoader = new TestComponentLoader(EasyMock.createMock(LoaderRegistry.class));
        ComponentDefinition<?> compDef = compLoader.load(null, null, getReader(compDefXml), null);
        
        //verify if component inherits referece definitions from componentType
        Assert.assertNotNull(compDef.getReferences().get("CTRefDef1"));
        Assert.assertEquals(Multiplicity.ZERO_N.name(), compDef.getReferences().get("CTRefDef1").getMultiplicity().name());
        Assert.assertEquals("TestComponent1/testService1", 
                            compDef.getReferences().get("CTRefDef1").getTargets().get(1).toString());
        Assert.assertTrue(
          TestIfc1.class.isAssignableFrom(compDef.getReferences().get("CTRefDef1").getServiceContract().getInterfaceClass()));
        Assert.assertTrue(compDef.getReferences().get("CTRefDef1").getBindings().get(0) instanceof LocalBindingDefinition);
    }
    
    @Test
    public void testRefDefValidOverriding() throws LoaderException,
                                      XMLStreamException,
                                      URISyntaxException {
        String overridenRef =
            "<tus:component name='TestComponent' xmlns:tus='http://www.osoa.org/xmlns/sca/1.0'>" 
            + "<tus:implementation.java class='mockedup'/>"
            + "<tus:reference name='CTRefDef1' target='OverridingTarget' multiplicity='0..1'>"
            + "</tus:reference>"
            + "</tus:component>";

        TestComponentLoader compLoader = new TestComponentLoader(EasyMock.createMock(LoaderRegistry.class));
        
        ComponentDefinition<?> compDef = compLoader.load(null, null, getReader(overridenRef), null);
    
        //verify if component inherits referece definitions from componentType
        Assert.assertNotNull(compDef.getReferences().get("CTRefDef1"));
        Assert.assertEquals(Multiplicity.ZERO_ONE.name(), 
                            compDef.getReferences().get("CTRefDef1").getMultiplicity().name());
        Assert.assertEquals("OverridingTarget", 
                            compDef.getReferences().get("CTRefDef1").getTargets().get(0).toString());
        Assert.assertTrue(
          TestIfc1.class.isAssignableFrom(compDef.getReferences().get("CTRefDef1").getServiceContract().getInterfaceClass()));
        Assert.assertTrue(compDef.getReferences().get("CTRefDef1").getBindings().get(0) instanceof LocalBindingDefinition);
    }
    
    @Test
    public void testRefDefInValidOverriding() throws LoaderException,
                                      XMLStreamException,
                                      URISyntaxException {
        String overridenRef =
            "<tus:component name='TestComponent' xmlns:tus='http://www.osoa.org/xmlns/sca/1.0'>" 
            + "<tus:implementation.java class='mockedup'/>"
            + "<tus:reference name='CTRefDef1' target='OverridingTarget' multiplicity='1..1'/>"
            + "</tus:component>";

        TestComponentLoader compLoader = new TestComponentLoader(EasyMock.createMock(LoaderRegistry.class));
        
        try {
            ComponentDefinition<?> compDef = compLoader.load(null, null, getReader(overridenRef), null);
            Assert.fail();
        } catch ( ReferenceMultiplicityOverridingException e) {
            //expected behaviour
        }
    }

    
    public static interface TestIfc1 {
        public String testMethod1();
    }

    public class TestComponentLoader extends ComponentLoader {

        public TestComponentLoader(LoaderRegistry registry) {
            super(registry, null);
        }

        @Override
        protected Implementation<?> loadImplementation(CompositeComponent parent,
                                                       XMLStreamReader reader,
                                                       DeploymentContext deploymentContext) throws XMLStreamException,
                                                                                           LoaderException {
            ComponentTypeReferenceDefinition ctRefDef = new ComponentTypeReferenceDefinition();

            ctRefDef.setName("CTRefDef1");
            try {
                ctRefDef.addTarget(new URI("TestComponent1"));
                ctRefDef.addTarget(new URI("TestComponent1/testService1"));
            
                ctRefDef.setMultiplicity(Multiplicity.ZERO_N);
    
                JavaServiceContract svcContract = new JavaServiceContract(TestIfc1.class);
                ctRefDef.setServiceContract(svcContract);
    
                BindingDefinition binding1 = new LocalBindingDefinition(new URI("localBindingUri"));
                ctRefDef.addBinding(binding1);
            } catch (URISyntaxException e) {
                //do nothing... unlikely to happen
            }

            PojoComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>> ct =
                new PojoComponentType<ServiceDefinition, ComponentTypeReferenceDefinition, Property<?>>();
            ct.add(ctRefDef);

            JavaImplementation javaImpl = new JavaImplementation();
            javaImpl.setComponentType(ct);
            reader.next();
            reader.next();
            return javaImpl;
        }
    }
}
