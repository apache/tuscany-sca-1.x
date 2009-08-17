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

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

/**
 * 
 * @version $Rev$ $Date$
 */
public class DefaultFileDocumentProcessor implements DocumentProcessor {

    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document doc,
                            String parent) {

        if (object instanceof FileContent) {
            FileContent file = (FileContent)object;

            Reader reader;
            try {
                reader = new InputStreamReader(file.getInputStream());

                if (doc == null) {
                    doc = documents.get(file.getPath());
                }

                doc.add(new Field(SearchFields.FILE_CONTENT_FIELD, reader));

                doc.add(new Field(SearchFields.FILE_CONTENT_FIELD, "", Field.Store.YES, Field.Index.ANALYZED));

                return doc;

            } catch (IOException e) {
                // ignore the file
            }

        }

        return null;

    }

    public Object getDocumentKey(Object object) {

        if (object instanceof File) {
            File file = (File)object;
            String path = file.getPath();

            if (path != null && path.length() == 0) {
                return null;
            }

            return path;

        }

        throw new IllegalArgumentException();

    }

}
