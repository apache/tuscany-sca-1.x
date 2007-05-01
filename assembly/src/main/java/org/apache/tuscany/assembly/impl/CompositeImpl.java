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

package org.apache.tuscany.assembly.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.tuscany.assembly.Component;
import org.apache.tuscany.assembly.Composite;
import org.apache.tuscany.assembly.Wire;
import org.apache.tuscany.assembly.util.Visitor;

public class CompositeImpl extends ComponentTypeImpl implements Composite, Cloneable {
    private List<Component> components = new ArrayList<Component>();
    private List<Composite> includes = new ArrayList<Composite>();
    private QName name;
    private List<Wire> wires = new ArrayList<Wire>();
    private boolean autowire;
    private boolean local = true;
    
    /**
     * Constructs a new composite.
     */
    protected CompositeImpl() {
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        CompositeImpl clone = (CompositeImpl)super.clone();
        
        clone.components = new ArrayList<Component>();
        for (Component component: getComponents()) {
            clone.components.add((Component)component.clone());
        }
        clone.wires = new ArrayList<Wire>();
        for (Wire wire: getWires()) {
            clone.wires.add((Wire)wire.clone());
        }
        return clone;
    }
    
    public List<Component> getComponents() {
        return components;
    }

    public List<Composite> getIncludes() {
        return includes;
    }

    public QName getName() {
        return name;
    }

    public List<Wire> getWires() {
        return wires;
    }

    public boolean isAutowire() {
        return autowire;
    }

    public boolean isLocal() {
        return local;
    }

    public void setAutowire(boolean autowire) {
        this.autowire = autowire;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public void setName(QName name) {
        this.name = name;
    }
    
    @Override
    public boolean accept(Visitor visitor) {
        boolean result = super.accept(visitor);
        if (!result) {
            return false;
        }
        
        for (Component component: components) {
            if (!component.accept(visitor)) {
                return false;
            }
        }
        
        for (Wire wire: wires) {
            if (!visitor.visit(wire))
                return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return String.valueOf(getName()).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Composite) {
            if (getName() != null) {
                return getName().equals(((Composite)obj).getName());
            } else {
                return ((Composite)obj).getName() == null;
            }
        } else {
            return false;
        }
    }
}
