package org.apache.tuscany.test.binding;

import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.ReferenceBinding;
import org.apache.tuscany.spi.component.ServiceBinding;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.extension.BindingBuilderExtension;
import org.apache.tuscany.spi.model.ReferenceDefinition;
import org.apache.tuscany.spi.model.ServiceDefinition;

/**
 * @version $Rev$ $Date$
 */
public class TestSocketBindingBuilder extends BindingBuilderExtension<TestSocketBindingDefinition> {

    public ServiceBinding build(CompositeComponent parent,
                                ServiceDefinition definition,
                                TestSocketBindingDefinition bindingDefinition,
                                DeploymentContext ctx) {
        int port = bindingDefinition.getPort();
        return new TestSocketBindingServiceBinding(definition.getName(), port, parent);
    }

    public ReferenceBinding build(CompositeComponent parent,
                                  ReferenceDefinition definition,
                                  TestSocketBindingDefinition bindingDefinition,
                                  DeploymentContext ctx) {
        String name = definition.getName();
        int port = bindingDefinition.getPort();
        String host = bindingDefinition.getHost();
        return new TestSocketReferenceBinding(name, host, port, parent);
    }

    protected Class<TestSocketBindingDefinition> getBindingType() {
        return TestSocketBindingDefinition.class;
    }
}
