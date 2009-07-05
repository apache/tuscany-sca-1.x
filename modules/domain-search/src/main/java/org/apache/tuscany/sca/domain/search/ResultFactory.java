package org.apache.tuscany.sca.domain.search;

import org.apache.lucene.document.Document;

public interface ResultFactory<T extends Result> {
	
	String getType();
	
	String getName(Document document);
	
	T createResult(String name);
	
	T createResult(Document document);

}
