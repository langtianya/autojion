package com.wangzhe.model;

import java.sql.Timestamp;

/**
 * ********************************************************************
 * Module:  Refresh.java
 * Author:  oucq
 * Purpose: Defines the Class Refresh
 * *********************************************************************
 */
//与客户端同步的对象类型，客户端修改了服务端必须修改，相反也一样

public class Refresh implements java.io.Serializable{
    private long rId;
    ///////////
    private long tId;         //任务所属ID
    private java.lang.String rKeyword;   //关键词
    private java.lang.String rUrl;   //网址
    private java.lang.String rSEType;  //搜索引擎类型
    ///////////         这里几个字段 貌似 任务表 里都有了!
    private java.lang.String rIp;          //刷新IP
    private Timestamp rDate;          //刷新日期
    private java.util.Date endDate; //
    private java.lang.String tarea; //刷新地区

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
