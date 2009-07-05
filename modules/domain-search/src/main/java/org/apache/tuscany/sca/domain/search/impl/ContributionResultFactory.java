package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class ContributionResultFactory implements
		ResultFactory<ContributionResult> {

	public ContributionResultFactory() {
		// empty constructor
	}

	public ContributionResult createResult(String name) {
		return new ContributionResult(name);
	}

	public String getName(Document document) {
		return document.get(SearchFields.CONTRIBUTION_FIELD);
	}

	public String getType() {
		return SearchFields.CONTRIBUTION_FIELD;
	}

	public ContributionResult createResult(Document document) {
		String name = getName(document);

		if (name != null) {
			return createResult(name);
		}

		return null;

	}

}
