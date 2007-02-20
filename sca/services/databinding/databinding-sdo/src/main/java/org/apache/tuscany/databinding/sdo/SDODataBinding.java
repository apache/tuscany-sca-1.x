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

import javax.xml.namespace.QName;

import org.apache.tuscany.spi.databinding.ExceptionHandler;
import org.apache.tuscany.spi.databinding.SimpleTypeMapper;
import org.apache.tuscany.spi.databinding.WrapperHandler;
import org.apache.tuscany.spi.databinding.extension.DataBindingExtension;
import org.apache.tuscany.spi.model.DataType;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.CopyHelper;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.XMLDocument;
import commonj.sdo.impl.HelperProvider;

/**
 * SDO Databinding
 * 
 * @version $Reve$ $Date$
 */
public class SDODataBinding extends DataBindingExtension {
    public static final String NAME = "commonj.sdo.DataObject";
    public static final String ROOT_NAMESPACE = "commonj.sdo";
    public static final QName ROOT_ELEMENT = new QName(ROOT_NAMESPACE, "dataObject");

    private WrapperHandler<Object> wrapperHandler;

    public SDODataBinding() {
        super(DataObject.class);
        wrapperHandler = new SDOWrapperHandler();
    }

    @Override
    public DataType introspect(Class<?> javaType) {
        if (javaType == null) {
            return null;
        }
        HelperContext context = HelperProvider.getDefaultContext();
        // FIXME: Need a better to test dynamic SDO
        if (DataObject.class.isAssignableFrom(javaType)) {
            // Dynamic SDO
            return new DataType<QName>(getName(), javaType, null);
        }
        // FIXME: We need to access HelperContext
        Type type = context.getTypeHelper().getType(javaType);
        if (type == null) {
            return null;
        }
        if (type.isDataType()) {
            // FIXME: Ignore simple types?
            return null;
        }
        String namespace = type.getURI();
        String name = context.getXSDHelper().getLocalName(type);
        QName xmlType = new QName(namespace, name);
        DataType<QName> dataType = new DataType<QName>(getName(), javaType, xmlType);
        return dataType;
    }

    @Override
    public WrapperHandler getWrapperHandler() {
        return wrapperHandler;
    }

    public SimpleTypeMapper getSimpleTypeMapper() {
        return new SDOSimpleTypeMapper();
    }

    @Override
    public Object copy(Object arg) {
        HelperContext context = HelperProvider.getDefaultContext();
        CopyHelper copyHelper = context.getCopyHelper();
        if (arg instanceof XMLDocument) {
            XMLDocument document = (XMLDocument)arg;
            DataObject dataObject = copyHelper.copy(document.getRootObject());
            return context.getXMLHelper().createDocument(dataObject,
                                                         document.getRootElementURI(),
                                                         document.getRootElementName());
        } else if (arg instanceof DataObject) {
            return context.getCopyHelper().copy((DataObject)arg);
        } else {
            return super.copy(arg);
        }
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new SDOExceptionHandler();
    }

}
