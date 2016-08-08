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
        <title>�޸ĳ�ֵ��¼ҳ��</title>
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
                <header><h3>�޸ĳ�ֵ��¼</h3></header>

                <%String msg = (String) request.getAttribute("msg");
                    if (msg != null) {

                %>

                <p style="color:red"><%=msg%></p>

                <%
                    }

                %>

                <form   action="${pageContext.request.contextPath}/edit" method="POST">
                    <div class="module_content">

                        <fieldset style="width:48%; float:left; margin-right: 3%;">
                            <label>�ͻ�ID</label>
                            <input type="text" name="aId" style="width:92%;" value='<%String aId = (String) request.getParameter("aId");
                                if (aId != null) {
                                   %>
                                   <%=aId%>
                                   <%
                                       }
                                   %>' readonly>

                        </fieldset>
                        <fieldset style="width:48%; float:left;">
                            <label>����</label>


                            <input type="text" name="uName" style="width:92%;" value="<%=request.getParameter("uName")%>" readonly>
                            <input type="hidden" name="uId" value="<%=request.getParameter("uId")%>">
                            <input type="hidden" name="rid" value="<%=request.getParameter("rid")%>">
                        </fieldset>

                        <fieldset style="width:48%; float:left; margin-right: 3%;"> <!-- to make two field float next to one another, adjust values accordingly -->
                            <label>����</label>
                            <select style="width:92%;" name="rClick">

                                <option value="2000" <%String rClick = (String) request.getParameter("rClick");
                                    if (rClick != null && rClick.equals("2000")) {
                                        out.println("selected='selected'");
                                    }%>>2000</option>
                                <option value="3000" <%
                                    if (rClick != null && rClick.equals("3000")) {
                                        out.println("selected='selected'");
                                    }%>>3000</option>
                                <option value="4000" <%
                                    if (rClick != null && rClick.equals("4000")) {
                                        out.println("selected='selected'");
                                    }%>>4000</option>
                                <option value="5000" <%
                                    if (rClick != null && rClick.equals("5000")) {
                                        out.println("selected='selected'");
                                    }%>>5000</option>
                            </select>
                        </fieldset>


                        <fieldset style="width:48%; float:left;"> <!-- to make two field float next to one another, adjust values accordingly -->
                            <label>���</label>
                            <input type="text" style="width:92%;" name="rMoney" value="<%=request.getParameter("rMoney")%>">
                        </fieldset>
                        <fieldset style="width:48%; float:left;">
                            <label>������</label>
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

                                        String uuId = (String) request.getParameter("uuId");
                                       
                                        if (uuId == null || uuId.trim().isEmpty()) {
                                            uuId = (String) request.getParameter("uId");
                                           
                                        }
 
                                        for (User u : users) {
                                            if (uuId!=null&& !uuId.trim().isEmpty() && u.getUId().intValue() == Integer.valueOf(uuId).intValue()) {
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

                            <input type="submit" value="ȷ��" class="alt_btn">
                            <input type="submit" value="����">
                        </div>
                    </footer>
                </form>
            </article><!-- end of post new article -->
        </section>

    </body>

</html>
