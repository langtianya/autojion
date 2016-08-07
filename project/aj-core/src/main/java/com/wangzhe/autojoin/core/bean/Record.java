package com.wangzhe.autojoin.core.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * User: Administrator
 * Date: 13-3-11
 * Time: ����4:45
 */
public class Record implements Serializable {


    private String rId;

    private String aId;    //�û�ID
    private String aName;
    private String uName;
    private String uAgent;
    private String uId;   //������
    private String uuId;
    private String rClick;//����
    private String rMoney;//���
    private Timestamp rDate;   //����
//    public String getUUid(){
//        return uuId;
//    }
//    public void setUUid(String uuid){
//        this.uuId=uuid;
//    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }


    public String getUName(){
        return uName;
    }
    public void  setUName(String uName){
        this.uName=uName;
    }
    public String getAName() {
        return aName;
    }

    public void setAName(String aName) {
        this.aName = aName;
    }

    public String getUAgent() {
        return this.uAgent;
    }

    public void setUAgent(String uAgent) {
        this.uAgent = uAgent;
    }

    public String getRId() {
        return rId;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    public String getAId() {
        return aId;
    }

    public void setAId(String aId) {
        this.aId = aId;
    }

    public String getRClick() {
        return rClick;
    }

    public void setRClick(String rClick) {
        this.rClick = rClick;
    }

    public String getRMoney() {
        return rMoney;
    }

    public void setRMoney(String rMoney) {
        this.rMoney = rMoney;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;

    }

    public Timestamp  getRDate() {
        return rDate;
    }

    public void setRDate(Timestamp  rDate) {

        this.rDate = rDate;
    }
}
