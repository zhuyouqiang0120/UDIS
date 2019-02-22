package com.zens.udis.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月31日 下午4:57:59
 */

public class BuzzController extends Controller {

	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();

	public void getData() {

		long time1 = System.currentTimeMillis();

		jsonObject.element("status", "200 OK");
		jsonObject.element("success", "1");
		jsonObject.element("action", "buzz/getData");

		List<Record> list = dbUtils.getBuzz(5);

		jsonObject.element("totalResults", JSONArray.fromObject(list.get(0).get("data")).size());
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
