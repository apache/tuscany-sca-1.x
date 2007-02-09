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
package echo;

import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

import org.apache.tuscany.test.SCATestCase;

/**
 * @version $Rev$ $Date$
 */
public class BootstrapTestCase extends SCATestCase {

    private Client client;
    private Client clientService;

    public void testDemoBoot() {
        client.call("foo");
        //clientService.call("foo");
    }

    protected void setUp() throws Exception {
        setApplicationSCDL(Client.class, "META-INF/sca/default.scdl");
        addExtension("echo.binding", getClass().getClassLoader().getResource("META-INF/sca/echo.system.scdl"));
        super.setUp();
        CompositeContext context = CurrentCompositeContext.getContext();
        client = context.locateService(Client.class, "Client");
        // JFM temporarily commented out until we get a better solution as only local bindings are available.  
        //clientService = context.locateService(Client.class, "ClientService");
    }
}
