package org.apache.tuscany.spi.context;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.tuscany.spi.wire.TargetInvoker;
import org.apache.tuscany.spi.wire.ReferenceWire;
import org.apache.tuscany.spi.wire.ServiceWire;

/**
 * Provides a runtime context for application artifacts configured as components
 *
 * @version $$Rev$$ $$Date$$
 */
public interface ComponentContext<T> extends Context<T> {

    /**
     * Returns a service associated with the given name
     *
     * @throws TargetException if an error occurs retrieving the service instance
     */
    Object getService(String name) throws TargetException;

    /**
     * Returns the service interfaces implemented by the context
     */
    List<Class<?>> getServiceInterfaces();

    /**
     * Adds a target-side wire. Target-side wire factories contain the invocation chains associated with the
     * destination service of a wire
     */
    void addServiceWire(ServiceWire wire);

    /**
     * Returns the target-side wire associated with the given service name
     */
    ServiceWire getServiceWire(String serviceName);

    /**
     * Adds a source-side wire for the given reference. Source-side wires contain the invocation chains for a
     * reference in the implementation associated with the instance wrapper created by this configuration.
     */
    void addReferenceWire(ReferenceWire wire);

    /**
     * Adds a set of source-side multiplicity wires for the given reference. Source-side wires contain the
     * invocation chains for a reference in the implementation associated with the instance wrapper created by
     * this configuration.
     */
    void addReferenceWires(Class<?> multiplicityClass, List<ReferenceWire> wires);

    /**
     * Returns a map of source-side wires for references. There may be 1..n wires per reference.
     */
    Map<String,List<ReferenceWire>> getReferenceWires();

    /**
     * Callback to create a {@link org.apache.tuscany.spi.wire.TargetInvoker} which dispatches to a service
     * contained by the context
     *
     * @param serviceName the name of the service
     * @param operation   the operation to invoke
     */
    TargetInvoker createTargetInvoker(String serviceName, Method operation);

}
