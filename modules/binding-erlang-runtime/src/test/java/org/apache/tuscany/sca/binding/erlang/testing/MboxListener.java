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

package org.apache.tuscany.sca.binding.erlang.testing;

import java.lang.annotation.Annotation;

import org.apache.tuscany.sca.binding.erlang.impl.types.TypeHelpersProxy;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangPid;
import com.ericsson.otp.erlang.OtpErlangTuple;
import com.ericsson.otp.erlang.OtpMbox;
import com.ericsson.otp.erlang.OtpMsg;

/**
 * @version $Rev$ $Date$
 */
public class MboxListener implements Runnable {

	private OtpMbox mbox;
	private OtpMsg msg;
	private Object response;
	private long duration;

	public MboxListener(OtpMbox mbox, Object response) {
		this(mbox, response, 0);
	}

	public MboxListener(OtpMbox mbox, Object response, long duration) {
		this.mbox = mbox;
		this.response = response;
		this.duration = duration;
	}

	public void run() {
		try {
			msg = mbox.receiveMsg();
			Thread.sleep(duration);
			OtpErlangPid senderPid = null;
			if (response != null) {
				if (msg.getMsg().getClass().equals(OtpErlangTuple.class)
						&& ((OtpErlangTuple) msg.getMsg()).elementAt(0)
								.getClass().equals(OtpErlangPid.class)) {
					senderPid = (OtpErlangPid) ((OtpErlangTuple) msg.getMsg())
							.elementAt(0);
				} else {
					senderPid = msg.getSenderPid();
				}
				mbox.send(senderPid, TypeHelpersProxy.toErlang(response,
						new Annotation[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OtpErlangObject getMsg() {
		try {
			// Sometimes clients tries to get message which isn't fully
			// received.
			// If so - give it more tries. This sometimes caused
			// NullPointerException in
			// ReferenceServiceTestCase.testMultipleArguments().
			for (int i = 0; i < 3; i++) {
				if (msg != null) {
					return ((OtpErlangTuple) msg.getMsg()).elementAt(1);
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
			return ((OtpErlangTuple) msg.getMsg()).elementAt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
