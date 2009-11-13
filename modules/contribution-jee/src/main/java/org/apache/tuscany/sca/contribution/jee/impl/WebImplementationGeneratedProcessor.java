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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.contribution.processor.BaseStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.monitor.Monitor;


/**
 * Dummy processor that's used to mark WebImplementationGeneratedImpl instance
 * as resolved at the appropriate point in the resolution processing
 * 
 */
public class WebImplementationGeneratedProcessor extends BaseStAXArtifactProcessor implements StAXArtifactProcessor<WebImplementationGeneratedImpl> {
    
    public WebImplementationGeneratedProcessor(ExtensionPointRegistry registry,
                                               Monitor monitor) {
    }

    public QName getArtifactType() {
        return null;
    }

    public Class<WebImplementationGeneratedImpl> getModelType() {
        // Returns the type of model processed by this processor
        return WebImplementationGeneratedImpl.class;
    }

    public WebImplementationGeneratedImpl read(XMLStreamReader reader) throws ContributionReadException, XMLStreamException {
        return null;
    }

    public void resolve(WebImplementationGeneratedImpl implementation, ModelResolver resolver) throws ContributionResolveException {
        implementation.setUnresolved(false);
    }

    public void write(WebImplementationGeneratedImpl implementation, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {
    }
}
