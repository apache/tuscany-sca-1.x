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
package org.apache.tuscany.core.implementation.composite;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.osoa.sca.Constants.SCA_NS;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.core.property.PropertyHelper;
import org.apache.tuscany.core.util.ReferenceLoaderHelper;
import org.apache.tuscany.spi.QualifiedName;
import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.CompositeClassLoader;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.LoaderExtension;
import org.apache.tuscany.spi.idl.InvalidServiceContractException;
import org.apache.tuscany.spi.idl.java.JavaInterfaceProcessorRegistry;
import org.apache.tuscany.spi.idl.java.JavaServiceContract;
import org.apache.tuscany.spi.loader.InvalidPromotedReferenceException;
import org.apache.tuscany.spi.loader.InvalidServiceException;
import org.apache.tuscany.spi.loader.InvalidWireException;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.loader.ReferenceMultiplicityOverridingException;
import org.apache.tuscany.spi.model.BindingDefinition;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.ComponentReferenceDefinition;
import org.apache.tuscany.spi.model.ComponentType;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.CompositeReferenceDefinition;
import org.apache.tuscany.spi.model.Implementation;
import org.apache.tuscany.spi.model.Include;
import org.apache.tuscany.spi.model.ModelObject;
import org.apache.tuscany.spi.model.Multiplicity;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.model.AbstractReferenceDefinition;
import org.apache.tuscany.spi.model.ReferenceTarget;
import org.apache.tuscany.spi.model.ServiceContract;
import org.apache.tuscany.spi.model.ServiceDefinition;
import org.apache.tuscany.spi.model.WireDefinition;
import org.apache.tuscany.spi.services.artifact.Artifact;
import org.apache.tuscany.spi.services.artifact.ArtifactRepository;

/**
 * Loads a composite component definition from an XML-based assembly file
 * 
 * @version $Rev$ $Date$
 */
public class CompositeLoader extends LoaderExtension<CompositeComponentType> {
    public static final QName COMPOSITE = new QName(SCA_NS, "composite");
    public static final String URI_DELIMITER = "/";

    @Autowire
    protected JavaInterfaceProcessorRegistry processorRegistry;

    private final ArtifactRepository artifactRepository;

    public CompositeLoader(@Autowire
    LoaderRegistry registry, @Autowire
    ArtifactRepository artifactRepository) {
        super(registry);
        this.artifactRepository = artifactRepository;
    }

    public QName getXMLType() {
        return COMPOSITE;
    }

    public CompositeComponentType load(CompositeComponent parent,
                                       ModelObject object,
                                       XMLStreamReader reader,
                                       DeploymentContext deploymentContext) throws XMLStreamException,
                                                                           LoaderException {
        CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>> composite =
            new CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>>();
        composite.setName(reader.getAttributeValue(null, "name"));
        boolean done = false;
        while (!done) {
            switch (reader.next()) {
                case START_ELEMENT:
                    ModelObject o = registry.load(parent, composite, reader, deploymentContext);
                    if (o instanceof ServiceDefinition) {
                        composite.add((ServiceDefinition)o);
                    } else if (o instanceof CompositeReferenceDefinition) {
                        composite.add((CompositeReferenceDefinition)o);
                    } else if (o instanceof Property<?>) {
                        composite.add((Property<?>)o);
                    } else if (o instanceof ComponentDefinition<?>) {
                        composite.add((ComponentDefinition<?>)o);
                    } else if (o instanceof Include) {
                        composite.add((Include)o);
                    } else if (o instanceof Dependency) {
                        Artifact artifact = ((Dependency)o).getArtifact();
                        if (artifactRepository != null) {
                            // default to jar type if not specified
                            if (artifact.getType() == null) {
                                artifact.setType("jar");
                            }
                            artifactRepository.resolve(artifact);
                        }
                        if (artifact.getUrl() != null) {
                            ClassLoader classLoader = deploymentContext.getClassLoader();
                            if (classLoader instanceof CompositeClassLoader) {
                                CompositeClassLoader ccl = (CompositeClassLoader)classLoader;
                                for (URL dep : artifact.getUrls()) {
                                    ccl.addURL(dep);
                                }
                            }
                        }
                    } else if (o instanceof WireDefinition) {
                        composite.add((WireDefinition)o);
                    } else {
                        // add as an unknown model extension
                        if (o != null) {
                            composite.getDeclaredExtensions().put(o.getClass(), o);
                        }
                    }
                    reader.next();
                    break;
                case END_ELEMENT:
                    if (COMPOSITE.equals(reader.getName())) {
                        // if there are wire defintions then link them up to the
                        // relevant components
                        resolveWires(composite);
                        validateCompositeDefintion(composite);
                        //verifyCompositeCompleteness(composite);
                        done = true;
                        break;
                    }
            }
        }
        processJavaInterfaces(composite);
        for (ComponentDefinition<? extends Implementation<?>> c : composite.getComponents()
            .values()) {
            processJavaInterfaces(c.getImplementation().getComponentType());
            PropertyHelper.processProperties(composite, c, deploymentContext);
        }
        return composite;
    }

