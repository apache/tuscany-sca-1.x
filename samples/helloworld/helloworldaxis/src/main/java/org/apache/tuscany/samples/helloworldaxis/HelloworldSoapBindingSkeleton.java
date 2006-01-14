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

public class HelloworldSoapBindingSkeleton implements org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl, org.apache.axis.wsdl.Skeleton {
    private org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
     * Returns List of OperationDesc objects with this name
     */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List) _myOperations.get(methodName);
    }

    /**
     * Returns Collection of OperationDescs
     */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc []{
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("getGreetings", _params, new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "getGreetingsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://helloworldaxis.samples.tuscany.apache.org", "getGreetings"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getGreetings") == null) {
            _myOperations.put("getGreetings", new java.util.ArrayList());
        }
        ((java.util.List) _myOperations.get("getGreetings")).add(_oper);
    }

    public HelloworldSoapBindingSkeleton() {
        this.impl = new org.apache.tuscany.samples.helloworldaxis.HelloworldSoapBindingImpl();
    }

    public HelloworldSoapBindingSkeleton(org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl impl) {
        this.impl = impl;
    }

    public java.lang.String getGreetings(java.lang.String in0) throws java.rmi.RemoteException {
        java.lang.String ret = impl.getGreetings(in0);
        return ret;
    }

}
