package org.apache.tuscany.container.groovy;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.tuscany.container.groovy.injectors.SingletonInjector;
import org.apache.tuscany.container.groovy.mock.Greeting;
import org.apache.tuscany.core.component.scope.ModuleScopeContainer;
import org.apache.tuscany.spi.model.Scope;
import org.apache.tuscany.spi.wire.WireService;
import org.apache.tuscany.test.ArtifactFactory;

/**
 * @version $$Rev$$ $$Date$$
 */
public class PropertyTestCase extends TestCase {

    private static final String SCRIPT = "import org.apache.tuscany.container.groovy.mock.Greeting;"
            + "class Foo implements Greeting{"
            + "   String property;"
            + "   public String greet(String name){"
            + "       return property;  "
            + "   }"
            + "}";

    /**
     * Tests injecting a simple property type on a Groovy implementation instance
     */
    public void testPropertyInjection() throws Exception {
        ModuleScopeContainer scope = new ModuleScopeContainer(null);
        scope.start();
        List<Class<?>> services = new ArrayList<Class<?>>();
        services.add(Greeting.class);
        List<PropertyInjector> injectors = new ArrayList<PropertyInjector>();
        injectors.add(new SingletonInjector("property", "bar"));
        WireService wireService = ArtifactFactory.createWireService();
        GroovyAtomicComponent<Greeting> context = new GroovyAtomicComponent<Greeting>("source",
                                                                                      PropertyTestCase.SCRIPT,
                                                                                      services,
                                                                                      Scope.MODULE,
                                                                                      injectors,
                                                                                      null,
                                                                                      scope,
                                                                                      wireService);
        scope.register(context);
        Greeting greeting = context.getServiceInstance();
        assertEquals("bar", greeting.greet("foo"));
        scope.stop();
    }


}
