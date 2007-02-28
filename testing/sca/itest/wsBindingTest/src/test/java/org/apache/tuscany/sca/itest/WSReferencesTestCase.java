package org.apache.tuscany.sca.itest;


import helloworld.HelloWorldService;
import junit.framework.Assert;

import org.apache.tuscany.test.SCATestCase;
import org.apache.tuscany.test.SCATestCaseRunner;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Test case for binding.ws references 
 */
@SuppressWarnings("deprecation")
public class WSReferencesTestCase extends SCATestCase {

    private SCATestCaseRunner server;
    private CompositeContext compositeContext;

    @Override
    protected void setUp() throws Exception {
    	setApplicationSCDL("WSBindingTest.composite");
        super.setUp();
        
        compositeContext = CurrentCompositeContext.getContext();

        server =  new SCATestCaseRunner(HelloWorldServerTestCase.class);
        server.setUp();
    }
    
    public void testWSClient() throws Exception {
        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientOld");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("Hi Petra", msg);
    }
    
    public void testWSClientSimplest() throws Exception {
        HelloWorldService helloWorldService = compositeContext.locateService(HelloWorldService.class, "HelloworldClientSimplest");
        String msg = helloWorldService.getGreetings("Petra");
        Assert.assertEquals("Hi Petra", msg);
    }

    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();
    	server.tearDown();
    }

}
