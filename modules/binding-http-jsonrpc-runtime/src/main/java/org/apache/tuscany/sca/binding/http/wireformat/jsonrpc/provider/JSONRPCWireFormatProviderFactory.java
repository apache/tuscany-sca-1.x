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

package org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.provider;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.JSONRPCWireFormat;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.provider.WireFormatProvider;
import org.apache.tuscany.sca.provider.WireFormatProviderFactory;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentReference;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;

/**
 * JSON-RPC Wire Format Provider Factory
 * 
 * @version $Rev$ $Date$
 */
public class JSONRPCWireFormatProviderFactory implements WireFormatProviderFactory <JSONRPCWireFormat> {
    private ExtensionPointRegistry extensionPoints;

    public JSONRPCWireFormatProviderFactory(ExtensionPointRegistry extensionPoints) {
        this.extensionPoints = extensionPoints;
    }

    public WireFormatProvider createReferenceWireFormatProvider(RuntimeComponent component,
                                                                RuntimeComponentReference reference,
                                                                Binding binding) {
        return new JSONRPCWireFormatReferenceProvider(extensionPoints, component, reference, binding);
    }

    public WireFormatProvider createServiceWireFormatProvider(RuntimeComponent component,
                                                              RuntimeComponentService service,
                                                              Binding binding) {
        return new JSONRPCWireFormatServiceProvider(extensionPoints, component, service, binding);
    }

    public Class<JSONRPCWireFormat> getModelType() {
        return JSONRPCWireFormat.class;
    }

}
