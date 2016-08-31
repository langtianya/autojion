package com.wangzhe.autojoin.wangfw.server;
/**
 * 服务器参数
* @author  oucq
* @version Aug 27, 2016 10:38:35 AM
*/
public class ServerParam {
	 private   String host;
	 private   int port;
	 private   String webDir;
	 private   String logDir;
	 private   String tempDir;
	 private   String contextPath;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getWebDir() {
		return webDir;
	}
	public void setWebDir(String webDir) {
		this.webDir = webDir;
	}
	public String getLogDir() {
		return logDir;
	}
	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}
	public String getTempDir() {
		return tempDir;
	}
	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

}
