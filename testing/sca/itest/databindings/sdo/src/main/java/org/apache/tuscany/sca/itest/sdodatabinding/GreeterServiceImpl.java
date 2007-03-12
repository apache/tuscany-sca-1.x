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

package org.apache.tuscany.sca.itest.sdodatabinding;

import org.apache.tuscany.sca.itest.databinding.types.PersonType;
import org.apache.tuscany.spi.annotation.Autowire;
import org.osoa.sca.annotations.Service;

import commonj.sdo.DataObject;
import commonj.sdo.helper.HelperContext;

/**
 * 
 */
@Service(GreeterService.class)
public class GreeterServiceImpl implements GreeterService {
    /**
     * It will be injected with a HelperContext for the composite
     */
    @Autowire
    protected HelperContext helperContext;
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.tuscany.sca.itest.sdodatabinding.GreeterService#greet(org.apache.tuscany.sca.itest.sdodatabinding.generated.PersonType)
     */
    public PersonType greet(PersonType who) {
        System.out.println(helperContext.getXMLHelper().save((DataObject) who, "http://person/", "person"));
        PersonType copy = (PersonType) helperContext.getCopyHelper().copy((DataObject) who);
        copy.setGreeting("Hello");
        System.out.println(who.toString());
        return copy;
    }

}
