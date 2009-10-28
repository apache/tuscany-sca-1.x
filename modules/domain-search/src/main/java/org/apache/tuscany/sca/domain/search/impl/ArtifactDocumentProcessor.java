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

import java.io.IOException;
import java.net.URL;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

/**
 * @version $Rev$ $Date$
 */
public class ArtifactDocumentProcessor implements DocumentProcessor {

    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document document,
                            String parent) {

        if (object instanceof Artifact) {
            Artifact artifact = (Artifact)object;

            if (!(object instanceof Contribution)) {

                String location = artifact.getLocation();

                try {

                    if (document == null) {
                        document = documents.get(SearchFields.ARTIFACT_FIELD + location);
                    }

                    FileContent fileContent = new WrappedFileContent(new URL(location));

                    document.add(new Field(SearchFields.ARTIFACT_FIELD, fileContent.getName(), Field.Store.YES,
                                           Field.Index.ANALYZED));

                    parent +=
                        DomainPathAnalyzer.PATH_SEPARATOR + SearchFields.ARTIFACT_FIELD
                            + DomainPathAnalyzer.TYPE_SEPARATOR
                            + location
                            + DomainPathAnalyzer.URI_SEPARATOR
                            + fileContent.getName();

                    // parent += DomainPathAnalyzer.PATH_SEPARATOR
                    // + SearchFields.FILE_FIELD
                    // + DomainPathAnalyzer.TYPE_SEPARATOR + location +
                    // DomainPathAnalyzer.URI_SEPARATOR + fileContent.getName();

                    Document fileDoc = parentProcessor.process(parentProcessor, documents, fileContent, null, parent);

                    fileDoc.add(new Field(SearchFields.PARENT_FIELD, parent, Field.Store.YES, Field.Index.ANALYZED));

                } catch (IOException e) {
                    // ignore location
                }

            }

            return document == null ? FAKE_DOCUMENT : document;

        }

        throw new IllegalArgumentException();

    }

    public Object getDocumentKey(Object obj) {

        if (obj instanceof Artifact) {
            Artifact artifact = (Artifact)obj;
            String uri = artifact.getLocation();

            if (uri != null && uri.length() == 0) {
                return null;
            }

            return SearchFields.ARTIFACT_FIELD + uri;

        }

        throw new IllegalArgumentException();

    }

}
