package org.apache.tuscany.sca.util;


import java.io.IOException;
import java.net.Socket;

import org.apache.tuscany.test.SCATestCase;

public class SCATestUtilityServerTestCase extends SCATestCase {
	
	@Override
	protected void setUp() throws Exception {
		setSystemSCDL("bindingsutility-system.composite");
		setApplicationSCDL("bindingsutility.composite");
		super.setUp();
	}
	
	public void testPing() throws IOException {
		new Socket("127.0.0.1", 8081);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
