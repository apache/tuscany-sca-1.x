package org.apache.tuscany.spi.policy;

import org.apache.tuscany.spi.builder.BuilderException;
import org.apache.tuscany.spi.model.Reference;
import org.apache.tuscany.spi.model.Service;
import org.apache.tuscany.spi.wire.ReferenceWire;
import org.apache.tuscany.spi.wire.ServiceWire;

/**
 * A registry for policy builders that dispatches to the appropriate builder when converting an assembly to
 * runtime artifacts. Policy builders operate on either a source- or target-side wires.
 *
 * @version $Rev$ $Date$
 */
public interface PolicyBuilderRegistry {

    public static final int INITIAL = 0;
    public static final int EXTENSION = 1;
    public static final int FINAL = 2;

    /**
     * Registers a target-side policy builder. Called by extensions to register their builders.
     *
     * @param phase   the phase hwne the builder must be run
     * @param builder the builder to register
     */
    public void registerTargetBuilder(int phase, TargetPolicyBuilder builder);

    /**
     * Registers a source-side policy builder. Called by extensions to register their builders.
     *
     * @param phase   the phase hwne the builder must be run
     * @param builder the builder to register
     */
    public void registerSourceBuilder(int phase, SourcePolicyBuilder builder);

    /**
     * Evaluates source-side policy metadata for reference and updates the curresponding collection of wire
     * configurations
     *
     * @throws BuilderException
     */
    public void buildSource(Reference reference, ReferenceWire wire) throws BuilderException;

    /**
     * Evaluates target-side policy metadata for configured reference and updates the curresponding collection
     * of wire configurations
     *
     * @throws BuilderException
     */
    public void buildTarget(Service service, ServiceWire wire) throws BuilderException;
}
