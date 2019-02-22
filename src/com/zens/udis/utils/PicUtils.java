package com.zens.udis.utils;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;

import net.sf.json.JSONObject;

public class PicUtils {

	JSONObject jsonObject = new JSONObject();
	DBUtils dbUtils = new DBUtils();

	public String getImgIP() {
		List<Record> list = dbUtils.getPicIP();

		return list.get(0).getStr("ip") ;
	}
	
	public String getImgPath() {
		List<Record> list = dbUtils.getPicIP();

		return list.get(0).getStr("path");
	}
}
