<%@page language="java" import="com.wangzhe.model.User,com.wangzhe.action.AccountAction,com.wangzhe.action.InterfaceAction" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
  
<header id="header">
		<hgroup>
			<h1 class="site_title"><a href="${pageContext.request.contextPath}/index">后台管理</a></h1>
			<h2 class="section_title">控制面板</h2><div class="btn_view_site"><a href="${pageContext.request.contextPath}/index">首页</a></div>
		</hgroup>
	</header> <!-- end of header bar -->

	<section id="secondary_bar">
		<div class="user">
			<p> <% if(session.getAttribute("login_")!=null){
User user =(User) session.getAttribute("login_");
      String uName=user.getUName();
      out.print(uName);
}
%></p>
			<!-- <a class="logout_user" href="#" title="Logout">退出</a> -->
		</div>
		<div class="breadcrumbs_container">
			<article class="breadcrumbs"><a href="${pageContext.request.contextPath}/index">后台管理</a> <div class="breadcrumb_divider"></div> <a class="current">控制面板</a></article>
		</div>
	</section><!-- end of secondary bar -->

	<aside id="sidebar" class="column">
		<!--  <form class="quick_search">
			<input type="text" value="Quick Search" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
		</form>
		<li class="hightSearch">
<a href="/cloudclick/goToAdSearch">高级搜索</a>
</li>-->
		<hr/>
		<h3>订单管理</h3>
		<ul class="toggle">
			<li class="icn_new_article"><a href="${pageContext.request.contextPath}/addRecord">充值</a></li>
			<li class="icn_edit_article"><a href="${pageContext.request.contextPath}/allRecord">订单管理</a></li>

			<li class="icn_categories"><a href="${pageContext.request.contextPath}/index">订单查询</a></li>
			<!--
			<li class="icn_tags"><a href="#">Tags</a></li>    -->
		</ul>
		<h3>客户管理</h3>
		<ul class="toggle">
			<li class="icn_add_user"><a href="${pageContext.request.contextPath}/register">添加用户</a></li>
			<li class="icn_view_users"><a href="${pageContext.request.contextPath}/goToAdSearch">用户查询</a></li>
			<li class="icn_view_users"><a href="${pageContext.request.contextPath}/recenAccount">最新加入</a></li>
			<!--<li class="icn_profile"><a href="#">删除客户</a></li>-->
		</ul>
	 
		<h3>系统管理</h3>
		<ul class="toggle">
			<li class="icn_settings"><a href="${pageContext.request.contextPath}/allUser">查看管理员</a></li>
			<li class="icn_security"><a href="${pageContext.request.contextPath}/goToAddUser">添加管理员</a></li>
			<li class="icn_jump_back"><a href="${pageContext.request.contextPath}/logout">退出</a></li>
		</ul>
              <h3>信息</h3>
              		<ul class="toggle">
              			<li><%out.println("当前在线用户数:"+AccountAction.onlineAids.size());%></li>
              			<li><%out.println("当前任务池任务数:"+InterfaceAction.getCountByType(""));%></li>
              			<li><%out.println("当前百度任务池任务数:"+InterfaceAction.getCountByType("Baidu"));%></li>
              		</ul>
		<footer>
			<hr/>
			<p><strong>Copyright &copy; 2013 http://www.wangzhesoft.com</strong></p>
			<p>by <a href="http://www.wangzhesoft.com">虫虫软件</a></p>
		</footer>

	</aside><!-- end of sidebar -->