package com.zens.udis.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jfinal.core.Controller;
import com.zens.udis.utils.GetPrograms;
import com.zens.udis.utils.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月22日 上午9:36:16
 */

public class TvController extends Controller {

	JSONObject jsonObject = new JSONObject();
	GetPrograms getPrograms = new GetPrograms();
	HttpRequest request = new HttpRequest();

	public void getData() throws IOException {

		long time1 = System.currentTimeMillis();

		String channel = "CCTV1";
		String[] week = { "zhouyi", "zhouer", "zhousan", "zhousi", "zhouwu", "zhouliu", "zhouri" };

		// String url =
		// "http://www.manmankan.com/dy2013/jiemubiao/1/zhousi.shtml";
		String channelurl = ResourceBundle.getBundle("url").getString("EPG");
		String channels = request.sendGet(channelurl, "");
		JSONArray array = JSONArray.fromObject(JSONObject.fromObject(channels).get("data"));

		for (int i = 0; i < array.size(); i++) {
			JSONObject o = JSONObject.fromObject(array.get(i));
			channel = o.getString("GUID");

			File file = new File(savepath(channel));
			if (file.exists()) {
				file.delete();
			}

			for (int j = 0; j < week.length; j++) {
				String url = "http://www.manmankan.com/dy2013/jiemubiao/" + o.getString("channelno") + "/" + week[j]
						+ ".shtml";
				String result = getPrograms.sendGet(url, "");
				parserHTML(channel, result, j);
			}

		}

		String syncurl = ResourceBundle.getBundle("url").getString("Sync");
		request.sendGet(syncurl, "");// 通知LEMON EPG抓取成功

		jsonObject.element("status", "200 OK");
		jsonObject.element("success", "1");
		jsonObject.element("action", "getData");
		jsonObject.element("servertype", "EPG抓取");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public void parserHTML(String channel, String program, int w) {

		StringBuffer buffer = new StringBuffer();

		try {
			Document doc = Jsoup.parse(program);

			Elements times = doc.getElementsByAttributeValue("class", "time");// 获取日期

			buffer.append(times.get(w).text());
			buffer.append("\n");
			buffer.append("\n");

			Elements lprogram = doc.getElementsByAttributeValue("class", "jmb_left");// 上午节目单
			Elements lpras = lprogram.first().getElementsByTag("li");
			for (int i = 1; i < lpras.size(); i++) {
				String pro = lpras.get(i).text();
				buffer.append(pro);
				buffer.append("\n");
			}

			Elements rprogram = doc.getElementsByAttributeValue("class", "jmb_right");// 下午节目单
			Elements rpras = rprogram.first().getElementsByTag("li");
			for (int i = 1; i < rpras.size(); i++) {
				String pro = rpras.get(i).text();
				buffer.append(pro);
				buffer.append("\n");
			}

			buffer.append("\n");

			WriteFile(channel, buffer.toString());// 写入文件

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WriteFile(String channel, String pro) throws Exception {// 写入文件
		File file = new File(savepath(channel));
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file, true);
		writer.write(pro);
		writer.close();
	}

	public String savepath(String channel) {// 获取文件保存路径
		String path = ResourceBundle.getBundle("url").getString("TV");
		return path + channel + ".txt";
	}
}
