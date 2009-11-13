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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultProcessor;

/**
 * 
 * @version $Rev$ $Date$
 */
public class FileContentResultProcessor implements ResultProcessor {

    public Result process(Document document, Result result) {

        if (document.getFieldable(SearchFields.FILE_CONTENT_FIELD) != null) {
            Reader reader;

            ParentField parentField = new ParentField(document.get(SearchFields.PARENT_FIELD));

            int lastParentElementIndex = parentField.getElementsCount() - 1;
            String parentURI;

            if (SearchFields.ARTIFACT_FIELD.equals(parentField.getElementType(lastParentElementIndex))) {
                parentURI = parentField.getElementURI(lastParentElementIndex);

                // if (parentURI.startsWith("jar:")) {

                try {
                    reader = new InputStreamReader(new URL(parentURI).openStream());

                } catch (IOException e) {
                    return result;
                }

                // } else {
                //
                // try {
                // reader = new InputStreamReader(new FileInputStream(
                // new File(parentURI + (parentURI.length() > 0 ? "/" : "") +
                // name)));
                //
                // } catch (FileNotFoundException e) {
                // return result;
                // }
                //
                // }

                try {

                    StringBuilder sb = new StringBuilder();
                    int c;

                    // TODO: load the chars into an array buffer instead of one
                    // at a
                    // time
                    while ((c = reader.read()) != -1) {
                        char character = (char)c;

                        if (!Character.isIdentifierIgnorable(character)) {
                            sb.append(character);
                        }

                    }

                    result.setValue(sb.toString());

                } catch (Exception e) {
                    // ignore content loading, TODO: maybe it should return an
                    // error
                    // message as the content

                }

            }

        }

        return result;

    }

}
