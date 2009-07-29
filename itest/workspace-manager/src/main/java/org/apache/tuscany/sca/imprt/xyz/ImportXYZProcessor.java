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

package org.apache.tuscany.sca.imprt.xyz;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.ExtensionFactory;
import org.apache.tuscany.sca.assembly.builder.impl.ProblemImpl;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.contribution.namespace.NamespaceImport;
import org.apache.tuscany.sca.contribution.namespace.NamespaceImportExportFactory;
import org.apache.tuscany.sca.contribution.processor.BaseStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXAttributeProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.Problem;
import org.apache.tuscany.sca.monitor.Problem.Severity;

/**
 * Artifact processor for Namespace import
 *
 * @version $Rev$ $Date$
 */
public class ImportXYZProcessor extends BaseStAXArtifactProcessor implements StAXArtifactProcessor<ImportXYZ> {
    private static final String NS = "http://someuri";

    private static final QName IMPORT = new QName(NS, "import.xyz");

    private static final String URI = "uri";
    private static final String AN_ATTRIBUTE = "anAttribute";

    private final ImportExportXYZFactory factory;
    private final ExtensionFactory extensionFactory;
    private final Monitor monitor;

    public ImportXYZProcessor(ModelFactoryExtensionPoint modelFactories,
                                    Monitor monitor) {
        this.factory = modelFactories.getFactory(ImportExportXYZFactory.class);
        this.monitor = monitor;
        this.extensionFactory = modelFactories.getFactory(ExtensionFactory.class);
    }

    /**
     * Report a warning.
     *
     * @param problems
     * @param message
     * @param model
     */
     private void error(String message, Object model, Object... messageParameters) {
    	 if (monitor != null) {
	        Problem problem = new ProblemImpl(this.getClass().getName(), "contribution-namespace-validation-messages", Severity.ERROR, model, message, (Object[])messageParameters);
	        monitor.problem(problem);
    	 }
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
             Problem problem = new ProblemImpl(this.getClass().getName(), "contribution-namespace-validation-messages", Severity.ERROR, model, message, ex);
             monitor.problem(problem);
         }
     }

    public QName getArtifactType() {
        return IMPORT;
    }

    public Class<ImportXYZ> getModelType() {
        return ImportXYZ.class;
    }

    public ImportXYZ read(XMLStreamReader reader) throws ContributionReadException {
    	ImportXYZ importXYZ= this.factory.createImport();
        QName element;

        try {
            while (reader.hasNext()) {
                int event = reader.getEventType();
                switch (event) {
                    case START_ELEMENT:
                        element = reader.getName();

                        // Read <import>
                        if (IMPORT.equals(element)) {
                            String ns = reader.getAttributeValue(null, URI);
                            if (ns == null) {
                            	error("AttributeNameSpaceMissing", reader);
                            } else {
                            	importXYZ.setURI(ns);
                            }

                            String anAttribute = reader.getAttributeValue(null, AN_ATTRIBUTE);
                            if (anAttribute != null) {
                            	importXYZ.setAnAttribute(anAttribute);
                            }
                        } 
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if (IMPORT.equals(reader.getName())) {
                            return importXYZ;
                        }
                        break;
                }

                // Read the next element
                if (reader.hasNext()) {
                    reader.next();
                }
            }
        }
        catch (XMLStreamException e) {
            ContributionReadException ex = new ContributionReadException(e);
            error("XMLStreamException", reader, ex);
        }

        return importXYZ;
    }

    public void write(ImportXYZ importXYZ, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {

        // Write <import>
        writer.writeStartElement(IMPORT.getNamespaceURI(), IMPORT.getLocalPart());

        if (importXYZ.getURI() != null) {
            writer.writeAttribute(URI, importXYZ.getURI());
        }
        if (importXYZ.getAnAttribute() != null) {
            writer.writeAttribute(AN_ATTRIBUTE, importXYZ.getAnAttribute());
        }

        writer.writeEndElement();
    }


    public void resolve(ImportXYZ model, ModelResolver resolver) throws ContributionResolveException {
    }
}
