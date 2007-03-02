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

package org.apache.tuscany.sca.itest;

import org.apache.tuscany.sca.itest.jaxbdatabinding.GreeterService;
import org.apache.tuscany.sca.itest.jaxbdatabinding.GreeterServiceClient;
import org.apache.tuscany.test.SCATestCase;
import org.osoa.sca.ComponentContext;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

import org.apache.tuscany.sca.itest.jaxbdatabinding.generated.ObjectFactory;
import org.apache.tuscany.sca.itest.jaxbdatabinding.generated.PersonType;

/**
 * 
 */
public class JaxbDatabindingTestCase extends SCATestCase {
    
    GreeterServiceClient greeterClient;
    private static boolean initalised = false;
    
    protected void setUp() throws Exception{
        if (!initalised) {
            setApplicationSCDL("greeterws.composite");
            super.setUp();
            initalised = true;
        }
    }
    
    protected void tearDown(){
        
    }
    
    private void setUpClient(String binding) throws Exception {
        CompositeContext ctx = CurrentCompositeContext.getContext();
        greeterClient = ctx.locateService(GreeterServiceClient.class, binding + "JAXBGreeterServiceClient");
    }

    /**
     * Invokes the JAXB Greet service using web service bindings with JAXB payload
     * @throws Exception
     */
    public void testWSGreet() throws Exception {
        setUpClient("WS");
        greet();
    }
    
    /**
     * Invokes the JAXB Greet service using default with JAXB payload
     * @throws Exception
     */
    public void testDefaultGreet() throws Exception {
        setUpClient("Default");
        greet();
    }
    
    public void greet() {
        ObjectFactory factory = new ObjectFactory();
        PersonType person = factory.createPersonType();
        person.setFirstName("George");
        person.setLastName("Doors");
        //System.out.println("Client side: " + person.getFirstName() + " " + person.getLastName() + " " + person.getGreeting());
        PersonType greetedPerson = greeterClient.greet(person);
        //System.out.println("Client side: " + greetedPerson.getFirstName() + " " + greetedPerson.getLastName() + " " + greetedPerson.getGreeting());
        assertNotSame("greetedPerson.getGreeting() not set","", greetedPerson.getGreeting());
    }
    
}
