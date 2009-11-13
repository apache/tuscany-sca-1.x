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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.wsdl.WSDLException;
import javax.wsdl.xml.WSDLWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.binding.ws.WebServiceBinding;
import org.apache.tuscany.sca.binding.ws.wsdlgen.WSDLGenerationException;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.apache.tuscany.sca.node.impl.NodeImpl;

/**
 * This class provides the tooling abstraction to Tuscany Contrib2WSDL and can be
 * invoked from command line with the following options 
 * 
 * java org.apache.tuscany.tools.contrib2wsdl.Contrib2WSDL -c ./MyContribution -o ./myOutputDir
 * 
 */
public class Contrib2WSDL {
    
    private static String contributionPath = ".";
    private static String outputPath = ".";
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        try {
            CommandLineParser parser = new PosixParser();
            Options options = getCommandLineOptions();
            CommandLine cli = parser.parse(options, args);
            
            if (cli.hasOption("c")) {
                contributionPath = cli.getOptionValue("c");
            }
            
            if (cli.hasOption("o")) {
                outputPath = cli.getOptionValue("o");
                try {
                    new File(outputPath).mkdir();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    
                }
            }
            
            SCANodeFactory factory = SCANodeFactory.newInstance();
            SCANode node = factory.createSCANode(null, 
                                                 new SCAContribution("contrib2wsdl", contributionPath));
            
            // Print out all generated WSDL
            // today this means finding all services in the deployment composites
            // that have a binding.ws configured
            printWSDL(((NodeImpl)node).getComposite());
            
            
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    static void printWSDL(Composite composite){
         for (Component component : composite.getComponents()){
             if (component.getImplementation() instanceof Composite){
                 printWSDL((Composite)component.getImplementation());
             } else {
                 for (Service service : component.getServices()){
                     for (Binding binding : service.getBindings()){
                         System.out.println("Processing: " +  
                                            component.getName() +
                                            "/" + 
                                            service.getName());
                         if (binding instanceof WebServiceBinding){
                             WebServiceBinding bindingWS = (WebServiceBinding) binding;
                             String filename = outputPath +
                                               "/" +
                                               component.getName() + 
                                               "_" +
                                               service.getName() + 
                                               "_" +
                                               binding.getName() + 
                                               ".wsdl";
                             try {
                                 System.out.println("  Writing file: " + filename); 
                                 System.out.flush();
                                 File file = new File(filename);
                                 file.createNewFile();
                                 FileOutputStream stream = new FileOutputStream(file);
                                 System.out.println("Generated WSDL for " + component.getName() + "/" + service.getName());
                                 WSDLWriter writer =  javax.wsdl.factory.WSDLFactory.newInstance().newWSDLWriter();
                                 writer.writeWSDL(bindingWS.getWSDLDefinition().getDefinition(),stream);
                             } catch (Exception e) {
                                 throw new WSDLGenerationException(e);
                             } 
                         } else {
                             System.out.println("  No binding.ws" ); 
                         }
                     }                     
                 }
             }
         }
    }
    
    static Options getCommandLineOptions() {
        Options options = new Options();
        Option opt1 = new Option("c", "contribution", true, "path to the contribution");
        opt1.setArgName("contributionPath");
        options.addOption(opt1);
        Option opt2 = new Option("o", "output", true, "path to the output directory");
        opt2.setArgName("outputPath");
        options.addOption(opt2);
        return options;
    }

    public static void printUsage() {
        System.out.println("Usage contrib2wsdl -c <contribution path> : path to contribution");
        System.out.println("-o <output Location> : output files location");
        System.exit(0);
    }
}
