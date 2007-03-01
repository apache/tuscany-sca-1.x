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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.tuscany.api.SCAContainer;
import org.apache.tuscany.api.TuscanyException;
import org.apache.tuscany.core.implementation.system.model.SystemCompositeImplementation;
import org.apache.tuscany.core.launcher.CompositeContextImpl;
import org.apache.tuscany.core.launcher.LauncherImpl;
import org.apache.tuscany.core.monitor.JavaLoggingMonitorFactory;
import org.apache.tuscany.core.services.deployment.AssemblyServiceImpl;
import org.apache.tuscany.core.util.FileHelper;
import org.apache.tuscany.host.MonitorFactory;
import org.apache.tuscany.host.deployment.AssemblyService;
import org.apache.tuscany.host.deployment.ContributionService;
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
public class DefaultSCAContainer extends SCAContainer {
    protected CompositeComponent component;
    protected CompositeContextImpl context;
    protected LauncherImpl launcher;
    protected MonitorFactory monitorFactory;
    // lresende - contribution
    protected AssemblyService assemblyService;
    protected ContributionService contributionService;

    protected void startup(URL system, URL[] exts, URL applicationSCDL, String compositePath) throws Exception {
        if (monitorFactory == null) {
            monitorFactory = new JavaLoggingMonitorFactory();
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        launcher = new LauncherImpl();
        launcher.setApplicationLoader(cl);

        if (system == null) {
            system = cl.getResource(SCAContainer.SYSTEM_SCDL);
            if (system == null) {
                system = cl.getResource(SCAContainer.DEFAULT_SYSTEM_SCDL);
            }
        }

        try {
            CompositeComponent composite = launcher.bootRuntime(system, monitorFactory);
            // lresende - contribution
            AtomicComponent csComponent =
                (AtomicComponent)composite.getSystemChild(ComponentNames.TUSCANY_CONTRIBUTION_SERVICE);
            contributionService = (ContributionService)csComponent.getTargetInstance();

            // TODO: Make assembly service a pluggable component?
            this.assemblyService = new AssemblyServiceImpl(contributionService, composite);
            composite.registerJavaObject(ComponentNames.TUSCANY_ASSEMBLY_SERVICE,
                                         AssemblyService.class,
                                         assemblyService);

            List<URL> extensions = new ArrayList<URL>();
            Enumeration<URL> urls = cl.getResources(SCAContainer.SERVICE_SCDL);
            extensions.addAll(Collections.list(urls));
            urls = cl.getResources(SCAContainer.EXTENSION_SCDL);
            extensions.addAll(Collections.list(urls));
            if (exts != null) {
                for (URL ext : exts) {
                    if (!extensions.contains(ext)) {
                        extensions.add(ext);
                    }
                }
            }
            int i = 0;
            for (URL ext : extensions) {
                deployExtension(composite, "tuscany.extension." + (i++), ext);
            }

            SCAObject wireServiceComponent = composite.getSystemChild(ComponentNames.TUSCANY_WIRE_SERVICE);
            if (!(wireServiceComponent instanceof AtomicComponent)) {
                throw new InitializationException("WireService must be an atomic component");
            }

            WireService wireService = (WireService)((AtomicComponent)wireServiceComponent).getTargetInstance();

            // Start using contribution services
            if (applicationSCDL == null) {
                applicationSCDL = cl.getResource(SCAContainer.APPLICATION_SCDL);
                if (applicationSCDL == null) {
                    applicationSCDL = cl.getResource(SCAContainer.META_APPLICATION_SCDL);
                    if (applicationSCDL != null) {
                        compositePath = SCAContainer.META_APPLICATION_SCDL;
                    }
                } else {
                    if (compositePath == null) {
                        compositePath = SCAContainer.APPLICATION_SCDL;
                    }
                }
                if (applicationSCDL == null) {
                    throw new RuntimeException("application SCDL not found: " + SCAContainer.APPLICATION_SCDL);
                }
            }

            // lresende - contribution
            URL contributionLocation = getContributionLocation(applicationSCDL, compositePath);
            URI contributionId = this.contributionService.contribute(contributionLocation, false);
            URI compositeDefinitionId = contributionId.resolve(compositePath);

            component =
                (CompositeComponent)this.assemblyService.addCompositeToDomain(contributionId, compositeDefinitionId);

            context = new CompositeContextImpl(component, wireService);
            CurrentCompositeContext.setContext(context);
        } catch (TuscanyException e) {
            DeploymentMonitor monitor = monitorFactory.getMonitor(DeploymentMonitor.class);
            monitor.deploymentError(e);
            throw e;
        }

    }

    private URL getContributionLocation(URL applicationSCDL, String compositePath) {
        URL root = null;

        // "jar:file://....../something.jar!/a/b/c/app.composite"

        try {
            String scdlUrl = applicationSCDL.toExternalForm();
            String protocol = applicationSCDL.getProtocol();
            if ("file".equals(protocol)) {
                // directory contribution
                if (scdlUrl.endsWith(compositePath)) {
                    String location = scdlUrl.substring(0, scdlUrl.lastIndexOf(compositePath));
                    // workaround from evil url/uri form maven
                    root = FileHelper.toFile(new URL(location)).toURI().toURL();
                }

            } else if ("jar".equals(protocol)) {
                // jar contribution
                String location = scdlUrl.substring(4, scdlUrl.lastIndexOf("!/"));
                // workaround from evil url/uri form maven
                root = FileHelper.toFile(new URL(location)).toURI().toURL();
            }
        } catch (MalformedURLException mfe) {
            throw new IllegalArgumentException(mfe);
        }

        return root;
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

    public CompositeComponent getCompsiteComponent() {
        return component;
    }
    
    @Override
    public Object getSystemService(String serviceName){

        if(serviceName.equals(ComponentNames.TUSCANY_CONTRIBUTION_SERVICE)) {
            return this.contributionService;
        } else if(serviceName.equals(ComponentNames.TUSCANY_ASSEMBLY_SERVICE)) {
            return this.assemblyService;
        } else {
            return null;
        }
    }
}
