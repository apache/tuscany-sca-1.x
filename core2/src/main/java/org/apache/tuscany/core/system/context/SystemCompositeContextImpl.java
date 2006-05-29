package org.apache.tuscany.core.system.context;

import org.apache.tuscany.core.context.AbstractCompositeContext;
import org.apache.tuscany.core.context.AutowireContext;
import org.apache.tuscany.core.context.AutowireResolutionException;
import org.apache.tuscany.spi.context.CompositeContext;

/**
 * Implements an composite context for system components. In addition, it implements an autowire policy A
 * system context may contain child composite contexts but an entry point in a child context will only be
 * outwardly accessible if there is an entry point that exposes it configured in the top-level system
 * context.
 *
 * @version $Rev: 399161 $ $Date: 2006-05-02 23:09:37 -0700 (Tue, 02 May 2006) $
 */
public class SystemCompositeContextImpl<S> extends AbstractCompositeContext<S> implements SystemCompositeContext<S> {

    public SystemCompositeContextImpl(String name, CompositeContext parent, AutowireContext autowireContext) {
        super(name, parent, autowireContext);
    }

    public <S, I extends S> void registerJavaObject(String name, Class<S> service, I instance) throws ObjectRegistrationException {
        registerContext(new SystemSingletonAtomicContext<S, I>(name, this, service, instance));
    }

    public <T> T resolveInstance(Class<T> instanceInterface) throws AutowireResolutionException {
        if (instanceInterface.isAssignableFrom(SystemCompositeContext.class)) {
            return instanceInterface.cast(this);
        } else {
            return super.resolveInstance(instanceInterface);
        }
    }

}
