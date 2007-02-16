package helloworld.server;

import java.io.IOException;

import org.apache.tuscany.api.TuscanyContainer;

public class HelloWorldServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TuscanyContainer.start();
		
		try {
			System.out.println("HelloWorld server started");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TuscanyContainer.stop();
		

	}

}
