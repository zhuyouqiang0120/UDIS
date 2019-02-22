package com.zens.udis.TVCrawler.services.tvsou;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Connection;  
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements; 

public class Spider {
 
	public Document SendGet(String url) throws UnknownHostException,IOException,SocketTimeoutException{
     Connection connection = Jsoup.connect(url);  //建立一个连接。 
     Document result = null;
	 result = connection.get();
     return result;
 }
 
 
//根据字符串参数pqpd爬取出该频道的中文名
 public String GetPdName(String pqpd){
	    String url = "http://www.tvsou.com/epg/" + pqpd;
	     Connection connection = Jsoup.connect(url);  //建立一个连接。 
	     Document content = null;
		try {
			content = connection.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Elements name = null;
		name = content.select("[class=color-01 r tit]");
		String pdName = name.text();
		String pdNameArrays[] = pdName.split(" ");
		pdName = pdNameArrays[0];
		//result = result.select("<上周</a>*=周日</a>");
		System.out.println("阿斯蒂芬pdName是"+pdName);
		return pdName;
	} 
 
 
 
 //找到并截取出这周七天的链接
public Elements getDaysUrl(Document content){
	Elements result = null;
	result = content.select("div.epgbox");
	//result = result.select("<上周</a>*=周日</a>");
	return result;
}

	// 从标签中筛选出纯链接，每个纯链接放进一个新实例化的cctv对象中，再将这些cctv对象放入新建的列表中，最后返回这个列表。
	public ArrayList<Programe> GetRecommendations(String url, Elements daysUrl) throws UnknownHostException,IOException,SocketTimeoutException{
	 // 预定义一个ArrayList来存储结果
	 ArrayList<Programe> results = new ArrayList<Programe>();
     Elements daysLink = daysUrl.select("a"); 
     Iterator<Element> liIter = daysLink.iterator();  
     int i = 0;
     while (liIter.hasNext()) {
         Element link = liIter.next();
    	 if(i==0||i==8){
    		 
    	 }else{
         String StringLink = link.select("[href]").attr("href"); 
         //System.out.println("每天的链接为：" + StringLink);
         Programe cctvTemp = new Programe(StringLink);
         cctvTemp.core();
         results.add(cctvTemp);
    	 }
         i++;
     }  
	 return results;
	}
 

}