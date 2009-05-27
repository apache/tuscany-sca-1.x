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

package org.apache.tuscany.sca.test.contribution.jee;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.jee.ExternalEarInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEApplicationInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEIntrospector;
import org.apache.tuscany.sca.contribution.jee.impl.JavaEEApplicationInfoImpl;
import org.apache.tuscany.sca.contribution.resolver.ExtensibleModelResolver;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.resolver.ModelResolverExtensionPoint;
import org.apache.tuscany.sca.contribution.service.ContributionService;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.host.embedded.impl.EmbeddedSCADomain;
import org.apache.tuscany.sca.implementation.ejb.EJBImplementation;
import org.apache.tuscany.sca.implementation.jee.JEEImplementation;
import org.apache.tuscany.sca.implementation.web.WebImplementation;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class SCAJarEarNonenhancedTestCase {
 
    private static final String CONTRIBUTION_001_ID = "contribution001/";
    private static final String CONTRIBUTION_002_ID = "contribution002/";

    private ClassLoader cl;
    private EmbeddedSCADomain domain;
    private ContributionService contributionService;

    /**
     * setUp() is a method in JUnit Frame Work which is executed before all others methods in the class extending
     * unit.framework.TestCase. So this method is used to create a test Embedded SCA Domain, to start the SCA Domain and
     * to get a reference to the contribution service
     */

    @Before
    public void setUp() throws Exception {
        //Create a test embedded SCA domain
        cl = getClass().getClassLoader();
        domain = new EmbeddedSCADomain(cl, "http://localhost");

        //Start the domain
        domain.start();

        //get a reference to the contribution service
        contributionService = domain.getContributionService();
    }

    /**
     * SCAJAR      - It's an SCA contribution in a JAR with no nested archives
     *               but which references a...
     * EAR         - It's an JEE EAR file
     *    nonenhanced  - It has no SCA enhancements. Just a normal EAR
     *    
     */
    @Test
    public void testSCAJarEarNonenhanced() throws Exception{
        /* some thoughts about how to preprocess the ear
         * as an alternative to relying on the model resolver
         * to do contribution processing. 
         
        // Get some things from the extension registry that help us create the
        // link between the contribution and the ear that it references
        ExtensionPointRegistry registry = domain.getExtensionPointRegistry();
        ModelResolverExtensionPoint modelResolvers = registry.getExtensionPoint(ModelResolverExtensionPoint.class);
        Class<? extends ModelResolver> externalEarModelResolver = modelResolvers.getResolver(ExternalEarInfo.class);
        
        // locate the ear
        URL earLocation = new File("../contribution-jee-samples/ear-nonenhanced/target/itest-contribution-jee-samples-13-ear-nonenhanced.ear").toURL();
        contributionService.contribute(CONTRIBUTION_001_ID, earLocation, false);
        
        // find the app info
        for (Artifact artifact : contributionService.getContribution(CONTRIBUTION_001_ID).getArtifacts()){
            if (artifact.getModel() instanceof JavaEEApplicationInfo){
                // need to make this model available to the external ear model
                // resolver. But how?
            }
        }
        */
          
        URL contributionLocation = new File("../contribution-jee-samples/scajar-ear-nonenhanced/target/itest-contribution-jee-samples-35-scajar-ear-nonenhanced.jar").toURL();
        Contribution contribution =  contributionService.contribute(CONTRIBUTION_002_ID, contributionLocation, false);
        
        Assert.assertNotNull(contribution);
        
        Composite composite = null;
        for (Artifact artifact : contribution.getArtifacts()){
            if (artifact.getModel() instanceof Composite){
                composite = (Composite) artifact.getModel();
            }
        }
        
        Assert.assertNotNull(composite);
        
        Assert.assertEquals(2, composite.getComponents().size());
        Assert.assertEquals(2, composite.getComponents().get(1).getImplementation().getServices().size());
        Assert.assertEquals("HelloworldService7Bean_HelloworldService7", composite.getComponents().get(1).getImplementation().getServices().get(0).getName());
        
        domain.buildComposite(composite);
        
        // assert that the build process has worked
        Assert.assertNotNull(composite);        
    }

}
