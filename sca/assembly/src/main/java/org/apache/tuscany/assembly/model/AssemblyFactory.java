package org.apache.tuscany.assembly.model;

/**
 * A factory for the assembly model
 */
public interface AssemblyFactory {

	/**
	 * Create a new abstract property.
	 * @return a new abstract property
	 */
	AbstractProperty createAbstractProperty();
	
	/**
	 * Create a new abstract reference.
	 * @return a new abstract reference
	 */
	AbstractReference createAbstractReference();
	
	/**
	 * Create a new abstract service.
	 * @return a new abstract service
	 */
	AbstractService createAbstractService();
	
	/**
	 * Create a new binding.
	 * @return a new binding
	 */
	Binding createBinding();
	
	/**
	 * Create a new component.
	 * @return a new component
	 */
	Component createComponent();
	
	/**
	 * Create a new component property.
	 * @return a new component property
	 */
	ComponentProperty createComponentProperty();
	
	/**
	 * Create a new component reference.
	 * @return a new component reference
	 */
	ComponentReference createComponentReference();
	
	/**
	 * Create a new component service.
	 * @return a new component service
	 */
	ComponentService createComponentService();
	
	/**
	 * Create a new component type
	 * @return a new component type
	 */
	ComponentType createComponentType();
	
	/**
	 * Create a new composite.
	 * @return a new composite
	 */
	Composite createComposite();
	
	/**
	 * Create a new composite reference.
	 * @return a new composite reference
	 */
	CompositeReference createCompositeReference();
	
	/**
	 * Create a new composite service.
	 * @return a new composite service
	 */
	CompositeService createCompositeService();
	
	/**
	 * Create a new constraining type.
	 * @return a new constraining type
	 */
	ConstrainingType createConstrainingType();
	
	/**
	 * Create a new property.
	 * @return a new property
	 */
	Property createProperty();
	
	/**
	 * Create a new reference.
	 * @return a new reference
	 */
	Reference createReference();
	
	/**
	 * Create a new service.
	 * @return a new service
	 */
	Service createService();
	
	/**
	 * Create a new wire.
	 * @return a new wire
	 */
	Wire createWire();

}
