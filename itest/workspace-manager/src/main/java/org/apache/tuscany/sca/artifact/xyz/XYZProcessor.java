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

package org.apache.tuscany.sca.artifact.xyz;

import java.net.URI;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;

import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.URLArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;
import org.apache.tuscany.sca.monitor.impl.ProblemImpl;

/**
 * An ArtifactProcessor for XYZ documents.
 * 
 * @version $Rev$ $Date$
 */
public class XYZProcessor implements URLArtifactProcessor<XYZ> {

    private XMLInputFactory inputFactory;
    private Monitor monitor;

    public XYZProcessor(ModelFactoryExtensionPoint modelFactories, Monitor monitor) {
        this.inputFactory = modelFactories.getFactory(XMLInputFactory.class);
        this.monitor = monitor;
    }
    
    /**
     * Report a exception.
     * 
     * @param problems
     * @param message
     * @param model
     */
    private void error(String message, Object model, Exception ex) {
        if (monitor != null) {
            Problem problem = new ProblemImpl(this.getClass().getName(), "xsd-xml-validation-messages", Severity.ERROR, model, message, ex);
            monitor.problem(problem);
        }        
    }

    public XYZ read(URL contributionURL, URI artifactURI, URL artifactURL) throws ContributionReadException {
        try {
            XYZ xyz = new XYZ();
            String attribute = artifactURL.toString();
            xyz.setAnAttribute(attribute.substring(attribute.lastIndexOf('/') + 1));
            return xyz;
        } catch (Exception e) {
            ContributionReadException ce = new ContributionReadException(e);
            error("ContributionReadException", artifactURL, ce);
            throw ce;
        }
    }

    public void resolve(XYZ model, ModelResolver resolver) throws ContributionResolveException {
    }

    public String getArtifactType() {
        return ".xyz";
    }

    public Class<XYZ> getModelType() {
        return XYZ.class;
    }

}
