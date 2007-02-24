package org.apache.tuscany.sca.util;


import java.io.IOException;

import org.apache.tuscany.api.SCAContainer;

public class SCATestUtilityServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SCAContainer.start("bindingsutility-system.composite", "bindingsutility.composite");
		
		try {
			System.out.println("SCATestUtility server started");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SCAContainer.stop();
		System.out.println("SCATestUtility server stopped");
	}

}
