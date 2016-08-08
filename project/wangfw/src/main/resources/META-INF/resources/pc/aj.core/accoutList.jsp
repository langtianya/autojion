<%@ page language="java"  pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<title>	���¼����û��б�</title>
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
		<article class="module width_full">
		<%
		//String msg=(String)request.getAttribute("msg");
		String msg=(String)session.getAttribute("msg");
                   if(msg!=null){
                   session.removeAttribute("msg");
        %>
                <h4 class="alert_info"><%=msg%></h4>
                <%}%>
		<header>
		<h3>
			�û��б�
		</h3>
		</header>
		<form action="${pageContext.request.contextPath}/deleteUser"
			method="POST">
			<div class="module_content">


				<table width="800" id="mytable" cellspacing="0">
					<tr>
						<th>
							�û���
						</th>
						<th>
							����
						</th>
						<th>
							�ֻ���
						</th>
						<th>
							QQ
						</th>
						<th>
							���Ա
						</th>
						<th>
							״̬
						</th>
						<th>
							��ֵ�ܵ���
						</th>
						<th>
							 ʣ�����
						</th>
						<th>
							ע������
						</th>
						<th>
							ע��IP
						</th>
						 <th>
                        							��ֵ
                        						</th>
                        <th>
							�޸�
						</th>
                        <th>
							ɾ��
						</th>
					</tr>
					
					<s:iterator value="accouts">
						<tr>
							<td>
							${aName}
							</td>
							<td>
								${aPwd}
							</td>
							<td>
								${aPhone}
							</td>
							<td>
								${aQq}
							</td>
							<td>
								${uId}
							</td>
							<td>
								${aStatus}
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
                                                        <a href="${pageContext.request.contextPath}/findOneAccount?aName=${aName}&aId=${aId}&uId=${uId}">��ֵ</a>

                            							</td>
							<td>
                            <a href="${pageContext.request.contextPath}/findOneAccount?aId=${aId}">�޸�</a>
								
							</td>
                            <td>
							 <a href="${pageContext.request.contextPath}/deleteAccount?aId=${aId}">ɾ��</a>
							</td>
						</tr>
					</s:iterator>
					
				</table>

				<div class="clear"></div>
			</div>
			<footer>
			<div class="submit_link">

				<input type="submit" value="���" class="alt_btn">
				<input type="submit" value="Reset">
			</div>
			</footer>
		</form>
		</article>
		<!-- end of post new article -->
		</section>
	</body>
</html>
