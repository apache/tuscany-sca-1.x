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
package scatours.smsgateway;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class SMSGatewayCORBAServiceBootstrap {

    public static void main(String[] args) throws Exception {
        System.out.println("Publishing SMS Gateway Service as a CORBA service: SMSGatewayCORBAService (port=5080)");
        
        String[] orbArgs = {"-ORBInitialPort", "5080"};
        ORB orb = ORB.init( orbArgs, null );

        NamingContextExt namingCtx;
        try {
          Object objRef = orb.resolve_initial_references("NameService");
          namingCtx = NamingContextExtHelper.narrow(objRef);
        } catch (Exception ex) {
          System.err.println("ERROR: Failed to resolve Name Service.");
          System.err.println("Don't forget to run it with:");
          System.err.println("  tnameserv -ORBInitialPort 5080");
          return;
        }

        Object rootPoaRef = orb.resolve_initial_references("RootPOA");
        POA rootPoa = POAHelper.narrow(rootPoaRef);
        rootPoa.the_POAManager().activate();
        
        SMSGatewayServant smsGateway = new SMSGatewayServant();
        Object smsGatewayRef = rootPoa.servant_to_reference(smsGateway);

        String corbaServerName = "SMSGatewayCORBAService";
        NameComponent[]  name = { new NameComponent(corbaServerName, "") };
        namingCtx.rebind(name, smsGatewayRef);

        System.out.println("CORBA server running - waiting for requests");
        orb.run();
    }
}
