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

package org.apache.tuscany.sca.contribution.jee;

import java.io.File;

import org.apache.openejb.OpenEJBException;
import org.apache.openejb.config.AnnotationDeployer;
import org.apache.openejb.config.AppModule;
import org.apache.openejb.config.DeploymentLoader;
import org.apache.openejb.config.EjbModule;
import org.apache.openejb.config.ReadDescriptors;
import org.apache.openejb.config.WebModule;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;

/**
 * @version $Rev$ $Date$
 */
public class JavaEEModuleHelper {

    public AppModule getMetadataCompleteModules(String jarFilePath) throws ContributionReadException {
        DeploymentLoader loader = new DeploymentLoader();
        AppModule appModule = null;
        try {
            appModule = loader.load(new File(jarFilePath));
        } catch (OpenEJBException e) {
            throw new ContributionReadException(e);
        }

        // Set the Thread context class loader as the module's class loader and all the Web and EJB modules
        // inside. Otherwise, SCA annotations could not be processed
        // TODO: Eliminate the use of reflection for setting the class loader
        java.lang.reflect.Field field = null;
        try {
            field = appModule.getClass().getDeclaredField("classLoader");
        } catch (SecurityException e) {
            throw new ContributionReadException(e);
        } catch (NoSuchFieldException e) {
            throw new ContributionReadException(e);
        }
        field.setAccessible(true);
        try{
            field.set(appModule, Thread.currentThread().getContextClassLoader());
        } catch (IllegalArgumentException e) {
            throw new ContributionReadException(e);
        } catch (IllegalAccessException e) {
            throw new ContributionReadException(e);
        }
        
        for(EjbModule ejbModule:appModule.getEjbModules()) {
            java.lang.reflect.Field field1 = null;
            try {
                field1 = ejbModule.getClass().getDeclaredField("classLoader");
            } catch (SecurityException e) {
                throw new ContributionReadException(e);
            } catch (NoSuchFieldException e) {
                throw new ContributionReadException(e);
            }
            field1.setAccessible(true);
            try {
                field1.set(ejbModule, Thread.currentThread().getContextClassLoader());
            } catch (IllegalArgumentException e) {
                throw new ContributionReadException(e);
            } catch (IllegalAccessException e) {
                throw new ContributionReadException(e);
            }
        }

        for(WebModule webModule:appModule.getWebModules()) {
            java.lang.reflect.Field field1 = null;
            try {
                field1 = webModule.getClass().getDeclaredField("classLoader");
            } catch (SecurityException e) {
                throw new ContributionReadException(e);
            } catch (NoSuchFieldException e) {
                throw new ContributionReadException(e);
            }
            field1.setAccessible(true);
            try {
                field1.set(webModule, Thread.currentThread().getContextClassLoader());
            } catch (IllegalArgumentException e) {
                throw new ContributionReadException(e);
            } catch (IllegalAccessException e) {
                throw new ContributionReadException(e);
            }
        }

        // Process deployment descriptor files
        ReadDescriptors readDescriptors = new ReadDescriptors();
        try {
            readDescriptors.deploy(appModule);
        } catch (OpenEJBException e) {
            throw new ContributionReadException(e);
        }

        // Process annotations
        AnnotationDeployer annDeployer = new AnnotationDeployer();
        try {
            annDeployer.deploy(appModule);
        } catch (OpenEJBException e) {
            throw new ContributionReadException(e);
        }

        return appModule;
    }
}
