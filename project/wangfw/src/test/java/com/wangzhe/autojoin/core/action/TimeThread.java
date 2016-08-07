package com.wangzhe.action;

import java.util.ArrayList;
import java.util.Set;

import com.wangzhe.common.Util;

public class TimeThread extends Thread {

	
	public void run()
	{
		int count = 0;
		while (true) {
			// 10分钟对比一次日期 1分钟检测一次在线状态
			try {
				Thread.sleep(1000);
				count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 对比日期
			if (count > 0 && count % 600 == 0) {
				
				System.out.println("对比日期任务开始");
				String time = Util.getTime();
				int p0 = time.indexOf(" ");
				int p2 = time.lastIndexOf("-");
				System.out.println(time+" "+InterfaceAction.time_yesterday);
				String timenow = time.substring(p2 + 1, p0);
				int p1 = InterfaceAction.time_yesterday.indexOf(" ");
				int p3 = InterfaceAction.time_yesterday.lastIndexOf("-");
				String timeold = InterfaceAction.time_yesterday
						.substring(p3 + 1, p1);
				System.out.println("初始时间:"+timeold+" 当前时间:"+timenow);
				if (!timeold.equals(timenow)) {
					InterfaceAction.time_yesterday = time;
					for (String type : InterfaceAction.TYPES) {
						InterfaceAction.initClicks(type);
					}
					InterfaceAction.initClicks("");
					InterfaceAction.clickedpools.clear();
					
					System.out.println("根据日期更新清空任务池成功");
				}
				if(timenow!=null&&timenow.equals("14")||timenow.equals("28")){
					RefreshAction.delNDateAgoRecord(14);
				}
				System.out.println("对比日期任务开始结束");
			}
         
			// 检测在线状态
			if (count > 0 && count % 60 == 0) {
				System.out.println("检测在线状态开始");
				System.out.println("当前在线用户数:"+AccountAction.onlineAids.size());
				System.out.println("当前任务池任务数:"+InterfaceAction.getCountByType(""));
				System.out.println("当前百度任务池任务数:"+InterfaceAction.getCountByType("Baidu"));
			
				if (AccountAction.checkoutAids != null
						&& AccountAction.checkoutAids.size() > 0) {
					ArrayList<Integer> delAids=new ArrayList<Integer>();
					for (Integer aid : AccountAction.checkoutAids) {
						// 如果在线用户池没有该用户，则在用户池里剔除该用户 和 IP
						if (!AccountAction.checkonlineAids.contains(aid)) {
							AccountAction.onlineAids.remove(aid);
						 //去除ip池对应的任务
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
						       System.out.println("检测到Uid:"+aid+"用户已离线，成功在IP池退出该用户！");
						       break;
							 }
						   }
						 }						 
						 delAids.add(aid);					
						//任务池里去除该用户的任务
						InterfaceAction.removeTasksByAid(aid,"");
						for(String type:InterfaceAction.TYPES)
						{
							 InterfaceAction.removeTasksByAid(aid,type);
						}
						System.out.println("检测到Uid:"+aid+"用户已离线，成功在用户池退出该用户！");
						
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
						System.out.println("检测到Uid:"+aid+"用户可能已离线，加入到检测观察池！");
					}
				}

				AccountAction.checkonlineAids.clear();
				System.out.println("检测在线状态结束");

			}
			
			//服务器同步检测
			
			
		}
		
		
	}

}
