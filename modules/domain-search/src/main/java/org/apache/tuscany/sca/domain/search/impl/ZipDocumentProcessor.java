package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

public class ZipDocumentProcessor implements DocumentProcessor {
	
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

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document document,
			String parent) {

		if (object instanceof SystemFileContent) {
			SystemFileContent file = (SystemFileContent) object;

			try {
				ZipFile zip = new ZipFile(file.getFile());
				
				if (document == null) {
					document = documents.get(file.getPath());
				}

				parent += DomainPathAnalyzer.PATH_SEPARATOR
						+ SearchFields.ARTIFACT_FIELD
						+ DomainPathAnalyzer.TYPE_SEPARATOR + "jar:file:" + file.getPath() + DomainPathAnalyzer.ARCHIVE_SEPARATOR + '/' + file.getName()
						+ DomainPathAnalyzer.URI_SEPARATOR + file.getName();
				
				document.add(new Field(SearchFields.ARTIFACT_FIELD, file.getName(), Field.Store.YES,
						Field.Index.ANALYZED));

				ZipFileContent[] zipFiles = ZipFileContent.createZipFileContent(zip);

				for (ZipFileContent zipFile : zipFiles) {
					
					parentProcessor.process(parentProcessor, documents,
							zipFile, document, parent);
					
				}
				
				return document;

			} catch (IOException e) {
				// ignore file
			}

		}
		
		return null;

	}

}
