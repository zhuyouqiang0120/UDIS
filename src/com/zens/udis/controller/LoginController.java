package com.zens.udis.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.zens.udis.interceptor.LoginInterceptor;
import com.zens.udis.service.SystemLogger;

/**
 * 
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年3月8日 上午10:48:00
 *
 */

public class LoginController extends Controller {

	public void index(){
		renderJsp("login.jsp");
		//render("/index.html");
	}
	
	/**
	 * 登陆拦截
	 */
	@Before(LoginInterceptor.class)
	public void login(){
		SystemLogger.warn("用户登录系统", getPara("username") + "登入系统。", "LOGIN");
		//render("/index.html");
		redirect("/main/home");
	}
	
	public void logout(){
		SystemLogger.warn("用户退出系统", getSessionAttr("loginUser") + "登出系统。", "LOGIN");
		getSession().invalidate();
		renderJsp("login.jsp");
	}
}
