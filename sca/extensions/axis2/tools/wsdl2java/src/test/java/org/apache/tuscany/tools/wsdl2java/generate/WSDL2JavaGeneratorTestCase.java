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
package org.apache.tuscany.tools.wsdl2java.generate;

import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

import junit.framework.TestCase;

/**
 * Test case for WSDL2Java
 */
public class WSDL2JavaGeneratorTestCase extends TestCase {

    public void testAccountService() {
        
        String basedir = System.getProperty("basedir");
        if (basedir == null)
            basedir =".";
        
        
        String[] args=new String[] { "-targetDirectory", basedir + "/target/wsdl2java-source",
                "-javaPackage", "org.apache.tuscany.tools.wsdl2java.generate.account",
                basedir + "/src/test/resources/AccountService.wsdl"};

        WSDL2JavaGenerator.main(args);
        
    }

    public void testDynamicSDO() throws IOException {
        String basedir = System.getProperty("basedir");
        if (basedir == null)
            basedir =".";

        String[] args=new String[] { "-targetDirectory", basedir + "/target/wsdl2java-source",
                "-javaPackage", "org.apache.tuscany.tools.wsdl2java.generate.account",
                "-dynamicSDO",
                basedir + "/src/test/resources/AccountService.wsdl"};

        WSDL2JavaGenerator.main(args);

        char buffer[] = new char[1024];
        Reader file = new FileReader("target/wsdl2java-source/org/apache/tuscany/tools/wsdl2java/generate/account/AccountService.java");
        file.read(buffer);
        file.close();
        assertTrue(new String(buffer).indexOf("commonj.sdo.DataObject") != -1);
    }
}