    protected void processJavaInterfaces(ComponentType componentType) throws LoaderException {
        if (processorRegistry == null) {
            return;
        }
        try {
            for (Object s : componentType.getServices().values()) {
                ServiceContract<?> contract = ((ServiceDefinition)s).getServiceContract();
                if (JavaServiceContract.class.isInstance(contract)) {
                    contract =
                        processorRegistry.introspect(contract.getInterfaceClass(), contract
                            .getCallbackClass(), true);
                    ((ServiceDefinition)s).setServiceContract(contract);
                }
            }
            for (Object r : componentType.getReferences().values()) {
                ServiceContract<?> contract = ((AbstractReferenceDefinition)r).getServiceContract();
                if (JavaServiceContract.class.isInstance(contract)) {
                    contract =
                        processorRegistry.introspect(contract.getInterfaceClass(), contract
                            .getCallbackClass(), true);
                    ((AbstractReferenceDefinition)r).setServiceContract(contract);
                }
            }
        } catch (InvalidServiceContractException e) {
            throw new LoaderException(e);
        }

    }

    protected void resolveWires(CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>> composite) throws InvalidWireException,
                                                                                                                               InvalidPromotedReferenceException {
        QualifiedName sourceName;
        ComponentDefinition componentDefinition;
        ServiceDefinition serviceDefinition;
        List<WireDefinition> wireDefns = composite.getDeclaredWires();
        for (WireDefinition wire : wireDefns) {
            URI targetUri = wire.getTarget();
            // validate the target before finding the source
            validateTarget(targetUri, composite);

            sourceName = new QualifiedName(wire.getSource().getPath());
            serviceDefinition = composite.getDeclaredServices().get(sourceName.getPartName());
            if (serviceDefinition != null) {
                serviceDefinition.setTarget(wire.getTarget());
            } else {
                componentDefinition =
                    composite.getDeclaredComponents().get(sourceName.getPartName());
                if (componentDefinition != null) {
                    if (sourceName.getPortName() == null || sourceName.getPortName().length() == 0) {
                        if (componentDefinition.getReferences().size() > 1 || componentDefinition
                            .getReferences().isEmpty()) {
                            throw new InvalidWireException("Unable to determine unique source reference");
                        } else {
                            ComponentReferenceDefinition ref =
                                (ComponentReferenceDefinition)componentDefinition.getReferences()
                                    .values().iterator().next();
                            ref.addTarget(targetUri);
                        }
                    } else {
                        ((ComponentReferenceDefinition)componentDefinition.getReferences()
                            .get(sourceName.getPortName())).addTarget(targetUri);
                    }

                } else {
                    throw new InvalidWireException("Source not found", sourceName.toString());
                }
            }
        }

        QualifiedName targetName = null;
        ComponentReferenceDefinition promotedComponentRef = null;
        for (CompositeReferenceDefinition compositeRefDef : composite.getDeclaredReferences()
            .values()) {
            for (URI promotedComponentRefUri : compositeRefDef.getPromotedReferences()) {
                targetName = new QualifiedName(promotedComponentRefUri.toString());
                componentDefinition = composite.getComponents().get(targetName.getPartName());
                if (componentDefinition != null) {
                    if (targetName.getPortName() != null) {
                        promotedComponentRef =
                            (ComponentReferenceDefinition)componentDefinition.getReferences()
                                .get(targetName.getPortName());
                    } else {
                        promotedComponentRef =
                            (ComponentReferenceDefinition)componentDefinition.getReferences()
                                .values().iterator().next();
                    }
                    if (promotedComponentRef != null) {
                        promotedComponentRef.addTarget(URI.create(compositeRefDef.getName()));
                    } else {
                        throw new InvalidPromotedReferenceException("Invalid promoted reference ",
                                                                    targetName.toString());
                    }
                } else {
                    throw new InvalidPromotedReferenceException("Invalid promoted reference ",
                                                                targetName.toString());
                }

            }
        }
    }

