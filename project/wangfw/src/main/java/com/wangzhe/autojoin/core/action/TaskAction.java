package com.wangzhe.action;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangzhe.common.MD5;
import com.wangzhe.common.Util;
import com.wangzhe.db.DB;
import com.wangzhe.model.Account;
import com.wangzhe.model.AccountMapper;
import com.wangzhe.model.Auditor;
import com.wangzhe.model.Refresh;
import com.wangzhe.model.RefreshMapper;
import com.wangzhe.model.Status;
import com.wangzhe.model.Task;
import com.wangzhe.model.TaskMapper;
import com.opensymphony.xwork2.ActionSupport;
/**
 * Task: Administrator Date: 13-2-28 Time: 下午5:39
 */
@Namespace("/")

public class TaskAction  extends BaseAction{
         // 对客户端提供的接口实现!
	 private List<Task> tasks;
	private int aId;
	private String opMsg;
    private Task task;
    private List<Task> adSearchResults;
		
		// 修改任务信息页面中审核员的select list
// private Auditor[] auditorList = {new Auditor(1, "官方"),new Auditor(2, "A代理"),
// new Auditor(3, "B代理")};
    private List<Auditor> auditorList = null;
    private List<Status> statusList = null;
    public TaskAction(){
		log = getLogger(TaskAction.class);
	}
	 public List<Auditor> getAuditorList() {
		return auditorList;
	}

	public void setAuditorList(List<Auditor> auditorList) {
		this.auditorList = auditorList;
	}

	public Task getTask() {
			return task;
		}

		public void setTask(Task task) {
			this.task = task;
		}

	public String getOpMsg() {
			return opMsg;
		}

		public void setOpMsg(String opMsg) {
			this.opMsg = opMsg;
		}

	/**
	 * 任务注册
	 * 
	 * @return
	 */
	    @Action(value = "/addTaskA")
	    public void addTask() {
	    	
	    	Date date = null;
			try {
	            date = new SimpleDateFormat("yyyy-MM-dd").parse(new Date().toString());
	        } catch (ParseException e) {
	            date=new Date();
	        }
	        String tKeyword=getParnameterByKey("tKeyword");
	        String tUrl=getParnameterByKey("tUrl");
	        String tSEType=getParnameterByKey("tSEType");
	        String tSetClick=getParnameterByKey("tSetClick");
	        String tTime=getParnameterByKey("tTime");
	        String tArea=getParnameterByKey("tArea");
	        String aId = getParnameterByKey("aId");
	        boolean flag=false;
	        if(tSEType!=null)
	        {
	        for(String type:InterfaceAction.TYPES)
	        {
	        	if(tSEType.equals(type))
	        	{
	        		flag=true;
	        		break;
	        	}
	        	
	        }        
	        }
	        if(tKeyword==null||tUrl==null||aId==null||tSEType==null||tSetClick==null||!flag)
	        {
	        	Util.writeDataToClient("缺少提交参数，请稍后再试！");
	           	return ;
	        }
	        if(tArea.equals(""))
	        {
	        	tArea=null;
	        }
	        if(tTime.equals(""))
	        {
	        	tTime=null;
	        }
      
	        Map<String, Object> taskMap = new Hashtable<String, Object>();        
	        taskMap.put("tKeyword",tKeyword);
	        taskMap.put("tUrl",tUrl);
	        taskMap.put("tSEType",tSEType);
	        taskMap.put("tSetClick",Integer.parseInt(tSetClick));
	        taskMap.put("tDate",new java.sql.Date(date.getTime()));
	        if(tTime==null)
	        {
	        	taskMap.put("tTime","");  
	        }
	        else
	        {
	            taskMap.put("tTime",tTime); 
	        }
	        if(tArea==null)
	        {
	            taskMap.put("tArea","");  
	        }
	        else
	        {
	            taskMap.put("tArea",tArea); 
	        }       	    
			taskMap.put("aId",aId);               
        try {
        	TaskMapper taskMapper = getModelMapper();           
            task = taskMapper.queryByTask2(taskMap);  
            //Action验证记录 是否存在 添加个数是否超过了权限上限 客户端会做本地限制，防止通过表单添加绕过权限设置 可改成通过客户端提交相关参数判断
        	// 判断是否存在
        	if(task!=null){
             	 Util.writeDataToClient("该搜索引擎里的关键词网址对已经提交过了，请确保记录中”关键词、网址、搜索引擎类型“不一样！");
             	 return ;
        	}       
        	// 判断添加个数是否超过了权限上限
        	try {
        		
            AccountMapper accoutMapper = AccountAction.getModelMapper();
            Account account=accoutMapper.getAccountById(aId);
            int Status=account.getAStatus();          
            // 免费用户只可以添加一个关键词
            if(Status==0){
            	Status=1;
            }
            int haveCount=getModelMapper().getCountByTask(task);
            if(Status==haveCount){
            	Util.writeDataToClient("对不起，您的权限只能添加"+haveCount+"个任务。");
           	    return ;
            }
        	}
        	 catch (Exception e) {
 	           e.printStackTrace();
 	        }       	
            taskMapper.insert(taskMap);
            
            //添加到任务池
            task = taskMapper.queryByTask(taskMap);  
            if(task!=null)
            {           
            	task.setTHaveClick(task.getTSetClick());
                InterfaceAction.addTaskToPool(task);             
            }
            else
            {
            	//System.out.println("新添加的任务,添加到任务池失败！");
            }
            Util.writeDataToClient("添加成功！");         
            
        } finally {
        	closeSqlSession();
        }
        return ;
	    }
	    
