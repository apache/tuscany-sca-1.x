package org.apache.tuscany.sca.domain.search.impl;

public class DomainSearchResultFactory extends PriorityFieldListResultFactory {

	private static final long serialVersionUID = -7421799172738469027L;

	public DomainSearchResultFactory() {
		add(SearchFields.COMPOSITE_FIELD);
		add(SearchFields.COMPONENT_FIELD);
		add(SearchFields.COMPONENT_TYPE_FIELD);
		add(SearchFields.CONTRIBUTION_FIELD);
		add(SearchFields.BINDING_FIELD);
		add(SearchFields.FILE_CONTENT_FIELD);
		add(SearchFields.ARTIFACT_FIELD);
		
	}

}
