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

package org.apache.tuscany.sca.contribution.namespace.impl;



import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;

import org.apache.tuscany.sca.contribution.namespace.NamespaceImport;
import org.apache.tuscany.sca.contribution.processor.ExtensibleStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.core.DefaultExtensionPointRegistry;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.impl.DefaultMonitorFactoryImpl;
import org.apache.tuscany.sca.monitor.impl.DefaultMonitorImpl;

/**
 * Test NamespaceImportProcessorTestCase
 *
 * @version $Rev$ $Date$
 */
public class NamespaceImportProcessorTestCase extends TestCase {

    private static final String VALID_XML =
        "<?xml version=\"1.0\" encoding=\"ASCII\"?>"
            + "<import  xmlns=\"http://www.osoa.org/xmlns/sca/1.0\" xmlns:ns=\"http://ns\""
            + " ns:ext=\"extended\" namespace=\"http://foo\" location=\"sca://contributions/001\">"
            + "<ns:foo/></import>";

    private static final String INVALID_XML =
        "<?xml version=\"1.0\" encoding=\"ASCII\"?>"
            + "<import  xmlns=\"http://www.osoa.org/xmlns/sca/1.0\" xmlns:ns=\"http://ns\" location=\"sca://contributions/001\"/>";

    private XMLInputFactory inputFactory;
    private StAXArtifactProcessor<Object> staxProcessor;
    private Monitor monitor;

    @Override
    protected void setUp() throws Exception {
        ExtensionPointRegistry extensionPoints = new DefaultExtensionPointRegistry();
        inputFactory = XMLInputFactory.newInstance();
        // Create a monitor
        UtilityExtensionPoint utilities = extensionPoints.getExtensionPoint(UtilityExtensionPoint.class);
        MonitorFactory monitorFactory = new DefaultMonitorFactoryImpl();
        if (monitorFactory != null) {
        	monitor = monitorFactory.createMonitor();
        	utilities.addUtility(monitorFactory);
        }
        StAXArtifactProcessorExtensionPoint staxProcessors = extensionPoints.getExtensionPoint(StAXArtifactProcessorExtensionPoint.class);
        staxProcessor = new ExtensibleStAXArtifactProcessor(staxProcessors, inputFactory, null, monitor);
    }

    /**
     * Test loading a valid import element from a contribution metadata stream
     * @throws Exception
     */
    public void testLoad() throws Exception {
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(VALID_XML));
        NamespaceImport namespaceImport = (NamespaceImport)staxProcessor.read(reader);

        assertEquals("http://foo", namespaceImport.getNamespace());
        assertEquals("sca://contributions/001", namespaceImport.getLocation());
        assertEquals(1, namespaceImport.getAttributeExtensions().size());
        assertEquals(1, namespaceImport.getExtensions().size());
    }

    /**
     * Test loading a INVALID import element from a contribution metadata stream
     * @throws Exception
     */
    public void testLoadInvalid() throws Exception {
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(INVALID_XML));
        /*try {
            staxProcessor.read(reader);
            fail("readerException should have been thrown");
        } catch (ContributionReadException e) {
            assertTrue(true);
        }*/
        staxProcessor.read(reader);
        Problem problem = ((DefaultMonitorImpl)monitor).getLastLoggedProblem();
        assertNotNull(problem);
        assertEquals("AttributeNameSpaceMissing", problem.getMessageId());
    }
}
