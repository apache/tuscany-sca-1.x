package org.apache.tuscany.sca.itest;


import java.io.IOException;

import org.apache.tuscany.api.SCAContainer;

public class SCATestToolServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SCAContainer.start("bindingscomposite-system.composite", "bindingscomposite.composite");
		
		try {
			System.out.println("SCATestTool server started");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SCAContainer.stop();
		System.out.println("SCATestTool server stopped");
	}

}
