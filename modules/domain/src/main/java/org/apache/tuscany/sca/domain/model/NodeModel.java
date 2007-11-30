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

package org.apache.tuscany.sca.domain.model;

import java.io.Externalizable;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;


/**
 * A node. Runs SCA composites
 * 
 * @version $Rev: 552343 $ $Date: 2007-09-07 12:41:52 +0100 (Fri, 07 Sep 2007) $
 */
public interface NodeModel {
    public enum LifecyleState {AVAILABLE, DEPLOYED, RUNNING, UNAVAILABLE }; 
    
    /**
     * Retrieve the node uri
     * 
     * @return node uri
     */
    public String getNodeURI();
    
    /**
     * Set the node uri
     * 
     * @param nodeURI
     */    
    public void setNodeURI(String nodeURI);    
    
    /**
     * Retrieve the node url
     *
     * @return node url
     */    
    public String getNodeURL();
   
    /**
     * Set the node url
     * 
     * @param nodeURL
     */    
    public void setNodeURL(String nodeURL);
    
    /**
     * Returns true if the node has been started
     *
     * @return tru if the node is running
     */    
    public boolean getIsRunning();
   
    /**
     * Set the running status of the node
     * 
     * @param isRunning
     */    
    public void setIsRunning(boolean isRunning);    
    
    /**
     * Retrieve the node manager reference
     *
     * @return node manager reference
     */    
    public Externalizable getNodeManagerReference();
   
    /**
     * Set the node url
     * 
     * @param nodeURL
     */    
    public void setNodeManagerReference(Externalizable nodeManagerReference);    
   
    public Map<String, ContributionModel> getContributions();
    public Map<QName, CompositeModel> getDeployedComposites();
    public Map<String, ServiceModel> getServices();
}
