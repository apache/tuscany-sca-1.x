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

package org.apache.tuscany.databinding.sdo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.tuscany.spi.databinding.TransformationContext;
import org.apache.tuscany.spi.databinding.WrapperHandler;
import org.apache.tuscany.spi.idl.ElementInfo;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.XMLDocument;
import commonj.sdo.helper.XMLHelper;

/**
 * SDO Wrapper Handler
 */
public class SDOWrapperHandler implements WrapperHandler<Object> {

    /**
     * @see org.apache.tuscany.spi.databinding.WrapperHandler#create(ElementInfo,
     *      TransformationContext)
     */
    public Object create(ElementInfo element, TransformationContext context) {
        HelperContext helperContext = SDODataTypeHelper.getHelperContext(context);
        QName typeName = element.getType().getQName();
        DataFactory dataFactory = helperContext.getDataFactory();
        DataObject root = dataFactory.create(typeName.getNamespaceURI(), typeName.getLocalPart());
        XMLHelper xmlHelper = helperContext.getXMLHelper();
        return xmlHelper.createDocument(root, element.getQName().getNamespaceURI(), element.getQName().getLocalPart());
    }

    /**
     * @see org.apache.tuscany.spi.databinding.WrapperHandler#setChild(java.lang.Object,
     *      int, ElementInfo, java.lang.Object)
     */
    public void setChild(Object wrapper, int i, ElementInfo childElement, Object value) {
        DataObject wrapperDO =
            (wrapper instanceof XMLDocument) ? ((XMLDocument)wrapper).getRootObject() : (DataObject)wrapper;
        wrapperDO.set(childElement.getQName().getLocalPart(), value);
    }

    public List getChildren(Object wrapper) {
        DataObject wrapperDO =
            (wrapper instanceof XMLDocument) ? ((XMLDocument)wrapper).getRootObject() : (DataObject)wrapper;
        List properties = wrapperDO.getInstanceProperties();
        List<Object> elements = new ArrayList<Object>();
        for (int i = 0; i < properties.size(); i++) {
            elements.add(wrapperDO.get(i));
        }
        return elements;
    }

}
