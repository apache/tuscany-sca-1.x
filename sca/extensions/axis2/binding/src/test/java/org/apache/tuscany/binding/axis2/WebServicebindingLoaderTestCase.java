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
package org.apache.tuscany.binding.axis2;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.tuscany.idl.wsdl.WSDLDefinitionRegistry;
import org.easymock.EasyMock;

/**
 * Tests for the WebServicebindingLoader class
 */
@SuppressWarnings("deprecation")
public class WebServicebindingLoaderTestCase extends TestCase {

    public void testCreateWSBinding() throws Exception {
        WSDLDefinitionRegistry wsdlReg = createMock(WSDLDefinitionRegistry.class);
        Definition mockDef = createMock(Definition.class);
        expect(wsdlReg.getDefinition("myNS")).andReturn(mockDef);
        replay(wsdlReg);

        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, wsdlReg);

        WebServiceBindingDefinition binding = loader.createWSBinding("myNS#wsdl.service(myService)", null, null, null);
        assertEquals(mockDef, binding.getWSDLDefinition());
        
        EasyMock.verify(wsdlReg);
    }

    public void testCreateWSBindingWsdlService() throws Exception {
        WSDLDefinitionRegistry wsdlReg = createMock(WSDLDefinitionRegistry.class);
        Definition mockDef = createMock(Definition.class);

        Service wsdlService = createMock(Service.class);
        expect(wsdlService.getQName()).andReturn(new QName("myNS", "myService"));

        Map<String, Service> services = new HashMap<String, Service>();
        services.put("myService", wsdlService);
        expect(mockDef.getServices()).andReturn(services);

        expect(wsdlReg.getDefinition("myNS")).andReturn(mockDef);

        replay(wsdlService);
        replay(wsdlReg);
        replay(mockDef);

        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, wsdlReg);

        WebServiceBindingDefinition binding = loader.createWSBinding("myNS#wsdl.service(myService)", null, null, null);
        assertEquals(mockDef, binding.getWSDLDefinition());
        
        assertEquals(wsdlService, binding.getWSDLService());

        EasyMock.verify(wsdlService);
        EasyMock.verify(wsdlReg);
        EasyMock.verify(mockDef);
    }

    public void testCreateWSBindingWsdlPort() throws Exception {
        WSDLDefinitionRegistry wsdlReg = createMock(WSDLDefinitionRegistry.class);
        Definition mockDef = createMock(Definition.class);

        Service wsdlService = createMock(Service.class);
        expect(wsdlService.getQName()).andReturn(new QName("myNS", "myService"));

        Port wsdlPort = createMock(Port.class);
        expect(wsdlService.getPort("myPort")).andReturn(wsdlPort);

        Map<String, Service> services = new HashMap<String, Service>();
        services.put("myService", wsdlService);
        expect(mockDef.getServices()).andReturn(services);

        expect(wsdlReg.getDefinition("myNS")).andReturn(mockDef);

        replay(wsdlService);
        replay(wsdlReg);
        replay(mockDef);

        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, wsdlReg);

        WebServiceBindingDefinition binding = loader.createWSBinding("myNS#wsdl.port(myService/myPort)", null, null, null);
        assertEquals(mockDef, binding.getWSDLDefinition());
        
        assertEquals(wsdlPort, binding.getWSDLPort());

        EasyMock.verify(wsdlService);
        EasyMock.verify(wsdlReg);
        EasyMock.verify(mockDef);
    }

    public void testCreateWSBindingWsdlBinding() throws Exception {
        WSDLDefinitionRegistry wsdlReg = createMock(WSDLDefinitionRegistry.class);
        Definition mockDef = createMock(Definition.class);

        Binding wsdlBinding = createMock(Binding.class);
        QName bindingQN = new QName("myNS", "myBinding");
        expect(mockDef.getBinding(bindingQN)).andReturn(wsdlBinding);

        expect(wsdlReg.getDefinition("myNS")).andReturn(mockDef);

        replay(wsdlReg);
        replay(mockDef);

        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, wsdlReg);

        WebServiceBindingDefinition binding = loader.createWSBinding("myNS#wsdl.binding(myBinding)", null, null, null);
        assertEquals(mockDef, binding.getWSDLDefinition());
        
        assertEquals(wsdlBinding, binding.getBinding());

        EasyMock.verify(wsdlReg);
        EasyMock.verify(mockDef);
    }

    public void testGetWSDLElementURIValue() throws Exception {
        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, null);
        assertEquals("myValue", loader.getWSDLElementURIValue("myNs#wsdl.foo(myValue)", "wsdl.foo"));
        assertNull(loader.getWSDLElementURIValue("myNs#wsdl.XXX(myValue)", "wsdl.foo"));
    }

    public void testBadGetWSDLElementURIValue() throws Exception {
        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, null);
        try {
            loader.getWSDLElementURIValue("myNs#wsdl.foo(myValue", "wsdl.foo");
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetWSDLNamespace() throws Exception {
        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, null);
        assertEquals("myNS", loader.getWSDLNamespace("myNS#bla"));
    }

    public void testNullGetWSDLNamespace() throws Exception {
        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, null);
        assertNull(loader.getWSDLNamespace(""));
        assertNull(loader.getWSDLNamespace(null));
    }

    public void testBadGetWSDLNamespace() throws Exception {
        WebServiceBindingLoader loader = new WebServiceBindingLoader(null, null);
        try {
            loader.getWSDLNamespace("bla");
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            loader.getWSDLNamespace("#bla");
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
