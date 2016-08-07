package com.wangzhe.action;

import com.wangzhe.common.MD5;
import com.wangzhe.common.RSA;
import com.wangzhe.common.RSAUser;
import com.wangzhe.common.Util;
import com.wangzhe.db.DB;
import com.wangzhe.model.Account;
import com.wangzhe.model.AccountMapper;
import com.wangzhe.model.Auditor;
import com.wangzhe.model.Status;
import com.wangzhe.model.User;
import com.wangzhe.model.UserMapper;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @InterceptorRefs({@InterceptorRef("permissionStack")}) User: Administrator Date: 13-2-27 Time: 下午3:36
 */
@Namespace("/")
// @ParentPackage("struts-default")

public class UserAction extends BaseAction {

    private List<User> users;
    // private User user = new User();
    private int uId;
    private User userBean;
    private Map<Integer,String> agentsMaps=User.agentsMap;
    
        private String opMsg;
    private List<Auditor> auditorList = null;

    public String getOpMsg() {
        return opMsg;
    }

    public void setOpMsg(String opMsg) {
        this.opMsg = opMsg;
    }

    public UserAction() {
        log = getLogger(UserAction.class);
    }

    // ,type="redirect"
    // value 是URL 地址,results 可以有多个
    @Action(value = "/loginCheck", results = {
            @Result(name = "success", location = "/index"),
            @Result(name = "error", location = "/login")})
    public String loginCheck() {
        // Map param= ActionContext.getContext().getParameters();
        String username = ServletActionContext.getRequest()
                .getParameter("user");
        String password = getParnameterByKey("pwd");
        // ServletContext sc = ServletActionContext.getServletContext();
        // RequestDispatcher rd = sc.getRequestDispatcher("/index"); //定向的页面
        // try {
        // rd.forward(ServletActionContext.getRequest(),
        // ServletActionContext.getResponse());
        // } catch (ServletException e) {
        // e.printStackTrace();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        if (username != null && password != null) {
            password = MD5.MD5(password);
            User user = getNewUser();
            user.setUName(username);
            user.setUPwd(password);

           SqlSession sqlSession = null;


            try {
                UserMapper userMapper = getModelMapper();
                user = userMapper.checkUser(user);

            } finally {
                closeSqlSession();
            }

            if (user!=null && user.getUId()!=null) {
                ServletActionContext.getRequest().getSession(true).setAttribute(
                        "login_", user);

//					RSA rsa =new RSAUser();


                String encryptUserName = user.getUName() + Math.random();
                try {
//						 byte[] byteArrUserName=null;
//						//用公钥加密用户名
//						byteArrUserName = rsa.encrypt(user.getUName().getBytes("ISO-8859-1"));
//						 encryptUserName = new String(byteArrUserName,"UTF-8");
//						System.out.println("RSA加密后用户名"+encryptUserName);

                    encryptUserName = MD5.MD5(encryptUserName);
 //                   System.out.println("MD5加密后用户名" + encryptUserName);
//						byteArrUserName=encryptUserName.getBytes("ISO-8859-1");
//						byteArrUserName=rsa.decrypt(byteArrUserName);
//						String decryptUserName=new String(byteArrUserName,"ISO-8859-1");
//						System.out.println("解密后"+decryptUserName);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //添加用户信息cookies
//					Cookie[] cookies=ServletActionContext.getRequest().getCookies();
//					for(int j=0;cookies!=null&&j<cookies.length;j++){
//						if(cookies[j].getName().equals("userLogin_n")){
//							break;
//						}
                Cookie cookieName = new Cookie("userLogin_n", encryptUserName);
                cookieName.setMaxAge(7 * 24 * 60 * 60);
                ServletActionContext.getResponse().addCookie(cookieName);
//					}

                ServletActionContext.getRequest().getSession(true).setAttribute(
                        "userLogin_n", encryptUserName);

                ServletActionContext.getRequest().getSession(true).setAttribute(
                        "UAuth", user.getUAuth());
                
 //               System.out.println("成功:" + user.getUId());
                getSession().removeAttribute("msg");
                ServletActionContext.getRequest().removeAttribute("msg");
                return "success";
            }else{
            	getSession().setAttribute("msg",
                "帐号或者密码错误!");
            	 return "error";
            }
        }
        getSession().setAttribute("msg",
                "登录失败!");
 //       System.out.println("失败");
        return "error";
    }

    /**
     * 跳转到添加管理员页面
     *
     * @return
     */
    @Action(value = "/goToAddUser", results = {
            @Result(name = "success", location = "/addUser"),
            @Result(name = "error", location = "/result")})
    public String goToAddUser() {
     //   System.out.println("跳转到高级搜索");
        getAuditorListInfo();
        return "success";
    }

