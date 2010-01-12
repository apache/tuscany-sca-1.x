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
package org.apache.tuscany.sca.domain.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.data.collection.Entry;
import org.apache.tuscany.sca.data.collection.Item;
import org.apache.tuscany.sca.data.collection.ItemCollection;
import org.apache.tuscany.sca.data.collection.LocalItemCollection;
import org.apache.tuscany.sca.data.collection.NotFoundException;
import org.apache.tuscany.sca.domain.search.DomainSearch;
import org.apache.tuscany.sca.domain.search.IndexException;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.impl.DomainSearchFormatter;
import org.apache.tuscany.sca.domain.search.impl.HighlightingUtil;
import org.apache.tuscany.sca.domain.search.impl.SearchFields;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

@Scope("COMPOSITE")
@Service(interfaces = {ItemCollection.class, LocalItemCollection.class})
public class Searcher implements ItemCollection, LocalItemCollection {
    static char[] characters = {'\u0001'/*  */, '\u0003'/*  */, '\u0004'/*  */,};

    final private static String HTML_NEW_LINE = "<BR/>";

    final private static String PATH_SEPARATOR = "/";

    final private static int MAX_CONTENT_LINE_WIDTH = 300;

    final private static String HIGHLIGHT_START = "<font color='#FF0000'>";

    final private static String HIGHLIGHT_END = "</font>";

    @Reference
    public ContributionsReader contributionReader;

    @Reference
    public LocalItemCollection contributionCollection;

    @Reference
    public DomainSearch domainSearch;

    private int elementCounter;

    public void delete(String key) throws NotFoundException {
        
    }

    private static void startIndentation(int size, StringWriter writer) {
        writer.write("<p style='margin-top:0em;margin-bottom:0em;padding:0em;margin-left:");
        writer.write(Integer.toString(size));
        writer.write("em'>");

    }

    private static void endIndentation(StringWriter writer) {
        writer.write("</p>");
    }

    public Item get(String key) throws NotFoundException {

        try {

            if (key.startsWith("highlight")) {
                int lastSemicolonIndex = key.lastIndexOf(";");
                String artifact = key.substring(lastSemicolonIndex + 1);
                int secondLastSemicolonIndex = key.lastIndexOf(";", lastSemicolonIndex - 1);
                String contribution = key.substring(secondLastSemicolonIndex + 1, lastSemicolonIndex);
                String query = key.substring("highlight".length(), secondLastSemicolonIndex);

                return highlightArtifact(contribution, artifact, query);

            } else if (key.startsWith("query")) {
                return executeQuery(key.substring("query".length()));

            } else {
                throw new NotFoundException("Invalid operation!");
            }

        } catch (Exception t) {
            if (t instanceof NotFoundException) {
                throw (NotFoundException)t;
            }
            throw new NotFoundException("Internal error!");
        }

    }

    private Item highlightArtifact(String contribution, String artifact, String query) throws NotFoundException {

        Item item = this.contributionCollection.get(contribution);

        if (item == null) {
            throw new NotFoundException("contribution not found: " + contribution);
        }

        String location = item.getAlternate();

        if (location.endsWith(".jar") || location.endsWith(".zip")) {
            location =
                "jar:" + (location.startsWith("file:") ? "" : "file:")
                    + location
                    + '!'
                    + (artifact.startsWith("/") ? "" : "/")
                    + artifact;

        } else {
            location += (location.endsWith("/") ? "" : "/") + artifact;
        }

        try {
            Reader reader = new InputStreamReader(new URL(location).openStream());
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

            String highlightedText = this.domainSearch.highlight(SearchFields.FILE_CONTENT_FIELD, sb.toString(), query);
            highlightedText = highlightedText.replaceAll("\n", "<br/>");
            highlightedText = highlightedText.replaceAll(" ", "&nbsp;");
            highlightedText =
                HighlightingUtil.replaceHighlightMarkupBy(highlightedText, HIGHLIGHT_START, HIGHLIGHT_END);

            item = new Item();
            item.setTitle(contribution + ";" + artifact);
            item.setContents(highlightedText);

            return item;

        } catch (Exception e) {
            throw new NotFoundException("Could not highlight artifact: " + e.getMessage(), e);
        }
        
    }

