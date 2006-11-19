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
package org.apache.tuscany.container.spring.integration;

import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

import org.apache.tuscany.spi.component.Service;

import org.apache.tuscany.container.spring.impl.SpringCompositeComponent;
import org.apache.tuscany.container.spring.mock.TestBean;
import org.apache.tuscany.test.Bootstrapper;

/**
 * Bootstraps a simple scenario where a service can invoke a Spring bean. This test case is intended to be temporary and
 * replaced when the SPI test harness is finished.
 * <p/>
 * <bold>PLEASE DO NOT EMULATE</bold>
 *
 * @version $Rev$ $Date$
 */
public class BootstrapTestCase extends Bootstrapper {

    private CompositeContext context;

    public void testDemoBoot() {
        SpringCompositeComponent comp = (SpringCompositeComponent) component.getChild("Spring");
        Service service = (Service) comp.getChild("fooService");
        TestBean bean = (TestBean) service.getServiceInstance();
        bean.echo("foo");
        bean.getBean().echo("foo");
    }

    protected void setUp() throws Exception {
        addExtension("spring.extension", getClass().getClassLoader().getResource("META-INF/sca/spring.system.scdl"));
        super.setUp();
        context = CurrentCompositeContext.getContext();
    }


}
