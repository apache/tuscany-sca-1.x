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
package org.apache.tuscany.sca.implementation.ejb;

import java.util.Collection;
import java.util.Map;

import org.apache.tuscany.sca.assembly.Implementation;
import org.apache.tuscany.sca.contribution.jee.InjectionTarget;
import org.apache.tuscany.sca.implementation.java.impl.JavaElementImpl;
import org.apache.tuscany.sca.implementation.java.impl.JavaResourceImpl;



/**
 * The model representing an EJB implementation in an SCA assembly model.
 *
 * @version $Rev$ $Date$
 */
public interface EJBImplementation extends Implementation {
    
    /**
     * Returns the EJB link URI.
     * @return the EJB link URI
     */
    String getEJBLink();
    
    /**
     * Sets the EJB link URI.
     * @param ejbLink the EJB link URI
     */
    void setEJBLink(String ejbLink);

    /**
     * Returns the injection points for SCA references
     * 
     * @return Map with injection points for SCA references
     */
    Map<String, JavaElementImpl> getReferenceInjectionPoints();
    
    /**
     * Returns the injection points for SCA properties
     * 
     * @return Map with injection points for SCA properties
     */
    Map<String, JavaElementImpl> getPropertyInjectionPoints();

    /**
     * Returns the injection points for SCA resources like component context, component name, etc.
     * 
     * @return Map with injection points for SCA resources
     */
    Map<String, JavaResourceImpl> getResourceInjectionPoints();
    
    /**
     * Returns the injection points for SCA callbacks
     * 
     * @return Map with injection points for SCA callbacks
     */
    Map<String, Collection<JavaElementImpl>> getCallbackInjectionPoints();
    
    Map<InjectionTarget, Class<?>> getOptExtensionReferenceInjectionPoints();

    Map<String, String> getOptExtensionPropertyInjectionPoints();
}
