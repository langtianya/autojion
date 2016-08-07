package com.wangzhe.action;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangzhe.common.MD5;
import com.wangzhe.common.Util;
import com.wangzhe.db.DB;
import com.wangzhe.model.Auditor;
import com.wangzhe.model.Refresh;
import com.wangzhe.model.RefreshMapper;
import com.wangzhe.model.Status;
import com.wangzhe.model.Task;
import com.wangzhe.model.UserMapper;
import com.opensymphony.xwork2.ActionSupport;
/**
 * Refresh: Administrator
 * Date: 13-2-28
 * Time: ����5:39
 */
@Namespace("/")
public class RefreshAction extends BaseAction{
	
         //�Կͻ����ṩ�Ľӿ�ʵ��!
	 private List<Refresh> refreshs;
	private int aId;
	private String opMsg;
    private Refresh refresh;
    private List<Refresh> adSearchResults;
		
		//�޸��û���Ϣҳ�������Ա��select list
//    private Auditor[] auditorList = {new Auditor(1, "�ٷ�"),new Auditor(2, "A����"), new Auditor(3, "B����")};
    private List<Auditor> auditorList = null;
    private List<Status> statusList = null;
    
    public RefreshAction(){
		log = getLogger(RefreshAction.class);
	}
	 public List<Auditor> getAuditorList() {
		return auditorList;
	}

	public void setAuditorList(List<Auditor> auditorList) {
		this.auditorList = auditorList;
	}

	public Refresh getRefresh() {
			return refresh;
		}

		public void setRefresh(Refresh refresh) {
			this.refresh = refresh;
		}

	public String getOpMsg() {
			return opMsg;
		}

		public void setOpMsg(String opMsg) {
			this.opMsg = opMsg;
		}

	/**
	     * ���ˢ�¼�¼
	     * @return
	     */
	    @Action(value = "/addRefreshA")
	    public void addRefresh() {
	    	
				Date date = null;
				try {
		            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new Date().toString());
		        } catch (ParseException e) {
		            date=new Date();
		        }
		        
		        String clientIP = ServletActionContext.getRequest().getRemoteAddr();
		        Refresh refresh = new Refresh();
		        
		        refresh.setRKeyword(getParnameterByKey("rKeyword"));
		        refresh.setRUrl(getParnameterByKey("rUrl"));
		        refresh.setRSEType(getParnameterByKey("rSEType"));
		        refresh.setRIp(clientIP);
		        refresh.setRDate(new Timestamp(date.getTime()));
		   
		        refresh.setTId(Long.valueOf(getParnameterByKey("tId")));
		        refresh.setArea(getParnameterByKey("area"));
		        
