package org.apache.tuscany.container.groovy;

import groovy.lang.GroovyObject;
import org.apache.tuscany.spi.AbstractLifecycle;
import org.apache.tuscany.spi.CoreRuntimeException;
import org.apache.tuscany.spi.ObjectCreationException;
import org.apache.tuscany.spi.component.InstanceWrapper;

/**
 * Wraps a <code>GroovyObject</code> so it can be managed by a <code>ScopeContainer</code>
 * 
 * @version $$Rev$$ $$Date$$
 */
public class GroovyInstanceWrapper extends AbstractLifecycle implements InstanceWrapper {

    private int lifecycleState = UNINITIALIZED;
    private GroovyAtomicComponent context;
    private GroovyObject groovyObject;


    public GroovyInstanceWrapper(GroovyAtomicComponent context, GroovyObject groovyObject) {
        this.context = context;
        this.groovyObject = groovyObject;
    }

    public Object getInstance() {
        return groovyObject;
    }

    public int getLifecycleState() {
        return lifecycleState;
    }

    public void start() throws CoreRuntimeException {
        try {
            context.init(groovyObject);
            lifecycleState = RUNNING;
        } catch (ObjectCreationException e) {
            lifecycleState = ERROR;
            throw e;
        }
    }

    public void stop() throws CoreRuntimeException {
        groovyObject = null;
        lifecycleState = STOPPED;
    }

}
