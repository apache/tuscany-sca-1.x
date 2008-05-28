/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.tuscany.sca.databinding.jaxb;

import java.beans.Introspector;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

import org.apache.tuscany.sca.databinding.TransformationContext;
import org.apache.tuscany.sca.databinding.TransformationException;
import org.apache.tuscany.sca.databinding.impl.SimpleTypeMapperImpl;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.util.XMLType;
import org.w3c.dom.Node;

/**
 *
 * @version $Rev$ $Date$
 */
// FIXME: [rfeng] We probably should turn this into a pluggable system service
public class JAXBContextHelper {
    // TODO: Do we need to set them for source and target?
    public static final String JAXB_CLASSES = "jaxb.classes";

    public static final String JAXB_CONTEXT_PATH = "jaxb.contextPath";
    
    private static final JAXBContextCache cache = new JAXBContextCache();

    private JAXBContextHelper() {
    }

    public static JAXBContext createJAXBContext(Class<?> cls) throws JAXBException {
        return cache.getJAXBContext(cls);
    }
    
    public static JAXBContext createJAXBContext(TransformationContext tContext, boolean source) throws JAXBException {
        if (tContext == null)
            throw new TransformationException("JAXB context is not set for the transformation.");

        DataType<?> dataType = source ? tContext.getSourceDataType() : tContext.getTargetDataType();
        // FIXME: We should check the context path or classes
        // FIXME: What should we do if JAXB is an intermediate node?

        // String contextPath = null;
        JAXBContext context = null;
        Class<?> cls = getJavaType(dataType);

        context = cache.getJAXBContext(cls);

        if (context == null) {
            throw new TransformationException("JAXB context is not set for the transformation.");
        }
        return context;
    }
    
    public static Unmarshaller getUnmarshaller(JAXBContext context) throws JAXBException {
        return cache.getUnmarshaller(context);
    }
    
    public static Marshaller getMarshaller(JAXBContext context) throws JAXBException {
        return cache.getMarshaller(context);
    }

    @SuppressWarnings("unchecked")
    public static JAXBElement createJAXBElement(DataType dataType, Object value) {
        if (value instanceof JAXBElement) {
            return (JAXBElement)value;
        } else {
            Class type = dataType.getPhysical();
            Object logical = dataType.getLogical();
            QName elementName = JAXBDataBinding.ROOT_ELEMENT;
            if (logical instanceof XMLType) {
                XMLType xmlType = (XMLType)logical;
                QName element = xmlType.getElementName();
                if (element != null) {
                    elementName = element;
                } else {
                    /**
                     * Set the declared type to Object.class so that xsi:type
                     * will be produced
                     */
                    type = Object.class;
                }
            } else {
                type = Object.class;
            }
            return new JAXBElement(elementName, type, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static Object createReturnValue(DataType dataType, Object value) {
        Class<?> cls = getJavaType(dataType);
        if (cls == JAXBElement.class) {
            return createJAXBElement(dataType, value);
        } else {
            if (value instanceof JAXBElement) {
                return ((JAXBElement)value).getValue();
            } else {
                return value;
            }
        }
    }

    public static Class<?> getJavaType(DataType<?> dataType) {
        if (dataType == null) {
            return null;
        }
        Class type = dataType.getPhysical();
        if (JAXBElement.class.isAssignableFrom(type)) {
            type = Object.class;
        }
        if (type == Object.class && dataType.getLogical() instanceof XMLType) {
            XMLType xType = (XMLType)dataType.getLogical();
            Class javaType = SimpleTypeMapperImpl.getJavaType(xType.getTypeName());
            if (javaType != null) {
                type = javaType;
            }
        }
        return type;
    }

    public static XMLType getXmlTypeName(Class<?> javaType) {
        if (javaType.isInterface()) {
            // JAXB doesn't support interfaces
            return null;
        }
        String namespace = null;
        String name = null;
        Package pkg = javaType.getPackage();
        if (pkg != null) {
            XmlSchema schema = pkg.getAnnotation(XmlSchema.class);
            if (schema != null) {
                namespace = schema.namespace();
            }
        }

        QName elementQName = null;
        QName typeQName = null;
        XmlRootElement rootElement = javaType.getAnnotation(XmlRootElement.class);
        if (rootElement != null) {
            String elementName = rootElement.name();
            String elementNamespace = rootElement.namespace();
            if (elementNamespace.equals("##default")) {
                elementNamespace = namespace;
            }
            if (elementName.equals("##default")) {
                elementName = Introspector.decapitalize(javaType.getSimpleName());
            }
            elementQName = new QName(elementNamespace, elementName);
        }
        XmlType type = javaType.getAnnotation(XmlType.class);
        if (type != null) {
            String typeNamespace = type.namespace();
            String typeName = type.name();

            if (typeNamespace.equals("##default")) {
                // namespace is from the package
                typeNamespace = namespace;
            }

            if (typeName.equals("##default")) {
                typeName = Introspector.decapitalize(javaType.getSimpleName());
            }
            typeQName = new QName(typeNamespace, typeName);
        } else {
            XmlEnum xmlEnum = javaType.getAnnotation(XmlEnum.class);
            if (xmlEnum != null) {
                name = Introspector.decapitalize(javaType.getSimpleName());
                typeQName = new QName(namespace, name);
            }
        }
        if (elementQName == null && typeQName == null) {
            return null;
        }
        return new XMLType(elementQName, typeQName);
    }
    
    public static Node generateSchema(JAXBContext context) throws Exception {
        SchemaOutputResolverImpl resolver = new SchemaOutputResolverImpl();
        context.generateSchema(resolver);
        return resolver.getSchema();
    }

    public static class SchemaOutputResolverImpl extends SchemaOutputResolver {
        private DOMResult result = new DOMResult();

        @Override
        public Result createOutput(String ns, String file) throws IOException {
            result.setSystemId("sca:dom");
            return result;
        }

        public Node getSchema() {
            return result != null ? result.getNode() : null;
        }

    }
    

}