package com.zens.udis.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2017年3月27日 下午3:13:39
 */

public class AqiController extends Controller {

	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();

	public void getData() {

		//new GrabAqi().grabAqi();

		long time1 = System.currentTimeMillis();

		jsonObject.element("status", "200 OK");
		jsonObject.element("success", "1");
		jsonObject.element("action", "aqi/getData");

		List<Record> list = dbUtils.getAqi(1);
		
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
