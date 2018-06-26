<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<html>
<header>
<title>404 page</title>
<body>
<pre>
<%
    Enumeration<String> attributeNames = request.getAttributeNames();
    while (attributeNames.hasMoreElements())
    {
        String attributeName = attributeNames.nextElement();
        Object attribute = request.getAttribute(attributeName);
   out.println("request.attribute['" + attributeName + "'] = " + attribute); 
    }
%>