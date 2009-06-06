package org.apache.tuscany.sca.domain.search;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.service.ContributionRepository;

public interface SearchContributionListenerExtensionPoint {
	
	/**
     * Notifies the listener that a contribution has been added.
     * 
     * @param repository The contribution repository 
     * @param contribution The new contribution
     */
    void contributionAdded(ContributionRepository repository, Contribution contribution);
    
    /**
     * Notifies the listener that a contribution has been removed.
     * 
     * @param repository The contribution repository 
     * @param contribution The removed contribution.
     */
    void contributionRemoved(ContributionRepository repository, Contribution contribution);
    
    /**
     * Notifies the listener that a contribution has been updated.
     * 
     * @param repository The contribution repository 
     * @param oldContribution The old contribution 
     * @param contribution The new contribution
     */
    void contributionUpdated(ContributionRepository repository, Contribution oldContribution, Contribution contribution);

}
