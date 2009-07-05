package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SpanScorer;

final public class HighlightingUtil {
	
	public static String bestFragmentHighlighted(Query query, Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		
		// TODO: load the chars into a buffer instead of one at a time
		while ((c = reader.read()) != -1) {
			char character = (char) c;
			
			if (!Character.isIdentifierIgnorable(character)) {
				sb.append(character);
			}
			
		}
		
		String text = sb.toString();
		
		CachingTokenFilter tokenStream = new CachingTokenFilter(new NamingAnalyzer().tokenStream(
		        SearchFields.FILE_CONTENT_FIELD, new StringReader(text)));
		
		Highlighter highlighter = new Highlighter(new DomainSearchFormatter(), new SpanScorer(query, SearchFields.FILE_CONTENT_FIELD, tokenStream, ""));
		highlighter.setTextFragmenter(new SimpleFragmenter(100));
		tokenStream.reset();
		
	    try {
			return highlighter.getBestFragments(tokenStream, text, 2, "...");
			
		} catch (InvalidTokenOffsetsException e) {
			
			// could not create fragments, return empty string
			return "";
			
		}
	    
	}
	
	public static String replaceHighlightMarkupBy(CharSequence text,
			String startHighlight, String endHighlight) {
		StringBuilder sb = new StringBuilder();
		int start = 0;
		int end = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (start > 0) {

				if (c == DomainSearchFormatter.HIGHLIGHT_START.charAt(start)) {
					start++;

					if (start == DomainSearchFormatter.HIGHLIGHT_START.length()) {
						sb.append(startHighlight);
						start = 0;

					}

				} else {
					
					for (int j = 0; j < start; j++) {
						sb.append(DomainSearchFormatter.HIGHLIGHT_START.charAt(j));
					}
					
					start = 0;

				}

			} else if (end > 0) {

				if (c == DomainSearchFormatter.HIGHLIGHT_END.charAt(end)) {
					end++;

					if (end == DomainSearchFormatter.HIGHLIGHT_END.length()) {
						sb.append(endHighlight);
						end = 0;

					}

				} else {

					for (int j = 0; j < end; j++) {
						sb.append(DomainSearchFormatter.HIGHLIGHT_END.charAt(j));
					}
					
					end = 0;

				}

			} else if (c == DomainSearchFormatter.HIGHLIGHT_START.charAt(0)) {
				start = 1;

			} else if (c == DomainSearchFormatter.HIGHLIGHT_END.charAt(0)) {
				end = 1;

			} else {
				sb.append(c);
			}

		}

		if (start > 0) {

			for (int j = 0; j < start; j++) {
				sb.append(DomainSearchFormatter.HIGHLIGHT_START.charAt(j));
			}

		} else if (end > 0) {

			for (int j = 0; j < start; j++) {
				sb.append(DomainSearchFormatter.HIGHLIGHT_END.charAt(j));
			}

		}
		
		return sb.toString();

	}

}
