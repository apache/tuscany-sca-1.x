package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DefaultFileContent implements FileContent {

	final private URL url;

	public DefaultFileContent(URL url) {
		this.url = url;
	}

	public FileContent[] getChildren() {
		return new FileContent[0];
	}

	public InputStream getInputStream() throws IOException {
		return this.url.openStream();
	}

	public String getName() {
		return this.url.getFile();
	}

	public String getPath() {
		return this.url.getPath();
	}

	public boolean isLeaf() {
		return false;
	}

}
