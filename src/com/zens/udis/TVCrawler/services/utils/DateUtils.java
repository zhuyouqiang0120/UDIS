package com.zens.udis.TVCrawler.services.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	//这个方法用来判断今天是本周中的第几天，并返回一个数字，周一就返回一，周二就返回2，......周日就返回7。
	public int getDayNumberOfThisWeek(){
		   Calendar cal = Calendar.getInstance();  
		   cal.setTime(new Date());  
		   int whichDay = cal.get(Calendar.DAY_OF_WEEK); //此时的whichDay是美式表达方式，周日是1，周一是2，......，周六是7。所以下面要对它稍作调整。
		   //System.out.println(whichDay);
		   
		   //将美式改为中式（即将周日为1改为周日为7，当然其它的也要一起改）。
		   if(whichDay==1){
			   whichDay = 7;
		   }else{
			   whichDay = whichDay - 1;
		   }
		   
		   return whichDay;
		   
		}
	
	
	
	//算出两个日期之间相差多少天。
    public  int getDaysBetween(String newDate,String oldDate){
//      public static void main(String args[]){
//      	String newDate = "2016-9-2";
//      	String oldDate = "2016-8-26";
  	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d");  
          Calendar cal = Calendar.getInstance();    
          try {
  			cal.setTime(sdf.parse(oldDate));
  		} catch (ParseException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}    
          long time1 = cal.getTimeInMillis();                 
          try {
  			cal.setTime(sdf.parse(newDate));
  		} catch (ParseException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}    
          long time2 = cal.getTimeInMillis();         
          long between_days=(time2-time1)/(1000*3600*24); 
          int days = Integer.parseInt(String.valueOf(between_days));
          //System.out.println(days);
              
          return days;     
      }
    
    
    
	//获得今天的日期。
    public String getDateToday() {
		  Date dt=new Date();
		     SimpleDateFormat matter1=new SimpleDateFormat("yyyy-M-d");
		     String date = matter1.format(dt);
		     //System.out.println(date);
		     return date;
    }
    
    
    

	
	
	
	///////////////////  该方法用来 获取本周七天的日期  （开始）  ////////////////////
    //这个方法用来获取本周7天的日期(周一到周日)，将每个日期以字符串"2016-8-29"的格式，
    //放进同一个ArrayList中，最后返回这个ArrayList。
	
	static ArrayList<String> daysList = new ArrayList<String>();
	
	public ArrayList<String> getDaysOfThisWeek(){
		
		iterateWeekdays();
		return daysList;

	}
	
	
	private static final int FIRST_DAY = Calendar.MONDAY; //Calendar.SUNDAY值为1,Calendar.MONDAY值为2，Calendar.TUESDAY值为3，Calendar.WEDNSDAY值为4......
	 
	   private static void iterateWeekdays() {		   
	       Calendar calendar = Calendar.getInstance(); //实例化一个日历对象（包含当前时区当前日期时间）。
	       //System.out.println("calendar是："+calendar);
	       setToFirstDay(calendar); //将calendar的日期从当天改为本周的星期一。
	       //System.out.println("calendar是："+calendar);
	       for (int i = 0; i < 7; i++) {
	           addDayToList(calendar);
	           calendar.add(Calendar.DATE, 1); //把calendar的日期往后增加一天，正数往后推，负数往前移动。
	       }
	   }
	 
	   private static void setToFirstDay(Calendar calendar) {
	       while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) { //calendar.get(Calendar.DAY_OF_WEEK)就是取出 calendar这个时间是本周中的第几天。       	
	           calendar.add(Calendar.DATE, -1); //把日期向前减少一天。
	       }
	   }
	 
	   private static void addDayToList(Calendar calendar) {
	       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");	      
	       String day = dateFormat.format(calendar.getTime());
	       //System.out.println(day);
	       daysList.add(day);
	        
	   }

	 
       ///////////////////  该方法用来 获取本周七天的日期  （结束）  ////////////////////
	   
	   
	   
	   
	   
	   
	   
//       ///////////////////  该方法用来 获取本周周一的日期  （开始）  ////////////////////
//	   public String getMonday() {
//	       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //"yyyy-MM-dd E"
//	       Date[] date = getWeekDay();
//	       //System.out.println(dateFormat.format(date[0]));
//	       String mondayDate = dateFormat.format(date[0]);
//	       return mondayDate;
//	       }
//
//	   public static Date[] getWeekDay() {
//	       Calendar calendar = Calendar.getInstance();
//	       while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
//	           calendar.add(Calendar.DAY_OF_WEEK, -1);
//	       }
//	       Date[] dates = new Date[5];
//	       for (int i = 0; i < 5; i++) {
//	           dates[i] = calendar.getTime();
//	           calendar.add(Calendar.DATE, 1);
//	       }
//	       return dates;
//	   }
//       ///////////////////  该方法用来 获取本周周一的日期  （结束）  ////////////////////
	   
	   
	   
	   
	   
	   
	   
	   
	   
//       ///////////////////  该方法用来 获取上周周一的日期  （开始）  ////////////////////	   
//	   public String getLastMonday(){
//	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//	       Calendar cal=Calendar.getInstance();
//	       cal.set(Calendar.DAY_OF_WEEK, 1);
//	       //System.out.println("上周日为："+sdf.format(cal.getTime()));
//	       cal.add(Calendar.WEEK_OF_MONTH, -1);
//	       cal.set(Calendar.DAY_OF_WEEK, 2);
//	       //System.out.println("上周一为："+sdf.format(cal.getTime()));
//	       return sdf.format(cal.getTime());
//	   }
//       ///////////////////  该方法用来 获取上周周一的日期  （结束）  ////////////////////    
    
    
	
	

}
