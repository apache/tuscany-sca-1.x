package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;

public class WrappedFileContent implements FileContent {

	final private FileContent fileContent;

	public WrappedFileContent(URL url) throws IOException {
		String protocol = url.getProtocol();

		if (protocol.equals("jar")) {
			JarURLConnection jarConn = (JarURLConnection) url.openConnection();
			//Enumeration<JarEntry> entries = jarConn.getJarFile().entries();
			String file = url.getFile();
			file = file.substring(file.lastIndexOf('!') + 1);
			
//			if (file.charAt(file.length() - 1) != '/') {
//				
//				int beginIndex;
//				
//				if (file.charAt(0) == '/') {
//					beginIndex = 1;
//					 
//				} else {
//					beginIndex = 0;
//				}
//				
//				file = file.substring(beginIndex);
//			
//				while (entries.hasMoreElements()) {
//					String actualFile = entries.nextElement().getName();
//					
//					if (actualFile.charAt(0) == '/') {
//						beginIndex = 1;
//						
//					} else {
//						beginIndex = 0;
//					}
//					
//					if (actualFile.length() - beginIndex == file.length() + 1 && actualFile.charAt(actualFile.length() - 1) == '/') {
//						
//						if (actualFile.startsWith(file, beginIndex)) {
//							file = actualFile;
//							
//							break;
//							
//						}
//						
//					}
//					
//				}
//				
//			}
			
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
