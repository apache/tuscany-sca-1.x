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

package org.apache.tuscany.sca.binding.jms;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.tuscany.sca.assembly.AssemblyFactory;
import org.apache.tuscany.sca.assembly.xml.Constants;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.resolver.ModelResolver;
import org.apache.tuscany.sca.contribution.service.ContributionReadException;
import org.apache.tuscany.sca.contribution.service.ContributionResolveException;
import org.apache.tuscany.sca.contribution.service.ContributionWriteException;
import org.apache.tuscany.sca.policy.Intent;
import org.apache.tuscany.sca.policy.PolicyFactory;
import org.apache.tuscany.sca.policy.PolicySet;
import org.apache.tuscany.sca.policy.PolicySetAttachPoint;

/**
 * A processor to read the XML that describes the JMS binding...
 * 
 * <binding.jms correlationScheme="string"?
 *              initialContextFactory="xs:anyURI"?
 *              jndiURL="xs:anyURI"?
 *              requestConnection="QName"?
 *              responseConnection="QName"?
 *              operationProperties="QName"?
 *              ...>
 * 
 *     <destination name="xs:anyURI" type="string"? create="string"?>
 *         <property name="NMTOKEN" type="NMTOKEN">*
 *     </destination>?
 * 
 *     <connectionFactory name="xs:anyURI" create="string"?>
 *         <property name="NMTOKEN" type="NMTOKEN">*
 *     </connectionFactory>?
 * 
 *     <activationSpec name="xs:anyURI" create="string"?>
 *         <property name="NMTOKEN" type="NMTOKEN">*
 *     </activationSpec>?
 * 
 *     <response>
 *         <destination name="xs:anyURI" type="string"? create="string"?>
 *             <property name="NMTOKEN" type="NMTOKEN">*
 *         </destination>?
 * 
 *         <connectionFactory name="xs:anyURI" create="string"?>
 *             <property name="NMTOKEN" type="NMTOKEN">*
 *         </connectionFactory>?
 * 
 *         <activationSpec name="xs:anyURI" create="string"?>
 *             <property name="NMTOKEN" type="NMTOKEN">*
 *         </activationSpec>?
 *     </response>?
 * 
 *     <resourceAdapter name="NMTOKEN">?
 *         <property name="NMTOKEN" type="NMTOKEN">*
 *     </resourceAdapter>?
 * 
 *     <headers JMSType="string"?
 *              JMSCorrelationId="string"?
 *              JMSDeliveryMode="string"?
 *              JMSTimeToLive="int"?
 *              JMSPriority="string"?>
 *         <property name="NMTOKEN" type="NMTOKEN">*
 *     </headers>?
 * 
 *     <operationProperties name="string" nativeOperation="string"?>
 *         <property name="NMTOKEN" type="NMTOKEN">*
 *         <headers JMSType="string"?
 *                  JMSCorrelationId="string"?
 *                  JMSDeliveryMode="string"?
 *                  JMSTimeToLive="int"?
 *                  JMSPriority="string"?>
 *             <property name="NMTOKEN" type="NMTOKEN">*
 *         </headers>?
 *     </operationProperties>*
 * </binding.jms>
 */

public class JMSBindingProcessor implements StAXArtifactProcessor<JMSBinding>{
    
    private AssemblyFactory assemblyFactory;
    private PolicyFactory policyFactory;    
    private JMSBindingFactory jmsBindingFactory;


    public JMSBindingProcessor(AssemblyFactory assemblyFactory,
                               PolicyFactory policyFactory,
                               JMSBindingFactory jmsBindingFactory) {
        this.assemblyFactory = assemblyFactory;
        this.policyFactory = policyFactory;
        this.jmsBindingFactory = jmsBindingFactory;
    }

    public QName getArtifactType() {
        return JMSBindingConstants.BINDING_JMS_QNAME;
    }

    public Class<JMSBinding> getModelType() {
        return JMSBinding.class;
    }

