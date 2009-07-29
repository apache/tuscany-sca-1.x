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

package org.apache.tuscany.sca.artifact.xyz;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.Import;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.namespace.NamespaceImport;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.imprt.xyz.ImportXYZ;

/**
 * A Model Resolver for Composite models.
 *
 * @version $Rev$ $Date$
 */
public class XYZModelResolver implements ModelResolver {

    private Map<String, XYZ> map = new HashMap<String, XYZ>();
    private Contribution contribution;
    
    public XYZModelResolver(Contribution contribution, ModelFactoryExtensionPoint modelFactories) {
        this.contribution = contribution;
    }

    public void addModel(Object resolved) {
        XYZ xyz = (XYZ)resolved;
        map.put(xyz.getAnAttribute(), xyz);
    }
    
    public Object removeModel(Object resolved) {
        return map.remove(((XYZ)resolved).getAnAttribute());
    }
    
    public <T> T resolveModel(Class<T> modelClass, T unresolved) {
        
        XYZ xyz = (XYZ)unresolved;
        
        XYZ resolved = map.get(xyz.getAnAttribute());
        if (resolved != null) {
            return modelClass.cast(resolved);
        }
       
        // No definition found, delegate the resolution to the imports
        for (Import import_ : this.contribution.getImports()) {
            if (import_ instanceof ImportXYZ) {
                ImportXYZ importXYZ = (ImportXYZ)import_;
                
                if (xyz.getAnAttribute().equals(importXYZ.getAnAttribute())) {
                    
                    // Delegate the resolution to the import resolver
                    resolved = importXYZ.getModelResolver().resolveModel(XYZ.class, (XYZ)unresolved);
                    if (!resolved.isUnresolved()) {
                        return modelClass.cast(resolved);
                    }
                }
            }
        }

        return (T)unresolved;
    }
    
}
