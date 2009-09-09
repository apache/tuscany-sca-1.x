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

package scatours;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

public class InteractionLauncher {

    public static void main(String[] args) throws Exception {
    	SCANode node1 = SCANodeFactory.newInstance().createSCANode("client.composite", 
            new SCAContribution("common", "../../contributions/common/target/classes"),
            new SCAContribution("currency", "../../contributions/currency/target/classes"),
            new SCAContribution("calendar", "../../contributions/calendar/target/classes"),
            new SCAContribution("shoppingcart", "../../contributions/shoppingcart/target/classes"),
            new SCAContribution("client", "../../contributions/interaction-client/target/classes"));
    	
    	SCANode node2 = SCANodeFactory.newInstance().createSCANode("service.composite", 
            new SCAContribution("common", "../../contributions/common/target/classes"),
            new SCAContribution("hotel", "../../contributions/hotel/target/classes"),
            new SCAContribution("flight", "../../contributions/flight/target/classes"),
            new SCAContribution("remoteService", "../../contributions/interaction-service-remote/target/classes"));

    	node2.start();
        node1.start();
        
        Runnable localInteraction = ((SCAClient)node1).getService(Runnable.class, "InteractionLocalClient/Runnable");
        localInteraction.run();
        
        Runnable remoteInteraction = ((SCAClient)node1).getService(Runnable.class, "InteractionRemoteClient/Runnable");
        remoteInteraction.run();   
        
        Runnable requestResponseInteraction = ((SCAClient)node1).getService(Runnable.class, "InteractionRequestResponseClient/Runnable");
        requestResponseInteraction.run();
        
        Runnable onewayCallbackInteraction = ((SCAClient)node1).getService(Runnable.class, "InteractionOneWayCallbackClient/Runnable");
        onewayCallbackInteraction.run();
        
        Runnable conversationalInteraction = ((SCAClient)node1).getService(Runnable.class, "InteractionConversationClient/Runnable");
        conversationalInteraction.run();
        
        Runnable statefulCallbackInteraction = ((SCAClient)node1).getService(Runnable.class, "InteractionStatefulCallbackClient/Runnable");
        statefulCallbackInteraction.run();            
        
        node1.stop();
        node2.stop();
    }    
}
