/**
 *
 * Copyright 2006 The Apache Software Foundation or its licensors as applicable
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
package org.apache.tuscany.spi.model;

import javax.wsdl.PortType;

/**
 * @version $Rev$ $Date$
 */
public class WSDLServiceContract extends ServiceContract {
    private PortType portType;
    private PortType callbackPortType;

    public PortType getPortType() {
        return portType;
    }

    public void setPortType(PortType portType) {
        this.portType = portType;
    }

    public PortType getCallbackPortType() {
        return callbackPortType;
    }

    public void setCallbackPortType(PortType callbackPortType) {
        this.callbackPortType = callbackPortType;
    }
}
