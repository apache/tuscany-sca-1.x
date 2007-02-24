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
package org.apache.tuscany.spi.databinding.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.osoa.sca.annotations.EagerInit;
import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Scope;
import org.osoa.sca.annotations.Service;

import org.apache.tuscany.spi.annotation.Autowire;
import org.apache.tuscany.spi.databinding.DataBinding;
import org.apache.tuscany.spi.databinding.DataBindingRegistry;
import org.apache.tuscany.spi.databinding.ExceptionHandler;
import org.apache.tuscany.spi.databinding.SimpleTypeMapper;
import org.apache.tuscany.spi.databinding.WrapperHandler;
import org.apache.tuscany.spi.model.DataType;

/**
 * Base Implementation of DataBinding
 * 
 * @version $Rev$ $Date$
 */
@Service(DataBinding.class)
@Scope("COMPOSITE")
@EagerInit
public abstract class DataBindingExtension implements DataBinding {

    protected DataBindingRegistry registry;

    protected Class<?> baseType;

    protected String name;
    protected String[] aliases; 

    /**
     * Create a databinding with the base java type whose name will be used as
     * the name of the databinding
     * 
     * @param baseType The base java class or interface representing the
     *            databinding, for example, org.w3c.dom.Node
     */
    protected DataBindingExtension(Class<?> baseType) {
        this(baseType.getName(), null, baseType);
    }

    /**
     * Create a databinding with the name and base java type
     * 
     * @param name The name of the databinding
     * @param baseType The base java class or interface representing the
     *            databinding, for example, org.w3c.dom.Node
     */
    protected DataBindingExtension(String name, Class<?> baseType) {
        this(name, null, baseType);
    }
    
    /**
     * Create a databinding with the name and base java type
     * 
     * @param name The name of the databinding
     * @param aliases The aliases of the databinding
     * @param baseType The base java class or interface representing the
     *            databinding, for example, org.w3c.dom.Node
     */
    protected DataBindingExtension(String name, String[] aliases, Class<?> baseType) {
        this.name = name;
        this.baseType = baseType;
        this.aliases = aliases;
    }    

    @Autowire
    public void setDataBindingRegistry(DataBindingRegistry registry) {
        this.registry = registry;
    }

    @Init
    public void init() {
        registry.register(this);
    }

    public boolean introspect(DataType type, Annotation[] annotations) {
        assert type != null;
        Type physical = type.getPhysical();
        if (physical instanceof ParameterizedType) {
            physical = ((ParameterizedType)physical).getRawType();
        }
        if (physical instanceof Class) {
            Class cls = (Class)physical;
            if (baseType != null && baseType.isAssignableFrom(cls)) {
                type.setDataBinding(getName());
                type.setLogical(baseType);
                return true;
            }
        }
        return false;
    }
    
    protected static org.apache.tuscany.api.annotation.DataType getDataTypeAnnotation(Annotation[] annotations) {
        for (Annotation a : annotations) {
            if (a.annotationType() == org.apache.tuscany.api.annotation.DataType.class) {
                return (org.apache.tuscany.api.annotation.DataType) a;
            }
        }
        return null;
    }

    public DataType introspect(Object value) {
        if (value == null) {
            return null;
        } else {
            DataType<Class> dataType = new DataType<Class>(value.getClass(), value.getClass());
            if (introspect(dataType, null)) {
                return dataType;
            } else {
                return null;
            }
        }
    }

    public final String getName() {
        return name;
    }

    /**
     * @see org.apache.tuscany.spi.databinding.DataBinding#getWrapperHandler()
     */
    public WrapperHandler getWrapperHandler() {
        return null;
    }

    public ExceptionHandler getExceptionHandler() {
        return null;
    }

    public Object copy(Object arg) {
        if (arg == null) {
            return null;
        }
        final Class clazz = arg.getClass();
        if (String.class == clazz || clazz.isPrimitive()
            || Number.class.isAssignableFrom(clazz)
            || Boolean.class.isAssignableFrom(clazz)
            || Character.class.isAssignableFrom(clazz)
            || Byte.class.isAssignableFrom(clazz)) {
            // Immutable classes
            return arg;
        }
        try {
            if (arg instanceof Serializable) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = getObjectOutputStream(bos);
                oos.writeObject(arg);
                oos.close();
                bos.close();

                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream ois = getObjectInputStream(bis, clazz.getClassLoader());
                Object objectCopy = ois.readObject();
                ois.close();
                bis.close();
                return objectCopy;
            } else {
                // return arg;
                throw new IllegalArgumentException("Pass-by-value is not supported for the given object");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Pass-by-value is not supported for the given object", e);
        }
    }

    protected ObjectOutputStream getObjectOutputStream(OutputStream os) throws IOException {
        return new ObjectOutputStream(os);
    }

    protected ObjectInputStream getObjectInputStream(InputStream is, final ClassLoader cl) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(is) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                try {
                    return Class.forName(desc.getName(), false, cl);
                } catch (ClassNotFoundException e) {
                    return super.resolveClass(desc);
                }
            }

        };
        return ois;
    }

    public SimpleTypeMapper getSimpleTypeMapper() {
        return new SimpleTypeMapperExtension();
    }

    public String[] getAliases() {
        return aliases;
    }

}
