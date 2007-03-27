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

package org.apache.tuscany.spi.loader;

import org.apache.tuscany.spi.model.Multiplicity;

/**
 * Denote the violation in overriding of multiplicity declaration for a reference either when a 
 * Component is overriding what is original in the underlying ComponentType or when a Composite is 
 * overriding while promoting a Component reference definition. 
 * 
 * @version $Rev $Date
 */
public class ReferenceMultiplicityOverridingException extends LoaderException {
	private static final long serialVersionUID = -5827228144446207988L;
	private final Multiplicity originalMultiplicity;
	private final Multiplicity overridenMultiplicity;
    
    /**
     * @param message
     * @param identifier
     * @param multiplicity
     * @param numberOfTargets
     */
    public ReferenceMultiplicityOverridingException(String message,
                                          String identifier,
                                          Multiplicity original,
                                          Multiplicity overriden) {
        super(message, identifier);
        this.originalMultiplicity = original;
        this.overridenMultiplicity = overriden;
    }

    /**
     * @param identifier
     * @param multiplicity
     * @param numberOfTargets
     */
    public ReferenceMultiplicityOverridingException(String identifier, Multiplicity original, Multiplicity overriden) {
        this("Multiplicity Overriding is violated", identifier, original, overriden);
    }
    
    
    /**
     * Get the original multiplicity for the reference definition
     * 
     * @return multiplicity of the reference definition
     */
    public Multiplicity getoriginalMultiplicity() {
		return originalMultiplicity;
	}

    /**
     * Get the overriden multiplicity for the reference definition
     * 
     * @return multiplicity of the reference definition
     */
	public Multiplicity getOverridenMultiplicity() {
		return overridenMultiplicity;
	}
}
