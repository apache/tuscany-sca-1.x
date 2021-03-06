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
package org.apache.tuscany.sca.implementation.xquery.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.NamespaceResolver;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

import org.apache.tuscany.sca.assembly.AssemblyFactory;
import org.apache.tuscany.sca.assembly.Multiplicity;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.contribution.resolver.ClassReference;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.implementation.xquery.XQueryImplementation;
import org.apache.tuscany.sca.interfacedef.InvalidInterfaceException;
import org.apache.tuscany.sca.interfacedef.java.JavaInterface;
import org.apache.tuscany.sca.interfacedef.java.JavaInterfaceContract;
import org.apache.tuscany.sca.interfacedef.java.JavaInterfaceFactory;
import org.apache.tuscany.sca.interfacedef.util.JavaXMLMapper;

/**
 * This class introspects an XQuery file and extracts out of it
 * all implemented service, references and properties
 * It also creates expression extensions for each operation
 * in the implemented services
 * @version $Rev$ $Date$
 */
public class XQueryIntrospector {

    private static final String SCA_SERVICE_PREFIX = "scaservice:java/";
    private static final String SCA_REFERENCE_PREFIX = "scareference:java/";
    private static final String SCA_PROPERTY_JAVA_PREFIX = "scaproperty:java/";
    private static final String SCA_PROPERTY_XML_PREFIX = "scaproperty:xml/";

    private AssemblyFactory assemblyFactory;
    private JavaInterfaceFactory javaFactory;

    public XQueryIntrospector(AssemblyFactory assemblyFactory, JavaInterfaceFactory javaFactory) {
        super();
        this.assemblyFactory = assemblyFactory;
        this.javaFactory = javaFactory;
    }

    public boolean introspect(XQueryImplementation xqueryImplementation, ModelResolver resolver) throws ContributionResolveException {
        
        String xqExpression = null;
        try {
            URL url = new URL(xqueryImplementation.getLocationURL());
            xqExpression = loadXQExpression(url);
        } catch (FileNotFoundException e) {
            throw new ContributionResolveException(e);
        } catch (IOException e) {
            throw new ContributionResolveException(e);
        }

        if (xqExpression == null) {
            return false;
        }

        xqueryImplementation.setXqExpression(xqExpression);

        xqExpression += "\r\n<dummy></dummy>";

        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = null;
        try {
            exp = sqc.compileQuery(xqExpression);
        } catch (XPathException e) {
            throw new ContributionResolveException(e);
        }

        if (exp == null) {
            return false;
        }
        xqueryImplementation.getCompiledExpressionsCache().put(xqExpression, exp);

        try {
            introspectServicesAndReferences(xqueryImplementation, exp, resolver);
        } catch (ClassNotFoundException e) {
            throw new ContributionResolveException(e);
        } catch (InvalidInterfaceException e) {
            throw new ContributionResolveException(e);
        }

        fillExpressionExtensions(xqueryImplementation);

        return true;
    }

