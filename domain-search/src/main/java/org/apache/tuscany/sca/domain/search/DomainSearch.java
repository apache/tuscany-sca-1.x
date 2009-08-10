package org.apache.tuscany.sca.domain.search;

import org.apache.lucene.search.Query;
import org.apache.tuscany.sca.contribution.Contribution;
import org.osoa.sca.annotations.Remotable;

@Remotable
public interface DomainSearch {
	
	boolean indexExists();
	
	Result[] parseAndSearch(String searchQuery, boolean highlight) throws Exception;
	
	Result[] search(Query searchQuery, boolean hightlight) throws Exception;
	
	String highlight(String field, String text, String searchQuery) throws Exception;
	
	/**
     * Notifies the listener that a contribution has been added.
     * 
     * @param repository The contribution repository 
     * @param contribution The new contribution
     */
    void contributionAdded(Contribution contribution);
    
    /**
     * Notifies the listener that a contribution has been removed.
     * 
     * @param repository The contribution repository 
     * @param contribution The removed contribution.
     */
    void contributionRemoved(Contribution contribution);
    
    /**
     * Notifies the listener that a contribution has been updated.
     * 
     * @param repository The contribution repository 
     * @param oldContribution The old contribution 
     * @param contribution The new contribution
     */
    void contributionUpdated(Contribution oldContribution, Contribution contribution);

}
