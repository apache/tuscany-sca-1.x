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
package itest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class InitTestCase {

    private static SCADomain scaDomain;

    @Before
    public void init() {
        scaDomain = SCADomain.newInstance("http://localhost", "/", "test.composite");
    }

    @Test
    public void testOk() throws Exception {
        Service client1 = scaDomain.getService(Service.class, "OkService");
        client1.doit();
        assertTrue(OkImpl.initRun);
        assertTrue(OkImpl.destroyRun); // its stateless so destory is called after every service invocations
    }

    @Test
    public void testConstructorException() throws Exception {
        Service client1 = scaDomain.getService(Service.class, "ConstructorException");
        try {
            client1.doit();
            fail();
        } catch (RuntimeException e) {
            // expected
        }
        assertFalse(ConstructorException.initRun);
        assertFalse(ConstructorException.doitRun);
        assertFalse(ConstructorException.destroyRun);
    }

    @Test
    public void testInitCompositeScopeException() throws Exception {
        Service client1 = scaDomain.getService(Service.class, "InitCompositeScopeException");
        try {
            client1.doit();
            fail();
        } catch (RuntimeException e) {
            // expected
        }
        assertTrue(InitCompositeScopeException.initRun);
        assertFalse(InitCompositeScopeException.doitRun);
        assertTrue(InitCompositeScopeException.destroyRun);

        // reset and try again to verify init init still gets run again
        InitCompositeScopeException.initRun = false;    
        InitCompositeScopeException.doitRun = false;    
        InitCompositeScopeException.destroyRun = false;    

        client1.doit();

        assertTrue(InitCompositeScopeException.initRun);
        assertTrue(InitCompositeScopeException.doitRun);
        scaDomain.close();
        scaDomain = null;
        assertTrue(InitCompositeScopeException.destroyRun);
    }

    @Test
    public void testInitStatelessScopeException() throws Exception {
        Service client1 = scaDomain.getService(Service.class, "InitStatelessScopeException");
        try {
            client1.doit();
            fail();
        } catch (RuntimeException e) {
            // expected
        }
        assertTrue(InitStatelessScopeException.initRun);
        assertFalse(InitStatelessScopeException.doitRun);
        assertTrue(InitStatelessScopeException.destroyRun);

        // reset and try again to verify init init still gets run again
        InitStatelessScopeException.initRun = false;    
        InitStatelessScopeException.doitRun = false;    
        InitStatelessScopeException.destroyRun = false;    

        client1.doit();

        assertTrue(InitStatelessScopeException.initRun);
        assertTrue(InitStatelessScopeException.doitRun);
        scaDomain.close();
        scaDomain = null;
        assertTrue(InitStatelessScopeException.destroyRun);
    }

    @Test
    public void testInitRequestScopeException() throws Exception {
        Service client1 = scaDomain.getService(Service.class, "InitRequestScopeException");
        try {
            client1.doit();
            fail();
        } catch (RuntimeException e) {
            // expected
        }
        assertTrue(InitRequestScopeException.initRun);
        assertFalse(InitRequestScopeException.doitRun);
        assertTrue(InitRequestScopeException.destroyRun);

        // reset and try again to verify init init still gets run again
        InitRequestScopeException.initRun = false;    
        InitRequestScopeException.doitRun = false;    
        InitRequestScopeException.destroyRun = false;    

        client1.doit();

        assertTrue(InitRequestScopeException.initRun);
        assertTrue(InitRequestScopeException.doitRun);
        scaDomain.close();
        scaDomain = null;
        assertTrue(InitRequestScopeException.destroyRun);
    }
    
    @Test
    public void testDestroyCompositeScopeException() throws Exception {
        Service client1 = scaDomain.getService(Service.class, "DestroyCompositeScopeException");
        try {
            client1.doit();
        } catch (RuntimeException e) {
        	fail();
        }
        assertTrue(DestroyCompositeScopeException.initRun);
        assertTrue(DestroyCompositeScopeException.doitRun);
        assertFalse(DestroyCompositeScopeException.destroyRun);
        
        Service client2 = scaDomain.getService(Service.class, "DestroyCompositeScopeException2");
        try {
            client2.doit();
        } catch (RuntimeException e) {
        	fail();
        }

        // close the domain to case @Destroy to run
        try {
        	scaDomain.close();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	fail();
        }
        scaDomain = null;
        
        // check that it has run twice
        // The first run having caused an exception
        assertTrue(DestroyCompositeScopeException.destroyRun);
        Assert.assertEquals(2, DestroyCompositeScopeException.count);
    }    

    @After
    public void end() {
        if (scaDomain != null) {
            scaDomain.close();
        }
    }
}
