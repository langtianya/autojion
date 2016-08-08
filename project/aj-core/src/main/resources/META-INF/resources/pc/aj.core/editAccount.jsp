<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>修改用户信息</title>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" type="text/css" media="screen" />
          <!--[if IE]>
                          <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ie.css" type="text/css" media="screen" />
                          <script src="${pageContext.request.contextPath}/js/html5.js"></script>
                          <![endif]-->
        <script src="${pageContext.request.contextPath}/js/jquery-1.8.0.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/hideshow.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.tablesorter.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.equalHeight.js"></script>
  <script src="${pageContext.request.contextPath}/js/register.js" type="text/javascript"></script>

    </head>
    <body>
   <%@include file="sidebar.jsp"%>
        <section id="main" class="column">
            <div class="clear"></div>
            <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
            <article class="module width_full">
                <header><h3>修改用户信息</h3></header>
                <span style="color:red; padding-left:300px;" >${opMsg}</span>
                
                <form   name="updateAccount" id="updateAccount"  action="${pageContext.request.contextPath}/updateAccount" method="POST">
                <div class="module_content">
                 <input type="hidden" name="account.aId"  value="<s:property value="AId" />">
                 <input type="hidden" name="aId"  value="<s:property value="AId" />">

                    <fieldset>
                        <label>用户名：</label><input type="text" name="account.aName" value="<s:property value="account.aName" />" style="width:15em;">
                    </fieldset>
                     <fieldset > 
                        <label>手机号</label>
                        <input type="text" style="width:15em;" value="<s:property value="account.aPhone" />" name="account.aPhone">
                    </fieldset>
                    <fieldset > 
                        <label>QQ</label>
                        <input type="text" style="width:15em;" name="account.aQq"  value="<s:property value="account.aQq" />">
                    </fieldset>
                     <fieldset>
	                    <s:select list="auditorList" label="所属代理商"
						name="account.aAgent" listKey="key" listValue="value"
						/>
                    </fieldset>
                    <fieldset>
                     <s:select list="statusList" label="状态"
						name="account.aStatus" listKey="key" listValue="value"
						 />
                    </fieldset>
                    
                    <div class="clear"></div>
                </div>
                <footer>
                    <div class="submit_link">

                        <input type="submit" value="保存" class="alt_btn">
                        <input type="reset" name="reset"  value="重置" />
                    </div>
                </footer>
                </form>

            </article><!-- end of post new article -->
        </section>
        
    </body>
</html>
