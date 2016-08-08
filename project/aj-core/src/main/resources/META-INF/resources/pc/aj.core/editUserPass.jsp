<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
    <head>
        <title>�޸Ĺ���Ա��Ϣ</title>
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
		alert("���벻��Ϊ��");
		document.login.newUPwd.focus();
		return false;
	}else 
	if (matchreg1 == null) {
		alert("���벻�ܰ��������ַ�");
		document.login.newUPwd.focus();
		return false;
	}else
	if (newUPwd.length < 6) {
		alert("���벻������6���ַ�");
		document.login.newUPwd.focus();
		return false;
	} else {
		
			if (newUPwd != reNewUPwd) {
				alert("�������벻ƥ�䣬���������룡");
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
                <header><h3>��Ϣ����Ա��Ϣ</h3></header>
                 <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
                <form   name="login" action="${pageContext.request.contextPath}/updateUserPass" onSubmit="return check()" method="POST">
                <div class="module_content">
                            <input type="hidden"  name="uId" value='<%=request.getParameter("uId")%>'>
                    <fieldset >
                        <label>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</label>
                       <!--   <s:property value=""/>-->
                       <%if(request.getAttribute("uName")==null){ %>
                        <input type="text" name="uName" style="width:92%;" value='<%=request.getParameter("uName")%>' disabled>
                        <%}else{ %>
                        <input type="text" name="uName" style="width:50%;" value='<%=request.getAttribute("uName")%>' disabled>
                        <%} %>
                    </fieldset>
                    <fieldset >
                        <label>��&nbsp;&nbsp;��&nbsp;&nbsp;��</label>
                        <input type="password" name="uPwd" style="width:50%;" value='<%=request.getParameter("uPwd")%>'>
                    </fieldset>
                    <fieldset >
                        <label>��&nbsp;&nbsp;��&nbsp;&nbsp;��</label>
                        <input type="password" name="newUPwd" style="width:50%;" >
                    </fieldset>
                     <fieldset >
                        <label>�ظ�������</label>
                        <input type="password" name="reNewUPwd" style=width:50%; >
                    </fieldset>
                    <div class="clear"></div>
                </div>
                <footer>
                    <div class="submit_link">

                        <input type="submit" value="�޸�" class="alt_btn">
                        <input type="submit" value="Reset">
                    </div>
                </footer>
                </form>
            </article><!-- end of post new article -->
        </section>
    </body>
</html>
