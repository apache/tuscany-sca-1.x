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
package org.apache.tuscany.implementation.java.processor;

import static org.apache.tuscany.implementation.java.processor.JavaIntrospectionHelper.getAllInterfaces;
import static org.apache.tuscany.implementation.java.processor.JavaIntrospectionHelper.getAllPublicAndProtectedFields;
import static org.apache.tuscany.implementation.java.processor.JavaIntrospectionHelper.getAllUniquePublicProtectedMethods;
import static org.apache.tuscany.implementation.java.processor.JavaIntrospectionHelper.toPropertyName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tuscany.api.annotation.Monitor;
import org.apache.tuscany.api.annotation.Resource;
import org.apache.tuscany.assembly.Contract;
import org.apache.tuscany.assembly.Multiplicity;
import org.apache.tuscany.implementation.java.impl.ConstructorDefinition;
import org.apache.tuscany.implementation.java.impl.JavaElement;
import org.apache.tuscany.implementation.java.impl.JavaImplementationDefinition;
import org.apache.tuscany.implementation.java.impl.Parameter;
import org.apache.tuscany.implementation.java.introspection.ImplementationProcessorExtension;
import org.apache.tuscany.implementation.java.introspection.ProcessingException;
import org.apache.tuscany.interfacedef.Interface;
import org.apache.tuscany.interfacedef.InvalidInterfaceException;
import org.apache.tuscany.interfacedef.java.JavaFactory;
import org.apache.tuscany.interfacedef.java.JavaInterface;
import org.apache.tuscany.interfacedef.java.impl.DefaultJavaFactory;
import org.apache.tuscany.interfacedef.util.JavaXMLMapper;
import org.osoa.sca.annotations.Callback;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Remotable;
import org.osoa.sca.annotations.Service;

/**
 * Heuristically evaluates an un-annotated Java implementation type to determine
 * services, references, and properties according to the algorithm described in
 * the SCA Java Client and Implementation Model Specification <p/> TODO
 * Implement: <p/> When no service inteface is annotated, need to calculate a
 * single service comprising all public methods that are not reference or
 * property injection sites. If that service can be exactly mapped to an
 * interface implemented by the class then the service interface will be defined
 * in terms of that interface.
 * 
 * @version $Rev$ $Date$
 */
public class HeuristicPojoProcessor extends ImplementationProcessorExtension {
    private JavaFactory javaFactory;

    public HeuristicPojoProcessor() {
        this.javaFactory = new DefaultJavaFactory();
    }

    public <T> void visitEnd(Class<T> clazz, JavaImplementationDefinition type) throws ProcessingException {
        List<org.apache.tuscany.assembly.Service> services = type.getServices();
        if (services.isEmpty()) {
            // heuristically determine the service
            // TODO finish algorithm
            Set<Class> interfaces = getAllInterfaces(clazz);
            if (interfaces.size() == 0) {
                // class is the interface
                addService(type, clazz);
            } else if (interfaces.size() == 1) {
                // Only one interface, take it
                addService(type, interfaces.iterator().next());
            }
        }
        Set<Method> methods = getAllUniquePublicProtectedMethods(clazz);
        if (!type.getReferenceMembers().isEmpty() || !type.getPropertyMembers().isEmpty()) {
            // references and properties have been explicitly defined
            if (type.getServices().isEmpty()) {
                calculateServiceInterface(clazz, type, methods);
                if (type.getServices().isEmpty()) {
                    throw new ServiceTypeNotFoundException(clazz.getName());
                }
            }
            evaluateConstructor(type, clazz);
            return;
        }
        calcPropRefs(methods, services, type, clazz);
        evaluateConstructor(type, clazz);
    }

    private void addService(JavaImplementationDefinition type, Class<?> clazz) throws ProcessingException {
        try {
            org.apache.tuscany.assembly.Service service = createService(clazz);
            type.getServices().add(service);
        } catch (InvalidInterfaceException e) {
            throw new ProcessingException(e);
        }
    }

    private boolean isPublicSetter(Method method) {
        return method.getParameterTypes().length == 1 && Modifier.isPublic(method.getModifiers())
               && method.getName().startsWith("set")
               && method.getReturnType() == void.class;
    }

