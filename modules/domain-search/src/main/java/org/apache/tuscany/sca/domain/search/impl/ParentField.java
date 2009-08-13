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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @version $Rev$ $Date$
 */
public class ParentField implements Serializable {

	private static final long serialVersionUID = -2090538050273088026L;

	final private List<ParentFieldElement> elements;

	public ParentField(String parentFieldValue) {
		int length = parentFieldValue.length();

		if (length == 0) {
			this.elements = Collections.emptyList();
			
			return;
			
		}
		
		this.elements = new ArrayList<ParentFieldElement>();

		ParentFieldElement element = null;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length ; i++) {
			char c = parentFieldValue.charAt(i);

			if (c == DomainPathAnalyzer.PATH_SEPARATOR || c == DomainPathAnalyzer.PATH_START) {

				if (sb.length() > 0 || element != null) {

					if (element == null) {
						element = new ParentFieldElement();
					}
					
					if (element.type == null) {
						element.type = "";
					}
					
					if (element.uri == null) {
						element.uri = element.name = sb.toString();
						
					} else {
						element.name = sb.toString();
					}
					
					sb.setLength(0);
					this.elements.add(element);
					element = null;

				}

			} else if (c == DomainPathAnalyzer.TYPE_SEPARATOR) {

				if (element == null) {
					element = new ParentFieldElement();
				}
				
				element.type = sb.toString();
				
				sb.setLength(0);

			} else if (c == DomainPathAnalyzer.URI_SEPARATOR) {
				
				if (element == null) {
					element = new ParentFieldElement();
				}
				
				element.uri = sb.toString();
				
				sb.setLength(0);
				
			} else {
				sb.append(c);
			}

		}
		
		if (sb.length() > 0 || element != null) {

			if (element == null) {
				element = new ParentFieldElement();
			}
			
			if (element.type == null) {
				element.type = "";
			}
			
			if (element.uri == null) {
				element.uri = element.name = sb.toString();
				
			} else {
				element.name = sb.toString();
			}
			
			sb.setLength(0);
			this.elements.add(element);
			element = null;

		}

	}
	
	public static String getURIPath(ParentField parentField) {
		return getURIPath(parentField, System.getProperty("file.separator"));
	}
	
	public static String getURIPath(ParentField parentField, String pathSeparator) {
		StringBuilder sb = new StringBuilder();
		sb.append(pathSeparator);
		int elementsCount = parentField.getElementsCount();
		
		for (int i = 0 ; i < elementsCount ; i++) {
			sb.append(parentField.getElementName(i));
			sb.append(pathSeparator);
			
		}
		
		if (sb.length() > pathSeparator.length()) {
			sb.setLength(sb.length() - pathSeparator.length());
		}
		
		return sb.toString();
		
	}

	public static int getParentElementsCount(String parent) {
		int length = parent.length();

		if (length == 0) {
			return 0;
		}

		boolean pathSeparatorBefore = true;
		int count = 1;

		for (int i = 0; i < length - 1; i++) {
			char c = parent.charAt(i);

			if (c == DomainPathAnalyzer.PATH_SEPARATOR && !pathSeparatorBefore) {
				pathSeparatorBefore = true;
				count++;

			} else {
				pathSeparatorBefore = false;
			}

		}

		return count;

	}
	
	public int getElementsCount() {
		return this.elements.size();
	}
	
	public String getElementType(int index) {
		return this.elements.get(index).type;
	}
	
	public String getElementURI(int index) {
		return this.elements.get(index).uri;
	}
	
	public String getElementName(int index) {
		return this.elements.get(index).name;
	}

	final private static class ParentFieldElement {

		String type;

		String uri;

		String name;

	}

}
