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
package org.apache.tuscany.runtime.webapp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionListener;

import org.osoa.sca.SCA;

import org.apache.tuscany.host.servlet.ServletRequestInjector;

/**
 * The contract for artifacts loaded in the web application classloader to comminicate with the Tuscany runtime loaded
 * in a child classloader. For example, filters and listeners may use this interface to notify the runtime of the web
 * container events.
 *
 * @version $Rev$ $Date$
 * @see TuscanyFilter
 * @see TuscanySessionListener
 */
public interface TuscanyWebappRuntime extends HttpSessionListener {
    /**
     * Initialize a runtime for the supplied servlet context.
     *
     * @param context the servlet context the runtime should run in
     */
    void initialize(ServletContext context);

    /**
     * Destroy the runtime.
     * Any further invocations should result in an error.
     */
    void destroy();

    /**
     * Returns the current SCA context
     */
    SCA getContext();

    /**
     * Returns the request injector for the runtime
     */
    ServletRequestInjector getRequestInjector();

    /**
     * Notification that the web application has begun servicing a request
     */
    void startRequest();

    /**
     * Notification that the web application has stopped servicing a request
     */
    void stopRequest();
}
