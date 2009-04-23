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

package org.apache.tuscany.sca.binding.http.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.builder.impl.ProblemImpl;
import org.apache.tuscany.sca.assembly.xml.Constants;
import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.binding.http.HTTPBindingFactory;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.BaseStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;

public class HTTPBindingProcessor extends BaseStAXArtifactProcessor implements StAXArtifactProcessor<HTTPBinding> {
    private static final String BINDING_HTTP = "binding.http";
    private static final QName BINDING_HTTP_QNAME = new QName(Constants.SCA10_NS, BINDING_HTTP);
    
    private static final String NAME = "name";
    private static final String URI = "uri";
    
    private HTTPBindingFactory httpBindingFactory;
    private Monitor monitor;

    public HTTPBindingProcessor(ModelFactoryExtensionPoint modelFactories, Monitor monitor) {
        this.httpBindingFactory = modelFactories.getFactory(HTTPBindingFactory.class);
        this.monitor = monitor;
    }
    
    public QName getArtifactType() {
        return BINDING_HTTP_QNAME;
    }
    
    public Class<HTTPBinding> getModelType() {
        return HTTPBinding.class;
    }

    public HTTPBinding read(XMLStreamReader reader) throws ContributionReadException, XMLStreamException {
        HTTPBinding httpBinding = httpBindingFactory.createHTTPBinding();

        String name = getString(reader, NAME);
        if(name != null) {
            httpBinding.setName(name);
        }
        
        String uri = getString(reader, URI);
        if (uri != null) {
            httpBinding.setURI(uri);
        }

        return httpBinding;
    }

    public void write(HTTPBinding httpBinding, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {
        writer.writeStartElement(Constants.SCA10_NS, BINDING_HTTP);
        
        // Write binding name
        if (httpBinding.getName() != null) {
            writer.writeAttribute(NAME, httpBinding.getName());
        }

        // Write binding URI
        if (httpBinding.getURI() != null) {
            writer.writeAttribute(URI, httpBinding.getURI());
        }
        
        writer.writeEndElement();
    }


    public void resolve(HTTPBinding model, ModelResolver resolver) throws ContributionResolveException {
        // Should not need to do anything here for now... 
        
    }
    
    /**
     * Report a error.
     * 
     * @param problems
     * @param message
     * @param model
     */
    private void error(String message, Object model, Object... messageParameters) {
         if (monitor != null) {
                Problem problem = new ProblemImpl(this.getClass().getName(), "impl-bpel-validation-messages", Severity.ERROR, model, message, (Object[])messageParameters);
                monitor.problem(problem);
         }
    }

}
