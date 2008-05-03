package org.apache.tuscany.sca.android;

import java.util.HashSet;
import java.util.Hashtable;

import android.content.Context;

public class ContextRegistry {
	
	private static Hashtable<String, HashSet<Context>> contexts = new Hashtable<String, HashSet<Context>>();
	
	public static void registerContext(Context context) {
		String packageName = context.getPackageName();
		HashSet<Context> packContexts = contexts.get(packageName);
		
		if (packContexts == null) {
			packContexts = new HashSet<Context>();
			contexts.put(packageName, packContexts);
			
		}
		
		packContexts.add(context);
		
	}
	
	public static void unregisterContext(Context context) {
		String packageName = context.getPackageName();
		HashSet<Context> packContexts = contexts.get(packageName);
		
		if (packContexts != null) {
			packContexts.remove(context);
			
			if (packContexts.isEmpty()) {
				contexts.remove(packageName);
			}
			
		}
		
	}
	
	public static Context[] getContexts(String packageName) {
		HashSet<Context> packageContexts = contexts.get(packageName);
		
		if (packageContexts == null) {
			return new Context[0];
		}
		
		Context[] ret = new Context[packageContexts.size()];
		packageContexts.toArray(ret);
		
		return ret;
		
	}

}
