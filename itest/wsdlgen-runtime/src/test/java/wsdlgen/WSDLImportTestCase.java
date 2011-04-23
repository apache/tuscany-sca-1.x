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

package wsdlgen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * Test ?wsdl works for WSDL with <wsdl:import>
 *
 * @version $Rev$ $Date$
 */
public class WSDLImportTestCase extends TestCase {

    private SCADomain domain;

    /**
     * Tests ?wsdl works and returns the correct port endpoint from the WSDL
     */
    public void testWSDLImportPortEndpoint() throws Exception {
        String endpoint = "http://localhost:8086/AccountService";
        String serviceNS = "http://account2/Account/Account";
        String serviceName = "AccountService";
        String portName = "AccountSoapPort";

        printWSDL(endpoint + "?wsdl");

        WSDLReader wsdlReader = WSDLFactory.newInstance().newWSDLReader();
        wsdlReader.setFeature("javax.wsdl.verbose", false);
        wsdlReader.setFeature("javax.wsdl.importDocuments", true);

        Definition definition = wsdlReader.readWSDL(endpoint + "?wsdl");
        assertNotNull(definition);
        Service service = definition.getService(new QName(serviceNS, serviceName));
        Port port = service.getPort(portName);
        String address = getAddress(port);

        assertEquals(endpoint, address);
    }

    private void printWSDL(String url) throws Exception {
        InputStream inp = new URL(url).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inp));
        String line;
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    private String getAddress(Port port) {
        List wsdlPortExtensions = port.getExtensibilityElements();
        for (final Object extension : wsdlPortExtensions) {
            if (extension instanceof SOAPAddress) {
                return ((SOAPAddress) extension).getLocationURI();
            }
        }
        throw new RuntimeException("no SOAPAddress");
    }

    @Override
    protected void setUp() throws Exception {
        domain = SCADomain.newInstance("test-wsdl-import.composite");
    }

    @Override
    protected void tearDown() throws Exception {
        domain.close();
    }
}