    private boolean isProtectedSetter(Method method) {
        return method.getParameterTypes().length == 1 && Modifier.isProtected(method.getModifiers())
               && method.getName().startsWith("set")
               && method.getReturnType() == void.class;
    }

    private <T> void calcPropRefs(Set<Method> methods,
                                  List<org.apache.tuscany.assembly.Service> services,
                                  JavaImplementationDefinition type,
                                  Class<T> clazz) throws ProcessingException {
        // heuristically determine the properties references
        // make a first pass through all public methods with one param
        Set<String> setters = new HashSet<String>();
        for (Method method : methods) {
            if (!isPublicSetter(method)) {
                continue;
            }
            if (!isInServiceInterface(method, services)) {
                // Not part of the service interface
                String name = toPropertyName(method.getName());
                setters.add(name);
                // avoid duplicate property or ref names
                if (!type.getPropertyMembers().containsKey(name) && !type.getReferenceMembers().containsKey(name)) {
                    Class<?> param = method.getParameterTypes()[0];
                    Type genericType = method.getGenericParameterTypes()[0];
                    if (isReferenceType(param, genericType)) {
                        type.getReferences().add(createReference(name, param));
                        type.getReferenceMembers().put(name, new JavaElement(method, 0));
                    } else {
                        type.getProperties().add(createProperty(name, param));
                        type.getPropertyMembers().put(name, new JavaElement(method, 0));
                    }
                }
            }
        }
        // second pass for protected methods with one param
        for (Method method : methods) {
            if (!isProtectedSetter(method)) {
                continue;
            }
            Class<?> param = method.getParameterTypes()[0];
            String name = toPropertyName(method.getName());
            setters.add(name);
            // avoid duplicate property or ref names
            if (!type.getPropertyMembers().containsKey(name) && !type.getReferenceMembers().containsKey(name)) {
                if (isReferenceType(param, method.getGenericParameterTypes()[0])) {
                    type.getReferences().add(createReference(name, param));
                    type.getReferenceMembers().put(name, new JavaElement(method, 0));
                } else {
                    type.getProperties().add(createProperty(name, param));
                    type.getPropertyMembers().put(name, new JavaElement(method, 0));
                }
            }
        }

        // Public or protected fields unless there is a public or protected
        // setter method
        // for the same name
        Set<Field> fields = getAllPublicAndProtectedFields(clazz);
        for (Field field : fields) {
            if (setters.contains(field.getName())) {
                continue;
            }
            String name = field.getName();
            Class<?> paramType = field.getType();
            if (isReferenceType(paramType, field.getGenericType())) {
                type.getReferences().add(createReference(name, paramType));
                type.getReferenceMembers().put(name, new JavaElement(field));
            } else {
                type.getProperties().add(createProperty(name, paramType));
                type.getPropertyMembers().put(name, new JavaElement(field));
            }
        }
    }

