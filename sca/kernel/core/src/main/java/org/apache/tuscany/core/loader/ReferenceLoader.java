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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.core.util.ReferenceLoaderHelper;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.LoaderExtension;
import org.apache.tuscany.spi.loader.DuplicateReferenceNameException;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.loader.ReferenceMultiplicityViolationException;
import org.apache.tuscany.spi.loader.UnrecognizedElementException;
import org.apache.tuscany.spi.model.BindingDefinition;
import org.apache.tuscany.spi.model.ComponentType;
import org.apache.tuscany.spi.model.ComponentTypeReferenceDefinition;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.CompositeReferenceDefinition;
import org.apache.tuscany.spi.model.ModelObject;
import org.apache.tuscany.spi.model.Multiplicity;
import org.apache.tuscany.spi.model.ServiceContract;
import org.apache.tuscany.spi.util.stax.StaxUtil;
import org.osoa.sca.annotations.Constructor;

/**
 * Loads a reference from an XML-based assembly file
 *
 * @version $Rev$ $Date$
 */
public class ReferenceLoader extends LoaderExtension<ComponentTypeReferenceDefinition> {
    public static final String NAME_ATTR = "name";

    public static final String MULTIPLICITY_ATTR = "multiplicity";

    public static final String WIRED_BY_IMPL_ATTR = "wiredByImpl";

    public static final String TARGET_ATTR = "target";
    
    public static final String PROMOTE_ATTR = "promote";

    public static final QName REFERENCE = new QName(SCA_NS, "reference");

    @Constructor
    public ReferenceLoader(@Autowire
    LoaderRegistry registry) {
        super(registry);
    }

    public QName getXMLType() { 
        return REFERENCE;
    }
    
    public ComponentTypeReferenceDefinition load(CompositeComponent parent,
                                                 ModelObject object,
                                                 XMLStreamReader reader,
                                                 DeploymentContext deploymentContext) throws XMLStreamException, LoaderException {
        if (object instanceof CompositeComponentType<?, ?, ?>) {
            return loadCompositeReference(parent, object, reader, deploymentContext);
            //return loadComponentType(parent, object, reader, deploymentContext);
        } else {
            return loadComponentTypeReference(parent, object, reader, deploymentContext);
        }
    }
                  
    

    public ComponentTypeReferenceDefinition loadComponentTypeReference(CompositeComponent parent,
                                    ModelObject object,
                                    XMLStreamReader reader,
                                    DeploymentContext deploymentContext) throws XMLStreamException, LoaderException {
        
        assert REFERENCE.equals(reader.getName());
        String name = reader.getAttributeValue(null, NAME_ATTR);
        String multiplicityVal = reader.getAttributeValue(null, MULTIPLICITY_ATTR);
        String wiredByImpl = reader.getAttributeValue(null, WIRED_BY_IMPL_ATTR);
        String targets = reader.getAttributeValue(null, TARGET_ATTR);
        Multiplicity multiplicity = StaxUtil.multiplicity(multiplicityVal, Multiplicity.ONE_ONE);
        
        
        
        ComponentType<?, ?, ?> componentType = (ComponentType<?, ?, ?>)object;
        if ( componentType.getReferences().get(name) != null) {
            throw new DuplicateReferenceNameException(name, 
                                                  DuplicateReferenceNameException.COMPONENT_TYPE,
                                                  "");
        }
        ComponentTypeReferenceDefinition referenceDefinition = new ComponentTypeReferenceDefinition();
        referenceDefinition.setName(name);
        referenceDefinition.setMultiplicity(multiplicity);
        referenceDefinition.setName(name);
        referenceDefinition.setWiredByImpl(Boolean.parseBoolean(wiredByImpl));
        
        if (targets != null && targets.length() > 0 )
            ReferenceLoaderHelper.populateRefTargets(referenceDefinition, targets);
        
        /*if (!ReferenceLoaderHelper.validateMultiplicityAndTargets(multiplicity, referenceDefinition
            .getTargets())) {
            throw new ReferenceMultiplicityViolationException(name, multiplicity, referenceDefinition.getTargets().size());
        }*/

        while (true) {
            switch (reader.next()) {
                case START_ELEMENT:
                    ModelObject o = registry.load(parent, null, reader, deploymentContext);
                    if (o instanceof ServiceContract) {
                        referenceDefinition.setServiceContract((ServiceContract)o);
                    } else if (o instanceof BindingDefinition) {
                        referenceDefinition.addBinding((BindingDefinition)o);
                    } else {
                        throw new UnrecognizedElementException(reader.getName());
                    }
                    break;
                case END_ELEMENT:
                    return referenceDefinition;
            }
        }
    }
    
    public CompositeReferenceDefinition loadCompositeReference(CompositeComponent parent,
                                                              ModelObject object,
                                                              XMLStreamReader reader,
                                                              DeploymentContext deploymentContext) throws XMLStreamException, LoaderException {
        
        String name = reader.getAttributeValue(null, NAME_ATTR);
        String multiplicityVal = reader.getAttributeValue(null, MULTIPLICITY_ATTR);
        String wiredByImpl = reader.getAttributeValue(null, WIRED_BY_IMPL_ATTR);
        String targets = reader.getAttributeValue(null, TARGET_ATTR);
        String promotedComponentRefs = reader.getAttributeValue(null, PROMOTE_ATTR);
        //if multiplicity is not set, it will be derived in the composite loader by looking
        //at the multiplicity of all promoted references - which can be done only after the composite
        //is completelly loaded
        Multiplicity multiplicity = StaxUtil.multiplicity(multiplicityVal, null);
        
        
        CompositeComponentType<?, ?, ?> compositeCompType = (CompositeComponentType<?, ?, ?>)object;
        if ( compositeCompType.getDeclaredReferences().get(name) != null) {
            throw new DuplicateReferenceNameException(name, 
                                                  DuplicateReferenceNameException.COMPOSITE,
                                                  compositeCompType.getName());
        }
        CompositeReferenceDefinition referenceDefinition = new CompositeReferenceDefinition();
        referenceDefinition.setName(name);
        referenceDefinition.setMultiplicity(multiplicity);
        
        if (promotedComponentRefs != null && promotedComponentRefs.length() > 0) {
            ReferenceLoaderHelper.populatePromotedRefs(referenceDefinition, promotedComponentRefs);
        }
        
        if (targets != null && targets.length() > 0 )
            ReferenceLoaderHelper.populateRefTargets(referenceDefinition, targets);
        
        referenceDefinition.setWiredByImpl(Boolean.parseBoolean(wiredByImpl));
        
        while (true) {
            switch (reader.next()) {
                case START_ELEMENT:
                    ModelObject o = registry.load(parent, null, reader, deploymentContext);
                    if (o instanceof ServiceContract) {
                        referenceDefinition.setServiceContract((ServiceContract)o);
                    } else if (o instanceof BindingDefinition) {
                        referenceDefinition.addBinding((BindingDefinition)o);
                    } else {
                        throw new UnrecognizedElementException(reader.getName());
                    }
                    break;
                case END_ELEMENT:
                    return referenceDefinition;
            }
        }
    }
}
