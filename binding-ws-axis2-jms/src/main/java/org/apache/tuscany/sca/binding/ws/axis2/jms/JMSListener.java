/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.tuscany.sca.binding.ws.axis2.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.TextMessage;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.TransportInDescription;
import org.apache.tuscany.sca.binding.ws.axis2.jms.ctype.ContentTypeRuleFactory;
import org.apache.tuscany.sca.binding.ws.axis2.jms.ctype.ContentTypeRuleSet;
import org.apache.tuscany.sca.binding.ws.axis2.jms.ctype.MessageTypeRule;
import org.apache.tuscany.sca.binding.ws.axis2.jms.ctype.PropertyRule;
import org.apache.tuscany.sca.binding.ws.axis2.transport.base.AbstractTransportListener;
import org.apache.tuscany.sca.binding.ws.axis2.transport.base.BaseConstants;
import org.apache.tuscany.sca.binding.ws.axis2.transport.base.ManagementSupport;
import org.apache.tuscany.sca.binding.ws.axis2.transport.base.event.TransportErrorListener;
import org.apache.tuscany.sca.binding.ws.axis2.transport.base.event.TransportErrorSource;
import org.apache.tuscany.sca.binding.ws.axis2.transport.base.event.TransportErrorSourceSupport;

/**
 * The revamped JMS Transport listener implementation. Creates {@link ServiceTaskManager} instances
 * for each service requesting exposure over JMS, and stops these if they are undeployed / stopped.
 * <p>
 * A service indicates a JMS Connection factory definition by name, which would be defined in the
 * JMSListner on the axis2.xml, and this provides a way to reuse common configuration between
 * services, as well as to optimize resources utilized
 * <p>
 * If the connection factory name was not specified, it will default to the one named "default"
 * {@see JMSConstants.DEFAULT_CONFAC_NAME}
 * <p>
 * If a destination JNDI name is not specified, a service will expect to use a Queue with the same
 * JNDI name as of the service. Additional Parameters allows one to bind to a Topic or specify
 * many more detailed control options. See package documentation for more details
 * <p>
 * All Destinations / JMS Administered objects used MUST be pre-created or already available 
 */
