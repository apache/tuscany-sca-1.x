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
import org.apache.tuscany.sca.contribution.namespace.NamespaceExport;
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


public class ExportXYZProcessor extends BaseStAXArtifactProcessor implements StAXArtifactProcessor<ExportXYZ> {

    private static final String NS = "http://someuri";
    private static final QName EXPORT = new QName(NS, "export.xyz");
    private static final String URI = "uri";
    private static final String AN_ATTRIBUTE = "anAttribute";

    private final ImportExportXYZFactory factory;
    private final Monitor monitor;
    private final ExtensionFactory extensionFactory;


    public ExportXYZProcessor(ModelFactoryExtensionPoint modelFactories,
                                    Monitor monitor) {
        this.factory = modelFactories.getFactory(ImportExportXYZFactory.class);
        this.extensionFactory = modelFactories.getFactory(ExtensionFactory.class);
        this.monitor = monitor;
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
	        Problem problem = new ProblemImpl(this.getClass().getName(), "export-validation-messages", Severity.ERROR, model, message, (Object[])messageParameters);
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
             Problem problem = new ProblemImpl(this.getClass().getName(), "export-validation-messages", Severity.ERROR, model, message, ex);
             monitor.problem(problem);
         }
     }

    public QName getArtifactType() {
        return EXPORT;
    }

    public Class<ExportXYZ> getModelType() {
        return ExportXYZ.class;
    }


    public ExportXYZ read(XMLStreamReader reader) throws ContributionReadException {
        ExportXYZ export = this.factory.createExport();
        QName element = null;

        try {
            while (reader.hasNext()) {
                int event = reader.getEventType();
                switch (event) {
                    case START_ELEMENT:
                        element = reader.getName();

                        // Read <export>
                        if (EXPORT.equals(element)) {
                            String ns = reader.getAttributeValue(null, URI);
                            if (ns == null) {
                            	error("AttributeNameSpaceMissing", reader);
                            } else {
                            	export.setURI(ns);
                            }
                            
                            String anAttribute = reader.getAttributeValue(null, AN_ATTRIBUTE);
                            export.setAnAttribute(anAttribute);
                        }

                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if (EXPORT.equals(reader.getName())) {
                            return export;
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

        return export;
    }

    public void write(ExportXYZ export, XMLStreamWriter writer) throws ContributionWriteException, XMLStreamException {

        // Write <export>
        writer.writeStartElement(EXPORT.getNamespaceURI(), EXPORT.getLocalPart());

        if (export.getURI() != null) {
            writer.writeAttribute(URI, export.getURI());
        }
        
        if (export.getAnAttribute() != null) {
            writer.writeAttribute(AN_ATTRIBUTE, export.getAnAttribute());
        }

        writer.writeEndElement();
    }

    public void resolve(ExportXYZ export, ModelResolver resolver) throws ContributionResolveException {

        if (export.getURI() != null)
            // Initialize the export's resolver
            export.setModelResolver(new ExportXYZModelResolver(resolver));
    }
}
