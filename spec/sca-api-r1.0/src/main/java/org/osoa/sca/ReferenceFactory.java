package org.osoa.sca;

/**
 * @version $Rev$ $Date$
 */
public interface ReferenceFactory<B> {
    /**
     * Returns the name of the reference associated with this factory.
     *
     * @return the name of the reference associated with this factory
     */
    String getName();

    /**
     * Create a new ServiceReference for the reference associated with this factory.
     *
     * @return a new ServiceReference that can be used access the reference
     */
    ServiceReference<B> newReference();
}
