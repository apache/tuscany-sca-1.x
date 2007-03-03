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
import java.util.Arrays;
import java.util.List;

import org.apache.tuscany.api.SCAContainer;
import org.apache.tuscany.api.TuscanyException;
import org.apache.tuscany.core.component.ComponentContextImpl;
import org.apache.tuscany.core.launcher.CompositeContextImpl;
import org.apache.tuscany.core.runtime.mini.SimpleRuntime;
import org.apache.tuscany.core.runtime.mini.SimpleRuntimeImpl;
import org.apache.tuscany.core.runtime.mini.SimpleRuntimeInfo;
import org.apache.tuscany.core.runtime.mini.SimpleRuntimeInfoImpl;
import org.apache.tuscany.spi.component.Component;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.component.TargetResolutionException;
import org.osoa.sca.ComponentContext;
import org.osoa.sca.CurrentCompositeContext;
import org.osoa.sca.ServiceUnavailableException;

/**
 * Base class for JUnit tests that want to run in an SCA client environment.
 * 
 * @version $Rev$ $Date$
 */
@SuppressWarnings("deprecation")
public class DefaultSCAContainer extends SCAContainer {
    protected CompositeComponent application;
    protected CompositeContextImpl context;

    protected SimpleRuntime runtime;

    protected void startup(URL system, URL[] exts, URL applicationSCDL, String compositePath) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        List<URL> extensions = exts == null ? null : Arrays.asList(exts);
        SimpleRuntimeInfo runtimeInfo =
            new SimpleRuntimeInfoImpl(cl, system, extensions, applicationSCDL, compositePath);
        runtime = new SimpleRuntimeImpl(runtimeInfo);

        try {
            application = runtime.start();
        } catch (TuscanyException e) {
            throw e;
        }

    }

    protected void shutdown() throws Exception {
        runtime.destroy();
    }

    @Override
    protected ComponentContext getContext(String componentName) {
        CompositeComponent composite = ((CompositeContextImpl) context).getComposite();
        Component component = (Component) composite.getChild(componentName);
        return new ComponentContextImpl(CurrentCompositeContext.getContext(), component);
    }

    public CompositeComponent getCompsiteComponent() {
        return application;
    }

    @Override
    public Object getSystemService(String serviceName) {
        try {
            return runtime.getSystemService(Object.class, serviceName);
        } catch (TargetResolutionException e) {
            throw new ServiceUnavailableException(e);
        }
    }
}
