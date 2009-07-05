package org.apache.tuscany.sca.domain.search.impl;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;
import org.apache.tuscany.sca.domain.search.ResultProcessor;

public class ResultProcessorList extends LinkedList<ResultProcessor> implements ResultProcessor {

	private static final long serialVersionUID = 7147307292452694895L;
	
	private ResultFactoryList resultFactoryList;
	
	private HashMap<String, ResultHashMap> resultRoots = new HashMap<String, ResultHashMap>();
	
	public ResultProcessorList(ResultFactoryList resultFactoryList) {
		this.resultFactoryList = resultFactoryList;
	}
	
	public Result process(Document document, Query query, Result result) {
		
		if (result == null) {
			result = this.resultFactoryList.createResult(document);
		}
		
		if (result == null) {
			return null;
		}
		
		ResultHashMap resultHashMap;
		
		String parent = document.get(SearchFields.PARENT_FIELD);
		
		if (parent == null) {
			resultHashMap = new ResultHashMap(result);
			resultRoots.put(result.getName(), resultHashMap);
			
		} else {
			
			ParentField parentField = new ParentField(parent);
			HashMap<String, ResultHashMap> current = this.resultRoots;
			Result currentResult = null;
			int elementsCount = parentField.getElementsCount();
			
			for (int i = 0 ; i < elementsCount ; i++) {
				String actualURI = parentField.getElementURI(i);
				String type = parentField.getElementType(i);
				
				if (actualURI.length() > 0) {
					ResultHashMap actualResultHashMap = current.get(actualURI);
					
					if (actualResultHashMap == null) {
						ResultHashMap aux = new ResultHashMap(type, parentField.getElementName(i));
						
						if (current.put(actualURI, aux) == null && currentResult != null) {
							currentResult.addContent(aux.result);
						}
						
						current = aux;
						currentResult = aux.result;
						
					} else {
						current = actualResultHashMap;
						currentResult = actualResultHashMap.result;
						
					}
					
				}
				
			}
			
			resultHashMap = current.get(result.getName());
			
			if (resultHashMap == null) {
				resultHashMap = new ResultHashMap(result);
				current.put(result.getName(), resultHashMap);
				
				if (currentResult != null) {
					currentResult.addContent(result);
				}
				
			}
			
		}
		
		for (ResultProcessor processor : this) {
			result = processor.process(document, query, result);
		}
		
		resultHashMap.result = result;
		
		return result;
		
	}
	
	public Result[] getResultRoots() {
		int size = this.resultRoots.size();
		
		if (size == 0) {
			return new Result[0];
		}
		
		Result[] res = new Result[size];
		
		int i = 0;
		for (ResultHashMap resultHashMap : this.resultRoots.values()) {
			res[i++] = resultHashMap.result;
		}
		
		return res;
		
	}
	
	private Result createResult(String type, String name) {
		
		for (ResultFactory<? extends Result> actualResultFactory : this.resultFactoryList) {
			
			if (type.equals(actualResultFactory.getType())) {
				return actualResultFactory.createResult(name);
			}
			
		}
		
		throw new IllegalArgumentException();
		
	}
	
	private class ResultHashMap extends HashMap<String, ResultHashMap> {

		private static final long serialVersionUID = 7982561264440904411L;
		
		Result result;
		
		ResultHashMap(String type, String name) {
			this.result = createResult(type, name);
		}
		
		ResultHashMap(Result result) {
			this.result = result;
		}
		
	}

}
