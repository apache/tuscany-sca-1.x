/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.osoa.sca;

/**
 * Class providing access to an SCA implementation.
 * SCA implementation providers may allow applications to bootstrap an SCA environment
 * by providing a publically available subclass.
 *
 * @version $Rev$ $Date$
 */
public abstract class SCA {
    /**
     * Protected method that allows an SCA implementation to set the current module context
     * that will be returned by {@link org.osoa.sca.CurrentModuleContext#getContext()}.
     *
     * @param ctx the new current module context
     */
    protected static void setModuleContext(ModuleContext ctx) {
        CurrentModuleContext.setContext(ctx);
    }

    /**
     * Start the SCA implementation.
     * If this method completes successfully then a module context will be associated with the current thread.
     */
    public abstract void start();

    /**
     * Stop the SCA implementation.
     * Once this method completes (successfully or abnormally) then no module context will be associated
     * with the current thread.
     */
    public abstract void stop();
}
