<%@page language="java" contentType="text/html" pageEncoding="GBK"%>
<%  session.removeAttribute("login_");
    response.sendRedirect("login");
%>
