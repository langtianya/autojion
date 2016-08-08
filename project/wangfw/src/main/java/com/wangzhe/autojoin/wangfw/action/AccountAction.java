package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.action;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.IpUtil;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.MD5;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.RSA;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.RSAUser;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.Util;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.db.DB;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Account;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.AccountMapper;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Account;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.AccountMapper;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Auditor;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Status;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Task;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.User;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.UserMapper;

import com.opensymphony.xwork2.ActionSupport;
/**
 * Account: Administrator
 * Date: 13-2-28
 * Time: ����5:39
 */
@Namespace("/")
public class AccountAction extends BaseAction {
	 //��ǰ����������Host
	 public static String localHost="";
	
	 //�����û���  �û�ID �û��ѷ������
	 public static  Hashtable<Integer,Integer>  onlineAids=new Hashtable<Integer,Integer>();
	 
	 //��������û���
	 public static  ArrayList<Integer>  checkonlineAids=new ArrayList<Integer>();
	 
	 //�����������û���
	 public static  ArrayList<Integer>  checkoutAids=new ArrayList<Integer>();
	 
	 //�����û�Ip��
	 public static Hashtable<String,Integer> ipPools=new Hashtable<String,Integer>();
	 private List<Account> accouts;
	private int aId;
	private String opMsg;
    private Account account;
    private List<Account> adSearchResults;
    private Map<Integer,String> agentsMap=User.agentsMap;
    
    
		//�޸��û���Ϣҳ�������Ա��select list
//    private Auditor[] auditorList = {new Auditor(1, "�ٷ�"),new Auditor(2, "A����"), new Auditor(3, "B����")};
    private List<Auditor> auditorList = null;
    private List<Status> statusList = null;	
    
	public AccountAction(){
		log = getLogger(AccountAction.class);
	}
	//������¼
	public static final String[][] updaterecords=new String[][]
	{ 
	  {"1", "false", "2013-05-02", "�����һ����ʽ�汾������"},
	  {"2", "true", "2013-05-30", "�޸��������ѯ�˺���Ϣ�����������������Ƶ����⡣"},
	  {"3", "true", "2013-05-31", "�޸�������ظ������������⡣"},
	  {"4", "true", "2013-06-01", "�޸���������첻�����������ȡʧЧ�ճɵ����⡣"},
	  {"5", "true", "2013-06-03", "�޸���Ĭ�ϵĵ�����ʾ��ʽ���޸��˲�����ַ���ʧ�ܺͻ�ȡIP����ʧ�ܵ�����."},
	  {"6", "true", "2013-06-04", "�޸�������������ӳ�ʱ������."},
	  {"7", "true", "2013-06-05", "�޸��˲����û�����Զ�ֹͣ�����⣬�ӳ����Զ���������ͼ������״̬ʱ��."},
	  {"8", "true", "2013-06-07", "�����˱��÷�������֧���˹һ��������Զ���ʾ."},
	  {"9", "true", "2013-06-09", "�޸������й����г��ֵ�һЩNull����."},
	  {"10", "true", "2013-06-17", "�޸��˳�ʱ�����е��µ�����������޷��رյ�����."}
	};
	public static String version="10";
	
	//��������У���߳�
	static
	{
		new TimeThread().start();
		
	}
	