    public JMSBinding read(XMLStreamReader reader) 
      throws ContributionReadException {
        try {
            JMSBinding jmsBinding = jmsBindingFactory.createJMSBinding();
            
            //Read policies
            readPolicies(jmsBinding, reader);

            // Read binding URI
            String uri = reader.getAttributeValue(null, "uri");
            if (uri != null && uri.length() > 0) {
                jmsBinding.setURI(uri);
            }

            // Read correlation scheme
            String correlationScheme = reader.getAttributeValue(null, "correlationScheme");
            if (correlationScheme != null && correlationScheme.length() > 0) {
                if (JMSBindingConstants.VALID_CORRELATION_SCHEMES.contains(correlationScheme.toLowerCase())) {
                    jmsBinding.setCorrelationScheme(correlationScheme);
                } else {
                    throw new JMSBindingException("invalid correlationScheme: " + correlationScheme);
                }
            }

            // Read initial context factory
            String initialContextFactory = reader.getAttributeValue(null, "initialContextFactory");
            if (initialContextFactory != null && initialContextFactory.length() > 0) {
                jmsBinding.setInitialContextFactoryName(initialContextFactory);
            }

            // Read jndi URL
            String jndiURL = reader.getAttributeValue(null, "jndiURL");
            if (jndiURL != null && jndiURL.length() > 0) {
                jmsBinding.setJndiURL(jndiURL);
            }
            
            // Read requestConnection
            // TODO
            // Read reponseConnection
            // TODO
            // Read operationProperties
            // TODO
            
            // Read subelements of binding.jms
            while (true) {
                switch (reader.next()) {
                    case START_ELEMENT:
                        String elementName = reader.getName().getLocalPart();
                        if ("destination".equals(elementName)) {
                            parseDestination(reader, jmsBinding);
                        } else if ("connectionFactory".equals(elementName)) {
                            parseConnectionFactory(reader, jmsBinding);
                        } else if ("activationSpec".equals(elementName)) {
                            parseActivationSpec(reader, jmsBinding);
                        } else if ("response".equals(elementName)) {
                            parseResponse(reader, jmsBinding);
                        } else if ("resourceAdapter".equals(elementName)) {
                            parseResourceAdapter(reader, jmsBinding);                            
                        } else if ("headers".equals(elementName)) {
                            parseHeaders(reader, jmsBinding);
                        } else if ("operationProperties".equals(elementName)) {
                            parseOperationProperties(reader, jmsBinding);
                        } 
                        reader.next();
                        break;
                    case END_ELEMENT:
                        QName x = reader.getName();
                        if (x.equals(JMSBindingConstants.BINDING_JMS_QNAME)) {
                            return jmsBinding;
                        }
                        throw new RuntimeException("Incomplete binding.jms definition found unexpected element " + x.toString());
                }
            }
 /*           
            // Skip to end element
            while (reader.hasNext()) {
                if (reader.next() == END_ELEMENT && 
                    BINDING_JMS_QNAME.equals(reader.getName())) {
                    break;
                }
            }
            return jmsBinding;
*/
        } catch (XMLStreamException e) {
            throw new ContributionReadException(e);
        }
    }
    
    public void resolve(JMSBinding model, ModelResolver resolver) throws ContributionResolveException {
    }    

    public void write(JMSBinding rmiBinding, XMLStreamWriter writer) throws ContributionWriteException {
        try {
            // Write a <binding.ws>
            writer.writeStartElement(Constants.SCA10_NS, JMSBindingConstants.BINDING_JMS);
/*            
            if (rmiBinding.getRmiHostName() != null) {
                writer.writeAttribute(RMIBindingConstants.RMI_HOST, rmiBinding.getRmiHostName());
            }
            
            if (rmiBinding.getRmiPort() != null) {
                writer.writeAttribute(RMIBindingConstants.RMI_PORT, rmiBinding.getRmiPort());
            }
            
            if (rmiBinding.getRmiServiceName() != null) {
                writer.writeAttribute(RMIBindingConstants.RMI_SERVICE, rmiBinding.getRmiServiceName());
            }
*/            
            writer.writeEndElement();

        } catch (XMLStreamException e) {
            throw new ContributionWriteException(e);
        }
    }


