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
package jtest.impl;

import java.util.List;
import java.util.Map;
import javax.jws.WebService;

import jtest.AbstractException;
import jtest.Bean1;
import jtest.Bean2;
import jtest.ConcreteException;
import jtest.TestAbstract;
import jtest.TestConcrete1;
import jtest.other.TestOther;
import jtest.TestWebService;

@WebService(endpointInterface = "jtest.TestWebService")
public class TestWebServiceImpl implements TestWebService {

    public void sendAbstract(TestAbstract testData) {
        System.out.println(testData.getGreeting());
    }

    public String sendConcrete(TestConcrete1 testData) {
        System.out.println(testData.getGreeting());
        return "Hi!";
    }

    public void sendOtherPackage(TestOther testData) {
        System.out.println(testData.getGreeting());
    }

    public void throwAbstract() throws AbstractException {
        throw new ConcreteException();
    }    

    public void sendList(List<String> data) {
        System.out.println(data.get(0) + " " + data.get(1));
    }

    //public Map<String, String> returnMap() {
    //    return null;
    //}

    public void sendWildcardExtends(Bean1<Bean2> arg) {
        System.out.println(arg);
    }
}