    /**
     * Determines the constructor to use based on the component type's
     * references and properties
     * 
     * @param type the component type
     * @param clazz the implementation class corresponding to the component type
     * @throws NoConstructorException if no suitable constructor is found
     * @throws AmbiguousConstructorException if the parameters of a constructor
     *             cannot be unambiguously mapped to references and properties
     */
    @SuppressWarnings("unchecked")
    private <T> void evaluateConstructor(JavaImplementationDefinition type, Class<T> clazz) throws ProcessingException {
        // determine constructor if one is not annotated
        ConstructorDefinition<?> definition = type.getConstructorDefinition();
        Constructor constructor;
        boolean explict = false;
        if (definition != null && definition.getConstructor()
                .isAnnotationPresent(org.osoa.sca.annotations.Constructor.class)) {
            // the constructor was already defined explicitly
            return;
        } else if (definition != null) {
            explict = true;
            constructor = definition.getConstructor();
        } else {
            // no definition, heuristically determine constructor
            Constructor[] constructors = clazz.getConstructors();
            if (constructors.length == 0) {
                throw new NoConstructorException("No public constructor for class");
            } else if (constructors.length == 1) {
                // Only one constructor, take it
                constructor = constructors[0];
            } else {
                // FIXME multiple constructors, none yet done
                Constructor<T> selected = null;
                int sites = type.getPropertyMembers().size() + type.getReferenceMembers().size();
                for (Constructor<T> ctor : constructors) {
                    if (ctor.getParameterTypes().length == 0) {
                        selected = ctor;
                    }
                    if (ctor.getParameterTypes().length == sites) {
                        // TODO finish
                        // selected = constructor;
                        // select constructor
                        // break;
                    }
                }
                if (selected == null) {
                    throw new NoConstructorException();
                }
                constructor = selected;
                definition = type.getConstructors().get(selected);
                type.setConstructorDefinition(definition);
                // return;
            }
            definition = type.getConstructors().get(constructor);
            type.setConstructorDefinition(definition);
        }
        Parameter[] parameters = definition.getParameters();
        if (parameters.length == 0) {
            return;
        }
        Map<String, JavaElement> props = type.getPropertyMembers();
        Map<String, JavaElement> refs = type.getReferenceMembers();
        Annotation[][] annotations = constructor.getParameterAnnotations();
        if (!explict) {
            // the constructor wasn't defined by an annotation, so check to see
            // if any of the params have an annotation
            // which we can impute as explicitly defining the constructor, e.g.
            // @Property, @Reference, or @Autowire
            explict = injectionAnnotationsPresent(annotations);
        }
        if (explict) {
            for (int i = 0; i < parameters.length; i++) {
                if (isAnnotated(parameters[i])) {
                    continue;
                } else if (!findReferenceOrProperty(parameters[i], props, refs)) {
                    throw new AmbiguousConstructorException(parameters[i].toString());
                }
            }
        } else {
            if (!areUnique(parameters)) {
                throw new AmbiguousConstructorException("Cannot resolve non-unique parameter types, use @Constructor");
            }
            if (!calcPropRefUniqueness(props.values(), refs.values())) {
                throw new AmbiguousConstructorException("Cannot resolve non-unique parameter types, use @Constructor");
            }
            if (!(props.isEmpty() && refs.isEmpty())) {
                calcParamNames(parameters, props, refs);
            } else {
                heuristicParamNames(type, parameters);

            }
        }
    }

    private void calcParamNames(Parameter[] parameters, Map<String, JavaElement> props, Map<String, JavaElement> refs)
        throws AmbiguousConstructorException {
        // the constructor param types must unambiguously match defined
        // reference or property types
        for (Parameter param : parameters) {
            if (!findReferenceOrProperty(param, props, refs)) {
                throw new AmbiguousConstructorException(param.getName());
            }
        }
    }

    private void heuristicParamNames(JavaImplementationDefinition type, Parameter[] parameters)
        throws ProcessingException {
        // heuristically determine refs and props from the parameter types
        for (Parameter p : parameters) {
            String name = p.getType().getSimpleName().toLowerCase();
            if (isReferenceType(p.getType(), p.getGenericType())) {
                type.getReferences().add(createReference(name, p.getType()));
                p.setClassifer(Reference.class);
                type.getReferenceMembers().put(name, p);
            } else {
                type.getProperties().add(createProperty(name, p.getType()));
                p.setClassifer(Property.class);
                type.getPropertyMembers().put(name, p);
            }
            p.setName(name);
        }
    }

    private static boolean areUnique(Class[] collection) {
        Set<Class> set = new HashSet<Class>(Arrays.asList(collection));
        return set.size() == collection.length;
    }

    /**
     * Returns true if the union of the given collections of properties and
     * references have unique Java types
     */
    private boolean calcPropRefUniqueness(Collection<JavaElement> props, Collection<JavaElement> refs) {

        Class[] classes = new Class[props.size() + refs.size()];
        int i = 0;
        for (JavaElement property : props) {
            classes[i] = property.getType();
            i++;
        }
        for (JavaElement reference : refs) {
            classes[i] = reference.getType();
            i++;
        }
        return areUnique(classes);
    }

