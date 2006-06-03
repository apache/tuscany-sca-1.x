package org.apache.tuscany.core.component.scope;

import junit.framework.TestCase;
import org.apache.tuscany.core.component.WorkContextImpl;
import org.apache.tuscany.spi.component.ScopeContext;
import org.apache.tuscany.spi.component.ScopeRegistry;
import org.apache.tuscany.spi.component.WorkContext;
import org.apache.tuscany.spi.model.Scope;

/**
 * @version $$Rev$$ $$Date$$
 */
public class ScopeRegistryTestCase extends TestCase {
    public void testScopeContextCreation() throws Exception {
        WorkContext workContext = new WorkContextImpl();
        ScopeRegistry scopeRegistry = new ScopeRegistryImpl(workContext);
        scopeRegistry.registerFactory(Scope.REQUEST, new RequestScopeObjectFactory());
        scopeRegistry.registerFactory(Scope.SESSION, new HttpSessionScopeObjectFactory());
        ScopeContext request = scopeRegistry.getScopeContext(Scope.REQUEST);
        assertTrue(request instanceof RequestScopeContext);
        assertSame(request, scopeRegistry.getScopeContext(Scope.REQUEST));
        ScopeContext session = scopeRegistry.getScopeContext(Scope.SESSION);
        assertTrue(session instanceof HttpSessionScopeContext);
        assertSame(session, scopeRegistry.getScopeContext(Scope.SESSION));
        assertNotSame(request, session);
    }
}
