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

package jtest;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The test XML adapter class
 */
public class TestAdapter extends XmlAdapter<TestAbstractImpl, TestAbstract> {
    public TestAbstract unmarshal(TestAbstractImpl ai) throws Exception {
        TestAbstract a = (TestAbstract)this.getClass().getClassLoader().loadClass(ai.className).newInstance();
        a.sender = ai.someMessage;
        return a;
    }
    public TestAbstractImpl marshal(TestAbstract v) throws Exception {
        TestAbstractImpl ai = new TestAbstractImpl();
        ai.className = v.getClass().getName();
        ai.someMessage = "YouKnowWho";
        return ai;
    }
}
