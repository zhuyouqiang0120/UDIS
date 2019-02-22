package com.zens.udis.TVCrawler.services.tvsou;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Programe {
 public String date = "";
 public String cctvUrl = "";// 网页链接
 public ArrayList<String> dataNameList = new ArrayList<String>();// 存储一天所有节目名称的数组
 public ArrayList<String> dataTimeList = new ArrayList<String>();// 存储一天所有节目名称的数组
 JSONArray array = new JSONArray();
 // 构造方法初始化数据
 public Programe(String url) {
  // 初始化属性
   cctvUrl = "http://www.tvsou.com" + url; 
   //System.out.println("正在抓取" + cctvUrl);    
 }
  
 public void core() throws UnknownHostException,IOException,SocketTimeoutException{
	// 根据url获取网页源码
	   Spider spider = new Spider(); 
	   Document content = spider.SendGet(cctvUrl); 

	   // 截取出一天的所有节目(即网页<div class="play-time-more">标签下的内容)
		Elements playTimeMore = null;
		playTimeMore = content.select("div.play-time-more");

	   //将当天所有节目的名称一个个截取出来按顺序放进列表。
		Elements titles = playTimeMore.select("a[title]");  
		Iterator<Element> liIterName = titles.iterator();  
	      while (liIterName.hasNext()) {  
	          Element title = liIterName.next();
	          String  stringTitle = title.attr("title");
	          //System.out.println("今天的节目有"+stringDataName);
	          dataNameList.add(stringTitle);   
	      }
	   
	    //将当天所有节目的播放时间一个个截取出来按顺序放进列表。
	  	Elements dataTimes = playTimeMore.select("span");
		Iterator<Element> liIterTime = dataTimes.iterator();  
	    while (liIterTime.hasNext()) {  
	        Element dataTime = liIterTime.next();
	        String  stringDataTime = dataTime.text(); // 获取<span>00:00</span>中的00:00
	        //System.out.println("今天的节目播放时间有："+stringDataTime);
	        dataTimeList.add(stringDataTime);   
	    }
 }
 
 public String writeString() throws UnknownHostException,IOException,SocketTimeoutException{  
	    // 拼接写入本地的字符串  
	    String result = "";
	    for (int i = 0; i < dataTimeList.size(); i++) {  
	        result += dataTimeList.get(i) + "   " + dataNameList.get(i)+ "\r\n";  
	    }
        result +="\r\n\r\n\r\n";
        // 将其中的html标签进行筛选  
	    result = result.replaceAll("<br>", "\r\n");  
	    result = result.replaceAll("<.*?>", ""); 
	    //System.out.println(result);
	    return result;  
	} 

 } 
