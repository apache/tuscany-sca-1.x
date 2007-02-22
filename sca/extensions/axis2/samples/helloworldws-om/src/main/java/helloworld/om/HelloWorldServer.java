package helloworld.om;


import java.io.IOException;

import org.apache.tuscany.api.SCAContainer;

public class HelloWorldServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SCAContainer.start("META-INF/sca/helloworldws-om.composite");
		
		try {
			System.out.println("HelloWorld server started");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SCAContainer.stop();
		System.out.println("HelloWorld server stopped");
	}

}
