package sample.client;

import org.osoa.sca.ComponentContext;
import sample.HelloService;

/**
 * @version $Rev$ $Date$
 */
public class ReallyBasicClient {
    /**
     * <composite name="impl2">
     *    <component name="comp2a">
     *      <implementation.unmanaged/>
     *      <reference name="helloService" target="hello"/>
     *    </component>
     *
     *    <component name="hello">
     *      <implementation.java class="sample.HelloServiceImpl"/>
     *    </component>
     * </composite>
     */
    public static void main(String[] args) {
        ComponentContext context = getComponentContext();

        // access a service through its reference
        HelloService helloService = context.getService(HelloService.class, "helloService");
        helloService.hello("World");
    }

    public static ComponentContext getComponentContext() {
        // implementation defined mechanism to retrieve appropriate ComponentContext
        throw new UnsupportedOperationException();
    }
}
