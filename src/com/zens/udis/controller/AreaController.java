package com.zens.udis.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.AreaDBUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月31日 下午4:57:59
 */

public class AreaController extends Controller {

	JSONObject jsonObject = new JSONObject();
	AreaDBUtils dbUtils = new AreaDBUtils();

	public void getAreas() {

		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int AreaType = getParaToInt("AreaType");
		int interTag = getParaToInt("interTag");
		int deleted = getParaToInt("deleted");

		String orderCase = getPara("orderCase");

		long time1 = System.currentTimeMillis();

		jsonObject.element("pageSize", pageSize);
		jsonObject.element("pageNumber", currPage);
		jsonObject.element("totalRow", 2);

		List<Record> list = dbUtils.getAreas(0);
		jsonObject.element("list", JsonKit.toJson(list));

		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		if (getPara("callback") == null) {
			renderJson(jsonObject);
		} else {
			renderJavascript(getPara("callback") + "(" + jsonObject + ")");
		}
	}

	public void insertArea() {
		Map<String, String[]> map = getParaMap();
		boolean flag = false;
		try {
			flag = dbUtils.insertArea(map);
			//SystemLogger.warn("添加接口任务", "添加接口任务 " + map.get("area") + " " + (flag ? "成功" : "失败"),(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("添加接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}
		/**
		 * 添加任务自动加入或者停止系统定时任务
		 */
		renderJson("{\"success\":" + flag + "}");
	}

	public void updateArea() {
		Map<String, String[]> map = getParaMap();
		boolean flag = false;
		try {
			flag = dbUtils.updateArea(map);
			//SystemLogger.warn("更新接口任务", "更新接口任务 " + map.get("area") + " " + (flag ? "成功" : "失败"),(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("更新接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}
		/**
		 * 更改任务自动加入或者停止系统定时任务
		 */
		renderJson("{\"success\":" + flag + "}");
	}

	public void removeArea() {
		String ids = getPara("ids");
		boolean flag = false;
		try {
			flag = dbUtils.removeAreas(ids);
			//SystemLogger.warn("移除接口任务", "移除接口任务 " + (flag ? "成功" : "失败") + "，ID集合为" + ids,(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("移除接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}

		renderJson("{\"success\":" + flag + "}");
	}

	public void deleteArea() {
		String ids = getPara("ids");
		boolean flag = false;
		try {
			flag = dbUtils.deleteAreas(ids);
			//SystemLogger.warn("删除接口任务", "删除接口任务 " + (flag ? "成功" : "失败") + "，ID集合为" + ids,(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("删除接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}
		renderJson("{\"success\":" + flag + "}");
	}
}
