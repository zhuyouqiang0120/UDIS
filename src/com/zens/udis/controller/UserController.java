package com.zens.udis.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUserUtils;
import com.zens.udis.utils.Lunar;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年5月6日 下午3:26:59
 */

public class UserController extends Controller {

	JSONObject jsonObject = new JSONObject();
	DBUserUtils dbUserUtils = new DBUserUtils();

	public void getData() {
		try {
			long time1 = System.currentTimeMillis();

			String stbip = getPara("IP");
			String stbmac = getPara("MAC");
			String stbca = getPara("CA");
			//stbca = new String(stbca.getBytes("ISO8859-1"), "UTF-8");

			String type = getPara("type");
			String param = getPara("data");

			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DATE);

			JSONObject obdate = new JSONObject();

			obdate.element("time", ft.format(cal.getTime()));
			obdate.element("week", weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1]);
			obdate.element("lunar", new Lunar(cal) + "");
			obdate.element("cyclical", new Lunar(cal).cyclical() + "(" + new Lunar(cal).animalsYear() + ")");
			obdate.element("date", new JSONObject().element("year", year).element("month", month).element("day", day));

			List<Record> userinfo = dbUserUtils.getUserInfo(stbip, stbmac, stbca);
			int count = userinfo.size();
			Record record = new Record();
			if (count == 0) {
				String info = ResourceBundle.getBundle("url").getString("info");
				info = new String(info.getBytes("ISO8859-1"), "UTF-8");
				record.set("ip", stbip).set("mac", stbmac).set("ca", stbca).set("idcard", "").set("uname", "机顶盒用户").set("info", info);
				//dbUserUtils.inuserinfo(record);
			} else if (count != 0) {
				if (type != null) {
					//type = new String(type.getBytes("ISO8859-1"), "UTF-8");
					if (!type.equals("0")) {
						//param = new String(param.getBytes("ISO8859-1"), "UTF-8");
						JSONObject info = JSONObject.fromObject(userinfo.get(0).get("info"));
						info.element(type, param);
						userinfo.get(0).set("info", info.toString());
						//dbUserUtils.upuserinfo(userinfo.get(0));
					}
					userinfo = dbUserUtils.getUserInfo(stbip, stbmac, stbca);
					obdate.element("userinfo", userinfo.get(0).getColumns());
				} else {
					userinfo = dbUserUtils.getUserInfo(stbip, stbmac, stbca);
					obdate.element("userinfo", userinfo.get(0).remove("info").getColumns());
				}
			}

			jsonObject.element("status", "200 OK");
			jsonObject.element("success", "1");
			jsonObject.element("action", "user/getData");

			jsonObject.element("repData", obdate);

			long time2 = System.currentTimeMillis();
			jsonObject.element("elapsed", time2 - time1);

			if (getPara("callback") == null) {
				renderJson(jsonObject);
			} else {
				renderJavascript(getPara("callback") + "(" + jsonObject + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upUserInfo() {
		try {
			long time1 = System.currentTimeMillis();

			String stbip = getPara("IP");
			//stbip = new String(stbip.getBytes("ISO8859-1"), "UTF-8");
			String stbmac = getPara("MAC");
			//stbmac = new String(stbmac.getBytes("ISO8859-1"), "UTF-8");
			String stbca = getPara("CA");
			//stbca = new String(stbca.getBytes("ISO8859-1"), "UTF-8");
			String uname = getPara("uname");
			//uname = new String(uname.getBytes("ISO8859-1"), "UTF-8");
			String idcard = getPara("idcard");
			//idcard = new String(idcard.getBytes("ISO8859-1"), "UTF-8");
			String param = getPara("data");
			//param = new String(param.getBytes("ISO8859-1"), "UTF-8");
			JSONObject obdate = new JSONObject();

			List<Record> userinfo = dbUserUtils.getUserInfo(stbip, stbmac, stbca);
			int count = userinfo.size();
			if (count != 0) {
				userinfo.get(0).set("uname", uname).set("idcard", idcard).set("info", param.toString());
				dbUserUtils.upuserinfo(userinfo.get(0));
			}

			userinfo = dbUserUtils.getUserInfo(stbip, stbmac, stbca);
			obdate.element("userinfo", userinfo.get(0).getColumns());

			jsonObject.element("status", "200 OK");
			jsonObject.element("success", "1");
			jsonObject.element("action", "user/getData");

			jsonObject.element("repData", obdate);   

			long time2 = System.currentTimeMillis();
			jsonObject.element("elapsed", time2 - time1);

			if (getPara("callback") == null) {
				renderJson(jsonObject);
			} else {
				renderJavascript(getPara("callback") + "(" + jsonObject + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
