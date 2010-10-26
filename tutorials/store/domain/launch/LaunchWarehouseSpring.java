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

package launch;

import java.io.IOException;

import org.apache.activemq.broker.BrokerService;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.launcher.Contribution;
import org.apache.tuscany.sca.node.launcher.NodeLauncher;

public class LaunchWarehouseSpring {
    public static void main(String[] args) throws Exception { 
        
        BrokerService jmsBroker;
        jmsBroker = new BrokerService(); 
        jmsBroker.setPersistent(false);
        jmsBroker.setUseJmx(false);
        jmsBroker.addConnector("tcp://localhost:61619");
        jmsBroker.start();
        
        NodeLauncher launcher = NodeLauncher.newInstance();
        SCANode node = launcher.createNode(null, 
                                           new Contribution("assets", "../assets/target/tutorial-assets.jar"),
                                           new Contribution("warehouse", "../warehouse-spring/target/tutorial-warehouse-spring.jar"));
        node.start();
        
        System.out.println("Press a key to stop");
        try {
            System.in.read();
        } catch (IOException e) {}
        
        node.stop();
        
        jmsBroker.stop();

    }
}
