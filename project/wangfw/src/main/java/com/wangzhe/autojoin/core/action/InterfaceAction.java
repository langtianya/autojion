package com.wangzhe.action;

import com.wangzhe.common.TaskbyCountComparator;
import com.wangzhe.common.Util;
import com.wangzhe.db.DB;
import com.wangzhe.model.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * User: Administrator Date: 13-3-14 Time: ����4:14
 */
public class InterfaceAction extends ActionSupport {
	
	public static final String[] serverUrls=new String[]{"https://s1.seorj.cn/cloudclick/","https://s3.seorj.cn/cloudclick/"};
	

	// ����� ��Ҫϸ��λ�����������������أ����ڷ�������ʱ�Ƚϸ����û��ķ������
	// �����
	public static ArrayList<Task> baiduTaskpools = new ArrayList<Task>();
	public static ArrayList<Task> googlehkTaskpools = new ArrayList<Task>();
	public static ArrayList<Task> yahoojpTaskpools = new ArrayList<Task>();
	public static ArrayList<Task> taobaoTaskpools = new ArrayList<Task>();
	public static ArrayList<Task> taskpools = new ArrayList<Task>();

	// �ѷ���� �û�ID �û��ѷ������
	public static Hashtable<Integer, Integer> baiduClickpools = new Hashtable<Integer, Integer>();
	public static Hashtable<Integer, Integer> googlehkClickpools = new Hashtable<Integer, Integer>();
	public static Hashtable<Integer, Integer> yahoojpClickpools = new Hashtable<Integer, Integer>();
	public static Hashtable<Integer, Integer> taobaoClickpools = new Hashtable<Integer, Integer>();

	// �ѵ������� �û�Id �ѵ��������Id List
	public static Hashtable<Integer, ArrayList> clickedpools = new Hashtable<Integer, ArrayList>();

	// ����
	public static final String[] TYPES = { "Baidu", "Google Hk", "Yahoo Jp",
			"Taobao" };

	// ����ʱ���
	public static String time_yesterday = Util.getTime();

	private SqlSession sqlSession = null;

	// �����˸��¼�¼��ֻ�ܸ����趨ˢ�³�ʼ����������ʶ�� Record��Ϊ�����ֶε�ʵ������
	// public boolean updateRecords(Record record) {
	// SqlSession sqlSession = null;
	//
	// try {
	// SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
	// sqlSession = sqlSessionFactory.openSession();
	// RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
	// recordMapper.editByRId(record);
	// } finally {
	// if (sqlSession != null) {
	// sqlSession.close();
	// }
	// }
	// return true;
	// }
	
	private AccountMapper getAccountMapper() {

		if (sqlSession == null) {
			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			sqlSession = sqlSessionFactory.openSession();
		}
		return sqlSession.getMapper(AccountMapper.class);
	}

	private TaskMapper getTaskMapper() {

		if (sqlSession == null) {
			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			sqlSession = sqlSessionFactory.openSession();
		}
		return sqlSession.getMapper(TaskMapper.class);
	}

