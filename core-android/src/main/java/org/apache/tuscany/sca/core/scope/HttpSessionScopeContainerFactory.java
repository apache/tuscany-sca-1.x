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

package org.apache.tuscany.sca.core.scope;

import org.apache.tuscany.sca.runtime.RuntimeComponent;

/**
 * @version $Rev: 568826 $ $Date: 2007-08-22 22:27:34 -0700 (Wed, 22 Aug 2007) $
 */
public class HttpSessionScopeContainerFactory implements ScopeContainerFactory {

    public HttpSessionScopeContainerFactory() {
        super();
    }

    public ScopeContainer createScopeContainer(RuntimeComponent component) {
        return new HttpSessionScopeContainer(component);
    }

    public Scope getScope() {
        return Scope.SESSION;
    }

}
