<%@page language="java" import="com.wangzhe.model.User,com.wangzhe.action.AccountAction,com.wangzhe.action.InterfaceAction" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
  
<header id="header">
		<hgroup>
			<h1 class="site_title"><a href="${pageContext.request.contextPath}/index">��̨����</a></h1>
			<h2 class="section_title">�������</h2><div class="btn_view_site"><a href="${pageContext.request.contextPath}/index">��ҳ</a></div>
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
			<!-- <a class="logout_user" href="#" title="Logout">�˳�</a> -->
		</div>
		<div class="breadcrumbs_container">
			<article class="breadcrumbs"><a href="${pageContext.request.contextPath}/index">��̨����</a> <div class="breadcrumb_divider"></div> <a class="current">�������</a></article>
		</div>
	</section><!-- end of secondary bar -->

	<aside id="sidebar" class="column">
		<!--  <form class="quick_search">
			<input type="text" value="Quick Search" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
		</form>
		<li class="hightSearch">
<a href="/cloudclick/goToAdSearch">�߼�����</a>
</li>-->
		<hr/>
		<h3>��������</h3>
		<ul class="toggle">
			<li class="icn_new_article"><a href="${pageContext.request.contextPath}/addRecord">��ֵ</a></li>
			<li class="icn_edit_article"><a href="${pageContext.request.contextPath}/allRecord">��������</a></li>

			<li class="icn_categories"><a href="${pageContext.request.contextPath}/index">������ѯ</a></li>
			<!--
			<li class="icn_tags"><a href="#">Tags</a></li>    -->
		</ul>
		<h3>�ͻ�����</h3>
		<ul class="toggle">
			<li class="icn_add_user"><a href="${pageContext.request.contextPath}/register">����û�</a></li>
			<li class="icn_view_users"><a href="${pageContext.request.contextPath}/goToAdSearch">�û���ѯ</a></li>
			<li class="icn_view_users"><a href="${pageContext.request.contextPath}/recenAccount">���¼���</a></li>
			<!--<li class="icn_profile"><a href="#">ɾ���ͻ�</a></li>-->
		</ul>
	 
		<h3>ϵͳ����</h3>
		<ul class="toggle">
			<li class="icn_settings"><a href="${pageContext.request.contextPath}/allUser">�鿴����Ա</a></li>
			<li class="icn_security"><a href="${pageContext.request.contextPath}/goToAddUser">��ӹ���Ա</a></li>
			<li class="icn_jump_back"><a href="${pageContext.request.contextPath}/logout">�˳�</a></li>
		</ul>
              <h3>��Ϣ</h3>
              		<ul class="toggle">
              			<li><%out.println("��ǰ�����û���:"+AccountAction.onlineAids.size());%></li>
              			<li><%out.println("��ǰ�����������:"+InterfaceAction.getCountByType(""));%></li>
              			<li><%out.println("��ǰ�ٶ������������:"+InterfaceAction.getCountByType("Baidu"));%></li>
              		</ul>
		<footer>
			<hr/>
			<p><strong>Copyright &copy; 2013 http://www.wangzhesoft.com</strong></p>
			<p>by <a href="http://www.wangzhesoft.com">������</a></p>
		</footer>

	</aside><!-- end of sidebar -->