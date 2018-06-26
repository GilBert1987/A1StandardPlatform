<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ page import="java.io.*" %>
<html>
<header>
<title>exception page</title>
<body>
<hr/>
<pre>
<%
response.getWriter().println("Exception: " + exception); 

if(exception != null)
{
   response.getWriter().println("<pre>"); 
   exception.printStackTrace(response.getWriter()); 
   response.getWriter().println("</pre>"); 
}

response.getWriter().println("<hr/>"); 
%>