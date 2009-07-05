package org.apache.tuscany.sca.domain.search.impl;

import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;

public class ResultFactoryList extends LinkedList<ResultFactory<? extends Result>> implements ResultFactory<Result> {
	
	private static final long serialVersionUID = 6806221945324235828L;

	public ResultFactoryList() {
		// empty constructor
	}
	
	public Result createResult(Document document) {
		
		for (ResultFactory<? extends Result> resultFactory : this) {
			Result result = resultFactory.createResult(document);
			
			if (result != null) {
				return result;
			}
			
		}
		
		return null;
		
	}

	public Result createResult(String name) {
		return null;
	}

	public String getName(Document document) {
		
		for (ResultFactory<? extends Result> resultFactory : this) {
			String name = resultFactory.getName(document);
			
			if (name != null) {
				return name;
			}
			
		}
		
		return null;
		
	}

	public String getType() {
		return "";
	}

}
