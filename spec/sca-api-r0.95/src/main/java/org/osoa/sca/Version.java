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
package org.osoa.sca;

/**
 * Class providing information on the version of the specification
 * supported by these API classes.
 *
 * @version $Rev$ $Date$
 */
public final class Version {
    /**
     * Identifier for version 1.0
     */
    public static final String VERSION_1_0 = "1.0";

    /**
     * Identifier for the XML Namespace for version 1.0
     */
    public static final String SCA_NS = "http://www.osoa.org/xmlns/sca/1.0";

    /**
     * The specification version of these API classes.
     */
    public static final String API_VERSION = VERSION_1_0;

    private Version() {
    }
}
