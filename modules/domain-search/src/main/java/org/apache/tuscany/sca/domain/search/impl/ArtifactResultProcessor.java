package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;
import org.apache.tuscany.sca.domain.search.ResultProcessor;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

public class ArtifactResultProcessor implements ResultProcessor {
	
	public Result process(Document document, Query query, Result result) {
		
		if (result instanceof ArtifactResult) {
			String location = document.get(SearchFields.LOCATION_FIELD);
			((ArtifactResult) result).setLocation(location);
			
		}
		
		return result;
		
	}
	
	public static class ArtifactResultFactory implements ResultFactory<ArtifactResult> {
		
		public ArtifactResultFactory() {
			// empty constructor
		}

		public ArtifactResult createResult(String name) {
			return new ArtifactResult(name);
		}

		public String getName(Document document) {
			return document.get(SearchFields.ARTIFACT_FIELD);
		}

		public String getType() {
			return SearchFields.ARTIFACT_FIELD;
		}

		public ArtifactResult createResult(Document document) {
			String name = getName(document);
			
			if (name != null) {
				return createResult(name);
			}
			
			return null;
			
		}
		
	}

}
