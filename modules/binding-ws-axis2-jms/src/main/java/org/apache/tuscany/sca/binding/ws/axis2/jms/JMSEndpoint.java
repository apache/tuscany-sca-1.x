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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.Parameter;
import org.apache.tuscany.sca.binding.ws.axis2.jms.ctype.ContentTypeRuleSet;

/**
 * Class that links an Axis2 service to a JMS destination. Additionally, it contains
 * all the required information to process incoming JMS messages and to inject them
 * into Axis2.
 */
public class JMSEndpoint {
    private JMSConnectionFactory cf;
    private AxisService service;
    private String jndiDestinationName;
    private int destinationType = JMSConstants.GENERIC;
    private Set<EndpointReference> endpointReferences = new HashSet<EndpointReference>();
    private ContentTypeRuleSet contentTypeRuleSet;

    public AxisService getService() {
        return service;
    }

    public void setService(AxisService service) {
        this.service = service;
    }
    
    public String getServiceName() {
        return service.getName();
    }

    public String getJndiDestinationName() {
        return jndiDestinationName;
    }

    public void setJndiDestinationName(String destinationJNDIName) {
        this.jndiDestinationName = destinationJNDIName;
    }

    public void setDestinationType(String destinationType) {
        if (JMSConstants.DESTINATION_TYPE_TOPIC.equalsIgnoreCase(destinationType)) {
            this.destinationType = JMSConstants.TOPIC;
        } else if (JMSConstants.DESTINATION_TYPE_QUEUE.equalsIgnoreCase(destinationType)) {
            this.destinationType = JMSConstants.QUEUE;
        } else {
            this.destinationType = JMSConstants.GENERIC;
        }
    }

    public EndpointReference[] getEndpointReferences() {
        return endpointReferences.toArray(new EndpointReference[endpointReferences.size()]);
    }

    public void computeEPRs() {
        List<EndpointReference> eprs = new ArrayList<EndpointReference>();
        for (Object o : getService().getParameters()) {
            Parameter p = (Parameter) o;
            if (JMSConstants.PARAM_PUBLISH_EPR.equals(p.getName()) && p.getValue() instanceof String) {
                if ("legacy".equalsIgnoreCase((String) p.getValue())) {
                    // if "legacy" specified, compute and replace it
                    endpointReferences.add(
                        new EndpointReference(JMSUtils.getEPR(cf, destinationType, this)));
                } else {
                    endpointReferences.add(new EndpointReference((String) p.getValue()));
                }
            }
        }

        if (eprs.isEmpty()) {
            // if nothing specified, compute and return legacy EPR
            endpointReferences.add(new EndpointReference(JMSUtils.getEPR(cf, destinationType, this)));
        }
    }

    public ContentTypeRuleSet getContentTypeRuleSet() {
        return contentTypeRuleSet;
    }

    public void setContentTypeRuleSet(ContentTypeRuleSet contentTypeRuleSet) {
        this.contentTypeRuleSet = contentTypeRuleSet;
    }

    public JMSConnectionFactory getCf() {
        return cf;
    }

    public void setCf(JMSConnectionFactory cf) {
        this.cf = cf;
    }
}
