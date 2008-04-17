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
package org.apache.tuscany.sca.binding.jms.impl;

import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.tuscany.sca.assembly.xml.Constants;
import org.apache.tuscany.sca.binding.jms.provider.XMLTextMessageProcessor;

public interface JMSBindingConstants {

    // Constants used when describing the JMS binding
    // model and for setting up defaults
    String BINDING_JMS = "binding.jms";
    QName BINDING_JMS_QNAME = new QName(Constants.SCA10_NS, BINDING_JMS);
    String CORRELATE_MSG_ID = "requestmsgidtocorrelid";
    String CORRELATE_CORRELATION_ID = "requestcorrelidtocorrelid";
    String CORRELATE_NONE = "none";
    List<String> VALID_CORRELATION_SCHEMES =
        Arrays.asList(new String[] {CORRELATE_MSG_ID, CORRELATE_CORRELATION_ID, CORRELATE_NONE});
    String DESTINATION_TYPE_QUEUE = "queue"; // 0
    String DESTINATION_TYPE_TOPIC = "topic"; // 1
    List<String> VALID_DESTINATION_TYPES =
        Arrays.asList(new String[] {DESTINATION_TYPE_QUEUE, DESTINATION_TYPE_TOPIC});
    String CREATE_ALWAYS = "always";
    String CREATE_NEVER = "never";
    String CREATE_IF_NOT_EXIST = "ifnotexist";
    String DEFAULT_DESTINATION_NAME = "NODESTINATION";
    String DEFAULT_RESPONSE_DESTINATION_NAME = "NORESPONSEDESTINATION";
    String DEFAULT_CONNECTION_FACTORY_NAME = "ConnectionFactory";
    String DEFAULT_CONTEXT_FACTORY_NAME = "org.apache.activemq.jndi.ActiveMQInitialContextFactory";
    String DEFAULT_JNDI_URL = "tcp://localhost:61616";
    int DEFAULT_TIME_TO_LIVE = 20000; // in milliseconds
    int DEFAULT_PRIORITY = 1;
    String DEFAULT_RF_CLASSNAME = "org.apache.tuscany.sca.host.jms.activemq.JMSResourceFactoryImpl";
    String DEFAULT_MP_CLASSNAME = XMLTextMessageProcessor.class.getName();
    String DEFAULT_OPERATION_PROP_NAME = "scaOperationName";

    String FAULT_PROPERTY = "org.apache.tuscany.sca.fault";

}
