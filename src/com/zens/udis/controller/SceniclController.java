package com.zens.udis.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zhl
 * @mail zhuhl@zensvision.com
 * @time 2016年4月27日 上午10:34:27
 */

public class SceniclController extends Controller {
	List<Record> list = new ArrayList<>();
	DBUtils dbutils = new DBUtils();

	public void getData() {
		long time1 = System.currentTimeMillis();

		JSONObject jo = new JSONObject();
		jo.element("status", "200 OK");
		jo.element("action", "scenicl/getData");
		jo.element("success", "1");

		list = dbutils.getScienicl(3);

		jo.element("totalResults", JSONArray.fromObject(list.get(0).get("data")).size());
		jo.element("repData", list.get(0).get("data"));
		jo.element("totlalResults", list.size());
		long time2 = System.currentTimeMillis();
		jo.element("elapsed", time2 - time1);

		if (getPara("callback") == null) {
			renderJson(jo);
		} else {
			renderJavascript(getPara("callback") + "(" + jo + ")");
		}
	}

}
