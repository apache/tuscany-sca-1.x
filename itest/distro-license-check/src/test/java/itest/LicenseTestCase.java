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
package itest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

public class LicenseTestCase extends TestCase {

    // TODO: turn this in to a maven plugin that can be run from the module that builds the archive
    
    public void testCreateComponent() throws ZipException, IOException {

        File archive = new File("..\\..\\distribution\\target\\apache-tuscany-sca-1.6-SNAPSHOT.zip");
        ZipFile zf = null;

        try {
            zf = new ZipFile(archive);
        } catch (FileNotFoundException e) {
            return;  // archive has not been built yet
        }

        try {

            String licenstText = getLicenseText(zf);
            List<String> jarsInArchive = getJarsInDistro(zf);

            List<String> jarsNotInLicense = getJarsNotInLicense(licenstText, jarsInArchive);
            if (jarsNotInLicense.size() > 0) {
                System.out.println("jarsNotInLicense: " + jarsNotInLicense);
            }

            List<String> jarsNotInArchive = getJarsNotInArchive(licenstText, jarsInArchive);
            if (jarsNotInArchive.size() > 0) {
                System.out.println("jarsNotInArchive: " + jarsNotInArchive);
            }
            
            assertTrue("License errors, check log for details", jarsNotInArchive.size()==0 && jarsNotInLicense.size()==0);

        } finally {
            zf.close();
        }
    }

    private List<String> getJarsNotInLicense(String licenstText, List<String> jarsInArchive) {
        List<String> jarsNotInLicense = new ArrayList<String>();
        for (String jarName : jarsInArchive) {
            if (!licenseHasJar(licenstText, jarName)) {
                jarsNotInLicense.add(jarName);
            }
        }
        return jarsNotInLicense;
    }

    private List<String> getJarsNotInArchive(String licenstText, List<String> jarsInArchive) throws IOException {
        List<String> jarsNotInArchive = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new StringReader(licenstText));
        String line = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.contains(".jar")) {
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    if (s.contains(".jar")) {
                        if (s.startsWith("(")) {
                            s = s.substring(1);
                        }
                        if (s.endsWith(",") || s.endsWith(":")) {
                            s = s.substring(0, s.length()-1);
                        }
                        if (s.endsWith(")")) {
                            s = s.substring(0, s.length()-1);
                        }
                        if (!jarsInArchive.contains(s) && !s.startsWith("tuscany-")) {
                            jarsNotInArchive.add(s);
                        }
                    }
                }
            }
        }
        return jarsNotInArchive;
    }

    private boolean licenseHasJar(String licenstText, String jarName) {
        // TODO: be good to make these configurable, maybe system props that can
        // be configured in the pom.xml?
        if (jarName.startsWith("tuscany-")) {
            return true;
        } else if (jarName.startsWith("demo-bigbank")) {
            return true;
        } else if (jarName.startsWith("tutorial-")) {
            return true;
        } else if (jarName.startsWith("sample-")) {
            return true;
        } else {
            return licenstText.indexOf(jarName) > -1;
        }
    }

    private String getLicenseText(ZipFile zf) throws IOException {
        ZipEntry ze = zf.getEntry("tuscany-sca-1.6-SNAPSHOT/LICENSE");
        InputStream in = zf.getInputStream(ze);
        String l = readLICENSE(in);
        return l;
    }

    private List<String> getJarsInDistro(ZipFile zf) {
        ZipEntry ze;
        List<String> jarsInArchive = new ArrayList<String>();
        for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
            ze = e.nextElement();
            String name = ze.getName();
            if (name.endsWith(".jar")) {
                if (name.lastIndexOf('/') > -1){
                    name = name.substring(name.lastIndexOf('/')+1);
                }
                jarsInArchive.add(name);
            }
        }
        System.out.println("jarsInArchive: " + jarsInArchive.size());
        return jarsInArchive;
    }

    private static String readLICENSE(InputStream in) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        char[] buf = new char[16384];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[16384];
        }
        reader.close();
        return fileData.toString();
    }
}
