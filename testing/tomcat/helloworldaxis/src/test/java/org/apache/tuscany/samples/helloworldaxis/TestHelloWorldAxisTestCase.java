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
package org.apache.tuscany.samples.helloworldaxis;

import junit.framework.TestCase;

import org.apache.tuscany.samples.helloworldaxis.HelloWorldAxisClient;


public class TestHelloWorldAxisTestCase extends TestCase {
	final static String urlstrAxisService=   "http://localhost:8080/tuscany-samples-helloworldaxissvc/services/helloworld";
	final static String urlstrTuscanyService= "http://localhost:8080/tuscany-samples-helloworldws-service/services/HelloWorldService";
	/**
	 * Test using axis client against axis server
	 * @throws Exception
	 */
	public void testHelloWorldAgainstAxis() throws Exception
	{

		helloWorld(urlstrAxisService);
		
		
	}

	public void testHelloWorldAgainstTuscany() throws Exception
	{

		helloWorld(urlstrTuscanyService);
		
		
	}

	
	private void helloWorld(final String serviceURL) throws Exception
	{


		String val= (new HelloWorldAxisClient()).getGreetings(serviceURL,"World");
		assertEquals(val,"Hello World");
		val= (new HelloWorldAxisClient()).getGreetings(serviceURL,"Axis World");
		assertEquals(val,"Hello Axis World");
		
	}

}
