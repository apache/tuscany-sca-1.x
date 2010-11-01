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

import java.util.concurrent.CountDownLatch;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

/**
 * This class implements the HelloWorld service.
 */
@Service(HelloWorldService.class)
public class HelloWorldClientImpl implements HelloWorldService {
    
    @Reference
    protected HelloWorldService helloworld;
    
    CountDownLatch resultsReceivedCountdown;
    
    private static int threadCount = 1000;

    public String getGreetings(String name) {
    	resultsReceivedCountdown = new CountDownLatch(threadCount);

        SendRequest[] requests = new SendRequest[threadCount];
        for (int i=0; i < threadCount; i++){
        	requests[i] = new SendRequest(name+i);
        }
        
        long start = System.currentTimeMillis();
        
        for (int i=0; i < threadCount; i++){
        	new Thread(requests[i]).start();
        }
        
        try {
            resultsReceivedCountdown.await();
        } catch (InterruptedException ex) {
        }
        
        long stop = System.currentTimeMillis();
        
    	return "Time = " + (stop - start);
    }
    
    public class SendRequest implements Runnable {
    	String name;
    	
    	public SendRequest(String name){
    		this.name = name;
    	}
    	
    	public void run() {
    		String response = "Client Hello " + helloworld.getGreetings(name);
    		System.out.println(response);
    		resultsReceivedCountdown.countDown();
    	}
    	
    }

}
