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

package org.apache.tuscany.spi.loader;

import org.apache.tuscany.spi.model.Operation;
import org.apache.tuscany.spi.model.ServiceContract;

import org.apache.tuscany.api.TuscanyException;

/**
 * Denotes a duplicate reference name
 *
 * @version $Rev: 487877 $ $Date: 2006-12-17 02:02:16 +0530 (Sun, 17 Dec 2006) $
 */
public class DuplicateReferenceNameException extends LoaderException {
    public static final String COMPOSITE = "Composite";
    public static final String COMPONENT_TYPE = "ComponentType";
    public static final String MESSAGE = "Duplicat reference name ";
        
    private static final long serialVersionUID = -9127740669182714792L;
    private String duplicateName;
    private String container;
    private String containerName;
    
    public DuplicateReferenceNameException(String dupName, String container, String containerName) {
        super(MESSAGE, (String) null);
        this.duplicateName = dupName;
        this.container = container;
        this.containerName = containerName;
    }

    public String getMessage() {
        return  MESSAGE + duplicateName + " in " + containerName + " " + container;
    }
       
    public String getContainer() {
        return container;
    }


    public void setContainer(String container) {
        this.container = container;
    }


    public String getContainerName() {
        return containerName;
    }


    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }


    public String getDuplicateName() {
        return duplicateName;
    }


    public void setDuplicateName(String duplicateName) {
        this.duplicateName = duplicateName;
    }
}