    public void setAuditorCommonData(List<Auditor> auditorList) {
//        List<User> users = new ArrayList();
//        try {
//            UserMapper userMapper = new UserAction().getModelMapper();
//            users = userMapper.getAllUsers();
//        } finally {
//            closeSqlSession();
//        }
//        if (users !=null&&users.size()>0){
//		for(User u:users){
//			auditorList.add(new Auditor(u.getUId(), u.getUName()));
//		}
//		}else{
//			auditorList.add(new Auditor(1, "官方"));
//			auditorList.add(new Auditor(2, "A代理"));
//			auditorList.add(new Auditor(3, "b代理"));
//		}
    	Integer[] keys = new Integer[User.agentsMap.size()];
    	 User.agentsMap.keySet().toArray(keys);
         for (Integer key : keys) {
        	 auditorList.add(new Auditor(key, User.agentsMap.get(key)));
         }
    	
	}
	
    /**
     * 超级管理员添加普通管理员
     *
     * @return
     */
    @Action(value = "/add", results = {
            @Result(name = "success", location = "/goToAddUser"),
            @Result(name = "error", location = "/goToAddUser")})
    public String add() {
    	
    	if(!Util.isHavePermission()){
    		opMsg="您没有权限添加管理员";
            return "error";  
    	}
    	try {
    		
            UserMapper userMapper = getModelMapper();
    	String regName = getParnameterByKey("uName");
    	
    	if(userMapper.getUserByName(regName)!=null){
        	opMsg="该用户名已被使用，请输入其他名称";
        	return "error";
        }
    	
        User user = getNewUser();
       
        String pwd = getParnameterByKey("uPwd");
        if (pwd != null) {
            user.setUPwd(MD5.MD5(pwd));
        } else {
            user.setUPwd("");
        }


        user.setUAgents(Integer.parseInt(getParnameterByKey(
                "uAgents")));
        String uAuth = getParnameterByKey("uAuth");
        
//		if(Integer.valueOf(uAuth)==1){
//        	 user.setUName("admin_"+regName);
//        }else{
//        	 user.setUName(regName);
//        }
        user.setUName(regName);
        user.setUAuth(Integer.valueOf(uAuth));
        String uDate = getParnameterByKey("uDate");
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(uDate);
        } catch (ParseException e) {
            //20130228
            try {
                date = new SimpleDateFormat("yyyyMMdd").parse(uDate);
            } catch (ParseException ex) {
                date = new java.util.Date();
            }

        }
        user.setUDate(new java.sql.Date(date.getTime()));

        
            userMapper.insert(user);

        } finally {
            closeSqlSession();
        }
        Util.writeDataToClient("<script language='javascript'>alert('添加成功');window.location.href='/cloudclick/goToAddUser';</script>");
        return "success";
    }
    
	

    /**
     * 超级管理员删除普通管理员
     *
     * @return
     */
