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

package org.apache.tuscany.sca.implementation.xyz;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tuscany.sca.assembly.ConstrainingType;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.assembly.Service;


/**
 * Represents an implementation in an SCA assembly.
 *
 * @version $Rev$ $Date$
 */
class ImplementationXYZImpl implements ImplementationXYZ {
    
    private String anAttribute;
    private String uri;
    private Map<String, Method> methods;
    private List<Service> services = new ArrayList<Service>();
    private List<Reference> references = new ArrayList<Reference>();
    private List<Property> properties = new ArrayList<Property>();
    private boolean unresolved;

    ImplementationXYZImpl() {
    }
    
    /**
     * Returns the module name
     * @return
     */
    public String getAnAttribute() {
        return anAttribute;
    }

    /**
     * Sets the module name
     * @param pojoName
     */
    public void setAnAttribute(String anAttribute) {
        this.anAttribute = anAttribute;
        uri = anAttribute;
    }
    
    
    /**
     * Returns the methods.
     * @return
     */
    public Map<String, Method> getMethods() {
        return methods;
    }

    public ConstrainingType getConstrainingType() {
        // The sample implementation does not support constrainingTypes
        return null;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<Service> getServices() {
        return services;
    }
    
    public List<Reference> getReferences() {
        return references;
    }

    public String getURI() {
        return uri;
    }

    public void setConstrainingType(ConstrainingType constrainingType) {
        // The sample implementation does not support constrainingTypes
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    public boolean isUnresolved() {
        return unresolved;
    }

    public void setUnresolved(boolean unresolved) {
        this.unresolved = unresolved;
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImplementationXYZImpl) {
            return ((ImplementationXYZImpl)obj).getURI().equals(uri);
        } else {
            return false;
        }
    }

}
