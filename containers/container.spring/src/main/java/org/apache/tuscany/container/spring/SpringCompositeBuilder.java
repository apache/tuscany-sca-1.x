package org.apache.tuscany.container.spring;

import org.apache.tuscany.spi.QualifiedName;
import org.apache.tuscany.spi.builder.BuilderConfigException;
import org.apache.tuscany.spi.component.Component;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.Service;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.ComponentBuilderExtension;
import org.apache.tuscany.spi.model.Binding;
import org.apache.tuscany.spi.model.BoundReferenceDefinition;
import org.apache.tuscany.spi.model.BoundServiceDefinition;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.model.CompositeComponentType;
import org.apache.tuscany.spi.model.Property;
import org.apache.tuscany.spi.model.ReferenceDefinition;
import org.apache.tuscany.spi.model.ReferenceTarget;
import org.apache.tuscany.spi.wire.InboundInvocationChain;
import org.apache.tuscany.spi.wire.InboundWire;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Creates a {@link SpringCompositeComponent} from an assembly model
 *
 * @version $$Rev$$ $$Date$$
 */
public class SpringCompositeBuilder extends ComponentBuilderExtension<SpringImplementation> {


    public Component build(CompositeComponent<?> parent, ComponentDefinition<SpringImplementation> componentDefinition,
                                  DeploymentContext deploymentContext) throws BuilderConfigException {
        String name = componentDefinition.getName();
        GenericApplicationContext applicationContext = componentDefinition.getImplementation().getApplicationContext();
        SpringCompositeComponent context = new SpringCompositeComponent(name, applicationContext, parent,wireService);
        CompositeComponentType<BoundServiceDefinition, BoundReferenceDefinition, ? extends Property> componentType = componentDefinition.getImplementation().getComponentType();
        for (BoundServiceDefinition serviceDefinition : componentType.getServices().values()) {
            // call back into deployment context to handle building of services
            Service<?> service = (Service) builderRegistry.build(parent,
                    serviceDefinition,
                    deploymentContext);
            // wire serviceDefinition to bean invokers
            InboundWire<?> wire = service.getInboundWire();
            QualifiedName targetName = new QualifiedName(serviceDefinition.getTarget().getPath());
            for (InboundInvocationChain chain : wire.getInvocationChains().values()) {
                chain.setTargetInvoker(context.createTargetInvoker(targetName.getPartName(), chain.getMethod()));
            }
            context.register(service);
        }
        // TODO is this correct?
        for (ReferenceTarget target : componentDefinition.getReferenceTargets().values()) {
            ReferenceDefinition referenceDefinition = target.getReference();
            if (referenceDefinition instanceof BoundReferenceDefinition) {
                // call back into deployment context to handle building of references
                context.register(builderRegistry.build(parent, (BoundReferenceDefinition<? extends Binding>)
                        referenceDefinition, deploymentContext));
            }
        }
        return context;
    }

    protected Class<SpringImplementation> getImplementationType() {
        return SpringImplementation.class;
    }
}
