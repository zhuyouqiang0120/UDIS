package com.zens.udis.controller;

import java.util.Date;

import com.jfinal.core.Controller;
import com.zens.udis.db.DBUtils;
import com.zens.udis.grab.GrabAqi;
import com.zens.udis.grab.GrabBuzz;
import com.zens.udis.grab.GrabRoad;
import com.zens.udis.grab.GrabScenicl;
import com.zens.udis.grab.GrabTalk;
import com.zens.udis.grab.GrabWeather;
import com.zens.udis.grab.GrabWhat;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月31日 下午4:57:59
 */

public class GrabController extends Controller {

	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();

	@SuppressWarnings("deprecation")
	public void getData() {
		System.out.println("GRAB!!!!!!!!!!!!");
		// new GrabAqi().grabAqi();

		long time1 = System.currentTimeMillis();

		try {
			new GrabAqi().grabAqi();
			System.out.println("执行AQI  " + new Date().toLocaleString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		new GrabWeather().grabWeather();
		System.out.println("执行Weather  " + new Date().toLocaleString());
		//new GrabScenicl().grapScenicl();
		//System.out.println("执行Scenicl  " + new Date().toLocaleString());
		new GrabTalk().grabTalk();
		System.out.println("执行GrabTalk  " + new Date().toLocaleString());
		new GrabBuzz().grabBuzz();
		System.out.println("执行GrabBuzz  " + new Date().toLocaleString());
		new GrabWhat().grabWhat();
		System.out.println("执行GrabWhat  " + new Date().toLocaleString());
		new GrabRoad().grabRoad();
		System.out.println("执行GrabRoad  " + new Date().toLocaleString());
		jsonObject.element("status", "抓取成功！");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public void grabWeather() {
		System.out.println("GRAB!!!!!!!!!!!!");
		// new GrabAqi().grabAqi();

		long time1 = System.currentTimeMillis();

		//new GrabAqi().grabAqi();
		//System.out.println("执行AQI  " + new Date().toLocaleString());

		new GrabWeather().grabWeather();
		System.out.println("执行Weather  " + new Date().toLocaleString());
		new GrabRoad().grabRoad();
		System.out.println("执行GrabRoad  " + new Date().toLocaleString());
		//new GrabScenicl().grapScenicl();
		//System.out.println("执行Scenicl  " + new Date().toLocaleString());
		jsonObject.element("status", "抓取成功！");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
}
