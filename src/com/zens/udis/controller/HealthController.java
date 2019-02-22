package com.zens.udis.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.grab.GrabHealth;
import com.zens.udis.utils.TimeUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author zhl
 * @mail zhuhl@zensvision.com
 * @time 2016年4月27日 上午10:34:27
 */
public class HealthController extends Controller {
	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();
	List<Record> status = this.dbUtils.getStatus();
	String json = "";

	public void getData() {
		long time1 = System.currentTimeMillis();
		String userName = getPara("idnum");
		String userPwd = getPara("idpwd");

		List<?> searchs = this.dbUtils.getSearch("Health");
		if (((Record) this.status.get(0)).getStr("status").equals("1")) {
			json = ((Record) searchs.get(0)).getStr("result");
		} else {
			jsonObject.element("status", "200 OK");
			jsonObject.element("success", "1");
			jsonObject.element("action", "health/getData");
			String result = new GrabHealth().grabHealth(userName, userPwd);

			if (result.equals("error")) {
				jsonObject.element("success", "0");
				jsonObject.element("repData", "用户名或密码错误！");
			} else {
				JSONObject ob = JSONObject.fromObject(result);
				ob.remove("oestate");
				ob.remove("accumaccount");
				ob.remove("form");
				ob.remove("apportionaccount");
				ob.remove("yearpay");
				ob.remove("sstate");
				ob.remove("type");
				ob.remove("respectivedistrict");
				ob.remove("hstate");
				ob.remove("pacccinterest");
				ob.remove("pidnumber");
				ob.remove("paccountlab");
				ob.remove("pstate");
				ob.remove("pintoaccamount");
				ob.remove("pname");
				ob.remove("pcaccount");
				ob.remove("ptaccount");
				jsonObject.element("repData", ob);
			}

			long time2 = System.currentTimeMillis();
			jsonObject.element("elapsed", time2 - time1);
			json = jsonObject.toString();
			if (searchs.size() == 0) {
				Record record = new Record().set("tag", "Health").set("pid", userName).set("pwd", userPwd)
						.set("result", json).set("updatetime", TimeUtil.getTime(new Date()));
				this.dbUtils.insertSearch(record);
			} else {
				Record record = new Record().set("tag", "Health").set("pid", userName).set("pwd", userPwd)
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
