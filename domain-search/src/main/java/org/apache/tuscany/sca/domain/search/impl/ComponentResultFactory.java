package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class ComponentResultFactory implements ResultFactory<ComponentResult> {

	public ComponentResultFactory() {
		// empty constructor
	}
	
	public ComponentResult createResult(String name) {
		return new ComponentResult(name);
	}

	public String getName(Document document) {
		return document.get(getType());
	}

	public String getType() {
		return SearchFields.COMPONENT_FIELD;
	}

	public ComponentResult createResult(Document document) {
		String name = getName(document);
		
		if (name != null) {
			return createResult(name);
		}
		
		return null;
		
	}

}
