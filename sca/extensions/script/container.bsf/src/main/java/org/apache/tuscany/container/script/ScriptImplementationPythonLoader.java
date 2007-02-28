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

import static org.osoa.sca.Constants.SCA_NS;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.component.CompositeComponent;
import org.apache.tuscany.spi.deployer.DeploymentContext;
import org.apache.tuscany.spi.loader.LoaderException;
import org.apache.tuscany.spi.loader.LoaderRegistry;
import org.apache.tuscany.spi.loader.LoaderUtil;
import org.apache.tuscany.spi.loader.MissingResourceException;
import org.apache.tuscany.spi.model.ModelObject;
import org.osoa.sca.annotations.Constructor;

public class ScriptImplementationPythonLoader extends ScriptImplementationLoader {

    private static final QName IMPLEMENTATION_PYTHON = new QName(SCA_NS, "implementation.python");

    @Constructor({"registry"})
    public ScriptImplementationPythonLoader(@Autowire LoaderRegistry registry) {
        super(registry);
    }

    public QName getXMLType() {
        return IMPLEMENTATION_PYTHON;
    }

    @Override
    public ScriptImplementation load(CompositeComponent parent, ModelObject mo, XMLStreamReader reader,
                                     DeploymentContext deploymentContext) throws XMLStreamException, LoaderException {
        String scriptName = reader.getAttributeValue(null, "module");
        if (scriptName == null) {
            throw new MissingResourceException("implementation element has no 'module' attribute");
        }

        String className = reader.getAttributeValue(null, "class");

        LoaderUtil.skipToEndElement(reader);

        ClassLoader cl = deploymentContext.getClassLoader();
        String scriptSource = loadSource(cl, scriptName);

        ScriptImplementation implementation = new ScriptImplementation();
        implementation.setResourceName(scriptName);
        implementation.setScriptSource(scriptSource);
        implementation.setClassName(className);
        implementation.setScriptName(scriptName);
        implementation.setClassLoader(cl);

        registry.loadComponentType(parent, implementation, deploymentContext);

        return implementation;
    }

}