	        try {
	          
	            RefreshMapper refreshMapper = getModelMapper();
	            refreshMapper.insert(refresh);
	            //����Ƿ���ӳɹ�
	            if(refreshMapper.checkRefresh(refresh)!=0){
	            	Util.writeDataToClient("add refresh success");
	      	        return;
	            }
	        } finally {
	        	closeSqlSession();
	        }
	        return ;
	    }

	    /**
	     * �û���¼
	     * @return
	     */
	    @Action(value = "/refreshLoginA", results = {
				@Result(name = "success", location = "/userHome"),
				@Result(name = "error", location = "/refreshLogin") })
		public String refreshLogin() {
	    	
			String username = ServletActionContext.getRequest()
					.getParameter("aName");
			String password = getParnameterByKey("aPwd");
		        password=MD5.MD5(password);
		

			if (username != null && password != null) {
				Refresh refresh = new Refresh();
//				refresh.setAName(username);
//				refresh.setAPwd(password);
				 
				ServletActionContext.getRequest().getSession(true).setAttribute(
						"aLogin_", username);
				SqlSession sqlSession = null;
				int i = -1;
				
				try {
					RefreshMapper refreshMapper = getModelMapper();
					i = refreshMapper.checkRefresh(refresh);

				} finally {
					closeSqlSession();
				}
				
				if (i > 0) {
					//System.out.println("�ɹ�"+i);
					return "success";
				}

			}
			ServletActionContext.getRequest().getSession().setAttribute("msg",
					"��¼ʧ��!");
		//	System.out.println("ʧ��");
			return "error";
		}
	    
		public String deleteRefresh(Map<String, Object> taskMap) {
			
				Refresh refresh=new Refresh();
				refresh.setRKeyword((String)taskMap.get("tKeyword"));
				refresh.setRUrl((String)taskMap.get("tUrl"));
				refresh.setRSEType((String)taskMap.get("tSEType"));
				refresh.setTId((Long)taskMap.get("tId"));
				refresh.setEndDate(new Date());
			try {
				RefreshMapper refreshMapper = getModelMapper();
				if(refreshMapper.checkRefresh(refresh)>0){
					refreshMapper.deleteRefresh(refresh);
					if(refreshMapper.checkRefresh(refresh)>0){
						return "error";
					}
				}else{
					return "success";
				}

			} finally {
				closeSqlSession();
			}
			return "success";
		}
		/**
		 * ע�⣺�պ�ʹ�õĻ����������ֻ�е���ʱ����ڵ����β�nDateAgo�ſ���
		 * ��������������nDateAgoΪ14��������ֻ��ÿ�µ�14��28�ղ�ʹ�ø÷���������14-14=�ϸ���ĩ��28-14���ڵ���14��
		 * @param nDateAgo
		 * @return
		 */
		public static String delNDateAgoRecord(int nDateAgo) {	
			SqlSession sqlSession =null;
		try {
			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			 sqlSession = sqlSessionFactory.openSession();
			RefreshMapper refreshMapper = sqlSession.getMapper(RefreshMapper.class);
			//System.out.println(new java.sql.Date(getNDateAgo(nDateAgo).getTime()));
				refreshMapper.delNDateAgoRecord(getNDateAgo(nDateAgo));
		
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return "success";
	}
		
		
		 /**
		 * ���������ѯ�������ˢ�¼�¼
		 * @return
		 */
		public List queryRefreshsByTask(Task task) {

			Date fifthDateAgo = getNDateAgo(15);
			
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("tId",task.getTId());
	        map.put("rKeyword",task.getTKeyword());
	        map.put("rUrl", task.getTUrl());
	        map.put("rSEType", task.getTSEType());
	        map.put("fiftDateAgo", fifthDateAgo);
	        
	        
        try {
          
            RefreshMapper refreshMapper = getModelMapper();
            List<Refresh> refreshs=refreshMapper.queryByRefresh(map);
            
            if(refreshs!=null&&refreshs.size()>0){

         		return refreshs;
            }else{
            	
            }
        }
        finally {
        	closeSqlSession();
        }
        return null;
		}
		/**
		 * ��ȡn����ǰ��ʱ��
		 * @param nDate
		 * @return
		 */
		private static Date getNDateAgo(int nDate) {
			long endTime=(new Date().getTime()-Math.abs(86400000*nDate));
			return new Date(endTime);
		}
		
	



		
		private Refresh getNewRefresh() {
			return new Refresh();
		}

		/**
		 * �����ݿ��ѯ����
		 * 
		 * @return
		 */
		@Action(value = "/allRefresh", results = {
				@Result(name = "success", location = "/refreshList"),
				@Result(name = "error", location = "/index") })
		public String getAllRefreshs() {

			try {
				RefreshMapper refreshMapper = getModelMapper();
				refreshs = refreshMapper.getAllRefreshs();
			

			} finally {
				closeSqlSession();
			}
			if (refreshs != null && refreshs.size() > 0) {

				return "success";
			} else {
				return "error";
			}
		}
		
		/**
		 * �û��߼�����
		 * 
		 * @return
		 */
		@Action(value = "/refreshAdvanSearch", results = {
				@Result(name = "success", location = "/refreshAdvanSearchResult"),
				@Result(name = "error", location = "/goToAdSearch") })
		public String advanSearch() {

			try {
				if(refresh!=null){
//					 System.out.println("�û��߼���������::"+refresh.getAName());
//					 System.out.println("�û��߼���������::"+refresh.getAPhone());
//					 System.out.println("�û��߼���������::"+refresh.getAStatus());
//					 refresh.setAStatus(null);
				}
				RefreshMapper refreshMapper = getModelMapper();
//				adSearchResults=refreshMapper.advanSearch(refresh);
				

			} finally {
				closeSqlSession();
			}
			if (adSearchResults != null && adSearchResults.size() > 0) {

				return "success";
			} else {
				return "error";
			}
		}
	    

		private RefreshMapper getModelMapper() {
			return (RefreshMapper)getModelMapper(RefreshMapper.class);
		}

		public SqlSession getSqlSession() {
			return sqlSession;
		}

		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}

		public List<Refresh> getRefreshs() {
			return refreshs;
		}

		public void setRefreshs(List<Refresh> refreshs) {
			this.refreshs = refreshs;
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
//			RefreshAction ua = new RefreshAction();
//			RefreshMapper refreshMapper = ua.getModelMapper();
////			Refresh refresh=refreshMapper.getRefreshById("1");
////			if (refresh!=null) {
//////				refresh.setAName("�޸�2");
////				ua.setRefresh(refresh);
////				ua.editOneRefreshById();
////			}
//			for(int i=0;i<100;i++){    
//				 
//				 String randString9=Util.getRandomStr(9);
//					int randomInt4 = Util.getRandomInt(1,4);
//					int randomInt2 = Util.getRandomInt(2);
//					
//					Refresh refresh = new Refresh();
//							
//			        refresh.setRKeyword(randomInt2==1?"������":"����΢��");
//			        refresh.setRUrl(randomInt2==1?"www.163.com":"www.weibo.com");
//			        refresh.setRSEType(randomInt2==1?"Google Hk":"Baidu");
//			        refresh.setRIp("172.0.0.1");
//			        refresh.setRDate(new java.sql.Date(new Date().getTime()));
//			        refresh.setTId(Long.valueOf(randomInt4));
//			        System.out.println("��ӳɹ��û�"+i);
//			        refreshMapper.insert(refresh);
//			 }

//			long endTime=(new Date().getTime()-86400000*15);
//	     
//	        System.out.println("ʮ����ǰ��"+new Date(endTime));
			
			String time = Util.getTime();
			int p0 = time.indexOf(" ");
			int p2 = time.lastIndexOf("-");
			//System.out.println(time+" "+InterfaceAction.time_yesterday);
			String timenow = time.substring(p2 + 1, p0);
			//System.out.println("�����գ�"+timenow);
			
			if(timenow!=null&&timenow.equals("14")||timenow.equals("25")){
				 RefreshAction.delNDateAgoRecord(25);
				 long endTime=(new Date().getTime()-86400000*26);
			//	 System.out.println("�����գ�"+Math.abs(86400000*26));
				// System.out.println("�����գ�"+Math.abs(86400000*26));
			}
		}

		public List<Status> getStatusList() {
			return statusList;
		}

		public void setStatusList(List<Status> statusList) {
			this.statusList = statusList;
		}

		public List<Refresh> getAdSearchResults() {
			return adSearchResults;
		}

		public void setAdSearchResults(List<Refresh> adSearchResults) {
			this.adSearchResults = adSearchResults;
		}
}
