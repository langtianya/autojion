package com.wangzhe.autojoin.core.model;

import java.sql.Timestamp;

/**
 * ********************************************************************
 * Module:  Refresh.java
 * Author:  oucq
 * Purpose: Defines the Class Refresh
 * *********************************************************************
 */
//��ͻ���ͬ���Ķ������ͣ��ͻ����޸��˷���˱����޸ģ��෴Ҳһ��

public class Refresh implements java.io.Serializable{
    private long rId;
    ///////////
    private long tId;         //��������ID
    private java.lang.String rKeyword;   //�ؼ���
    private java.lang.String rUrl;   //��ַ
    private java.lang.String rSEType;  //������������
    ///////////         ���Ｘ���ֶ� ò�� ����� �ﶼ����!
    private java.lang.String rIp;          //ˢ��IP
    private Timestamp rDate;          //ˢ������
    private java.util.Date endDate; //
    private java.lang.String tarea; //ˢ�µ���

    public long getRId() {
        return rId;
    }

    /**
     * @param newRId
     */
    public void setRId(long newRId) {
        rId = newRId;
    }

    public java.lang.String getRKeyword() {
        return rKeyword;
    }

    /**
     * @param newRKeyword
     */
    public void setArea(java.lang.String newarea) {
    	tarea = newarea;
    }

    public java.lang.String getArea(){
        return tarea;
    }
    
    public void setRKeyword(java.lang.String newRKeyword) {
        rKeyword = newRKeyword;
    }

    public java.lang.String getRUrl() {
        return rUrl;
    }

    /**
     * @param newRUrl
     */
    public void setRUrl(java.lang.String newRUrl) {
        rUrl = newRUrl;
    }

    public java.lang.String getRSEType() {
        return rSEType;
    }

    /**
     * @param newRSEType
     */
    public void setRSEType(java.lang.String newRSEType) {
        rSEType = newRSEType;
    }

    public java.lang.String getRIp() {
        return rIp;
    }

    /**
     * @param newRIp
     */
    public void setRIp(java.lang.String newRIp) {
        rIp = newRIp;
    }

    public Timestamp getRDate() {
        return rDate;
    }

  

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	/**
     * @param newRDate
     */
    public void setRDate(Timestamp newRDate) {
        rDate = newRDate;
    }

        public long getTId() {
                return tId;
        }

        public void setTId(long id) {
                tId = id;
        }
}
