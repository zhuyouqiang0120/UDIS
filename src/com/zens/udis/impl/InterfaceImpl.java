package com.zens.udis.impl;

import java.util.Date;

import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.entity.NewsEntity;

/**
 * 数据入库
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月31日 下午4:57:43
 */
public class InterfaceImpl {

	DBUtils dbUtils = new DBUtils();

	/*
	public boolean insertWeather(WeatherEntity weatherEntity) {// 写入天气预报
		@SuppressWarnings("deprecation")
		Record record = new Record().set("datetime", weatherEntity.getDatetime())
				.set("direction", weatherEntity.getDirection()).set("speed", weatherEntity.getSpeed())
				.set("tempe", weatherEntity.getTempe()).set("weather", weatherEntity.getWeather())
				.set("weatherpic", weatherEntity.getWeatherpic()).set("pic", weatherEntity.getPic())
				.set("date", weatherEntity.getDate()).set("week", weatherEntity.getWeek())
				.set("createtime", new Date().toLocaleString()).set("remark", "sh");
		return dbUtils.insertWeather(record);
	}

	
	 * public boolean insertAqi(AqiEntity aqiEntity) {// 天气质量
	 * 
	 * @SuppressWarnings("deprecation") Record record = new
	 * Record().set("image", aqiEntity.getImage()).set("color",
	 * aqiEntity.getColor()) .set("aq", aqiEntity.getAq()).set("health",
	 * aqiEntity.getHealth()).set("method", aqiEntity.getMethod()) .set("lblpp",
	 * aqiEntity.getLblpp()).set("lblND", aqiEntity.getLblND()) .set("aqi24hr",
	 * aqiEntity.getAqi24hr()).set("flbltime", aqiEntity.getFlbltime())
	 * .set("flblcontent", aqiEntity.getFlblcontent()).set("createtime", new
	 * Date().toLocaleString()) .set("remark", "sh"); return
	 * dbUtils.insertAqi(record); }
	 */
	
