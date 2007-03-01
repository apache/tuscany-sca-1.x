package org.apache.tuscany.sca.test;

import junit.framework.TestCase;

import org.apache.tuscany.api.SCAContainer;
import org.osoa.sca.CurrentCompositeContext;

public class CallBackIdTest extends TestCase  {

    private CallBackIdClient aCallBackClient;
	
    public void testCallBackBasic() {
    	aCallBackClient.run(); 
    }

    protected void setUp() throws Exception {
    	SCAContainer.start("CallBackIdClient.composite");
    	
    	aCallBackClient = CurrentCompositeContext.getContext().locateService(CallBackIdClient.class, "CallBackIdClient");
    }
    
    protected void tearDown() throws Exception {
    	SCAContainer.stop();
    }
}
