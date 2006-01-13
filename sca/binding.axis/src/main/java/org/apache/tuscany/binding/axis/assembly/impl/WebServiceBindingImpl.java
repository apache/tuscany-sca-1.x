/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.binding.axis.assembly.impl;

import org.apache.tuscany.binding.axis.assembly.WebServiceBinding;
import org.apache.tuscany.model.assembly.AssemblyModelContext;
import org.apache.tuscany.model.assembly.AssemblyModelVisitor;
import org.apache.tuscany.model.assembly.impl.AssemblyModelVisitorHelperImpl;

/**
 * An implementation of the model object '<em><b>Web Service Binding</b></em>'.
 */
public class WebServiceBindingImpl extends org.apache.tuscany.binding.axis.assembly.sdo.impl.WebServiceBindingImpl implements WebServiceBinding {

    private Object runtimeConfiguration;

    /**
     * Constructor
     */
    protected WebServiceBindingImpl() {
    }

    /**
     * @see org.apache.tuscany.binding.axis.assembly.sdo.impl.WebServiceBindingImpl#getPort()
     */
    public String getPort() {
        return super.getPort();
    }

    /**
     * @see org.apache.tuscany.binding.axis.assembly.sdo.impl.WebServiceBindingImpl#setPort(java.lang.String)
     */
    public void setPort(String newPort) {
        super.setPort(newPort);
    }

    /**
     * @see org.apache.tuscany.model.assembly.Binding#setURI(java.lang.String)
     */
    public void setURI(String value) {
        super.setUri(value);
    }

    /**
     * @see org.apache.tuscany.model.assembly.Binding#getURI()
     */
    public String getURI() {
        return super.getUri();
    }

    /**
     * @see org.apache.tuscany.model.assembly.ConfiguredRuntimeObject#getRuntimeConfiguration()
     */
    public Object getRuntimeConfiguration() {
        return runtimeConfiguration;
    }

    /**
     * @see org.apache.tuscany.model.assembly.ConfiguredRuntimeObject#setRuntimeConfiguration(java.lang.Object)
     */
    public void setRuntimeConfiguration(Object configuration) {
        this.runtimeConfiguration = configuration;
    }

    /**
     * @see org.apache.tuscany.model.assembly.AssemblyModelObject#initialize(org.apache.tuscany.model.assembly.AssemblyModelContext)
     */
    public void initialize(AssemblyModelContext modelContext) {
    }

    /**
     * @see org.apache.tuscany.model.assembly.AssemblyModelObject#freeze()
     */
    public void freeze() {
    }

    /**
     * @see org.apache.tuscany.model.assembly.AssemblyModelObject#accept(org.apache.tuscany.model.assembly.AssemblyModelVisitor)
     */
    public boolean accept(AssemblyModelVisitor visitor) {
        return AssemblyModelVisitorHelperImpl.accept(this, visitor);
    }

} //TWebServiceBindingImpl
