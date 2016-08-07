package com.wangzhe.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.wangzhe.common.BeanFunction;
import com.wangzhe.common.Util;

/**
 * User: Administrator
 * Date: 13-2-25
 * Time: 下午3:28
 * 普通用户
 */
public class Account implements java.io.Serializable {

    // ID 用户名 密码 手机号 QQ  审核员 状态 充值总点数 剩余点数
    private Integer aId;
    private String aName;
    private String aPwd;
    private String aPhone;
    private String aQq;
    private Integer uId;
    private Integer aStatus;//vip or
    private String aTotal;
    private String aLast;
    private Date aDate;//reg date
    private String aIp;//reg ip
    private boolean online;  //在线状态
    private User userBean;
    
    private Date serchEndDate;//非持久化所用，仅用于搜索
    private Integer aAgent;//非持久化所用，仅用于搜索
    
	

	public Integer getAAgent() {
		return aAgent;
	}
	public void setAAgent(Integer agent) {
		aAgent = agent;
	}
	public Integer getAId() {
		return aId;
	}
	public void setAId(Integer id) {
		aId = id;
	}
	public String getAName() {
		return aName;
	}
	public void setAName(String name) {
		aName = name;
	}
	public String getAPwd() {
		return aPwd;
	}
	public void setAPwd(String pwd) {
		aPwd = pwd;
	}
	public String getAPhone() {
		return aPhone;
	}
	public void setAPhone(String phone) {
		aPhone = phone;
	}
	public String getAQq() {
		return aQq;
	}
	public void setAQq(String qq) {
		aQq = qq;
	}
	public Integer getUId() {
		return uId;
	}
	public void setUId(Integer id) {
		uId = id;
	}
	public Integer getAStatus() {
		return aStatus;
	}
	public void setAStatus(Integer status) {
		aStatus = status;
	}
	public String getATotal() {
		return aTotal;
	}
	public void setATotal(String total) {
		aTotal = total;
	}
	public String getALast() {
		return aLast;
	}
	public void setALast(String last) {
		aLast = last;
	}
	public Date getADate() {
		return aDate;
	}
	public void setADate(Date date) {
		aDate = date;
	}
	public String getAIp() {
		return aIp;
	}
	public void setAIp(String ip) {
		aIp = ip;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
//	public String getAStrDate() {
//		return aStrDate;
//	}
	public void setSercStartDate(String strDate) throws ParseException{
		aDate=Util.parseSqlDate(strDate);
	}
	public void setSercEndDate(String strDate) throws ParseException{
		serchEndDate=Util.parseSqlDate(strDate);
	}
	
	public Date getSerchEndDate() {
		return serchEndDate;
	}
	public void setSerchEndDate(Date serchEndDate) {
		this.serchEndDate = serchEndDate;
	}
	public User getUserBean() {
		return userBean;
	}
	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}
	
    }

