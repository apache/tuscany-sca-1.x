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

import java.io.IOException;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * This server program shows how to create an SCA runtime, and start it which
 * activates the helloworld Web service endpoint.
 */
public class HelloWorldServer {

	private static final String EPMD_COMMAND = "epmd";

	public static void main(String[] args) {
		try {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(EPMD_COMMAND);
			} catch (Exception e) {
				System.out
						.println("Cannot proceed - exception while executing "
								+ EPMD_COMMAND
								+ ": "
								+ e.getMessage()
								+ ". Valid and working Erlang/OTP distribution is required.");
			}
			if (process != null) {
				System.out.println("EPMD server started");
				SCADomain scaDomain = SCADomain
						.newInstance("helloworlderlangservice.composite");
				System.out
						.println("HelloWorld server started (press enter to shutdown)");
				System.in.read();
				process.destroy();
				scaDomain.close();
				System.out.println("EPMD server stopped");
				System.out.println("HelloWorld server stopped");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
