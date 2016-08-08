package com.wangzhe.autojoin.core.action;

import java.util.ArrayList;
import java.util.Set;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.Util;

public class TimeThread extends Thread {

	
	public void run()
	{
		int count = 0;
		while (true) {
			// 10���ӶԱ�һ������ 1���Ӽ��һ������״̬
			try {
				Thread.sleep(1000);
				count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// �Ա�����
			if (count > 0 && count % 600 == 0) {
				
				System.out.println("�Ա���������ʼ");
				String time = Util.getTime();
				int p0 = time.indexOf(" ");
				int p2 = time.lastIndexOf("-");
				System.out.println(time+" "+InterfaceAction.time_yesterday);
				String timenow = time.substring(p2 + 1, p0);
				int p1 = InterfaceAction.time_yesterday.indexOf(" ");
				int p3 = InterfaceAction.time_yesterday.lastIndexOf("-");
				String timeold = InterfaceAction.time_yesterday
						.substring(p3 + 1, p1);
				System.out.println("��ʼʱ��:"+timeold+" ��ǰʱ��:"+timenow);
				if (!timeold.equals(timenow)) {
					InterfaceAction.time_yesterday = time;
					for (String type : InterfaceAction.TYPES) {
						InterfaceAction.initClicks(type);
					}
					InterfaceAction.initClicks("");
					InterfaceAction.clickedpools.clear();
					
					System.out.println("�������ڸ����������سɹ�");
				}
				if(timenow!=null&&timenow.equals("14")||timenow.equals("28")){
					RefreshAction.delNDateAgoRecord(14);
				}
				System.out.println("�Ա���������ʼ����");
			}
         
			// �������״̬
			if (count > 0 && count % 60 == 0) {
				System.out.println("�������״̬��ʼ");
				System.out.println("��ǰ�����û���:"+AccountAction.onlineAids.size());
				System.out.println("��ǰ�����������:"+InterfaceAction.getCountByType(""));
				System.out.println("��ǰ�ٶ������������:"+InterfaceAction.getCountByType("Baidu"));
			
				if (AccountAction.checkoutAids != null
						&& AccountAction.checkoutAids.size() > 0) {
					ArrayList<Integer> delAids=new ArrayList<Integer>();
					for (Integer aid : AccountAction.checkoutAids) {
						// ��������û���û�и��û��������û������޳����û� �� IP
						if (!AccountAction.checkonlineAids.contains(aid)) {
							AccountAction.onlineAids.remove(aid);
						 //ȥ��ip�ض�Ӧ������
						 Set keys=AccountAction.ipPools.keySet();
						 if(keys!=null&&keys.size()>0)
						 {
						   String[] key=new String[keys.size()];
						   keys.toArray(key);
						   for(String s:key)
						   {					 
							 if(AccountAction.ipPools.get(s)==aid)
							 {
						       AccountAction.ipPools.remove(s);
						       System.out.println("��⵽Uid:"+aid+"�û������ߣ��ɹ���IP���˳����û���");
						       break;
							 }
						   }
						 }						 
						 delAids.add(aid);					
						//�������ȥ�����û�������
						InterfaceAction.removeTasksByAid(aid,"");
						for(String type:InterfaceAction.TYPES)
						{
							 InterfaceAction.removeTasksByAid(aid,type);
						}
						System.out.println("��⵽Uid:"+aid+"�û������ߣ��ɹ����û����˳����û���");
						
					 }				
					 
				 }
			    for(Integer aid :delAids)
			    {
			    	AccountAction.checkoutAids.remove(aid);		    	
			    }
			 }

				Set key = AccountAction.onlineAids.keySet();
				Integer[] keys = new Integer[key.size()];
				key.toArray(keys);

				for (Integer aid : keys) {
					if (!AccountAction.checkonlineAids.contains(aid)) {
						AccountAction.checkoutAids.add(aid);
						System.out.println("��⵽Uid:"+aid+"�û����������ߣ����뵽���۲�أ�");
					}
				}

				AccountAction.checkonlineAids.clear();
				System.out.println("�������״̬����");

			}
			
			//������ͬ�����
			
			
		}
		
		
	}

}
