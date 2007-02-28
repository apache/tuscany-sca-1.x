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
package org.apache.tuscany.core.loader;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.osoa.sca.Constants.SCA_NS;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.core.binding.local.LocalBindingDefinition;
import org.apache.tuscany.core.implementation.system.model.SystemImplementation;
import org.apache.tuscany.spi.ObjectFactory;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.databinding.extension.DOMHelper;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.LoaderExtension;
import org.apache.tuscany.spi.loader.InvalidReferenceException;
import org.apache.tuscany.spi.loader.InvalidValueException;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.loader.LoaderUtil;
import org.apache.tuscany.spi.loader.MissingImplementationException;
import org.apache.tuscany.spi.loader.MissingPropertyValueException;
import org.apache.tuscany.spi.loader.MissingReferenceException;
import org.apache.tuscany.spi.loader.PropertyObjectFactory;
import org.apache.tuscany.spi.loader.ReferenceMultiplicityViolationException;
import org.apache.tuscany.spi.loader.UndefinedPropertyException;
import org.apache.tuscany.spi.loader.UndefinedReferenceException;
import org.apache.tuscany.spi.loader.UnrecognizedElementException;
import org.apache.tuscany.spi.model.BindingDefinition;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.ComponentType;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.Implementation;
import org.apache.tuscany.spi.model.ModelObject;
import org.apache.tuscany.spi.model.Multiplicity;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.model.PropertyValue;
import org.apache.tuscany.spi.model.ReferenceDefinition;
import org.apache.tuscany.spi.model.ReferenceTarget;
import org.apache.tuscany.spi.model.ServiceDefinition;
import org.apache.tuscany.spi.util.stax.StaxUtil;
import org.osoa.sca.annotations.Constructor;
import org.w3c.dom.Document;

/**
 * Loads a component definition from an XML-based assembly file 
 * 
 * @version $Rev$ $Date$
 */
public class ComponentLoader extends LoaderExtension<ComponentDefinition<?>> {
    private static final QName COMPONENT = new QName(SCA_NS, "component");
    private static final QName PROPERTY = new QName(SCA_NS, "property");
    private static final QName REFERENCE = new QName(SCA_NS, "reference");

    private static final String PROPERTY_FILE_ATTR = "file";
    private static final String PROPERTY_SOURCE_ATTR = "source";
    private static final String PROPERTY_NAME_ATTR = "name";
    private static final String PROPERTY_TYPE_ATTR = "type";
    private static final String PROPERTY_ELEMENT_ATTR = "element";
    public static final char COLON = ':';

    private PropertyObjectFactory propertyFactory;

    @Constructor
    public ComponentLoader(@Autowire
    LoaderRegistry registry, @Autowire
    PropertyObjectFactory propertyFactory) {
        super(registry);
        this.propertyFactory = propertyFactory;
    }

    public QName getXMLType() {
        return COMPONENT;
    }

    @SuppressWarnings("unchecked")
    public ComponentDefinition<?> load(CompositeComponent parent,
                                       ModelObject object,
                                       XMLStreamReader reader,
                                       DeploymentContext deploymentContext) throws XMLStreamException,
                                                                           LoaderException {
        assert COMPONENT.equals(reader.getName());
        String name = reader.getAttributeValue(null, "name");
        String initLevel = reader.getAttributeValue(null, "initLevel");

        try {
            Implementation<?> impl = loadImplementation(parent, reader, deploymentContext);
            registry.loadComponentType(parent, impl, deploymentContext);

            ComponentDefinition<Implementation<?>> componentDefinition =
                new ComponentDefinition<Implementation<?>>(name, impl);

            if (initLevel != null) {
                if (initLevel.length() == 0) {
                    componentDefinition.setInitLevel(0);
                } else {
                    try {
                        componentDefinition.setInitLevel(Integer.valueOf(initLevel));
                    } catch (NumberFormatException e) {
                        throw new InvalidValueException(initLevel, "initValue", e);
                    }
                }
            }

            while (true) {
                switch (reader.next()) {
                    case START_ELEMENT:
                        QName qname = reader.getName();
                        if (PROPERTY.equals(qname)) {
                            loadProperty(reader, deploymentContext, componentDefinition);
                        } else if (REFERENCE.equals(qname)) {
                            loadReference(reader, deploymentContext, componentDefinition);
                        } else {
                            throw new UnrecognizedElementException(qname);
                        }
                        reader.next();
                        break;
                    case END_ELEMENT:
                        if (reader.getName().equals(COMPONENT)) {
                            // hack to leave alone SystemImplementation
                            if (!((Implementation)componentDefinition.getImplementation() instanceof SystemImplementation)) {
                                populatePropertyValues(componentDefinition);
                            }
                            ComponentType<ServiceDefinition, ReferenceDefinition, Property<?>> type =
                                (ComponentType<ServiceDefinition, ReferenceDefinition, Property<?>>)componentDefinition
                                    .getImplementation().getComponentType();
                            for (ReferenceDefinition ref : type.getReferences().values()) {
                                if (ref.isAutowire()) {
                                    ReferenceTarget referenceTarget = new ReferenceTarget();
                                    referenceTarget.setReferenceName(ref.getName());
                                    componentDefinition.add(referenceTarget);
                                }
                            }
                            validate(componentDefinition);
                            return componentDefinition;
                        }
                        break;
                }
            }
        } catch (LoaderException e) {
            e.addContextName(name);
            throw e;
        }
    }

