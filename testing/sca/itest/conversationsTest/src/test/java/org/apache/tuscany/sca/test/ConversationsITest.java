package org.apache.tuscany.sca.test;

import junit.framework.TestCase;
import org.osoa.sca.CurrentCompositeContext;
import org.osoa.sca.annotations.Reference;

public class ConversationsITest extends TestCase  {

    private ConversationsClient aConversationsClient;
	
    public void testConversations() {
    	aConversationsClient.run(); 
    }

    protected void setUp() throws Exception {
      super.setUp();   
      aConversationsClient = CurrentCompositeContext.getContext().locateService(ConversationsClient.class, "ConversationsClient/org.apache.tuscany.sca.test.ConversationsClient");
 
    }
   
       
   
}