    /**
     * Reads policy intents and policy sets.
     * @param attachPoint where the intents and policy sets will be stored
     * @param reader the XML stream reader
     */
    private void readPolicies(PolicySetAttachPoint attachPoint, XMLStreamReader reader) {
        String value = reader.getAttributeValue(null, Constants.REQUIRES);
        if (value != null) {
            List<Intent> requiredIntents = attachPoint.getRequiredIntents();
            for (StringTokenizer tokens = new StringTokenizer(value); tokens.hasMoreTokens();) {
                QName qname = getQNameValue(reader, tokens.nextToken());
                Intent intent = policyFactory.createIntent();
                intent.setName(qname);
                requiredIntents.add(intent);
            }
        }

        value = reader.getAttributeValue(null, Constants.POLICY_SETS);
        if (value != null) {
            List<PolicySet> policySets = attachPoint.getPolicySets();
            for (StringTokenizer tokens = new StringTokenizer(value); tokens.hasMoreTokens();) {
                QName qname = getQNameValue(reader, tokens.nextToken());
                PolicySet policySet = policyFactory.createPolicySet();
                policySet.setName(qname);
                policySets.add(policySet);
            }
        }
    }
    
    /**
     * Returns a qname from a string.  
     * @param reader the XML stream reader
     * @param value the string version of the QName
     * @return
     */
    private QName getQNameValue(XMLStreamReader reader, String value) {
        if (value != null) {
            int index = value.indexOf(':');
            String prefix = index == -1 ? "" : value.substring(0, index);
            String localName = index == -1 ? value : value.substring(index + 1);
            String ns = reader.getNamespaceContext().getNamespaceURI(prefix);
            if (ns == null) {
                ns = "";
            }
            return new QName(ns, localName, prefix);
        } else {
            return null;
        }
    } 
    
    protected void parseDestination(XMLStreamReader reader, JMSBinding jmsBinding) throws XMLStreamException {
        String name = reader.getAttributeValue(null, "name");
        if (name != null && name.length() > 0) {
            jmsBinding.setDestinationName(name);
        }
        String type = reader.getAttributeValue(null, "type");
        if (type != null && type.length() > 0) {
            if ("queue".equalsIgnoreCase(type)) {
                jmsBinding.setDestinationType(JMSBindingConstants.DESTINATION_TYPE_QUEUE);
            } else if ("topic".equalsIgnoreCase("type")) {
                jmsBinding.setDestinationType(JMSBindingConstants.DESTINATION_TYPE_TOPIC);
            } else {
                throw new RuntimeException("invalid destination type: " + type);
            }
        }
        String create = reader.getAttributeValue(null, "create");
        if (create != null && create.length() > 0) {
            jmsBinding.setDestinationCreate(create);
        }
    } 
    
    protected void parseConnectionFactory(XMLStreamReader reader, JMSBinding jmsBinding) {
        String name = reader.getAttributeValue(null, "name");
        if (name != null && name.length() > 0) {
            jmsBinding.setConnectionFactoryName(name);
        } else {
            throw new RuntimeException("missing connectionFactory name");
        }
    }    

    protected void parseActivationSpec(XMLStreamReader reader, JMSBinding jmsBinding) {
        String name = reader.getAttributeValue(null, "name");
        if (name != null && name.length() > 0) {
            jmsBinding.setActivationSpecName(name);
        } else {
            throw new RuntimeException("missing ActivationSpec name");
        }
    }

    protected void parseResponse(XMLStreamReader reader, JMSBinding jmsBinding) {
        // TODO 
    }

    protected void parseResourceAdapter(XMLStreamReader reader, JMSBinding jmsBinding) throws XMLStreamException {
        // TODO 
    }
    
    protected void parseHeaders(XMLStreamReader reader, JMSBinding jmsBinding) throws XMLStreamException {
        // TODO 
    }    

    protected void parseOperationProperties(XMLStreamReader reader, JMSBinding jmsBinding) throws XMLStreamException {
        // TODO 
    }



    
    

}
