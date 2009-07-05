package org.apache.tuscany.sca.domain.search.impl;

public class ArtifactResult extends ResultImpl {

	private static final long serialVersionUID = 7418036586148544103L;
	
	private String location;
	
	public ArtifactResult() {
		// empty constructor
	}

	public ArtifactResult(String name) {
		super(name);
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return this.location;
	}
	
}
