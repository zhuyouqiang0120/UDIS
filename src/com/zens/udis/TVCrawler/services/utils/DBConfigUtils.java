package com.zens.udis.TVCrawler.services.utils;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @2016年3月1日 下午3:09:58
 *
 */

public class DBConfigUtils {
	// private static final String tbl_column = "tbl_column";
	private static final String tbl_config = "tbl_config";

	public String getConfig(String key) {
		String sql = "select * from " + tbl_config + " where key = '" + key + "'";
		// System.out.println(sql);
		String value = Db.find(sql).get(0).getStr(key);
		return value;
	}

	public boolean inuserinfo(Record record) {
		return Db.save(tbl_config, record);
	}

	public boolean upConfig(String key, String value) {
		String sql = "update " + tbl_config + " set value = '" + value + "' where key = '" + key + "'";
		return Db.queryBoolean(sql);
	}
}
