/**********************************************************************
 **********************************************************************
 **    Project Name : TVCrawler
 **    Package Name : com.tvcrawler.common								 
 **    Type    Name : GetPaths 							     	
 **    Create  Time : 2017年3月2日 下午4:28:24								
 ** 																
 **    (C) Copyright Zensvision Information Technology Co., Ltd.	 
 **            Corporation 2017 All Rights Reserved.				
 **********************************************************************
 **	     注意： 本内容仅限于上海仁视信息科技有限公司内部使用，禁止转发		 **
 **********************************************************************
 */
package com.zens.udis.TVCrawler.services.utils;

import com.jfinal.kit.PathKit;

/**
 * @author  Jhonson
 * @email   xzc@zensvision.com
 * @create  2017年3月2日下午4:28:24
 * @version 1.0 
 */
public class Paths {
	
	public static String webRootPath; //项目在tomcat下的根路径。
	public static String infoSourcePath; //这个路径是tomcat中该项目文件夹下的data文件夹。
	public static String sourcePath; //这个路径下的文本文件用来保存所有的信息源。
	public static String unreachablePath; //这个路径的文本文件用来保存不可达的信息源。
	public static String userXyPath; //这个路径的文本文件用来保存用户选择的信息源。
	public static String apiPath; //这个路径的文本文件用来保存“获取频道名单的http接口”。
	public static String overApiPath; //这个路径的文本文件用来保存“抓取完毕后回调的http接口”。
	public static String programesPP; //这个路径的文本文件用来保存用户在配置页面输入的抓取节目所保存的路径。
	
	public void initialize() {
		webRootPath = PathKit.getWebRootPath();
		infoSourcePath = webRootPath + "/data/";
		sourcePath = infoSourcePath + "xy/source.txt";
		unreachablePath = infoSourcePath + "xy/unreachable.txt";
		userXyPath = infoSourcePath + "userParam/userXy.txt";
		apiPath = infoSourcePath + "userParam/api.txt";
		overApiPath = infoSourcePath + "userParam/overApi.txt";
		programesPP = infoSourcePath + "userParam/programesPath.txt";
	}

}
