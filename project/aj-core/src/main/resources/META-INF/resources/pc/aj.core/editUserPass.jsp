<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
    <head>
        <title>修改管理员信息</title>
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
         
    <script type="text/javascript">
    
	function check(){
          var newUPwd = document.login.newUPwd.value;
	var reNewUPwd = document.login.reNewUPwd.value;
	var passwordlimit = /^[0-9a-zA-Z\u4E00-\u9FA5&,.!#]+$/;
	var matchreg1 = newUPwd.match(passwordlimit);
	var matchreg2 = reNewUPwd.match(passwordlimit);
	
	if (newUPwd == "") {
		alert("密码不能为空");
		document.login.newUPwd.focus();
		return false;
	}else 
	if (matchreg1 == null) {
		alert("密码不能包含特殊字符");
		document.login.newUPwd.focus();
		return false;
	}else
	if (newUPwd.length < 6) {
		alert("密码不能少于6个字符");
		document.login.newUPwd.focus();
		return false;
	} else {
		
			if (newUPwd != reNewUPwd) {
				alert("两次密码不匹配，请重新输入！");
				document.login.reNewUPwd.focus();
			return false;
			}
			}
		
	}
          </script>

    </head>
    <body>
        <%@include file="sidebar.jsp" %>
        <section id="main" class="column">
            <div class="clear"></div>
            <article class="module width_full">
                <header><h3>信息管理员信息</h3></header>
                 <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
                <form   name="login" action="${pageContext.request.contextPath}/updateUserPass" onSubmit="return check()" method="POST">
                <div class="module_content">
                            <input type="hidden"  name="uId" value='<%=request.getParameter("uId")%>'>
                    <fieldset >
                        <label>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</label>
                       <!--   <s:property value=""/>-->
                       <%if(request.getAttribute("uName")==null){ %>
                        <input type="text" name="uName" style="width:92%;" value='<%=request.getParameter("uName")%>' disabled>
                        <%}else{ %>
                        <input type="text" name="uName" style="width:50%;" value='<%=request.getAttribute("uName")%>' disabled>
                        <%} %>
                    </fieldset>
                    <fieldset >
                        <label>旧&nbsp;&nbsp;密&nbsp;&nbsp;码</label>
                        <input type="password" name="uPwd" style="width:50%;" value='<%=request.getParameter("uPwd")%>'>
                    </fieldset>
                    <fieldset >
                        <label>新&nbsp;&nbsp;密&nbsp;&nbsp;码</label>
                        <input type="password" name="newUPwd" style="width:50%;" >
                    </fieldset>
                     <fieldset >
                        <label>重复新密码</label>
                        <input type="password" name="reNewUPwd" style=width:50%; >
                    </fieldset>
                    <div class="clear"></div>
                </div>
                <footer>
                    <div class="submit_link">

                        <input type="submit" value="修改" class="alt_btn">
                        <input type="submit" value="Reset">
                    </div>
                </footer>
                </form>
            </article><!-- end of post new article -->
        </section>
    </body>
</html>