    /**
     * Loads the XQuery expression from the location that is provided with the implementation
     */
    private String loadXQExpression(URL locationURL) throws FileNotFoundException, IOException {
    	
        InputStream xqResourceStream = locationURL.openStream();

        if (xqResourceStream == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = 0;
        while ((i = xqResourceStream.read()) >= 0) {
            baos.write(i);
        }
        xqResourceStream.close();
        baos.flush();
        baos.close();

        String xqExpression = baos.toString();

        return xqExpression;
    }
    
    private Class<?> resolveClass(ModelResolver resolver, String className) throws ClassNotFoundException {
        ClassReference classReference = new ClassReference(className);
        classReference = resolver.resolveModel(ClassReference.class, classReference);
        Class<?> javaClass = classReference.getJavaClass();
        if (javaClass == null) {
            throw new ClassNotFoundException(className);
        } else {
            return javaClass;
        }
    }

    /**
     * From the compiled XQuery expression get all namespaces and see if they
     * are services, references or properties declarations
     */
    private void introspectServicesAndReferences(XQueryImplementation xqueryImplementation, XQueryExpression exp, ModelResolver resolver)
        throws ClassNotFoundException, InvalidInterfaceException {
        NamespaceResolver namespaceResolver = exp.getStaticContext().getNamespaceResolver();
        Iterator declaredPrefixesIterator = namespaceResolver.iteratePrefixes();
        while (declaredPrefixesIterator.hasNext()) {
            String prefix = (String)declaredPrefixesIterator.next();
            String uri = namespaceResolver.getURIForPrefix(prefix, false);
            if (uri.startsWith(SCA_SERVICE_PREFIX)) {
                String serviceName = prefix;
                String className = uri.substring(SCA_SERVICE_PREFIX.length());
                Class<?> interfaze = resolveClass(resolver, className);
                Service theService = createService(interfaze, serviceName);
                xqueryImplementation.getServices().add(theService);
            } else if (uri.startsWith(SCA_REFERENCE_PREFIX)) {
                String referenceName = prefix;
                String className = uri.substring(SCA_REFERENCE_PREFIX.length());
                Class<?> interfaze = resolveClass(resolver, className);
                Reference theReference = createReference(interfaze, referenceName);
                xqueryImplementation.getReferences().add(theReference);
            } else if (uri.startsWith(SCA_PROPERTY_JAVA_PREFIX)) {
                String propertyName = prefix;
                String className = uri.substring(SCA_PROPERTY_JAVA_PREFIX.length());
                Class<?> clazz = resolveClass(resolver, className);
                QName xmlType = JavaXMLMapper.getXMLType(clazz);
                Property theProperty = createProperty(xmlType, propertyName);
                xqueryImplementation.getProperties().add(theProperty);
            } else if (uri.startsWith(SCA_PROPERTY_XML_PREFIX)) {
                String propertyName = prefix;
                String namespaceAndLocalname = uri.substring(SCA_PROPERTY_XML_PREFIX.length());
                int localNameDelimiterPostition = namespaceAndLocalname.lastIndexOf(':');
                String localName = null;
                String namespace = null;
                if (localNameDelimiterPostition < 0) {
                    localName = namespaceAndLocalname;
                    namespace = "";
                } else {
                    namespace = namespaceAndLocalname.substring(0, localNameDelimiterPostition);
                    localName = namespaceAndLocalname.substring(localNameDelimiterPostition + 1);
                }
                QName xmlType = new QName(namespace, localName);
                Property theProperty = createProperty(xmlType, propertyName);
                xqueryImplementation.getProperties().add(theProperty);
            }
        }
    }

    /**
     * Creates a Service for the component type based on its name and Java interface
     */
    private Service createService(Class<?> interfaze, String name) throws InvalidInterfaceException {
        Service service = assemblyFactory.createService();
        JavaInterfaceContract interfaceContract = javaFactory.createJavaInterfaceContract();
        service.setInterfaceContract(interfaceContract);

        // Set the name for the service
        service.setName(name);

        // Set the call interface and, if present, the callback interface
        JavaInterface callInterface = null;
        try {
            callInterface = (JavaInterface) javaFactory.createJavaInterface(interfaze).clone();
        } catch (CloneNotSupportedException e) {
            // Ignore
        }
        //setDataBindingForInterface(callInterface, DataObject.class.getName());
        service.getInterfaceContract().setInterface(callInterface);
        if (callInterface.getCallbackClass() != null) {
            JavaInterface callbackInterface = null;
            try {
                callbackInterface = (JavaInterface) javaFactory.createJavaInterface(callInterface.getCallbackClass()).clone();
            } catch (CloneNotSupportedException e) {
                //Ignore
            }
            //setDataBindingForInterface(callbackInterface, DataObject.class.getName());
            service.getInterfaceContract().setCallbackInterface(callbackInterface);
        }
        return service;
    } // end method createService

    protected Property createProperty(QName type, String name) {

        Property property = assemblyFactory.createProperty();
        property.setName(name);
        property.setXSDType(type);

        property.setMany(false);
        return property;

    }

    /**
     * Creates a Reference for the component type based on its name and Java interface
     */
    private Reference createReference(Class<?> interfaze, String name) throws InvalidInterfaceException {
        Reference reference = assemblyFactory.createReference();
        JavaInterfaceContract interfaceContract = javaFactory.createJavaInterfaceContract();
        reference.setInterfaceContract(interfaceContract);

        // Set the name of the reference to the supplied name and the multiplicity of the reference
        // to 1..1 - for XQuery implementations, this is the only multiplicity supported
        reference.setName(name);
        reference.setMultiplicity(Multiplicity.ONE_ONE);

        // Set the call interface and, if present, the callback interface
        // Set the call interface and, if present, the callback interface
        JavaInterface callInterface = null;
        try {
            callInterface = (JavaInterface) javaFactory.createJavaInterface(interfaze).clone();
        } catch (CloneNotSupportedException e) {
            // Ignore
        }
        reference.getInterfaceContract().setInterface(callInterface);
        if (callInterface.getCallbackClass() != null) {
            JavaInterface callbackInterface = null;
            try {
                callbackInterface = (JavaInterface) javaFactory.createJavaInterface(callInterface.getCallbackClass()).clone();
            } catch (CloneNotSupportedException e) {
                //Ignore
            }
            reference.getInterfaceContract().setCallbackInterface(callbackInterface);
        }

        return reference;
    }

    /**
     * For the methods of each implemented service corresponding expression extension
     * is generated
     * @param xqueryImplementation
     */
    private void fillExpressionExtensions(XQueryImplementation xqueryImplementation) {
        for (Service service : xqueryImplementation.getServices()) {
            Class<?> interfaze = ((JavaInterface)service.getInterfaceContract().getInterface()).getJavaClass();

            // For each of the methods
            for (Method method : interfaze.getMethods()) {
                String expressionExtension = createExpressionExtension(method, interfaze, service.getName());
                xqueryImplementation.getXqExpressionExtensionsMap().put(method, expressionExtension);
            }
        }
    }

    private String createExpressionExtension(Method method, Class<?> interfaze, String serviceName) {
        StringBuffer exprBuf = new StringBuffer();

        exprBuf.append("\r\n");

        String methodName = method.getName();

        // For each of the declared parameters
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            exprBuf.append("declare variable $" + methodName + "_" + i + " external;\r\n");
        }

        exprBuf.append(serviceName + ":" + methodName + "(");

        for (int i = 0; i < method.getParameterTypes().length; i++) {
            exprBuf.append("$" + methodName + "_" + i);
            if (i != method.getParameterTypes().length - 1) {
                exprBuf.append(", ");
            }
        }
        exprBuf.append(")");

        return exprBuf.toString();
    }
}
