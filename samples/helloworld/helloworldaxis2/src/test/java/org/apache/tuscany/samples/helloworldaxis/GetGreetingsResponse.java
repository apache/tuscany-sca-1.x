/**
 * GetGreetingsResponse.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: #axisVersion# #today#
 */
package org.apache.tuscany.samples.helloworldaxis;

/**
 * GetGreetingsResponse bean class
 */
public class GetGreetingsResponse
    implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org",
            "getGreetingsResponse", "ns1");

    /** field for GetGreetingsReturn */
    private java.lang.String localGetGreetingsReturn;

    /**
     * Auto generated getter method
     *
     * @return java.lang.String
     */
    public java.lang.String getGetGreetingsReturn() {
        return localGetGreetingsReturn;
    }

    /**
     * Auto generated setter method
     *
     * @param param GetGreetingsReturn
     */
    public void setGetGreetingsReturn(java.lang.String param) {
        this.localGetGreetingsReturn = param;
    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(
        javax.xml.namespace.QName qName) {
        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName(
                "http://helloworldaxis.samples.tuscany.apache.org",
                "getGreetingsReturn"));
        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localGetGreetingsReturn));

        return org.apache.axis2.databinding.utils.ADBPullParser.createPullParser(qName,
            elementList.toArray(), attribList.toArray());
    }

    /**
     * static method to create the object Note -  This is not complete
     */
    public static GetGreetingsResponse parse(
        javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        GetGreetingsResponse object = new GetGreetingsResponse();

        try {
            int event = reader.getEventType();
            int count = 0;
            int argumentCount = 1;
            boolean done = false;

            //event better be a START_ELEMENT. if not we should go up to the start element here
            while (!reader.isStartElement()) {
                event = reader.next();
            }

            while (!done) {
                if (javax.xml.stream.XMLStreamConstants.START_ELEMENT == event) {
                    if ("getGreetingsReturn".equals(reader.getLocalName())) {
                        String content = reader.getElementText();
                        object.setGetGreetingsReturn(org.apache.axis2.databinding.utils.ConverterUtil.convertTostring(
                                content));
                        count++;
                    }
                }

                if (argumentCount == count) {
                    done = true;
                }

                if (!done) {
                    event = reader.next();
                }
            }
        } catch (javax.xml.stream.XMLStreamException e) {
            throw new java.lang.Exception(e);
        }

        return object;
    }
}
