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
package org.apache.tuscany.api.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation that can be applied to interfaces or methods to provide IDL mapping metadata 
 * 
 * @version $Rev$ $Date$
 */
@Target( {METHOD, TYPE})
@Retention(RUNTIME)
public @interface IDLMapping {

    /**
     * The name of the databinding for the wrapper
     */
    String dataBinding() default "";
    
    /**
     * To indicate if the java interface/method is generated from a WSDL using wrapper style.
     * 
     * @see javax.xml.ws.RequestWrapper
     * @see javax.xml.ws.RequestWrapper
     * 
     * @return
     */
    boolean wrapperStyle() default true;
}
