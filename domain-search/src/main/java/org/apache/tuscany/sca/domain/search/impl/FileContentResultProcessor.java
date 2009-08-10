package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultProcessor;

public class FileContentResultProcessor implements ResultProcessor {

	public Result process(Document document, Result result) {

		if (document.getFieldable(SearchFields.FILE_CONTENT_FIELD) != null) {
			Reader reader;

			ParentField parentField = new ParentField(document
					.get(SearchFields.PARENT_FIELD));

			int lastParentElementIndex = parentField.getElementsCount() - 1;
			String parentURI;

			if (SearchFields.ARTIFACT_FIELD.equals(parentField
					.getElementType(lastParentElementIndex))) {
				parentURI = parentField.getElementURI(lastParentElementIndex);

				// if (parentURI.startsWith("jar:")) {

				try {
					reader = new InputStreamReader(new URL(parentURI)
							.openStream());

				} catch (IOException e) {
					return result;
				}

				// } else {
				//
				// try {
				// reader = new InputStreamReader(new FileInputStream(
				// new File(parentURI + (parentURI.length() > 0 ? "/" : "") +
				// name)));
				//
				// } catch (FileNotFoundException e) {
				// return result;
				// }
				//
				// }

				try {

					StringBuilder sb = new StringBuilder();
					int c;

					// TODO: load the chars into an array buffer instead of one
					// at a
					// time
					while ((c = reader.read()) != -1) {
						char character = (char) c;

						if (!Character.isIdentifierIgnorable(character)) {
							sb.append(character);
						}

					}

					result.setValue(sb.toString());

				} catch (Exception e) {
					// ignore content loading, TODO: maybe it should return an
					// error
					// message as the content

				}

			}

		}

		return result;

	}

}
