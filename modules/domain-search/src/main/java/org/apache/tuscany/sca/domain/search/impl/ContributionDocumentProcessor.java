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
package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Field;

import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

/**
 * 
 * @version $Rev$ $Date$
 */
public class ContributionDocumentProcessor implements DocumentProcessor {

    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document doc,
                            String parent) {

        if (object instanceof Contribution) {
            Contribution contribution = (Contribution)object;
            String uri = contribution.getURI();

            if (uri != null) {

                if (uri.length() == 0) {
                    uri = null;

                } else {

                    parent +=
                        Character.toString(DomainPathAnalyzer.PATH_START) + SearchFields.CONTRIBUTION_FIELD
                            + DomainPathAnalyzer.TYPE_SEPARATOR
                            + uri;

                }

            }

            if (uri != null) {

                if (doc == null) {
                    doc = documents.get(uri);
                }

                doc.add(new Field(SearchFields.CONTRIBUTION_FIELD, uri, Field.Store.YES, Field.Index.ANALYZED));

            } else {
                doc = FAKE_DOCUMENT;
            }

            for (Artifact artifact : contribution.getArtifacts()) {
                Document artifactDoc = parentProcessor.process(parentProcessor, documents, artifact, null, parent);

                if (uri != null) {

                    artifactDoc
                        .add(new Field(SearchFields.PARENT_FIELD, parent, Field.Store.YES, Field.Index.ANALYZED));

                }

            }

            // for (Import imprt : contribution.getImports()) {
            // Document importDoc = processors.process(processors, documents,
            // imprt, null);
            //
            // if (uri != null) {
            //
            // importDoc.add(new Field(SearchFields.IMPORTEDBY_FIELD, uri,
            // Field.Store.YES, Field.Index.ANALYZED));
            //
            // }
            //
            // }
            //
            // for (Export export : contribution.getExports()) {
            // Document exportDoc = processors.process(processors, documents,
            // export, null);
            //
            // if (uri != null) {
            //
            // exportDoc.add(new Field(SearchFields.EXPORTEDBY_FIELD, uri,
            // Field.Store.YES, Field.Index.ANALYZED));
            //
            // }
            //
            // }

            if (!object.getClass().getSimpleName().contains("Workspace")) {

                for (Composite composite : contribution.getDeployables()) {
                    Document compositeDoc =
                        parentProcessor.process(parentProcessor, documents, composite, null, parent);

                    if (uri != null) {

                        compositeDoc.add(new Field(SearchFields.PARENT_FIELD, parent, Field.Store.YES,
                                                   Field.Index.ANALYZED));

                    }

                }

            }

            return doc;

        }

        throw new IllegalArgumentException();

    }

    public Object getDocumentKey(Object object) {

        if (object instanceof Contribution) {
            Contribution contribution = (Contribution)object;
            String uri = contribution.getURI();

            if (uri != null && uri.length() == 0) {
                return null;
            }

            return uri;

        }

        throw new IllegalArgumentException();

    }

}