    private Item executeQuery(String query) throws NotFoundException {

        if (!this.domainSearch.indexExists()) {
            Contribution[] contributions = this.contributionReader.readContributions();

            for (Contribution contribution : contributions) {
                if (!contribution.getURI().equals(DomainManagerUtil.DEPLOYMENT_CONTRIBUTION_URI)) {
                    
                    try {
                        this.domainSearch.updateContribution(contribution, contribution);
                    } catch (IndexException e) {
                        
                    }
                    
                }
            }
        }

        Result[] results;
        try {
            results = this.domainSearch.parseAndSearch(query, true);
        } catch (Exception e1) {
            throw new NotFoundException("Exception while searching: " + e1.getMessage(), e1);
        }

        Item item = new Item();
        item.setTitle("Results");

        StringWriter sw = new StringWriter();
        this.elementCounter = 0;

        if (results.length > 0) {
            for (Result result : results) {
                try {
                    writeToHTML(0, result, sw);
                } catch (IOException e) {
                    // ignore result
                }
            }

            String contents = HighlightingUtil.replaceHighlightMarkupBy(sw.getBuffer(), HIGHLIGHT_START, HIGHLIGHT_END);

            item.setContents(replaceAll(contents, 40));

        } else {
            item.setContents("No results match: <u>" + query + "</u>");
        }

        // System.out.println(item.getContents());

        return item;

    }

    private static String replaceAll(CharSequence c, int less) {
        StringBuilder sb = new StringBuilder();
        // HashSet<Character> set = new HashSet<Character>();
        Arrays.sort(characters);
        // int start = 0, end = 4;
        // char[] chars = new char[end - start];
        // System.arraycopy(characters, start, chars, 0, end - start);

        for (int i = 0; i < c.length(); i++) {
            char actual = c.charAt(i);

            // if (Arrays.binarySearch(characters, actual) < 0) {

            // if (actual != '\u000b' && actual != '\u0020' &&
            // actual != '\u000c' && actual != '\u0009' &&
            // actual != ')' && actual != '(') {

            if (actual > less || actual == '&'
                || actual == '#'
                || actual == '\''
                || actual == ' '
                || actual == '%'
                || actual == ':'
                || actual == '('
                || actual == ')'
                || actual == '"') {

                sb.append(actual);

            } else {
                // sb.append('0');
            }

        }

        return sb.toString();

    }

    private static String getIconLocationForResult(Result result) {

        if (SearchFields.COMPONENT_FIELD.equals(result.getField())) {
            return "icons/component.png' title='Component";

        } else if (SearchFields.COMPOSITE_FIELD.equals(result.getField())) {
            return "icons/composite.png' title='Composite";

        } else if (SearchFields.ARTIFACT_FIELD.equals(result.getField())) {
            return "icons/artifact.png' title='Artifact";
        }

        return "icons/feed-icon.png";

    }

    private static void writeResultIcon(Writer writer, Result result) throws IOException {
        writer.write("<img src='");
        writer.write(getIconLocationForResult(result));
        writer.write("'/> ");

    }

    private static Result writeFileContentResultToHTML(int indentation, Result result, StringWriter writer)
        throws IOException {

        Map<String, Result> contents = result.getContents();
        writeResultIcon(writer, result);

        if (contents.size() == 1 && SearchFields.ARTIFACT_FIELD.equals(contents.values().iterator().next().getField())) {

            writer.write(result.getValue());
            contents = result.getContents();

            while (contents.size() == 1 && SearchFields.ARTIFACT_FIELD.equals(contents.values().iterator().next()
                .getField())) {

                result = contents.values().iterator().next();

                writer.write(PATH_SEPARATOR);
                StringEscapeUtils.escapeHtml(writer, result.getValue());

                contents = result.getContents();

            }

        } else {
            StringEscapeUtils.escapeHtml(writer, result.getValue());
        }

        return result;

    }

    private static String removeHighlighting(String text) {
        return HighlightingUtil.replaceHighlightMarkupBy(text, "", "");
    }

    private static void writeResultName(Result result, StringWriter writer) throws IOException {

        if (SearchFields.CONTRIBUTION_FIELD.equals(result.getField())) {
            writer.write("<a href='/contribution/");
            writer.write(removeHighlighting(result.getValue()));
            writer.write("'>");
            writer.write(StringEscapeUtils.escapeHtml(result.getValue()));
            writer.write("</a>");

        } else if (SearchFields.COMPOSITE_FIELD.equals(result.getField())) {
            writer.write("<a href='/composite-source/composite:");
            writer.write(getContributionURI(result));
            writer.write(';');
            writer.write(removeHighlighting(result.getValue()));
            writer.write("'>");
            writer.write(StringEscapeUtils.escapeHtml(result.getValue()));
            writer.write("</a>");

        } else {
            StringEscapeUtils.escapeHtml(writer, result.getValue());
        }

    }

