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
package org.osoa.sca.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate an injection site for the SCA identity of a component.
 * Every component in a SCA Domain has a unique identity that derived from the base URI of the domain.
 * This annotation can be used to indicate to the container that a component implemention requires that
 * its identity be injected. The annotation can be applied to:
 * <ul>
 * <li>a public or protected field with type @{link java.lang.String} or @{link java.net.URI}</li>
 * <li>a public or protected method with a single parameter with type @{link java.lang.String} or @{link java.net.URI}</li>
 * <li>a parameter of a public constructor with type @{link java.lang.String} or @{link java.net.URI}</li>
 * </ul>
 *
 * @version $Rev$ $Date$
 */
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ComponentID {
}
