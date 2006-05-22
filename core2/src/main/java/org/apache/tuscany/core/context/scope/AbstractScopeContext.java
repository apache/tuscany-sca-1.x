package org.apache.tuscany.core.context.scope;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.tuscany.spi.AbstractLifecycle;
import org.apache.tuscany.spi.context.AtomicContext;
import org.apache.tuscany.spi.context.InstanceWrapper;
import org.apache.tuscany.spi.context.ScopeContext;
import org.apache.tuscany.spi.context.TargetException;
import org.apache.tuscany.spi.context.WorkContext;
import org.apache.tuscany.spi.event.Event;
import org.apache.tuscany.spi.event.EventFilter;
import org.apache.tuscany.spi.event.RuntimeEventListener;
import org.apache.tuscany.spi.event.TrueFilter;

/**
 * Implements functionality common to scope contexts.
 *
 * @version $Rev: 399161 $ $Date: 2006-05-02 23:09:37 -0700 (Tue, 02 May 2006) $
 */
public abstract class AbstractScopeContext extends AbstractLifecycle implements ScopeContext {
    private final String name;
    // The event context the scope container is associated with
    protected WorkContext workContext;
    private Map<EventFilter, List<RuntimeEventListener>> listeners;
    private static final EventFilter TRUE_FILTER = new TrueFilter();

    public AbstractScopeContext(String name, WorkContext workContext) {
        this.name = name;
        this.workContext = workContext;
    }

    public String getName() {
        return name;
    }

    public void setWorkContext(WorkContext workContext) {
        this.workContext = workContext;
    }

    public void addListener(RuntimeEventListener listener) {
        addListener(TRUE_FILTER, listener);
    }

    public void removeListener(RuntimeEventListener listener) {
        assert (listener != null) : "Listener cannot be null";
        synchronized (getListeners()) {
            for (List<RuntimeEventListener> currentList : getListeners().values()) {
                for (RuntimeEventListener current : currentList) {
                    if (current == listener) {
                        currentList.remove(current);
                        return;
                    }
                }
            }
        }
    }

    public void addListener(EventFilter filter, RuntimeEventListener listener) {
        assert (listener != null) : "Listener cannot be null";
        synchronized (getListeners()) {
            List<RuntimeEventListener> list = getListeners().get(filter);
            if (list == null) {
                list = new CopyOnWriteArrayList<RuntimeEventListener>();
                listeners.put(filter, list);
            }
            list.add(listener);
        }
    }

    public void publish(Event event) {
        assert(event != null): "Event object was null";
        for (Map.Entry<EventFilter, List<RuntimeEventListener>> entry : getListeners().entrySet()) {
            if (entry.getKey().match(event)) {
                for (RuntimeEventListener listener : entry.getValue()) {
                    listener.onEvent(event);
                }
            }
        }
    }

    public Object getInstance(AtomicContext context) throws TargetException {
        InstanceWrapper ctx = getInstanceContext(context);
        if (ctx != null) {
            if (ctx.getLifecycleState() == UNINITIALIZED) {
                ctx.start();
            }
            return ctx.getInstance();
        }
        return null;
    }

    protected Map<EventFilter, List<RuntimeEventListener>> getListeners() {
        if (listeners == null) {
            listeners = new ConcurrentHashMap<EventFilter, List<RuntimeEventListener>>();
        }
        return listeners;
    }

    protected void checkInit() {
        if (getLifecycleState() != RUNNING) {
            throw new IllegalStateException("Scope not running [" + getLifecycleState() + "]");
        }
    }

    protected WorkContext getEventContext() {
        return workContext;
    }

    public String toString() {
        return "ScopeContext [" + name + "] in state [" + super.toString() + ']';
    }
}
