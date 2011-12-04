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
package helloworld;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.apache.tuscany.sca.core.context.ServiceReferenceImpl;
import org.osoa.sca.ServiceReference;
import org.osoa.sca.annotations.Reference;

/**
 * The HelloWorld service implementation
 */
public class HelloWorldServiceComponent implements HelloWorldService {

    ServiceReference<HelloWorldService> helloWorldService;

    public String getGreetings(String name) {
        System.out.println("Called getGreetings");
        //return helloWorldService.getService().getGreetings(name);
        String xml = null;
        try {
            xml = serializeServiceReferenceXML(helloWorldService);
        } catch (IOException e) {
            e.printStackTrace();
            return "serialization failure";
        }
        System.out.println(xml);
        ServiceReference<HelloWorldService> srCopy = null;
        try {
            srCopy = deserializeServiceReferenceXML(xml);
        } catch (Exception e) {
            e.printStackTrace();
            return "deserialization failure";
        }
        return srCopy.getService().getGreetings(name);
    }

    private String serializeServiceReferenceXML(ServiceReference<HelloWorldService> sr) throws IOException {
        ServiceReferenceImpl<HelloWorldService> sri = (ServiceReferenceImpl<HelloWorldService>) sr;
        return sri.toXMLString();
    }

    private ServiceReference<HelloWorldService> deserializeServiceReferenceXML(String serializedSR) throws Exception {
        StringReader reader = new StringReader(serializedSR);
        XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(reader);
        ServiceReferenceImpl<HelloWorldService> sri
            = new ServiceReferenceImpl<HelloWorldService>(/*HelloWorldService.class, */xmlReader);
        return sri;
    }

    public ServiceReference<HelloWorldService> getHelloWorldService() {
        System.out.println("Got Injected helloWorldService");
        return helloWorldService;
    }

    @Reference
    public void setHelloWorldService(ServiceReference<HelloWorldService> helloWorldService) {
        System.out.println("Injected helloWorldService");        
        this.helloWorldService = helloWorldService;
    }
}