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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 * Tests for the WebServicebindingLoader class
 */
@SuppressWarnings("deprecation")
public class Axis2BindingBuilderTestCase extends TestCase {

    private static final String NAME = "MyService";
    private static final String COMPOSITE_NAME = "MyComposite";
    private static final String BASE_URI = "http://localhost/services";

    public void testDefaultURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition(null, null, null, null, null, null);
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://localhost/services/MyComposite/MyService", uri.toString());
    }

    public void testExplicitURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition(null, null, null, null, null, "http://foo/bar");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://foo/bar", uri.toString());
    }
    
    public void testExplicitWSDL() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("http://my/wsdl/uri"), "myService", "myPort", null, null);
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://my/wsdl/uri", uri.toString());
    }
    
    public void testExplicitWSDLExplicitURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("http://my/wsdl/uri"), "myService", "myPort", null, "foo");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);

        // explicit URI 'foo' should be ignored
        assertEquals("http://my/wsdl/uri", uri.toString());
    }

    public void testExplicitWSDLBindingExplicitURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDLBinding("http://my/wsdl/uri"), null, null, "myBinding", "http://my/uri");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);

        // explicit URI should be used as the WSDL binding is used not the wsdl service/port
        assertEquals("http://my/uri", uri.toString());
    }

    public void testExplicitWSDLBindingExplicitRelativeURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDLBinding("http://my/wsdl/uri"), null, null, "myBinding", "x/y");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);

        // explicit URI should be used as the WSDL binding is used not the wsdl service/port
        assertEquals("http://localhost/services/MyComposite/MyService/x/y", uri.toString());
    }

    public void testExplicitWSDLBindingExplicitRelativeURIWithDot() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDLBinding("http://my/wsdl/uri"), null, null, "myBinding", "x/./y");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);

        // explicit URI should be used as the WSDL binding is used not the wsdl service/port
        assertEquals("http://localhost/services/MyComposite/MyService/x/y", uri.toString());
    }

    public void testExplicitWSDLBindingExplicitRelativeURIWithDots() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDLBinding("http://my/wsdl/uri"), null, null, "myBinding", "../../x/y");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);

        // explicit URI should be used as the WSDL binding is used not the wsdl service/port
        assertEquals("http://localhost/services/x/y", uri.toString());
    }

    public void testExplicitRelativeWSDL() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("foo/bar"), "myService", "myPort", null, null);
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://localhost/services/MyComposite/MyService/foo/bar", uri.toString());
    }

    public void testExplicitRelativeWSDLWithDots() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("../foo/bar"), "myService", "myPort", null, null);
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://localhost/services/MyComposite/foo/bar", uri.toString());
    }

    public void testExplicitRelativeWSDLExplicitURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("foo/bar"), "myService", "myPort", null, "http://my/wsdl/uri");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://my/wsdl/uri/foo/bar", uri.toString());
    }

    public void testExplicitRelativeWSDLWithDotsExplicitURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("../foo/bar"), "myService", "myPort", null, "http://my/wsdl/uri");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://my/wsdl/foo/bar", uri.toString());
    }

    public void testExplicitRelativeWSDLExplicitRelativeURI() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("foo/bar"), "myService", "myPort", null, "x/y");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://localhost/services/MyComposite/MyService/x/y/foo/bar", uri.toString());
    }

    public void testExplicitRelativeWSDLExplicitRelativeURIWithDots() throws Exception {
        Axis2BindingBuilder builder = new Axis2BindingBuilder();
        WebServiceBindingDefinition wsBinding = new WebServiceBindingDefinition("myNS", createMockWSDL("foo/bar"), "myService", "myPort", null, "../x/y");
        URI uri = builder.computeActualURI(wsBinding, BASE_URI, COMPOSITE_NAME, NAME);
        assertEquals("http://localhost/services/MyComposite/x/y/foo/bar", uri.toString());
    }

    protected Definition createMockWSDL(String endpoint) {
        Definition mockDef = createMock(Definition.class);
        
        Service wsdlService = createMock(Service.class);
        expect(wsdlService.getQName()).andReturn(new QName("myNS", "myService"));

        Port wsdlPort = createMock(Port.class);
        List<SOAPAddress> ees = new ArrayList<SOAPAddress>();
        SOAPAddress sa = createMock(SOAPAddress.class);
        expect(sa.getLocationURI()).andReturn(endpoint);
        ees.add(sa);
        expect(wsdlPort.getExtensibilityElements()).andReturn(ees);

        expect(wsdlService.getPort("myPort")).andReturn(wsdlPort);

        Map<String, Service> services = new HashMap<String, Service>();
        services.put("myService", wsdlService);
        expect(mockDef.getServices()).andReturn(services);

        replay(mockDef);
        replay(wsdlService);
        replay(wsdlPort);
        replay(sa);
        return mockDef;
    }
    protected Definition createMockWSDLBinding(String endpoint) {
        Definition mockDef = createMock(Definition.class);
        replay(mockDef);
        return mockDef;
    }
}
