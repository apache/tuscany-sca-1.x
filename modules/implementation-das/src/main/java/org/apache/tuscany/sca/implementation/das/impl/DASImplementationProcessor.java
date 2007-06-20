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
package org.apache.tuscany.sca.implementation.das.impl;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.xml.Constants;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.implementation.das.DASImplementation;
import org.apache.tuscany.sca.implementation.das.DASImplementationFactory;


/**
 * Implements a STAX artifact processor for DAS implementations.
 * 
 * The artifact processor is responsible for processing <implementation.das>
 * elements in SCA assembly XML composite files and populating the DAS
 * implementation model, resolving its references to other artifacts in the SCA
 * contribution, and optionally write the model back to SCA assembly XML.
 * 
 * @version $Rev$ $Date$
 */
public class DASImplementationProcessor implements StAXArtifactProcessor<DASImplementation> {
    private static final QName IMPLEMENTATION_DAS = new QName(Constants.SCA10_NS, "implementation.das");
    
    private DASImplementationFactory dasFactory;
    
    public DASImplementationProcessor(DASImplementationFactory crudFactory) {
        this.dasFactory = crudFactory;
    }

    public QName getArtifactType() {
        // Returns the qname of the XML element processed by this processor
        return IMPLEMENTATION_DAS;
    }

    public Class<DASImplementation> getModelType() {
        // Returns the type of model processed by this processor
        return DASImplementation.class;
    }

    public DASImplementation read(XMLStreamReader reader) throws ContributionReadException {
        assert IMPLEMENTATION_DAS.equals(reader.getName());
        
        // Read an <implementation.crud> element
        try {
            // Read the das config file attribute. 
            // This is das configuration side file to use
            String config = reader.getAttributeValue(null, "config");
            
            // Read the data access type attribute
            // This is the type of data store in use (e.g rdb, xml, etc)
            String dataAccessType = reader.getAttributeValue(null, "dataAccessType");

            // Create an initialize the DAS implementation model
            DASImplementation implementation = dasFactory.createDASImplementation();
            implementation.setConfig(config);
            implementation.setDataAccessType(dataAccessType);
            
            // Skip to end element
            while (reader.hasNext()) {
                if (reader.next() == END_ELEMENT && IMPLEMENTATION_DAS.equals(reader.getName())) {
                    break;
                }
            }
            
            return implementation;
        } catch (XMLStreamException e) {
            throw new ContributionReadException(e);
        }
    }

    public void resolve(DASImplementation impl, ModelResolver resolver) throws ContributionResolveException {
    }

    public void write(DASImplementation model, XMLStreamWriter outputSource) throws ContributionWriteException {
    }
}
