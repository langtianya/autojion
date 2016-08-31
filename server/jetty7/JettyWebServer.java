package com.wangzhe.autojoin.wangfw.server.jetty7;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.LoggerFactory;

import com.wangzhe.autojoin.wangfw.util.DateUtil;

/**如果需要使用jetty7，请移除jetty9的jar包，改成jetty7的jar包，然后把本类的jetty7前缀代码注释去掉
 * @author oucq
 *
 */
@SuppressWarnings("unused")
public class JettyWebServer {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(JettyWebServer.class);

    private Server server;
    private int port;
    private String host;
    private String tempDir;
    private String logDir;
    private String webDir;
    private String contextPath;


    public JettyWebServer(int port, String host, String tempDir, String webDir, String logDir, String contextPath) {

        logger.info("port:{},host:{},tempDir:{},webDir:{},logDir:{},contextPath:{}", port, host, tempDir, webDir, logDir, contextPath);
        this.port = port;
        this.host = host;
        this.tempDir = tempDir;
        this.webDir = webDir;
        this.contextPath = contextPath;
        this.logDir = logDir;
    }

    public void start() throws Exception {
    	  server = new Server();
   //方法1：可以实现jsp访问
 /*		jetty7 Connector connector = new SelectChannelConnector();
		connector.setPort(80);

 		server.setConnectors(new Connector[] { connector });

 		WebAppContext webAppContext = new WebAppContext();

 		webAppContext.setContextPath("/");
 		webAppContext.setDescriptor(webDir+"\\WEB-INF\\web.xml");
 		webAppContext.setResourceBase(webDir);
 		webAppContext.setDisplayName("myProject");
 		webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
 		webAppContext.setConfigurationDiscovered(true);
 		webAppContext.setParentLoaderPriority(true);
 		server.setHandler(webAppContext);
 		System.out.println(webAppContext.getContextPath());
 		System.out.println(webAppContext.getDescriptor());
 		System.out.println(webAppContext.getResourceBase());
 		System.out.println(webAppContext.getBaseResource());

 		try {
 			server.start();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		///方法2：目前只能实现html访问
 		System.out.println("server is  start");*/
//        server = new Server(createThreadPool());
//        server.addConnector(createConnector());
//        server.setHandler(createHandlers());
//        server.setStopAtShutdown(true);
//        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }


   
	private ThreadPool createThreadPool() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        return threadPool;
    }


//    private NetworkConnector createConnector() {
//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(port);
//        connector.setHost(host);
//        return connector;
//    }

    private HandlerCollection createHandlers() {
    	
//        WebAppContext context = new WebAppContext(webDir,"/");
    	WebAppContext context = new WebAppContext();
        context.setContextPath(contextPath);
//        context.setWar(webDir);//for war
        context.setResourceBase(webDir);//for dir
        context.setTempDirectory(new File(tempDir));
        
        context.setWelcomeFiles(new String[]{webDir+"\\index.html"});//欢迎页面
    // http://blog.csdn.net/zjc/article/details/43169931
     //http://www.open-open.com/lib/view/open1335833010671.html
     // http://blog.csdn.net/robinpipi/article/details/7557035
        context.setDescriptor(webDir+"\\WEB-INF\\web.xml");  
        context.setDisplayName("自联网");  
        context.setClassLoader(Thread.currentThread().getContextClassLoader());  
        context.setConfigurationDiscovered(true);  
        context.setParentLoaderPriority(true);

        RequestLogHandler logHandler = new RequestLogHandler();
        logHandler.setRequestLog(createRequestLog());
        HandlerCollection handlerCollection = new HandlerCollection();
       /* jetty7 GzipHandler gzipHandler = new GzipHandler();
        handlerCollection.setHandlers(new Handler[]{context, logHandler, gzipHandler});*/
        return handlerCollection;
    }

    private RequestLog createRequestLog() {
        //记录访问日志的处理
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename(logDir + "/jetty_log"+DateUtil.formatDate(DateUtil.YYYYMMDD)+".log");
        requestLog.setRetainDays(90);
        requestLog.setExtended(false);
        requestLog.setAppend(true);
        //requestLog.setLogTimeZone("GMT");
        requestLog.setLogTimeZone("Asia/beijing");
        requestLog.setLogDateFormat("yyyy-MM-dd HH:mm:ss");
        requestLog.setLogLatency(true);
        return requestLog;
    }

}
