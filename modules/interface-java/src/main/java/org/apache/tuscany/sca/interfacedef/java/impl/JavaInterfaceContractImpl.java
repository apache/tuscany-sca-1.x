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
package org.apache.tuscany.sca.interfacedef.java.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.sca.assembly.Extension;
import org.apache.tuscany.sca.interfacedef.impl.InterfaceContractImpl;
import org.apache.tuscany.sca.interfacedef.java.JavaInterfaceContract;

/**
 * Represents a Java interface contract.
 * 
 * @version $Rev$ $Date$
 */
public class JavaInterfaceContractImpl extends InterfaceContractImpl implements JavaInterfaceContract {
    private List<Object> extensions = new ArrayList<Object>();
    private List<Extension> attributeExtensions = new ArrayList<Extension>();
    
    protected JavaInterfaceContractImpl() {
    }

    @Override
    public JavaInterfaceContractImpl clone() throws CloneNotSupportedException {
        return (JavaInterfaceContractImpl) super.clone();
    }

    public List<Object> getExtensions() {
        return extensions;
    }
    
    public List<Extension> getAttributeExtensions() {
        return attributeExtensions;
    }
    

}
