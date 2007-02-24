package org.apache.tuscany.sca.itest;


import java.io.IOException;
import java.net.Socket;

import org.apache.tuscany.test.SCATestCase;

public class SCATestToolServerTestCase extends SCATestCase {
	
	@Override
	protected void setUp() throws Exception {
		setSystemSCDL("bindingscomposite-system.composite");
		setApplicationSCDL("bindingscomposite.composite");
		super.setUp();
	}
	
	public void testPing() throws IOException {
		new Socket("127.0.0.1", 8080);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