	    /**
		 * 任务登录
		 * 
		 * @return
		 */
	    @Action(value = "/taskLoginA", results = {
				@Result(name = "success", location = "/userHome"),
				@Result(name = "error", location = "/taskLogin") })
		public String taskLogin() {
	    	
			String username = ServletActionContext.getRequest()
					.getParameter("aName");
			String password = ServletActionContext.getRequest().getParameter("aPwd");
		        password=MD5.MD5(password);
		

			if (username != null && password != null) {
				Task task = new Task();
// task.setAName(username);
// task.setAPwd(password);
				 
				ServletActionContext.getRequest().getSession(true).setAttribute(
						"aLogin_", username);
				SqlSession sqlSession = null;
				int i = -1;
				
				try {
					TaskMapper taskMapper = getModelMapper();
					i = taskMapper.checkTask(task);

				} finally {
					closeSqlSession();
				}
				
				if (i > 0) {
					//System.out.println("成功"+i);
					return "success";
				}

			}
			ServletActionContext.getRequest().getSession().setAttribute("msg",
					"登录失败!");
			//System.out.println("失败");
			return "error";
		}
	    
	    /**
		 * 删除任务
		 * 
		 * @return
		 */
		@Action(value = "/delTask", results = {
				@Result(name = "success", location = "/result"),
				@Result(name = "error", location = "/result") })
		public String deleteOneTask() {
			
			Map<String, Object> taskMap = new Hashtable<String, Object>();
			
	        taskMap.put("tKeyword",getParnameterByKey("tKeyword"));
	        taskMap.put("tUrl",getParnameterByKey("tUrl"));
	        taskMap.put("tSEType",getParnameterByKey("tSEType"));
	        taskMap.put("aId",getParnameterByKey("aId"));
	        
		      Task task=null;
			try {
				TaskMapper taskMapper = getModelMapper();
					sqlSession.getConnection().setAutoCommit(false);
					task=taskMapper.queryByTask(taskMap);
				if(task!=null){
					taskMap.put("tId", task.getTId());
				taskMapper.deleteByTask(taskMap);
				}else{
					 Util.writeDataToClient("对不起，云端没有该任务信息");
					return "error";
				}
				
				// 查询，看是否删除成功
				task=taskMapper.queryByTask(taskMap);			
			if(task==null){
				
				
				String isSuccess =null;
				isSuccess = new RefreshAction().deleteRefresh(taskMap);
				
				   if(isSuccess!=null&&isSuccess=="success"){
					sqlSession.commit();
					delTaskFromPool(taskMap);
				     Util.writeDataToClient("删除任务成功");
				     return "success";
					}else{
						sqlSession.rollback();
					}
			}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			closeSqlSession();
		}
			return "error";
		}
		
