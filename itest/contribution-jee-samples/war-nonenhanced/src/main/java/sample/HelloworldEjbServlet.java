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
package sample;

import java.io.IOException;
import java.io.Writer;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osoa.sca.annotations.Reference;

import sample.ejb3.HelloworldService;

/**
 * A servlet that invokes HelloworldService which is an SCA enhanced stateless EJB.
 * 
 * @version $Rev$ $Date$
 */
public class HelloworldEjbServlet extends HttpServlet {
	private static final long serialVersionUID = -4448818871934180718L;

	@EJB
    private HelloworldService service;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String greeting = service.getGreetings(name);
        //String greeting2 = helloworldSca.getGreetings(name.toUpperCase());

        Writer out = response.getWriter();
        out.write("<html><head><title>Apache Tuscany Helloworld Web Sample</title></head><body>");
        out.write("<h2>Apache Tuscany Helloworld Web Sample</h2>");
        out.write("This page displays the response got from HelloworldServiceBean, which is a Stateless EJB.");
        out.write("<br>Parameter sent to HelloworldService.getGreeting: "+name);
        out.write(greeting);
        out.write("The following is got by invoking the HelloworldService SCA service provided by the HelloworldServiceBean");
        out.write("<br>Parameter sent to HelloworldService.getGreeting: "+name.toUpperCase());
        //out.write(greeting2);
        out.write("</body></html>");
        out.flush();
        out.close();
    }
}
