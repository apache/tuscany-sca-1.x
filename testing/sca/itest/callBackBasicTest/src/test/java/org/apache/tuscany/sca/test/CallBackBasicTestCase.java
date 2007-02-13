package org.apache.tuscany.sca.test;

import junit.framework.TestCase;

import org.apache.tuscany.api.TuscanyContainer;
import org.osoa.sca.CurrentCompositeContext;
import org.osoa.sca.annotations.Reference;

public class CallBackBasicTestCase extends TestCase  {

    private CallBackBasicClient aCallBackClient;
	
    public void testCallBackBasic() {
    	aCallBackClient.run(); 
    }

    protected void setUp() throws Exception {
      super.setUp();
      
      TuscanyContainer.start();
      
      aCallBackClient = CurrentCompositeContext.getContext().locateService(CallBackBasicClient.class, "CallBackBasicClient");
    }
   
    @Override
    protected void tearDown() throws Exception {
    	TuscanyContainer.stop();
    	
    	super.tearDown();
    }
       
   
}
