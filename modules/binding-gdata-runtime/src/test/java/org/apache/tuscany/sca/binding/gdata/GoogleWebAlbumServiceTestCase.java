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

package org.apache.tuscany.sca.binding.gdata;

import java.net.Socket;
import java.net.URL;

import junit.framework.Assert;

import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gdata.client.Query;
import com.google.gdata.data.Entry;
import com.google.gdata.data.Feed;

public class GoogleWebAlbumServiceTestCase {

    private static SCADomain scaDomainConsumer = null;
    private static CustomerClient testService = null;    
    
    public GoogleWebAlbumServiceTestCase(){

    }
    
    @BeforeClass
    public static void setUp() throws Exception {
        //Initialize the GData client service (Reference Binding test)
        if (internetConnected()) {
            scaDomainConsumer = SCADomain.newInstance("org/apache/tuscany/sca/binding/gdata/ConsumerGoogleWebAlbum.composite");
            testService = scaDomainConsumer.getService(CustomerClient.class, "CustomerClient");
        }
    }

    @AfterClass
    public static void tearDown(){
        if (scaDomainConsumer != null) {
            scaDomainConsumer.close();
        }
    }        
    
    @Test
    public void testClientGetFeed() throws Exception {
        if (testService == null) {
            // no internet connection
            return;
        }
        Feed feed = testService.clientGetFeed();
        //System.out.println("feed title: " + feed.getTitle().getPlainText());        
        Assert.assertEquals("flowers", feed.getTitle().getPlainText());
     }
    


    
    @Test
    public void testClientGetEntry() throws Exception {
        if (testService == null) {
            // no internet connection
            return;
        }
        String entryID = "photoid/5233468700029715874";
        Entry contactEntry = testService.clientGetEntry(entryID);
        //System.out.println("Entry ID: " + contactEntry.getId());
        Assert.assertTrue(contactEntry.getId().endsWith(entryID));
        //System.out.println("------------------------------------------------------------\n\n");
    }
    
    
    @Test
    public void testClientQuery() throws Exception {
        if (testService == null) {
            // no internet connection
            return;
        }
    	String feedUrlString = "http://picasaweb.google.com/data/feed/api/user/haibotuscany/album/flowers";
    	URL feedURL = new URL(feedUrlString);   
    	Query myQuery = new Query(feedURL);
        myQuery.setMaxResults(100);
        myQuery.setFullTextQuery("photo");    
        Feed resultFeed = testService.clientQuery(myQuery);        
        //System.out.println("Query result feed title: " + resultFeed.getTitle().getPlainText());    
        //System.out.println("Query result entry number: "+ resultFeed.getEntries().size());
        //assertEquals("gdata binding tuscany test", resultFeed.getTitle().getPlainText());
     }
    
    
    
    @Test
    public void testClientPut() throws Exception {  
        if (testService == null) {
            // no internet connection
            return;
        }
        String entryID = "photoid/5233468700029715874";          
        String newBlogEntryTitle = "updatedTitle:dog";
        testService.clientPut(entryID, newBlogEntryTitle);      //update the title
        Thread.sleep(Constants.SLEEP_INTERVAL);            
        Entry updatedEntry = testService.clientGetEntry(entryID);         
        Assert.assertEquals(newBlogEntryTitle, updatedEntry.getTitle().getPlainText());
    }
    
    
    @Test
    @Ignore("Not testing anything")
    public void testClientDelete() throws Exception {
        
    	//Tested and it worked, but only once because we can not delete the same entry twice
    	String entryID = "photoid/5233468698153151618"; 
    	
    	//Delete this entry
    	//To test, change the entryID
        //testService.clientDelete(entryID);
    }    

    private static boolean internetConnected() {
        try {
            // see whether an internet connection is available 
            Socket testInternet = new Socket("tuscany.apache.org", 80);
            testInternet.close();

            // internet connection available
            return true;

        } catch (Exception e) {
            // no internet connection
            System.out.println("Unable to run test because no internet connection available");
            return false;
        }
    }

}
