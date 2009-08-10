package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class DomainSearchResultProcessor extends ResultProcessorList {

	private static final long serialVersionUID = 792292814333612713L;

	public DomainSearchResultProcessor(
			ResultFactory<? extends Result> resultFactory) {
		
		super(new DomainSearchResultFactory());
		
		add(new FileContentResultProcessor());
		
	}

}
