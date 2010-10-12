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
package test;

import helloworld.BeanA;
import helloworld.HelloWorldService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.apache.tuscany.sca.node.SCANode;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BindingTestCase {

    private static SCANode node;

    @Test
    public void testService() throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8085/HelloWorldComponent/HelloWorldService/sayHello?name=petra&callback=foo");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String response = br.readLine();
        Assert.assertEquals("foo(\"Hello petra\");", response);

    }

    @Test
    public void testTwoArgs() throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8085/HelloWorldComponent/HelloWorldService/sayHello2?first=petra&last=arnold&callback=foo");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String response = br.readLine();
        Assert.assertEquals("foo(\"Hello petra arnold\");", response);

    } 

    @Test
    public void testReference() throws MalformedURLException, IOException {
        
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        Assert.assertEquals("Hello beate", client.sayHello("beate"));
        Assert.assertEquals("Hello beate arnold", client.sayHello2("beate", "arnold"));
    }
    
    @Test
    //@Ignore("TUSCANY-3635")
    public void testComplexParams() throws MalformedURLException, IOException {      
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        BeanA bean = new BeanA();
        bean.setB(true);
        bean.setS("Fred");
        bean.setX(2);
        bean.setX(5);

        Assert.assertEquals("XYZ", client.sayHello3(bean).getS());
    }  
    
    @Test
    public void testStringArray() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        String[] names = {"Fred", "Bloggs"};
        String[] response = client.sayHello4(names);

        Assert.assertEquals("Hello Fred Bloggs", response[0]);
    }   
    
    @Test
    public void testBeanArray() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        BeanA bean = new BeanA();
        bean.setB(true);
        bean.setS("Fred");
        bean.setX(2);
        bean.setX(5);
        
        BeanA[] beans = {bean};
        BeanA[] response = client.sayHello5(beans);

        Assert.assertEquals("Hello Fred", response[0].getS());
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
    public void testVoid() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

        try {
        	client.sayHello7();
	    } catch (Exception ex){
	        Assert.fail();
	    }
    }   
    
    @Test
    public void testVoidOut() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

	    try {
	        client.sayHello8("Fred");
	    } catch (Exception ex){
	        Assert.fail();
	    }
    }  
    
    @Test
    public void testIntOut() throws MalformedURLException, IOException {
        HelloWorldService client = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClient");

	    try {
	        Assert.assertEquals(28, client.sayHello9(28));
	    } catch (Exception ex){
	        Assert.fail();
	    }
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
        //JettyServer.portDefault = 8085;
        //node = SCANodeFactory.newInstance().createSCANode("helloworld.composite", new SCAContribution("contrib1", "./target/test-classes"));
        //node.start();
        
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
