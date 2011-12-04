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

package org.apache.tuscany.sca.core.assembly;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.runtime.BusinessInterface;

/**
 * Artifact processor for reference parameters.
 * 
 * @version $Rev: 658664 $ $Date: 2008-05-21 13:46:26 +0100 (Wed, 21 May 2008) $
 */
public class BusinessInterfaceProcessor implements StAXArtifactProcessor<BusinessInterface> {
    private static final QName BUSINESS_INTERFACE =
        new QName("http://tuscany.apache.org/xmlns/sca/1.0", "businessInterface", "tuscany");
    
    /**
     * Constructs a new processor.
     * 
     * @param modelFactories
     */
    public BusinessInterfaceProcessor(ModelFactoryExtensionPoint modelFactories, Monitor monitor) {
    }

    /**
     * @see org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor#getArtifactType()
     */
    public QName getArtifactType() {
        return BUSINESS_INTERFACE;
    }

    /**
     * @see org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor#read(javax.xml.stream.XMLStreamReader)
     */
    public BusinessInterface read(XMLStreamReader reader) throws ContributionReadException, XMLStreamException {
        BusinessInterface businessInterface = new BusinessInterfaceImpl();
        businessInterface.setInterface(reader.getAttributeValue(null, "interface"));
        return businessInterface;
    }

    /**
     * @see org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor#write(java.lang.Object, javax.xml.stream.XMLStreamWriter)
     */
    public void write(BusinessInterface model, XMLStreamWriter writer) throws ContributionWriteException,
        XMLStreamException {
        writer.writeStartElement(BUSINESS_INTERFACE.getPrefix(),
                                 BUSINESS_INTERFACE.getLocalPart(),
                                 BUSINESS_INTERFACE.getNamespaceURI());
        writer.writeNamespace(BUSINESS_INTERFACE.getPrefix(), BUSINESS_INTERFACE.getNamespaceURI());
        if (model.getInterface() != null) {
            writer.writeAttribute("interface", model.getInterface().toString());
        }
        writer.writeEndElement();
    }

    /**
     * @see org.apache.tuscany.sca.contribution.processor.ArtifactProcessor#getModelType()
     */
    public Class<BusinessInterface> getModelType() {
        return BusinessInterface.class;
    }

    /**
     * @see org.apache.tuscany.sca.contribution.processor.ArtifactProcessor#resolve(java.lang.Object, org.apache.tuscany.sca.contribution.resolver.ModelResolver)
     */
    public void resolve(BusinessInterface model, ModelResolver resolver) throws ContributionResolveException {
    }

}