		private void delTaskFromPool(Map<String, Object> taskMap) {
			Task task;
			task=new Task();
			task.setTKeyword((String)taskMap.get("tKeyword"));
			task.setTUrl((String)taskMap.get("tUrl"));
			task.setTSEType((String)taskMap.get("tSEType"));
			task.setAId((String)taskMap.get("aId"));
			InterfaceAction.delTaskFromPool(task);
		}
		
		 /**
			 * 删除任务列表
			 * 
			 * @return
			 */
	@Action(value = "/deleteTasks", results = {
			@Result(name = "success", location = "/allTask"),
			@Result(name = "error", location = "/allTask") })
	public String deleteTasks() {

		String jsionStr = getParnameterByKey("records");
		if (jsionStr == null) {
			return "error";
		}
		try {
			TaskMapper taskMapper = getModelMapper();

		// 下面两个循环内是解析jsion格式数据，并根据解析的数据查询数据库
		String[][] records = Util.getPatternValuesArray(jsionStr,"\"(\\d+)\":\\{(.*?)\\},", 2);
		for (int i = 0; i < records.length; i++) {
			if (records[i][0] == null || records[i][1] == null) {
				continue;
			}
			String[][] recordPros = Util.getPatternValuesArray(records[i][1],"(.*?):(.*?)[,]+", 2);

			Map<String, Object> parasMap = new HashMap<String, Object>();
			for (int j = 0; j < recordPros.length; j++) {
				if (recordPros[j][0] == null || recordPros[j][1] == null) {
					continue;
				}
				parasMap.put(recordPros[j][0], recordPros[j][1]);
				taskMapper.deleteByTask(parasMap);
			}
		}
		} finally {
			closeSqlSession();
		}
		Util.writeDataToClient("删除任务成功");
		return "success";
	}
		
		 /**
			 * 修改任务
			 * 
			 * @return
			 */
		@Action(value = "/updateOneById", results = {
				@Result(name = "success", location = "/allTask"),
				@Result(name = "error", location = "/findOneTask") })
		public String updateOneById() {
			
	    	HttpServletResponse response=ServletActionContext.getResponse();
// Date date = null;
// try {
// date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new
// Date().toString());
// } catch (ParseException e) {
// date=new Date();
// }
	        
	        Task task = new Task();
	        task.setTKeyword(getParnameterByKey("tId"));
// task.setTKeyword(getParnameterByKey("tKeyword"));
// task.setTUrl(getParnameterByKey("tUrl"));
// task.setTSEType(getParnameterByKey("tSEType"));
	        task.setTSetClick(Integer.parseInt(getParnameterByKey("tSetClick")));
// task.setTAssignedClick(0);
// task.setTHaveClick(0);
// task.setTPriority(Integer.parseInt(getParnameterByKey("tPriority")));
// task.setTDate(new java.sql.Date(date.getTime()));
// task.setAId(getParnameterByKey("aId"));
	        
        try {
          
            TaskMapper taskMapper = getModelMapper();
            taskMapper.update(task);

        } finally {
        	closeSqlSession();
        }
        String msg="更新成功";
        Util.writeDataToClient(msg);
		return "success";
		}
		
		 /**
			 * 修改根据任务对象任务
			 * 
			 * @return
			 */
		@Action(value = "/updateOneByObject", results = {
				@Result(name = "success", location = "/index"),
				@Result(name = "error", location = "/index") })
		public String updateOneByObject() {
			
	    	HttpServletResponse response=ServletActionContext.getResponse();
 
	        Task task = new Task();
	        task.setAId(getParnameterByKey("uId"));
	        task.setTKeyword(getParnameterByKey("tKeyword"));
	        task.setTUrl(getParnameterByKey("tUrl"));
	        task.setTSEType(getParnameterByKey("tSEType"));
	        task.setTSetClick(Integer.parseInt(getParnameterByKey("tSetClick")));
// task.setTPriority(Integer.parseInt(getParnameterByKey("tPriority")));
	        
        try {        
            TaskMapper taskMapper = getModelMapper();
            taskMapper.update(task);
        } finally {
        	closeSqlSession();
        }
        InterfaceAction.updateTaskpools(task);
        String msg="更新成功";
        Util.writeDataToClient(msg);
        InterfaceAction.updateTaskToPool(task);
		return "success";
		}

