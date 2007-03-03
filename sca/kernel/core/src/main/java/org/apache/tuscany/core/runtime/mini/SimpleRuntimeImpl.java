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
package org.apache.tuscany.core.runtime.mini;

import static org.apache.tuscany.spi.bootstrap.ComponentNames.TUSCANY_ASSEMBLY_SERVICE;
import static org.apache.tuscany.spi.bootstrap.ComponentNames.TUSCANY_CONTRIBUTION_SERVICE;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.api.annotation.LogLevel;
import org.apache.tuscany.core.launcher.CompositeContextImpl;
import org.apache.tuscany.core.monitor.JavaLoggingMonitorFactory;
import org.apache.tuscany.core.runtime.AbstractRuntime;
import org.apache.tuscany.core.services.deployment.AssemblyServiceImpl;
import org.apache.tuscany.host.MonitorFactory;
import org.apache.tuscany.host.RuntimeInfo;
import org.apache.tuscany.host.deployment.AssemblyService;
import org.apache.tuscany.host.deployment.ContributionService;
import org.apache.tuscany.host.monitor.FormatterRegistry;
import org.apache.tuscany.host.runtime.InitializationException;
import org.apache.tuscany.spi.component.AtomicComponent;
import org.apache.tuscany.spi.component.ComponentRegistrationException;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.SCAObject;
import org.apache.tuscany.spi.component.TargetResolutionException;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * @version $Rev$ $Date$
 */
public class SimpleRuntimeImpl extends AbstractRuntime implements SimpleRuntime {
    protected JavaLoggingMonitorFactory monitorFactory;
    protected SimpleMonitor monitor;
    protected CompositeComponent application;

    public SimpleRuntimeImpl(SimpleRuntimeInfo runtimeInfo) {
        super();
        monitorFactory = new JavaLoggingMonitorFactory();
        setMonitorFactory(monitorFactory);
        monitor = monitorFactory.getMonitor(SimpleMonitor.class);
        ClassLoader hostClassLoader = ClassLoader.getSystemClassLoader();
        setHostClassLoader(hostClassLoader);
        setSystemScdl(runtimeInfo.getSystemSCDL());
        setRuntimeInfo(runtimeInfo);
    }

    public interface SimpleMonitor {
        @LogLevel("SEVERE")
        void runError(Exception e);
    }

    public CompositeComponent start() throws Exception {
        initialize();
        ContributionService contributionService =
            getSystemService(ContributionService.class, TUSCANY_CONTRIBUTION_SERVICE);
        CompositeComponent composite = getTuscanySystem();
        // TODO: Make assembly service a pluggable component?
        AssemblyService assemblyService = new AssemblyServiceImpl(contributionService, composite);
        composite.registerJavaObject(TUSCANY_ASSEMBLY_SERVICE, AssemblyService.class, assemblyService);

        SimpleRuntimeInfo runtimeInfo = (SimpleRuntimeInfo)getRuntimeInfo();
        int i = 0;
        for (URL ext : runtimeInfo.getExtensionSCDLs()) {
            CompositeComponent extensionComponent =
                deploySystemScdl(getDeployer(), getTuscanySystem(), "tuscany.extension." + (i++), ext, runtimeInfo
                    .getClassLoader());
            extensionComponent.start();
        }

        URI contributionId = contributionService.contribute(runtimeInfo.getContributionRoot(), false);
        URI compositeDefinitionId = contributionId.resolve(runtimeInfo.getCompositePath());

        application =
            (CompositeComponent)assemblyService.addCompositeToDomain(contributionId, compositeDefinitionId);

        CompositeContext context = new CompositeContextImpl(application, getWireService());
        CurrentCompositeContext.setContext(context);
        return application;
    }

    public <T> T getSystemService(Class<T> type, String name) throws TargetResolutionException {
        SCAObject child = getTuscanySystem().getSystemChild(name);
        if (child == null) {
            return null;
        }
        AtomicComponent service = (AtomicComponent)child;
        return type.cast(service.getTargetInstance());
    }

    @Override
    protected void registerSystemComponents() throws InitializationException {
        try {
            // initialize the runtime info
            CompositeComponent parent = getSystemComponent();
            parent.registerJavaObject("RuntimeInfo", RuntimeInfo.class, getRuntimeInfo());

            // register the monitor factory
            if (monitorFactory instanceof FormatterRegistry) {
                List<Class<?>> interfazes = new ArrayList<Class<?>>(2);
                interfazes.add(MonitorFactory.class);
                interfazes.add(FormatterRegistry.class);
                parent.registerJavaObject("MonitorFactory", interfazes, monitorFactory);
            } else {
                parent.registerJavaObject("MonitorFactory", MonitorFactory.class, monitorFactory);
            }
        } catch (ComponentRegistrationException e) {
            throw new InitializationException(e);
        }
    }

    @Override
    public void destroy() {
        CurrentCompositeContext.setContext(null);
        application.stop();
        super.destroy();
    }

}
