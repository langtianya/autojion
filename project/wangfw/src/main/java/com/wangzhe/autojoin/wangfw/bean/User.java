package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-2-25
 * Time: ����3:28
 * ID �û��� ���� ����������
 */
public class User implements java.io.Serializable {

    private Integer uId;
    private String uName;
    private String uPwd;
    private Integer uAgents;// ������
    private Integer uAuth;// Ȩ�ޣ�
    private Date uDate;//reg date
    
    /**�������б����Ӵ����̵�ʱ��ֻҪ��������ӣ������κεط�����Ҫ�޸ģ����Զ�����**/
    public final static Map<Integer,String> agentsMap=new HashMap<Integer,String>();
    static{
    	agentsMap.put(1, "�ٷ�");
    	agentsMap.put(2, "������a");
    	agentsMap.put(3, "������b");
    	agentsMap.put(4, "������c");
    }
    
    private List<Account> accounts;
	
    public User(){
    	
    }
    
	public Integer getUId() {
		return uId;
	}
	public void setUId(Integer id) {
		uId = id;
	}
	public String getUName() {
		return uName;
	}
	public void setUName(String name) {
		uName = name;
	}
	public String getUPwd() {
		return uPwd;
	}
	public void setUPwd(String pwd) {
		uPwd = pwd;
	}
//	public String getUAgents() {
//		return uAgents;
//	}
//	public void setUAgents(String agents) {
//		uAgents = agents;
//	}
	
	public Integer getUAuth() {
		return uAuth;
	}
	public void setUAuth(Integer auth) {
		uAuth = auth;
	}
	public Date getUDate() {
		return uDate;
	}
	public void setUDate(Date date) {
		uDate = date;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Integer getUAgents() {
		return uAgents;
	}

	public void setUAgents(Integer agents) {
		uAgents = agents;
	}


    
}
