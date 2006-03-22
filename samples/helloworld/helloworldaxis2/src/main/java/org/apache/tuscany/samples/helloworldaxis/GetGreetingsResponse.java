
            /**
            * GetGreetingsResponse.java
            *
            * This file was auto-generated from WSDL
            * by the Apache Axis2 version: #axisVersion# #today#
            */

            package org.apache.tuscany.samples.helloworldaxis;
            /**
            *  GetGreetingsResponse bean class
            */
        
        public  class GetGreetingsResponse
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://helloworldaxis.samples.tuscany.apache.org",
                "getGreetingsResponse",
                "ns1");

            


            /**
            * field for GetGreetingsReturn
            */

            protected java.lang.String localGetGreetingsReturn ;
           
           

           /**
           * Auto generated getter method
           * @return java.lang.String
           */
           public  java.lang.String getGetGreetingsReturn(){
               return localGetGreetingsReturn;
           }

           
            
                    /**
                   * Auto generated setter method
                   * @param param GetGreetingsReturn
                   */
                   public void setGetGreetingsReturn(java.lang.String param){
                    
                   this.localGetGreetingsReturn=param;
                   }
                

        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName){


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                             elementList.add(new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org",
                                                                      "getGreetingsReturn"));
                            
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGetGreetingsReturn));
                                

                return org.apache.axis2.databinding.utils.ADBPullParser.createPullParser(qName, elementList.toArray(), attribList.toArray());
            
            

        }

        /**
        * utility method to http://www.w3.org/2001/XMLSchema-instance
        */

    /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{
        /**
        * static method to create the object
        */
        public static GetGreetingsResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
        GetGreetingsResponse object = new GetGreetingsResponse();
        try {
        int event = reader.getEventType();

       //event better be a START_ELEMENT. if not we should go up to the start element here
        while (event!= javax.xml.stream.XMLStreamReader.START_ELEMENT) {
            event = reader.next();
        }

        
        if (!MY_QNAME.equals(reader.getName())){
                    throw new Exception("Wrong QName");
        }
        
                org.apache.axis2.databinding.utils.SimpleElementReaderStateMachine stateMachine1
                  = new org.apache.axis2.databinding.utils.SimpleElementReaderStateMachine();
                javax.xml.namespace.QName startQname1 = new javax.xml.namespace.QName(
                                     "http://helloworldaxis.samples.tuscany.apache.org",
                                    "getGreetingsReturn");
                stateMachine1.setElementNameToTest(startQname1);
                stateMachine1.read(reader);
                object.setGetGreetingsReturn(
                  
                     org.apache.axis2.databinding.utils.ConverterUtil.convertTostring(
                           stateMachine1.getText()));
                      
        } catch (javax.xml.stream.XMLStreamException e) {
             throw new java.lang.Exception(e);
        }

        return object;
        }
        }//end of factory class

        }
    