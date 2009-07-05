package org.apache.tuscany.sca.domain.search.impl;

public class DomainSearchResultFactory extends ResultFactoryList {

	private static final long serialVersionUID = -7421799172738469027L;

	public DomainSearchResultFactory() {
		add(new ContributionResultFactory());
		add(new CompositeResultFactory());
		add(new ComponentResultFactory());
		add(new ComponentTypeResultFactory());
		add(new ArtifactResultProcessor.ArtifactResultFactory());
		add(new BindingResultFactory());
		add(new FileContentResultProcessor.FileContentResultFactory());
		
	}

}
