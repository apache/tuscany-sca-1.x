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
package org.apache.tuscany.tools.contrib2wsdl;

import junit.framework.TestCase;

/**
 * This shows how to run Contrib2WSDL.
 */
public class Contrib2WSDLTestCase extends TestCase {


    public void testContrib2WSDL() throws Exception {
        //String[] args = {"-c", "target/test-classes", "-o", "target/wsdl"};
        String[] args = {"-c", "../../itest/wsdlgen/target/classes", "-o", "target/wsdl"};
        Contrib2WSDL.main(args);
    }
}
