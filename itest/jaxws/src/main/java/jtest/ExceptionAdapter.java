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
 * The test XML exception adapter class
 */
public class ExceptionAdapter extends XmlAdapter<AbstractExceptionImpl, AbstractException> {
    public AbstractException unmarshal(AbstractExceptionImpl ai) throws Exception {
        AbstractException a = (AbstractException)this.getClass().getClassLoader().loadClass(ai.className).newInstance();
        a.setGreeting(ai.testGreeting);
        return a;
    }
    public AbstractExceptionImpl marshal(AbstractException v) throws Exception {
        AbstractExceptionImpl ai = new AbstractExceptionImpl();
        ai.className = v.getClass().getName();
        ai.testGreeting = "Hi there!";
        return ai;
    }
}
