/**
 * HelloWorldServiceImplMessageReceiver.java This file was auto-generated from
 * WSDL by the Apache Axis2 version: 0.94-SNAPSHOT Jan 10, 2006 (10:21:58 LKT)
 */
package org.apache.tuscany.samples.helloworldaxis;

/**
 * HelloWorldServiceImplMessageReceiver message receiver
 */
public class HelloWorldServiceImplMessageReceiver
    extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            //Inject the Message Context if it is asked for
            org.apache.axis2.engine.DependencyManager.configureBusinessLogicProvider(obj,
                msgContext, newMsgContext);

            HelloWorldServiceImplSkeleton skel = (HelloWorldServiceImplSkeleton) obj;

            //Out Envelop
            org.apache.axis2.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            String methodName;

            if ((op.getName() != null) &
                    ((methodName = op.getName().getLocalPart()) != null)) {
                if ("getGreetings".equals(methodName)) {
                    org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param5 =
                        null;

                    //doc style
                    param5 = skel.getGreetings((org.apache.tuscany.samples.helloworldaxis.GetGreetings) fromOM(
                                msgContext.getEnvelope().getBody()
                                          .getFirstElement(),
                                org.apache.tuscany.samples.helloworldaxis.GetGreetings.class));

                    envelope = toEnvelope(getSOAPFactory(msgContext), param5);
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axis2.om.OMElement toOM(
        org.apache.tuscany.samples.helloworldaxis.GetGreetings param) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axis2.om.impl.llom.builder.StAXOMBuilder builder = new org.apache.axis2.om.impl.llom.builder.StAXOMBuilder(org.apache.axis2.om.OMAbstractFactory.getOMFactory(),
                    param.getPullParser(
                        org.apache.tuscany.samples.helloworldaxis.GetGreetings.MY_QNAME));
            org.apache.axis2.om.OMElement documentElement = builder.getDocumentElement();
            ((org.apache.axis2.om.impl.OMNodeEx) documentElement).setParent(null); // remove the parent link

            return documentElement;
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axis2.soap.SOAPEnvelope toEnvelope(
        org.apache.axis2.soap.SOAPFactory factory,
        org.apache.tuscany.samples.helloworldaxis.GetGreetings param) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new org.apache.axis2.databinding.ADBSOAPModelBuilder(param.getPullParser(
                        org.apache.tuscany.samples.helloworldaxis.GetGreetings.MY_QNAME),
                    factory);

            return builder.getEnvelope();
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axis2.om.OMElement toOM(
        org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axis2.om.impl.llom.builder.StAXOMBuilder builder = new org.apache.axis2.om.impl.llom.builder.StAXOMBuilder(org.apache.axis2.om.OMAbstractFactory.getOMFactory(),
                    param.getPullParser(
                        org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.MY_QNAME));
            org.apache.axis2.om.OMElement documentElement = builder.getDocumentElement();
            ((org.apache.axis2.om.impl.OMNodeEx) documentElement).setParent(null); // remove the parent link

            return documentElement;
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axis2.soap.SOAPEnvelope toEnvelope(
        org.apache.axis2.soap.SOAPFactory factory,
        org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse param) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new org.apache.axis2.databinding.ADBSOAPModelBuilder(param.getPullParser(
                        org.apache.tuscany.samples.helloworldaxis.GetGreetingsResponse.MY_QNAME),
                    factory);

            return builder.getEnvelope();
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private java.lang.Object fromOM(org.apache.axis2.om.OMElement param,
        java.lang.Class type) {
        Object obj;

        try {
            java.lang.reflect.Method parseMethod = type.getMethod("parse",
                    new Class[] { javax.xml.stream.XMLStreamReader.class });
            obj = null;

            if (parseMethod != null) {
                obj = parseMethod.invoke(null,
                        new Object[] { param.getXMLStreamReaderWithoutCaching() });
            } else {
                //oops! we don't know how to deal with this. Perhaps the reflective one is a good choice here
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
}
