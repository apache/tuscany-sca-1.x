/**
 * HelloWorldServiceImplTest.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 0.94-SNAPSHOT Jan 10, 2006 (10:21:58 LKT)
 */
package org.apache.tuscany.samples.helloworldaxis;


/*
 *  HelloWorldServiceImplTest Junit test case
*/
public class HelloWorldServiceImplTest extends junit.framework.TestCase {
  
//FIXME we don't want to run this test as part of the main build, AXIS2 is not necessarily set up to run it    
    /**
     * Auto generated test method
     */
    public void testgetGreetings() throws java.lang.Exception {
//        org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplStub stub =
//            new org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplStub("http://localhost:8080/axis2/services/HelloWorldServiceImplService"); //the default implementation should point to the right endpoint
//
//        org.apache.tuscany.samples.helloworldaxis.GetGreetings param14 = (org.apache.tuscany.samples.helloworldaxis.GetGreetings) getTestObject(org.apache.tuscany.samples.helloworldaxis.GetGreetings.class);
//        param14.setIn0("Hello, World!");
//
//        // todo Fill in the param14 here
//        assertNotNull(stub.getGreetings(param14));
    }

    /**
     * Auto generated test method
     */
    public void testStartgetGreetings() throws java.lang.Exception {
//        org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplStub stub =
//            new org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplStub("http://localhost:8080/axis2/services/HelloWorldServiceImplService");
//        org.apache.tuscany.samples.helloworldaxis.GetGreetings param14 = (org.apache.tuscany.samples.helloworldaxis.GetGreetings) getTestObject(org.apache.tuscany.samples.helloworldaxis.GetGreetings.class);
//        param14.setIn0("Hello, World!");
//
//        // todo Fill in the param14 here
//        stub.startgetGreetings(param14, new tempCallbackN1000C());
    }

    //Create an ADBBean and provide it as the test object
    public org.apache.axis2.databinding.ADBBean getTestObject(
        java.lang.Class type) throws Exception {
        return (org.apache.axis2.databinding.ADBBean) type.newInstance();
    }

    private class tempCallbackN1000C
        extends org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplCallbackHandler {
        public tempCallbackN1000C() {
            super(null);
        }

        public void receiveResultgetGreetings(
            org.apache.axis2.client.async.AsyncResult result) {
            assertNotNull(result.getResponseEnvelope().getBody()
                                .getFirstElement());
        }

        public void receiveErrorgetGreetings(java.lang.Exception e) {
            fail();
        }
    }
}
