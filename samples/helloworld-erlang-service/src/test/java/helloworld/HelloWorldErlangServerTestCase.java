/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package helloworld;

import java.io.InputStream;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import helloworld.dynaignore.IgnorableRunner;
import helloworld.dynaignore.IgnoreTest;

import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests that the helloworld server is available
 */
@RunWith(IgnorableRunner.class)
public class HelloWorldErlangServerTestCase {

	private static final String EPMD_COMMAND = "epmd";

	@Test
	public void testServiceCall() {
		Process epmdProcess = null;
		try {
			epmdProcess = Runtime.getRuntime().exec(EPMD_COMMAND);
		} catch (IOException e) {
			System.out
					.println("Cannot proceed - exception while executing "
							+ EPMD_COMMAND
							+ ": "
							+ e.getMessage()
							+ ". Valid and working Erlang/OTP distribution is required.");
			throw new IgnoreTest();
		}
        startReaderThread(epmdProcess.getInputStream());
        startReaderThread(epmdProcess.getErrorStream());

		SCADomain scaDomain = SCADomain
				.newInstance("helloworlderlangservice.composite");
		HelloWorldService helloWorldService = scaDomain.getService(
				HelloWorldService.class,
				"HelloWorldServiceComponent/HelloWorldService");
		assertNotNull(helloWorldService);
		assertEquals("Hello Smith", helloWorldService.getGreetings("Smith"));
		scaDomain.close();

		epmdProcess.destroy();
	}

    private static void startReaderThread(final InputStream stream) {
        Thread readerThread = new Thread() {
            public void run() {
                try {
                    byte[] buf = new byte[100];
                    while (true) {
                        stream.read(buf);
                    }
                } catch (Exception e) {
                    return;
                }
            }
        };
        readerThread.start();
    }

}
