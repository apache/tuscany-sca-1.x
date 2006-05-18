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
 * Class providing access to the current ModuleContext.
 * <p/>
 * The current context is set by the SCA container implementation before dispatching
 * a request to any component in the module.
 *
 * @version $Rev$ $Date$
 */
public final class CurrentModuleContext {
    private static final ThreadLocal<ModuleContext> CURRENT_CONTEXT = new InheritableThreadLocal<ModuleContext>();

    /**
     * Return the current ModuleContext.
     *
     * @return the current ModuleContext
     */
    public static ModuleContext getContext() {
        return CURRENT_CONTEXT.get();
    }

    static void setContext(ModuleContext ctx) {
        CURRENT_CONTEXT.set(ctx);
    }
}
