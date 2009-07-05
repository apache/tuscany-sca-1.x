package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.InputStream;

public interface FileContent {
	
	String getPath();
	
	String getName();
	
	FileContent[] getChildren();
	
	boolean isLeaf();
	
	InputStream getInputStream() throws IOException;

}
