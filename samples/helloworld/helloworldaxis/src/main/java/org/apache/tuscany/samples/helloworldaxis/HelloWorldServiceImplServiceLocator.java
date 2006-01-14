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
package org.apache.tuscany.samples.helloworldaxis;

public class HelloWorldServiceImplServiceLocator extends org.apache.axis.client.Service implements org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImplService {

    public HelloWorldServiceImplServiceLocator() {
    }


    public HelloWorldServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HelloWorldServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for helloworld
    private java.lang.String helloworld_address = "http://localhost:8080/axis/services/helloworld";

    public java.lang.String gethelloworldAddress() {
        return helloworld_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String helloworldWSDDServiceName = "helloworld";

    public java.lang.String gethelloworldWSDDServiceName() {
        return helloworldWSDDServiceName;
    }

    public void sethelloworldWSDDServiceName(java.lang.String name) {
        helloworldWSDDServiceName = name;
    }

    public org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl gethelloworld() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(helloworld_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return gethelloworld(endpoint);
    }

    public org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl gethelloworld(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.apache.tuscany.samples.helloworldaxis.HelloworldSoapBindingStub _stub = new org.apache.tuscany.samples.helloworldaxis.HelloworldSoapBindingStub(portAddress, this);
            _stub.setPortName(gethelloworldWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void sethelloworldEndpointAddress(java.lang.String address) {
        helloworld_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                org.apache.tuscany.samples.helloworldaxis.HelloworldSoapBindingStub _stub = new org.apache.tuscany.samples.helloworldaxis.HelloworldSoapBindingStub(new java.net.URL(helloworld_address), this);
                _stub.setPortName(gethelloworldWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("helloworld".equals(inputPortName)) {
            return gethelloworld();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "HelloWorldServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "helloworld"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("helloworld".equals(portName)) {
            sethelloworldEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
