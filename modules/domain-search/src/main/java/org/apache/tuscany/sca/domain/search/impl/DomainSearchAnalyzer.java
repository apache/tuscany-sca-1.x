package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;

public class DomainSearchAnalyzer extends PerFieldAnalyzerWrapper {

	public DomainSearchAnalyzer() {
		super(new NamingAnalyzer());
		
		addAnalyzer(SearchFields.PARENT_FIELD, new DomainPathAnalyzer());
		
	}

}
