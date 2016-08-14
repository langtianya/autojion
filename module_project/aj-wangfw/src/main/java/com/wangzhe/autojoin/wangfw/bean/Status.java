package com.wangzhe.autojoin.wangfw.bean;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;

@InterceptorRefs({@InterceptorRef("defaultStack")})
public class Status {
    private int key;
    private String value;
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


    public Status(int key, String value) {
        this.key = key;
        this.value = value;
    }
}