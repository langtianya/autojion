package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.action;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.MD5;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.RSA;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.RSAUser;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.Util;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.db.DB;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Account;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.AccountMapper;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Auditor;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Status;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.User;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.UserMapper;
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
 * @InterceptorRefs({@InterceptorRef("permissionStack")}) User: Administrator Date: 13-2-27 Time: ����3:36
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
    // value ��URL ��ַ,results �����ж��
    @Action(value = "/loginCheck", results = {
            @Result(name = "success", location = "/index"),
            @Result(name = "error", location = "/login")})
    public String loginCheck() {
        // Map param= ActionContext.getContext().getParameters();
        String username = ServletActionContext.getRequest()
                .getParameter("user");
        String password = getParnameterByKey("pwd");
        // ServletContext sc = ServletActionContext.getServletContext();
        // RequestDispatcher rd = sc.getRequestDispatcher("/index"); //�����ҳ��
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
//						//�ù�Կ�����û���
//						byteArrUserName = rsa.encrypt(user.getUName().getBytes("ISO-8859-1"));
//						 encryptUserName = new String(byteArrUserName,"UTF-8");
//						System.out.println("RSA���ܺ��û���"+encryptUserName);

                    encryptUserName = MD5.MD5(encryptUserName);
 //                   System.out.println("MD5���ܺ��û���" + encryptUserName);
//						byteArrUserName=encryptUserName.getBytes("ISO-8859-1");
//						byteArrUserName=rsa.decrypt(byteArrUserName);
//						String decryptUserName=new String(byteArrUserName,"ISO-8859-1");
//						System.out.println("���ܺ�"+decryptUserName);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //����û���Ϣcookies
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
                
 //               System.out.println("�ɹ�:" + user.getUId());
                getSession().removeAttribute("msg");
                ServletActionContext.getRequest().removeAttribute("msg");
                return "success";
            }else{
            	getSession().setAttribute("msg",
                "�ʺŻ����������!");
            	 return "error";
            }
        }
        getSession().setAttribute("msg",
                "��¼ʧ��!");
 //       System.out.println("ʧ��");
        return "error";
    }

    /**
     * ��ת����ӹ���Աҳ��
     *
     * @return
     */
    @Action(value = "/goToAddUser", results = {
            @Result(name = "success", location = "/addUser"),
            @Result(name = "error", location = "/result")})
    public String goToAddUser() {
     //   System.out.println("��ת���߼�����");
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
//			auditorList.add(new Auditor(1, "�ٷ�"));
//			auditorList.add(new Auditor(2, "A����"));
//			auditorList.add(new Auditor(3, "b����"));
//		}
    	Integer[] keys = new Integer[User.agentsMap.size()];
    	 User.agentsMap.keySet().toArray(keys);
         for (Integer key : keys) {
        	 auditorList.add(new Auditor(key, User.agentsMap.get(key)));
         }
    	
	}
	
    /**
     * ��������Ա�����ͨ����Ա
     *
     * @return
     */
    @Action(value = "/add", results = {
            @Result(name = "success", location = "/goToAddUser"),
            @Result(name = "error", location = "/goToAddUser")})
    public String add() {
    	
    	if(!Util.isHavePermission()){
    		opMsg="��û��Ȩ����ӹ���Ա";
            return "error";  
    	}
    	try {
    		
            UserMapper userMapper = getModelMapper();
    	String regName = getParnameterByKey("uName");
    	
    	if(userMapper.getUserByName(regName)!=null){
        	opMsg="���û����ѱ�ʹ�ã���������������";
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
        Util.writeDataToClient("<script language='javascript'>alert('��ӳɹ�');window.location.href='/cloudclick/goToAddUser';</script>");
        return "success";
    }
    
	

    /**
     * ��������Աɾ����ͨ����Ա
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
        opMsg="ɾ���ɹ�";
        return "success";
    }

    private User getNewUser() {
        return new User();
    }

    /**
     * �����ݿ��ѯ������ͨ����Ա
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
     * ����δʹ�ã�����ʹ�õ��˸��������޸ģ�֮ǰ��Ϊ�õ�������û�õ��ⷽ��
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
                	opMsg="�����벻��ȷ";
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
                opMsg="�޸�����ɹ�";
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
    		opMsg="�Բ�����������ǳ�������Ա����ֻ���޸��Լ�����Ϣ";
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
  //          	System.out.println("�޸Ĺ���Ա��Ϣ��"+userBean.getUId()+userBean.getUName()+userBean.getUAgents()+userBean.getUAuth());
            	userMapper.updateUserInfo(userBean);
            	 opMsg="�޸Ĺ���Ա��Ϣ�ɹ�";
            	 return "success";
            }
        } finally {
            closeSqlSession();
        }
        opMsg="�޸Ĺ���Ա��Ϣʧ��";
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
	 * ���ݹ���Աid��ѯ�����û���Ϣ
	 * @return
	 */
	@Action(value = "/findOneUser", results = {
			@Result(name = "success", location = "/editUserInfo"),
			@Result(name = "error", location = "/allUser") })
	public String findOneUserById() {
		
	//	System.out.println("���ݹ���Աid���ң�Ȼ����ת���༭ҳ��");
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
	        opMsg="��ȡ����Աʧ��";
	        return "error";
	}

	private void getAuditorListInfo() {
		auditorList = new ArrayList<Auditor>();
	        auditorList.add(new Auditor(-1, "��ѡ��"));
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
  //          System.out.println("��ӳɹ��û�"+i);
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
