package com.wangzhe.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-2-25
 * Time: 下午3:28
 * ID 用户名 密码 所属代理商
 */
public class User implements java.io.Serializable {

    private Integer uId;
    private String uName;
    private String uPwd;
    private Integer uAgents;// 代理商
    private Integer uAuth;// 权限，
    private Date uDate;//reg date
    
    /**代理商列表，增加代理商的时候只要往这里添加，其他任何地方不需要修改，会自动更新**/
    public final static Map<Integer,String> agentsMap=new HashMap<Integer,String>();
    static{
    	agentsMap.put(1, "官方");
    	agentsMap.put(2, "代理商a");
    	agentsMap.put(3, "代理商b");
    	agentsMap.put(4, "代理商c");
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
