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

package org.apache.tuscany.sca.imprt.xyz;

import org.apache.tuscany.sca.assembly.impl.ExtensibleImpl;
import org.apache.tuscany.sca.contribution.Export;
import org.apache.tuscany.sca.contribution.namespace.NamespaceExport;
import org.apache.tuscany.sca.contribution.namespace.NamespaceImport;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;

/**
 * The representation of an import for the contribution
 *
 * @version $Rev$ $Date$
 */
public class ImportXYZImpl extends ExtensibleImpl implements ImportXYZ {
    private ModelResolver modelResolver;

    private String URI;
    private String anAttribute;


    protected ImportXYZImpl() {
        super();
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getAnAttribute() {
        return anAttribute;
    }

    public void setAnAttribute(String anAttribute) {
        this.anAttribute = anAttribute;
    }

    public ModelResolver getModelResolver() {
        return modelResolver;
    }

    public void setModelResolver(ModelResolver modelResolver) {
        this.modelResolver = modelResolver;
    }

    public boolean match(Export export) {
        if (export instanceof ExportXYZ){
            return anAttribute.equals(((ExportXYZ)export).getAnAttribute());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(URI);
    }
}
