package com.wangzhe.autojoin.wangfw.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangzhe.autojoin.wangfw.server.util.Constant;
import com.wangzhe.autojoin.wangfw.util.FileUtil;

/**
 * @author oucq
 * @version Aug 27, 2016 10:32:24 AM
 */
public class Common {
	public static Logger logger = LoggerFactory.getLogger(Common.class);

	public static void initServerParam(String[] args, ServerParam param) {
		
		String host = null;
		String port = null;
		String webDir = null;
		String logDir = null;
		String tempDir = null;
		String contextPath = null;
		
		if (args.length == 0) {
			port="80";// 80可以不用输入端口
			webDir="src\\main\\webapp";
			logDir="logs";
			tempDir="temp";
			param.setContextPath("/");// ""或"/"可以直接访问根目录
			host = "localhost";
		} else {
			Map<String, String> tempParam = new HashMap<>();
			// 解析传进来的参数
			for (String arg : args) {
				logger.debug(arg);
				if (!StringUtils.isEmpty(arg) && arg.contains("=")) {
					String[] t = arg.trim().split("=");
					tempParam.put(t[0], t[1]);
				}
			}
			port = tempParam.get(Constant.PORT);
			webDir = tempParam.get(Constant.WEB_DIR);
			logDir = tempParam.get(Constant.LOG_DIR);
			tempDir = tempParam.get(Constant.TEMP_DIR);
			host = tempParam.get(Constant.HOST);
		}
		
		 logDir = FileUtil.currentWorkDir + logDir;
		 tempDir = FileUtil.currentWorkDir + tempDir;
		 webDir = FileUtil.currentWorkDir + webDir;
		 
		logger.info("====jetty9 log dir===="+logDir);
		logger.info("====jetty9 temp dir===="+tempDir);
		logger.info("====jetty9 webapp root dir===="+webDir);

		String temp = "x.x";// 占位
		FileUtil.createDirs(logDir + "/" + temp);
		FileUtil.createDirs(tempDir + "/" + temp);
		FileUtil.createDirs(webDir + "/" + temp);
	
		param.setPort(Integer.parseInt(port));
		param.setWebDir(webDir);
		param.setLogDir(logDir);
		param.setTempDir(tempDir);
		param.setContextPath(contextPath);
		param.setHost(host);
	}
	
}
