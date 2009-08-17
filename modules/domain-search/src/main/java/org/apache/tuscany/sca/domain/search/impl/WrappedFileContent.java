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
package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;

/**
 * 
 * @version $Rev$ $Date$
 */
public class WrappedFileContent implements FileContent {

    final private FileContent fileContent;

    public WrappedFileContent(URL url) throws IOException {
        String protocol = url.getProtocol();

        if (protocol.equals("jar")) {
            JarURLConnection jarConn = (JarURLConnection)url.openConnection();
            // Enumeration<JarEntry> entries = jarConn.getJarFile().entries();
            String file = url.getFile();
            file = file.substring(file.lastIndexOf('!') + 1);

            // if (file.charAt(file.length() - 1) != '/') {
            //				
            // int beginIndex;
            //				
            // if (file.charAt(0) == '/') {
            // beginIndex = 1;
            //					 
            // } else {
            // beginIndex = 0;
            // }
            //				
            // file = file.substring(beginIndex);
            //			
            // while (entries.hasMoreElements()) {
            // String actualFile = entries.nextElement().getName();
            //					
            // if (actualFile.charAt(0) == '/') {
            // beginIndex = 1;
            //						
            // } else {
            // beginIndex = 0;
            // }
            //					
            // if (actualFile.length() - beginIndex == file.length() + 1 &&
            // actualFile.charAt(actualFile.length() - 1) == '/') {
            //						
            // if (actualFile.startsWith(file, beginIndex)) {
            // file = actualFile;
            //							
            // break;
            //							
            // }
            //						
            // }
            //					
            // }
            //				
            // }

            this.fileContent = ZipFileContent.createZipFileContent(jarConn.getJarFile(), file);

        } else if (protocol.equals("file")) {
            this.fileContent = new SystemFileContent(new File(url.getFile()));

        } else {
            this.fileContent = new DefaultFileContent(url);
        }

    }

    public FileContent[] getChildren() {
        return this.fileContent.getChildren();
    }

    public InputStream getInputStream() throws IOException {
        return this.fileContent.getInputStream();
    }

    public String getName() {
        return this.fileContent.getName();
    }

    public String getPath() {
        return this.fileContent.getPath();
    }

    public boolean isLeaf() {
        return this.fileContent.isLeaf();
    }

}
