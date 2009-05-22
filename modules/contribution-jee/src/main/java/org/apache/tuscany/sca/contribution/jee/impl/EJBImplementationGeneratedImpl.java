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

import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ConstrainingType;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.assembly.builder.ComponentPreProcessor;
import org.apache.tuscany.sca.assembly.impl.ImplementationImpl;
import org.apache.tuscany.sca.contribution.jee.EJBImplementationGenerated;
import org.apache.tuscany.sca.runtime.RuntimeComponent;


/**
 * The model representing an EJB implementation in an SCA assembly model.
 *
 * @version $Rev$ $Date$
 */
class EJBImplementationGeneratedImpl extends ImplementationImpl implements EJBImplementationGenerated {

    private String ejbLink;

    /**
     * Constructs a new EJB implementation.
     */
    EJBImplementationGeneratedImpl() {
        super();
    }

    @Override
    public ConstrainingType getConstrainingType() {
        // The EJB implementation does not support constrainingTypes
        return null;
    }

    public String getEJBLink() {
        return ejbLink;
    }
    
    @Override
    public void setConstrainingType(ConstrainingType constrainingType) {
        // The EJB implementation does not support constrainingTypes
    }

    public void setEJBLink(String ejbLink) {
        this.ejbLink = ejbLink;
    }
    
}
