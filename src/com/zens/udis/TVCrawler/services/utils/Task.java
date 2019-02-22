package com.zens.udis.TVCrawler.services.utils;

import java.util.Date;

import com.zens.udis.TVCrawler.services.common.CrawlService;


public class Task implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 System.out.println(new Date());
		 //System.out.println("哈哈哈哈哈哈哈！");
		 CrawlService crawlService = new CrawlService();
		 crawlService.crawl();

	}

}
