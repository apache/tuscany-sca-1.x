package org.apache.tuscany.sca.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

import android.content.Context;

public class DexURLConnection extends URLConnection {

	private InputStream input;

	protected DexURLConnection(URL url) {
		super(url);
		setAllowUserInteraction(false);
		setUseCaches(false);
		setDefaultUseCaches(false);
		setConnectTimeout(0);
		setReadTimeout(0);
		setDoInput(true);
		setDoOutput(false);

	}

	public InputStream getInputStream() throws IOException {

		if (input == null) {
			connect();
		}

		return input;

	}
	
	private String guessContentTypeFromInput() {
		
		if (!connected) {
			
			try {
				connect();
			} catch (IOException e) {
				return null;
			}
			
		}
		
		try {
			return guessContentTypeFromStream(input);
		} catch (IOException e) {
			return null;
		}
		
	}
	
	@Override
	public String getContentType() {
		
		if (DexResource.getFolder(url.getPath()) == null || DexResource.getFile(url.getPath()) == null) {
			return "application/x-dex";
		}
		
		return guessContentTypeFromInput();
		
	}

	public OutputStream getOutputStream() throws IOException {
		throw new UnknownServiceException("Output not supported!");
	}

	public void connect() throws IOException {
		
		if (!connected) {
			String host = url.getHost();
			Context[] contexts = ContextRegistry.getContexts(host);
			
			if (contexts.length == 0) {
				throw new IOException("Android context not found!");
			}
			
			Context context = contexts[0];
			
			if ("".equals(host)) {
				throw new IOException("not valid host name: \"\"");
			}
			
			String path = url.getPath();
			String file = DexResource.getFile(path);
			String folder = DexResource.getFolder(path);
			
			if (file == null) {
				return;
			}
			
			file = file.replace('.', '_');
			
			try {
				
				StringBuffer sb = new StringBuffer(context.getPackageName());
				sb.append('.').append('R').append('$').append(folder);
				
				Class clazz = getClass().getClassLoader().loadClass(sb.toString());
				Field field = clazz.getDeclaredField(file);
				
				int id = field.getInt(null);
				input = context.getResources().openRawResource(id);
				connected = true;
				
			} catch (ClassNotFoundException e) {
				throw new IOException(e.getMessage());
			} catch (SecurityException e) {
				throw new IOException(e.getMessage());
			} catch (NoSuchFieldException e) {
				throw new IOException(e.getMessage());
			} catch (IllegalArgumentException e) {
				throw new IOException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new IOException(e.getMessage());
			}
			
		}

	}

}
