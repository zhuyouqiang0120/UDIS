package com.zens.udis.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.utils.HttpRequest;
import com.zens.udis.utils.TimeUtil;

/**
 * 
 * zyq zhuyq@zensvision.com 2016年3月18日 下午12:57:19
 */
public class PublicController extends Controller {

	HttpRequest request = new HttpRequest();
	DBUtils dbUtils = new DBUtils();
	List<Record> status = this.dbUtils.getStatus();

	public void GJJ() {
		long time1 = System.currentTimeMillis();
		String json = "";
		JSONObject jsonObject = new JSONObject();

		String username = getPara("username");
		String pwd = getPara("pwd");

		List<?> searchs = this.dbUtils.getSearch("GJJ");
		if (((Record) this.status.get(0)).getStr("status").equals("1")) {
			System.out.println(11);
			json = ((Record) searchs.get(0)).getStr("result");
		} else {
			String act = "do_login";
			String url = ResourceBundle.getBundle("url").getString("GJJ");
			String detailurl = ResourceBundle.getBundle("url").getString("GJJdetail");
			String param = "act=" + act + "&username=" + username + "&pwd=" + pwd;
			String cookie = this.request.getcookie(url, param);
			String result = this.request.sendGet(detailurl, cookie);

			Document doc = Jsoup.parse(result);

			Elements users = doc.getElementsByAttributeValue("class", "ui-bar ui-body-a user");
			if (users.size() != 0) {
				JSONObject object = new JSONObject();
				Elements ps = users.first().getElementsByTag("p");
				object.element("username", ps.first().text());
				Elements date = users.first().getElementsByTag("span");
				object.element("enddate", date.first().text());

				Elements uls = doc.getElementsByTag("ul");
				Elements lips = uls.first().getElementsByTag("p");
				object.element("state", lips.get(1).text());
				object.element("balance", lips.get(2).text());
				object.element("monthbal", lips.get(3).text());
				object.element("lastbal", lips.get(4).text());
				object.element("account", lips.get(5).text());
				object.element("opentime", lips.get(6).text());
				object.element("company", lips.get(7).text());

				jsonObject.element("status", "200 OK");
				jsonObject.element("success", "1");
				jsonObject.element("action", "public/GJJ");
				jsonObject.element("repData", object);
			} else {
				jsonObject.element("status", "404");
				jsonObject.element("success", "0");
				jsonObject.element("action", "public/GJJ");
				jsonObject.element("servertype", "公积金");
				jsonObject.element("repData", "用户名或密码错误！");
			}

			long time2 = System.currentTimeMillis();
			jsonObject.element("elapsed", time2 - time1);
			json = jsonObject.toString();

			if (searchs.size() == 0) {
				Record record = new Record().set("tag", "GJJ").set("pid", username).set("pwd", pwd).set("result", json)
						.set("updatetime", TimeUtil.getTime(new Date()));
				this.dbUtils.insertSearch(record);
			} else {
				Record record = new Record().set("tag", "GJJ").set("pid", username).set("pwd", pwd).set("result", json)
						.set("updatetime", TimeUtil.getTime(new Date()))
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

	public void YLJ() throws IOException {
		long time1 = System.currentTimeMillis();
		String json = "";
		JSONObject jsonObject = new JSONObject();
		String idnum = getPara("idnum");
		String idpwd = getPara("idpwd");

		List<?> searchs = this.dbUtils.getSearch("YLJ");
		if (((Record) this.status.get(0)).getStr("status").equals("1")) {
			json = ((Record) searchs.get(0)).getStr("result");
		} else {
			String url = ResourceBundle.getBundle("url").getString("YLJ");
			String param = "idnum=" + idnum + "&idpwd=" + idpwd;
			String data = this.request.sendPost(url, param);
			JSONObject object = JSONObject.fromObject(data);
			String msg = (String) object.get("msg");
			String code = (String) object.get("errcode");

			if (code.equals("200")) {
				jsonObject.element("status", "1");
				String token = ResourceBundle.getBundle("url").getString("YLJtoken");
				String result = this.request.sendGet(token + msg, "");

				Document doc = Jsoup.parse(result);
				Elements cbs = doc.getElementsByAttributeValue("class", "box person_info");
				JSONObject userob = new JSONObject();
				userob.element("name", cbs.first().child(0).text().split(":")[1]);
				userob.element("account", cbs.first().child(1).text().split(":")[1]);
				userob.element("company", cbs.first().child(2).text().split(":")[1]);

				Elements pays = doc.getElementsByAttributeValue("class", "recentPay_result");
				Elements sumos = pays.get(2).getElementsByAttributeValue("class", "sumo");
				JSONObject payob = new JSONObject();
				payob.element("type", "养老金");
				payob.element("enddate", sumos.get(0).text());
				payob.element("months", sumos.get(1).text());
				payob.element("balance", sumos.get(2).text());

				JSONObject msgob = new JSONObject();
				msgob.element("user", userob);
				msgob.element("pay", payob);

				jsonObject.element("status", "200 OK");
				jsonObject.element("success", "1");
				jsonObject.element("action", "YLJ");
				jsonObject.element("servertype", "养老金");
				jsonObject.element("repData", msgob);
			} else {
				jsonObject.element("status", "404");
				jsonObject.element("success", "0");
				jsonObject.element("action", "YLJ");
				jsonObject.element("servertype", "养老金");
				jsonObject.element("repData", "用户名或密码错误！");
			}
			long time2 = System.currentTimeMillis();
			jsonObject.element("elapsed", time2 - time1);
			json = jsonObject.toString();
			System.out.println(searchs.size());
			if (searchs.size() == 0) {
				Record record = new Record().set("tag", "YLJ").set("pid", idnum).set("pwd", idpwd).set("result", json)
						.set("updatetime", TimeUtil.getTime(new Date()));
				this.dbUtils.insertSearch(record);
			} else {
				Record record = new Record().set("tag", "YLJ").set("pid", idnum).set("pwd", idpwd).set("result", json)
						.set("updatetime", TimeUtil.getTime(new Date()))
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

	public void YB() {
		String json = "";
		JSONObject jsonObject = new JSONObject();
		String idnum = getPara("idnum");
		String idpwd = getPara("idpwd");

		List<?> searchs = this.dbUtils.getSearch("YB");
		if (((Record) this.status.get(0)).getStr("status").equals("1")) {
			json = ((Record) searchs.get(0)).getStr("result");
		} else {
			String url = ResourceBundle.getBundle("url").getString("YB");
			String param = "idnum=" + idnum + "&idpwd=" + idpwd;
			System.out.println(url + "?" + param);
			String data = this.request.sendPost(url, param);

			System.out.println(data);
			JSONObject object = JSONObject.fromObject(data);
			String msg = (String) object.get("data");
			String code = (String) object.get("errcode");

			if (code.equals("200")) {
				jsonObject.element("status", "1");
				String token = ResourceBundle.getBundle("url").getString("YBtoken");
				String result = this.request.sendGet(token + msg, "");

				Document doc = Jsoup.parse(result);
				Elements arts = doc.getElementsByTag("article");
				Elements ps = arts.first().getElementsByTag("p");
				JSONObject msgob = new JSONObject();

				msgob.element("account", idnum);
				msgob.element("workstate", ps.get(0).getElementsByTag("span").text());
				msgob.element("acstate", ps.get(1).getElementsByTag("span").text());
				msgob.element("type", ps.get(2).getElementsByTag("span").text());
				msgob.element("city", ps.get(3).getElementsByTag("span").text());
				msgob.element("batype", ps.get(4).getElementsByTag("span").text());
				msgob.element("medtype", ps.get(5).getElementsByTag("span").text());
				msgob.element("mednum", ps.get(7).getElementsByTag("span").text());
				msgob.element("balance", ps.get(8).getElementsByTag("span").text());

				jsonObject.element("msg", msgob);
			} else {
				jsonObject.element("status", "0");
				jsonObject.element("msg", "用户名或密码错误！");
			}
			json = jsonObject.toString();
			if (searchs.size() == 0) {
				Record record = new Record().set("tag", "YB").set("pid", idnum).set("pwd", idpwd).set("result", json)
						.set("updatetime", TimeUtil.getTime(new Date()));
				this.dbUtils.insertSearch(record);
			} else {
				Record record = new Record().set("tag", "YB").set("pid", idnum).set("pwd", idpwd).set("result", json)
						.set("updatetime", TimeUtil.getTime(new Date()))
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

	public void JHYY() {
		JSONObject object = new JSONObject();
		String bookid = getPara("bookid");
		String cardid = getPara("cardid");
		String url = ResourceBundle.getBundle("url").getString("JHYY");
		String param = "bookid=" + bookid + "&cardid=" + cardid;
		String data = this.request.sendPost(url, param);

		Document doc = Jsoup.parse(data);
		Elements arts = doc.getElementsByTag("article");
		object.element("status", "1");
		object.element("msg", arts.first().text());
		renderText(object.toString());
	}

	public void CRJYY() {
		String cknumber = getPara("cknumber");
		String ckname = getPara("ckname");
		String url = ResourceBundle.getBundle("url").getString("CRJ");
		String param = "ac=getYyxx&cknumber=" + cknumber + "&ckname=" + ckname;
		url = url + "?" + param;
		String data = this.request.sendGet(url, "");
		if (getPara("callback") == null) {
			renderJson(data);
		} else {
			renderJavascript(getPara("callback") + "(" + data + ")");
		}
	}

	public void CRJJD() {
		String cknumber = getPara("cknumber");
		String ckzwxm = getPara("ckzwxm");
		String url = ResourceBundle.getBundle("url").getString("CRJ");
		String param = "ac=getFlowInfo&cknumber=" + cknumber + "&ckzwxm=" + ckzwxm;
		url = url + "?" + param;
		String data = this.request.sendGet(url, "");
		System.out.println(data);
		if (getPara("callback") == null) {
			renderJson(data);
		} else {
			renderJavascript(getPara("callback") + "(" + data + ")");
		}
	}

	public void Tax() throws IOException {
		long time1 = System.currentTimeMillis();
		JSONObject jsonObject = new JSONObject();
		String idnum = getPara("idnum");
		String idpwd = getPara("idpwd");
		String url = ResourceBundle.getBundle("url").getString("YLJ");
		String param = "idnum=" + idnum + "&idpwd=" + idpwd;
		String data = this.request.sendPost(url, param);
		JSONObject object = JSONObject.fromObject(data);
		String msg = (String) object.get("msg");
		String code = (String) object.get("errcode");

		if (code.equals("200")) {
			jsonObject.element("status", "1");
			String token = ResourceBundle.getBundle("url").getString("YLJtoken");
			String result = this.request.sendGet(token + msg, "");

			Document doc = Jsoup.parse(result);
			Elements cbs = doc.getElementsByAttributeValue("class", "box person_info");
			JSONObject userob = new JSONObject();
			userob.element("name", cbs.first().child(0).text().split(":")[1]);
			userob.element("account", cbs.first().child(1).text().split(":")[1]);
			userob.element("company", cbs.first().child(2).text().split(":")[1]);

			Elements pays = doc.getElementsByAttributeValue("class", "recentPay_result");
			Elements sumos = pays.get(2).getElementsByAttributeValue("class", "sumo");
			JSONObject payob = new JSONObject();
			payob.element("type", "养老金");
			payob.element("enddate", sumos.get(0).text());
			payob.element("months", sumos.get(1).text());
			payob.element("balance", sumos.get(2).text());

			JSONObject msgob = new JSONObject();
			msgob.element("user", userob);
			msgob.element("pay", payob);

			jsonObject.element("status", "200 OK");
			jsonObject.element("success", "1");
			jsonObject.element("action", "YLJ");
			jsonObject.element("servertype", "养老金");
			jsonObject.element("repData", msgob);
		} else {
			jsonObject.element("status", "404");
			jsonObject.element("success", "0");
			jsonObject.element("action", "YLJ");
			jsonObject.element("servertype", "养老金");
			jsonObject.element("repData", "用户名或密码错误！");
		}
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);
		if (getPara("callback") == null) {
			renderJson(jsonObject.toString());
		} else {
			renderJavascript(getPara("callback") + "(" + jsonObject.toString() + ")");
		}
	}
}