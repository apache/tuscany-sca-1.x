package org.apache.tuscany.sca.test;

import junit.framework.TestCase;

import org.apache.tuscany.test.SCATestCase;
import org.osoa.sca.CurrentCompositeContext;
import org.osoa.sca.annotations.Reference;

public class CallBackIdTest extends SCATestCase  {

    private CallBackIdClient aCallBackClient;
	
    public void testCallBackBasic() {
    	aCallBackClient.run(); 
    }

    protected void setUp() throws Exception {
      super.setUp();
      aCallBackClient = CurrentCompositeContext.getContext().locateService(CallBackIdClient.class, "CallBackIdClient");
    }
   
       
   
}
