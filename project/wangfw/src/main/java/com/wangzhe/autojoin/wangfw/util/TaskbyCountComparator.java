package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common;

import java.util.Comparator;
import java.util.Hashtable;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.action.AccountAction;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.action.InterfaceAction;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.Task;

public class TaskbyCountComparator implements Comparator{
	private String type;
	private Hashtable<Integer,Integer> clickpools=new Hashtable<Integer,Integer>();
	public TaskbyCountComparator(String type)
	{
		if(type.equals("Baidu"))
		{
			clickpools=InterfaceAction.baiduClickpools;			
		}
		if(type.equals("Google Hk"))
		{
			clickpools=InterfaceAction.googlehkClickpools;			
		}
		if(type.equals("Yahoo Jp"))
		{
			clickpools=InterfaceAction.yahoojpClickpools;			
		}
		if(type.equals("Taobao"))
		{
			clickpools=InterfaceAction.taobaoClickpools;			
		}
	}
	

	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub		
		Task task0=(Task)arg0;
		Task task1=(Task)arg1;
		Object o1=clickpools.get(Integer.parseInt(task0.getAId()));
		Object o2=clickpools.get(Integer.parseInt(task1.getAId()));		
		int a1=0;
		int a2=0;
		if(o1!=null)
		{
			a1=(Integer)o1;
			
		}
		if(o2!=null)
		{
			a2=(Integer)o2;
			
		}
		return  a1-a2;
	}

}
