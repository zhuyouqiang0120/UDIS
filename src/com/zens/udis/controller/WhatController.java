package com.zens.udis.controller;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.utils.HttpRequest;

import gui.ava.html.image.generator.HtmlImageGenerator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月31日 下午4:57:59
 */

public class WhatController extends Controller {

	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();

	public void getData() {
		try {
			if(getParaToInt("pageNum") != null) {
				long time1 = System.currentTimeMillis();
				int pageNum = getParaToInt("pageNum");
				int pageIndex = getParaToInt("pageIndex");
				jsonObject.element("status", "200 OK");
				jsonObject.element("success", "1");
				jsonObject.element("pageIndex", getParaToInt("pageIndex"));
				jsonObject.element("pageNum", pageNum);
				jsonObject.element("action", "what/getData");

				List<Record> list = dbUtils.getWhat(6);
				int count = JSONArray.fromObject(list.get(0).get("data")).size();

				jsonObject.element("totalResults", count);
				JSONArray jsonArray = JSONArray.fromObject(list.get(0).get("data"));
				JSONArray resData = new JSONArray();
				int index = (pageIndex <= 0 ? 0 : pageIndex);
				System.out.println(index + "---" + index* pageNum + "---" + (index+1)  * pageNum);
				for (int i = index * pageNum; i < (index+1)  * pageNum; i++) {
					if (i >= count)
						break;
					resData.add(jsonArray.get(i));
				}

				jsonObject.element("totalPage", count % pageNum == 0 ? count / pageNum : count / pageNum + 1);
				jsonObject.element("repData", resData);

				long time2 = System.currentTimeMillis();
				jsonObject.element("elapsed", time2 - time1);
			}else {
				long time1 = System.currentTimeMillis();
				jsonObject.element("status", "200 OK");
				jsonObject.element("success", "1");
				jsonObject.element("action", "what/getData");

				List<Record> list = dbUtils.getWhat(6);
				int count = list.size();

				jsonObject.element("totalResults", count);
				jsonObject.element("repData", JSONArray.fromObject(list.get(0).get("data")));

				long time2 = System.currentTimeMillis();
				jsonObject.element("elapsed", time2 - time1);
			}
			

			if (getPara("callback") == null) {
				renderJson(jsonObject);
			} else {
				renderJavascript(getPara("callback") + "(" + jsonObject + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) throws IOException, IOException, URISyntaxException, AWTException, InterruptedException {
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();  
        String htmlstr = "<div><img src='http://avatar.csdn.net/2/3/A/3_u010304626.jpg'><p>编号:10001</p></div>";  
        imageGenerator.loadHtml(new HttpRequest().sendGet("http://www.kankanews.com/a/2018-06-15/0038479669.shtml", "")); // 加载html源码内容  
       // imageGenerator.loadUrl("http://www.kankanews.com/a/2018-06-15/0038479669.shtml");
        imageGenerator.getBufferedImage(); // 生成图片字符流  
  
        imageGenerator.saveAsImage("/Users/zyq/Desktop/10001.jpg"); // 保存到本地  
        
        //保存图片  
	}
}
