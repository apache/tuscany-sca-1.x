package org.apache.tuscany.sca.domain.search.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SystemFileContent extends File implements FileContent {

	private static final long serialVersionUID = -8337926886777467728L;

	final boolean leaf;

	final private File file;

	public SystemFileContent(File file) {
		super(file.getPath());

		this.leaf = !file.isDirectory();
		this.file = file;

	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this);
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

}
