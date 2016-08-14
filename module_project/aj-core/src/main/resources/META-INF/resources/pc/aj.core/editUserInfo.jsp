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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/wangzheConnon.js"></script>

<script type="text/javascript">

function check() {
	
var uName = document.getElementById("uName").value;

	var limit = /^[_0-9a-zA-Z\u4E00-\u9FA5]+$/;
	var namelimit = uName.match(limit);
	
	if (uName == "") {
		alert("�û�������Ϊ�գ�����������");
		document.login.uName.focus();
		return false;
	}else
	if (namelimit == null){
		alert("�û���ֻ�������֡�_����ĸ���֣�����������");
		document.login.uName.focus();
		return false;
	}else
	if (uName.length < 4) {
		alert("�û�����������4���ַ�������������");
		document.login.uName.focus();
		return false;
	}
	}
</script>

    </head>
    <body>
        <%@include file="sidebar.jsp" %>
        <section id="main" class="column">
            <div class="clear"></div>
            <article class="module width_full">
            <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
                <header><h3>�޸Ĺ���Ա��Ϣ</h3></header>
                <form   name="login" action="${pageContext.request.contextPath}/updateUserInfo" method="POST" onSubmit="return check()">
                <div class="module_content">
				<input type="hidden" name="userBean.uId"  value="<s:property value="UId" />">
				<input type="hidden" name="uId"  value="<s:property value="UId" />">
				
                    <fieldset>
                        <label>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ţ�</label>
                        <input type="text" id="uName" name="userBean.uName" value="<s:property value="userBean.uName" />">
                    </fieldset>
                    
                    <fieldset>
                        <!-- <label>����</label>
                        <select name="uAgents">
                            <option value="1">�ٷ�</option>
                            <option value="2">A����</option>
                            <option value="3">B����</option>
                        </select> -->
                         <s:select list="auditorList" label="����������"
						name="userBean.uAgents" listKey="key" listValue="value"
						/>
						
                    </fieldset>
                     <br/>
                    <fieldset > 
                        <label>Ȩ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ޣ�</label>
                        <select  name="userBean.uAuth">
                         <option value="1">��������Ա</option>
                                                    <option value="2">��ͨ����Ա</option>
                                                </select>
                    </fieldset>

                        <input type="hidden" style="width:92%;" name="uDate">

                    <div class="clear"></div>
                </div>
                <footer>
                    <div class="submit_linkLeft"  style="clear:both">

                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="�����޸�" class="alt_btn">
                    </div>
                </footer>
                </form>
            </article><!-- end of post new article -->
        </section>
    </body>
</html>
