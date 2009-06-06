package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.service.ContributionRepository;
import org.apache.tuscany.sca.domain.search.SearchContributionListenerExtensionPoint;

public class DefaultSearchContributionListenerExtensionPoint implements SearchContributionListenerExtensionPoint {

	public void contributionAdded(ContributionRepository repository,
			Contribution contribution) {
		System.out.println("contributionAdded:");
		System.out.println(repository);
		System.out.println(contribution);
		
	}

	public void contributionRemoved(ContributionRepository repository,
			Contribution contribution) {
		System.out.println("contributionRemoved:");
		System.out.println(repository);
		System.out.println(contribution);
		
	}

	public void contributionUpdated(ContributionRepository repository,
			Contribution oldContribution, Contribution contribution) {
		
		System.out.println("contributionUpdated:");
		System.out.println(repository);
		System.out.println(oldContribution);
		System.out.println(contribution);
		
	}

}
