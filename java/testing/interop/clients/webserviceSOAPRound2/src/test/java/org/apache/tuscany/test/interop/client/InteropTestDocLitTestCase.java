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
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.osoa.sca.CurrentModuleContext;
import org.soapinterop.wsdl.interop.test.doc.lit.WSDLInteropTestDocLitPortType;
import org.soapinterop.xsd.ArrayOfstring_literal;
import org.soapinterop.xsd.SOAPStruct;
import org.soapinterop.xsd.XsdPackage;

import commonj.sdo.helper.DataFactory;

/**
 * This test case is part of the tuscany interop tests. This is a reduced version 
 * of the SOAPBuilders InteropTest test, document/literal mode. This version has 
 * operations such as echoString, echoArrayOfString and echoStruct.
 * 
 * <p>The WSDL for the external service used in this test case can be 
 * downloaded from http://www.mssoapinterop.org/stkV3/wsdl/InteropTestDocLit.wsdl.
 * This is part of the WSDL interop test from Microsoft and more details aobout this 
 * test case are found at http://www.mssoapinterop.org/stkV3/wsdl/WSDLInterop-0118.htm 
 */
public class InteropTestDocLitTestCase extends TestCase {

	private TuscanyRuntime tuscany = null;
	private WSDLInteropTestDocLitPortType doc = null;
	private DataFactory dataFactory;
    private ClassLoader oldCL; 
	
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
        doc = locateInteropDocService();
	}

	/**
     * Locate the interop service to test
     * @return
     */
	protected WSDLInteropTestDocLitPortType locateInteropDocService() {
        Object o = CurrentModuleContext.getContext().locateService("RemoteInteropDocService");
        Class[] ifaces = o.getClass().getInterfaces();
        return (WSDLInteropTestDocLitPortType) o;
    }
	
    /**
     * test echo void
     * @throws RemoteException
     */
    public void testEchoVoid() throws RemoteException {
        doc.echoVoid();
        assertTrue(true);
    }

    /**
	 * test echo string
	 * @throws RemoteException
	 */
	public void testEchoString() throws RemoteException {
        String input = "a test string";
		String output = doc.echoString(input);
		assertEquals(input, output);
	}
		
    /**
     * test echo string
     * @throws RemoteException
     */
    public void testEchoStringArray() throws RemoteException {

        ArrayOfstring_literal input=(ArrayOfstring_literal)dataFactory.create(ArrayOfstring_literal.class);
        List inStrings = Arrays.asList(new String[] {"petra", "sue"});
        input.set(XsdPackage.ARRAY_OFSTRING_LITERAL__STRING, inStrings);

        ArrayOfstring_literal output = doc.echoStringArray(input);
         
        List outStrings = output.getString();         
        assertNotNull(outStrings);
        assertEquals(2, outStrings.size());
        assertEquals("petra", outStrings.get(0));
        assertEquals("sue", outStrings.get(1));
    }
        
	/**
	 * test echo struct
	 * @throws RemoteException
	 */
	public void testEchoStruct() throws RemoteException {
		SOAPStruct input=(SOAPStruct)dataFactory.create(SOAPStruct.class);
		input.setVarInt(200);
		input.setVarFloat(.002f);
		input.setVarString("Hello");
		SOAPStruct output = doc.echoStruct(input);
        assertEquals(input.getVarInt(), output.getVarInt());
        assertEquals(input.getVarFloat(), output.getVarFloat());
        assertEquals(input.getVarString(), output.getVarString());
	}
	
	
	protected void tearDown() throws Exception {
		// Stop the runtime
        tuscany.stop();

        Thread.currentThread().setContextClassLoader(oldCL);
        
        super.tearDown();
	}

}
