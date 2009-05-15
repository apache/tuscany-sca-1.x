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
package helloworld;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.IOException;

import org.apache.activemq.broker.BrokerService;
import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests that the helloworld server is available
 */
public class JmsTransportTestCase{

    private SCADomain scaDomain;
    private BrokerService jmsBroker;

    @Before
	public void startServer() throws Exception {
            startBroker();
            scaDomain = SCADomain.newInstance("helloworld.composite");
	}

    protected void startBroker() throws Exception {
        jmsBroker = new BrokerService(); 
        jmsBroker.setPersistent(false);
        jmsBroker.setUseJmx(false);
        jmsBroker.addConnector("tcp://localhost:51293");
        jmsBroker.start();
    }
    
    //@Ignore
    @Test
    public void testComponent1() throws IOException {
        HelloWorldService helloWorldService = scaDomain.getService(HelloWorldService.class, "HelloWorldServiceComponent1/HelloWorldService");
        assertNotNull(helloWorldService);
        
        assertEquals("Hello Smith", helloWorldService.getGreetings("Smith"));
    }
    
    //@Ignore
    @Test
    public void testComponent2() throws IOException {
        HelloWorldService helloWorldService = scaDomain.getService(HelloWorldService.class, "HelloWorldServiceComponent2/HelloWorldService");
        assertNotNull(helloWorldService);
        
        assertEquals("Hello Smith", helloWorldService.getGreetings("Smith"));
    }  
    
    @Ignore
    @Test
    public void testComponent3() throws IOException {
        HelloWorldService helloWorldService = scaDomain.getService(HelloWorldService.class, "HelloWorldServiceComponent3/HelloWorldService");
        assertNotNull(helloWorldService);
        
        assertEquals("Hello Smith", helloWorldService.getGreetings("Smith"));
    }    
    
    @Ignore
    @Test
    public void testComponent4() throws IOException {
        HelloWorldService helloWorldService = scaDomain.getService(HelloWorldService.class, "HelloWorldServiceComponent4/HelloWorldService");
        assertNotNull(helloWorldService);
        
        assertEquals("Hello Smith", helloWorldService.getGreetings("Smith"));
    }    
    
    @Ignore
    @Test
    public void testComponent5() throws IOException {
        HelloWorldService helloWorldService = scaDomain.getService(HelloWorldService.class, "HelloWorldServiceComponent5/HelloWorldService");
        assertNotNull(helloWorldService);
        
        assertEquals("Hello Smith", helloWorldService.getGreetings("Smith"));
    }
       
    @Ignore
    @Test
    public void testWaitForInput() {
        System.out.println("Press a key to end");
        try {
            System.in.read();
        } catch (Exception ex) {
        }
        System.out.println("Shutting down");
    }      

	@After
	public void stopServer() throws Exception {
            if (scaDomain != null) {
                scaDomain.close();
            }
            if (jmsBroker != null) {
                jmsBroker.stop();
            }
	}

}