    protected Implementation<?> loadImplementation(CompositeComponent parent,
                                                   XMLStreamReader reader,
                                                   DeploymentContext deploymentContext) throws XMLStreamException,
                                                                                       LoaderException {
        reader.nextTag();
        ModelObject o = registry.load(parent, null, reader, deploymentContext);
        if (!(o instanceof Implementation)) {
            throw new MissingImplementationException();
        }
        return (Implementation<?>)o;
    }

    @SuppressWarnings("unchecked")
    protected void loadProperty(XMLStreamReader reader,
                                DeploymentContext deploymentContext,
                                ComponentDefinition<?> componentDefinition) throws XMLStreamException,
                                                                                   LoaderException {
        String name = reader.getAttributeValue(null, PROPERTY_NAME_ATTR);
        Implementation<?> implementation = componentDefinition.getImplementation();
        ComponentType<?, ?, ?> componentType = implementation.getComponentType();
        Property<Type> property = componentType.getProperty(name);
        if (property == null) {
            throw new UndefinedPropertyException(name);
        }
        
        PropertyValue<Type> propertyValue;
        readPropertyType(reader, property);
        
        String source = reader.getAttributeValue(null, PROPERTY_SOURCE_ATTR);
        String file = reader.getAttributeValue(null, PROPERTY_FILE_ATTR);
        
        if (source != null || file != null) {
            propertyValue = new PropertyValue<Type>(name, source, file);
            propertyValue.setValue(property.getDefaultValues());
            LoaderUtil.skipToEndElement(reader);
        } else {
            try {
                DocumentBuilder documentBuilder = DOMHelper.newDocumentBuilder();
                List<Document> values = loadPropertyValues(reader, documentBuilder, property.getXmlType(), property.getXmlElement());
                propertyValue = new PropertyValue<Type>(name, values);
                if (!property.isMany() && values.size() > 1) {
                    ManyPropertyValueLoaderException ex = new ManyPropertyValueLoaderException();
                    ex.setPropertyName(name);
                    ex.setLine(reader.getLocation().getLineNumber());
                    ex.setColumn(reader.getLocation().getColumnNumber());
                    throw ex;
                }
            } catch (ParserConfigurationException e) {
                throw new LoaderException(e);
            }
        }
        
        ObjectFactory<?> objectFactory = null;
        if (property.isMany()) {
            objectFactory = propertyFactory.createListObjectFactory(property, propertyValue);
        } else {
            objectFactory = propertyFactory.createObjectFactory(property, propertyValue);
        }
        // propertyValue.setValueFactory(new
        // SimplePropertyObjectFactory(property, propertyValue.getValue()));
        propertyValue.setValueFactory(objectFactory);
        componentDefinition.add(propertyValue);
    }

