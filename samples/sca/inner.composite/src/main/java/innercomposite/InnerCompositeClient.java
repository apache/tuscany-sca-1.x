package innercomposite;

import org.apache.tuscany.api.SCAContainer;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Simple client program that invokes the components that we wired together.
 *
 * @version $Rev$ $Date$
 */
public class InnerCompositeClient {

    public static void main(String[] args) throws Exception {
    	SCAContainer.start();
    	
        CompositeContext context = CurrentCompositeContext.getContext();

        Source source = context.locateService(Source.class, "SourceComponent/InnerSourceService");
        System.out.println("Main thread " + Thread.currentThread());
        source.clientMethod("Client.main");
        Thread.sleep(500);
        
        SCAContainer.stop();
    }
}
