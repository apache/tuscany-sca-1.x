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

package org.apache.tuscany.tools.wsdl2java.dom;

import javax.xml.namespace.QName;

import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.common.model.DefaultValueWriter;
import org.apache.cxf.tools.wsdlto.core.DataBindingProfile;

/**
 * 
 */
public class DOMDataBinding implements DataBindingProfile {

    private static final String DOM_ELEMENT = "org.w3c.dom.Element";

    public DefaultValueWriter createDefaultValueWriter(QName qn, boolean element) {
        return null;
    }

    public DefaultValueWriter createDefaultValueWriterForWrappedElement(QName wrapperElement, QName qn) {
        return null;
    }

    public void generate(ToolContext context) throws ToolException {
    }

    public String getType(QName qn, boolean element) {
        return DOM_ELEMENT;
    }

    public String getWrappedElementType(QName wrapperElement, QName item) {
        return DOM_ELEMENT;
    }

    public void initialize(ToolContext c) throws ToolException {
    }

}
