package org.apache.tuscany.sca.domain.search;

import org.apache.tuscany.sca.domain.search.impl.Document;

public interface DocumentProcessor {
	
	final public static Document FAKE_DOCUMENT = new Document();
	
	Document process(DocumentProcessorsMap processors, DocumentMap documents, Object object, Document document, String parent);
	
	Object getDocumentKey(Object object);

}
