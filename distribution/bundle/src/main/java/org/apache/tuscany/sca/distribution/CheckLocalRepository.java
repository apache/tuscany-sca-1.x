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
package org.apache.tuscany.sca.distribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.jar.JarFile;

public class CheckLocalRepository {

    public static void main(String[] args) {
        String repo = args[0];
        checkJarFile(repo);
        checkPomFile(repo);
        System.exit(0);
    }

    private static void checkJarFile(String repo) {
        try {
            JarFile jaxwsJar = new JarFile(repo + "/javax/xml/ws/jaxws-api/2.1/jaxws-api-2.1.jar");
            if (jaxwsJar.getEntry("javax/xml/ws/soap/Addressing.class") != null) {
                return;
            }
        } catch (Exception e) {
        }
        System.out.println("===================================================================");
        System.out.println("ERROR: Incorrect version of jaxws-api jar in local maven repository");
        System.out.println("ERROR: To fix this, delete the javax/xml/ws/jaxws-api/2.1 directory");
        System.out.println("ERROR: and rerun the distribution build");
        System.out.println("===================================================================");
        System.exit(1);
    }

    private static void checkPomFile(String repo) {
        try {
            FileReader jaxwsPom = new FileReader(repo + "/javax/xml/ws/jaxws-api/2.1/jaxws-api-2.1.pom");
            BufferedReader reader = new BufferedReader(jaxwsPom);
            while (true) {
                if (reader.readLine().contains("saaj-api")) {
                    return;
                }
            }
        } catch (Exception e) {
        }
        System.out.println("===================================================================");
        System.out.println("ERROR: Incorrect version of jaxws-api pom in local maven repository");
        System.out.println("ERROR: To fix this, delete the javax/xml/ws/jaxws-api/2.1 directory");
        System.out.println("ERROR: and rerun the distribution build");
        System.out.println("===================================================================");
        System.exit(1);
    }
}
