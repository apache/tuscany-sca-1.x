package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class BindingResultFactory implements ResultFactory<BindingResult> {

	public BindingResultFactory() {
		// empty constructor
	}

	public BindingResult createResult(String name) {
		return new BindingResult(name);
	}

	public String getType() {
		return SearchFields.BINDING_FIELD;
	}

	public String getName(Document document) {
		return document.get(getType());
	}

	public BindingResult createResult(Document document) {
		String name = getName(document);
		
		if (name != null) {
			return createResult(name);
		}
		
		return null;
		
		
	}
	
}
