/**
 * HelloWorldServiceImplStub.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 0.94-SNAPSHOT Jan 10, 2006 (10:21:58 LKT)
 */
package org.apache.tuscany.samples.helloworldaxis;

/*
 *  HelloWorldServiceImplStub java implementation
 */
public class HelloWorldServiceImplStub
    extends org.apache.axis2.client.Stub
{
    //default axis home being null forces the system to pick up the mars from the axis2 library
    public static final String AXIS2_HOME = null;

    protected static org.apache.axis2.description.AxisOperation[] _operations;

    static
    {
        //creating the Service
        _service = new org.apache.axis2.description.AxisService( "HelloWorldServiceImpl" );

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;
        _operations = new org.apache.axis2.description.OutInAxisOperation[1];

        __operation = new org.apache.axis2.description.OutInAxisOperation();
        __operation.setName( new javax.xml.namespace.QName( "http://helloworldaxis.samples.tuscany.apache.org",
                                                            "getGreetings" ) );
        _operations[0] = __operation;
        _service.addOperation( __operation );
    }

    public HelloWorldServiceImplStub( org.apache.axis2.context.ConfigurationContext configurationContext,
                                     String targetEndpoint )
        throws java.lang.Exception
    {
        _serviceClient = new org.apache.axis2.client.ServiceClient( configurationContext, _service );
        _serviceClient.getOptions().setTo( new org.apache.axis2.addressing.EndpointReference( targetEndpoint ) );
    }

    /**
     * Default Constructor
     */
    public HelloWorldServiceImplStub()
        throws java.lang.Exception
    {
        this( "http://localhost:8080/axis2/services/HelloWorldServiceImplService" );
    }

    /**
     * Constructor taking the traget endpoint
     */
    public HelloWorldServiceImplStub( String targetEndpoint )
        throws java.lang.Exception
    {
        this( new org.apache.axis2.context.ConfigurationContextFactory()
            .createConfigurationContextFromFileSystem( AXIS2_HOME ), targetEndpoint );
    }

    /**
     * Auto generated method signature
     *
     * @param param10
     *
     * @see org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl#getGreetings
     */
    public org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse getGreetings(
                                                                                       org.apache.tuscany.samples.helloworldaxis.GetGreetings param10 )
        throws java.rmi.RemoteException
    {
        org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient( _operations[0]
            .getName() );
        _operationClient.getOptions().setSoapAction( "" );
        _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault( true );

        // create SOAP envelope with that payload
        org.apache.axis2.soap.SOAPEnvelope env = null;

        //Style is Doc.
        env = toEnvelope( getFactory( _options.getSoapVersionURI() ), param10 );

        // create message context with that soap envelope
        org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
        _messageContext.setEnvelope( env );

        // add the message contxt to the operation client
        _operationClient.addMessageContext( _messageContext );

        //set the options hierarchy
        _options.setParent( _operationClient.getOptions() );
        _operationClient.setOptions( _options );

        //execute the operation client
        _operationClient.execute( true );

        org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
            .getMessageContext( org.apache.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE );
        org.apache.axis2.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

        java.lang.Object object = fromOM( getElement( _returnEnv, "doc" ),
                                          org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.class );

        return (org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse) object;
    }

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param param10
     *
     * @see org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl#startgetGreetings
     */
    public void startgetGreetings(
                                  org.apache.tuscany.samples.helloworldaxis.GetGreetings param10,
                                  final org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplCallbackHandler callback )
        throws java.rmi.RemoteException
    {
        org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient( _operations[0]
            .getName() );
        _operationClient.getOptions().setSoapAction( "" );
        _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault( true );

        // create SOAP envelope with that payload
        org.apache.axis2.soap.SOAPEnvelope env;

        //Style is Doc.
        env = toEnvelope( getFactory( _options.getSoapVersionURI() ), param10 );

        // create message context with that soap envelope
        org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
        _messageContext.setEnvelope( env );

        // add the message contxt to the operation client
        _operationClient.addMessageContext( _messageContext );

        //set the options hierarchy
        _options.setParent( _operationClient.getOptions() );
        _operationClient.setOptions( _options );

        _operationClient.setCallback( new org.apache.axis2.client.async.Callback()
        {
            public void onComplete( org.apache.axis2.client.async.AsyncResult result )
            {
                java.lang.Object object = fromOM( getElement( result.getResponseEnvelope(), "doc" ),
                                                  org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.class );
                callback
                    .receiveResultgetGreetings( (org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse) object );
            }

            public void onError( java.lang.Exception e )
            {
                callback.receiveErrorgetGreetings( e );
            }
        } );

        //execute the operation client
        _operationClient.execute( true );
    }

    //http://localhost:8080/axis2/services/HelloWorldServiceImplService
    private org.apache.axis2.om.OMElement toOM( org.apache.tuscany.samples.helloworldaxis.GetGreetings param )
    {
        if ( param instanceof org.apache.axis2.databinding.ADBBean )
        {
            org.apache.axis2.om.impl.llom.builder.StAXOMBuilder builder = new org.apache.axis2.om.impl.llom.builder.StAXOMBuilder(
                                                                                                                                   org.apache.axis2.om.OMAbstractFactory
                                                                                                                                       .getOMFactory(),
                                                                                                                                   param
                                                                                                                                       .getPullParser( org.apache.tuscany.samples.helloworldaxis.GetGreetings.MY_QNAME ) );
            org.apache.axis2.om.OMElement documentElement = builder.getDocumentElement();
            ( (org.apache.axis2.om.impl.OMNodeEx) documentElement ).setParent( null ); // remove the parent link

            return documentElement;
        }
        else
        {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axis2.soap.SOAPEnvelope toEnvelope( org.apache.axis2.soap.SOAPFactory factory,
                                                          org.apache.tuscany.samples.helloworldaxis.GetGreetings param )
    {
        if ( param instanceof org.apache.axis2.databinding.ADBBean )
        {
            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new org.apache.axis2.databinding.ADBSOAPModelBuilder(
                                                                                                                             param
                                                                                                                                 .getPullParser( org.apache.tuscany.samples.helloworldaxis.GetGreetings.MY_QNAME ),
                                                                                                                             factory );

            return builder.getEnvelope();
        }
        else
        {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axis2.om.OMElement toOM( org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param )
    {
        if ( param instanceof org.apache.axis2.databinding.ADBBean )
        {
            org.apache.axis2.om.impl.llom.builder.StAXOMBuilder builder = new org.apache.axis2.om.impl.llom.builder.StAXOMBuilder(
                                                                                                                                   org.apache.axis2.om.OMAbstractFactory
                                                                                                                                       .getOMFactory(),
                                                                                                                                   param
                                                                                                                                       .getPullParser( org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.MY_QNAME ) );
            org.apache.axis2.om.OMElement documentElement = builder.getDocumentElement();
            ( (org.apache.axis2.om.impl.OMNodeEx) documentElement ).setParent( null ); // remove the parent link

            return documentElement;
        }
        else
        {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axis2.soap.SOAPEnvelope toEnvelope(
                                                          org.apache.axis2.soap.SOAPFactory factory,
                                                          org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param )
    {
        if ( param instanceof org.apache.axis2.databinding.ADBBean )
        {
            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new org.apache.axis2.databinding.ADBSOAPModelBuilder(
                                                                                                                             param
                                                                                                                                 .getPullParser( org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.MY_QNAME ),
                                                                                                                             factory );

            return builder.getEnvelope();
        }
        else
        {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private java.lang.Object fromOM( org.apache.axis2.om.OMElement param, java.lang.Class type )
    {
        Object obj;

        try
        {
            java.lang.reflect.Method parseMethod = type
                .getMethod( "parse", new Class[] { javax.xml.stream.XMLStreamReader.class } );
            obj = null;

            if ( parseMethod != null )
            {
                obj = parseMethod.invoke( null, new Object[] { param.getXMLStreamReaderWithoutCaching() } );
            }
            else
            {
                //oops! we don't know how to deal with this. Perhaps the reflective one is a good choice here
            }
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

        return obj;
    }
}
