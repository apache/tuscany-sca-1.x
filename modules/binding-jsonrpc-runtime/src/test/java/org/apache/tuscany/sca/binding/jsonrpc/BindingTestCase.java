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
package org.apache.tuscany.sca.binding.jsonrpc;

import helloworld.BeanA;
import helloworld.HelloWorldService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.Assert;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class BindingTestCase {

    private static SCANode node;

    /* SMD =
     *
     * {"SMDVersion":".1","objectName":"HelloWorldService","serviceType":"JSON-RPC","serviceURL":"http://ibm-7a66aa9141b:8085/HelloWorldComponent/HelloWorldService","methods":[{"name":"sayHello","parameters":[{"name":"param0","type":"STRING"}]},{"name":"sayHello2","parameters":[{"name":"param0","type":"STRING"},{"name":"param1","type":"STRING"}]},{"name":"sayHello3","parameters":[{"name":"param0","type":"STRING"}]},{"name":"sayHello4","parameters":[{"name":"param0","type":"STRING"}]},{"name":"sayHello5","parameters":[{"name":"param0","type":"STRING"}]},{"name":"sayHello6","parameters":[{"name":"param0","type":"STRING"},{"name":"param1","type":"STRING"},{"name":"param2","type":"STRING"}]},{"name":"sayHello7","parameters":[]}]}
     */

    public JSONObject callService(String url, JSONObject jsonRequest) throws Exception {
        System.out.println("Request = " + jsonRequest.toString());
        WebConversation wc   = new WebConversation();
        WebRequest request   = new PostMethodWebRequest( url,
                                                         new ByteArrayInputStream(jsonRequest.toString().getBytes("UTF-8")),"application/json");
        WebResponse response = wc.getResource(request);
        System.out.println("Response= " + response.getText());
        Assert.assertEquals(200, response.getResponseCode());
        return new JSONObject(response.getText());
    }

    @Test
    public void testOneArg() throws MalformedURLException, IOException {
        try {
	        JSONObject jsonRequest = new JSONObject("{\"params\":[\"petra\"],\"method\":\"sayHello\",\"id\":1}");
	        JSONObject jsonResp    = callService ("http://localhost:8085/HelloWorldComponent/HelloWorldService",
	                                              jsonRequest);
	        Assert.assertNotNull(jsonResp);
	        Assert.assertEquals("{\"id\":1,\"result\":\"Hello petra\"}", jsonResp.toString());

        } catch(Exception ex){
        	ex.printStackTrace();
        	Assert.fail();
        }
    }

    @Test
    public void testTwoArgs() throws MalformedURLException, IOException {
        try {
	        JSONObject jsonRequest = new JSONObject("{\"params\":[\"petra\", \"arnold\"],\"method\":\"sayHello2\",\"id\":1}");
	        JSONObject jsonResp    = callService ("http://localhost:8085/HelloWorldComponent/HelloWorldService",
	                                              jsonRequest);
	        Assert.assertNotNull(jsonResp);
	        Assert.assertEquals("{\"id\":1,\"result\":\"Hello petra arnold\"}", jsonResp.toString());

        } catch(Exception ex){
        	ex.printStackTrace();
        	Assert.fail();
        }

    }

    @Test
    public void testComplexParams() throws MalformedURLException, IOException {
        try {
	        JSONObject jsonRequest = new JSONObject("{\"params\":[{\"b\":true, \"s\":\"fred\", \"x\":2, \"y\":5}],\"method\":\"sayHello3\",\"id\":1}");
	        JSONObject jsonResp    = callService ("http://localhost:8085/HelloWorldComponent/HelloWorldService",
	                                              jsonRequest);
	        Assert.assertNotNull(jsonResp);

	        //JabSorb sends class hints with complex parameters/types
	        Assert.assertEquals("{\"id\":1,\"result\":{\"s\":\"XYZ\",\"b\":true,\"javaClass\":\"helloworld.BeanA\",\"y\":5,\"x\":2}}", jsonResp.toString());

        } catch(Exception ex){
        	ex.printStackTrace();
        	Assert.fail();
        }
    }

    @Test
    public void testStringArray() throws MalformedURLException, IOException {

        try {
	        JSONObject jsonRequest = new JSONObject("{\"params\":[[\"Fred\", \"Bloggs\"]],\"method\":\"sayHello4\",\"id\":1}");
	        JSONObject jsonResp    = callService ("http://localhost:8085/HelloWorldComponent/HelloWorldService",
	                                              jsonRequest);
	        Assert.assertNotNull(jsonResp);
	        Assert.assertEquals("{\"id\":1,\"result\":[\"Hello Fred Bloggs\"]}", jsonResp.toString());

        } catch(Exception ex){
        	ex.printStackTrace();
        	Assert.fail();
        }
    }

    @Test
    @Ignore
    public void testBeanArray() throws MalformedURLException, IOException {
/*
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        BeanA bean = new BeanA();
        bean.setB(true);
        bean.setS("Fred");
        bean.setX(2);
        bean.setX(5);

        BeanA[] beans = {bean};
        BeanA[] response = client.sayHello5(beans);

        Assert.assertEquals("Hello Fred", response[0].getS());
*/

        try {
	        JSONObject jsonRequest = new JSONObject("{\"params\":[{\"b\":true, \"s\":\"fred\", \"x\":2, \"y\":5}],\"method\":\"sayHello3\",\"id\":1}");
	        JSONObject jsonResp    = callService ("http://localhost:8085/HelloWorldComponent/HelloWorldService",
	                                              jsonRequest);
	        Assert.assertNotNull(jsonResp);
	        Assert.assertEquals("{\"id\":1,\"result\":{\"s\":\"XYZ\",\"b\":true,\"y\":5,\"x\":2}}", jsonResp.toString());

        } catch(Exception ex){
        	ex.printStackTrace();
        	Assert.fail();
        }
    }

    /* HTTP Request for testMixedArray
        GET /HelloWorldComponent/HelloWorldService/sayHello6?arg0=%5B%7B%22b%22%3Atrue%2C%22s%22%3A%22Fred%22%2C%22y%22%3Anull%2C%22x%22%3A5%7D%5D&arg1=%5B%22Fred%22%2C%22Bloggs%22%5D&arg2=%22Bloggs%22 HTTP/1.1
        Host: localhost:8085
        Connection: Keep-Alive
        User-Agent: Apache-HttpClient/4.0 (java 1.5)
     */
    /* FYI Converted URL =
        /HelloWorldComponent/HelloWorldService/sayHello6?arg0=[{"b":true,"s":"Fred","y":null,"x":5}]&arg1=["Fred":"Bloggs"]&arg2="Bloggs" HTTP/1.1
     */
    /* HTTP Response for testMixedArray
        HTTP/1.1 200 OK
        Content-Length: 35
        ["Hello Fred Fred Bloggs Bloggs"]
     */
    @Test
    @Ignore
    public void testMixedArray() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        BeanA bean = new BeanA();
        bean.setB(true);
        bean.setS("Fred");
        bean.setX(2);
        bean.setX(5);

        BeanA[] beans = {bean};
        String[] names = {"Fred", "Bloggs"};

        String[] response = client.sayHello6(beans, names, "Bloggs");

        Assert.assertEquals("Hello Fred Fred Bloggs Bloggs", response[0]);
    }

    @Test
    @Ignore
    public void testVoid() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        client.sayHello7();
    }

    @Test
    @Ignore
    public void waitForInput(){
        System.out.println("Press a key");
        try {
            System.in.read();
        } catch (Exception ex){
            // do nothing
        }
    }

    @BeforeClass
    public static void init() throws Exception {
        SCANodeFactory factory = SCANodeFactory.newInstance();
        node = factory.createSCANodeFromClassLoader("helloworld.composite", BindingTestCase.class.getClassLoader());
        node.start();
    }

    @AfterClass
    public static void destroy() throws Exception {
        if (node != null) {
            node.stop();
        }
    }

}
