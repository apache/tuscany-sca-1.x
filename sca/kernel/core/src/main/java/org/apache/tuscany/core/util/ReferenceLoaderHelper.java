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
package org.apache.tuscany.core.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tuscany.spi.loader.IncompatibleOverridingServiceContractException;
import org.apache.tuscany.spi.loader.InvalidReferenceException;
import org.apache.tuscany.spi.model.CompositeReferenceDefinition;
import org.apache.tuscany.spi.model.Multiplicity;
import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.model.AbstractReferenceDefinition;
import org.apache.tuscany.spi.model.ServiceContract;

/**
 * Functions to help in loading of references
 */

public final class ReferenceLoaderHelper {
    /**
     * Hide constructor
     */
    private ReferenceLoaderHelper() {
    }

    public static void populateRefTargets(AbstractReferenceDefinition refDefn, String concatenatedTargets) throws InvalidReferenceException {
        StringTokenizer st = new StringTokenizer(concatenatedTargets);
        while (st.hasMoreTokens()) {
            try {
                refDefn.addTarget(new URI(st.nextToken()));
            } catch (URISyntaxException e) {
                throw new InvalidReferenceException(e);
            }
        }
    }
    
    public static void populatePromotedRefs(CompositeReferenceDefinition refDefn, String concatenatedUris) throws InvalidReferenceException {
        StringTokenizer st = new StringTokenizer(concatenatedUris);
        while (st.hasMoreTokens()) {
            try {
                refDefn.addPromotedReference(new URI(st.nextToken()));
            } catch (URISyntaxException e) {
                throw new InvalidReferenceException(e);
            }
        }
    }
    
    public static boolean isCompatibleMultiplicity(Multiplicity definedMul, Multiplicity overridenMul) {
        return ((definedMul == overridenMul) || 
            (definedMul == Multiplicity.ZERO_ONE && overridenMul == Multiplicity.ZERO_N) || 
            (definedMul == Multiplicity.ZERO_N && overridenMul == Multiplicity.ZERO_ONE) ||
            (definedMul == Multiplicity.ONE_ONE && overridenMul == Multiplicity.ONE_N) || 
                (definedMul == Multiplicity.ONE_N && overridenMul == Multiplicity.ONE_ONE));
    }

    public static boolean isValidMultiplicityOverride(Multiplicity definedMul, Multiplicity overridenMul) {
        if (definedMul != overridenMul) {
            switch (definedMul) {
                case ZERO_N:
                    return overridenMul == Multiplicity.ZERO_ONE;
                case ONE_N:
                    return overridenMul == Multiplicity.ONE_ONE;
                default:
                    return false;
            }
        } else {
            return true;
        }
    }
    
    public static boolean validateMultiplicityAndTargets(Multiplicity multiplicity,
                                                         List<URI> targets) {
        int count = targets.size();
        switch (multiplicity) {
            case ZERO_N:
                break;
            case ZERO_ONE:
                if (count > 1) {
                    return false;
                }
                break;
            case ONE_ONE:
                if (count != 1) {
                    return false;
                }
                break;
            case ONE_N:
                if (count < 1) {
                    return false;
                }
                break;
        }
        return true;
    }

    public static void checkInterfaceCompatibility(ServiceContract<?> source,
                                                   ServiceContract<?> target,
                                                   boolean ignoreCallback) throws IncompatibleOverridingServiceContractException {
        if (source == target) {
            // Shortcut for performance
            return;
        }
        
        //FIXME: we don't go into operations comparison for now since the service contract thta the 
        //interace loader loads does not have operation details as the intropection 'has not been deep'
        //The JavaInterfaceLoader has set the 'deep' introspection to false....will fix this after
        //sorting that out.
        
        if (source.getInteractionScope() != target.getInteractionScope()) {
            throw new IncompatibleOverridingServiceContractException(
                                                           "Interaction scopes settings do not match",
                                                           source, target);
        }

        for (Operation<?> operation : source.getOperations().values()) {
            Operation<?> targetOperation = target.getOperation(operation.getName());
            if (targetOperation == null) {
                throw new IncompatibleOverridingServiceContractException("Operation not found on target",
                                                               source, target);
            }
            if (!targetOperation.equals(operation)) {
                throw new IncompatibleOverridingServiceContractException(
                                                               "Target operations are not compatible",
                                                               source, target);
            }
        }

        if (ignoreCallback) {
            return;
        }

        for (Operation<?> operation : source.getCallbackOperations().values()) {
            Operation<?> targetOperation = target.getCallbackOperations().get(operation.getName());
            if (targetOperation == null) {
                throw new IncompatibleOverridingServiceContractException(
                                                               "Callback operation not found on target",
                                                               source, target, null,
                                                               targetOperation);
            }
            if (!operation.equals(targetOperation)) {
                throw new IncompatibleOverridingServiceContractException(
                                                               "Target callback operation is not compatible",
                                                               source, target, operation,
                                                               targetOperation);
            }
        }
    }
}
