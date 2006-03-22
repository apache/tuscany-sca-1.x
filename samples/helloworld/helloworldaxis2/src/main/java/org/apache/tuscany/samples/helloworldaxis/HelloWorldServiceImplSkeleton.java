
    /**
     * HelloWorldServiceImplSkeleton.java
     *
     * This file was auto-generated from WSDL
     * by the Apache Axis2 version: 0.95-SNAPSHOT Mar 21, 2006 (05:13:26 EST)
     */
    package org.apache.tuscany.samples.helloworldaxis;
    /**
     *  HelloWorldServiceImplSkeleton java skeleton for the axisService
     */
    public class HelloWorldServiceImplSkeleton {
     
		 
        /**
         * Auto generated method signature
         
          * @param param0
         
         */
        public  org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse getGreetings
                  (org.apache.tuscany.samples.helloworldaxis.GetGreetings greetings ) throws Exception {
                
               
         GetGreetingsResponse response = new GetGreetingsResponse();
        response.setGetGreetingsReturn( "Greeting: " + greetings.getIn0() );
        return response;
        }
     
    }
    