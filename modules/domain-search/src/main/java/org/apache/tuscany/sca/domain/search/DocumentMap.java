package org.apache.tuscany.sca.domain.search;

import java.util.HashMap;

import org.apache.tuscany.sca.domain.search.impl.Document;

public class DocumentMap extends HashMap<Object, Document> {

	private static final long serialVersionUID = -2402910290314939928L;
	
	@Override
	public Document get(Object key) {
		Document doc = super.get(key);
		
		if (doc == null) {
			doc = new Document();
			put(key, doc);
			
		}
		
		return doc;
		
	}
	
}