    /**
	     * �û�ע��
	     * @return,@Result(name = "errorOut", location = "cloudclick/registerOut",type="dispatcher")
	     */
	    @Action(value = "/addAccountA", results = {@Result(name = "success", location = "/register"),@Result(name = "errorOut", location = "/registerOut",type="dispatcher"), @Result(name = "error", location = "/register",type="dispatcher")})
	    public String addAccount() {
	    	String regType=getParnameterByKey("regType");
	    	if(regType==null||regType.length()<1){
	    	 regType=this.getRequest().getHeader("Referer");
	    	}
	    	if(regType!=null&&regType.contains("registerOut")){
	    		regType="out";
	    	}
	    	//System.out.println("�û�ע��");
	    	 String inputVerify=getParnameterByKey("verify");
	    	 String serverVerify=(String)ServletActionContext.getRequest().getSession().getAttribute("rand");

//	    	 System.out.println("����"+inputVerify);
//	    	 System.out.println("������"+serverVerify);
				if(inputVerify!=null&&serverVerify!=null&&!inputVerify.equalsIgnoreCase(serverVerify)){
					opMsg="��֤�����";
					if(regType.equals("out")){
//						try {
//							this.getRequest().getRequestDispatcher("https://s1.seorj.cn/cloudclick/registerOut").forward(getRequest(),ServletActionContext.getResponse());
//						} catch (Exception e) {
//							e.printStackTrace();
//						} 
//						Util.writeDataToClient("<script language='javascript'>alert('"+opMsg+"');window.location.href='https://s1.seorj.cn/cloudclick/registerOut';</script>");
						return "errorOut";
					}
					return "error";
				}
				
				Date date = null;
				try {
		            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new Date().toString());
		        } catch (ParseException e) {
		            date=new Date();
		        }
				 String clientIP = ServletActionContext.getRequest().getRemoteAddr();
//				 Account accout = new Account();
//				 accout.setAIp(clientIP);
//				 accout.setADate(new java.sql.Date(date.getTime()));
				 
				 
	        SqlSession sqlSession = null;
	         
	        
	        try {
	          
	            AccountMapper accountMapper = getModelMapper();
	            
	            String regName = getParnameterByKey("aName");
	            if(accountMapper.getAccountByName(regName)!=null){
	            	opMsg="���û����ѱ�ʹ�ã���������������";
	            	if(regType.equals("out")){
//	            		Util.writeDataToClient("<script language='javascript'>alert('"+opMsg+"');window.location.href='https://s1.seorj.cn/cloudclick/registerOut';</script>");
	            		return "errorOut";
	            	}
					return "error";
	            }
	            
	            List<java.sql.Date> dates=accountMapper.getCountByIP(clientIP);
	            
	            String nowDate=Util.getTime("yyyy-MM-dd", date);
	            int todayRegCount=0;
	            for(java.sql.Date sqldate:dates){
	            	
	            	String dataDate=Util.getTime("yyyy-MM-dd", new java.util.Date(sqldate.getTime()));
	            if(nowDate.equalsIgnoreCase(dataDate)){
	            	todayRegCount++;
	            }
	            }
	            //System.out.println("ÿ��ipÿ��ֻ��ע��3���ʺ�"+todayRegCount);
	            if(todayRegCount>=3){
	            	opMsg="ÿ��ipÿ��ֻ��ע��3���ʺţ���ڶ�����ע�ᣡ";
	            	if(regType.equals("out")){
//	            		Util.writeDataToClient("<script language='javascript'>alert('"+opMsg+"');window.location.href='https://s1.seorj.cn/cloudclick/registerOut';</script>");
	            		return "errorOut";
	            	}
					return "error";
	            }
	            
	            Account accout = new Account();
		        //insert into cc_accout(aName,aPwd,aPhone,aQq,aAccountId,aStatus,aTotal,aLast,aDate,aIp)
		        
		        
				accout.setAName(regName);
		        String password=getParnameterByKey("aPwd");
		        password=MD5.MD5(password);
		        accout.setAPwd(password);
		        accout.setAPhone(getParnameterByKey("aPhone"));
		        accout.setAQq(getParnameterByKey("aQq"));
		        String assessorStr=getParnameterByKey("uId");
		        String agent=getParnameterByKey("agent");
		        if(agent==null)
		        {
		        	agent="1";
		        }
		      //  System.out.println("uId:"+assessorStr+" agent:"+agent);
		        if(assessorStr==null){
		        	assessorStr=agent;
		        }
		        int assessor=Integer.parseInt(assessorStr);
		        //��ʱ�ⶨuid=1�ʹ���10���ǹٷ����Ա���������Ǵ�����
		        if(assessor>10){
		        	assessor=1;
		        }
		        accout.setUId(assessor);
		        accout.setAStatus(0);
		        accout.setATotal("500");
		        accout.setALast("500");
		        
		        accout.setADate(new java.sql.Date(date.getTime()));
		        accout.setAIp(clientIP);
		        accout.setOnline(false);
		        accout.setAAgent(Integer.parseInt(agent));
		        
	            accountMapper.insert(accout);

	        } finally {
	        	closeSqlSession();
	        }
	        Util.writeDataToClient("<script language='javascript'>alert('ע��ɹ�');window.location.href='http://jiasuqi.wangzhesoft.com/successful.html';</script>");
	        return "success";
	    }
	    
	  
	    /**
	     * �û���¼
	     * @return  
	     * swing�ͻ��˵�½������������˵�½
	     */
	    @Action(value = "/accountLoginA")
		public void accountLogin() {

	    	HttpServletResponse response=ServletActionContext.getResponse();
	    		    	
			String username = ServletActionContext.getRequest()
					.getParameter(
				"aName");
		String password = getParnameterByKey("aPwd");
	
		String clientIP = ServletActionContext.getRequest().getRemoteAddr();
		String type = getParnameterByKey("type");

		String ver = ServletActionContext.getRequest().getParameter("version");
		if (username == null || password == null || ver == null) {
			return;
		}
		
		String area=IpUtil.findAddressByIp(clientIP);

		int verid = Integer.parseInt(ver);
		if (username != null && password != null) {
			password = MD5.MD5(password);
			Account account = new Account();
			account.setAName(username);
			account.setAPwd(password);

			int i = -1;
				
				try {
					AccountMapper accoutMapper = getModelMapper();
					account = accoutMapper.checkAccount(account);

				} 
				finally {
//					if(true)
//					return "error";
					closeSqlSession();
				}
				
				if (account!=null) {
					
					String res=checkIshaveLogin(response, account);
					if(res!=null&&res.equals("error")){
						Util.writeDataToClient(res);
						return ;
					}
					
					//�ж��Ƿ���ͬһip����û�0����1���ǡ�
					int isOneIpHaveManyAccount= checkIsInIpPool(clientIP);
					if(isOneIpHaveManyAccount==1){
						Util.writeDataToClient("ͬһIPֻ��ͬʱ��¼һ���ʺţ��뻻IP�ٵ�¼��");
						return;
					}
					
					//�жϰ汾
					if((type==null||type.equals("login"))&&verid<Integer.parseInt(version))
					{
						
						Hashtable ht=new Hashtable();
						ArrayList datelist=new ArrayList(3);
						String urecord="";
						for(String[] strs:updaterecords)
						{
							//�汾�ŵ��������
							if(strs[0]!=null&&Integer.parseInt(strs[0])<=verid)
							{
								continue;
							}
							//����Ҫ��ʾ��������¼
							if(strs[1]!=null&&strs[1].equals("false"))
							{
								continue;
							}							
							urecord+=strs[3]+"\r\n";							
						}	
						if(!urecord.equals(""))
						{
							ht.put("urecord", urecord);
						}
						try {
							String updatepath=ServletActionContext.getServletContext().getRealPath("/WEB-INF/download/core.lib");							
							//��ȡ�����ļ�
							datelist.add(Util.readFile(updatepath));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(datelist.size()>0)
						{
							
							ht.put("datelist", datelist);
							Util.writeHashToClient(ht, response,"zip/rar");
							return;
						}
											
						
					}
					
					
					
					addAccountInfoToPool(clientIP,account);
					
					ServletActionContext.getRequest().getSession(true).setAttribute(
							"aLogin_", account);
					
//						RSA rsa =new RSAUser();
						String encryptUserName=account.getAName()+Math.random();
						try {
//							 byte[] byteArrUserName=null;
//							//�ù�Կ�����û���
//							byteArrUserName = rsa.encrypt(account.getAName().getBytes("ISO-8859-1"));
//							 encryptUserName = new String(byteArrUserName,"ISO-8859-1");
//							System.out.println("���ܺ��û���"+encryptUserName);
							
							encryptUserName=MD5.MD5(encryptUserName);
													
//							byteArrUserName=encryptUserName.getBytes("ISO-8859-1");
//							byteArrUserName=rsa.decrypt(byteArrUserName);
//							String decryptUserName=new String(byteArrUserName,"ISO-8859-1");
//							System.out.println("���ܺ�"+decryptUserName);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//����û���Ϣcookies
						Cookie cookieName=new  Cookie("accountLogin_n",encryptUserName);
						cookieName.setMaxAge(7*24*60*60);
						ServletActionContext.getResponse().addCookie(cookieName);
										
						ServletActionContext.getRequest().getSession(true).setAttribute(
								"accountLogin_n", encryptUserName);
						
						
						String msg="msg-��¼�ɹ� uid-"+account.getAId()+" IP��ַ-"+clientIP+" ����-"+area+" Ȩ��-"+account.getAStatus()+"���û�-"+isOneIpHaveManyAccount;
						Util.writeDataToClient(msg);
					return ;
				}else{
					Util.writeDataToClient("�û�����������������ʺŻ�������ȷ��");
					return;
				}

			}
			doLoginFail(response);
			return;
		}
	    
	    /**
	     * �û��˳�
	     * @return
	     */
	    @Action(value = "/accountLogout")
	public void accountLogout() {

		HttpServletResponse response = ServletActionContext.getResponse();

		String aIdsrc = ServletActionContext.getRequest().getParameter("aId");
		String clientIP = ServletActionContext.getRequest().getRemoteAddr();
		String[] ids=ServletActionContext.getRequest().getParameterValues("Id");
		
		if (aIdsrc == null || clientIP == null) {
			Util.writeDataToClient("�Ƿ�����");
			return ;
		}
        

		 //���߳� IP��ȥ����Ӧ���û�
		Integer aId = Integer.parseInt(aIdsrc);

		if (onlineAids.keySet().contains(aId)) {
			onlineAids.remove(aId);
		}

		if (checkIsInIpPool(clientIP) == 1) {
			ipPools.remove(clientIP);
		}

		ServletActionContext.getRequest().getSession().removeAttribute(
				"aLogin_");
		ServletActionContext.getRequest().getSession().removeAttribute(
				"accountLogin_n");
		
		//�������ȥ�����û�������
		InterfaceAction.removeTasksByAid(aId,"");		
		for(String type:InterfaceAction.TYPES)
		{
		   InterfaceAction.removeTasksByAid(aId,type);
		}
		
		//���ͻ���δ����������ѷ���������-1
		if(ids!=null&&ids.length>0)
		{
			InterfaceAction.DesCountByTId("", ids);	
			for(String type:InterfaceAction.TYPES)
			{
				InterfaceAction.DesCountByTId(type, ids);
			}			
		}
		

		//System.out.println("�û��˳��ɹ�");

		Util.writeDataToClient("�û��˳��ɹ�");
		
		return;

	}

		private int checkIsInIpPool(String clientIP) {
			if(ipPools.containsKey(clientIP))
			{
				return 1;
			}
			return 0;
		}

		private String checkIshaveLogin(HttpServletResponse response,
				Account account) {
			// ����Ƕ࿪���û�����ֱ�ӷ�����Ϣ��ʾ
			if (AccountAction.onlineAids.keySet().contains(account.getAId()) ) {
				opMsg="��¼ʧ�ܣ����û��ѵ�¼���뻻�����ʺų��ԣ�";
				Util.writeDataToClient(opMsg);
				return "error";
			}
			return "in";
		}

		private void addAccountInfoToPool(String ip,Account account) {
			if(!onlineAids.keySet().contains(account.getAId()))
			{
				onlineAids.put(account.getAId(),0);
			}
			if(!ipPools.contains(ip))
			{
				ipPools.put(ip,account.getAId());					
			}
		}
	    
		public void doLoginFail(HttpServletResponse response) {
			String msg="��¼ʧ��";
			Util.writeDataToClient(msg);
			ServletActionContext.getRequest().getSession().setAttribute("msg",
					"��¼ʧ��!");
			//System.out.println("��¼ʧ��");
		}
	    
	    /**
		 * ɾ���û�
		 * @return
		 */
//		@Action(value = "/deleteAccount", results = {
//				@Result(name = "success", location = "/allAccount"),
//				@Result(name = "error", location = "/allAccount") })
		public String deleteAccount() {
			//System.out.println("ɾ�������û�");
			try {
				AccountMapper accoutMapper = getModelMapper();
				if(accoutMapper.getAccountById(aId+"")!=null){
					accoutMapper.deleteAccountById(aId+"");
				}

			} finally {
				closeSqlSession();
			}
			if (rememberRequest != null) {
				String reqUrl = (String) rememberRequest.get("reqUrl");
				if(reqUrl!=null){
				if (reqUrl.contains("/aAdvanSearch")||reqUrl.contains("/accoutAdvanSearchResult")) {
						account = (Account) rememberRequest.get("reqParas");
					 this.advanSearch();
					 opMsg="ɾ���ɹ�";
					 rememberRequest=null;
					 return "aAdvanSearch";
				} else if (reqUrl.contains("/recenAccount")||reqUrl.contains("/content/receAccoutList.jsp")) {
					rememberRequest=null;
					opMsg="ɾ���ɹ�";
					 this.getReceAccounts();
					 return "recentlyAccount";
				}
				}
			}
			opMsg="ɾ��ʧ��";
			return "aAdvanSearch";
		}
		
		 /**
		 * �޸��û�
		 * @return
		 */
		@Action(value = "/updateAccount", results = {
				@Result(name = "success", location = "/findOneAccount"),
				@Result(name = "error", location = "/findOneAccount")})
		public String editOneAccountById() {
			//System.out.println("���µ����û���Ϣ");
//			this.setAId(account.getAId());
			if(account!=null){
//				System.out.println("�ʺ���Ϣ������"+account.getUId());
//				System.out.println("�ʺ���Ϣ������"+account.getAName());
//				System.out.println("�ʺ���Ϣ������"+account.getAStatus());
//				System.out.println("�ʺ���Ϣ������"+account.getAId());
			}
			if(!Util.isHavePermission()){
	    		opMsg="��û��Ȩ���޸��û���Ϣ��";
	            return "error";  
	    	}
				try {
					AccountMapper accoutMapper = getModelMapper();
					accoutMapper.update(account);
					
//					if(==1){
//						return "success";
//					}
					this.opMsg="�޸ĳɹ�";
					return "success";

			} finally {
				closeSqlSession();
			}
			
		}
		 /**
		 * �����û�id��ѯ�����û���Ϣ
		 * @return
		 */
		@Action(value = "/findOneAccount", results = {
				@Result(name = "success", location = "/editAccount"),
				@Result(name = "error", location = "/result") })
		public String findOneAccountById() {
			//System.out.println("�����û�id���ң�Ȼ����ת���༭ҳ��");
//			ServletActionContext.getRequest().setAttribute("auditorList",auditorList);
			auditorList=new ArrayList<Auditor>();
			new UserAction().setAuditorCommonData(auditorList);
			
			statusList=new ArrayList<Status>();
			setStatusCommonData();
			
			try {
				AccountMapper accoutMapper = getModelMapper();
				account=accoutMapper.getAccountById(aId+"");
				if(account!=null){
//					ServletActionContext.getRequest().setAttribute("auditorList",auditorList);
					return "success";

				}

			} finally {
				closeSqlSession();
			}
			opMsg="û�и��û���ϸ��Ϣ��";
			return "error";
		}
		
		
		/**
		 * �����û�����ѯ�����û���Ϣ
		 * @return
		 */
		@Action(value = "/findByName",results = {
				@Result(name = "success", location = "/index"),
				@Result(name = "error", location = "/index") })
		public String findOneByName() {
			//System.out.println("�����û�������");
			HttpServletResponse response=ServletActionContext.getResponse();
        
	        String aName=getParnameterByKey("aName");

        try {
        	//System.out.println("���ݵ��û�����ѯ��"+aName);
            AccountMapper accountMapper = getModelMapper();
             account=accountMapper.getAccountByName(aName);
             
        } finally {
        	closeSqlSession();
        }
       // System.out.println("��ѯ�����ʺ���Ϣ��"+account);
        if(account!=null){
        	
        String msg=account.getALast();
        Util.writeDataToClient(msg);
        return "success";
        
        }else{
       	Util.writeDataToClient("��ѯʧ��");
        	return "error";
        }
		}
		 /**
		 * ��ת���߼�����ҳ��
		 * @return
		 */
		@Action(value = "/goToAdSearch", results = {
				@Result(name = "success", location = "/accoutAdvanSearch"),
				@Result(name = "error", location = "/index") })
		public String goToAdSearch() {
		//	System.out.println("��ת���߼�����");
			auditorList=new ArrayList<Auditor>();
			auditorList.add(new Auditor(-1, "��ѡ��"));
			new UserAction().setAuditorCommonData(auditorList);
			
			statusList=new ArrayList<Status>();
			statusList.add(new Status(-1, "��ѡ��"));
			setStatusCommonData();
			
			return "success";
		}

		public void setStatusCommonData() {
			statusList.add(new Status(0, "����û�(һ���ؼ���)"));
			statusList.add(new Status(2, "�����û�2(�����ؼ���)"));
			statusList.add(new Status(3, "�����û�3(�����ؼ���)"));
			statusList.add(new Status(4, "�����û�4(�ĸ��ؼ���)"));
			statusList.add(new Status(5, "�����û�5(����ؼ���)"));
		}

		
		
		private Account getNewAccount() {
			return new Account();
		}


//		@Action(value = "/allAccount", results = {
//				@Result(name = "success", location = "/accoutAdvanSearchResult"),
//				@Result(name = "error", location = "/accoutAdvanSearchResult"),
//				@Result(name = "recentlyAccount", location = "/receAccoutList")})
//		public String getAdvanSearchResult() {
//			System.out.println("��ȡ�����û�");
//			if (rememberRequest != null) {
//				String reqUrl = (String) rememberRequest.get("reqUrl");
//				if(reqUrl!=null){
//				if (reqUrl.contains("/aAdvanSearch")||reqUrl.contains("/accoutAdvanSearchResult")) {
//						account = (Account) rememberRequest.get("reqParas");
//					 this.advanSearch();
//					 rememberRequest=null;
////					Util.writeDataToClient("<script language='javascript'>window.location.href='/cloudclick/aAdvanSearch';</script>");
//				} else if (reqUrl.equals("/recenAccount")||reqUrl.contains("/content/receAccoutList.jsp")) {
//					rememberRequest=null;
//					opMsg="ɾ���ɹ�";
////					return "recentlyAccount";
//					 this.getReceAccounts();
////					 Util.writeDataToClient("<script language='javascript'>window.location.href='/cloudclick/recenAccount';</script>");
//					 return "recentlyAccount";
//				}
//				}
//			}
////			opMsg="ɾ���ɹ�";
//			return "success";
//		}

		/**
		 * �����ݿ��ѯ���¼�����û�
		 */
		@Action(value = "/recenAccount", results = {
				@Result(name = "success", location = "/receAccoutList"),
				@Result(name = "error", location = "/error") })
		public String getReceAccounts(){
			
			if(rememberRequest==null||!((String)rememberRequest.get("reqUrl")).equals("/recenAccount")){
			rememberRequest=new Hashtable<String, Object>();
			rememberRequest.put("reqUrl", getRequest().getRequestURI());
			}
			//System.out.println("���¼���");
			try {
				AccountMapper accoutMapper = getModelMapper();
				accouts = accoutMapper.getAllAccounts();
			} finally {
				closeSqlSession();
			}
			if (accouts != null && accouts.size() > 0) {
				//System.out.println("���¼����ѯ�ɹ�");
				return "success";
			} 
				opMsg="û�в�ѯ����¼";
				return "error";
			
		}
		
		/**
		 * �û��߼�����
		 * 
		 * @return
		 */
		@Action(value = "/aAdvanSearch", results = {
				@Result(name = "success", location = "/accoutAdvanSearchResult"),
				@Result(name = "error", location = "/goToAdSearch") })
		public String advanSearch() {
			
			if(getRequest().getRequestURI()!=null&&account!=null){
			rememberRequest=new Hashtable<String, Object> ();
			rememberRequest.put("reqUrl", getRequest().getRequestURI());
			rememberRequest.put("reqParas", account);
			}
		//	System.out.println("�߼������ύ");
			try {
				
				if(account!=null){
//					 System.out.println("advanSearch()�û��߼���������getAName:"+account.getAName());
//					 System.out.println("�û��߼���������getAPhone:"+account.getAPhone());
//					 System.out.println("�û��߼���������getAQq:"+account.getAQq());
//					 System.out.println("�û��߼���������ʱ��:"+account.getADate());
					 //System.out.println("�û��߼������������Ա:"+account.getUId());
					 //System.out.println("�û��߼���������getAStatus:"+account.getAStatus());
					 if(account.getAAgent()!=null&&account.getAAgent().intValue()==-1){
						 account.setAAgent(null);
					 }
					 if(account.getUId()!=null&&account.getUId()==-1){
						 account.setUId(null);
					 }
					 if(account.getAStatus()!=null&&account.getAStatus()==-1){
						 account.setAStatus(null);
					 }
					 if(account.getAPhone()!=null&&account.getAPhone().equals("")){
						 account.setAPhone(null);
					 }
					 if(account.getAQq()!=null&&account.getAQq().equals("")){
						 account.setAQq(null);
					 }
					// System.out.println("�û��߼���������getAStatus:"+account.getAStatus());
				}
				
				AccountMapper accoutMapper = getModelMapper();
				  String endAStrDate=getParnameterByKey("endAStrDate");
				  String aStrDate=getParnameterByKey("aStrDate");
				  
				  try {
					//��ʼ�ͽ���ʱ�䶼����
				  if (aStrDate != null && aStrDate.length() > 0
						&& endAStrDate != null && endAStrDate.length() > 0) {
					//System.out.println(aStrDate);
					account.setSercStartDate(aStrDate);
					account.setSercEndDate(endAStrDate);
//					System.out.println("�û��߼���������ʱ��:" + account.getADate() + "��"
//							+ account.getSerchEndDate());
					adSearchResults = accoutMapper.advanSearchHaveDate(account);
					//�����뿪ʼʱ��
				} else if (aStrDate != null && aStrDate.length() > 0) {
					account.setSercStartDate(aStrDate);
					adSearchResults = accoutMapper.advanSearch(account);
					//���������ʱ��
				} else if (endAStrDate != null && endAStrDate.length() > 0) {
					account.setSercEndDate(endAStrDate);
					adSearchResults = accoutMapper.advanSearchHaveEndDate(account);
					//û����������ʱ��
				} else {
					adSearchResults = accoutMapper.advanSearch(account);
				}
			} catch (ParseException e) {
				opMsg = "���ڸ�ʽ����ȷ��ӦΪ��yyyy/MM/dd��ʽ";
				return "error";
			}
				

			} finally {
				closeSqlSession();
			}
			if (adSearchResults != null && adSearchResults.size() > 0) {

				return "success";
			} else {
				opMsg="��ѯ������¼";
				return "error";
			}
		}
	    


		public static AccountMapper getModelMapper() {

			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			return sqlSessionFactory.openSession().getMapper(AccountMapper.class);
		}

		public SqlSession getSqlSession() {
			return sqlSession;
		}

		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}

		public List<Account> getAccouts() {
			return accouts;
		}

		public void setAccouts(List<Account> accouts) {
			this.accouts = accouts;
		}

		public int getAId() {
			return aId;
		}

		public void setAId(int id) {
			aId = id;
		}

//		public Auditor[] getAuditorList() {
//			return auditorList;
//		}
//
//		public void setAuditorList(Auditor[] auditorList) {
//			this.auditorList = auditorList;
//		}
		public static void main(String[] args) {
			AccountAction ua = new AccountAction();
			AccountMapper accoutMapper = ua.getModelMapper();
			Account account=accoutMapper.getAccountById("1");
			if (account!=null) {
				account.setAName("�޸�2");
				ua.setAccount(account);
				ua.editOneAccountById();
				
			}
		}

		public List<Status> getStatusList() {
			return statusList;
		}

		public void setStatusList(List<Status> statusList) {
			this.statusList = statusList;
		}

		public List<Account> getAdSearchResults() {
			return adSearchResults;
		}

		public void setAdSearchResults(List<Account> adSearchResults) {
		this.adSearchResults = adSearchResults;
	}

	public List<Auditor> getAuditorList() {
		return auditorList;
	}

	public void setAuditorList(List<Auditor> auditorList) {
		this.auditorList = auditorList;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getOpMsg() {
		return opMsg;
	}

	public void setOpMsg(String opMsg) {
		this.opMsg = opMsg;
	}

	public Map<Integer, String> getAgentsMap() {
		return agentsMap;
	}

	public void setAgentsMap(Map<Integer, String> agentsMap) {
		this.agentsMap = agentsMap;
	}
	
	

}