	public boolean insertWeather(String json) {// 写入天气预报
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 2).set("data", json).set("updatetime", new Date().toLocaleString())
		.set("createtime", new Date().toLocaleString());
		return dbUtils.insertWeather(record);
	}
	
	public boolean insertAqi(String json) {// 天气质量
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 1).set("data", json).set("updatetime", new Date().toLocaleString())
				.set("createtime", new Date().toLocaleString());
		return dbUtils.insertAqi(record);
	}

	public int deleteScenicl() {// 英文解沪语
		return dbUtils.deleteScienil();
	}

	/*
	public boolean insertScenicl(Scenicl scenicl) {// 景区实况
		@SuppressWarnings("deprecation")
		Record record = new Record().set("time", scenicl.getTime()).set("rank", scenicl.getRank())
				.set("name", scenicl.getName()).set("maxNum", scenicl.getMaxNum()).set("image", scenicl.getImage())
				.set("startTime", scenicl.getStartTime()).set("Switch", scenicl.getSwitch())
				.set("initial", scenicl.getInitial()).set("SSD", scenicl.getSSD()).set("county", scenicl.getCounty())
				.set("location_x", scenicl.getLocation_x()).set("location_y", scenicl.getLocation_y())
				.set("r_time", scenicl.getR_time()).set("end_time", scenicl.getEnd_time())
				.set("info", scenicl.getInfo()).set("num", scenicl.getNum()).set("type", scenicl.getType())
				.set("code", scenicl.getCode()).set("createtime", new Date().toLocaleString());
		return dbUtils.insertScenicl(record);
	}
*/
	
	public boolean insertScenicl(String json) {// 景区实况
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 3).set("data", json).set("updatetime", new Date().toLocaleString())
		.set("createtime", new Date().toLocaleString());
		return dbUtils.insertScenicl(record);
	}
	
	public int deleteTalk() {// 英文解沪语
		return dbUtils.deleteTalk();
	}

	/*
	public boolean insertTalk(TalkEntity talkEntity) {// 英文解沪语
		@SuppressWarnings("deprecation")
		Record record = new Record().set("title", talkEntity.getTitle()).set("ftitle", talkEntity.getFtitle())
				.set("tid", talkEntity.getTid()).set("keyboard", talkEntity.getKeyboard())
				.set("newstime", talkEntity.getNewstime()).set("titlepic", talkEntity.getTitlepic())
				.set("smalltext", talkEntity.getSmalltext()).set("titleurl", talkEntity.getTitleurl())
				.set("createtime", new Date().toLocaleString()).set("remark", talkEntity.getRemark());
		return dbUtils.insertTalk(record);
	}
*/
	public boolean insertTalk(String json) {// 英文解沪语
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 4).set("data", json).set("updatetime", new Date().toLocaleString())
		.set("createtime", new Date().toLocaleString());
		return dbUtils.insertTalk(record);
	}
	public int deleteBuzz() {// 英文翻热词
		return dbUtils.deleteBuzz();
	}

	/*
	public boolean insertBuzz(BuzzEntity buzzEntity) {// 英文翻热词
		@SuppressWarnings("deprecation")
		Record record = new Record().set("title", buzzEntity.getTitle()).set("ftitle", buzzEntity.getFtitle())
				.set("tid", buzzEntity.getTid()).set("keyboard", buzzEntity.getKeyboard())
				.set("newstime", buzzEntity.getNewstime()).set("titlepic", buzzEntity.getTitlepic())
				.set("smalltext", buzzEntity.getSmalltext()).set("titleurl", buzzEntity.getTitleurl())
				.set("createtime", new Date().toLocaleString()).set("remark", buzzEntity.getRemark());
		return dbUtils.insertBuzz(record);
	}
*/
	public boolean insertBuzz(String json) {// 英文翻热词
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 5).set("data", json).set("updatetime", new Date().toLocaleString())
		.set("createtime", new Date().toLocaleString());
		return dbUtils.insertBuzz(record);
	}
	
	public int deleteWhat() {// 文艺早知道
		return dbUtils.deleteWhat();
	}

	/*
	public boolean insertWhat(WhatEntity whatEntity) {// 文艺早知道
		@SuppressWarnings("deprecation")
		Record record = new Record().set("title", whatEntity.getTitle()).set("ftitle", whatEntity.getFtitle())
				.set("tid", whatEntity.getTid()).set("keyboard", whatEntity.getKeyboard())
				.set("newstime", whatEntity.getNewstime()).set("titlepic", whatEntity.getTitlepic())
				.set("smalltext", whatEntity.getSmalltext()).set("titleurl", whatEntity.getTitleurl())
				.set("createtime", new Date().toLocaleString()).set("remark", whatEntity.getRemark());
		return dbUtils.insertWhat(record);
	}
*/
	public boolean insertWhat(String json) {// 文艺早知道
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 6).set("data", json).set("updatetime", new Date().toLocaleString())
		.set("createtime", new Date().toLocaleString());
		return dbUtils.insertWhat(record);
	}
	
/*
	public boolean insertRoad(RoadEntity roadEntity) {// 文艺早知道
		Record record = new Record().set("city", roadEntity.getCity()).set("velocity", roadEntity.getVelocity())
				.set("intercity", roadEntity.getIntercity()).set("createtime", roadEntity.getCreatetime())
				.set("remark", roadEntity.getRemark());
		return dbUtils.insertRoad(record);
	}
*/
	public boolean insertRoad(String json) {// 文艺早知道
		@SuppressWarnings("deprecation")
		Record record = new Record().set("iid", 7).set("data", json).set("updatetime", new Date().toLocaleString())
				.set("createtime", new Date().toLocaleString());
		return dbUtils.insertRoad(record);
	}
	
	public boolean insertNews(NewsEntity newsEntity) {
		// TODO Auto-generated method stub
		return false;
	}

}