    /**
     * Unambiguously finds the reference or property associated with the given
     * type
     * 
     * @return the name of the reference or property if found, null if not
     * @throws AmbiguousConstructorException if the constructor parameter cannot
     *             be resolved to a property or reference
     */
    private boolean findReferenceOrProperty(Parameter parameter,
                                            Map<String, JavaElement> props,
                                            Map<String, JavaElement> refs) throws AmbiguousConstructorException {

        boolean found = false;
        if (!"".equals(parameter.getName())) {
            // Match by name
            JavaElement prop = props.get(parameter.getName());
            if (prop != null && prop.getType() == parameter.getType()) {
                parameter.setClassifer(Property.class);
                return true;
            }
            JavaElement ref = refs.get(parameter.getName());
            if (ref != null && ref.getType() == parameter.getType()) {
                parameter.setClassifer(Reference.class);
                return true;
            }
        }
        for (JavaElement property : props.values()) {
            if (property.getType() == parameter.getType()) {
                if (found) {
                    throw new AmbiguousConstructorException("Ambiguous property or reference for constructor type",
                                                            (Member)parameter.getAnchor());
                }
                parameter.setClassifer(Property.class);
                parameter.setName(property.getName());
                found = true;
                // do not break since ambiguities must be checked, i.e. more
                // than one prop or ref of the same type
            }
        }
        for (JavaElement reference : refs.values()) {
            if (reference.getType() == parameter.getType()) {
                if (found) {
                    throw new AmbiguousConstructorException("Ambiguous property or reference for constructor type",
                                                            (Member)parameter.getAnchor());
                }
                parameter.setClassifer(Reference.class);
                parameter.setName(reference.getName());
                found = true;
                // do not break since ambiguities must be checked, i.e. more
                // than one prop or ref of the same type
            }
        }
        return found;
    }

    /**
     * Returns true if a given type is reference according to the SCA
     * specification rules for determining reference types The following rules
     * are used to determine whether an unannotated field or setter method is a
     * property or reference:
     * <ol>
     * <li>If its type is simple, then it is a property.
     * <li>If its type is complex, then if the type is an interface marked by
     * 
     * @Remotable, then it is a reference; otherwise, it is a property.
     *             <li>Otherwise, if the type associated with the member is an
     *             array or a java.util.Collection, the basetype is the element
     *             type of the array or the parameterized type of the
     *             Collection; otherwise the basetype is the member type. If the
     *             basetype is an interface with an
     * @Remotable or
     * @Service annotation then the memberis defined as a reference. Otherwise,
     *          it is defined as a property.
     *          </ol>
     *          <p>
     *          The name of the reference or of the property is derived from the
     *          name found on the setter method or on the field.
     */
    private boolean isReferenceType(Class<?> cls, Type genericType) {
        Class<?> baseType = JavaIntrospectionHelper.getBaseType(cls, genericType);
        return baseType.isInterface() && (baseType.isAnnotationPresent(Remotable.class) || baseType
                   .isAnnotationPresent(Service.class));
    }

