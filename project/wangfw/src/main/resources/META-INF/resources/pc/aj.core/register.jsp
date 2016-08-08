<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
   <%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.wangzhe.model.User" %>
<!DOCTYPE html>
<html>
    <head>
        <title>用户注册</title>
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
<<<<<<< .mine
    
    <s:if test="#session.login_!=null">
   <%@include file="sidebar.jsp"%>
   </s:if>
=======


        <%@include file="sidebar.jsp"%>

>>>>>>> .r253
 <!-- <input type="text" name="aName" value="<s:property value="#session.login_" />" style="width:15em;"> -->
        <section id="main" class="column">
            <div class="clear"></div>
            <article class="module width_full">
                <header><h3>用户注册</h3></header>
                
               <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
                
                <form   name="register" id="register" onSubmit="return check()" action="${pageContext.request.contextPath}/addAccountA" method="POST">
                <div class="module_content">
                <% User user=(User)session.getAttribute("login_");
                   if(user!=null && user.getUId()!=null){
                   %>

                 <input type="hidden" name="uId"  value="<%=user.getUId()%>">
                 
                 <!-- 以后根据代理商的不同修改agent文本域的默认值，这里1代表官方 -->
				<input type="hidden" name="agent"  value="1">
                 <%}
                      %>
                   <fieldset>
                        <label>用户名 ：</label><input type="text" name="aName" value="<s:property value="#parameters.aName" />" style="width:15em;">
                    </fieldset>
                    <fieldset >
                        <label>密  码</label>
                        <input type="password" name="aPwd" style="width:15em;">
                    </fieldset>
                    <fieldset >
                        <label>重复密码</label>
                        <input type="password" name="reAPwd" style="width:15em;">
                    </fieldset>
                    <fieldset> 
                        <label>手机号</label>
                        <input type="text" style="width:15em;" name="aPhone" value="<s:property value="#parameters.aPhone" />">
                    </fieldset>
                    <fieldset > 
                        <label>QQ</label>
                        <input type="text" style="width:15em;" name="aQq" value="<s:property value="#parameters.aQq" />">
                    </fieldset>
                     <fieldset > 
                        <label>验证码：</label>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input name="verify" type="text" style="width:4em;">
											&nbsp;<a><span id="verifyImg"><img border=0 src="verifyCode" onClick="javascript:this.src='verifyCode?tm='+Math.random()" ></span></a>
                                            <span id="update" onClick="javascript:verifyImg.src='verifyCode?tm='+Math.random()"></span>
                                            <!-- <a onClick="javascript:this.src='verifyCode?tm='+Math.random()">点击刷新验证码</a>
                                            <button onClick="javascript:this.src='verifyCode?tm='+Math.random()">刷新</button>-->

                                           <!-- <button onclick="return validate('11')">点击刷新</button>-->


							
                    </fieldset>
                  &nbsp;&nbsp;
                    <div class="clear"></div>
                </div>
                <footer>
                    <div class="submit_link">

                        <input type="submit" value="注册" class="alt_btn">
                        <input type="reset" name="reset"  value="重置" />
                    </div>
                </footer>
                </form>

            </article><!-- end of post new article -->
        </section>
        
    </body>
</html>
