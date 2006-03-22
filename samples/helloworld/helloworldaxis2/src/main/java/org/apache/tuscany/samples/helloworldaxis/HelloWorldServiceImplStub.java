
        /**
        * HelloWorldServiceImplStub.java
        *
        * This file was auto-generated from WSDL
        * by the Apache Axis2 version: 0.95-SNAPSHOT Mar 21, 2006 (05:13:26 EST)
        */
        package org.apache.tuscany.samples.helloworldaxis;

import org.apache.ws.commons.om.impl.llom.builder.StAXOMBuilder;

        

        /*
        *  HelloWorldServiceImplStub java implementation
        */

        public class HelloWorldServiceImplStub extends org.apache.axis2.client.Stub
        {
        //default axis home being null forces the system to pick up the mars from the axis2 library
        public static final java.lang.String AXIS2_HOME = null;
        protected static org.apache.axis2.description.AxisOperation[] _operations;
	
	private void populateAxisService(){

        //creating the Service
        _service = new org.apache.axis2.description.AxisService("HelloWorldServiceImpl");
	

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;
	


        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "getGreetings"));

	    

            _operations[0]=__operation;
            _service.addOperation(__operation);
        
        }



     public HelloWorldServiceImplStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint)
        throws java.lang.Exception {
       //To populate AxisService
       populateAxisService();
	
	
        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        
    }

    /**
     * Default Constructor
     */
    public HelloWorldServiceImplStub() throws java.lang.Exception {
        
                    this("http://localhost:8080/helloworldws-SNAPSHOT/services/HelloWorldService" );
                
    }

    /**
     * Constructor taking the traget endpoint
     */
    public HelloWorldServiceImplStub(java.lang.String targetEndpoint) throws java.lang.Exception {
        this(org.apache.axis2.context.ConfigurationContextFactory.createConfigurationContextFromFileSystem(AXIS2_HOME,null),
                targetEndpoint);
    }



        
                    /**
                    * Auto generated method signature
                    * @see org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl#getGreetings
                        * @param param10
                    
                    */
                    public org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse getGreetings(
                    org.apache.tuscany.samples.helloworldaxis.GetGreetings param10) throws java.rmi.RemoteException{

               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              

              // create SOAP envelope with that payload
              org.apache.ws.commons.soap.SOAPEnvelope env = null;
                    
                                    //Style is Doc.
                                    
                                    
                                                 env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), param10, optimizeContent(new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "getGreetings")));
                                            

        // create message context with that soap envelope
        org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext() ;
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.ws.commons.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                           java.lang.Object object = fromOM(getElement(_returnEnv,"doc"),org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.class);
                           return (org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse)object;
                    

                    }
            
	
    private javax.xml.namespace.QName[] opNameArray = null;
	private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
			return false;
		}
		for (int i = 0; i < opNameArray.length; i++) {
			if (opName.equals(opNameArray[i])) {
				return true;   
			}
		}
		return false;
	}
    //http://localhost:8080/helloworldws-SNAPSHOT/services/HelloWorldService
                    private  org.apache.ws.commons.om.OMElement  toOM(org.apache.tuscany.samples.helloworldaxis.GetGreetings param){
                        if (param instanceof org.apache.axis2.databinding.ADBBean){
                            StAXOMBuilder builder = new StAXOMBuilder
                            (org.apache.ws.commons.om.OMAbstractFactory.getOMFactory(), param.getPullParser(org.apache.tuscany.samples.helloworldaxis.GetGreetings.MY_QNAME));
                            org.apache.ws.commons.om.OMElement documentElement = builder.getDocumentElement();
                            ((org.apache.ws.commons.om.impl.OMNodeEx) documentElement).setParent(null); // remove the parent link
                            return documentElement;
                        }else{
                           
                           //todo finish this onece the bean serializer has the necessary methods
                            return null;
                        }
                    }

                    private  org.apache.ws.commons.soap.SOAPEnvelope toEnvelope(org.apache.ws.commons.soap.SOAPFactory factory, org.apache.tuscany.samples.helloworldaxis.GetGreetings param, boolean optimizeContent){
                        if (param instanceof org.apache.axis2.databinding.ADBBean){
                            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new
                                    org.apache.axis2.databinding.ADBSOAPModelBuilder(param.getPullParser(org.apache.tuscany.samples.helloworldaxis.GetGreetings.MY_QNAME),
                                                                                     factory);
                            return builder.getEnvelope();
                        }else{
                           
                           //todo finish this onece the bean serializer has the necessary methods
                            return null;
                        }
                    }
                
                    private  org.apache.ws.commons.om.OMElement  toOM(org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param){
                        if (param instanceof org.apache.axis2.databinding.ADBBean){
                            StAXOMBuilder builder = new StAXOMBuilder
                            (org.apache.ws.commons.om.OMAbstractFactory.getOMFactory(), param.getPullParser(org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.MY_QNAME));
                            org.apache.ws.commons.om.OMElement documentElement = builder.getDocumentElement();
                            ((org.apache.ws.commons.om.impl.OMNodeEx) documentElement).setParent(null); // remove the parent link
                            return documentElement;
                        }else{
                           
                           //todo finish this onece the bean serializer has the necessary methods
                            return null;
                        }
                    }

                    private  org.apache.ws.commons.soap.SOAPEnvelope toEnvelope(org.apache.ws.commons.soap.SOAPFactory factory, org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param, boolean optimizeContent){
                        if (param instanceof org.apache.axis2.databinding.ADBBean){
                            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new
                                    org.apache.axis2.databinding.ADBSOAPModelBuilder(param.getPullParser(org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.MY_QNAME),
                                                                                     factory);
                            return builder.getEnvelope();
                        }else{
                           
                           //todo finish this onece the bean serializer has the necessary methods
                            return null;
                        }
                    }
                

           /**
           *  get the default envelope
           */
           private org.apache.ws.commons.soap.SOAPEnvelope toEnvelope(org.apache.ws.commons.soap.SOAPFactory factory){
                return factory.getDefaultEnvelope();
           }


            private  java.lang.Object fromOM(org.apache.ws.commons.om.OMElement param,
            java.lang.Class type){

                try {
                       
                      if (org.apache.tuscany.samples.helloworldaxis.GetGreetings.class.equals(type)){
                           return org.apache.tuscany.samples.helloworldaxis.GetGreetings.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                      }
                              
                      if (org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.class.equals(type)){
                           return org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                      }
                              
                } catch (Exception e) {
                     throw new RuntimeException(e);
                }

                return null;
            }

        
   }



    