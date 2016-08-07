<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html>
<html>
    <head>
        <title>添加管理员</title>
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
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/wangzheConnon.js"></script>
	
    </head>
    <body>
        <%@include file="sidebar.jsp" %>
        <section id="main" class="column">
            <div class="clear"></div>
            <article class="module width_full">
            <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
                <header><h3>添加管理员账号</h3></header>
                <form   name="login" action="${pageContext.request.contextPath}/add" method="POST" onSubmit="return check()">
                <div class="module_content">

                    <fieldset>
                        <label>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
                        <input type="text" name="uName" >
                    </fieldset>
                    <br/>
                    <fieldset>
                        <label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                        <input type="password" name="uPwd" >
                    </fieldset>
                     <fieldset>
                        <label>重复密码：</label>
                        <input type="password" name="reuPwd" >
                    </fieldset>

                    <fieldset>
                        <!-- <label>所属</label>
                        <select name="uAgents">
                            <option value="1">官方</option>
                            <option value="2">A代理</option>
                            <option value="3">B代理</option>
                        </select> -->
                         <s:select list="auditorList" label="所属代理商"
						name="uAgents" listKey="key" listValue="value"
						/>
                    </fieldset>
                     <br/>
                    <fieldset > 
                        <label>权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限：</label>
                        <select  name="uAuth">
                         <option value="1">超级管理员</option>
                                                    <option value="2">普通管理员</option>
                                                </select>
                    </fieldset>

                        <input type="hidden" style="width:92%;" name="uDate">

                    <div class="clear"></div>
                </div>
                <footer>
                    <div class="submit_link">

                        <input type="submit" value="添加" class="alt_btn">
                        <input type="submit" value="Reset">
                    </div>
                </footer>
                </form>
            </article><!-- end of post new article -->
        </section>
    </body>
</html>
