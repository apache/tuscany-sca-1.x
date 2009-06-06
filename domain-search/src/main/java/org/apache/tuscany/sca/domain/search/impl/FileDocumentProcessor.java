package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;
import org.apache.tuscany.sca.domain.search.DocumentProcessorsMap;

public class FileDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessorsMap processors,
			DocumentMap documents, Object object, Document doc, String parent) {

		if (object instanceof File) {
			File file = (File) object;
			documents.get(file.getAbsoluteFile());
			
			if (file.isFile()) {
				doc.add(new Field(SearchFields.DIRECTORY_FIELD, file.getAbsolutePath(),
						Field.Store.YES, Field.Index.ANALYZED));
				
				parent += DomainPathAnalyzer.PATH_SEPARATOR + SearchFields.FILE_FIELD + DomainPathAnalyzer.TYPE_SEPARATOR + file.getName();
				
				File[] files = file.listFiles();
				
				for (File childFile : files) {
					Document fileDoc = processors.process(processors, documents, childFile, null, parent);
					
					fileDoc.add(new Field(SearchFields.PARENT_FIELD, parent,
							Field.Store.YES, Field.Index.ANALYZED));
					
				}
				
			}
			
			return doc;

		}

		throw new IllegalArgumentException();

	}

	public Object getDocumentKey(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
