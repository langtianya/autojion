package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;

public class Auditor {
    private int key;
//	private String key;
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
//	public String getKey() {
//		return key;
//	}
//	public void setKey(String key) {
//		this.key = key;
//	}

//	 public Auditor(String key, String value) {
//       this.key = key;
//       this.value = value;
//  }
	 
    public Auditor(int key, String value) {
        this.key = key;
        this.value = value;
    }
}