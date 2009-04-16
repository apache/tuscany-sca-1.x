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


public class JEEClassLoader extends URLClassLoader {
    
    private Contribution contribution;
    private List<WebModuleInfo> webModules = new ArrayList<WebModuleInfo>();
    private List<EjbModuleInfo> ejbModules = new ArrayList<EjbModuleInfo>();
    
    /**
     * Constructor for contribution ClassLoader 
     * 
     * @param contribution
     * @param parent
     * @throws MalformedURLException
     */
    public JEEClassLoader(Contribution contribution, final ClassLoader parent) {
        super(new URL[0], parent);
        
        this.contribution = contribution;
        
        // get the classloaders for any JEE archives references by the contribution
        // TODO extend to case where JEE archive is outside the contribution
        for (Artifact artifact : contribution.getArtifacts()){
            if (artifact.getModel() instanceof WebModuleInfo){
                WebModuleInfo webModuleInfo = (WebModuleInfo)artifact.getModel();
                webModules.add(webModuleInfo);
            } else if (artifact.getModel() instanceof EjbModuleInfo){
                EjbModuleInfo ejbModuleInfo = (EjbModuleInfo)artifact.getModel();
                ejbModules.add(ejbModuleInfo);
            } else if (artifact.getModel() instanceof JavaEEApplicationInfo){
                JavaEEApplicationInfo jeeAppInfo = (JavaEEApplicationInfo)artifact.getModel();
                webModules.addAll(jeeAppInfo.getWebModuleInfos().values());
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
        
        // ask each of the JEE archive classloaders in turn
        // TODO This is a bad thing as two archives may have the same class
        //       but don't know how to avoid this
        for (WebModuleInfo webModuleInfo : webModules){
            try {
                clazz = webModuleInfo.getModuleClassloader().loadClass(className);
            } catch (ClassNotFoundException e) {
                cne = e;
            }
            
            if (clazz != null){
                break;
            }
        }
        
        // We would expect the webapp classloaders to have an ejb classloader as parent
        // but we have to go through the ebj module classloaders here just in case
        // there are no webapps
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


   
    /*
     * (non-Javadoc)
     * 
     * @see java.net.URLClassLoader#findResource(java.lang.String)
     */
    @Override
    public URL findResource(String name) {
        
        URL url = null;
/*        
        URL url = findResourceFromContribution(name);
        
        if (url == null) {
            for (Import import_ : this.contribution.getImports()) {
                if (resourceNameMatchesImport(name, import_)) {
                    // Delegate the resolution to the imported contribution
                    for (Contribution exportingContribution : ((JavaImportModelResolver)import_.getModelResolver()).getExportContributions()) {
                                
                        ClassLoader exportClassLoader = getExportClassLoader(exportingContribution);
                        if (exportClassLoader instanceof JEEClassLoader) {

                            for (Export export : exportingContribution.getExports()) {
                                if (import_.match(export)) {
                                    url = ((JEEClassLoader)exportClassLoader).findResourceFromContribution(name);
                                    if (url != null) break;
                                }
                            }
                            if (url != null)  break;
                        }
                    }
                    if (url != null) break;
                }
            }

        }
*/        
        return url;
    }


    /* (non-Javadoc)
     * @see java.net.URLClassLoader#findResources(java.lang.String)
     */
    @Override
    public Enumeration<URL> findResources(String name) throws IOException {       
        return Collections.enumeration(findResourceSet(name));
    }
    
    /* (non-Javadoc)
     * @see java.lang.ClassLoader#getResource(java.lang.String)
     * 
     * Find a resource. 
     * Search path for resource:
     *     Parent ClassLoader
     *     This contribution
     *     Imported contributions
     */
    @Override
    public URL getResource(String resName) {
 
        URL resource  = null;
        
        if (this.getParent() != null) {
            resource  = this.getParent().getResource(resName);
        }        
        if (resource == null)
            resource  = findResource(resName);
        
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
       
        HashSet<URL> resourceSet = findResourceSet(resName);
        addEnumerationToCollection(resourceSet, super.getResources(resName));
        
        return Collections.enumeration(resourceSet);
    }
    

    /*
     * Find set of resources
     */
    private HashSet<URL> findResourceSet(String name) throws IOException {
        
        HashSet<URL> resources = new HashSet<URL>();

/*
        addEnumerationToCollection(resources, super.findResources(name));
        
        for (Import import_ : this.contribution.getImports()) {
            if (!(import_ instanceof JavaImport)) {
                continue;
            }
            if (resourceNameMatchesImport(name, import_)) {
                // Delegate the resolution to the imported contribution
                for (Contribution exportingContribution : ((JavaImportModelResolver)import_.getModelResolver()).getExportContributions()) {
                                
                    ClassLoader exportClassLoader = getExportClassLoader(exportingContribution);
                    if (exportClassLoader instanceof JEEClassLoader) {

                        for (Export export : exportingContribution.getExports()) {
                            if (import_.match(export)) {
                                addEnumerationToCollection(resources,
                                        ((JEEClassLoader)exportClassLoader).findResources(name));
                            }
                        }
                    }
                }
            }
         }
*/         

        return resources;
    }


    /*
     * Find class from contribution. If class has already been loaded, return loaded class.
     */
    private Class<?> findClassFromContribution(String className) throws ClassNotFoundException {
        
        Class<?> clazz = findLoadedClass(className);
        if (clazz == null)
            clazz =  super.findClass(className);
        return clazz;
       
    }
    
    /*
     * Find resource from contribution.
     */
    private URL findResourceFromContribution(String name) {
        
        return super.findResource(name);
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