	// ��ѯ�˺ŵ�����Ϣ
	@Action(value = "/queryUserMsg", results = {
			@Result(name = "success", location = "/result"),
			@Result(name = "error", location = "/result") })
	public String queryUserMsg() {
		String aId = ServletActionContext.getRequest().getParameter("aId");
		if (aId == null || aId.trim().length() < 1) {
			ServletActionContext.getRequest().setAttribute("msg", "����ȷ��ݣ�");
			return "error";
		}
		SqlSession sqlSession = null;
		String point = "0";
		try {
			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			sqlSession = sqlSessionFactory.openSession();
			AccountMapper accountMapper = sqlSession
					.getMapper(AccountMapper.class);
			point = accountMapper.getPoint(aId);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		ServletActionContext.getRequest().setAttribute("msg",
				"��ѯ�ɹ�������ǰʣ�����Ϊ:" + point + "��");
		return "success";
	}

	private void closeSeesion() {
		if (sqlSession != null) {
			sqlSession.close();
		}
	}

	// ��ѯˢ�¼�¼ Freshδˢ���ֶε�ʵ������
	@Action(value = "/queryFresh", results = {
			@Result(name = "success", location = "/result"),
			@Result(name = "error", location = "/result") })
	public String queryFresh(List<Refresh> records, String starttime,
			String endtime) {
		List<Refresh> fs = new ArrayList<Refresh>();
		// TODO
		ServletActionContext.getRequest().setAttribute("", fs);
		return "success";
	}

	// ��ѯ�ѵ������
	@Action(value = "/loadCount")
	public void loadCount() {
		String aId = ServletActionContext.getRequest().getParameter("aId");
		String tKeyword = ServletActionContext.getRequest().getParameter(
				"tKeyword");
		String tUrl = ServletActionContext.getRequest().getParameter("tUrl");
		String tSEType = ServletActionContext.getRequest().getParameter(
				"tSEType");
		if (aId == null || aId.trim().length() < 1) {
			Util.writeDataToClient("����ȷ��ݣ�");			
			return ;
		}
		if (tKeyword == null || tKeyword.trim().length() < 1) {
			Util.writeDataToClient("����ȷ��ݣ�");
			return ;
		}
		if (tUrl == null || tUrl.trim().length() < 1) {
			Util.writeDataToClient("����ȷ��ݣ�");
			return ;
		}
		if (tSEType == null || tSEType.trim().length() < 1) {
			Util.writeDataToClient("����ȷ��ݣ�");
			return ;
		}
		SqlSession sqlSession = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aId", aId);
		map.put("tKeyword", tKeyword);
		map.put("tUrl", tUrl);
		map.put("tSEType", tSEType);
		try {
			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			sqlSession = sqlSessionFactory.openSession();
			TaskMapper taskMapper = sqlSession.getMapper(TaskMapper.class);
			String tId = taskMapper.getTaskId(map);
			if (tId != null) {

				RefreshMapper refreshMapper = sqlSession
						.getMapper(RefreshMapper.class);
				Integer size = refreshMapper.getRefreshSize(tId);
				if (size != null) {
					Util.writeDataToClient(size + "");
					return ;
				}
			}
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		Util.writeDataToClient("��ѯʧ��!");
		return ;
	}

	// ��½�ӿڣ��ں�̨���ݿ���֤�û���������
	@Action(value = "/loginverify", results = {
			@Result(name = "success", location = "/userList"),
			@Result(name = "error", location = "/index") })
	public boolean loginverify(String username, String password) {
		return true;
	}

	// �۳�����
	@Action(value = "/desPoint")
	public void desPoint() {
		String aId = ServletActionContext.getRequest().getParameter("aId");
		String userId = ServletActionContext.getRequest()
				.getParameter("userId");
		String tId = ServletActionContext.getRequest().getParameter("tId");
		String des = ServletActionContext.getRequest().getParameter("desflag");
		if (aId == null || userId==null||tId==null||des==null) {
			Util.writeDataToClient("ȱ��������������Ժ����ԣ�");
			return ;
		}
		//�۵�
		if (des != null && des.equals("1")) {
			SqlSession sqlSession = null;
			try {
				SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
				sqlSession = sqlSessionFactory.openSession();
				AccountMapper accountMapper = sqlSession
						.getMapper(AccountMapper.class);
				String point = accountMapper.getPoint(aId);
				if (point != null && StringUtils.isNumeric(point)) {

					Integer pin = 0;
					try {
						pin = Integer.valueOf(point);
					} catch (Exception e) {
						pin = 0;
					}
					if (pin > 0) {
						accountMapper.desPoint(aId);
					}
				}
			} finally {
				if (sqlSession != null) {
					sqlSession.close();
				}
			}
			Util.writeDataToClient("�۵�ɹ�!");
		}
		// ������ӵ�����ID��ӵ������
		if (userId != null && tId != null) {
			
			ArrayList list = new ArrayList();
			Object obj = clickedpools.get(Integer.parseInt(userId));
			if (obj != null) {
				list = (ArrayList) obj;
			}
			list.add(Long.parseLong(tId));
			clickedpools.put(Integer.parseInt(userId), list);
		}
		//�������ʱ������������
		int timeindex=Util.getIndex()+1;
		Task task=null;
		for(String type:TYPES)
		{
			task=findTaskById(type, Long.parseLong(tId));
			if (task != null) {
				break;
			}
		}
		if (task != null) {
			Object obj = task.timeCount.get(timeindex);
			if (obj != null) {
				int haveCount = (Integer) obj;
				task.timeCount.put(timeindex, haveCount + 1);
			} else {
				task.timeCount.put(timeindex, 1);
			}
		}
		return ;
	}

	// ��ӵ���
	@Action(value = "/inscutPoint")
	public void inscutPoint() {
		String aId = ServletActionContext.getRequest().getParameter("aId");
		String point_str = ServletActionContext.getRequest().getParameter(
				"point");
		if (aId == null) {
			Util.writeDataToClient("ȱ��������������Ժ����ԣ�");
			return ;
		}
		if (point_str == null || !StringUtils.isNumeric(point_str)) {
			Util.writeDataToClient(" �ύ�������Ϸ�");
			return ;
		}
		SqlSession sqlSession = null;
		try {
			SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
			sqlSession = sqlSessionFactory.openSession();
			AccountMapper accountMapper = sqlSession
					.getMapper(AccountMapper.class);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aId", aId);
			map.put("point", point_str);
			accountMapper.InscutPoint(map);
			Util.writeDataToClient("��ֵ�ɹ���");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}	
		return ;
	}

	@Action(value = "/loadTasks")
	// �ͻ������������� userId�û�ID Ip�û�Ip alast �û�ʣ����� ��Ҫ�������ֶ� Account aId,aIp Task
	// aId,activated,tAssignedClick
	public void loadTasks() {
		String userIdsrc = ServletActionContext.getRequest().getParameter(
				"userId");

		String alastsrc = ServletActionContext.getRequest().getParameter(
				"alast");
		
		String area = ServletActionContext.getRequest().getParameter(
		"area");
		
		int timeindex=Util.getIndex();
		
		if (userIdsrc == null || alastsrc == null||area==null) {
			Util.writeDataToClient("ȱ��������������Ժ����ԣ�");
			return;
		}
		int userId = Integer.parseInt(userIdsrc);
		int alast = Integer.parseInt(alastsrc);

		ArrayList res = new ArrayList();
		try {

			// ����û�ʣ��������㣬�򽫸��û���������ӵ������
			if (alast > 0) {
				// ���Ҹ��û����ݿ������е������¼
				List<Task> tasks_userId = getActiveTasks(userId);
				int alast0 = alast;
				int insertCount = 0;

				// ���������鿴�Ƿ����и��û��������¼�����û������ӵ������
				for (Task task : tasks_userId) {
					if (alast0 < 1) {
						break;
					}

					// ������и�������ȡ�����������
					boolean isExisted = false;
					for (Task taskpool : taskpools) {
						if (taskpool.getTId() == task.getTId()) {
							taskpool.setActivated(false);
							isExisted = true;
							break;
						}
					}

					// ��������û�и���������ӵ��������
					if (!isExisted) {

						// Ԥ�۵�
						if (alast0 >=task.getTSetClick()) {
							task.setTHaveClick(task.getTSetClick());
							alast0 -=task.getTSetClick();
						} 
						else 
						{
							task.setTHaveClick(alast0);
							alast0 = 0;
						}

						// ���������
						taskpools.add(task);

						if (task.getTSEType().equals("Baidu")) {
							baiduTaskpools.add(task);
						}
						if (task.getTSEType().equals("Google Hk")) {
							googlehkTaskpools.add(task);
						}
						if (task.getTSEType().equals("Yahoo Jp")) {
							yahoojpTaskpools.add(task);
						}
						if (task.getTSEType().equals("Taobao")) {
							taobaoTaskpools.add(task);
						}
						insertCount++;
					}

				}
				//System.out.println("Uid:" + userId + " ��ӽ����������:" + insertCount);

			}



			// ������ذ����������С����
			Collections
					.sort(baiduTaskpools, new TaskbyCountComparator("Baidu"));
			Collections.sort(googlehkTaskpools, new TaskbyCountComparator(
					"Google Hk"));
			Collections.sort(yahoojpTaskpools, new TaskbyCountComparator(
					"Yahoo Jp"));
			Collections.sort(taobaoTaskpools,
					new TaskbyCountComparator("Taobo"));

			// ���Բ鿴������
//			System.out.println("�ٶ�����ذ��ѷ������������:");
//			for (int i = 0; i < baiduTaskpools.size(); i++) {
//				System.out.println("Baidu:"
//						+ baiduTaskpools.get(i).getTAssignedClick());
//
//			}
			// for (int i = 0; i < googlehkTaskpools.size(); i++) {
			// System.out.println("Google:"
			// + googlehkTaskpools.get(i).getTAssignedClick());
			//
			// }
			// for (int i = 0; i < yahoojpTaskpools.size(); i++) {
			// System.out.println("Yahoojp:"
			// + yahoojpTaskpools.get(i).getTAssignedClick());
			//
			// }
			// for (int i = 0; i < taobaoTaskpools.size(); i++) {
			// System.out.println("Taobao:"
			// + taobaoTaskpools.get(i).getTAssignedClick());
			//
			// }

			// Ϊ�û���ÿ�������������������
			for (String type : TYPES) {
				sendTaskByType(res, type, userId,area,timeindex);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSeesion();
		}

		//System.out.println("Uid:" + userId + " ������ؼ�����������:" + res.size());

		if (res != null && res.size() > 0) {
			writeListToClient(res);
		}

		return;
	}

	@Action(value = "/loadTasksbyAid")
	// �ͻ������������� userId�û�ID Ip�û�Ip alast �û�ʣ����� ��Ҫ�������ֶ� Account aId,aIp Task
	// aId,activated,tAssignedClick
	public void loadTasksByAid() {
		String userIdsrc = ServletActionContext.getRequest().getParameter(
				"userId");
		if (userIdsrc == null) {
			Util.writeDataToClient("ȱ��������������Ժ����ԣ�");
			return;
		}
		Integer userId = Integer.parseInt(userIdsrc);
		List<Task> tasks = getTaskMapper().getTasksByUserId(userId);
		if (tasks != null && tasks.size() > 0) {
			writeListToClient(tasks);
		}
		return;
	}

	// ��ȡ�û��ύ������
	public List<Task> getActiveTasks(Integer userId) {

		// ���Ҹ��û��������������
		List<Task> tasks = getTaskMapper().getTasksByUserId(userId);

		// System.out.println("getActiveTasks_before:" + tasks.size());

		// δ������ɵ��������
		if (tasks != null && tasks.size() > 0) {

			List<Task> deltasks = new ArrayList<Task>();
			for (Task tasknew : tasks) {

				if (tasknew.getTHaveClick() == tasknew.getTSetClick()) {

					deltasks.add(tasknew);
				}

			}
			if (deltasks.size() > 0) {
				for (Task deltask : deltasks) {
					tasks.remove(deltask);
				}
			}

		}

		return tasks;

	}
	
	//��������ID��ѯ���������
	public Task findTaskById(String type,long taskId)
	{
		ArrayList<Task> pools = null;
		if (type.equals("Baidu")) {
			pools = baiduTaskpools;
		}
		if (type.equals("Google Hk")) {
			pools = googlehkTaskpools;			
		}
		if (type.equals("Yahoo Jp")) {
			pools = yahoojpTaskpools;
		}
		if (type.equals("Taobao")) {
			pools = taobaoTaskpools;			
		}
		for(Task task:pools)
		{
			if(task.getTId()==taskId)
			{
				return task;
			}
		}
		return null;

		
	}

	// Ϊָ���������������ͷ�������
	public void sendTaskByType(ArrayList res, String type, Integer userId,String area,int timeindex) {
		//ʱ��Ҫ�����һλ		
		timeindex+=1;	
		ArrayList<Task> pools = null;
		Hashtable<Integer, Integer> clickpools = null;
		if (type.equals("Baidu")) {
			pools = baiduTaskpools;
			clickpools = InterfaceAction.baiduClickpools;
		}
		if (type.equals("Google Hk")) {
			pools = googlehkTaskpools;
			clickpools = InterfaceAction.googlehkClickpools;
		}
		if (type.equals("Yahoo Jp")) {
			pools = yahoojpTaskpools;
			clickpools = InterfaceAction.yahoojpClickpools;
		}
		if (type.equals("Taobao")) {
			pools = taobaoTaskpools;
			clickpools = InterfaceAction.taobaoClickpools;
		}
		if (pools != null && pools.size() > 0) {
			// ����������������ȡ���������������500������
			for (int i = 0; i < pools.size(); i++) {
			
				if(res.size()>499)
				{
					return;					
				}				
				Task task = pools.get(i);
				// ������Լ������� �� Ԥ��������Ѵﵽ�ѷ������ �������򲻷���
				if (task.isActivated()
						|| Integer.parseInt(task.getAId()) == userId
						|| task.getTAssignedClick() >= task.getTHaveClick()) {				
					continue;
				}
				// ������������û��ѵ����������������
				if (clickedpools.containsKey(userId)) {
					Object obj = clickedpools.get(userId);
					ArrayList list = new ArrayList();
					if (obj != null) {
						list = (ArrayList) obj;
					}
					if (list.contains(task.getTId())) {
//						System.out
//								.println("Uid:" + userId + "�û�������һ�������ѵ��������ID��"+task.getTId());
						continue;
					}
				}
				//������������˵�����������Ҫ�ж�
				if(task.getTArea()!=null&&!task.getTArea().equals("")&&!task.getTArea().contains(area))
				{
					System.out
					.println("Uid:" + task.getAId() + "�û�ѡ��ĵ���:"+task.getTArea()+"������һ�������ϵĵ���:"+area+" Uid:"+userId);
					continue;
				}
				//�������������ʱ��ֶ�����Ҫ�ж�
				if(task.getTTime()!=null&&!task.getTTime().equals(""))
				{
					int timeCount=Util.getTimeSetCount(timeindex,task.getTTime());
					//System.out.println("timeindex:"+timeindex+" time:"+task.getTTime());
					int haveCount=0;
					Object obj=task.timeCount.get(timeindex);
					if(obj!=null)
					{
						 haveCount=(Integer)obj;	
					}					
					if(haveCount>=timeCount)
					{
					    //System.out.println("Uid:" + task.getAId() + "�û�������һ����ǰʱ��:"+timeindex+" �ѵ������:"+haveCount+" �������ô���:"+timeCount+"������");
					    continue;
					}		
						    				
				}
								
				res.add(task);
				// �ѷ������+1
				task.setTAssignedClick(task.getTAssignedClick() + 1);
				// ���ѷ���������ｫ���û�����������������
				int key = Integer.parseInt(task.getAId());
				int value = 0;
				Object obj = clickpools.get(key);
				if (obj != null) {
					value = (Integer) obj;
				}
				clickpools.put(key, value + 1);
			}
		}

	}

	// ��ʼ������������
	public static void initClicks(String type) {
		ArrayList<Task> pools = null;
		Hashtable<Integer, Integer> clickpools = null;
		if (type.equals("Baidu")) {
			pools = baiduTaskpools;
			clickpools = InterfaceAction.baiduClickpools;
		}
		if (type.equals("Google Hk")) {
			pools = googlehkTaskpools;
			clickpools = InterfaceAction.googlehkClickpools;
		}
		if (type.equals("Yahoo Jp")) {
			pools = yahoojpTaskpools;
			clickpools = InterfaceAction.yahoojpClickpools;
		}
		if (type.equals("Taobao")) {
			pools = taobaoTaskpools;
			clickpools = InterfaceAction.taobaoClickpools;
		}
		if (type.equals("")) {
			pools = taskpools;	
		}
		for (Task task : pools) {
			task.setTAssignedClick(0);
			task.setActivated(false);
		}
		if(clickpools!=null)
		{
		  Set sets = clickpools.keySet();
		  Integer[] keys = new Integer[sets.size()];
		  sets.toArray(keys);	
		  for (Integer key : keys) {
			clickpools.put(key, 0);
		  }
		}
	}

	// ����������޳���Ӧ�û��������¼ ͨ����������ļ���״̬
	public static void removeTasksByAid(Integer Aid, String type) {
		ArrayList<Task> pools = null;
		if (type.equals("")) {
			pools = taskpools;
		}
		if (type.equals("Baidu")) {
			pools = baiduTaskpools;
		}
		if (type.equals("Google Hk")) {
			pools = googlehkTaskpools;
		}
		if (type.equals("Yahoo Jp")) {
			pools = yahoojpTaskpools;
		}
		if (type.equals("Taobao")) {
			pools = taobaoTaskpools;
		}
		int delcount = 0;
		for (Task task : pools) {
			if (Integer.parseInt(task.getAId()) == Aid) {
				delcount++;
				task.setActivated(true);
			}
		}
//		System.out.println(type + "�������ɹ�ɾ����UID:" + Aid + " " + delcount
//				+ "������");
		return;

	}

	// ��������ʱ��ͬʱ��ӵ������
	public static boolean addTaskToPool(Task task) {

		if (task == null) {
			return false;
		}

		String tSEType = task.getTSEType();
       // System.out.println("��ӵ�����ص�����:"+tSEType);
		if (tSEType.equals("Baidu")) {
			baiduTaskpools.add(task);
		} else if (tSEType.equals("Google Hk")) {
			googlehkTaskpools.add(task);
		} else if (tSEType.equals("Yahoo Jp")) {
			yahoojpTaskpools.add(task);
		} else if (tSEType.equals("Taobao")) {
			taobaoTaskpools.add(task);
		} else {
			return false;
		}
		taskpools.add(task);
		return true;
	}

	// ɾ�������ʱ��ͬʱɾ������صĶ�Ӧ����
	public static boolean delTaskFromPool(Task task) {

		if (task == null) {
			return false;
		}

		String tSEType = task.getTSEType();

		if (tSEType.equals("Baidu")) {
			delTaskFromPool(task, baiduTaskpools);
		} else if (tSEType.equals("Google Hk")) {
			delTaskFromPool(task, googlehkTaskpools);
		} else if (tSEType.equals("Yahoo Jp")) {
			delTaskFromPool(task, yahoojpTaskpools);
		} else if (tSEType.equals("Taobao")) {
			delTaskFromPool(task, taobaoTaskpools);
		} else {
			return false;
		}
		return true;
	}

	// ���������ʱ��ͬʱ��������صĶ�Ӧ����
	public static boolean updateTaskToPool(Task task) {

		if (task == null) {
			return false;
		}

		String tSEType = task.getTSEType();

		if (tSEType.equals("Baidu")) {
			updateTaskToPool(task, baiduTaskpools);
		} else if (tSEType.equals("Google Hk")) {
			updateTaskToPool(task, googlehkTaskpools);
		} else if (tSEType.equals("Yahoo Jp")) {
			updateTaskToPool(task, yahoojpTaskpools);
		} else if (tSEType.equals("Taobao")) {
			updateTaskToPool(task, taobaoTaskpools);
		} else {
			return false;
		}
		return true;
	}

	private static void delTaskFromPool(Task task, ArrayList<Task> TypePool) {

		List<Task> deltasks = new ArrayList<Task>();

		// ���ݲ�ͬ����ɾ��
		for (Task poolTask : TypePool) {
			if (poolTask.getAId().equals(task.getAId())
					&& poolTask.getTKeyword().equals(task.getTKeyword())
					&& poolTask.getTUrl().equals(task.getTUrl())
					&& poolTask.getTSEType().equals(task.getTSEType())) {
				deltasks.add(poolTask);
			}
		}
		if (deltasks.size() > 0) {
			for (Task deltask : deltasks) {
				TypePool.remove(deltask);
			}
		}

		// �����б���ɾ��
		deltasks = new ArrayList<Task>();

		for (Task poolTask : taskpools) {
			if (poolTask.getAId().equals(task.getAId())
					&& poolTask.getTKeyword().equals(task.getTKeyword())
					&& poolTask.getTUrl().equals(task.getTUrl())
					&& poolTask.getTSEType().equals(task.getTSEType())) {
				deltasks.add(poolTask);
			}
		}
		if (deltasks.size() > 0) {
			for (Task deltask : deltasks) {
				taskpools.remove(deltask);
			}
		}
	}

	private static void updateTaskToPool(Task task, ArrayList<Task> TypePool) {

		// List<Task> deltasks = new ArrayList<Task>();

		// ���ݲ�ͬ����ɾ��
		for (Task poolTask : TypePool) {
			if (poolTask.getAId().equals(task.getAId())
					&& poolTask.getTKeyword().equals(task.getTKeyword())
					&& poolTask.getTUrl().equals(task.getTUrl())
					&& poolTask.getTSEType().equals(task.getTSEType())) {
				poolTask.setTSetClick(task.getTSetClick());
			}
		}
		// if (deltasks.size() > 0) {
		// for (Task deltask : deltasks) {
		// TypePool.remove(deltask);
		// }
		// }

		// �����б���ɾ��
		// deltasks= new ArrayList<Task>();

		for (Task poolTask : taskpools) {
			if (poolTask.getAId().equals(task.getAId())
					&& poolTask.getTKeyword().equals(task.getTKeyword())
					&& poolTask.getTUrl().equals(task.getTUrl())
					&& poolTask.getTSEType().equals(task.getTSEType())) {
				poolTask.setTSetClick(task.getTSetClick());
			}
		}
		// if (deltasks.size() > 0) {
		// for (Task deltask : deltasks) {
		// taskpools.remove(deltask);
		// }
		// }
	}

	public static void updateTaskpools(Task task) {
		ArrayList<Task> taskpools = null;
		if (task.getTSEType().equals("Baidu")) {
			taskpools = baiduTaskpools;
		}
		if (task.getTSEType().equals("Google Hk")) {
			taskpools = googlehkTaskpools;
		}
		if (task.getTSEType().equals("Yahoo Jp")) {
			taskpools = yahoojpTaskpools;
		}
		if (task.getTSEType().equals("Taobao")) {
			taskpools = taobaoTaskpools;
		}
		for (Task task0 : taskpools) {
			if (task0.getAId().equals(task.getAId())
					&& task0.getTKeyword().equals(task.getTKeyword())
					&& task0.getTUrl().equals(task.getTUrl())
					&& task0.getTSEType().equals(task0.getTSEType())) {
				task0.setTSetClick(task.getTSetClick());
				break;

			}
		}

	}

	public void writeListToClient(List hmap) {
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
			response.setContentType("zip/rar");

			os = response.getOutputStream();
			os.write(data);
			os.flush();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

			try {
				os.close();
				os = null;
				oos.close();
				baos.close();
				zos.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
	}

	/**
	 * �û��˳�
	 * 
	 * @return
	 */
	@Action(value = "/checkonline")
	public void accountLogout() {

		HttpServletResponse response = ServletActionContext.getResponse();

		String aIdsrc = ServletActionContext.getRequest().getParameter("aId");
		String alastsrc = ServletActionContext.getRequest().getParameter(
				"alast");
		String version = ServletActionContext.getRequest().getParameter(
		"version");
		String clientIP = ServletActionContext.getRequest().getRemoteAddr();

		if (aIdsrc == null || alastsrc == null) {
			return ;
		}

		int aid = Integer.parseInt(aIdsrc);
		int alast = Integer.parseInt(alastsrc);
		if (!AccountAction.checkonlineAids.contains(aid)) {
			AccountAction.checkonlineAids.add(aid);
		}
		if (AccountAction.checkoutAids.contains(aid)) {
			AccountAction.checkoutAids.remove(aid);
		}

		// ���ܷ�����������ʼ��������ر���ʼ�������������Ƿ����û��������¼
		checkTasks(aid, alast, clientIP);
        String res="���ɹ���";
		if(version!=null&&!version.equals(AccountAction.version))
		{		
			res+=" update";
		}
		Util.writeDataToClient(res);
		return ;

	}
	
	/**
	 * �����û�������ͬ�� ��û��ʵ��
	 * 
	 * @return
	 */
	@Action(value = "/updateServerRequest")
	public void updateServerRequest() {
	    for(String url:serverUrls)
	    {
	    	if(url.equals(AccountAction.localHost))
	    	{
	    		continue;
	    	}
	    		    	
	    }
		return ;

	}
	
	@Action(value = "/updateServerRespone")
	public void updateServerRespone() {
	    for(String url:serverUrls)
	    {
	    	if(url.equals(AccountAction.localHost))
	    	{
	    		continue;
	    	}
	    	
    	
	    	
	    }
		return ;

	}

	public void checkTasks(Integer aID, Integer alast, String ip) {

		if (!AccountAction.onlineAids.containsKey(aID)) {
			AccountAction.onlineAids.put(aID, 0);
		}
		if (!AccountAction.ipPools.containsKey(ip)) {
			AccountAction.ipPools.put(ip, aID);
		}

		boolean isExisted = false;

		for (Task taskpool : taskpools) {
			if (Integer.parseInt(taskpool.getAId()) == aID) {
				isExisted = true;
				break;
			}
		}
		if (isExisted) {
			return;
		}
		if (alast > 0) {
			// ���Ҹ��û����ݿ������е������¼
			List<Task> tasks_userId = getActiveTasks(aID);

			// System.out.println("getActiveTasks_after:" +
			// tasks_userId.size());

			int alast0 = alast;

			// ���������鿴�Ƿ����и��û��������¼�����û������ӵ������
			for (Task task : tasks_userId) {
				if (alast0 < 1) {
					break;
				}

				// Ԥ�۵�
				if (alast0 > task.getTSetClick()
						|| alast0 == task.getTSetClick()) {
					task.setTHaveClick(task.getTSetClick());
					alast0 = alast0 - task.getTSetClick();
				} else if (alast0 < task.getTSetClick()) {
					task.setTHaveClick(alast0);
					alast0 = 0;
				}

				// ���������
				taskpools.add(task);

				if (task.getTSEType().equals("Baidu")) {
					baiduTaskpools.add(task);
				}
				if (task.getTSEType().equals("Google Hk")) {
					googlehkTaskpools.add(task);
				}
				if (task.getTSEType().equals("Yahoo Jp")) {
					yahoojpTaskpools.add(task);
				}
				if (task.getTSEType().equals("Taobao")) {
					taobaoTaskpools.add(task);
				}
				// System.out.println("����ؼ�����һ������");

			}

		}

	}

	public static int getCountByType(String type) {
		ArrayList<Task> taskpools = null;
		if (type.equals("")) {
			taskpools = InterfaceAction.taskpools;
		}
		if (type.equals("Baidu")) {
			taskpools = baiduTaskpools;
		}
		if (type.equals("Google Hk")) {
			taskpools = googlehkTaskpools;
		}
		if (type.equals("Yahoo Jp")) {
			taskpools = yahoojpTaskpools;
		}
		if (type.equals("Taobao")) {
			taskpools = taobaoTaskpools;
		}
		int count = 0;
		for (Task task : taskpools) {
			if (!task.isActivated()) {
				count++;
			}
		}
		return count;

	}

	public static void DesCountByTId(String type, String[] Tids) {
		ArrayList<Task> taskpools = null;
		if (type.equals("")) {
			taskpools = InterfaceAction.taskpools;
		}
		if (type.equals("Baidu")) {
			taskpools = baiduTaskpools;
		}
		if (type.equals("Google Hk")) {
			taskpools = googlehkTaskpools;
		}
		if (type.equals("Yahoo Jp")) {
			taskpools = yahoojpTaskpools;
		}
		if (type.equals("Taobao")) {
			taskpools = taobaoTaskpools;
		}
		Long[] Ids = new Long[Tids.length];
		for (int i = 0; i < Tids.length; i++) {
			Ids[i] = Long.parseLong(Tids[i]);

		}
		int count = 0;
		for (Task task : taskpools) {
			for (Long Id : Ids) {
				if (task.getTId() == Id) {
					count++;
					task.setTAssignedClick(task.getTAssignedClick() - 1);
					break;
				}
			}
		}
		//System.out.println("�ɹ�����" + count + "������ĵ������.");
		return;

	}
	
	

	

}
