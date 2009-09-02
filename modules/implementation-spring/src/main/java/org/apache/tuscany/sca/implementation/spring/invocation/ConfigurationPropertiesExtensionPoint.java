package org.apache.tuscany.sca.implementation.spring.invocation;

public interface ConfigurationPropertiesExtensionPoint {
	
	boolean isAnnotationSupported();
	
	boolean isMultipleContextSupported();
	
	String getSupportedVersion();

}