//    @Action(value = "/deleteAdmin", results = {
//            @Result(name = "success", location = "/allUser"),
//            @Result(name = "error", location = "/allUser")})
    public String deleteUser() {

        try {
            UserMapper userMapper = getModelMapper();
            if (userMapper.getUserById(uId + "") != null) {
                userMapper.deleteUserById(uId + "");
            }

        } finally {
            closeSqlSession();
        }
        opMsg="删除成功";
        return "success";
    }

    private User getNewUser() {
        return new User();
    }

    /**
     * 从数据库查询所有普通管理员
     *
     * @return
     */
    @Action(value = "/allUser", results = {
            @Result(name = "success", location = "/userList"),
            @Result(name = "error", location = "/index")})
    public String getAllUsers() {

        try {
            UserMapper userMapper = getModelMapper();
            users = userMapper.getAllUsers();

        } finally {
            closeSqlSession();
        }
        if (users != null && users.size() > 0) {

            return "success";
        } else {
            return "error";
        }
    }

    UserMapper getModelMapper() {
    	return (UserMapper)getModelMapper(UserMapper.class);
    }

    /**
     * 方法未使用，后面使用到了根据需求修改，之前以为用到，后发现没用到这方法
     *
     * @return
     */
    @Action(value = "/getUser", results = {
            @Result(name = "success", location = "/userList"),
            @Result(name = "error", location = "/index")})
    public String findUserById() {

        User user = getNewUser();
        UserMapper userMapper = getModelMapper();
        user = userMapper.getUserById(uId + "");
        return "success";
    }

    @Action(value = "/updateUserPass", results = {
            @Result(name = "success", location = "/allUser"),
            @Result(name = "error", location = "/editUserPass")})
    public String updateUserPass() {

        String uId = getParnameterByKey("uId");
        
        if (uId != null && !uId.trim().isEmpty()) {
            try {
                SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
                sqlSession = sqlSessionFactory.openSession();
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                
                User cc_user = userMapper.getUserById(uId);
                
                String oldUPwd = getParnameterByKey("uPwd");
                if (oldUPwd != null && !oldUPwd.trim().isEmpty() && !oldUPwd.equals(cc_user.getUPwd())) {
                	  this.getRequest().setAttribute("uName", cc_user.getUName());
                	opMsg="旧密码不正确";
                	 return "error";
                }
//                String uName = getParnameterByKey("uName");
//                if (uName != null && !uName.trim().isEmpty() && !uName.equals(cc_user.getUName())) {
//                    cc_user.setUName(uName);
//                }
                
                String newUPwd = getParnameterByKey("newUPwd");
                if (newUPwd != null && !newUPwd.trim().isEmpty()) {
                    String md5_upwd = MD5.MD5(newUPwd);
                    if (!md5_upwd.equals(cc_user.getUPwd())) {

                        cc_user.setUPwd(md5_upwd);
                    }
                }
                //String uAuth=  getParnameterByKey("uAuth");
                //uAgents

                userMapper.updateUserPass(cc_user);
                opMsg="修改密码成功";
                return "success";
            } finally {
                closeSqlSession();
            }
        }

        return "error";
    }
    @Action(value = "/updateUserInfo", results = {
            @Result(name = "success", location = "/allUser"),
            @Result(name = "error", location = "/findOneUser")})
    public String updateUserInfo() {
    	
    	User user =getSessionUser();
    	if(!Util.isHavePermission()&&user.getUId()!=uId){
    		opMsg="对不起，如果您不是超级管理员，您只能修改自己的信息";
    		if(user!=null)
    		return "error";
    	}
    	
    	try {
            UserMapper userMapper = getModelMapper();
            if (userMapper.getUserById(uId + "") != null) {
//            	if(userBean.getUAuth()==1){
//            		userBean.setUName("admin_"+userBean.getUName());
//            	}else if(userBean.getUName().startsWith("admin_")){
//                		userBean.setUName(userBean.getUName().replaceFirst("admin_", ""));
//            	}
  //          	System.out.println("修改管理员信息："+userBean.getUId()+userBean.getUName()+userBean.getUAgents()+userBean.getUAuth());
            	userMapper.updateUserInfo(userBean);
            	 opMsg="修改管理员信息成功";
            	 return "success";
            }
        } finally {
            closeSqlSession();
        }
        opMsg="修改管理员信息失败";
        return "error";
    }

	

	private User getSessionUser() {
		return (User)getSession().getAttribute("login_");
	}

	

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
	 * 根据管理员id查询单个用户信息
	 * @return
	 */
	@Action(value = "/findOneUser", results = {
			@Result(name = "success", location = "/editUserInfo"),
			@Result(name = "error", location = "/allUser") })
	public String findOneUserById() {
		
	//	System.out.println("根据管理员id查找，然后跳转到编辑页面");
		 getAuditorListInfo();
		
		 try {
	            UserMapper userMapper = getModelMapper();
	            userBean=userMapper.getUserById(uId + "");
	            if (userBean != null) {
	            	 return "success";
	            }
	        } finally {
	            closeSqlSession();
	        }
	        opMsg="获取管理员失败";
	        return "error";
	}

	private void getAuditorListInfo() {
		auditorList = new ArrayList<Auditor>();
	        auditorList.add(new Auditor(-1, "请选择"));
	        setAuditorCommonData(auditorList);
	}
	
	
    public static void main(String[] args) {
        UserAction ua = new UserAction();
        UserMapper accoutMapper = ua.getModelMapper();
//        if (ua.getAllUsers() != null) {
//            System.out.print(ua.getAllUsers());
//            for (int i = 0; i < ua.users.size(); i++) {
//                System.out.println(ua.users.get(i));
//            }
//        }
        
        for(int i=0;i<500;i++){      	
        	User user = ua.getNewUser();
        	String randString9=Util.getRandomStr(9);
        	 String randInt = ""+Util.getRandomInt(99999999,1000000000);
        	 
            user.setUName(randString9);
            
            String pwd = randString9;
            if (pwd != null) {
                user.setUPwd(MD5.MD5(pwd));
            } else {
                user.setUPwd("");
            }


            int randomInt4 = Util.getRandomInt(1,4);
			user.setUAgents(randomInt4);
			
            user.setUAuth(randomInt4);
            
            String uDate = new java.util.Date().getTime()+"";
            java.util.Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(uDate);
            } catch (ParseException e) {
                //20130228
                try {
                    date = new SimpleDateFormat("yyyyMMdd").parse(uDate);
                } catch (ParseException ex) {
                    date = new java.util.Date();
                }

            }
            user.setUDate(new java.sql.Date(date.getTime()));
            accoutMapper.insert(user);
  //          System.out.println("添加成功用户"+i);
            }
    }

    // public User getUser() {
    // return user;
    // }
    // public void setUser(User user) {
    // this.user = user;
    // }
    public int getUId() {
        return uId;
    }

    public void setUId(int id) {
        uId = id;
    }
//    public void opNotAllow(){
//    	
//    }
	public List<Auditor> getAuditorList() {
		return auditorList;
	}
	public void setAuditorList(List<Auditor> auditorList) {
		this.auditorList = auditorList;
	}



	public User getUserBean() {
		return userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

	public Map<Integer, String> getAgentsMaps() {
		return agentsMaps;
	}

	public void setAgentsMaps(Map<Integer, String> agentsMaps) {
		this.agentsMaps = agentsMaps;
	}

}
