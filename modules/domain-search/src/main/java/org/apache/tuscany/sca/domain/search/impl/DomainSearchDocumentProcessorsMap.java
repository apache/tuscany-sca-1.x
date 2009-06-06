package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ComponentType;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.domain.search.DocumentProcessorsMap;

public class DomainSearchDocumentProcessorsMap extends DocumentProcessorsMap {
	
	private static final long serialVersionUID = -4651637686945322606L;

	public DomainSearchDocumentProcessorsMap() {
		addDocumentProcessor(Contribution.class, new ContributionDocumentProcessor());
		addDocumentProcessor(Artifact.class, new ArtifactDocumentProcessor());
		addDocumentProcessor(Property.class, new PropertyDocumentProcessor());
		addDocumentProcessor(ComponentType.class, new ComponentTypeDocumentProcessor());
		addDocumentProcessor(Binding.class, new BindingDocumentProcessor());
		addDocumentProcessor(Component.class, new ComponentDocumentProcessor());
		addDocumentProcessor(Composite.class, new CompositeDocumentProcessor());
		
	}

}
