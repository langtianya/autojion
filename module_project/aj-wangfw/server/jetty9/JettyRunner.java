//
//  ========================================================================
//  Copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package com.wangzhe.autojoin.wangfw.server.jetty9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.MetaData;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;

import com.wangzhe.autojoin.wangfw.init.WebAppStrarUpInitializer;
import com.wangzhe.autojoin.wangfw.server.Common;
import com.wangzhe.autojoin.wangfw.server.ServerParam;
import com.wangzhe.autojoin.wangfw.server.jetty9.test.DateServlet;
import com.wangzhe.autojoin.wangfw.util.DateUtil;
import com.wangzhe.autojoin.wangfw.util.ObjectUtil;
import com.wangzhe.autojoin.wangfw.util.Pic2Char;

/**
 * Example of using JSP's with embedded jetty and not requiring all of the
 * overhead of a WebAppContext
 */
public class JettyRunner {
	private static final Logger LOG = LoggerFactory.getLogger(JettyRunner.class);

	// web容器相关参数
	private static final ServerParam param = new ServerParam();

	public static void main(String[] args) throws Exception {
		printFrameWorkLog();
		Common.initServerParam(args, param);
		LoggingUtil.config();
		Log.setLog(new JavaUtilLog());
		JettyRunner main = new JettyRunner();
		main.start();
		printStartSuccess();
		main.waitForInterrupt();
		
	}

	private static void printFrameWorkLog() throws IOException {
		String fileStr = new File("").getAbsolutePath()+"\\src\\main\\webapp\\pc\\aj.wangfw\\img\\wang_font.png";
//		LOG.info(new File("").getAbsolutePath());
//		String path2=JettyRunner.class.getResource(fileStr).getPath();
//		Assert.assertNotNull(path2);
		
//		String path=FileUtil.getFilePath(fileStr);
		Pic2Char.startPic2Char(fileStr);
	}
	private static void printStartSuccess() throws IOException {
		String fileStr = new File("").getAbsolutePath()+"\\src\\main\\webapp\\pc\\aj.wangfw\\img\\startSuccess.png";
		System.out.println();
		Pic2Char.startPic2Char(fileStr);
	}

	private Server server;
	private URI serverURI;

	public JettyRunner() {
	}

	public URI getServerURI() {
		return serverURI;
	}

	public void start() throws Exception {
		server = new Server(createThreadPool());
		NetworkConnector connector = createConnector();
		server.addConnector(connector);

		URI baseUri = getWebRootResourceUri();
		// Set JSP to use Standard JavaC always
		System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");

		server.setHandler(getWebAppContext(baseUri, getScratchDir()));
		server.addConnector(connector);
		server.setStopAtShutdown(true);
		// Start Server
		server.start();

		// Show server state
		// if (LOG.isDebugEnabled())
		String dump = server.dump();// 输出jetty部分dump信息
		LOG.info(dump.substring(dump.indexOf("> sun.misc.Launcher$AppClassLoader")));
		// }

		this.serverURI = getServerUri(connector);
	}

	private NetworkConnector createConnector() {
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(param.getPort());
		connector.setHost(param.getHost());
		return connector;
	}

	private URI getWebRootResourceUri() throws FileNotFoundException, URISyntaxException {
		URL indexUri = this.getClass().getResource("" + param.getWebDir());
		if (indexUri == null) {
			return null;
		}
		// Points to wherever /webroot/ (the resource) is
		return indexUri.toURI();
	}

	/**
	 * Establish Scratch directory for the servlet context (used by JSP
	 * compilation)
	 */
	private File getScratchDir() throws IOException {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

		if (!scratchDir.exists()) {
			if (!scratchDir.mkdirs()) {
				throw new IOException("Unable to create scratch directory: " + scratchDir);
			}
		}
		return scratchDir;
	}

	/**
	 * Setup the basic application "context" for this application at "/" This is
	 * also known as the handler tree (in jetty speak)
	 */
	private HandlerCollection getWebAppContext(URI baseUri, File scratchDir) {
		WebAppContext webappCtx = new WebAppContext();
		webappCtx.setContextPath("/");
		// webappCtx.setDescriptor(param.getWebDir()+"\\WEB-INF\\web.xml");
		// webappCtx.setWar(param.getWebDir());//for war
		webappCtx.setDisplayName("auto join");
		webappCtx.setTempDirectory(new File(param.getTempDir()));
		webappCtx.setWelcomeFiles(new String[] { param.getWebDir() + "\\index.html" });// 欢迎页面
		webappCtx.setConfigurationDiscovered(true);
		webappCtx.setParentLoaderPriority(true);
		RequestLogHandler logHandler = new RequestLogHandler();
		logHandler.setRequestLog(createRequestLog());

		webappCtx.setAttribute("javax.servlet.context.tempdir", scratchDir);
		webappCtx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/.*taglibs.*\\.jar$");
		if (ObjectUtil.isNotEmpty(baseUri)) {
			webappCtx.setResourceBase(baseUri.toASCIIString());// for dir
		} else {
			webappCtx.setResourceBase(param.getWebDir());
		}

		webappCtx.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
		webappCtx.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
		webappCtx.addBean(new ServletContainerInitializersStarter(webappCtx), true);
		webappCtx.setClassLoader(getUrlClassLoader());

		webappCtx.addServlet(jspServletHolder(), "*.jsp");
		// Add Application Servlets
		webappCtx.addServlet(DateServlet.class, "/date/");
		webappCtx.addServlet(exampleJspFileMappedServletHolder(), "/test/foo/");
		webappCtx.addServlet(defaultServletHolder(baseUri), "/");
		
		setConfigurations(webappCtx);
		
		MetaData metaData = webappCtx.getMetaData();
		Resource webappInitializer = Resource.newResource(WebAppStrarUpInitializer.class.getProtectionDomain().getCodeSource().getLocation());
		metaData.addContainerResource(webappInitializer);

		HandlerCollection handlerCollection = new HandlerCollection();
		GzipHandler gzipHandler = new GzipHandler();
		handlerCollection.setHandlers(new Handler[] { webappCtx, logHandler, gzipHandler });
		return handlerCollection;
	}

