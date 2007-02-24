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

package org.apache.tuscany.core.databinding.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.tuscany.spi.databinding.extension.DataBindingExtension;
import org.apache.tuscany.spi.model.DataType;

/**
 * A DataBinding for the StAX
 * 
 * @version $Rev$ $Date$
 */
public abstract class GroupDataBinding extends DataBindingExtension {
    public static final String NAME = "databinding:group";

    protected Class[] types;

    public GroupDataBinding(Class[] types) {
        super(NAME, null, GroupDataBinding.class);
        this.types = types;
    }

    @SuppressWarnings("unchecked")
    public boolean introspect(DataType type, Annotation[] annotations) {
        if (types == null) {
            return false;
        }
        Type physical = type.getPhysical();
        if (physical instanceof ParameterizedType) {
            physical = ((ParameterizedType)physical).getRawType();
        }
        if (!(physical instanceof Class)) {
            return false;
        }
        Class cls = (Class)physical;
        for (Class<?> c : types) {
            if (c.isAssignableFrom(cls)) {
                type.setDataBinding(NAME);
                DataType realType = null;
                try {
                    realType = (DataType)type.clone();
                } catch (CloneNotSupportedException e) {
                    // Never happen
                    assert false;
                }
                realType.setDataBinding(c.getName());
                type.setLogical(realType);
            }
        }
        return false;
    }

}
