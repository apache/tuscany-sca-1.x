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

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.tuscany.api.SCARuntime;
import org.apache.tuscany.sca.itest.jaxbdatabinding.GreeterServiceClient;
import org.apache.tuscany.sca.itest.jaxbdatabinding.generated.ObjectFactory;
import org.apache.tuscany.sca.itest.jaxbdatabinding.generated.PersonType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * 
 */
public class JaxbDatabindingTestCase {
    private GreeterServiceClient greeterClient;
    
    @BeforeClass
    public static void init() {
        SCARuntime.start("greeterws.composite");
    }

    private void setUpClient(String binding) throws Exception {
        CompositeContext ctx = CurrentCompositeContext.getContext();
        greeterClient = ctx.locateService(GreeterServiceClient.class, binding + "JAXBGreeterServiceClient");
    }

    /**
     * Invokes the JAXB Greet service using web service bindings with JAXB payload
     * 
     * @throws Exception
     */
    @Test
    public void testWSGreet() throws Exception {
        setUpClient("WS");
        greet();
    }

    /**
     * Invokes the JAXB Greet service using default with JAXB payload
     * 
     * @throws Exception
     */
    @Test
    public void testDefaultGreet() throws Exception {
        setUpClient("Default");
        greet();
    }

    public void greet() {
        ObjectFactory factory = new ObjectFactory();
        PersonType person = factory.createPersonType();
        person.setFirstName("George");
        person.setLastName("Doors");
        // System.out.println("Client side: " + person.getFirstName() + " " + person.getLastName() + " " +
        // person.getGreeting());
        PersonType greetedPerson = greeterClient.greet(person);
        // System.out.println("Client side: " + greetedPerson.getFirstName() + " " + greetedPerson.getLastName() + " " +
        // greetedPerson.getGreeting());
        Assert.assertNotSame("greetedPerson.getGreeting() not set", "", greetedPerson.getGreeting());
    }

}
