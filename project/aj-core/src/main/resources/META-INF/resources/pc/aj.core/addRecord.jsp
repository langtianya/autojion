<%@page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.List" %>
<%@ page import="com.wangzhe.model.User" %>
<%@ page import="com.wangzhe.model.UserMapper" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="com.wangzhe.db.DB" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>充值页面</title>
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
                <header><h3>充值</h3></header>

                <%String msg = (String) request.getAttribute("msg");
                    if (msg != null) {

                %>

                <p style="color:red"><%=msg%></p>

                <%
                    }

                %>

                <form   action="${pageContext.request.contextPath}/recharge" method="POST">
                    <div class="module_content">

                        <fieldset style="width:48%; float:left; margin-right: 3%;">
                            <label>客户ID</label>
                            <input type="text" name="aId" style="width:92%;" value='<%String aId = (String) request.getParameter("aId");
                                if (aId != null) {
                                   %>
                                   <%=aId%>
                                   <%
                                       }
                                   %>'>

                        </fieldset>
                        <fieldset style="width:48%; float:left;">
                            <label>所属</label>
                            <%
                                User user = null;
                                if (session.getAttribute("login_") != null) {
                                    user = (User) session.getAttribute("login_");

                                }

                            %>

                            <input type="text" name="uName" style="width:92%;" value="<%if (user != null && user.getUName() != null) {%><%=user.getUName()%><%}%>" readonly>
                            <input type="hidden" name="uId" value="<%if (user != null && user.getUId() != null) {%><%=user.getUId()%><%}%>">

                        </fieldset>

                        <fieldset style="width:48%; float:left; margin-right: 3%;"> <!-- to make two field float next to one another, adjust values accordingly -->
                            <label>点数</label>
                            <select style="width:92%;" name="rClick">

                                <option value="2000">2000</option>

                                <option value="5000">5000</option>
                                 <option value="7000">7000</option>
                                 <option value="9000">9000</option>

                            </select>
                        </fieldset>


                        <fieldset style="width:48%; float:left;"> <!-- to make two field float next to one another, adjust values accordingly -->
                            <label>金额</label>
                            <input type="text" style="width:92%;" name="rMoney">
                        </fieldset>
                        <fieldset style="width:48%; float:left;">
                            <label>代办人</label>
                            <%
                                List<User> users = (List<User>) request.getAttribute("users_");
                                if (users == null) {
                                    SqlSession sqlSession = null;
                                    try {
                                        SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
                                        sqlSession = sqlSessionFactory.openSession();
                                        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                                        users = userMapper.getAllUsers();

                                    } finally {
                                        if (sqlSession != null) {
                                            sqlSession.close();
                                        }
                                    }
                                    request.setAttribute("users_", users);
                                }
                            %>
                            <select style="width:92%;" name="uuId">



                                <%
                                    if (users != null && users.size() > 0) {
                                        for (User u : users) {
                                            if (user != null && user.getUId() != null && user.getUId().intValue() == u.getUId().intValue()) {
 
                                                out.println("<option value='" + u.getUId() + "' selected='selected'>" + u.getUName() + "</option>");
                                            } else {
                                                out.println("<option value='" + u.getUId() + "'>" + u.getUName() + "</option>");
                                            }
                                        }

                                    }%>


                            </select>
                        </fieldset>
                        <div class="clear"></div>
                    </div>
                    <footer>
                        <div class="submit_link">

                            <input type="submit" value="确定" class="alt_btn">
                            <input type="submit" value="重置">
                        </div>
                    </footer>
                </form>
            </article><!-- end of post new article -->
        </section>

    </body>

</html>
