package com.zens.udis.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.zens.udis.db.DBUtils;
import com.zens.udis.interceptor.SessionInterceptor;
import com.zens.udis.model.Menu;

public class MainController extends Controller {

	/**
	 * 主页session检测
	 */
	@Before(SessionInterceptor.class)
	public void home() {
		renderJsp("home.jsp");
	}

	public void head() {
		renderJsp("head.jsp");
	}

	public void center() {
		Menu menu = new Menu();
		setAttr("menus", JsonKit.toJson(menu.getMenuList()));
		renderJsp("center.jsp");
	}

	public void left() {
		Menu menu = new Menu();
		setAttr("menus", JsonKit.toJson(menu.getMenuList()));
		renderJsp("left.jsp");
	}

	public void right() {
		renderJsp("right.jsp");
	}

	public void bottom() {
		renderJsp("bottom.jsp");
	}

	public void area() {
		renderJsp("cp/area.jsp");
	}

	public void nativeTask() {
		renderJsp("cp/inter.jsp");
	}

	public void vote() {
		renderJsp("strategy/vote.jsp");
	}

	public void vote_detail() {
		renderJsp("strategy/vote_detail.jsp");
	}

	public void sysLog() {
		renderJsp("log/sys.jsp");
	}

	public void userIndex() {
		renderJsp("sys/user.jsp");
	}

	public void backup() {
		renderJsp("sys/data.jsp");
	}

	public void sys_log() {
		renderJsp("log/sys.jsp");
	}

	public void changeStauts() {
		String status = getPara("tag");
		DBUtils dbUtils = new DBUtils();
		dbUtils.updateStatus(status);
		String sts = "";
		if (status.equals("1"))
			sts = "本地缓存数据";
		else {
			sts = "网络接口数据";
		}
		renderText("状态修改成功！数据来源修改为：" + sts);
	}
}
