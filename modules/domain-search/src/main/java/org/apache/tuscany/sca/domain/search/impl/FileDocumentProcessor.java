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

import java.util.LinkedList;

import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

/**
 * 
 * @version $Rev$ $Date$
 */
public class FileDocumentProcessor extends LinkedList<DocumentProcessor>
		implements DocumentProcessor {

	private static final long serialVersionUID = 7843338343970738591L;

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document doc, String parent) {

		if (object instanceof FileContent) {
			FileContent file = (FileContent) object;

			if (!file.isLeaf()) {

				if (doc == null) {
					doc = documents.get(SearchFields.FILE_CONTENT_FIELD + file.getPath());
				}

				//FileContent[] files = file.getChildren();

//				for (FileContent childFile : files) {
//					// Document fileDoc = parentProcessor.process(
//					// parentProcessor, documents, childFile, null,
//					// parent);
//
//					Document fileDoc = null;
//
//					fileDoc = process(this, documents, childFile, null, parent);
//
//					if (fileDoc == null) {
//						continue;
//					}
//
//					fileDoc.add(new Field(SearchFields.PARENT_FIELD, parent,
//							Field.Store.YES, Field.Index.ANALYZED));
//
//				}

				return doc;

			} else {

				for (DocumentProcessor processor : this) {
					Document newDoc = processor.process(this, documents, file,
							doc, parent);

					if (newDoc != null) {
						return newDoc;
					}

				}

			}

		}

		return doc;

	}

	public Object getDocumentKey(Object object) {

		if (object instanceof FileContent) {
			FileContent file = (FileContent) object;
			String path = file.getPath();

			if (path != null && path.length() == 0) {
				return null;
			}

			return SearchFields.FILE_CONTENT_FIELD + path;

		}

		throw new IllegalArgumentException();

	}

}
