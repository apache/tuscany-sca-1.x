/**
 * HelloWorldServiceImplCallbackHandler.java This file was auto-generated from
 * WSDL by the Apache Axis2 version: 0.94-SNAPSHOT Jan 10, 2006 (10:21:58 LKT)
 */
package org.apache.tuscany.samples.helloworldaxis;

/**
 * HelloWorldServiceImplCallbackHandler Callback class
 */
public abstract class HelloWorldServiceImplCallbackHandler {
    private Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the
     * NonBlocking  Web service call is finished and appropreate method of
     * this CallBack is called.
     *
     * @param clientData Object mechanism by which the user can pass in user
     *        data that will be avilable at the time this callback is called.
     */
    public HelloWorldServiceImplCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * auto generated Axis2 call back method for getGreetings method
     */
    public void receiveResultgetGreetings(
        org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param9) {
        //Fill here with the code to handle the response
    }

    /**
     * auto generated Axis2 Error handler
     */
    public void receiveErrorgetGreetings(java.lang.Exception e) {
        //Fill here with the code to handle the exception
    }
}
