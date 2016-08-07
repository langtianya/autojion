<%@ page language="java" contentType="text/html; charset=GBK"
         pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.lang.String" %>
<!DOCTYPE html>
<html>
	<head>
		<title>响应结果</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link rel="shortcut icon"
			href="${pageContext.request.contextPath}/favicon.ico" />
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/layout.css"
			type="text/css" media="screen" />

		<script
			src="${pageContext.request.contextPath}/js/jquery-1.8.0.min.js"
			type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/hideshow.js"
			type="text/javascript"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery.tablesorter.min.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/jquery.equalHeight.js"></script>

	</head>

	<body>
		<%@include file="sidebar.jsp"%>

		<section id="main" class="column">
		<div class="clear"></div>
		
		<article class="module width_full">
		<header>
		<h3>

			响应结果
		</h3>
		</header>
		</article>
		<s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
		<%
		String msg=(String)request.getAttribute("msg");
		//String msg=(String)session.getAttribute("msg");
                   if(msg!=null){
                   session.removeAttribute("msg");
        %>
                <h4 class="alert_info"><%=msg%></h4>
                <%}%>

		</section>
	</body>
</html>
