package org.apache.tuscany.sca.domain.search.impl;

public class FileContentResult extends ResultImpl {

	private static final long serialVersionUID = 950227008096181276L;
	
	private String content;
	
	public FileContentResult() {
		// empty constructor
	}
	
	public FileContentResult(String name) {
		super(name);
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

}
