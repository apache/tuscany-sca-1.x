package org.apache.tuscany.sca.domain.search.impl;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;
import org.apache.tuscany.sca.domain.search.ResultProcessor;

public class ResultProcessorList extends LinkedList<ResultProcessor> implements
		ResultProcessor {

	private static final long serialVersionUID = 7147307292452694895L;

	private HashMap<String, ResultHashMap> resultRoots = new HashMap<String, ResultHashMap>();

	private ResultFactory<? extends Result> resultFactory;
	
	public ResultProcessorList(ResultFactory<? extends Result> resultFactory) {
		this.resultFactory = resultFactory;
	}

	public Result process(Document document, Result result) {

		if (result == null) {
			result = this.resultFactory.createResult(document);
			
			if (result == null) {
				return null;
			}
			
		}

		ResultHashMap resultHashMap;

		String parent = document.get(SearchFields.PARENT_FIELD);

		if (parent == null) {
			resultHashMap = new ResultHashMap(result);
			resultRoots.put(result.getValue(), resultHashMap);

		} else {

			ParentField parentField = new ParentField(parent);
			HashMap<String, ResultHashMap> current = this.resultRoots;
			//Result currentResult = null;
			int elementsCount = parentField.getElementsCount();

			for (int i = 0; i < elementsCount; i++) {
				String actualName = parentField.getElementName(i);
				String type = parentField.getElementType(i);

				if (actualName.length() > 0) {
					ResultHashMap actualResultHashMap = current.get(actualName);

					if (actualResultHashMap == null) {
						ResultHashMap aux = new ResultHashMap(type, parentField
								.getElementName(i));

						current.put(aux.result.getValue(), aux);
//						if (current.put(aux.result.getValue(), aux) == null
//								&& currentResult != null) {
//							currentResult.addContent(aux.result);
//						}

						current = aux;
						//currentResult = aux.result;

					} else {
						current = actualResultHashMap;
						//currentResult = actualResultHashMap.result;

					}

				}

			}

			resultHashMap = current.get(result.getValue());

			if (resultHashMap == null) {
				resultHashMap = new ResultHashMap(result);
				current.put(result.getValue(), resultHashMap);

//				if (currentResult != null) {
//					currentResult.addContent(result);
//				}

			}

		}

		for (ResultProcessor processor : this) {
			result = processor.process(document, result);
		}

		resultHashMap.result = result;

		return result;

	}
	
	private static void addContentsToResult(ResultHashMap resultHashMap) {
		
		for (ResultHashMap actual : resultHashMap.values()) {
			addContentsToResult(actual);
			
			resultHashMap.result.addContent(actual.result);
			
		}
		
	}

	public Result[] createResultRoots() {
		int size = this.resultRoots.size();

		if (size == 0) {
			return new Result[0];
		}

		Result[] res = new Result[size];

		int i = 0;
		for (ResultHashMap resultHashMap : this.resultRoots.values()) {
			
			addContentsToResult(resultHashMap);
			res[i++] = resultHashMap.result;
			
		}
		
		this.resultRoots.clear();

		return res;

	}

	private Result createResult(String field, String value) {
		Result result = this.resultFactory.createResult(field, value);
		
		if (result != null) {
			return result;
		}

		throw new IllegalArgumentException();

	}

	private class ResultHashMap extends HashMap<String, ResultHashMap> {

		private static final long serialVersionUID = 7982561264440904411L;

		Result result;

		ResultHashMap(String type, String name) {
			this(createResult(type, name));
		}

		ResultHashMap(Result result) {
			this.result = result;
		}

	}

}
