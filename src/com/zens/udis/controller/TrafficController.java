package com.zens.udis.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.utils.HttpRequest;
import com.zens.udis.utils.TimeUtil;

public class TrafficController extends Controller {

	HttpRequest request = new HttpRequest();
	DBUtils dbUtils = new DBUtils();
	List<Record> status = this.dbUtils.getStatus();
	String json = "";

	public void DZJK() throws IOException {// 电子监控
		String cardno = new String(getPara("cardno").getBytes("iso-8859-1"), "utf-8");
		String fdjh = getPara("fdjh");
		String atype = getPara("atype");
		String url = ResourceBundle.getBundle("url").getString("DZJK");
		String param = "cardno=" + cardno + "&fdjh=" + fdjh + "&atype=" + atype + "&ctype=wfqk";// "cardno=沪CQZ101&fdjh=BB674098&atype=02&ctype=wfqk";
		String result = request.sendPost(url, param);

		if (result.equals("error") || result.equals("-3")) {
			renderText("{\"status\": 0,\"mes\":\"网络或参数异常！\"}");
		} else {
			renderText("{\"status\": -1,\"mes\":" + result + "}");
		}
	}

	public void WFJF() {// 违法积分
		String cardno = getPara("cardno");
		String url = ResourceBundle.getBundle("url").getString("WFJF");
		String param = "cardno=" + cardno + "&ctype=dabh";// "cardno=310021049154&ctype=dabh";
		String result = request.sendPost(url, param);

		if (result.equals("error") || result.equals("-3")) {
			renderText("{\"status\": 0,\"mes\":\"网络或参数异常！\"}");
		} else {
			renderText("{\"status\": -1,\"mes\":" + result + "}");
		}
	}

	public void JTKYE() {// 交通卡余额
		List<?> searchs = this.dbUtils.getSearch("JTKYE");
		if (((Record) this.status.get(0)).getStr("status").equals("1")) {
			json = ((Record) searchs.get(0)).getStr("result");
		} else {
			String result = "";
			try {
				String id = getPara("id");// "53655966234";//
				String url = ResourceBundle.getBundle("url").getString("JTKYE");
				String param = "id=" + id;
				result = request.sendPost(url, param).replace(" ", "").replace("status", "success").trim();
				
				if (result.equals("error")) {
					result = "{\"success\": 0,\"mes\":\"网络或参数异常！\"}";
				}else {
					if (searchs.size() == 0) {
						Record record = new Record().set("tag", "JTKYE").set("pid", id).set("result", result)
								.set("updatetime", TimeUtil.getTime(new Date()));
						this.dbUtils.insertSearch(record);
					} else {
						Record record = new Record().set("tag", "JTKYE").set("pid", id).set("result", result)
								.set("updatetime", TimeUtil.getTime(new Date()))
								.set("id", ((Record) searchs.get(0)).getInt("id"));
						this.dbUtils.updateSearch(record);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			if (getPara("callback") == null) {
				renderJson(result);
			} else {
				renderJavascript(getPara("callback") + "(" + result + ")");
			}
		}
		
	}

	public void BUS() throws IOException {
		JSONObject jsonObject = new JSONObject();
		String idnum = URLDecoder.decode(getPara("idnum"), "utf-8");
		String url = ResourceBundle.getBundle("url").getString("BUS");
		String href = ResourceBundle.getBundle("url").getString("BUSdetail");
		String param = "idnum=" + idnum;
		System.out.println(url + "?" + param);
		String result = request.sendPost(url, param);
		JSONObject sidob = JSONObject.fromObject(result);
		String sid = (String) sidob.get("sid");

		if (sid == null) {
			jsonObject.element("status", "0");
			jsonObject.element("msg", "公交路线错误！");
		} else {
			String datas = request.sendGet(href + sid, "");
			Document doc = Jsoup.parse(datas);
			Elements busDirection = doc.getElementsByAttributeValue("class", "busDirection");
			Elements times = busDirection.first().getElementsByTag("em");
			Elements station = busDirection.first().getElementsByTag("span");

			JSONObject line = new JSONObject();
			JSONObject upob = new JSONObject();
			JSONObject dowob = new JSONObject();

			upob.element("beg", times.get(0).text());
			upob.element("end", times.get(1).text());
			upob.element("begsta", station.get(0).text());
			upob.element("endsta", station.get(1).text());

			dowob.element("beg", times.get(2).text());
			dowob.element("end", times.get(3).text());
			dowob.element("begsta", station.get(2).text());
			dowob.element("endsta", station.get(3).text());

			line.element("busline", idnum);
			line.element("upgoing", upob);
			line.element("downgoing", dowob);

			Elements stations = doc.getElementsByAttributeValue("class", "stationBox");
			Elements stats = stations.first().getElementsByAttributeValue("class", "station");
			JSONObject lines = new JSONObject();
			for (int i = 0; i < stats.size(); i++) {
				Element e = stats.get(i);
				Elements names = e.getElementsByTag("span");
				lines.element(i + "", names.get(1).text());
			}

			line.element("stations", lines);

			jsonObject.element("status", "-1");
			jsonObject.element("msg", line);
		}

		renderText(jsonObject.toString());
	}

	public void file(String dd) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/zyq/MyWork/dd.txt")));

			writer.write(dd);

			writer.close();

		} catch (Exception e) {

		}
	}
}
