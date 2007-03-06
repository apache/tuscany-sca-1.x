package org.apache.tuscany.databinding.sdo2om;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.Assert;

import org.apache.axiom.om.OMElement;
import org.apache.tuscany.spi.idl.XMLType;
import org.apache.tuscany.spi.model.DataType;

import commonj.sdo.DataObject;
import commonj.sdo.helper.XMLDocument;

/**
 * 
 */
public class XMLDocument2OMElementTestCase extends SDOTransformerTestCaseBase {

    @Override
    protected DataType<?> getSourceDataType() {
        return new DataType<XMLType>(XMLDocument.class.getName(), XMLDocument.class, new XMLType(ORDER_QNAME, null));
    }

    @Override
    protected DataType<?> getTargetDataType() {
        return new DataType<XMLType>(OMElement.class.getName(), OMElement.class, new XMLType(ORDER_QNAME, null));
    }

    public final void testTransform() throws XMLStreamException {
        XMLDocument document =
            helperContext.getXMLHelper().createDocument(dataObject,
                                                        ORDER_QNAME.getNamespaceURI(),
                                                        ORDER_QNAME.getLocalPart());
        OMElement element = new XMLDocument2OMElement().transform(document, context);
        Assert.assertEquals(ORDER_QNAME.getNamespaceURI(), element.getNamespace().getNamespaceURI());
        Assert.assertEquals(ORDER_QNAME.getLocalPart(), element.getLocalName());
        StringWriter writer = new StringWriter();
        element.serialize(writer);
    }

}
