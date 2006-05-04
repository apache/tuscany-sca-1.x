package org.apache.tuscany.core.context.event;

import org.apache.tuscany.spi.event.Event;
import org.apache.tuscany.spi.context.CompositeContext;

/**
 * Implemented by runtime events associated with a module, e.g. lifecycle events
 *
 * @version $$Rev$$ $$Date$$
 */
public interface ModuleEvent extends Event {

    public CompositeContext getContext();

}
