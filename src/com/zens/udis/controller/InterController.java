package com.zens.udis.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.InterDBUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月31日 下午4:57:59
 */

public class InterController extends Controller {

	JSONObject jsonObject = new JSONObject();
	InterDBUtils dbUtils = new InterDBUtils();

	public void getInters() {

		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int taskType = getParaToInt("taskType");
		int aid = getParaToInt("aid");
		int deleted = getParaToInt("deleted");
		
		String orderCase = getPara("orderCase");

		long time1 = System.currentTimeMillis();

		jsonObject.element("pageSize", pageSize);
		jsonObject.element("pageNumber", currPage);
		jsonObject.element("totalRow", 2);

		List<Record> list = dbUtils.getInters(aid);
		jsonObject.element("list", JsonKit.toJson(list));

		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		if(getPara("callback") == null){
			renderJson(jsonObject);
		}else{
			renderJavascript(getPara("callback") + "(" + jsonObject + ")");
		}
	}
	
	public void insertInter() {
		Map<String, String[]> map = getParaMap();

		boolean flag = false;
		try {
			flag = dbUtils.insertInter(map);
			//SystemLogger.warn("添加接口任务", "添加接口任务 " + map.get("Name") + " " + (flag ? "成功" : "失败"),(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("添加接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}
		/**
		 * 添加任务自动加入或者停止系统定时任务
		 */
		renderJson("{\"success\":" + flag + "}");
	}

	public void updateInter() {
		Map<String, String[]> map = getParaMap();
		boolean flag = false;
		try {
			flag = dbUtils.updateInter(map);
			//SystemLogger.warn("更新接口任务", "更新接口任务 " + map.get("Name") + " " + (flag ? "成功" : "失败"),(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("更新接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}
		/**
		 * 更改任务自动加入或者停止系统定时任务
		 */
		renderJson("{\"success\":" + flag + "}");
	}

	public void removeInter() {
		String ids = getPara("ids");
		boolean flag = false;
		try {
			flag = dbUtils.removeInters(ids);
			//SystemLogger.warn("移除接口任务", "移除接口任务 " + (flag ? "成功" : "失败") + "，ID集合为" + ids,(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("移除接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}

		renderJson("{\"success\":" + flag + "}");
	}

	public void deleteInter() {
		String ids = getPara("ids");
		boolean flag = false;
		try {
			flag = dbUtils.deleteInters(ids);
			//SystemLogger.warn("删除接口任务", "删除接口任务 " + (flag ? "成功" : "失败") + "，ID集合为" + ids,(String) getSessionAttr("loginUser"));
		} catch (Exception e) {
			flag = false;
			//SystemLogger.error("删除接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(),"EXCEPTION");
		}
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void runInter(){
		String ids = getPara("ids");
		
		renderJson("[{\"success\":\"true\",\"taskID\":\""+ ids +"\",\"name\":\"111\"}]");
	}
	
	public void getData(){
		int id = getParaToInt("id");
		
		long time1 = System.currentTimeMillis();

		jsonObject.element("status", "200 OK");
		jsonObject.element("success", "1");
		jsonObject.element("action", "aqi/getData");

		List<Record> list = dbUtils.getData(id);
		
		jsonObject.element("repData", list.get(0).get("data"));

		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		if(getPara("callback") == null){
			renderJson(jsonObject);
		}else{
			renderJavascript(getPara("callback") + "(" + jsonObject + ")");
		}
	}
}
