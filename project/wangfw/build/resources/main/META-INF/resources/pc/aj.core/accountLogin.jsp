
<%@ page language="java" contentType="text/html; charset=GBK"
         pageEncoding="GBK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>前台登录</title>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK" />

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
         <!--[if IE]>
                 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ie.css" type="text/css" media="screen" />
                 <script src="${pageContext.request.contextPath}/js/html5.js"></script>
                 <![endif]-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.0.min.js"></script>

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
        <style type="text/css">
            * { font-family:"Microsoft YaHei",Segoe UI,Tahoma,Arial,Verdana,sans-serif; }
            body { background:#f9f9f9; font-size:14px; }
            #login_title { text-align:center; width:360px; margin:120px auto; margin-bottom:0px; font-size:32px; color:#333;
                           text-shadow: 0 2px 0 #FFFFFF;}
            #login_form { width:360px; margin:0 auto; margin-top:20px; border:solid 1px #e0e0e0; background:#fff;
                          border-radius:3px 3px 3px 3px;}
            #login_form_box { padding:16px; }
            #login_form .label { font-weight:bold; padding-bottom:6px; color:#333; }
            #login_form .textbox input { border:none; padding:0; font-size:24px; width:312px; color:#333; }
            #login_form .textbox { border:1px solid #e0e0e0; padding:6px; margin-bottom:20px; border-radius:3px 3px 3px 3px;
            }
            #login_form .bottom { text-align:right; }
            #login_form .button { padding:8px 16px; font-size:14px; }
        </style>

    </head>
    <element>
    <body>
        <div id="login_title">用户登录</div>
        <div id="login_form" >
            <div id="login_form_box">
                <form id="loginForm" action="${pageContext.request.contextPath}/accountLoginA" method="POST">
                  
                     <%String msg=(String)session.getAttribute("msg");
                       if(msg==null){
                          msg="";
                       }else{
                     %>
                       <div class="textbox" id="msg">
                     <%=msg%>
                     </div>
                      <%}%>
                    
                    <div class="textbox"><input name=aName type="text" placeholder="帐号"/></div>

                    <div class="textbox"><input name="aPwd" type="password" placeholder="密码"/></div>
                    <div class="bottom"><input   type="submit" value="登录" class="button"/>
                </form>
            </div>
        </div>
        

    </body></element>
</html>
