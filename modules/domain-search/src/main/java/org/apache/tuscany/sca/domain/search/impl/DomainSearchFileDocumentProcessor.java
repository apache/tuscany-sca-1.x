package org.apache.tuscany.sca.domain.search.impl;

public class DomainSearchFileDocumentProcessor extends FileDocumentProcessor {

	private static final long serialVersionUID = -2725616937948969598L;

	public DomainSearchFileDocumentProcessor() {
		add(new ZipDocumentProcessor());
		add(new DefaultFileDocumentProcessor());
		
	}
	
}
