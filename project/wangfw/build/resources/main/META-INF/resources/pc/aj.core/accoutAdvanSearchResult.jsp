<%@ page language="java"  pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<title>�û��������</title>
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
			�û��������
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
							�û���
						</th>
						<!-- <th>
							����
						</th> -->
						<th>
							�ֻ���
						</th>
						<th>
							QQ
						</th>
						<th>
							������
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
							�޸�
						</th>
                        <th>
							ɾ��
						</th>
						<th>
                        							��ֵ
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
							<s:else>�����̲�����</s:else>  -->
							<s:property value="agentsMap.get(aAgent)"/>
							
								<!-- ${aAgent} ${userBean} ${aStatus}-->
							</td>
							<td>
							<s:if test="aStatus==0">����û�(һ���ؼ���)</s:if>
							<s:elseif test="aStatus==1">�����û�1(һ���ؼ���)</s:elseif>
							<s:elseif test="aStatus==2">�����û�2(�����ؼ���)</s:elseif>
							<s:elseif test="aStatus==3">�����û�3(�����ؼ���)</s:elseif>
							<s:elseif test="aStatus==4">�����û�4(�ĸ��ؼ���)</s:elseif>
							<s:elseif test="aStatus==5">�����û�5(����ؼ���)</s:elseif>
							<s:else>δ֪</s:else>
								
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
                            <a href="${pageContext.request.contextPath}/findOneAccount?aId=${aId}">�޸�</a>
								
							</td>
                            <td>
							 <a href="${pageContext.request.contextPath}/deleteAccount?aId=${aId}">ɾ��</a>
							</td>
							<td>
                                                        <a href="${pageContext.request.contextPath}/addRecord?aName=${aName}&aId=${aId}&uId=${uId}">��ֵ</a>

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
