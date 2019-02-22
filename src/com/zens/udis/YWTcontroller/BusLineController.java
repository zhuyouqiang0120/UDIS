package com.zens.udis.YWTcontroller;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.zens.udis.utils.HttpRequest;
/**
 * 
 * @author ZYQ(zhuyq@zensvision.com)
 * @date  2015年11月4日下午3:25:49
 */

public class BusLineController extends Controller {//公交车线路查询
	//http://localhost:8080/JMYiWangTong/bus/busline?c=302&wd=41%E8%B7%AF
	//http://www.gongjiao.com/
	
	private HttpRequest request = new HttpRequest();
	private JSONObject jsonObject = new JSONObject();
	
	public void busline() throws IOException{
		JSONObject upjson = new JSONObject();
		JSONObject downjson = new JSONObject();

		String c = getPara("c");
		String wd = getPara("wd");
		wd = new String(wd.getBytes("ISO-8859-1"), "UTF-8");
		
		String uidurl = "http://api.map.baidu.com/?qt=bl&c="+ c +"&wd="+ wd +"&ie=utf-8&oue=1&fromproduct=jsapi&res=api&callback=BMap._rd._cbk37766";

		String bl = request.sendGet(uidurl, "");
		int f = bl.indexOf("(");
		bl = bl.substring(f + 1, bl.length() - 1);

		JSONObject blobject = JSONObject.fromObject(bl);
		JSONArray blcontent = JSONArray.fromObject(blobject.get("content"));
		String num = JSONObject.fromObject(blobject.get("result")).getString(
				"total_busline_num");
		String city = JSONObject.fromObject(blobject.get("current_city"))
				.getString("name");
		String pro = JSONObject.fromObject(blobject.get("current_city"))
				.getString("up_province_name");
		
		if (num.equals("0")) {
			jsonObject.element("success", "1");
			jsonObject.element("msg", "error busline!");
		} else if (num.equals("1")) {
			jsonObject.element("success", "0");
			jsonObject.element("msg", "one busline");
			String uid1 = JSONObject.fromObject(blcontent.get(0)).getString(
					"uid");
			upjson = line(c, uid1);
		} else {
			jsonObject.element("success", "0");
			jsonObject.element("msg", "two buslines!");
			String uid1 = JSONObject.fromObject(blcontent.get(0)).getString(
					"uid");
			String uid2 = JSONObject.fromObject(blcontent.get(1)).getString(
					"uid");
			upjson = line(c, uid1);
			downjson = line(c, uid2);
		}
		
		jsonObject.element("line", wd);
		jsonObject.element("city", pro+" "+city);
		jsonObject.element("up", upjson);
		jsonObject.element("down", downjson);
		
		renderJson(jsonObject);
	}
	
	public JSONObject line(String c, String uid){
		JSONObject json = new JSONObject();
		
		String lineurl = "http://api.map.baidu.com/?qt=bsl&c="+ c +"&uid="+ uid + "&ie=utf-8&oue=1&fromproduct=jsapi&res=api&callback=BMap._rd._cbk7719";
		
		String bsl = request.sendGet(lineurl, "");
		int f = bsl.indexOf("(");
		bsl = bsl.substring(f + 1, bsl.length() - 1);
		JSONObject bslobject = JSONObject.fromObject(bsl);
		JSONArray bslcontent = JSONArray.fromObject(bslobject.get("content"));

		JSONObject line = JSONObject.fromObject(bslcontent.get(0));

		JSONArray stations = JSONArray.fromObject(line.get("stations"));
		for (Object o1 : stations) {
			JSONObject station = (JSONObject) o1;
			station.remove("rt_info");
			station.remove("tri_rt_info");
			station.remove("geo");
			station.remove("uid");
		}

		json.element("name", line.get("name"));
		json.element("starttime", line.get("startTime"));
		json.element("endtime", line.get("endTime"));
		json.element("stations", stations.toString());
		
		return json;
	}
}
