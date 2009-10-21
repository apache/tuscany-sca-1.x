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
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.jee.ExternalEarInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEApplicationInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEIntrospector;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionService;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.apache.tuscany.sca.host.embedded.impl.EmbeddedSCADomain;

public class TestExternalEarModelResolver implements ModelResolver {

    private Map<URI, ExternalEarInfo> map = new HashMap<URI, ExternalEarInfo>();
    private JavaEEIntrospector jeeIntrospector;
    private ContributionService contributionService;
    
    
    public TestExternalEarModelResolver(Contribution contribution, ExtensionPointRegistry extensionPoints) {
       jeeIntrospector = extensionPoints.getExtensionPoint(JavaEEIntrospector.class);
       UtilityExtensionPoint utilities = extensionPoints.getExtensionPoint(UtilityExtensionPoint.class);
       contributionService = utilities.getUtility(ContributionService.class);

    }
        
    public void addModel(Object resolved) {
        ExternalEarInfo jeeApp = (ExternalEarInfo)resolved;
        map.put(jeeApp.getAppInfo().getUri(), jeeApp);
    }

    public Object removeModel(Object resolved) {
        return map.remove(((ExternalEarInfo)resolved).getAppInfo().getUri());
    }

    public <T> T resolveModel(final Class<T> modelClass, T unresolved) {
        URI uri = ((ExternalEarInfo)unresolved).getAppInfo().getUri();
        if (uri != null) {
            ExternalEarInfo resolved = (ExternalEarInfo) map.get(uri);
            if (resolved != null) {
                return modelClass.cast(resolved);
            } else {
                // if you don't care about application composites you can 
                // simply read the EAR directly
                /*                
                try {
                    File f = new File(uri.toString());
                    final JavaEEApplicationInfo o = jeeIntrospector.introspectJeeArchive(f.toURI().toURL());
                    return (T)new ExternalEarInfo() {
                        public JavaEEApplicationInfo getAppInfo() {
                            return (JavaEEApplicationInfo)o;
                        }
                    };
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                */
                
                // if you do care about the application composite you
                // need to process the ear as a contribution 
                try{
                    // find the location of the ear using a very specific algorithm 
                    URL earLocation = null;
                    if ( uri.toString().equals("itest-contribution-jee-samples-13-ear-nonenhanced.ear")){                       
                        earLocation = new File("../contribution-jee-samples/ear-nonenhanced/target/itest-contribution-jee-samples-13-ear-nonenhanced.ear").toURL();
                    } else if ( uri.toString().equals("./itest-contribution-jee-samples-29-ear-appcomp-contrib-implicit.ear")){                       
                        earLocation = new File("../contribution-jee-samples/ear-appcomp-contrib-implicit/target/itest-contribution-jee-samples-29-ear-appcomp-contrib-implicit.ear").toURL();
                    } else if ( uri.toString().equals("itest-contribution-jee-samples-30-ear-appcomp-contrib-implicit-war-appcomp.ear")){                       
                        earLocation = new File("../contribution-jee-samples/ear-appcomp-contrib-implicit-war-appcomp/target/itest-contribution-jee-samples-30-ear-appcomp-contrib-implicit-war-appcomp.ear").toURL();                        
                    } else {
                        return unresolved;
                    }
                    
                    // if you do care about application composites we have 
                    // to process the EAR as a contribution
                    Contribution contribution = contributionService.contribute(uri.toString(), earLocation, false);
                    
                    JavaEEApplicationInfo appInfo = null;
                    Composite appComposite = null;
                    
                    // get the ear info 
                    for (Artifact artifact : contribution.getArtifacts()){
                        if (artifact.getModel() instanceof JavaEEApplicationInfo){
                            appInfo = (JavaEEApplicationInfo)artifact.getModel();
                        }
                        
                        if (artifact.getURI().equals("META-INF/application.composite")){
                            appComposite = (Composite)artifact.getModel();
                        }
                    }
                                       
                    
                    final JavaEEApplicationInfo returnAppInfo = appInfo;
                    final Composite returnAppComposite = appComposite;
                    
                    return (T)new ExternalEarInfo() {
                        public JavaEEApplicationInfo getAppInfo() {
                            return returnAppInfo;
                        }
                        public Composite getAppComposite() {
                            return returnAppComposite;
                        }                        
                    };
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return unresolved;
    }

}
