package org.apache.tuscany.sca.implementation.spring.invocation;

public class DefaultConfigurationPropertiesExtensionPoint implements ConfigurationPropertiesExtensionPoint {
	
	// Contructor
	public DefaultConfigurationPropertiesExtensionPoint() {
		
	}
	
	// By default SCA annotations for implementation.spring
	// will be supproted for Tuscany.
	public boolean isAnnotationSupported() {
		return true;
	}
	
	// By default multiple application context implementation.spring
	// will be supproted for Tuscany.
	public boolean isMultipleContextSupported() {
		return true;
	}
	
	// By defauly all the Spring version are supported for 
	// Tuscany.
	public String getSupportedVersion() {
		
		// Customize the code here for a limited version
		// support
		/* 
		return "2.5.5"; 
		*/		
		return "ANY";
	}
}