	private void setConfigurations(WebAppContext webappCtx) {
		webappCtx.setConfigurations(new Configuration[] { 
	    		 new AnnotationConfiguration() {
	    		        @Override
	    		        public void preConfigure(WebAppContext context) throws Exception {
	    		        	ClassInheritanceMap map = new ClassInheritanceMap();
	    		            ConcurrentHashSet<String> hashSet = new  ConcurrentHashSet<String>();
	    		            hashSet.add(WebAppStrarUpInitializer.class.getName());//注册WebApplicationInitializer
	    		            map.put(WebApplicationInitializer.class.getName(), hashSet);
	    		            context.setAttribute(CLASS_INHERITANCE_MAP, map);
	    		            _classInheritanceHandler = new ClassInheritanceHandler(map);
	    		        }
	    		    } 
		 });
	}

	/**
	 * Ensure the jsp engine is initialized correctly
	 */
	private List<ContainerInitializer> jspInitializers() {
		JettyJasperInitializer sci = new JettyJasperInitializer();
		ContainerInitializer initializer = new ContainerInitializer(sci, null);
		List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
		initializers.add(initializer);
		return initializers;
	}

	/**
	 * Set Classloader of Context to be sane (needed for JSTL) JSP requires a
	 * non-System classloader, this simply wraps the embedded System classloader
	 * in a way that makes it suitable for JSP to use
	 */
	private ClassLoader getUrlClassLoader() {
		ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
		// jspClassLoader=Thread.currentThread().getContextClassLoader();
		// //也可以用这种方式
		return jspClassLoader;
	}

	/**
	 * Create JSP Servlet (must be named "jsp")
	 */
	private ServletHolder jspServletHolder() {
		ServletHolder holderJsp = new ServletHolder("jsp", JettyJspServlet.class);
		holderJsp.setInitOrder(0);
		holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
		holderJsp.setInitParameter("fork", "false");
		holderJsp.setInitParameter("xpoweredBy", "false");
		holderJsp.setInitParameter("compilerTargetVM", "1.7");
		holderJsp.setInitParameter("compilerSourceVM", "1.7");
		holderJsp.setInitParameter("keepgenerated", "true");
		return holderJsp;
	}

	/**
	 * Create Example of mapping jsp to path spec
	 */
	private ServletHolder exampleJspFileMappedServletHolder() {
		ServletHolder holderAltMapping = new ServletHolder();
		holderAltMapping.setName("foo.jsp");
		holderAltMapping.setForcedPath("/test/foo/foo.jsp");
		return holderAltMapping;
	}

	/**
	 * Create Default Servlet (must be named "default")
	 */
	private ServletHolder defaultServletHolder(URI baseUri) {
		ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
		if (ObjectUtil.isNotEmpty(baseUri)) {
			LOG.info("Base URI: " + baseUri);
			holderDefault.setInitParameter("resourceBase", baseUri.toASCIIString());
		} else {
			holderDefault.setInitParameter("resourceBase", param.getWebDir());
		}

		holderDefault.setInitParameter("dirAllowed", "true");
		return holderDefault;
	}

	/**
	 * Establish the Server URI
	 */
	private URI getServerUri(NetworkConnector connector) throws URISyntaxException {
		String scheme = "http";
		for (ConnectionFactory connectFactory : connector.getConnectionFactories()) {
			if (connectFactory.getProtocol().equals("SSL-http")) {
				scheme = "https";
			}
		}
		String host = connector.getHost();
		if (host == null) {
			host = "localhost";
		}
		int port = connector.getLocalPort();
		serverURI = new URI(String.format("%s://%s:%d/", scheme, host, port));
		LOG.info("start sucess on Server URI: " + serverURI);
	
		return serverURI;
	}

	public void stop() throws Exception {
		server.stop();
	}

	/**
	 * Cause server to keep running until it receives a Interrupt.
	 * <p>
	 * Interrupt Signal, or SIGINT (Unix Signal), is typically seen as a result
	 * of a kill -TERM {pid} or Ctrl+C
	 * 
	 * @throws InterruptedException
	 *             if interrupted
	 */
	public void waitForInterrupt() throws InterruptedException {
		server.join();
	}

	public static ServerParam getParam() {
		return param;
	}

	private RequestLog createRequestLog() {
		// 记录访问日志的处理
		NCSARequestLog requestLog = new NCSARequestLog();
		requestLog.setFilename(param.getLogDir() + "/jetty_log" + DateUtil.formatDate(DateUtil.YYYYMMDD) + ".log");
		requestLog.setRetainDays(90);
		requestLog.setExtended(false);
		requestLog.setAppend(true);
		// requestLog.setLogTimeZone("GMT");
		requestLog.setLogTimeZone("Asia/beijing");
		requestLog.setLogDateFormat("yyyy-MM-dd HH:mm:ss");
		requestLog.setLogLatency(true);
		return requestLog;
	}

	/**
	 * 设置线程池
	 * 
	 * @return
	 */
	private ThreadPool createThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(10);
		threadPool.setMaxThreads(500);
		return threadPool;
	}
}