		 /**
			 * 
			 * @return
			 * @throws IOException
			 */
		@Action(value = "/queryIdByTask")
		public void queryIdByTask() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String jsionStr = getParnameterByKey("records");

		if (jsionStr == null) {
			return ;
		}
		Map<String, List> refreshMap = new Hashtable<String, List>();
		try {
			TaskMapper taskMapper = getModelMapper();
			
			// 下面两个循环内是解析jsion格式数据，并根据解析的数据查询数据库
			String[][] records = Util.getPatternValuesArray(jsionStr,"\"(\\d+)\":\\{(.*?)\\},", 2);
			for (int i = 0; i < records.length; i++) {
				if (records[i][0] == null || records[i][1] == null) {
					continue;
				}
				String[][] recordPros = Util.getPatternValuesArray(records[i][1],"(.*?):(.*?)[,]+", 2);
				
				Map<String, Object> parasMap = new HashMap<String, Object>();
				for (int j = 0; j < recordPros.length; j++) {
					if (recordPros[j][0] == null || recordPros[j][1] == null) {
						continue;
					}
					parasMap.put(recordPros[j][0], recordPros[j][1]);
				}
				
					task = taskMapper.queryByTask(parasMap);
					if (task != null) {
						List refreshs=new RefreshAction().queryRefreshsByTask(task);
						if(refreshs!=null){
							refreshMap.put(records[i][0], refreshs);
						}

					}
			}
		} finally {
			closeSqlSession();
		}
		
		if (refreshMap != null && refreshMap.size() > 0) {
			writeMapToClient(refreshMap);
			return ;
		}else
		if (refreshMap != null) {
			List<String> opMsg=new ArrayList<String>();
			opMsg.add("该关键词网址对没有云点击记录!");
			refreshMap.put("opMsg",opMsg);
			writeMapToClient(refreshMap);
		}
		return ;
	}
		
		public void writeMapToClient(Map<String, List> hmap) {
			HttpServletResponse response = ServletActionContext.getResponse();

			GZIPOutputStream zos = null;
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			OutputStream os = null;
			try {

				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(hmap);
				oos.flush();
				byte[] data = baos.toByteArray();
				int len = data.length;
				baos.reset();
				zos = new GZIPOutputStream(baos, data.length);
				zos.write(data);
				zos.finish();
				data = baos.toByteArray();
				response.setContentLength(data.length);

				os = response.getOutputStream();
				os.write(data);
				// os.flush();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				closeSqlSession();
				try {
					os.close();
					os=null;
					oos.close();
					baos.close();
					zos.close();
				} catch (Exception ex1) {
				}
			}
		}
		
		private String getRequestParas(HttpServletRequest request) {
			String jsionStr="error";
			
			 InputStream is=null;
			try {
				is = request.getInputStream();
			
			 String dataLenth = request.getHeader("Content-Length");
		     final int len = dataLenth == null ? 0 : Integer.parseInt(dataLenth);
		     // 这里如果返回长度为0，表示服务器繁忙中
		     if (len == 0) {
		         return "error";
		     }
		     byte[] data = new byte[len];
		
		     for (int i = 0; i < len; i++) {
		         data[i] = (byte) is.read();
		     }
		     jsionStr=new String(data,"utf-8");
			} catch (IOException e) {
				e.toString();
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
			return jsionStr;
		}
		
		 /**
			 * 根据任务id查询单个任务信息
			 * 
			 * @return
			 */
		@Action(value = "/findOneTask", results = {
				@Result(name = "success", location = "/editTask"),
				@Result(name = "error", location = "/allTask") })
		public String findOneTaskById() {
			
			HttpServletResponse response=ServletActionContext.getResponse();
        
	        Task task = new Task();
	        task.setTKeyword(getParnameterByKey("tId"));
// task.setTKeyword(getParnameterByKey("tKeyword"));
// task.setTUrl(getParnameterByKey("tUrl"));
// task.setTSEType(getParnameterByKey("tSEType"));
	        task.setTSetClick(Integer.parseInt(getParnameterByKey("tSetClick")));
// task.setTAssignedClick(0);
// task.setTHaveClick(0);
// task.setTPriority(Integer.parseInt(getParnameterByKey("tPriority")));
// task.setTDate(new java.sql.Date(date.getTime()));
// task.setAId(getParnameterByKey("aId"));
	        
        try {
          
            TaskMapper taskMapper = getModelMapper();
            taskMapper.update(task);

        } finally {
        	closeSqlSession();
        }
        String msg="成功";
        Util.writeDataToClient(msg);
		return "success";
		}
		
		
		private Task getNewTask() {
			return new Task();
		}

		/**
		 * 从数据库查询所有
		 * 
		 * @return
		 */
		@Action(value = "/allTask", results = {
				@Result(name = "success", location = "/taskList"),
				@Result(name = "error", location = "/index") })
		public String getAllTasks() {

			try {
				TaskMapper taskMapper = getModelMapper();
				tasks = taskMapper.getAllTasks();
			

			} finally {
				closeSqlSession();
			}
			if (tasks != null && tasks.size() > 0) {

				return "success";
			} else {
				return "error";
			}
		}
		
		/**
		 * 任务高级搜索
		 * 
		 * @return
		 */
		@Action(value = "/taskAdvanSearch", results = {
				@Result(name = "success", location = "/taskAdvanSearchResult"),
				@Result(name = "error", location = "/goToAdSearch") })
		public String advanSearch() {

			try {
// if(task!=null){
// System.out.println("任务高级搜索输入::"+task.getAName());
// System.out.println("任务高级搜索输入::"+task.getAPhone());
// System.out.println("任务高级搜索输入::"+task.getAStatus());
// task.setAStatus(null);
// }
// TaskMapper taskMapper = getModelMapper();
// adSearchResults=taskMapper.advanSearch(task);
				

			} finally {
				closeSqlSession();
			}
			if (adSearchResults != null && adSearchResults.size() > 0) {

				return "success";
			} else {
				return "error";
			}
		}
		
		
	    
		private TaskMapper getModelMapper() {
			return (TaskMapper)getModelMapper(TaskMapper.class);
		}

		public SqlSession getSqlSession() {
			return sqlSession;
		}

		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}

		public List<Task> getTasks() {
			return tasks;
		}

		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}

		public int getAId() {
			return aId;
		}

		public void setAId(int id) {
			aId = id;
		}

