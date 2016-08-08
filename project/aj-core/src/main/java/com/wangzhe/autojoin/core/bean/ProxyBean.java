package com.wangzhe.autojoin.core.model;

/**
 *
 * @author Seaman
 */
public class ProxyBean {

    private int id;
    private String host;
    private int port;
    private String userID;
    private String pwd;
    private String source;
    private String time_stamp;
    private String serverType;

    public ProxyBean() {
    }

    public ProxyBean(String host, int port, String userID, String pwd, String source) {
        this.host = host;
        this.port = port;
        this.userID = userID;
        this.pwd = pwd;
        this.source = source;
    }

    public ProxyBean(String host, int port, String source) {
        this.host = host;
        this.port = port;
        this.source = source;
    }

    public ProxyBean(int id, String host, int port, String userID, String pwd, String source, String time_stamp) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.userID = userID;
        this.pwd = pwd;
        this.source = source;
        this.time_stamp = time_stamp;
    }

    public ProxyBean(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return "涓绘満鍦板潃" + getHost() + "锛岀鍙�" + getPort() + ",鐢ㄦ埛鍚�" + getUserID() + ",瀵嗙爜" + getPwd();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the time_stamp
     */
    public String getTime_stamp() {
        return time_stamp;
    }

    /**
     * @param time_stamp the time_stamp to set
     */
    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    /**
     * @return the serverType
     */
    public String getServerType() {
        return serverType;
    }

    /**
     * @param serverType the serverType to set
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }
}
