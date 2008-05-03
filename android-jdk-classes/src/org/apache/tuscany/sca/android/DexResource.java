package org.apache.tuscany.sca.android;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;

public class DexResource {
	
	private URL url;
	
	private String folder;
	
	private String file;
	
	public DexResource(URL url) {
		String protocol = url.getProtocol();
		
		if (!"dex".equals(protocol)) {
			throw new IllegalArgumentException("The URL protocol should be \"dex\"");
		}
		
		String host = url.getHost();
		
		if ("".equals(host)) {
			throw new IllegalArgumentException("The host should not be empty!");
		}
		
		String path = url.getPath();
		file = getFile(path);
		folder = getFolder(path);
		
		if (file != null && file.indexOf('/') != -1) {
			throw new IllegalArgumentException("The dex URL format should be: dex:/<package>/[<folder>]/[<file>] only");
		}
		
		this.url = url;
		
	}
	
	public static String getFolder(String path) {
		String file = path.trim();
		
		int firstSlashIndex = file.indexOf('/');
		
		if ("".equals(file) || "/".equals(file) || firstSlashIndex == -1 || firstSlashIndex >= file.length() - 1) {
			return null;
		}
		
		int secondSlashIndex = file.indexOf("/", firstSlashIndex + 1);
		
		if (secondSlashIndex == -1 || secondSlashIndex >= file.length() - 1 || firstSlashIndex == secondSlashIndex - 1) {
			return null;
		}
		
		return file.substring(firstSlashIndex + 1, secondSlashIndex);
		
	}
	
	public static String getFile(String path) {
		String file = path.trim();
		
		int firstSlashIndex = file.indexOf('/');
		
		if ("".equals(file) || "/".equals(file) || firstSlashIndex == -1 || firstSlashIndex >= file.length() - 1) {
			return null;
		}
		
		int secondSlashIndex = file.indexOf("/", firstSlashIndex + 1);
		
		if (secondSlashIndex == -1 || secondSlashIndex >= file.length() - 1 || firstSlashIndex == secondSlashIndex - 1) {
			return null;
		}
		
		return file.substring(secondSlashIndex + 1);
		
	}
	
	public String getFolderName() {
		return folder;
	}
	
	public Context getContext() {
		Context[] contexts = ContextRegistry.getContexts(url.getHost());
		
		if (contexts.length == 0) {
			return null;
		}
		
		return contexts[0];
		
	}
	
	public String getFileName() {
		return file;
	}
	
	public boolean isPackage() {
		return folder == null;
	}
	
	public boolean isFolder() {
		return file == null && folder != null;
	}
	
	public boolean isFile() {
		return file != null;
	}
	
	public String getPackageName() {
		return url.getHost();
	}
	
	public URI[] getContentFiles() throws IOException {
		
		if (isFile()) {
			throw new UnsupportedOperationException("Not supported when the resource is a file!");
		}
		
		String packageName = url.getHost();
		
		Context[] contexts = ContextRegistry.getContexts(packageName);
		
		if (contexts.length == 0) {
			throw new IOException("Android context not found!");
		}
		
		Context context = contexts[0];
		
		ArrayList<URI> files = new ArrayList<URI>();
		StringBuffer className = new StringBuffer(packageName).append(".R");
		
			if (isPackage()) {
				ClassLoader classLoader = context.getClass().getClassLoader();
				
				try {
				
					for (String folderName : new String[] {"raw", "xml"}) {
						Class clazz = classLoader.loadClass(className.toString() + '$' + folderName);
						folderName = '/' + folderName + '/';
						Field[] fields = clazz.getFields();
						
						for (Field field : fields) {
							try {
								files.add(new URI("dex://" + packageName + folderName + field.getName()));
							} catch (URISyntaxException e) {}
						}
						
					}
				
				} catch (ClassNotFoundException e) {}
				
			} else {
				
				try {
					className.append('$').append(folder);
					Class clazz = getClass().getClassLoader().loadClass(className.toString());
					String folderName = '/' + clazz.getSimpleName() + '/';  
					Field[] fields = clazz.getFields();
					
					for (Field field : fields) {
						try {
							files.add(new URI("dex://" + packageName + folderName + field.getName()));
						} catch (URISyntaxException e) {}
					}
				
				} catch (ClassNotFoundException e) {
					throw new IOException("Resource not found!");
				}
				
			}
			
		
		
		URI[] ret = new URI[files.size()];
		files.toArray(ret);
		
		return ret;
		
	}
	
	public URL getURL() {
		return url;
	}
	
}