// public Auditor[] getAuditorList() {
// return auditorList;
// }
//
// public void setAuditorList(Auditor[] auditorList) {
// this.auditorList = auditorList;
// }
		public static void main(String[] args) {
			TaskAction ua = new TaskAction();
			TaskMapper taskMapper = ua.getModelMapper();
// Task task=taskMapper.getTaskById("1");
// if (task!=null) {
// // task.setAName("修改2");
// ua.setTask(task);
// // ua.editOneTaskById();
//				
// }
			
			 for(int i=0;i<100;i++){    
				 
				 String randString9=Util.getRandomStr(9);
					int randomInt4 = Util.getRandomInt(1,4);
					int randomInt2 = Util.getRandomInt(2);
					
			Task task = new Task();
	        task.setTKeyword(randomInt2==1?"网易网":"新浪微博");
	        task.setTUrl(randomInt2==1?"www.163.com":"www.weibo.com");
	        task.setTSEType(randomInt2==1?"Google Hk":"Baidu");
	        task.setTSetClick(randomInt4);
	        task.setTAssignedClick(randomInt4);
	        task.setTHaveClick(randomInt4);
	        task.setTDate(new java.sql.Date(new Date().getTime()));
	        task.setAId(randomInt4+"");
	        task.setActivated(randomInt2==1?true:false);
	       // System.out.println("添加成功用户"+i);
// taskMapper.insert(task);
			 }
		}

		public List<Status> getStatusList() {
			return statusList;
		}

		public void setStatusList(List<Status> statusList) {
			this.statusList = statusList;
		}

		public List<Task> getAdSearchResults() {
			return adSearchResults;
		}

		public void setAdSearchResults(List<Task> adSearchResults) {
			this.adSearchResults = adSearchResults;
		}
		
}
