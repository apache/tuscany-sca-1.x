package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;
import org.apache.tuscany.sca.domain.search.ResultProcessor;

public class FileContentResultProcessor implements ResultProcessor {

	public static class FileContentResultFactory implements
			ResultFactory<FileContentResult> {

		public FileContentResultFactory() {
			// empty constructor
		}

		public FileContentResult createResult(String name) {
			return new FileContentResult(name);
		}

		public String getName(Document document) {
			return document.get(SearchFields.FILE_FIELD);
		}

		public String getType() {
			return SearchFields.FILE_FIELD;
		}

		public FileContentResult createResult(Document document) {
			String name = getName(document);

			if (name != null) {
				return createResult(name);
			}

			return null;

		}

	}

	public Result process(Document document, Query query, Result result) {

		if (result instanceof FileContentResult) {
			FileContentResult fileContentResult = (FileContentResult) result;
			Reader reader;

			ParentField parentField = new ParentField(document
					.get(SearchFields.PARENT_FIELD));
			String parentURI = parentField.getElementURI(parentField
					.getElementsCount() - 1);
			String name = document.get(SearchFields.FILE_FIELD);

			if (name == null) {
				return result;
			}

			if (parentURI.indexOf(ZipDocumentProcessor.ARCHIVE_SEPARATOR) != -1) {
				String[] locations = parentURI.split(Character
						.toString(ZipDocumentProcessor.ARCHIVE_SEPARATOR));

				try {
					ZipFile zip = new ZipFile(new File(locations[0]));

					// TODO: normalize the entry path and check if the entry still exists
					ZipEntry entry = zip.getEntry(locations[1].substring(1) + name);
					
					Enumeration<? extends ZipEntry> entries = zip.entries();
					
					while (entries.hasMoreElements()) System.out.println(entries.nextElement().getName());
					
					reader = new InputStreamReader(zip.getInputStream(entry));

				} catch (IOException e) {
					return result;
				}

			} else {

				try {
					reader = new InputStreamReader(new FileInputStream(
							new File(parentURI + '/' + name)));

				} catch (FileNotFoundException e) {
					return result;
				}

			}

			try {
				fileContentResult.setContent(HighlightingUtil
						.bestFragmentHighlighted(query, reader));

			} catch (IOException e) {
				// ignore content loading, TODO: maybe it return an error
				// message as the content
			}

		}

		return result;

	}

}
