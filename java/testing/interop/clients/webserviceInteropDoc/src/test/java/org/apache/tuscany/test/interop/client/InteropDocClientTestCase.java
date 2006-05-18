/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.test.interop.client;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.osoa.sca.CurrentModuleContext;
import org.soapinterop.ArrayOfSimpleDocument;
import org.soapinterop.ChildDocument;
import org.soapinterop.ComplexDocument;
import org.soapinterop.DocTestPortType;
import org.soapinterop.SimpleDocument;
import org.soapinterop.SimpleDocument1;
import org.soapinterop.SingleTag;

import commonj.sdo.helper.DataFactory;

public class InteropDocClientTestCase extends TestCase {

    private TuscanyRuntime tuscany;
    private DataFactory dataFactory;
    private DocTestPortType interopDoc;
    private ClassLoader oldCL; 
    
    public void testSingleTag() throws RemoteException {

        // Create the input
        SingleTag input=(SingleTag)dataFactory.create(SingleTag.class);
        
        // Invoke the service
        SingleTag output=interopDoc.SingleTag(input);
        
        // Test the results
        assertNotNull(output);
        
    }
    
    public void testSimpleDocument() throws RemoteException {

        // Create the input
        SimpleDocument1 input=(SimpleDocument1)dataFactory.create(SimpleDocument1.class);
        input.setValue("123");
        
        // Invoke the service
        SimpleDocument1 output=interopDoc.SimpleDocument(input);
        
        // Test the results
        assertNotNull(output);
        assertEquals("123", output.getValue());
        
    }
    
    public void testComplexDocument() throws RemoteException {

        // Create the input
        ComplexDocument input = (ComplexDocument)dataFactory.create(ComplexDocument.class);
        input.setAnAttribute("789");
        ChildDocument childDocument = (ChildDocument)dataFactory.create(ChildDocument.class);
        SimpleDocument simpleDocument = (SimpleDocument)dataFactory.create(SimpleDocument.class);;
        SimpleDocument1 simpleDocument1 = (SimpleDocument1)dataFactory.create(SimpleDocument1.class);;
        simpleDocument.setSimpleDocument(simpleDocument1);
        simpleDocument1.setValue("456");
        ArrayOfSimpleDocument arrayOfSimpleDocument = (ArrayOfSimpleDocument)dataFactory.create(ArrayOfSimpleDocument.class);;
        arrayOfSimpleDocument.getSimpleDocument().add(simpleDocument1);
        childDocument.setChildSimpleDoc(arrayOfSimpleDocument);
        input.setChild(childDocument);
        
        // Invoke the service
        ComplexDocument output=interopDoc.ComplexDocument(input);
        
        // Test the results
        assertNotNull(output);
        assertEquals("789", output.getAnAttribute());
        assertNotNull(output.getChild());
        assertNotNull(output.getChild().getChildSimpleDoc());
        
        //FIXME Add more tests of the output document 
        
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        
        // Required to allow the SDO runtime to use the correct classloader
        oldCL=Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        
        // Obtain Tuscany runtime
        tuscany = new TuscanyRuntime("interopclient", null);
        
        // Start the runtime
        tuscany.start();

        // Get the SDO DataFactory
        dataFactory=DataFactory.INSTANCE;
        
        // Locate the service to test
        interopDoc=locateInteropDocService();
        
    }
    
    /**
     * Locate the interop service to test
     * @return
     */
    protected DocTestPortType locateInteropDocService() {
        String interopLocation = System.getProperty("interopLocation");
        
        // Valid service names are:
        // RemoteInteropDocService: the live interop Web Service 
        // LocalHostInteropDocService: the interop Web Service hosted by Tuscany on localhost
        // LoopbackInteropDocServiceComponent: a dummy loopback service component
        
        // To specify the service name run mvn -DinteropDocServiceName="RemoteInteropDocService"
        
        if (interopLocation == null)
        	interopLocation = "Remote";
        
        return (DocTestPortType)CurrentModuleContext.getContext().locateService(interopLocation + "InteropDocService");
    }
    
    protected void tearDown() throws Exception {
        
        // Stop the runtime
        tuscany.stop();

        Thread.currentThread().setContextClassLoader(oldCL);
        
        super.tearDown();
    }

}
