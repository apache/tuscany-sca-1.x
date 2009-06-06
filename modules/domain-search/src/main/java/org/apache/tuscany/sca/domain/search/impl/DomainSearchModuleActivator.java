package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.core.ModuleActivator;

public class DomainSearchModuleActivator implements ModuleActivator {
	
	public DomainSearchModuleActivator() {
		// empty constructor
	}

	public void start(ExtensionPointRegistry registry) {
		registry.addExtensionPoint(new DefaultSearchContributionListenerExtensionPoint());
	}

	public void stop(ExtensionPointRegistry registry) {
		// does nothing
	}

}
