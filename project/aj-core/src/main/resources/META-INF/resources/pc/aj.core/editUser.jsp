<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html>
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

    </head>
    <body>
        <%@include file="sidebar.jsp" %>
        <section id="main" class="column">
            <div class="clear"></div>
            <article class="module width_full">
                <header><h3>��Ϣ����Ա��Ϣ</h3></header>
                <form   action="${pageContext.request.contextPath}/updateUser" method="POST">
                <div class="module_content">
                            <input type="hidden"  name="uId" value='<%=request.getParameter("uId")%>'>
                    <fieldset style="width:48%; float:left; margin-right: 3%;">
                        <label>�˺�</label>
                        <input type="text" name="uName" style="width:92%;" value='<%=request.getParameter("uName")%>'>
                    </fieldset>
                    <fieldset style="width:48%; float:left;">
                        <label>����</label>
                        <input type="password" name="uPwd" style="width:92%;" value='<%=request.getParameter("uPwd")%>'>
                    </fieldset>
                    <!--
                    <fieldset style="width:48%; float:left; margin-right: 3%;">
                        <label>����</label>
                        <select style="width:92%;" name="uAgents">
                            <option value="1">�ٷ�</option>
                            <option value="2">A����</option>
                            <option value="3">B����</option>
                        </select>
                    </fieldset>
                    <fieldset style="width:48%; float:left;">
                        <label>Ȩ��</label>
                        <select style="width:92%;" name="uAuth">
                         <option value="1">��������Ա</option>
                                                    <option value="2">��ͨ����Ա</option>
                                                </select>
                    </fieldset>

                        <input type="hidden" style="width:92%;" name="uDate">
                           -->
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