    /**
     * Returns true if the given operation is defined in the collection of
     * service interfaces
     */
    private boolean isInServiceInterface(Method operation, List<org.apache.tuscany.assembly.Service> services) {
        for (org.apache.tuscany.assembly.Service service : services) {
            Interface interface1 = service.getInterface();
            if (interface1 instanceof JavaInterface) {
                Class<?> clazz = ((JavaInterface)interface1).getJavaClass();
                if (isMethodMatched(clazz, operation)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Test if the class declares a method which matches the signature of the
     * given method
     * 
     * @param clazz
     * @param method
     * @return
     */
    private boolean isMethodMatched(Class<?> clazz, Method method) {
        if (method.getDeclaringClass() == clazz) {
            return true;
        }
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (JavaIntrospectionHelper.exactMethodMatch(method, m)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a mapped property
     * 
     * @param name the property name
     * @param member the injection site the reference maps to
     * @param paramType the property type
     */
    private org.apache.tuscany.assembly.Property createProperty(String name, Class<?> paramType) {
        org.apache.tuscany.assembly.Property property = factory.createProperty();
        property.setName(name);
        property.setXSDType(JavaXMLMapper.getXMLType(paramType));
        return property;
    }

    /**
     * Populates a component type with a service whose interface type is
     * determined by examining all implemented interfaces of the given class and
     * chosing one whose operations match all of the class's non-property and
     * non-reference methods
     * 
     * @param clazz the class to examine
     * @param type the component type
     * @param methods all methods in the class to examine
     */
    private void calculateServiceInterface(Class<?> clazz, JavaImplementationDefinition type, Set<Method> methods)
        throws ProcessingException {
        List<Method> nonPropRefMethods = new ArrayList<Method>();
        // Map<String, Service> services = type.getServices();
        Map<String, JavaElement> references = type.getReferenceMembers();
        Map<String, JavaElement> properties = type.getPropertyMembers();
        // calculate methods that are not properties or references
        for (Method method : methods) {
            String name = toPropertyName(method.getName());
            if (!references.containsKey(name) && !properties.containsKey(name)) {
                nonPropRefMethods.add(method);
            }
        }
        // determine if an implemented interface matches all of the non-property
        // and non-reference methods
        Class[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0) {
            return;
        }
        for (Class interfaze : interfaces) {
            if (analyzeInterface(interfaze, nonPropRefMethods)) {
                org.apache.tuscany.assembly.Service service;
                try {
                    service = createService(interfaze);
                } catch (InvalidInterfaceException e) {
                    throw new ProcessingException(e);
                }
                type.getServices().add(service);
            }
        }
    }

    /**
     * Determines if the methods of a given interface match the given list of
     * methods
     * 
     * @param interfaze the interface to examine
     * @param nonPropRefMethods the list of methods to match against
     * @return true if the interface matches
     */
    private boolean analyzeInterface(Class<?> interfaze, List<Method> nonPropRefMethods) {
        Method[] interfaceMethods = interfaze.getMethods();
        if (nonPropRefMethods.size() != interfaceMethods.length) {
            return false;
        }
        for (Method method : nonPropRefMethods) {
            boolean found = isMethodMatched(interfaze, method);
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private boolean isAnnotated(Parameter parameter) {
        for (Annotation annotation : parameter.getAnnotations()) {
            Class<? extends Annotation> annotType = annotation.annotationType();
            if (annotType.equals(Property.class) || annotType.equals(Reference.class)
                || annotType.equals(Resource.class)) {
                return true;
            }
        }
        return false;
    }

    public boolean areUnique(Parameter[] parameters) {
        Set<Class> set = new HashSet<Class>(parameters.length);
        for (Parameter p : parameters) {
            if (!set.add(p.getType())) {
                return false;
            }
        }
        return true;
    }

    public org.apache.tuscany.assembly.Reference createReference(String name, Class<?> paramType)
        throws ProcessingException {
        org.apache.tuscany.assembly.Reference reference = factory.createReference();
        reference.setName(name);
        try {
            interfaceProcessorRegistry.introspect(reference, paramType);
            reference.setMultiplicity(Multiplicity.ZERO_ONE);
        } catch (InvalidInterfaceException e1) {
            throw new ProcessingException(e1);
        }
        try {
            processCallback(paramType, reference);
        } catch (InvalidServiceType e) {
            throw new ProcessingException(e);
        }
        return reference;
    }

    public org.apache.tuscany.assembly.Service createService(Class<?> interfaze) throws InvalidInterfaceException {
        org.apache.tuscany.assembly.Service service = factory.createService();
        service.setName(interfaze.getSimpleName());
        interfaceProcessorRegistry.introspect(service, interfaze);
        Interface javaInterface = service.getInterface();
        javaInterface.setRemotable(interfaze.getAnnotation(Remotable.class) != null);
        service.setInterface(javaInterface);
        return service;
    }

    public void processCallback(Class<?> interfaze, Contract contract) throws InvalidServiceType {
        Callback callback = interfaze.getAnnotation(Callback.class);
        if (callback != null && !Void.class.equals(callback.value())) {
            Class<?> callbackClass = callback.value();
            JavaInterface javaInterface = javaFactory.createJavaInterface();
            javaInterface.setJavaClass(callbackClass);
            contract.setCallbackInterface(javaInterface);
        } else if (callback != null && Void.class.equals(callback.value())) {
            throw new InvalidServiceType("No callback interface specified on annotation", interfaze);
        }
    }

    public boolean injectionAnnotationsPresent(Annotation[][] annots) {
        for (Annotation[] annotations : annots) {
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotType = annotation.annotationType();
                if (annotType.equals(Property.class) || annotType.equals(Reference.class)
                    || annotType.equals(Resource.class)
                    || annotType.equals(Monitor.class)) {
                    return true;
                }
            }
        }
        return false;
    }
}
