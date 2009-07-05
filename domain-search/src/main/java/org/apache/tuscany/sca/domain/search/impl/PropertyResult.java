package org.apache.tuscany.sca.domain.search.impl;

public class PropertyResult extends ResultImpl {

	private static final long serialVersionUID = 5744678253277504237L;
	
	private String[] values;

	public PropertyResult(String name, String... values) {
		super(name);
		
		this.values = values;
		
	}
	
	public String[] getValues() {
		return this.values.clone();
	}
	
}
