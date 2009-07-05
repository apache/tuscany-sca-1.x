package org.apache.tuscany.sca.domain.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

public interface ResultProcessor {
	
	Result process(Document document, Query query, Result result);

}
