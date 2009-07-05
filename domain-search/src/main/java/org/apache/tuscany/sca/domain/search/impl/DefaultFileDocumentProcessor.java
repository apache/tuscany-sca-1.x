package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

public class DefaultFileDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document doc, String parent) {

		if (object instanceof FileContent) {
			FileContent file = (FileContent) object;
			
			Reader reader;
			try {
				reader = new InputStreamReader(file.getInputStream());
				
				if (doc == null) {
					doc = documents.get(file.getPath());
				}
				
				doc.add(new Field(SearchFields.FILE_FIELD, file.getName(), Field.Store.YES,
						Field.Index.ANALYZED));
				
				doc.add(new Field(SearchFields.FILE_CONTENT_FIELD, reader));
				
				return doc;
				
			} catch (IOException e) {
				// ignore the file
			}
			
		}
		
		return null;

	}

	public Object getDocumentKey(Object object) {

		if (object instanceof File) {
			File file = (File) object;
			String path = file.getPath();

			if (path != null && path.length() == 0) {
				return null;
			}

			return path;

		}

		throw new IllegalArgumentException();

	}

}