    protected void loadReference(XMLStreamReader reader,
                                 DeploymentContext deploymentContext,
                                 ComponentDefinition<?> componentDefinition) throws XMLStreamException,
                                                                            LoaderException {
        String name = reader.getAttributeValue(null, "name");
        String text = reader.getElementText();
        String target = text != null ? text.trim() : null;

        if (name == null) {
            throw new InvalidReferenceException("No name specified");
        } else if (target == null) {
            throw new InvalidReferenceException("No target specified", name);
        }
        Implementation<?> impl = componentDefinition.getImplementation();
        ComponentType<?, ?, ?> componentType = impl.getComponentType();
        if (!componentType.getReferences().containsKey(name)) {
            throw new UndefinedReferenceException(name);
        }
        if (componentType instanceof CompositeComponentType) {
            ReferenceDefinition definition = componentType.getReferences().get(name);
            if (definition.getBindings().isEmpty()) {
                // TODO JFM allow selection of a default binding
                try {
                    LocalBindingDefinition binding = new LocalBindingDefinition(new URI(target));
                    definition.addBinding(binding);
                } catch (URISyntaxException e) {
                    throw new InvalidReferenceException(e);
                }
            } else {
                for (BindingDefinition binding : definition.getBindings()) {
                    try {
                        // FIXME this is bad - clarify in the spec how URIs are
                        // overriden
                        binding.setTargetUri(new URI(target));
                    } catch (URISyntaxException e) {
                        throw new LoaderException(e);
                    }
                }
            }
        } else {
            ReferenceTarget referenceTarget = componentDefinition.getReferenceTargets().get(name);
            if (referenceTarget == null) {
                referenceTarget = new ReferenceTarget();
                referenceTarget.setReferenceName(name);
                componentDefinition.add(referenceTarget);
            }
            try {
                referenceTarget.addTarget(new URI(target));
            } catch (URISyntaxException e) {
                throw new InvalidReferenceException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void populatePropertyValues(ComponentDefinition<Implementation<?>> componentDefinition) throws LoaderException,
                                                                                                     MissingPropertyValueException {
        ComponentType componentType = componentDefinition.getImplementation().getComponentType();
        if (componentType != null) {
            Map<String, Property<?>> properties = componentType.getProperties();
            Map<String, PropertyValue<?>> propertyValues = componentDefinition.getPropertyValues();

            for (Property<?> aProperty : properties.values()) {
                if (propertyValues.get(aProperty.getName()) == null) {
                    if (aProperty.isMustSupply()) {
                        throw new MissingPropertyValueException(aProperty.getName());
                    } else if (aProperty.getDefaultValues() != null) {
                        PropertyValue propertyValue = new PropertyValue();
                        propertyValue.setName(aProperty.getName());
                        propertyValue.setValue(aProperty.getDefaultValues());
                        propertyValue.setValueFactory(propertyFactory
                            .createObjectFactory(aProperty, propertyValue));
                        /*
                         * propertyValue.setValueFactory(new
                         * SimplePropertyObjectFactory(aProperty,
                         * propertyValue.getValue()));
                         */
                        propertyValues.put(aProperty.getName(), propertyValue);
                    }
                }
            }
        }
    }

    /**
     * Validates a component definition, ensuring all component type
     * configuration elements are satisfied
     */
    protected void validate(ComponentDefinition<Implementation<?>> definition) throws LoaderException {
        // validate refererences
        Implementation<?> implementation = definition.getImplementation();
        ComponentType<?, ?, ?> type = implementation.getComponentType();
        if (type == null) {
            return;
        }
        for (ReferenceDefinition referenceDef : type.getReferences().values()) {
            if (referenceDef.isAutowire() || !referenceDef.isRequired()) {
                continue;
            }
            String name = referenceDef.getName();
            ReferenceTarget target = definition.getReferenceTargets().get(name);
            if (target == null) {
                throw new MissingReferenceException(name);
            }
            int count = target.getTargets().size();
            Multiplicity multiplicity = referenceDef.getMultiplicity();
            switch (multiplicity) {
                case ZERO_N:
                    break;
                case ZERO_ONE:
                    if (count > 1) {
                        throw new ReferenceMultiplicityViolationException(name, multiplicity, count);
                    }
                    break;
                case ONE_ONE:
                    if (count != 1) {
                        throw new ReferenceMultiplicityViolationException(name, multiplicity, count);
                    }
                    break;
                case ONE_N:
                    if (count < 1) {
                        throw new ReferenceMultiplicityViolationException(name, multiplicity, count);
                    }
                    break;
            }

        }
    }

    private void readPropertyType(XMLStreamReader reader, Property property) throws MissingTypePropertyLoaderException{

        String typeName = reader.getAttributeValue(null, PROPERTY_TYPE_ATTR);
        String elementName = reader.getAttributeValue(null, PROPERTY_ELEMENT_ATTR);
        QName xmlElement = null;
        QName xmlType = null;

        if (typeName != null) {
            int index = typeName.indexOf(COLON);
            if (index != -1) {
                String prefix = typeName.substring(0, index);
                String localName = typeName.substring(index + 1);
                String ns = reader.getNamespaceURI(prefix);
                xmlType = new QName(ns, localName, prefix);
            }
        } else if (elementName != null) {
            int index = elementName.indexOf(COLON);
            if (index != -1) {
                String prefix = elementName.substring(0, index);
                String localName = elementName.substring(index + 1);
                String ns = reader.getNamespaceURI(prefix);
                xmlElement = new QName(ns, localName, prefix);
                // FIXME :
                // need to figure out how to determine the xmltype from this
                // xmlelement
                // this need access to the global xml element thro
                // schemalocation or thro
                // artifact repository
                xmlType = null;
            }
        }

        if (xmlType != null) {
            property.setXmlType(xmlType);
        }

        if (xmlElement != null) {
            property.setXmlElement(xmlElement);
        }
        
        /*if (property.getXmlType() == null && property.getXmlElement() == null) {
            MissingTypePropertyLoaderException ex = new MissingTypePropertyLoaderException();
            ex.setPropertyName(property.getName());
            ex.setLine(reader.getLocation().getLineNumber());
            ex.setColumn(reader.getLocation().getColumnNumber());
            throw ex; 
        }*/
    }

    private List<Document> loadPropertyValues(XMLStreamReader reader,
                                    DocumentBuilder documentBuilder,
                                    QName xmlType,
                                    QName xmlElement) throws XMLStreamException {
        return StaxUtil.createPropertyValues(reader, xmlType, xmlElement, documentBuilder);
    }
    
 }
