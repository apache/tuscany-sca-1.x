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

import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.TokenGroup;

/**
 * 
 * @version $Rev$ $Date$
 */
public class DomainSearchFormatter implements Formatter {

	final public static String HIGHLIGHT_START = "\u0005\u0005\u0006";

	final public static String HIGHLIGHT_END = "\u0006\u0005\u0005";

	private StringBuilder sb = new StringBuilder();

	public String highlightTerm(String originalText, TokenGroup tokenGroup) {

		if (tokenGroup.getTotalScore() > 0) {
			sb.setLength(0);
			
			sb.append(HIGHLIGHT_START).append(originalText).append(
					HIGHLIGHT_END);

			return sb.toString();

		} else {
			return originalText;
		}

	}
	
	public static boolean isHighlighted(String text) {
		int start = text.indexOf(HIGHLIGHT_START);
		int end = text.indexOf(HIGHLIGHT_END);
		
		if (start < end && start != -1) {
			start = text.indexOf(HIGHLIGHT_START, start + 1);
			
			if (start > end || start == -1) {
				return true;
			}
			
		}
		
		return false;
		
	}

}
