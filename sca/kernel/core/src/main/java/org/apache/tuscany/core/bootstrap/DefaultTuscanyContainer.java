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
package org.apache.tuscany.core.bootstrap;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;

import org.apache.tuscany.api.TuscanyContainer;
import org.apache.tuscany.api.TuscanyException;
import org.apache.tuscany.core.implementation.system.model.SystemCompositeImplementation;
import org.apache.tuscany.core.launcher.CompositeContextImpl;
import org.apache.tuscany.core.launcher.LauncherImpl;
import org.apache.tuscany.core.monitor.JavaLoggingMonitorFactory;
import org.apache.tuscany.host.MonitorFactory;
import org.apache.tuscany.host.runtime.InitializationException;
import org.apache.tuscany.spi.bootstrap.ComponentNames;
import org.apache.tuscany.spi.builder.BuilderException;
import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.component.Component;
import org.apache.tuscany.spi.component.ComponentException;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.SCAObject;
import org.apache.tuscany.spi.deployer.Deployer;
import org.apache.tuscany.spi.deployer.DeploymentMonitor;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.model.ComponentDefinition;
import org.apache.tuscany.spi.wire.WireService;
import org.osoa.sca.ComponentContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * Base class for JUnit tests that want to run in an SCA client environment.
 * 
 * @version $Rev$ $Date$
 */
@SuppressWarnings("deprecation")
public class DefaultTuscanyContainer extends TuscanyContainer {
    protected CompositeComponent component;
    private CompositeContextImpl context;
    private LauncherImpl launcher;
    private MonitorFactory monitorFactory;

    protected void startup(URL system, URL[] exts, URL applicationSCDL) throws Exception {
        if (monitorFactory == null) {
            monitorFactory = new JavaLoggingMonitorFactory();
        }
        ClassLoader cl = getClass().getClassLoader();
        launcher = new LauncherImpl();
        launcher.setApplicationLoader(cl);

        if (system == null) {
            system = cl.getResource(TuscanyContainer.SYSTEM_SCDL);
        }

        try {
            CompositeComponent composite = launcher.bootRuntime(system, monitorFactory);
            if (exts == null) {
                Enumeration<URL> urls = cl.getResources(TuscanyContainer.EXTENSION_SCDL);
                exts = Collections.list(urls).toArray(new URL[0]);
            }
            for (int i=0;i<exts.length;i++) {
                deployExtension(composite, "tuscany.extension." + (i++), exts[i]);
            }

            SCAObject wireServiceComponent = composite.getSystemChild(ComponentNames.TUSCANY_WIRE_SERVICE);
            if (!(wireServiceComponent instanceof AtomicComponent)) {
                throw new InitializationException("WireService must be an atomic component");
            }

            WireService wireService = (WireService)((AtomicComponent)wireServiceComponent).getTargetInstance();

            if (applicationSCDL == null) {
                applicationSCDL = cl.getResource(TuscanyContainer.APPLICATION_SCDL);
                throw new RuntimeException("application SCDL not found: " + applicationSCDL);
            }
            component = launcher.bootApplication("application", applicationSCDL);
            component.start();
            context = new CompositeContextImpl(component, wireService);
            CurrentCompositeContext.setContext(context);
        } catch (TuscanyException e) {
            DeploymentMonitor monitor = monitorFactory.getMonitor(DeploymentMonitor.class);
            monitor.deploymentError(e);
            throw e;
        }

    }

    /**
     * Sets the monitor factory to use
     * 
     * @param monitorFactory the monitor factory to use
     */
    protected void setMonitorFactory(MonitorFactory monitorFactory) {
        this.monitorFactory = monitorFactory;
    }

    protected void deployExtension(CompositeComponent composite, String extensionName, URL scdlURL)
        throws LoaderException, BuilderException, ComponentException, InitializationException {
        SystemCompositeImplementation implementation = new SystemCompositeImplementation();
        implementation.setScdlLocation(scdlURL);
        implementation.setClassLoader(new URLClassLoader(new URL[] {scdlURL}, getClass().getClassLoader()));

        ComponentDefinition<SystemCompositeImplementation> definition =
            new ComponentDefinition<SystemCompositeImplementation>(extensionName, implementation);

        SCAObject child = composite.getSystemChild(ComponentNames.TUSCANY_DEPLOYER);
        if (!(child instanceof AtomicComponent)) {
            throw new InitializationException("Deployer must be an atomic component");
        }
        Deployer deployer = (Deployer)((AtomicComponent)child).getTargetInstance();
        Component component = deployer.deploy(composite, definition);
        component.start();
    }

    protected void shutdown() throws Exception {
        CurrentCompositeContext.setContext(null);
        component.stop();
        launcher.shutdownRuntime();
    }

    @Override
    protected ComponentContext getContext(String componentName) {
        // TODO
        return null;
    }
}
