package org.apache.tuscany.sca.domain.manager.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.tuscany.sca.data.collection.Entry;
import org.apache.tuscany.sca.data.collection.Item;
import org.apache.tuscany.sca.data.collection.ItemCollection;
import org.apache.tuscany.sca.data.collection.LocalItemCollection;
import org.apache.tuscany.sca.data.collection.NotFoundException;
import org.apache.tuscany.sca.domain.search.DomainSearch;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.impl.ArtifactResult;
import org.apache.tuscany.sca.domain.search.impl.FileContentResult;
import org.apache.tuscany.sca.domain.search.impl.HighlightingUtil;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

@Scope("COMPOSITE")
@Service(interfaces = { ItemCollection.class, LocalItemCollection.class })
public class Searcher implements ItemCollection, LocalItemCollection {

	static char[] characters = { '\u0001'/*  */, '\u0003'/*  */,
			'\u0004'/*  */,

	};

	final private static String HTML_NEW_LINE = "<BR/>";

	final private static String PATH_SEPARATOR = "/";

	@Reference
	public WorkspaceReader contributionCollection;

	@Reference
	public DomainSearch domainSearch;

	public void delete(String key) throws NotFoundException {
		System.out.println("delete");
	}

	private static void startIndentation(int size, StringWriter writer) {
		writer
				.write("<p style='margin-top:0em;margin-bottom:0em;padding:0em;margin-left:");
		writer.write(Integer.toString(size));
		writer.write("em'>");

	}

	private static void endIndentation(StringWriter writer) {
		writer.write("</p>");
	}

	public Item get(String key) throws NotFoundException {
		System.out.println("get");
		this.domainSearch.contributionAdded(null, this.contributionCollection
				.getWorkspace());
		Result[] results = this.domainSearch.parseAndSearch(key, false);

		Item item = new Item();
		item.setTitle("Results");

		StringWriter sw = new StringWriter();

		for (Result result : results) {

			try {
				writeToHTML(0, result, sw);

			} catch (IOException e) {
				// ignore result
			}

			sw.write(HTML_NEW_LINE);

		}

		String contents = HighlightingUtil.replaceHighlightMarkupBy(sw
				.getBuffer(), "<font color='#FF0000'>", "</font>");

		item.setContents(replaceAll(contents, 40) + "end");
		
		System.out.println(item.getContents().indexOf("@Referencevar"));

		System.out.println(item.getContents());

		return item;

	}

	//	
	// private static String replaceAll(CharSequence c) {
	// StringBuilder sb = new StringBuilder();
	// // HashSet<Character> set = new HashSet<Character>();
	// Arrays.sort(characters);
	// // int start = 0, end = 4;
	// // char[] chars = new char[end - start];
	// // System.arraycopy(characters, start, chars, 0, end - start);
	//		
	// for (int i = 0 ; i < c.length() ; i++) {
	// char actual = c.charAt(i);
	//			
	// //if (Arrays.binarySearch(characters, actual) < 0) {
	//
	// if (!Character.isIdentifierIgnorable(actual)) {
	// sb.append(actual);
	//				
	// } else {
	// //sb.append('0');
	// }
	//			
	// }
	// //
	// // System.out.println("set-size: " + set.size());
	// // for (char character : set) {
	// // System.out.print(",");
	// // System.out.print((int) character);
	// // System.out.print("/*" + character + "*/");
	// //
	// // }
	// //
	// // System.out.println();
	//		
	// return sb.toString();
	//		
	// }

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

			if (actual > less || actual == '&' || actual == '#'
					|| actual == '\'' || actual == ' ' || actual == '%'
					|| actual == ':') {
				sb.append(actual);

			} else {
				// sb.append('0');
			}

		}
		//		
		// System.out.println("set-size: " + set.size());
		// for (char character : set) {
		// System.out.print(",");
		// System.out.print((int) character);
		// System.out.print("/*" + character + "*/");
		//			
		// }
		//		
		// System.out.println();

		return sb.toString();

	}

	private static Result writeFileContentResultToHTML(int indentation,
			Result result, StringWriter writer) throws IOException {

		String content = ((FileContentResult) result).getContent();
		writer.write("file:");

		if (content != null && content.length() > 0) {

			StringEscapeUtils.escapeHtml(writer, result.getName());
			writer.write(HTML_NEW_LINE);

			startIndentation(indentation, writer);
			writer.write("<span style='max-width:100%'>");
			StringEscapeUtils.escapeHtml(writer, content);
			writer.write("</span>");
			endIndentation(writer);
			writer.write(HTML_NEW_LINE);

		} else {

			writer.write(result.getName());
			Collection<Result> contents = result.getContents().values();

			while (contents.size() == 1) {
				result = contents.iterator().next();

				if (result instanceof FileContentResult) {
					writer.write(PATH_SEPARATOR);
					writer.write(result.getName());

					contents = result.getContents().values();

				} else {
					break;
				}

			}
			
			if (result instanceof FileContentResult) {
				FileContentResult fileResult = (FileContentResult) result;
				content = fileResult.getContent();

				if (content != null && content.length() > 0) {

					writer.write(HTML_NEW_LINE);

					startIndentation(indentation, writer);
					writer.write("<span style='max-width:100%'>");
					StringEscapeUtils.escapeHtml(writer, content);
					writer.write("</span>");
					endIndentation(writer);
					writer.write(HTML_NEW_LINE);

				}

			}

		}
		
		return result;

	}

	private static void writeToHTML(int indentation, Result result,
			StringWriter writer) throws IOException {

		startIndentation(indentation++, writer);

		if (result instanceof FileContentResult) {
			result = writeFileContentResultToHTML(indentation, result, writer);

		} else {

			StringEscapeUtils.escapeHtml(writer, result.getClass()
					.getSimpleName().substring(0,
							result.getClass().getSimpleName().length() - 6));
			writer.write(':');
			StringEscapeUtils.escapeHtml(writer, result.getName());
			writer.write(HTML_NEW_LINE);

			if (result instanceof ArtifactResult) {
				String location = ((ArtifactResult) result).getLocation();

				if (location != null) {
					startIndentation(indentation, writer);
					writer.write("location: ");
					StringEscapeUtils.escapeHtml(writer, location);
					endIndentation(writer);
					writer.write(HTML_NEW_LINE);

				}

			}

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

	public Entry<String, Item>[] query(String queryString) {
		System.out.println("query");
		return null;
	}

}
