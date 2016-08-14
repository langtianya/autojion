package com.wangzhe.autojoin.wangfw.bean;

import java.util.Hashtable;

/***********************************************************************
 * Module:  Task.java
 * Author:  oucq
 * Purpose: Defines the Class Task
 ***********************************************************************/

//������ͬ���Ķ�������
public class Task implements java.io.Serializable {
	private long tId;
	private String aId; //�û�ID
	private java.lang.String tKeyword; //�ؼ���
	private java.lang.String tUrl; //��ַ
	private java.lang.String tSEType; //������������
	private int tSetClick; //�趨�������
	private int tAssignedClick; //�Ѿ�����ĵ������
	private int tHaveClick; //Ԥ��������
	private java.sql.Date tDate;
	private boolean activated; //����״̬
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
