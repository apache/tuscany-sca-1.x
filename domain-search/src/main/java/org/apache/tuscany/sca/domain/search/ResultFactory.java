package org.apache.tuscany.sca.domain.search;

import org.apache.lucene.document.Document;

public interface ResultFactory<T extends Result> {
	
	T createResult(String field, String value);
	
	T createResult(Document document);

}
