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

import org.apache.tuscany.sca.assembly.ConstrainingType;
import org.apache.tuscany.sca.assembly.impl.ImplementationImpl;
import org.apache.tuscany.sca.contribution.jee.EJBImplementationGenerated;
import org.apache.tuscany.sca.contribution.jee.EjbInfo;
import org.apache.tuscany.sca.contribution.jee.EjbModuleInfo;

/**
 * The model representing an EJB implementation in an SCA assembly model when the 
 * EJB implementation has been generated by introspecting a non-enhanced EAR $
 */
class EJBImplementationGeneratedImpl extends ImplementationImpl implements EJBImplementationGenerated {

    private EjbInfo ejbInfo;
    private EjbModuleInfo ejbModuleInfo;

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
   
    @Override
    public void setConstrainingType(ConstrainingType constrainingType) {
        // The EJB implementation does not support constrainingTypes
    }
    
    public EjbInfo getEJBInfo() {
        return ejbInfo;
    }
    
    public void setEJBInfo(EjbInfo ejbInfo) {
        this.ejbInfo = ejbInfo;
    }

	public EjbModuleInfo getEjbModuleInfo() {
		return ejbModuleInfo;
	}

	public void setEjbModuleInfo(EjbModuleInfo ejbModuleInfo) {
		this.ejbModuleInfo = ejbModuleInfo;
	}
	
	// make sure the generated implementation can be distinguished 
	// based on the ejb info that is referenced
	@Override
	public int hashCode() {
	    return getEJBInfo().hashCode();
	}
    
}
