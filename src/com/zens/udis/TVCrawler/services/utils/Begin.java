package com.zens.udis.TVCrawler.services.utils;

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  

/*自定义监听器*/  
public class Begin implements ServletContextListener{  
	
	AutoRun autoRun = new AutoRun();
	
    /*在Tomcat容器启动时就会触发该方法*/  
    @Override  
    public void contextInitialized(ServletContextEvent sce) {        
    	
    	//给所有路径赋初始值
    	Paths paths = new Paths();
    	paths.initialize();
    	
    	//判断本地有无初始信息文件，若有则什么都不做，若没有，则生成。
    	InitFiles initFiles = new InitFiles();
    	initFiles.Initialize();    	    	
    	//运行定时任务。
    	autoRun.core();
    }  
    /*在Tomcat容器销毁时就会触发该方法*/  
    @Override  
    public void contextDestroyed(ServletContextEvent sce) {  
        System.out.println("===context Destory!===");
        autoRun.service.shutdownNow();
    }  
}  