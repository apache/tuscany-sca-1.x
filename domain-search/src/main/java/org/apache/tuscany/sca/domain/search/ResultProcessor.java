package org.apache.tuscany.sca.domain.search;

import org.apache.lucene.document.Document;

public interface ResultProcessor {
	
	Result process(Document document, Result result);

}
