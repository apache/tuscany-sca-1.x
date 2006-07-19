package org.apache.tuscany.core.component.scope;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.component.TargetException;
import org.apache.tuscany.spi.component.WorkContext;
import org.apache.tuscany.spi.event.Event;
import org.apache.tuscany.spi.model.Scope;

import org.apache.tuscany.core.component.event.HttpSessionEnd;
import org.apache.tuscany.core.component.event.HttpSessionStart;

/**
 * A scope context which manages atomic component instances keyed on HTTP session
 *
 * @version $Rev: 415162 $ $Date: 2006-06-18 11:19:43 -0700 (Sun, 18 Jun 2006) $
 */
public class HttpSessionScopeContainer extends AbstractScopeContainer {

    public static final Object HTTP_IDENTIFIER = new Object();

    private final Map<AtomicComponent, Map<Object, InstanceWrapper>> contexts;
    private final Map<Object, List<InstanceWrapper>> destroyQueues;

    public HttpSessionScopeContainer() {
        this(null);
    }

    public HttpSessionScopeContainer(WorkContext workContext) {
        super("Session Scope", workContext);
        contexts = new ConcurrentHashMap<AtomicComponent, Map<Object, InstanceWrapper>>();
        destroyQueues = new ConcurrentHashMap<Object, List<InstanceWrapper>>();
    }

    public Scope getScope() {
        return Scope.SESSION;
    }

    public void onEvent(Event event) {
        checkInit();
        if (event instanceof HttpSessionStart) {
            Object key = ((HttpSessionStart) event).getId();
            for (Map.Entry<AtomicComponent, Map<Object, InstanceWrapper>> entry : contexts.entrySet()) {
                if (entry.getKey().isEagerInit()) {
                    getInstance(entry.getKey(), key);
                }
            }
        } else if (event instanceof HttpSessionEnd) {
            shutdownInstances(((HttpSessionEnd) event).getId());
        }
    }

    public synchronized void start() {
        if (lifecycleState != UNINITIALIZED && lifecycleState != STOPPED) {
            throw new IllegalStateException("Scope must be in UNINITIALIZED or STOPPED state [" + lifecycleState + "]");
        }
        lifecycleState = RUNNING;
    }

    public synchronized void stop() {
        contexts.clear();
        synchronized (destroyQueues) {
            destroyQueues.clear();
        }
        lifecycleState = STOPPED;
    }

    public void register(AtomicComponent component) {
        contexts.put(component, new ConcurrentHashMap<Object, InstanceWrapper>());
        component.addListener(this);

    }

    protected InstanceWrapper getInstanceWrapper(AtomicComponent component) throws TargetException {
        Object key = workContext.getIdentifier(HTTP_IDENTIFIER);
        assert key != null : "HTTP session key not bound in work component";
        return getInstance(component, key);
    }

    private InstanceWrapper getInstance(AtomicComponent component, Object key) {
        Map<Object, InstanceWrapper> wrappers = contexts.get(component);
        InstanceWrapper ctx = wrappers.get(key);
        if (ctx == null) {
            ctx = new InstanceWrapperImpl(component, component.createInstance());
            ctx.start();
            wrappers.put(key, ctx);
            List<InstanceWrapper> destroyQueue = destroyQueues.get(key);
            if (destroyQueue == null) {
                destroyQueue = new ArrayList<InstanceWrapper>();
                destroyQueues.put(key, destroyQueue);
            }
            synchronized (destroyQueue) {
                destroyQueue.add(ctx);
            }
        }
        return ctx;

    }

    private void shutdownInstances(Object key) {
        List<InstanceWrapper> destroyQueue = destroyQueues.remove(key);
        if (destroyQueue != null) {
            for (Map<Object, InstanceWrapper> map : contexts.values()) {
                map.remove(key);
            }
            ListIterator<InstanceWrapper> iter = destroyQueue.listIterator(destroyQueue.size());
            synchronized (destroyQueue) {
                while (iter.hasPrevious()) {
                    try {
                        iter.previous().stop();
                    } catch (TargetException e) {
                        // TODO send a monitoring event
                    }
                }
            }
        }
    }

}