    private static String getContributionURI(Result result) {

        if (result == null) {
            return "";

        } else if (SearchFields.CONTRIBUTION_FIELD.equals(result.getField())) {
            return removeHighlighting(result.getValue());

        } else {
            return getContributionURI(result.getContainer());
        }

    }

    private void writeToHTML(int indentation, Result result, StringWriter writer) throws IOException {

        startIndentation(indentation++, writer);
        String field = result.getField();

        if (SearchFields.FILE_CONTENT_FIELD.equals(field)) {
            String content = result.getValue();

            if (content != null && content.length() > 0 && DomainSearchFormatter.isHighlighted(content)) {
                String contributionPlusArtifact =
                    getContributionURI(result) + ";" + removeHighlighting(result.getContainer().getValue());
                
                writer.write(HTML_NEW_LINE);

                this.elementCounter++;
                writer.write("<div style='margin-top:0em;margin-left:");
                writer.write(Integer.toString(indentation));
                writer.write("em;background-color:#FFE175;max-width:100%;border-style:dashed;border-width:1px;padding:5px' id='");
    		writer.write(contributionPlusArtifact);
    		writer.write("'>");
                writer.write("<p style='margin:0px;padding:0px;font-size:70%'>");
                // + "<a style='margin:0px;padding:0px' href='#filecontent");
                // writer.write(Integer.toString(this.elementCounter));

                

                // writer.write("' onclick='search");
                // writer.write("'>view all</a>&nbsp;&nbsp;<a href='/files/contribution=");
                writer.write("<a style='margin:0px;padding:0px' href='javascript:getHighlighted(\"");
                writer.write(contributionPlusArtifact);
                writer.write("\");'>view all</a>&nbsp;&nbsp;");

                writer.write("<a href='/files/contribution=");
                writer.write(contributionPlusArtifact);
                writer.write("'>download</a></p><div id='");
                writer.write("test");
                writer.write("' style='margin:8px 0px 0px 0px;padding:0px'>");

                int i = 0;
                while (i < content.length()) {
                    StringEscapeUtils.escapeHtml(writer, content.substring(i, Math.min(i + MAX_CONTENT_LINE_WIDTH,
                                                                                       content.length())));
                    writer.write(HTML_NEW_LINE);
                    i += MAX_CONTENT_LINE_WIDTH;
                }

                writer.write("</div>");
                writer.write("</div>");

            }

        } else if (SearchFields.ARTIFACT_FIELD.equals(field)) {
            result = writeFileContentResultToHTML(indentation, result, writer);
        } else {
            writeResultIcon(writer, result);
            writeResultName(result, writer);
        }

        endIndentation(writer);

        for (Result actualResult : result.getContents().values()) {
            writeToHTML(indentation, actualResult, writer);
        }
    }

    public Entry<String, Item>[] getAll() {
        System.out.println("getAll");
        return null;
    }

    public String post(String key, Item item) {
        System.out.println("post");
        return null;
    }

    public void put(String key, Item item) throws NotFoundException {
        System.out.println("put");
    }

    @SuppressWarnings("unchecked")
    public Entry<String, Item>[] query(String queryString) {

        try {

            Item item;
            String key;

            if (queryString.startsWith("highlight")) {
                int lastSemicolonIndex = queryString.lastIndexOf(";");
                String artifact = queryString.substring(lastSemicolonIndex + 1);
                int secondLastSemicolonIndex = queryString.lastIndexOf(";", lastSemicolonIndex - 1);
                String contribution = queryString.substring(secondLastSemicolonIndex + 1, lastSemicolonIndex);
                String query = queryString.substring("highlight".length(), secondLastSemicolonIndex);

                item = highlightArtifact(contribution, artifact, query);
                key = queryString.substring("highlight".length());

            } else if (queryString.startsWith("query")) {
                key = queryString.substring("query".length());
                item = executeQuery(key);

            } else {
                throw new NotFoundException("Invalid operation!");
            }

            Entry<String, Item>[] returnArray = new Entry[1];
            returnArray[0] = new Entry<String, Item>(key, item);

            return returnArray;

        } catch (Exception t) {
            return new Entry[0];
        }

    }

}
