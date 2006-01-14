<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- 
 *  Copyright (c) 2005 The Apache Software Foundation or its licensors, as applicable.
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
 --%>

<HTML>
<HEAD>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" session="true" autoFlush="true"
             isThreadSafe="true" isErrorPage="false"
             import="org.apache.tuscany.samples.customers.CustomerClient"
             import="java.io.PrintWriter"
             import="java.io.StringWriter"
             import="java.util.Iterator"
             import="commonj.sdo.*"
            %>
    <META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <META name="GENERATOR" content="IBM Software Development Platform">
    <TITLE>Tuscany HelloWorld WEB Example</TITLE>
    <%
        String id = request.getParameter("id");
    %>
</HEAD>

<BODY>
<H2>Tuscany CustomerDB WEB Example</H2>

<P>&nbsp;</P>

<form action="Customer.jsp" method="get">ID
    please:&nbsp;&nbsp;&nbsp; <INPUT type="text" id="i1" name="id"
                                     size="40" maxlength="220" value="<%= null == id ? "1" : id %>"> <BR>
    <BR>
    <INPUT type="submit" name="submit" value="Submit"></FORM>
<BR>

<%
    try {
        if (null != id) {
%>
<HR>

<P>

    <% DataObject obj = (new CustomerClient()).getCustomer(id);
	if ( obj == null ) {%>

<p>Customer <%= id%> not found</p>
<%} else {%>


<table id="table1" border=5><tr><TH>ID</TH><TH>First Name</TH><TH>Last Name</TH></TR><TR>
    <%Iterator iter = obj.getType().getProperties().iterator();
        while (iter.hasNext()) {
            Property p = (Property) iter.next();%>
    <td><%= obj.get(p)%></td>
    <%
        }
    %>
</tr></table>
<%
        }
    }
} catch (Exception e) {
    e.printStackTrace();
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    e.printStackTrace(pw);
    pw.flush();

%>
Whoops!<BR clear="all">
<PRE>
    Exception &quot;<%=e.getClass().getName()%>&quot; Exception message: &quot;<%=e.getMessage()%>&quot;<BR clear="all">
    <%=sw.toString() %>
</PRE>
<%

    }%>
</BODY>
</HTML>