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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @version $Rev$ $Date$
 */
public class SystemFileContent implements FileContent {

	private static final long serialVersionUID = -8337926886777467728L;

	final boolean leaf;
	
	final private File file;

	public SystemFileContent(File file) {
		this.leaf = !file.isDirectory();
		this.file = file;

	}
	
	public File getFile() {
		return this.file;
	}
	
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	public FileContent[] getChildren() {
		File[] files = this.file.listFiles();
		FileContent[] ret = new FileContent[files.length];

		for (int i = 0; i < files.length; i++) {
			ret[i] = new SystemFileContent(files[i]);
		}
		
		return ret;

	}

	public boolean isLeaf() {
		return this.leaf;
	}

	public String getName() {
		return this.file.getName();
	}

	public String getPath() {
		return this.file.getPath();
	}

}
