package com.zens.udis.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.grab.GrabSocial;
import com.zens.udis.utils.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zhl
 * @mail zhuhl@zensvision.com
 * @time 2016年4月27日 上午10:34:27
 */
public class SocialController extends Controller {
	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();
	List<Record> status = this.dbUtils.getStatus();
	String json = "";

	public void getData() {
		long time1 = System.currentTimeMillis();
		String userName = getPara("idnum");
		String userPwd = getPara("idpwd");

		List<?> searchs = this.dbUtils.getSearch("Social");
		if (((Record) this.status.get(0)).getStr("status").equals("1")) {
			json = ((Record) searchs.get(0)).getStr("result");
		} else {
			jsonObject.element("status", "200 OK");
			jsonObject.element("success", "1");
			jsonObject.element("action", "social/getData");

			String result = new GrabSocial().grabSocial(userName, userPwd);

			if (result.equals("error")) {
				jsonObject.element("success", "0");
				jsonObject.element("repData", "用户名或密码错误！");
			} else {
				try {
					JSONObject repData = new JSONObject();

					JSONObject obj = JSONObject.fromObject(result);
					JSONArray paylist = JSONArray
							.fromObject(obj.get("paylist").toString().replace("\"[", "[").replace("]\"", "]"));
					JSONArray paylist4 = JSONArray.fromObject(paylist.get(3));
					JSONObject ylj = JSONObject.fromObject(paylist4.get(0));
					JSONArray rights = JSONArray
							.fromObject(obj.get("rights").toString().replace("\"[", "[").replace("]\"", "]"));
					JSONArray rights1 = JSONArray.fromObject(rights.get(0));
					JSONObject ylbx = JSONObject.fromObject(rights1.get(0));

					repData.element("xm", ylbx.get("jsjs4"));
					repData.element("zjhm", ylbx.get("jsjs2"));
					repData.element("gs", ylbx.get("jsjs1"));
					repData.element("ylj", new JSONObject().element("ldate", ylj.get("jsjs1"))
							.element("total", ylj.get("jsjs4")).element("month", ylj.get("jsjs2")));
					repData.element("ylbx", new JSONObject().element("ldate", ylj.get("jsjs1"))
							.element("total", ylbx.get("jsjs17")).element("month", ylbx.get("jsjs16")));
					jsonObject.element("repData", repData);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			long time2 = System.currentTimeMillis();
			jsonObject.element("elapsed", time2 - time1);
			json = jsonObject.toString();
			if (searchs.size() == 0) {
				Record record = new Record().set("tag", "Social").set("pid", userName).set("pwd", userPwd)
						.set("result", json).set("updatetime", TimeUtil.getTime(new Date()));
				this.dbUtils.insertSearch(record);
			} else {
				Record record = new Record().set("tag", "Social").set("pid", userName).set("pwd", userPwd)
						.set("result", json).set("updatetime", TimeUtil.getTime(new Date()))
						.set("id", ((Record) searchs.get(0)).getInt("id"));
				this.dbUtils.updateSearch(record);
			}
		}

		if (getPara("callback") == null) {
			renderJson(json);
		} else {
			renderJavascript(getPara("callback") + "(" + json + ")");
		}
	}
}
