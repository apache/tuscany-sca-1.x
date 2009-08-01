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
package test.abdera;

import java.net.Socket;
import java.util.List;

import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class NewsServiceTestCase {
	   private static SCADomain domain;
	    private static NewsService newsService;

	    @BeforeClass
	    public static void init() throws Exception {
	        domain = SCADomain.newInstance("news/news.composite");
	        Assert.assertNotNull(domain);
	        newsService = domain.getService(NewsService.class, "NewsServiceComponent");
	        Assert.assertNotNull(newsService);
	    }

	    @AfterClass
	    public static void destroy() throws Exception {
	        if(domain != null) {
	            domain.close();
	        }
	    }
	    
	    @Test
	    public void testPing() throws Exception {
	        new Socket("127.0.0.1", 8085);
	        System.in.read();
	    }
	    
	    @Test
	    public void testNewsServicesGetAll() throws Exception {    	
	        List<Entry> entries = newsService.getAll();
	        
	        Assert.assertNotNull(entries);
	        Assert.assertTrue(entries.size() > 0);
	        
	        for(Entry entry : entries) {
	            System.out.println(">>> Entry - " + ((Item)entry.getData()).getTitle());
	        }
	    }
	    
	    @Test
	    public void testNewsServicesGet() throws Exception {    	
	        Item item = newsService.get("1");
	        
	        Assert.assertNotNull(item);
	        System.out.println(">>> Entry - " + item.getTitle());
	    }	    
}
