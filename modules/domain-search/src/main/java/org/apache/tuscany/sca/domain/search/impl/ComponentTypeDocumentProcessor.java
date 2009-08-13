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
import org.apache.tuscany.sca.assembly.ComponentType;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;
import org.apache.tuscany.sca.interfacedef.Interface;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.Operation;

/**
 * 
 * @version $Rev$ $Date$
 */
public class ComponentTypeDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document doc, String parent) {

		if (object instanceof ComponentType) {
			ComponentType componentType = (ComponentType) object;
			String uri = componentType.getURI();

			if (uri != null && uri.length() == 0) {
				uri = null;
			}

			if (doc == null) {

				if (uri != null) {
					doc = documents.get(uri);

				} else {
					doc = FAKE_DOCUMENT;
				}

			}

			if (uri != null) {

				parent += DomainPathAnalyzer.PATH_SEPARATOR
						+ SearchFields.COMPONENT_TYPE_FIELD
						+ DomainPathAnalyzer.TYPE_SEPARATOR + uri;

				doc.add(new Field(SearchFields.COMPONENT_TYPE_FIELD, uri,
						Field.Store.YES, Field.Index.ANALYZED));

				for (Service service : componentType.getServices()) {
					Document serviceDoc = documents.get(uri + ':'
							+ service.getName());

					serviceDoc.add(new Field(SearchFields.SERVICE_NAME_FIELD,
							service.getName(), Field.Store.YES,
							Field.Index.ANALYZED));

					InterfaceContract interfaceContract = service
							.getInterfaceContract();

					if (interfaceContract != null) {

						Interface interfac = interfaceContract.getInterface();

						if (interfac != null) {

							for (Operation operation : interfac.getOperations()) {

								serviceDoc.add(new Field(
										SearchFields.SERVICE_INTERFACE_FIELD,
										operation.getName(), Field.Store.YES,
										Field.Index.ANALYZED));

							}

						}

						interfac = interfaceContract.getCallbackInterface();

						if (interfac != null) {

							for (Operation operation : interfac.getOperations()) {

								serviceDoc
										.add(new Field(
												SearchFields.SERVICE_INTERFACE_CALLBACK_FIELD,
												operation.getName(),
												Field.Store.YES,
												Field.Index.ANALYZED));

							}

						}

					}

					serviceDoc.add(new Field(SearchFields.PARENT_FIELD, parent,
							Field.Store.YES, Field.Index.ANALYZED));

				}

				for (Reference reference : componentType.getReferences()) {
					Document referenceDoc = documents.get(componentType
							.getURI()
							+ ':' + reference.getName());

					referenceDoc.add(new Field(
							SearchFields.REFERENCE_NAME_FIELD, reference
									.getName(), Field.Store.YES,
							Field.Index.ANALYZED));

					InterfaceContract interfaceContract = reference
							.getInterfaceContract();

					if (interfaceContract != null) {

						for (Operation operation : interfaceContract
								.getInterface().getOperations()) {

							referenceDoc.add(new Field(
									SearchFields.REFERENCE_INTERFACE_FIELD,
									operation.getName(), Field.Store.YES,
									Field.Index.ANALYZED));

						}

						for (Operation operation : interfaceContract
								.getCallbackInterface().getOperations()) {

							referenceDoc
									.add(new Field(
											SearchFields.REFERENCE_INTERFACE_CALLBACK_FIELD,
											operation.getName(),
											Field.Store.YES,
											Field.Index.ANALYZED));

						}

					}

					referenceDoc.add(new Field(SearchFields.PARENT_FIELD,
							parent, Field.Store.YES, Field.Index.ANALYZED));

				}

			}

			for (Property property : componentType.getProperties()) {
				Document propertyDoc = parentProcessor.process(parentProcessor,
						documents, property, null, parent);

				if (uri != null) {

					propertyDoc.add(new Field(SearchFields.PARENT_FIELD,
							parent, Field.Store.YES, Field.Index.ANALYZED));

				}

			}

			return doc;

		}

		throw new IllegalArgumentException();

	}

	public Object getDocumentKey(Object object) {

		if (object instanceof ComponentType) {
			ComponentType componentType = (ComponentType) object;
			String uri = componentType.getURI();

			if (uri == null || uri.length() == 0) {
				return null;

			} else {
				return uri;
			}

		}

		throw new IllegalArgumentException();

	}

}
