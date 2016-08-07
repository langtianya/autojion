package com.wangzhe.model;

import java.util.Hashtable;

/***********************************************************************
 * Module:  Task.java
 * Author:  oucq
 * Purpose: Defines the Class Task
 ***********************************************************************/

//与服务端同步的对象类型
public class Task implements java.io.Serializable {
	private long tId;
	private String aId; //用户ID
	private java.lang.String tKeyword; //关键词
	private java.lang.String tUrl; //网址
	private java.lang.String tSEType; //搜索引擎类型
	private int tSetClick; //设定点击次数
	private int tAssignedClick; //已经分配的点击次数
	private int tHaveClick; //预设点击次数
	private java.sql.Date tDate;
	private boolean activated; //激活状态
	private String tTime;
	private String tArea;
	public Hashtable<Integer,Integer> timeCount=new Hashtable<Integer,Integer>();
	


	public boolean isActivated() {
		return activated;
	}

	public String getTTime() {
		return tTime;
	}
	

	/** @param newTKeyword */
	public void setTTime(java.lang.String tTime) {
		this.tTime = tTime;
	}

	public String getTArea() {
		return tArea;
	}

	/** @param newTKeyword */
	public void setTArea(java.lang.String tArea) {
		this.tArea = tArea;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public long getTId() {
		return tId;
	}

	/** @param newTId */
	public void setTId(long newTId) {
		tId = newTId;
	}

	public java.lang.String getTKeyword() {
		return tKeyword;
	}

	/** @param newTKeyword */
	public void setTKeyword(java.lang.String newTKeyword) {
		tKeyword = newTKeyword;
	}

	public java.lang.String getTUrl() {
		return tUrl;
	}

	/** @param newTUrl */
	public void setTUrl(java.lang.String newTUrl) {
		tUrl = newTUrl;
	}

	public java.lang.String getTSEType() {
		return tSEType;
	}

	/** @param newTSEType */
	public void setTSEType(java.lang.String newTSEType) {
		tSEType = newTSEType;
	}

	public int getTSetClick() {
		return tSetClick;
	}

	/** @param newTSetClick */
	public void setTSetClick(int newTSetClick) {
		tSetClick = newTSetClick;
	}

	public int getTAssignedClick() {
		return tAssignedClick;
	}

	/** @param newTAssignedClick */
	public void setTAssignedClick(int newTAssignedClick) {
		tAssignedClick = newTAssignedClick;
	}

	public int getTHaveClick() {
		return tHaveClick;
	}

	/** @param newTHaveClick */
	public void setTHaveClick(int newTHaveClick) {
		tHaveClick = newTHaveClick;
	}

	//   public int getTPriority() {
	//      return tPriority;
	//   }
	//
	//   /** @param newTPriority */
	//   public void setTPriority(int newTPriority) {
	//      tPriority = newTPriority;
	//   }

	public java.sql.Date getTDate() {
		return tDate;
	}

	/** @param newTDate */
	public void setTDate(java.sql.Date newTDate) {
		tDate = newTDate;
	}

	public String getAId() {
		return aId;
	}

	/** @param newAId */
	public void setAId(String newAId) {
		aId = newAId;
	}

}
