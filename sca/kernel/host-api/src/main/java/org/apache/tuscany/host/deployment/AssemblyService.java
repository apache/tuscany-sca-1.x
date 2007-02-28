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
package org.apache.tuscany.host.deployment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Service interface for managing the logical assembly for a Tuscany runtime.
 * 
 * @version $Rev$ $Date$
 */
public interface AssemblyService {

    /**
     * Add the composite identified by a supplied URI to the Domain Level
     * Composite. The supplied composite is added to the domain composite with
     * semantics that correspond to the domain-level composite having an
     * &lt;include&gt; statement that references the supplied composite. All of
     * the composite’s components become top-level components and the services
     * become externally visible services.
     * 
     * @param contribution The URI of the contribution
     * @param composite The URI of the composite
     * @return The added composite
     * @throws DeploymentException
     */
    Object addCompositeToDomain(URI contribution, URI composite) throws DeploymentException;

    /**
     * Remove from the Domain Level composite the elements corresponding to the
     * composite identified by a supplied composite URI. This means that the
     * removal of the components, wires, services and references originally
     * added to the domain level composite by the identified composite.
     * 
     * @param contribution
     * @param composite
     * @throws DeploymentException
     */
    void removeCompositeFromDomain(URI contribution, URI composite) throws DeploymentException;

    /**
     * Returns a &lt;composite&gt; definition that has an &lt;include&gt; line
     * for each composite that had been added to the domain level composite.
     * 
     * @return The composite representing the SCA domain
     */
    Object getDomainComposite();

    /**
     * Get the definitions for named artifacts in the included composites.
     * 
     * @param <T> The type of the definition
     * @param contribution The URI of an installed contribution
     * @param type The java type of the symbol space such as
     *            javax.wsdl.Definition
     * @param namespace The namespace of the artifact
     * @param name The name of the artifact
     * @return A single definition, in whatever form is appropriate for that
     *         definition type.
     */
    <T> T getDefinition(URI contribution, Class<T> type, String namespace, String name);

    /**
     * Apply a set of changes to the SCA Domain's logical assembly.
     * 
     * @param changeSet the location of a resource containing a set of changes
     * @throws DeploymentException if there was a problem making the changes
     * @throws IOException if there was a problem accessing the resource
     */
    void applyChanges(URL changeSet) throws DeploymentException, IOException;

    /**
     * Apply a set of changes to the SCA Domain's logical assembly.
     * 
     * @param changeSet a stream for reading a resource containing a set of
     *            changes; the stream will not be closed but no guarantee is
     *            made on the position the stream is left in
     * @param contentType the type of changeSet on the stream; must be a valid
     *            Content-Type value as specified by <a
     *            href="http://www.ietf.org/rfc/rfc2045.txt">RFC2045</a> and
     *            must not be null
     * @throws DeploymentException if there was a problem making the changes
     * @throws IOException if there was a problem reading the stream
     */
    void applyChanges(InputStream changeSet, String contentType) throws DeploymentException, IOException;
}
