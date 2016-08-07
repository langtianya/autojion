package com.wangzhe.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangzhe.db.DB;
import com.opensymphony.xwork2.ActionSupport;

public  class BaseAction extends ActionSupport {

	protected static Map<String, Object> rememberRequest =null;
	private static final long serialVersionUID = 2327825145250187463L;
	protected static Logger log = null;
	protected SqlSession sqlSession = null;

	protected static Logger getLogger(Class<?> myClass) {
		return LoggerFactory.getLogger(myClass);
	}

	protected void closeSqlSession() {
		if (sqlSession != null) {
			sqlSession.close();
		}
	}

	protected Object getModelMapper(Class<?> myClass) {

		SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
		sqlSession = sqlSessionFactory.openSession();
		return sqlSession.getMapper(myClass);
	}
	protected  String getParnameterByKey(String key) {
			return ServletActionContext.getRequest().getParameter(key);
		}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	protected HttpSession getSession() {
		return getRequest().getSession();
	}
}
