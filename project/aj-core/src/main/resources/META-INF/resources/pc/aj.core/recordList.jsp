<%@ page language="java"  pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.wangzhe.model.Record" %>
<%@ page import="java.util.List" %>


<%@ page import="com.wangzhe.model.User" %>
<%@ page import="com.wangzhe.model.UserMapper" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="com.wangzhe.db.DB" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.lang.Integer" %>

<%@ page import="com.wangzhe.model.User" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
        <title>��������</title>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <link rel="shortcut icon"
              href="${pageContext.request.contextPath}/favicon.ico" />
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/layout.css"
              type="text/css" media="screen" />
          <!--[if IE]>
                          <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ie.css" type="text/css" media="screen" />
                          <script src="${pageContext.request.contextPath}/js/html5.js"></script>
                          <![endif]-->
        <script
            src="${pageContext.request.contextPath}/js/jquery-1.8.0.min.js"
        type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/hideshow.js"
        type="text/javascript"></script>
        <script
            src="${pageContext.request.contextPath}/js/jquery.tablesorter.min.js"
        type="text/javascript"></script>
        <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery.equalHeight.js"></script>
        <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/common.js"></script>
    </head>

    <body>
        <%@include file="sidebar.jsp"%>

        <section id="main" class="column">
            <%String msg = (String) request.getAttribute("msg");
                if (msg != null) {
            %>
            <h4 class="alert_info"><%=msg%></h4>
            <%}%>
            <%
                //String msg=(String)request.getAttribute("msg");
                msg = (String) session.getAttribute("msg");
                if (msg != null) {
                    session.removeAttribute("msg");
            %>
            <h4 class="alert_info"><%=msg%></h4>
            <%}%>
            <article class="module width_full">
                <header>
                    <h3>
                        ��������
                    </h3>
                </header>

                <div class="module_content">


                    <table width="800" id="mytable" cellspacing="0">
                        <tr>
                            <th>
                                �û���
                            </th>
                            <th>
                                �û�ID
                            </th>
                            <th>
                                ����
                            </th>
                            <th>
                                ���
                            </th>

                            <th>
                                ������
                            </th>
                            <th>
                                ������
                            </th>
                            <th>
                                ������
                            </th>
                            <th>
                                ����
                            </th>
                            <%
                                User user = null;
                                if (session.getAttribute("login_") != null) {
                                    user = (User) session.getAttribute("login_");

                                }
                                if (user != null && user.getUAuth() != null && user.getUAuth() == 1) {
                                    out.println("<th>����</th>");
                                }
                            %>


                        </tr>
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


                            request.setAttribute("agent", User.agentsMap);
                        %>
                        <s:iterator value="#request.records">
                            <tr>
                                <td>
                                    ${aName}
                                </td>
                                <td>
                                    ${aId}
                                </td>
                                <td>
                                    ${rClick}
                                </td>
                                <td>
                                    ${rMoney}
                                </td>
                                <td>
                                    ${uName}
                                </td>
                                <td>
                                    <s:iterator value="#request.agent">

                                        <s:if test="uAgent==key"><s:property value="value"/></s:if>
                                    </s:iterator>
                                </td>
                                <td>
                                  
                                    <s:iterator value="#request.users_">


                                        <s:if test="uuId==uId"><s:property value="uName"/></s:if>
                                       
                                    </s:iterator>
                                     

                                </td>
                                <td>
                                    ${rDate}
                                </td>
                                <% if (user != null && user.getUAuth() != null && user.getUAuth() == 1) {
                                %>
                                <td>
                                    <a href="${pageContext.request.contextPath}/deleteRecord?rid=${rId}">ɾ��</a> |
                                    <a href="${pageContext.request.contextPath}/editRecord?rid=${rId}&aId=${aId}&aName=${aName}&uName=${uName}&uAgent=${uAgent}&uId=${uId}&rDate=${rDate}&rClick=${rClick}&rMoney=${rMoney}&uuId=${uuId}">�༭</a>

                                </td>
                                <%}
                                %>

                            </tr>
                        </s:iterator>

                    </table>

                    <%
                        int maxPage = (Integer) request.getAttribute("maxPage");

                        if (maxPage != 1) {

                    %>

                    <form name="PageForm"  action="${pageContext.request.contextPath}/records" method="post">
                        ÿҳ<%=request.getAttribute("rowsPerPage")%>��
                        ��<%=request.getAttribute("maxRowCount")%>��
                        ��<%=request.getAttribute("curPage")%>ҳ
                        ��<%=request.getAttribute("maxPage")%>ҳ
                        <BR>
                        <%
                            int curPage = (Integer) request.getAttribute("curPage");
                            if (curPage == 1) {
                                out.print(" ��ҳ ��һҳ");
                            } else {%>
                        <A HREF="javascript:gotoPage(1)">��ҳ</A>
                        <A HREF="javascript:gotoPage(<%=curPage - 1%>)">��һҳ</A>
                        <%}%>
                        <%if (curPage == maxPage) {
                                out.print("��һҳ βҳ");
                            } else {%>
                        <A HREF="javascript:gotoPage(<%=curPage + 1%>)">��һҳ</A>
                        <A HREF="javascript:gotoPage(<%=maxPage%>)">βҳ</A>
                        <%}%>
                        ת����<SELECT name="jumpPage" onchange="Jumping()">
                            <% for (int i = 1; i <= maxPage; i++) {
                                    if (i == curPage) {
                            %>
                            <OPTION selected value=<%=i%>><%=i%></OPTION>
                            <%} else {%>
                            <OPTION value=<%=i%>><%=i%></OPTION>
                            <%}
                                }%>
                        </SELECT>ҳ
                    </form>
                    <%}%>

                    <div class="clear"></div>
                </div>


            </article>
            <!-- end of post new article -->
        </section>
    </body>
</html>
