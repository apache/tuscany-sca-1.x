package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class CompositeResultFactory implements ResultFactory<CompositeResult> {

	public CompositeResultFactory() {
		// empty constructor
	}

	public CompositeResult createResult(String name) {
		return new CompositeResult(name);
	}

	public String getName(Document document) {
		return document.get(getType());
	}

	public String getType() {
		return SearchFields.COMPOSITE_FIELD;
	}

	public CompositeResult createResult(Document document) {
		String name = getName(document);

		if (name != null) {
			return createResult(name);
		}

		return null;

	}

}
