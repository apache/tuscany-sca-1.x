package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class ComponentTypeResultFactory implements
		ResultFactory<ComponentTypeResult> {

	public ComponentTypeResultFactory() {
		// empty constructor
	}

	public String getName(Document document) {
		return document.get(getType());
	}

	public String getType() {
		return SearchFields.COMPONENT_TYPE_FIELD;
	}

	public ComponentTypeResult createResult(String name) {
		return new ComponentTypeResult(name);
	}

	public ComponentTypeResult createResult(Document document) {
		String name = getName(document);

		if (name != null) {
			return createResult(name);
		}

		return null;

	}

}
