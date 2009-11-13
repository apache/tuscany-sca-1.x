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

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.jee.ExternalEarInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEApplicationInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEIntrospector;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;

public class SomeCustomModelResolver implements ModelResolver {

    private Map<URI, ExternalEarInfo> map = new HashMap<URI, ExternalEarInfo>();
    private JavaEEIntrospector jeeIntrospector;
    
    
    public SomeCustomModelResolver(Contribution contribution, ExtensionPointRegistry extensionPoints) {
       jeeIntrospector = extensionPoints.getExtensionPoint(JavaEEIntrospector.class);
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
            }
        }

        return unresolved;
    }

}
