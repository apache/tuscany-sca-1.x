<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>BigBank - <%=request.getParameter("account") %> </TITLE>
</HEAD>
<BODY>
<P>Account <%=  request.getParameter("account") %><BR>
<BR>
<BR>
</P>
<FORM method="post" action="FormServlet">
<input type="hidden" name="action"    value='account' />
<input type="hidden" name="account"    value='<%=  request.getParameter("account") %>' />
<input type="hidden" name="actionType"    value='<%=request.getParameter("transaction")%>' />
Amount to <%=request.getParameter("transaction")%> <INPUT type="text" name="Amount" size="10"
	maxlength="10"><BR>
<BR>
<BR>
<BR>
<INPUT type="submit" name="Submit"
	value="Submit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type="submit"
	name="cancel" value="cancel">
</FORM>
</BODY>
</HTML>
