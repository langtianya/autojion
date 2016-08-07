package com.wangzhe.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.PrintStream;

/**
 * 初始化LOG日子文件!
 * User: Administrator
 * Date: 13-4-25
 * Time: 上午10:06
 */
public class InitLogListener implements ServletContextListener {

    private static final String ERROR_FILE = "cc_error.log";
    private static final String OUT_FILE = "cc_out.log";

    protected static void redirectStdErrorAndOut(String out, String error) {

        try {
            System.setErr(new PrintStream(new File(error)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        try {
//            System.setOut(new PrintStream(new File(out)));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
       // System.out.println("初始化LOG日子文件!");
    }

    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        redirectStdErrorAndOut(OUT_FILE, ERROR_FILE);
    }

    
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
