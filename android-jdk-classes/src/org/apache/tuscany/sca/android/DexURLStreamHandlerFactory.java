package org.apache.tuscany.sca.android;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class DexURLStreamHandlerFactory implements URLStreamHandlerFactory {
	
	private static DexURLStreamHandlerFactory instance;
	
	public static DexURLStreamHandlerFactory getInstance() {
		
		if (instance == null) {
			instance = new DexURLStreamHandlerFactory();
		}
		
		return instance;
		
	}
	
	public DexURLStreamHandlerFactory() {}

	public URLStreamHandler createURLStreamHandler(String protocol) {
		URLStreamHandler urlStreamHandler = null;
		
		if ("dex".equals(protocol)) {
			urlStreamHandler = new DexURLStreamHandler();
		}
		
		return urlStreamHandler;
		
	}

}
