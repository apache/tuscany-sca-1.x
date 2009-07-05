package org.apache.tuscany.sca.domain.search.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.lucene.document.Fieldable;

public class Document {

	private Hashtable<String, Hashtable<String, Fieldable>> fieldablesTable = new Hashtable<String, Hashtable<String, Fieldable>>();
	
	private Hashtable<String, LinkedList<Fieldable>> readerMap = new Hashtable<String, LinkedList<Fieldable>>();;

	public Document() {
		// empty constructor
	}

	public void add(Fieldable fieldable) {
		
		String strValue = fieldable.stringValue();
		
		if (strValue != null) {
			
			Hashtable<String, Fieldable> fieldables = this.fieldablesTable
			.get(fieldable.name());
			
			if (fieldables == null) {
				fieldables = new Hashtable<String, Fieldable>();
				this.fieldablesTable.put(fieldable.name(), fieldables);

			}
			
			fieldables.put(strValue, fieldable);
			
		} else {
			
			LinkedList<Fieldable> fieldables = this.readerMap
			.get(fieldable.name());
			
			if (fieldables == null) {
				fieldables = new LinkedList<Fieldable>();
				this.readerMap.put(fieldable.name(), fieldables);

			}
			
			fieldables.add(fieldable);
			
		}

	}
	
	public org.apache.lucene.document.Document createLuceneDocument() {
		org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
		
		for (Hashtable<String, Fieldable> fieldables : this.fieldablesTable.values()) {
			
			for (Fieldable fieldable : fieldables.values()) {
				doc.add(fieldable);
			}
			
		}
		
		for (LinkedList<Fieldable> fieldables : this.readerMap.values()) {
			
			for (Fieldable fieldable : fieldables) {
				doc.add(fieldable);
			}
			
		}
		
		return doc;
		
	}
	
	public Collection<String> getFieldValues(String field) {
		Hashtable<String, Fieldable> fieldables = this.fieldablesTable.get(field);
		
		if (fieldables != null) {
			return fieldables.keySet();
		}
		
		return Collections.emptyList();
			
	}
	
	public boolean containsField(String field) {
		return this.fieldablesTable.containsKey(field);
	}

}
