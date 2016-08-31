package com.wangzhe.autojoin.wangfw.server.jetty7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangzhe.autojoin.wangfw.server.Common;
import com.wangzhe.autojoin.wangfw.server.ServerParam;

/**
 * 启动内置jetty web容器，运行webapp。
 * 
 * @author oucq
 *
 */
public class JettyRunner {
	public static Logger logger = LoggerFactory.getLogger(JettyWebServer.class);

	// web容器相关参数
	public static final ServerParam param = new ServerParam();

	/**
	 * web入口
	 * 
	 * @param anArgs
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Common.initServerParam(args, param);
		// DOMConfigurator.configure("../aj-wangfw/src/main/resources/log4j.xml");

		unzipSelf();

		new JettyRunner().start();

	}

	

	private JettyWebServer jserver;

	public JettyRunner() {
		jserver = new JettyWebServer(param.getPort(), param.getHost(), param.getTempDir(),
				param.getWebDir(), param.getWebDir(), param.getContextPath());
	}

	public void start() throws Exception {
		jserver.start();
		jserver.join();
	}

	private static void unzipSelf() {
		// 将jar自身解压
		/*
		 * String selfPath = FileUtil.getJarExecPath(JettyApp.class); if
		 * (selfPath.endsWith(".jar")) { // 运行环境 try { logger.info("正在将\n" +
		 * selfPath + "\n解压至\n" + param.get(WEB_DIR)); JarUtils.unJar(selfPath,
		 * param.get(WEB_DIR)); } catch (Exception e) {
		 * logger.error("解压web内容失败!", e); } } else { // IDE环境 param.put(WEB_DIR,
		 * selfPath); } logger.info(selfPath);
		 */
	}
}
