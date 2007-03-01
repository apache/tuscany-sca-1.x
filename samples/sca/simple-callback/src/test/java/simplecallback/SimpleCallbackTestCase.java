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
package simplecallback;

import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

import org.apache.tuscany.test.SCATestCase;

/**
 * A testcase that demonstrates resolving the client service and initiating the callback sequence
 */
public class SimpleCallbackTestCase extends SCATestCase {

    private MyClient myClient;

    protected void setUp() throws Exception {
        setApplicationSCDL(MyClient.class, "META-INF/sca/default.scdl");
        super.setUp();

        CompositeContext context = CurrentCompositeContext.getContext();
        myClient = context.locateService(MyClient.class, "MyClientComponent");
    }

    public void test() throws Exception {
        System.out.println("Main thread " + Thread.currentThread());
        myClient.aClientMethod();
        System.out.println("Sleeping ...");
        Thread.sleep(1000);
    }
}
