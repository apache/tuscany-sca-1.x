package org.apache.tuscany.core.component.scope;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import org.apache.tuscany.core.component.WorkContextImpl;
import org.apache.tuscany.core.component.event.RequestEnd;
import org.apache.tuscany.core.injection.MethodEventInvoker;
import org.apache.tuscany.core.mock.factories.MockContextFactory;
import org.apache.tuscany.core.system.component.SystemAtomicComponent;
import org.apache.tuscany.spi.component.WorkContext;

/**
 * Verifies the scope container properly disposes resources and canbe restarted
 *
 * @version $$Rev$$ $$Date$$
 */
public class RequestScopeRestartTestCase extends TestCase {

    public void testRestart() throws Exception {
        WorkContext ctx = new WorkContextImpl();
        RequestScopeContainer scope = new RequestScopeContainer(ctx);
        scope.start();
        MethodEventInvoker<Object> initInvoker = new MethodEventInvoker<Object>(RequestScopeRestartTestCase.InitDestroyOnce.class.getMethod("init"));
        MethodEventInvoker<Object> destroyInvoker = new MethodEventInvoker<Object>(RequestScopeRestartTestCase.InitDestroyOnce.class.getMethod("destroy"));
        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        interfaces.add(RequestScopeRestartTestCase.InitDestroyOnce.class);
        SystemAtomicComponent context = MockContextFactory.createSystemAtomicContext("InitDestroy", scope, interfaces,
                RequestScopeRestartTestCase.InitDestroyOnce.class, false, initInvoker, destroyInvoker, null, null);
        context.start();

        Object instance = context.getServiceInstance();
        assertSame(instance, context.getServiceInstance());

        scope.onEvent(new RequestEnd(this));
        scope.stop();
        context.stop();

        scope.start();
        context.start();
        assertNotSame(instance, context.getServiceInstance());
        scope.onEvent(new RequestEnd(this));
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
