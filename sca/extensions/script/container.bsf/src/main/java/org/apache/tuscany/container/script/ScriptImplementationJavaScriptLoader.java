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

package org.apache.tuscany.container.script;

import static org.osoa.sca.Version.XML_NAMESPACE_1_0;

import javax.xml.namespace.QName;

import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.osoa.sca.annotations.Constructor;

public class ScriptImplementationJavaScriptLoader extends ScriptImplementationLoader {

    private static final QName IMPLEMENTATION_JS = new QName(XML_NAMESPACE_1_0, "implementation.js");

    @Constructor({"registry"})
    public ScriptImplementationJavaScriptLoader(@Autowire LoaderRegistry registry) {
        super(registry);
    }

    public QName getXMLType() {
        return IMPLEMENTATION_JS;
    }

}
