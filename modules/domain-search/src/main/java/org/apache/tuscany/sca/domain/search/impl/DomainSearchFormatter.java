package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.TokenGroup;

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
