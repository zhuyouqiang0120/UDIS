package com.zens.udis.TVCrawler.services.esk365;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import net.sf.json.JSONArray;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Programe {
 public String url = "";// 网页链接
 public ArrayList<String> dataNameList = new ArrayList<String>();// 存储一天所有节目名称的数组
 public ArrayList<String> dataTimeList = new ArrayList<String>();;// 存储一天所有节目名称的数组
 JSONArray array = new JSONArray();
 
 // 构造方法初始化数据
 public Programe(String url) {
     // 初始化属性
     this.url = url; 
     //System.out.println("正在抓取" + url); 
 }
 
 
 public void core() throws UnknownHostException,IOException,SocketTimeoutException{
 //public static void main(String args[]){	 
	 //url = "http://www.esk365.com/tools/tv/?fid=2&tvid=1&channelid=1"; 
	 // 根据url获取网页源码
	 Spider spider = new Spider(); 
	 Document content = spider.SendGet(url); 	   
	   
	 // 截取出一天的所有节目(即网页<div class="tvgenre clear">标签下的内容)
	 Elements tvgenre_clear = null;
	 tvgenre_clear = content.select("div.tvgenre").select("div.clear");
	 
	 //去除杂质（去除一些干扰标签）。
	 tvgenre_clear.select("a.red").remove(); //从tvgenre_clear中删掉所有含有class='red'的a标签，比如<a href='http://mov.tvsou.com/drama/Jh/index_JhBjFr.htm' target='_blank' class='red'>剧情</a>
	 tvgenre_clear.select("div.border1px").remove(); //从tvgenre_clear中删掉所有含有class='border1px'的div标签，比如<div class='border1px'>倪家岛。满意和倪好故地重游，两人不约而同地想起之前傻里傻气地行为，纷纷笑出声来。满意试探倪好的态度，倪好直言满意确实和自己想象中的有出入，但是他真的很有才华，希望两人能好好……　[<a href='http://mov.tvsou.com/drama/drama2.asp?id=JhBjFr&CurrentPage=7#21' target='_blank' class='red'>第21集剧情</a>]</div>
	 /*tvgenre_clear.select("a#day").remove(); //从tvgenre_clear中删掉所有含有id='day'的a标签，比如<a name='day' id='day'></a>
	 tvgenre_clear.select("a#night").remove();*/
	 tvgenre_clear.select("div#Day_Div").remove();//从tvgenre_clear中删掉所有含有id="Day_Div"的div标签，比如<div id="Day_Div" style="margin:5px"><a name="day" id="day"></a><strong>日间节目预告</strong></div>
	 tvgenre_clear.select("div#Night_Div").remove();	 
	 //String stringTvgenre_clear = tvgenre_clear.toString();
	 //System.out.println("今天的节目名称有："+stringTvgenre_clear);
	    
	   
	  //将当天所有节目的播放时间一个个截取出来按顺序放进列表。
	  Elements dataTimes = tvgenre_clear.select("span");
	  dataTimes.select("div.border1px").remove();
	  Iterator<Element> liIterTime = dataTimes.iterator();  
	  while (liIterTime.hasNext()) {
	      Element dataTime = liIterTime.next();
	      String  stringDataTime = dataTime.text(); 
	      //System.out.println("今天的节目播放时间有："+i+stringDataTime);
	      dataTimeList.add(stringDataTime);   
	   } 
	  
//	  for(int i=0;i<dataTimeList.size();i++){
//		  System.out.println("第"+i+"个："+dataTimeList.get(i));
//	  }
	  
	  
	  
		 //将当天所有节目的名称一个个截取出来按顺序放进列表。
	     tvgenre_clear.select("span").remove();
	     Elements dataNames = tvgenre_clear.select("li");
		 for(int i=0;i<dataNames.size();i++){		 		 
		     Element dataName = dataNames.get(i);
		     String  stringDataName = dataName.text();
		     //System.out.println("今天的节目有"+i+stringDataName);
		     if(stringDataName.equals("")){
		    	 //什么都不做
		     }else{
		     dataNameList.add(stringDataName);
		     }
		  }
//		  for(int i=0;i<dataNameList.size();i++){
//			  System.out.println("第"+i+"个："+dataNameList.get(i));
//		  }
	  
	  
	  
	  
	  
 }



 public String writeString() throws UnknownHostException,IOException,SocketTimeoutException{
	 
	 System.out.println(dataNameList.size() +" -----   0000 ");
	 // 拼接写入本地的字符串  
	 String result = "";
	 for (int i = 0; i < dataTimeList.size(); i++) {
	     result += dataTimeList.get(i) + "   " + dataNameList.get(i)+ "\r\n";  
	 }
     result +="\r\n\r\n\r\n";
     // 将其中的html标签进行筛选  
	 result = result.replaceAll("<br>", "\r\n");  
	 result = result.replaceAll("<.*?>", ""); 
	 //System.out.println("本次返回的result为：" + result);
	 return result;  
	} 
 

 /*
 public JSONObject writeJsonString(String pqpd) {  
	    // 拼接要返回给jsonMain.java的json格式的字符串   
	    //System.out.println("这次爬取的频道是：" + pqpd);
	    
	
	    for (int i = 0; i < dataTimeList.size()-1; i++) {  
	         JSONObject timeProgrameJson = new JSONObject();
	         try {
	        	 timeProgrameJson.put("time", dataTimeList.get(i));
	        	 timeProgrameJson.put("program", dataNameList.get(i));
		        array.put(timeProgrameJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}         
	    }
	    JSONObject dayJson = new JSONObject();
	    try {
			dayJson.put("date", date);
			dayJson.put("programes", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

     // 将其中的html标签进行筛选  
	    //System.out.println(result);
	    return dayJson;
	}  
*/

 
 

 } 
