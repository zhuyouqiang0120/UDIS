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
 * 用户建凯物业费用查询
 * 
 * @author zyq
 *
 */
public class JianKaiController extends Controller {

	HttpRequest request = new HttpRequest();
	DBUtils dbUtils = new DBUtils();
	List<Record> status = this.dbUtils.getStatus();

	public void check() {
		long time1 = System.currentTimeMillis();

		JSONObject jsonObject = new JSONObject();
		String ca = getPara("ca");

		jsonObject.element("action", "jiankai/check");
		jsonObject.element("status", "200 OK");
		jsonObject.element("success", "0");
		jsonObject.element("type", "1");
		jsonObject.element("msg", "你已拖欠“代收水费”共计11.1元，请及时缴纳！");
		
		//jsonObject.element("type", "0");
		//jsonObject.element("msg", "无欠费记录");

		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);
		if (getPara("callback") == null) {
			renderJson(jsonObject);
		} else {
			renderJavascript(getPara("callback") + "(" + jsonObject + ")");
		}
	}

}