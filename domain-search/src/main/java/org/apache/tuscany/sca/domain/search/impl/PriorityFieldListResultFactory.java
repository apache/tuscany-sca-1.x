package org.apache.tuscany.sca.domain.search.impl;

import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class PriorityFieldListResultFactory extends LinkedList<String> implements ResultFactory<Result> {
	
	private static final long serialVersionUID = 6806221945324235828L;

	public PriorityFieldListResultFactory() {
		// empty constructor
	}
	
	public Result createResult(Document document) {
		
		for (String field : this) {
			String value = document.get(field);
			
			if (value != null) {
				return new ResultImpl(field, value);
			}
			
		}
		
		return null;
		
	}

	public Result createResult(String field, String value) {
		return new ResultImpl(field, value);
	}

}
