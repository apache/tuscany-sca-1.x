package org.apache.tuscany.sca.test;

import junit.framework.TestCase;

import org.apache.tuscany.api.SCAContainer;
import org.osoa.sca.CurrentCompositeContext;

public class CallBackBasicTestCase extends TestCase  {

    private CallBackBasicClient aCallBackClient;
	
    public void testCallBackBasic() {
    	aCallBackClient.run(); 
    }

    protected void setUp() throws Exception {
      SCAContainer.start("CallBackBasicTest.composite");
      
      aCallBackClient = CurrentCompositeContext.getContext().locateService(CallBackBasicClient.class, "CallBackBasicClient");
    }
   
    @Override
    protected void tearDown() throws Exception {
    	SCAContainer.stop();
    }
       
   
}
