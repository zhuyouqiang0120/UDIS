package com.zens.udis.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.plugin.quartz.QuartzPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.zens.udis.TVCrawler.controller.TVCrawlerController;
import com.zens.udis.YWTcontroller.*;
import com.zens.udis.controller.AqiController;
import com.zens.udis.controller.AreaController;
import com.zens.udis.controller.BuzzController;
import com.zens.udis.controller.GrabController;
import com.zens.udis.controller.HealthController;
import com.zens.udis.controller.InterController;
import com.zens.udis.controller.InterfaceController;
import com.zens.udis.controller.JianKaiController;
import com.zens.udis.controller.LoginController;
import com.zens.udis.controller.MainController;
import com.zens.udis.controller.PublicController;
import com.zens.udis.controller.RoadController;
import com.zens.udis.controller.SceniclController;
import com.zens.udis.controller.SocialController;
import com.zens.udis.controller.SysUserController;
import com.zens.udis.controller.TalkController;
import com.zens.udis.controller.TrafficController;
import com.zens.udis.controller.TvController;
import com.zens.udis.controller.UserController;
import com.zens.udis.controller.WeatherController;
import com.zens.udis.controller.WhatController;

/**
 * 
 * zyq zhuyq@zensvision.com 2016年3月18日 上午11:16:29
 */
public class UdisConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setBaseViewPath("WEB-INF/views"); //路径
		me.setViewType(ViewType.JSP); //视图类型
		me.setErrorView(404, "/WEB-INF/views/error/404.jsp");
		me.setErrorView(500, "/WEB-INF/views/error/500.jsp");
	}

	// 路由
	@Override
	public void configRoute(Routes me) {
		me.add("/", LoginController.class);
		

		me.add("/main", MainController.class);
		me.add("/area", AreaController.class);
		me.add("/inter", InterController.class);
		me.add("/user", SysUserController.class);
		me.add("/jiankai", JianKaiController.class);

		//LEMON EPG抓取
		me.add("/tv", TVCrawlerController.class);//节目单
		
		me.add("/SHLife/user", UserController.class);
		me.add("/SHLife/traffic", TrafficController.class);
		me.add("/SHLife/public", PublicController.class);
		me.add("/SHLife/inter", InterfaceController.class);
		me.add("/SHLife/weather", WeatherController.class);
		me.add("/SHLife/aqi", AqiController.class);
		me.add("/SHLife/scenicl", SceniclController.class);
		me.add("/SHLife/talk", TalkController.class);
		me.add("/SHLife/buzz", BuzzController.class);
		me.add("/SHLife/what", WhatController.class);
		me.add("/SHLife/road", RoadController.class);
		me.add("/SHLife/grab", GrabController.class);
		//me.add("/tv", TvController.class);
		me.add("/SHLife/social",SocialController.class);
		me.add("/SHLife/health",HealthController.class);
		
		//邑网通
		me.add("/registration", RegisTrationController.class);//医疗挂号
		me.add("/news", ZiXunController.class);//资讯
		me.add("/bus", BusLineController.class);//资讯
	}

	@Override
	public void configPlugin(Plugins me) {
		loadPropertyFile("jdbc.properties"); // load配置文件
		C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbc.url"), getProperty("jdbc.username"),
				getProperty("jdbc.password"));
		me.add(c3p0); // 添加插件

		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		me.add(arp);
		arp.setDialect(new MysqlDialect());

		QuartzPlugin quartzPlugin = new QuartzPlugin("job.properties");
		me.add(quartzPlugin);
	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	@Override
	public void configHandler(Handlers me) {
		ContextPathHandler path = new ContextPathHandler("ContextPath");
		me.add(path); //添加全局路径，提供给jsp定位使用
	}

}
