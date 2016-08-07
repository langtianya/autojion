
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%

 if(session.getAttribute("login_")==null){
      response.sendRedirect("login");
      return;
}
%>