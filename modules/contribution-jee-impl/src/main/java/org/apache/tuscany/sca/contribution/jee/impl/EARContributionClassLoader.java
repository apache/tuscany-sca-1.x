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

package org.apache.tuscany.sca.contribution.jee.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.Export;
import org.apache.tuscany.sca.contribution.Import;
import org.apache.tuscany.sca.contribution.java.JavaImport;
import org.apache.tuscany.sca.contribution.jee.EjbModuleInfo;
import org.apache.tuscany.sca.contribution.jee.JavaEEApplicationInfo;
import org.apache.tuscany.sca.contribution.jee.WebModuleInfo;


public class EARContributionClassLoader extends URLClassLoader {
    
    private Contribution contribution;
    private List<EjbModuleInfo> ejbModules = new ArrayList<EjbModuleInfo>();
    
    /**
     * Constructor for contribution ClassLoader 
     * 
     * @param contribution
     * @param parent
     * @throws MalformedURLException
     */
    public EARContributionClassLoader(Contribution contribution, final ClassLoader parent) {
        super(new URL[0], parent);
        
        this.contribution = contribution;
        
        // get the classloaders for any EAR archive that is a contribution
        for (Artifact artifact : contribution.getArtifacts()){
            if (artifact.getModel() instanceof JavaEEApplicationInfo){
                JavaEEApplicationInfo jeeAppInfo = (JavaEEApplicationInfo)artifact.getModel();
                // TODO - won't these all have the same classloader?
                ejbModules.addAll(jeeAppInfo.getEjbModuleInfos().values());
            }
        }
             
    }
    
    /* (non-Javadoc)
     * @see java.net.URLClassLoader#findClass(java.lang.String)
     * 
     * Search path for class:
     *     This contribution
     *     Imported contributions
     */
    @Override   
    protected Class<?> findClass(String className) throws ClassNotFoundException {
               
        Class<?> clazz = null;
        ClassNotFoundException cne = null;
                
        // TODO - Won't these classloaders all be the same?
        for (EjbModuleInfo ejbModuleInfo : ejbModules){
            try {
                clazz = ejbModuleInfo.getModuleClassloader().loadClass(className);
            } catch (ClassNotFoundException e) {
                cne = e;
            }
            
            if (clazz != null){
                break;
            }
        }        
        
        if (clazz == null){
            throw cne;
        }
             
        return clazz;
    }

    
    /* (non-Javadoc)
     * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
     * 
     * Search path for class:
     *     Parent ClassLoader
     *     This contribution
     *     Imported contributions
     *     
     */
    @Override
    protected synchronized Class<?> loadClass(String className, boolean resolveClass) 
        throws ClassNotFoundException {
       
        Class<?> clazz = null;
        try {
            
            if (this.getParent() != null)
                clazz = this.getParent().loadClass(className);
            
        } catch (ClassNotFoundException e) {
        }

        if (clazz == null)
            clazz = findClass(className);


        if (resolveClass)
            this.resolveClass(clazz);
        return clazz;
        
    }


   
    /* (non-Javadoc)
     * @see java.lang.ClassLoader#getResource(java.lang.String)
     * 
     * Find a resource. 
     * Search path for resource:
     *     Parent ClassLoader
     *     This contribution
     */
    @Override
    public URL getResource(String resName) {
 
        URL resource  = null;
        
        if (this.getParent() != null) {
            resource  = this.getParent().getResource(resName);
        }        
        if (resource == null) {
// TODO Which classloader?            
//           resource  = webModuleInfo.getModuleClassloader().getResource(resName);
        }
        
        return resource;
    }

    
    /* (non-Javadoc)
     * @see java.lang.ClassLoader#getResources(java.lang.String)
     * 
     * Return list of resources from this contribution, resources
     * imported through imported contributions and resources from parent 
     * ClassLoader.
     */
    @Override
    public Enumeration<URL> getResources(String resName) throws IOException {
       
        HashSet<URL> resourceSet = new HashSet<URL>();
  
// TODO - which classloader
//        addEnumerationToCollection(resourceSet, webModuleInfo.getModuleClassloader().getResources(resName));
        addEnumerationToCollection(resourceSet, super.getResources(resName));
        
        return Collections.enumeration(resourceSet);
    }
       
    /*
     * Add an enumeration to a Collection
     */
    private <T extends Object> void addEnumerationToCollection(Collection<T> collection, Enumeration<T> enumeration) {
        
        while (enumeration.hasMoreElements())
            collection.add(enumeration.nextElement());
    }
    
    
    @Override
    public String toString() {
        return "SCA JEE ClassLoader, parent ClassLoader: " + getParent();
    }
    
    
}
