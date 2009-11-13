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

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osoa.sca.annotations.ComponentName;
import org.osoa.sca.annotations.Reference;

import sample.ejb3.HelloworldService;

/**
 * A servlet that invokes Helloworld SCA service through injected references.
 * 
 */
public class HelloworldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Reference(name="service")
    protected HelloworldService service;
    
    @ComponentName
    protected String scaComponentName;

    @Override
    public void init(ServletConfig config) {
        // This method assumes that componentContext is injected.
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String greeting0 = service.getGreetings(name);
        
        Writer out = response.getWriter();
        out.write("<html><head><title>Apache Tuscany Helloworld Servlet Sample 11 Client</title></head><body>");
        out.write("<h2>Apache Tuscany Helloworld Servlet Sample 11 Client</h2>");
        out.write("<h3>SCA Service provided by EJB</h3>");
        out.write("<br>Reference injected. <br><strong>Result: </strong>" + greeting0);
        out.write("<hr>");
        
        out.write("<h3>@ComponentName</h3>");
        out.write("<br>Injected into field: "+scaComponentName);
        out.write("<hr>");
        out.write("</body></html>");
        out.flush();
        out.close();
    }
}
