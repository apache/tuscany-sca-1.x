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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osoa.sca.annotations.Service;

import jtest.AbstractException;
import jtest.Bean1;
import jtest.Bean2;
import jtest.ConcreteException;
import jtest.TestAbstract;
import jtest.TestService;
import jtest.WrapBean;
import jtest.WrapMap;

/**
 * The test service implementation
 */
@Service(TestService.class)
public class TestServiceImpl implements TestService {

    public void sendAbstract(TestAbstract data) {
        System.out.println("data is instance of class " + data.getClass().getName());
        System.out.println("data sent by " + data.sender);
        System.out.println("data first+last name is " + data.firstName + " " + data.lastName);
        System.out.println(data.getGreeting());
    }

    public void throwAbstract() throws AbstractException {
        throw new ConcreteException();
    }

    public void sendList(List<String> data) {
        System.out.println(data.get(0) + " " + data.get(1));
    }

    public Map<String, String> returnMap() {
        Map<String, String> yourMap = new HashMap<String, String>();
        yourMap.put("greeting", "Hello, World Map!");
        return yourMap;
    }

    public WrapMap returnWrapMap() {
        Map<String, String> yourMap = new HashMap<String, String>();
        yourMap.put("greeting", "Hello, World Map!");
        WrapMap wrapped = new WrapMap();
        wrapped.setMap(yourMap);
        return wrapped;
    }

    public void sendWildcardExtends(Bean1</*? extends*/ Bean2> arg) {
        System.out.println("TestServiceImpl received generic bean " + arg);
    }

    public void sendWrapBean(WrapBean arg) {
        System.out.println("TestServiceImpl received wrapped bean " + arg.getBean());
    }
}
