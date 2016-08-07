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
    <body style=" background-color:#d4f0fc">
    <div id="top" style="background:url(${pageContext.request.contextPath}/images/top_b.jpg)"><img src="${pageContext.request.contextPath}/images/top_i.jpg" width="1033" height="134"></div>
<section id="main"  class="column" style=" margin:100px 40px; width:100%>
            <div class="clear"></div>
            <article class="module width_full" >
                <header><h3>用户注册</h3></header>
                
               <s:if test="opMsg!=null"><h4 class="alert_info">${opMsg}</h4></s:if>
                
                <form   name="register" id="register" onSubmit="return check()" action="${pageContext.request.contextPath}/addAccountA" method="POST">
                <div class="module_content">
                <% User user=(User)session.getAttribute("login_");
                   if(user!=null && user.getUId()!=null){
                   %>
                 <input type="hidden" name="uId"  value="<%=user.getUId()%>">
                 <%}%>
                  <input type="hidden" name="regType"  value="registerOut">
                 
                 <!-- 以后根据代理商的不同修改agent文本域的默认值，这里1代表官方 -->
				<input type="hidden" name="agent"  value="1">
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
                <footer style=" text-align:left">
                
                    <div class="submit_link" style="width:700px;margin-left: 240px;padding: 5px 0;float:left" >
                        <input type="submit" value="注册" style="width:15em" class="alt_btn" >
                        <input type="reset" name="reset"  style="width:15em" value="重置" />                
                    </div>
                </footer>
                </form>

</article><!-- end of post new article -->
        </section>
        
        <div id="foot" style="overflow:hidden; width:100%;margin-top:40px;background-color: #F9F9F9;text-align:top; color:#6b6b6b; background:url(${pageContext.request.contextPath}/images/foot_b.jpg)"><div style="float:left"><img src="${pageContext.request.contextPath}/images/foot_i.jpg" width="89" height="119"></div><br>
        <div style="float:left; margin-left:20px;line-height:22px; color:#e7f7fe"><span style="font-size:14px; font-weight:bold; line-height:24px; letter-spacing:5px">深圳市辉创软件技术有限公司 <br></span>联系地址：深圳市南山区南海大道花样年美年广场3栋1205
          <br>
          电话：0755-86617335 86617336 传真:0755-86617332 <br>
    Copyright@2007 - 2012 深圳市辉创软件技术有限公司 All Rights Reserved </div></div>
    <s:debug/>
</body>
</html>
