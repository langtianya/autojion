<%@ page language="java"  pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<title>用户搜索结果</title>
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
		<article class="module width_full">
		<header>
		<h3>
			用户搜索结果
		</h3>
		</header>
		<s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
		<form action="${pageContext.request.contextPath}/deleteUser"
			method="POST">
			<div class="module_content">
<input type="hidden" name="accouts"  value="<s:property value="accouts" />">

				<table width="800" id="mytable" cellspacing="0">
					<tr>
						<th>
							用户名
						</th>
						<!-- <th>
							密码
						</th> -->
						<th>
							手机号
						</th>
						<th>
							QQ
						</th>
						<th>
							代理商
						</th>
						<th>
							状态
						</th>
						<th>
							充值总点数
						</th>
						<th>
							 剩余点数
						</th>
						<th>
							注册日期
						</th>
						<th>
							注册IP
						</th>
                        <th>
							修改
						</th>
                        <th>
							删除
						</th>
						<th>
                        							充值
                        						</th>
                      
					</tr>
					
					<s:iterator value="adSearchResults">
						<tr>
							<td>
							${aName}
							</td>
							<!-- <td>
								${aPwd}
							</td> -->
							<td>
								${aPhone}
							</td>
							<td>
								${aQq}
							</td>
							<td>
							<!--<s:if test="userBean!=null">
							<s:property value="userBean.uName"/></s:if>
							<s:else>代理商不存在</s:else>  -->
							<s:property value="agentsMap.get(aAgent)"/>
							
								<!-- ${aAgent} ${userBean} ${aStatus}-->
							</td>
							<td>
							<s:if test="aStatus==0">免费用户(一个关键词)</s:if>
							<s:elseif test="aStatus==1">付费用户1(一个关键词)</s:elseif>
							<s:elseif test="aStatus==2">付费用户2(两个关键词)</s:elseif>
							<s:elseif test="aStatus==3">付费用户3(三个关键词)</s:elseif>
							<s:elseif test="aStatus==4">付费用户4(四个关键词)</s:elseif>
							<s:elseif test="aStatus==5">付费用户5(五个关键词)</s:elseif>
							<s:else>未知</s:else>
								
							</td>
							<td>
								${aTotal}
							</td>
							<td>
								${aLast}
							</td>
							<td>
								${aDate}
							</td>
							<td>
								${aIp}
							</td>
							<td>
                            <a href="${pageContext.request.contextPath}/findOneAccount?aId=${aId}">修改</a>
								
							</td>
                            <td>
							 <a href="${pageContext.request.contextPath}/deleteAccount?aId=${aId}">删除</a>
							</td>
							<td>
                                                        <a href="${pageContext.request.contextPath}/addRecord?aName=${aName}&aId=${aId}&uId=${uId}">充值</a>

                            							</td>
						</tr>
					</s:iterator>
					
				</table>

				<div class="clear"></div>
			</div>
			<footer>
			<div class="submit_link">

			</div>
			</footer>
		</form>
		</article>
		<!-- end of post new article -->
		</section>
	</body>
</html>
