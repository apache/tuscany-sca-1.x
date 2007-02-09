package sample.client;

import org.osoa.sca.ComponentContext;
import org.osoa.sca.ServiceReference;
import org.osoa.sca.CallableReference;
import sample.HelloService;
import sample.HelloCallback;

/**
 * @version $Rev$ $Date$
 */
public class BasicClient {
    private ComponentContext context;

    public void useComponentDirectly() {
        HelloService helloService = context.getService(HelloService.class, "helloService");
        helloService.hello("World");
    }

    public void useComponentViaReference() {
        ServiceReference<HelloService> ref = context.getServiceReference(HelloService.class, "helloService");
        HelloService helloService = ref.getService();
        helloService.hello("World");

        ServiceReference<HelloService> ref2 = context.cast(helloService);
    }

    public void useReferenceDirectly() {
        HelloService helloService = context.getService(HelloService.class, "helloReference");
        helloService.hello("World");
    }

    public void useReferenceViaReference() {
        ServiceReference<HelloService> ref = context.getServiceReference(HelloService.class, "helloReference");
        HelloService helloService = ref.getService();
        helloService.hello("World");

        ServiceReference<HelloService> ref2 = context.cast(helloService);
    }

    public void castCallback() {
        HelloCallback callback = null;
        CallableReference<HelloCallback> cb = context.cast(callback);
    }
}