    protected void validateCompositeDefintion(
        CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>> composite) 
            throws LoaderException {
        verifyCompositeCompleteness(composite);
        validateCompositeReferenceDefinition(composite);
    }

    protected void verifyCompositeCompleteness(
        CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>> composite) 
            throws InvalidServiceException {
        // check if all of the composite services have been wired
        for (ServiceDefinition svcDefn : composite.getDeclaredServices().values()) {
            if (svcDefn.getTarget() == null) {
                throw new InvalidServiceException("Composite service not wired to a target",
                                                  svcDefn.getName());
            }
        }
    }
    
    protected void verifyReferenceInterfaceCompatibility(CompositeReferenceDefinition compositeRefDefn, 
                                                ComponentReferenceDefinition componentRefDefn) 
                                                throws LoaderException {
        if (compositeRefDefn.getServiceContract() != null) {
            //TODO : Since the JavaInterfaceProcessorRegistryImpl does not do a deep introspection
            //this comparison is not possible.  This will be uncommented once that is fixed.
            /*ReferenceLoaderHelper.checkInterfaceCompatibility(componentRefDefn.getServiceContract(),
                                                              compositeRefDefn.getServiceContract(),
                                                              false);*/
        } else {
            //FIXME : the wireservice needs a service contract in the composite ref. def
            //so filling up with one of the promoted component ref. defn's service contract
            compositeRefDefn.setServiceContract(componentRefDefn.getServiceContract());
        }
    }
    
    protected Multiplicity deriveReferenceMultiplicity(CompositeReferenceDefinition compositeRefDefn, 
                                                       ComponentReferenceDefinition componentRefDefn,
                                                       Multiplicity leastMultiplicity) 
                                                            throws LoaderException
    {
        if (compositeRefDefn.getMultiplicity() != null) {
            if (!ReferenceLoaderHelper.
                isValidMultiplicityOverride(componentRefDefn.getMultiplicity(), 
                                            compositeRefDefn.getMultiplicity())) {
               throw new ReferenceMultiplicityOverridingException(compositeRefDefn.getName(), 
                                                                  componentRefDefn.getMultiplicity(),
                                                                  compositeRefDefn.getMultiplicity());
           } 
        } else {
            if (leastMultiplicity != null) {
                if (!ReferenceLoaderHelper.isCompatibleMultiplicity(componentRefDefn.getMultiplicity(),
                                                                    leastMultiplicity)) {
                    throw new ReferenceMultiplicityOverridingException(compositeRefDefn.getName(), 
                                                                       componentRefDefn.getMultiplicity(),
                                                                       leastMultiplicity);
                } else {
                    if (!ReferenceLoaderHelper.
                        isValidMultiplicityOverride(componentRefDefn.getMultiplicity(), 
                                                    leastMultiplicity)) {
                        leastMultiplicity = componentRefDefn.getMultiplicity();
                    }
                }
            }
            else {
                leastMultiplicity = componentRefDefn.getMultiplicity();
            }
        }
        return leastMultiplicity;
    }
    
