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
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SpanScorer;
import org.apache.tuscany.sca.domain.search.Result;

/**
 * 
 * @version $Rev$ $Date$
 */
final public class HighlightingUtil {

    public static void highlightResult(Result result, Query query) {
        highlightResult(result, query, new SimpleFragmenter(70));
    }

    public static void highlightResult(Result result, Query query, Fragmenter fragmenter) {
        Map<String, Result> contents = result.getContents();

        if (contents != null) {

            for (Result content : contents.values()) {
                highlightResult(content, query, fragmenter);
            }

        }

        try {
            String highlightedText =
                HighlightingUtil.bestFragmentHighlighted(result.getField(), query, result.getValue(), fragmenter);

            // checks if something was highlighted before resetting the value
            if (highlightedText != null && highlightedText.length() > 0) {
                result.setValue(highlightedText);
            }

        } catch (IOException e) {
            // ignore highlighting
        }

    }

    public static String highlight(String field, Query query, String text) throws IOException {
        String highlightedText = bestFragmentHighlighted(field, query, text, new NullFragmenter());

        if (highlightedText == null || text.length() >= highlightedText.length()) {
            return text;
        }

        return highlightedText;

    }

    public static String bestFragmentHighlighted(String field, Query query, String text) throws IOException {
        return bestFragmentHighlighted(field, query, text, new SimpleFragmenter(100));
    }

    public static String bestFragmentHighlighted(String field, Query query, String text, Fragmenter fragmenter)
        throws IOException {
        CachingTokenFilter tokenStream =
            new CachingTokenFilter(new DomainSearchAnalyzer().tokenStream(field, new StringReader(text)));

        Highlighter highlighter =
            new Highlighter(new DomainSearchFormatter(), new SpanScorer(query, field, tokenStream, ""));
        highlighter.setTextFragmenter(fragmenter);
        tokenStream.reset();

        try {
            return highlighter.getBestFragments(tokenStream, text, 2, " ... ");

        } catch (InvalidTokenOffsetsException e) {

            // could not create fragments, return empty string
            return "";

        }

    }

    public static String replaceHighlightMarkupBy(CharSequence text, String startHighlight, String endHighlight) {
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
