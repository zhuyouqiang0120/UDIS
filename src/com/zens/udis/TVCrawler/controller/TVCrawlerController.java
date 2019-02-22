package com.zens.udis.TVCrawler.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.chasonx.tools.HttpUtil;
import com.jfinal.core.Controller;
import com.zens.udis.TVCrawler.services.common.ConfigParamService;
import com.zens.udis.TVCrawler.services.common.CrawlService;


/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2017年3月27日 下午3:13:39
 */

public class TVCrawlerController extends Controller {

	ConfigParamService configParamService = new ConfigParamService();
	CrawlService crawlService = new CrawlService();

	public void run() {
		System.out.println("90");renderText("aaaa");
/*
		ArrayList<Object> resultsList = crawlService.crawl();
		if (resultsList.size() != 0) {
			String usefulXy = (String) resultsList.get(0);
			@SuppressWarnings("unchecked")
			ArrayList<String> programesList = (ArrayList<String>) resultsList.get(1);
			setAttr("usefulXy", usefulXy);
			setAttr("programesList", programesList);
			//render("Successfully.jsp");
			renderText("aaaa");
		} else if (resultsList.size() == 0) {
			//render("Unsuccessfully.jsp");
			renderText("aaaa");
		}
	*/
	}
	
	public static void main(String[] args) {
		try{
			Map<String, String> param = new HashMap<String, String>();
			param.put("mac", "aa:bb:cc:dd");
			HttpUtil.UrlPostFile("http://192.168.0.86:8080/AnyProbe/home/device/screenshot", "/Users/zyq/Downloads/Movie/异形：契约.jpg", param);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