    protected void validateCompositeReferenceDefinition(CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>> composite) throws LoaderException {
        QualifiedName promotedName = null;
        ComponentDefinition promotedComponentDefinition = null;
        ComponentReferenceDefinition promotedComponentReference = null;

        for (CompositeReferenceDefinition compositeRefDefn : composite.getDeclaredReferences().values()) {
            // ensure if there is a service contract defined, then it is
            // compatible with all
            // promoted component reference interfaces
            Multiplicity leastMultiplicity = null;
            boolean bindingsOverriden = compositeRefDefn.getBindings().size() > 0;
            for (URI promotedRef : compositeRefDefn.getPromotedReferences()) {
                // check for valid promotions
                promotedName = new QualifiedName(promotedRef.toString());
                promotedComponentDefinition =
                    composite.getComponents().get(promotedName.getPartName());
                if (promotedComponentDefinition != null) {
                    if (promotedName.getPortName() != null) {
                        promotedComponentReference =
                            (ComponentReferenceDefinition)promotedComponentDefinition
                                .getReferences().get(promotedName.getPortName());
                    } else {
                        promotedComponentReference =
                            (ComponentReferenceDefinition)promotedComponentDefinition
                                .getReferences().values().iterator().next();
                    }
                    // check for service contract compatibility
                    if (promotedComponentReference != null) {
                        verifyReferenceInterfaceCompatibility(compositeRefDefn,
                                                              promotedComponentReference);
                        leastMultiplicity =
                            deriveReferenceMultiplicity(compositeRefDefn,
                                                        promotedComponentReference,
                                                        leastMultiplicity);

                        // if bindings have not been overridden int the composite then copy them
                        // over for convenience so that when accessed, traversing of all promoted
                        // references can be avoided
                        if (!bindingsOverriden) {
                            for (BindingDefinition refBinding : promotedComponentReference
                                .getBindings()) {
                                compositeRefDefn.addBinding((BindingDefinition)refBinding.clone());
                            }
                        }
                    } else {
                        throw new InvalidPromotedReferenceException("Invalid promoted reference ",
                                                                    promotedRef.toString());
                    }
                } else {
                    throw new InvalidPromotedReferenceException("Invalid promoted reference ",
                                                                promotedRef.toString());
                }

            }
            if (compositeRefDefn.getMultiplicity() == null) {
                compositeRefDefn.setMultiplicity(leastMultiplicity);
            }

        }
    }

    private void validateTarget(URI target,
                                CompositeComponentType<ServiceDefinition, CompositeReferenceDefinition, Property<?>> composite) throws InvalidWireException {
        QualifiedName targetName = new QualifiedName(target.getPath());
        // if target is not a reference of the composite
        if (composite.getReferences().get(targetName.getPartName()) == null) {
            ComponentDefinition<?> targetDefinition =
                composite.getDeclaredComponents().get(targetName.getPartName());
            // if a target component exists in this composite
            if (targetDefinition != null) {
                Implementation<?> implementation = targetDefinition.getImplementation();
                ComponentType<?, ?, ?> componentType = implementation.getComponentType();
                Map<String, ? extends ServiceDefinition> services = componentType.getServices();
                if (targetName.getPortName() == null) {
                    if (services.size() > 1 || services.isEmpty()) {
                        throw new InvalidWireException("Ambiguous target", targetName.toString());
                    }
                } else {
                    if (services.get(targetName.getPortName()) == null) {
                        throw new InvalidWireException("Invalid target service", targetName
                            .toString());
                    }
                }
            } else {
                throw new InvalidWireException("Target not found", targetName.toString());
            }
        }
    }
}
