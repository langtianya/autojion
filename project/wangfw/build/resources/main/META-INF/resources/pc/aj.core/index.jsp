<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@include file="check.jsp"%>
<%@ page import="java.lang.String" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="java.util.List" %>
  <%@ page import="com.wangzhe.model.User" %>
  <%@ page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>后台控制面板</title>
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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/kalendae.css" type="text/css" />
                 <script src="${pageContext.request.contextPath}/js/kalendae.js"></script>




        <script type="text/javascript">
            $(document).ready(function() {
                $(".tablesorter").tablesorter();
            });
            $(document).ready(function() {

                //When page loads...
                $(".tab_content").hide(); //Hide all content
                $("ul.tabs li:first").addClass("active").show(); //Activate first tab
                $(".tab_content:first").show(); //Show first tab content

                //On Click Event
                $("ul.tabs li").click(function() {

                    $("ul.tabs li").removeClass("active"); //Remove any "active" class
                    $(this).addClass("active"); //Add "active" class to selected tab
                    $(".tab_content").hide(); //Hide all tab content

                    var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
                    $(activeTab).fadeIn(); //Fade in the active ID content
                    return false;
                });

            });
        </script>
        <script type="text/javascript">
            $(function(){
                $('.column').equalHeight();
            });

        </script>
    </head>
    <body>
        <%@include file="sidebar.jsp" %>
        <!--<iframe src="${pageContext.request.contextPath}/sidebar"   name="sidebar" frameborder="0" height="100%" width="25%"></iframe>-->
    <section id="main" class="column">
         <%String msg=(String)request.getAttribute("msg");
           if(msg!=null){         
%>
        <h4 class="alert_info"><%=msg%></h4>
        <%}%>
        <article class="module width_full">
            <header><h3>订单查询</h3></header>
            <div class="module_content">
           <form method="POST" action="${pageContext.request.contextPath}/allRecord">
                <table  width="90%" cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" bordercolorlight="#008080">

                    <tr>
                        <td width="30%" align="right" height="26">用户名：</td>
                        <td width="70%" height="20"><input type="text" name="aName" value="" size=25></td>
                    </tr>

                    <tr>
                        <td width="30%" align="right" height="22">用户AID：</td>
                        <td width="70%" height="22"><input type="text" name="aId" value="" size=25>
                    </tr>
                    <tr>
                        <td width="30%" align="right" height="25">订单RID：</td>
                        <td width="70%" height="25">
                            <input type="text" value="" name="rId" size=25>&nbsp;
                            
                        </td>

                    </tr>
                    <tr>
                        <td width="30%" align="right" height="25">订单创建日期：</td>
                        <td width="70%" height="25"><input type="text" name="rDate" size="25" value="" id="datepicker">
                        <script type="text/javascript" charset="utf-8">
                        		  new Kalendae.Input('datepicker', {
                                              months:1,mode:'single',
                                              selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
                        		});
                        	</script>
                               到:
                                                    <input type="text" name="tDate" size="25" value="" id="datepicker2">
                                                    <script type="text/javascript" charset="utf-8">
                                                    		  new Kalendae.Input('datepicker2', {
                                                                          months:1,mode:'single',
                                                                          selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
                                                    		});
                                                    	</script>
                                                    	</td>
                    </tr>
                    <tr>
                        <td width="30%" align="right" height="26">操作人：</td>
                        <td width="70%" height="26"><input type="text" name="uName" size="25" value=""></td>
                    </tr>

                    <tr>
                        <td width="30%" align="right" height="20">所属代理商：</td>
                        <td width="70%" height="20"><!--<input type="text" name="uAgents" size="25"/>-->
                                  <select name="uAgents">
                                  <%Map<Integer,String> agentsMaps=User.agentsMap;
                                      for(Integer o:agentsMaps.keySet()){
                                        
                                       out.println("<option value='"+o+"'>"+agentsMaps.get(o)+"</option>");                                       
                                      }
                                  %>
                                  </select>
                         </td>


                    </tr>



                </table>
                <br/>
                       <input type="submit" value="查询" style="background-color: #FF8E8E">
           </form>
            </div>
        </article>
        <div class="spacer"></div>
    </section>



</body>
</html>