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

package org.apache.tuscany.sca.binding.http.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.contribution.processor.ExtensibleStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.core.DefaultExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.monitor.impl.DefaultMonitorFactoryImpl;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @version $Rev$ $Date$
 */
public class HTTPBindingProcessorTestCase {
    private static final String XML = "<ns1:binding.http xmlns:ns1=\"http://tuscany.apache.org/xmlns/sca/1.0\" name=\"name\" uri=\"uri\" />";
    
    private static XMLInputFactory inputFactory;
    private static XMLOutputFactory outputFactory;

    private static Monitor monitor;

    private static ExtensibleStAXArtifactProcessor staxProcessor;
    private static HTTPBindingProcessor httpBindingProcessor;
    
    @BeforeClass
    public static void setUp() throws Exception {
        inputFactory = XMLInputFactory.newInstance();
        
        outputFactory = XMLOutputFactory.newInstance();
        outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);
        
        DefaultExtensionPointRegistry extensionPoints = new DefaultExtensionPointRegistry();
        // Create a monitor
        UtilityExtensionPoint utilities = extensionPoints.getExtensionPoint(UtilityExtensionPoint.class);
        MonitorFactory monitorFactory = new DefaultMonitorFactoryImpl();  
        if (monitorFactory != null) {
                monitor = monitorFactory.createMonitor();
                utilities.addUtility(monitorFactory);
        }
        
        StAXArtifactProcessorExtensionPoint staxProcessors = extensionPoints.getExtensionPoint(StAXArtifactProcessorExtensionPoint.class);
        staxProcessor = new ExtensibleStAXArtifactProcessor(staxProcessors, inputFactory, XMLOutputFactory.newInstance(), null);
        
        httpBindingProcessor = new HTTPBindingProcessor(extensionPoints, staxProcessor, null, monitor);
    }
    
    
    /**
     * Read and Write a composite using the HTTP Binding
     * 
     * @throws Exception
     */
    @Test
    public void testReadWriteCompositeWithHTTPBinding() throws Exception {

        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(XML));
        reader.next();
        
        HTTPBinding httpBinding = (HTTPBinding) httpBindingProcessor.read(reader);
        assertNotNull(httpBinding);
        reader.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriter writer = outputFactory.createXMLStreamWriter(bos);
        writer.setDefaultNamespace("http://www.osoa.org/xmlns/sca/1.0");
        httpBindingProcessor.write(httpBinding, writer);
        writer.writeEndDocument();
        
        // used for debug comparison
         System.out.println(XML);
         System.out.println(bos.toString());

        assertEquals(XML, bos.toString());      
    }
}
