<%@ page language="java"  pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<title>管理员列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link rel="shortcut icon"
			href="${pageContext.request.contextPath}/favicon.ico" />
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/layout.css"
			type="text/css" media="screen" />
          <!--[if IE]>
                          <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ie.css" type="text/css" media="screen" />
                          <script src="${pageContext.request.contextPath}/js/html5.js"></script>
                          <![endif]-->
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
		<%
		//String msg=(String)request.getAttribute("msg");
		String msg=(String)session.getAttribute("msg");
                   if(msg!=null){
                   session.removeAttribute("msg");
        %>
                <h4 class="alert_info"><%=msg%></h4>
                <%}%>
                <!--<s:if test="sessionScope.msg!=null"><h4 class="alert_info">${sessionScope.msg}</h4></s:if>-->
		<article class="module width_full">
		<header>
		<h3>
			管理员列表
		</h3>
		</header>
		<s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
		
		<form action="${pageContext.request.contextPath}/deleteUser"
			method="POST">
			<div class="module_content">


				<table width="600" id="mytable" cellspacing="0">
					<tr>
						<th>
							帐号
						</th>
						<th>
							所属
						</th>
						<th>
							权限
						</th>
						<th>
							创建日期
						</th>
                        <th>
							修改密码
						</th>
						<th>
							修改信息
						</th>
                        <th>
							删除
						</th>
						 
					</tr>
					
					<s:iterator value="users" status="index">
					<!--<s:if test="#index.Odd">
					<tr style="background: #CCC">
					</s:if><s:else><td></s:else>-->
					<tr>
							<td>
							${uName}
							</td>
							<td>
							<s:property value="agentsMaps.get(uAgents)"/>
								<!-- ${uAgents} -->
							</td>
							<td>
							<s:if test="uAuth==1">
							超级管理员</s:if>
							<s:elseif test="uAuth==2">普通管理员</s:elseif>
							<s:else>其他</s:else>
							</td>
							<td>
							${uDate}
							</td>
							<td>
                            <a href="${pageContext.request.contextPath}/editUserPass?uId=${uId}&uName=${uName}&uPwd=${uPwd}">修改密码</a>
								
							</td>
							<td>
                            <a href="${pageContext.request.contextPath}/findOneUser?uId=${uId}">修改信息</a>
								
							</td>
                            <td>
							 <a href="${pageContext.request.contextPath}/deleteUser?uId=${uId}">删除</a>
							</td>
						</tr>
					</s:iterator>
					
				</table>
				<div class="clear"></div>
			</div>
			<footer>
			<div class="submit_link">

				<input type="submit" value="添加" class="alt_btn">
				<input type="submit" value="Reset">
			</div>
			</footer>
		</form>
		</article>
		<!-- end of post new article -->
		</section>
		<s:debug></s:debug>
	</body>
</html>
