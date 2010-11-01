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

import java.io.ByteArrayInputStream;

import junit.framework.Assert;
import helloworld.HelloWorldService;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ConcurrencyTestCase {
    private static SCANode node;
    private static HelloWorldService service;

    @BeforeClass
    public static void init() throws Exception {
        try {
            SCANodeFactory factory = SCANodeFactory.newInstance();
            node = factory.createSCANodeFromClassLoader("helloworld.composite", 
                                                        ConcurrencyTestCase.class.getClassLoader());
            node.start();
            
            service = ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldClientComponent");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void destroy() throws Exception {
        node.stop();
    }
    
    @Test
    public void testConcurrency() {
        String greetings = service.getGreetings("Simon");
        System.out.println(">>>" + greetings);
    } 
}
