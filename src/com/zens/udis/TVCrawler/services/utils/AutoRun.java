package com.zens.udis.TVCrawler.services.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class AutoRun {
	
	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

		public void core() {
			//获取本周日24点的毫秒数。
			DateUtils dateUtils = new DateUtils();
			ArrayList<String> daysList = dateUtils.getDaysOfThisWeek();
			String Sunday = daysList.get(6);
		    String[] SundayArray = Sunday.split("-");
		    int year = Integer.parseInt(SundayArray[0]);
		    int month = Integer.parseInt(SundayArray[1]);
		    int day = Integer.parseInt(SundayArray[2]);
		    Calendar c = Calendar.getInstance();
		    c.set(year, month-1, day, 24, 0, 0); //这里month-1是因为月份的起始值是0不是1。 
		    c.set(Calendar.MILLISECOND, 0);
		    long sundayMILLISECOND = c.getTime().getTime();
		    
		    //获取当前时间毫秒数。
		    long nowTime = System.currentTimeMillis();
		    
		    long delayTime = sundayMILLISECOND - nowTime;
		    System.out.println("delayTime是："+delayTime);

      		      
	        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		    //service.scheduleAtFixedRate(new Task(), 5000, 10000, TimeUnit.MILLISECONDS);
		    service.scheduleAtFixedRate(new Task(), delayTime, 604800000, TimeUnit.MILLISECONDS); //一周有604800秒。
	  
	 }
	}