public class JMSListener extends AbstractTransportListener implements ManagementSupport,
    TransportErrorSource {

    public static final String TRANSPORT_NAME = Constants.TRANSPORT_JMS;

    /** The JMSConnectionFactoryManager which centralizes the management of defined factories */
    private JMSConnectionFactoryManager connFacManager;
    /** A Map of service name to the JMS endpoints */
    private Map<String,JMSEndpoint> serviceNameToEndpointMap = new HashMap<String,JMSEndpoint>();
    /** A Map of service name to its ServiceTaskManager instances */
    private Map<String, ServiceTaskManager> serviceNameToSTMMap =
        new HashMap<String, ServiceTaskManager>();
    private final TransportErrorSourceSupport tess = new TransportErrorSourceSupport(this);
    
    /**
     * TransportListener initialization
     *
     * @param cfgCtx the Axis configuration context
     * @param trpInDesc the TransportIn description
     */
    public void init(ConfigurationContext cfgCtx,
        TransportInDescription trpInDesc) throws AxisFault {

        super.init(cfgCtx, trpInDesc);
        connFacManager = new JMSConnectionFactoryManager(trpInDesc);
        log.info("JMS Transport Receiver/Listener initialized...");
    }

    /**
     * Returns EPRs for the given service over the JMS transport
     *
     * @param serviceName service name
     * @return the JMS EPRs for the service
     */
    public EndpointReference[] getEPRsForService(String serviceName) {
        //Strip out the operation name
        if (serviceName.indexOf('/') != -1) {
            serviceName = serviceName.substring(0, serviceName.indexOf('/'));
        }
        // strip out the endpoint name if present
        if (serviceName.indexOf('.') != -1) {
            serviceName = serviceName.substring(0, serviceName.indexOf('.'));
        }
        JMSEndpoint endpoint = serviceNameToEndpointMap.get(serviceName);
        if (endpoint != null) {
            return endpoint.getEndpointReferences();
        } else {
            return null;
        }
    }

    /**
     * Listen for JMS messages on behalf of the given service
     *
     * @param service the Axis service for which to listen for messages
     */
    protected void startListeningForService(AxisService service) throws AxisFault {
        JMSConnectionFactory cf = getConnectionFactory(service);
        if (cf == null) {
            throw new AxisFault("The service doesn't specify a JMS connection factory or refers " +
                "to an invalid factory.");
        }

        JMSEndpoint endpoint = new JMSEndpoint();
        endpoint.setService(service);
        endpoint.setCf(cf);

        Parameter destParam = service.getParameter(JMSConstants.PARAM_DESTINATION);
        if (destParam != null) {
            endpoint.setJndiDestinationName((String)destParam.getValue());
        } else {
            // Assume that the JNDI destination name is the same as the service name
            endpoint.setJndiDestinationName(service.getName());
        }
        
        Parameter destTypeParam = service.getParameter(JMSConstants.PARAM_DEST_TYPE);
        if (destTypeParam != null) {
            String paramValue = (String) destTypeParam.getValue();
            if (JMSConstants.DESTINATION_TYPE_QUEUE.equals(paramValue) ||
                    JMSConstants.DESTINATION_TYPE_TOPIC.equals(paramValue) )  {
                endpoint.setDestinationType(paramValue);
            } else {
                throw new AxisFault("Invalid destinaton type value " + paramValue);
            }
        } else {
            log.debug("JMS destination type not given. default queue");
            endpoint.setDestinationType(JMSConstants.DESTINATION_TYPE_QUEUE);
        }
        
        Parameter contentTypeParam = service.getParameter(JMSConstants.CONTENT_TYPE_PARAM);
        if (contentTypeParam == null) {
            ContentTypeRuleSet contentTypeRuleSet = new ContentTypeRuleSet();
            contentTypeRuleSet.addRule(new PropertyRule(BaseConstants.CONTENT_TYPE));
            contentTypeRuleSet.addRule(new MessageTypeRule(BytesMessage.class, "application/octet-stream"));
            contentTypeRuleSet.addRule(new MessageTypeRule(TextMessage.class, "text/plain"));
            endpoint.setContentTypeRuleSet(contentTypeRuleSet);
        } else {
            endpoint.setContentTypeRuleSet(ContentTypeRuleFactory.parse(contentTypeParam));
        }

        endpoint.computeEPRs(); // compute service EPR and keep for later use        
        serviceNameToEndpointMap.put(service.getName(), endpoint);
        
        ServiceTaskManager stm = JMSUtils.createTaskManagerForService(cf, service, workerPool);
        stm.setJmsMessageReceiver(new JMSMessageReceiver(this, cf, endpoint));
        stm.start();
        serviceNameToSTMMap.put(service.getName(), stm);

        for (int i=0; i<3; i++) {
            if (stm.getActiveTaskCount() > 0) {
                log.info("Started to listen on destination : " + stm.getDestinationJNDIName() +
                    " of type " + JMSUtils.getDestinationTypeAsString(stm.getDestinationType()) +
                    " for service " + stm.getServiceName());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {}
        }

        log.warn("Polling tasks on destination : " + stm.getDestinationJNDIName() +
            " of type " + JMSUtils.getDestinationTypeAsString(stm.getDestinationType()) +
            " for service " + stm.getServiceName() + " have not yet started after 3 seconds ..");
    }

    /**
     * Stops listening for messages for the service thats undeployed or stopped
     *
     * @param service the service that was undeployed or stopped
     */
    protected void stopListeningForService(AxisService service) {

        ServiceTaskManager stm = serviceNameToSTMMap.get(service.getName());
        if (stm != null) {
            if (log.isDebugEnabled()) {
                log.debug("Stopping listening on destination : " + stm.getDestinationJNDIName() +
                    " for service : " + stm.getServiceName());
            }

            stm.stop();

            serviceNameToSTMMap.remove(service.getName());
            serviceNameToEndpointMap.remove(service.getName());
            log.info("Stopped listening for JMS messages to service : " + service.getName());

        } else {
            log.error("Unable to stop service : " + service.getName() +
                " - unable to find its ServiceTaskManager");
        }
    }
    /**
     * Return the connection factory name for this service. If this service
     * refers to an invalid factory or defaults to a non-existent default
     * factory, this returns null
     *
     * @param service the AxisService
     * @return the JMSConnectionFactory to be used, or null if reference is invalid
     */
    public JMSConnectionFactory getConnectionFactory(AxisService service) {

        Parameter conFacParam = service.getParameter(JMSConstants.PARAM_JMS_CONFAC);
        // validate connection factory name (specified or default)
        if (conFacParam != null) {
            return connFacManager.getJMSConnectionFactory((String) conFacParam.getValue());
        } else {
            return connFacManager.getJMSConnectionFactory(JMSConstants.DEFAULT_CONFAC_NAME);
        }
    }

    // -- jmx/management methods--
    /**
     * Pause the listener - Stop accepting/processing new messages, but continues processing existing
     * messages until they complete. This helps bring an instance into a maintenence mode
     * @throws AxisFault on error
     */
    public void pause() throws AxisFault {
        if (state != BaseConstants.STARTED) return;
        try {
            for (ServiceTaskManager stm : serviceNameToSTMMap.values()) {
                stm.pause();
            }
            state = BaseConstants.PAUSED;
            log.info("Listener paused");
        } catch (AxisJMSException e) {
            log.error("At least one service could not be paused", e);
        }
    }

    /**
     * Resume the lister - Brings the lister into active mode back from a paused state
     * @throws AxisFault on error
     */
    public void resume() throws AxisFault {
        if (state != BaseConstants.PAUSED) return;
        try {
            for (ServiceTaskManager stm : serviceNameToSTMMap.values()) {
                stm.resume();
            }
            state = BaseConstants.STARTED;
            log.info("Listener resumed");
        } catch (AxisJMSException e) {
            log.error("At least one service could not be resumed", e);
        }
    }

    /**
     * Stop processing new messages, and wait the specified maximum time for in-flight
     * requests to complete before a controlled shutdown for maintenence
     *
     * @param millis a number of milliseconds to wait until pending requests are allowed to complete
     * @throws AxisFault on error
     */
    public void maintenenceShutdown(long millis) throws AxisFault {
        if (state != BaseConstants.STARTED) return;
        try {
            long start = System.currentTimeMillis();
            stop();
            state = BaseConstants.STOPPED;
            log.info("Listener shutdown in : " + (System.currentTimeMillis() - start) / 1000 + "s");
        } catch (Exception e) {
            handleException("Error shutting down the listener for maintenence", e);
        }
    }

    public void addErrorListener(TransportErrorListener listener) {
        tess.addErrorListener(listener);
    }

    public void removeErrorListener(TransportErrorListener listener) {
        tess.removeErrorListener(listener);
    }

    void error(AxisService service, Throwable ex) {
        tess.error(service, ex);
    }
}
