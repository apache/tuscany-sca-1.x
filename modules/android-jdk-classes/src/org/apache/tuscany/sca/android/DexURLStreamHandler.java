package org.apache.tuscany.sca.android;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class DexURLStreamHandler extends URLStreamHandler {
	
	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		return new DexURLConnection(url);
	}

}
