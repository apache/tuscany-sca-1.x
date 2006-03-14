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
package org.apache.tuscany.samples.helloworldws;

import java.io.File;

import junit.framework.TestCase;

import org.apache.axis2.Constants;
import org.apache.tuscany.samples.helloworldaxis.GetGreetings;
import org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse;
import org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplStub;


public class TestHelloWorldAxisTestCase extends TestCase {
    
    final static String urlstrTuscanyService = "http://localhost:8080/helloworldws-SNAPSHOT/services/HelloWorldService";

    public String getGreetings(String urlstr, String name) throws Exception {
        HelloWorldServiceImplStub stub = new HelloWorldServiceImplStub(urlstr);

        GetGreetings input = new GetGreetings();
        input.setIn0(name);

        GetGreetingsResponse response=stub.getGreetings(input);

        return response.getGetGreetingsReturn();
    }


	public void testHelloWorldDefault() throws Exception {
        System.setProperty(Constants.HOME_AXIS2,System.getProperty("basedir"));
		final String name= "World";
		String greeting= getGreetings(urlstrTuscanyService, name);
		assertEquals(greeting, "Hello " + name);
		
		final String name2= "SCA World!";
		greeting= getGreetings(urlstrTuscanyService, name2);
		assertEquals(greeting, "Hello " + name2);

	}

}
