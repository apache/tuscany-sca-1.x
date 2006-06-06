package org.apache.tuscany.core.component.scope;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import org.apache.tuscany.core.component.WorkContextImpl;
import org.apache.tuscany.core.component.event.HttpSessionEnd;
import org.apache.tuscany.core.component.event.HttpSessionStart;
import org.apache.tuscany.core.injection.MethodEventInvoker;
import org.apache.tuscany.core.mock.factories.MockContextFactory;
import org.apache.tuscany.core.system.component.SystemAtomicComponent;
import org.apache.tuscany.spi.component.WorkContext;

/**
 * Verifies the scope container properly disposes resources and canbe restarted
 * @version $$Rev$$ $$Date$$
 */
public class HttpSessionScopeRestartTestCase extends TestCase {

    public void testRestart() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        HttpSessionScopeContainer scope = new HttpSessionScopeContainer(ctx);
        scope.start();
        MethodEventInvoker<Object> initInvoker = new MethodEventInvoker<Object>(HttpSessionScopeRestartTestCase.InitDestroyOnce.class.getMethod("init"));
        MethodEventInvoker<Object> destroyInvoker = new MethodEventInvoker<Object>(HttpSessionScopeRestartTestCase.InitDestroyOnce.class.getMethod("destroy"));
        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        interfaces.add(HttpSessionScopeRestartTestCase.InitDestroyOnce.class);
        SystemAtomicComponent context = MockContextFactory.createSystemAtomicContext("InitDestroy", scope, interfaces,
                HttpSessionScopeRestartTestCase.InitDestroyOnce.class, false, initInvoker, destroyInvoker, null, null);
        context.start();

        Object session = new Object();
        ctx.setIdentifier(HttpSessionScopeContainer.HTTP_IDENTIFIER, session);
        scope.onEvent(new HttpSessionStart(this, session));
        Object instance = context.getServiceInstance();
        assertSame(instance, context.getServiceInstance());

        scope.onEvent(new HttpSessionEnd(this, session));
        scope.stop();
        context.stop();

        scope.start();
        scope.onEvent(new HttpSessionStart(this, session));
        context.start();
        assertNotSame(instance, context.getServiceInstance());
        scope.onEvent(new HttpSessionEnd(this, session));
        scope.stop();
        context.stop();
    }

    public static class InitDestroyOnce {

        private boolean initialized;
        private boolean destroyed;

        public InitDestroyOnce() {
        }

        public void init() {
            if (!initialized) {
                initialized = true;
            } else {
                fail("Scope did not clean up properly - Init called more than once");
            }
        }

        public void destroy() {
            if (!destroyed) {
                destroyed = true;
            } else {
                fail("Scope did not clean up properly - Destroyed called more than once");
            }
        }

    }
}
