package org.apache.tuscany.sca.implementation.spring.invocation;

public class DefaultAnnotationProcessorExtensionPoint implements AnnotationProcessorExtensionPoint {
	
	// Contructor
	public DefaultAnnotationProcessorExtensionPoint() {
		
	}
	
	// By default SCA annotations for implementation.spring
	// will be supproted.
	public boolean isAnnotationSupported() {
		return true;
	}
}
