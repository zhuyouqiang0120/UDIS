package com.zens.udis.TVCrawler.services.esk365;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.jsoup.Connection;  
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;

import com.zens.udis.TVCrawler.services.utils.DateUtils;


public class Spider {
 
	public Document SendGet(String url) throws UnknownHostException,IOException,SocketTimeoutException {
     Connection connection = Jsoup.connect(url);  //建立一个连接。 
     Document result = null;
		result = connection.get();
  return result;
 }
 
 
 
 //找到并截取出这周六天的链接（esk365的网页源代码的一周连接中是没有当天的连接的，某频道对应的url默认就是当天链接）
public Elements getDaysUrl(Document content){
	Elements result = null;
	result = content.select("div.weeks").select("div.clear");
	return result;
}

	

// 从标签中筛选出纯链接，每个纯链接放进一个新实例化的cctv对象中，再将这些cctv对象放入新建的列表中，最后返回这个列表。
	public ArrayList<Programe> GetRecommendations(String url, String usefulXy, Elements daysUrl) throws UnknownHostException,IOException,SocketTimeoutException{
		
	//调用工具类判断今天是本周中的第几天，周一为1，周二为2，......，周日为7。结果用变量whichDay来表示。
	DateUtils dateUtils = new DateUtils();
	int whichDay = dateUtils.getDayNumberOfThisWeek();
	
	 // 预定义一个ArrayList来存储结果
	 ArrayList<Programe> results = new ArrayList<Programe>();
     
	 Elements daysLink = daysUrl.select("a"); //select方法返回的是一个Elements 对象，里面包含着找到的所有节点。遍历Elements ，通过get（index），就可以拿出具体的 节点了。通过节点的text()方法，就可用拿出文本值。而想得到节点的其他属性，可以看API的介绍。

     int flag = 0;
	 for(int i=1;i<daysLink.size()+1;i++){
    	 if(i==whichDay&&flag==0){
             //System.out.println("每天的链接为：" + url);
             Programe cctvTemp = new Programe(url);
             cctvTemp.core();
             results.add(cctvTemp);
             i--;
             flag = 1;
    	 }else{
    		 Element link = daysLink.get(i-1);
             String StringLink = link.select("[href]").attr("href"); 
             String dayUrl = usefulXy + StringLink;
             //System.out.println("每天的链接为：" + dayUrl);
             Programe cctvTemp = new Programe(dayUrl);
             cctvTemp.core();
             results.add(cctvTemp);
    	 }
		 
		 
	 }
	return results;
	}
}