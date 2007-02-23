package org.apache.tuscany.core.loader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import junit.framework.TestCase;

import org.apache.tuscany.core.injection.JNDIObjectFactory;
import org.apache.tuscany.spi.model.PropertyValue;
import org.easymock.EasyMock;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @version $Rev$ $Date$
 */
public class JNDIPropertyFactoryTestCase extends TestCase {

    public void testCreate() throws Exception {
        String old = System.getProperty(Context.INITIAL_CONTEXT_FACTORY);
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, MockInitialContextFactory.class.getName());
            JNDIPropertyFactory factory = new JNDIPropertyFactory();
            Element element = EasyMock.createMock(Element.class);
            EasyMock.expect(element.getTextContent()).andReturn("foo");
            EasyMock.replay(element);
            Document doc = EasyMock.createMock(Document.class);
            EasyMock.expect(doc.getDocumentElement()).andReturn(element);
            EasyMock.replay(doc);
            PropertyValue<?> value = new MockPropertyValue<Type>();
            List<Document> docList = new ArrayList<Document>();
            docList.add(doc);
            value.setValue(docList);
            JNDIObjectFactory<?> jndiFactory = (JNDIObjectFactory<?>) factory.createObjectFactory(null, value);
            assertEquals("bar", jndiFactory.getInstance());
        } finally {
            System.clearProperty(Context.INITIAL_CONTEXT_FACTORY);
            if (old != null) {
                System.setProperty(Context.INITIAL_CONTEXT_FACTORY, old);
            }
        }

    }

    private class MockPropertyValue<T> extends PropertyValue<T> {

    }

    public static class MockInitialContextFactory implements InitialContextFactory {
        public MockInitialContextFactory() {
        }

        public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
            Context context = EasyMock.createMock(Context.class);
            EasyMock.expect(context.lookup("foo")).andReturn("bar");
            EasyMock.replay(context);
            return context;
        }
    }

}
