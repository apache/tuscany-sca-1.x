/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.apache.tuscany.tools.wsdltojava;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.cxf.tools.wsdlto.WSDLToJava;
import org.apache.cxf.tools.common.CommandInterfaceUtils;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;

import java.io.File;


public class DependencyTestCase
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DependencyTestCase( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DependencyTestCase.class );
    }

    protected void setUp(){
      // creating a directory named <generatedSources> before running the tool
        boolean created=false;
        File directory=new File("target/generatedSources");

        if(!directory.exists()){
            created=directory.mkdir();
        }
        if (created){
            System.out.println("The directory named generatedSources is created in the absolute path "+ directory.getAbsolutePath());
        }
    }

    protected void tearDown(){
        // no resources to free

    }

    public void testDependency()
    {
         // immitate the main method of wsdltojava class of CXF.
        String clientDir="./target/generatedSources";
        String args = "-ant -client -d "+clientDir+" ./src/test/resources/helloService.wsdl";
        String[] pargs= args.split(" ");// split by spaces
        CommandInterfaceUtils.commandCommonMain();
        WSDLToJava w2j = new WSDLToJava(pargs);
        try {

            w2j.run(new ToolContext());

        } catch (ToolException ex) {
            System.err.println();
            System.err.println("WSDLToJava Error: " + ex.getMessage());
            System.err.println();
            System.exit(1);

        } catch (Exception ex) {
            // error
            System.err.println("WSDLToJava Error: " + ex.getMessage());
            System.err.println();
            System.exit(1);
        }
        // the program executed successfully
            System.out.println("Test successfull : artifacts generated with jaxb databinding" +
                    " & jaxws frontend ");

    }
}

