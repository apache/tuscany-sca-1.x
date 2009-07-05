package org.apache.tuscany.sca.domain.search.impl;

public class ReferenceResult extends ResultImpl {

	private static final long serialVersionUID = -2846199490484466090L;
	
	private String[] operations;
	
	private String[] callbackOperations;

	public ReferenceResult(String name, String[] operations, String[] callbackOperations) {
		super(name);
		
		this.operations = operations;
		this.callbackOperations = callbackOperations;
		
	}
	
	public String[] getOperations() {
		return this.operations.clone();
	}
	
	public String[] getCallbackOperations() { 
		return this.callbackOperations.clone();
	}

}
