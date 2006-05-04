/**
 *
 * Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.container.java.integration;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Service;

/**
 * @version $Rev$ $Date$
 */
@Service(GreetingProvider.class)
public class GreetingProviderImpl implements GreetingProvider {

    public String greeting;

    @Property
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting(String name, String locale) {
        if ("fr".equals(locale)) {
            return "Bonjour " + name;
        } else {
            return greeting + name;
        }
    }
}
