package org.apache.tuscany.sca.itest;


import java.io.IOException;
import java.net.Socket;

import org.apache.tuscany.test.SCATestCase;

public class HelloWorldServerTestCase extends SCATestCase {
	
	@Override
	protected void setUp() throws Exception {
		setApplicationSCDL("helloworldws.composite");
		super.setUp();
	}
	
	public void testPing() throws IOException, InterruptedException {
		new Socket("127.0.0.1", 8080);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
