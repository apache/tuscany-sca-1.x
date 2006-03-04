/**
 * HelloWorldServiceImplSkeleton.java This file was auto-generated from WSDL by
 * the Apache Axis2 version: 0.94-SNAPSHOT Jan 10, 2006 (10:21:58 LKT)
 */
package org.apache.tuscany.samples.helloworldaxis;

/**
 * HelloWorldServiceImplSkeleton java skeleton for the axisService
 */
public class HelloWorldServiceImplSkeleton
{
    /**
     * Auto generated method signature
     *
     * @param greetings
     */
    public org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse getGreetings(
                                                                                       org.apache.tuscany.samples.helloworldaxis.GetGreetings greetings )
    {
        GetGreetingsResponse response = new GetGreetingsResponse();
        response.setGetGreetingsReturn( "Greeting: " + greetings.getIn0() );
        return response;
    }
}
