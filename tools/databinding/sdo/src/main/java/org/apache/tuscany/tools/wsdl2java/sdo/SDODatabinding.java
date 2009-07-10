/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tuscany.tools.wsdl2java.sdo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.tools.common.ToolConstants;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.common.model.DefaultValueWriter;
import org.apache.cxf.tools.wsdlto.core.DataBindingProfile;
import org.apache.tuscany.sdo.api.SDOUtil;
import org.apache.tuscany.sdo.generate.JavaGenerator;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;

public class SDODatabinding extends JavaGenerator implements DataBindingProfile {

    private TypeHelper typeHelper;
    private XSDHelper xsdHelper;

    public void generate(ToolContext context) throws ToolException {
        String srcd = (String)context.get(ToolConstants.CFG_OUTPUTDIR);
        String classesd = (String)context.get(ToolConstants.CFG_CLASSDIR);

        // preparing the directories where files to be written.
        File srcDir;
        File classesDir;
        if (srcd == null) {
            String wsdl = (String)context.get(ToolConstants.CFG_WSDLLOCATION);
            try {
                srcd = new File(new URI(wsdl)).getAbsolutePath();
            } catch (URISyntaxException e) {
                srcd = new File(".").getAbsolutePath();
            }
        }
        srcDir = new File(srcd);
        srcDir.mkdirs();

        if (classesd == null) {
            classesDir = srcDir;
        } else {
            classesDir = new File(classesd);
            classesDir.mkdirs();
        }
    }

    public void initialize(ToolContext context) throws ToolException {
        String wsdl = (String)context.get(ToolConstants.CFG_WSDLLOCATION);
        xsdFileName = wsdl;

        HelperContext helperContext = SDOUtil.createHelperContext();
        xsdHelper = helperContext.getXSDHelper();
        URL location;
        try {
            location = new URL(xsdFileName);
            InputStream is = location.openStream();
            xsdHelper.define(is, xsdFileName);
        } catch (IOException e) {
            throw new ToolException(e);
        }
        this.typeHelper = helperContext.getTypeHelper();
    }

    public String getType(QName qName, boolean element) {
        Type type = null;
        if (element) {
            Property property = xsdHelper.getGlobalProperty(qName.getNamespaceURI(), qName.getLocalPart(), true);
            if (property != null) {
                type = property.getType();
            }
        }
        typeHelper.getType(qName.getNamespaceURI(), qName.getLocalPart());
        if (type != null && type.isDataType()) {
            return type.getInstanceClass().getName();
        }
        return DataObject.class.getName();
    }

    public String getWrappedElementType(QName wrapperElement, QName item) {
        Type type = null;
        Property property =
            xsdHelper.getGlobalProperty(wrapperElement.getNamespaceURI(), wrapperElement.getLocalPart(), true);
        if (property != null) {
            type = property.getType();
            Property itemProp = type.getProperty(item.getLocalPart());
            if (itemProp != null) {
                type = itemProp.getType();
            }
        }
        if (type != null && type.isDataType()) {
            return type.getInstanceClass().getName();
        }
        return DataObject.class.getName();
    }

    public DefaultValueWriter createDefaultValueWriter(QName qName, boolean b) {
        // since we dont need any sample client/server code with default values we return null
        return null;
    }

    public DefaultValueWriter createDefaultValueWriterForWrappedElement(QName qName, QName qName1) {
        // since we dont need any sample client/server code with default values we return null
        return null;
    }

    protected void run(String[] strings) {
        // We extended the JavaGenerator class in order to make use of its utility methods
        //but we are not going to implement standalone java generator.
        // So no functionality in this method.
    }

}
