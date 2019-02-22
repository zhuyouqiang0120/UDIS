package com.zens.udis.db;

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

public class DBUserUtils {
	// private static final String tbl_column = "tbl_column";
	private static final String tbl_userinfo = "tbl_userinfo";

	public List<Record> getUserInfo(String ip, String mac, String ca) {
		String sql = "";
		if (!ca.equals("") && !mac.equals("") && !ip.equals("")) {
			sql = "select * from " + tbl_userinfo + " where ip = '" + ip + "' and mac = '" + mac + "' and ca = '" + ca + "' order by id desc";
		} else if (!ca.equals("")) {
			sql = "select * from " + tbl_userinfo + " where ca = '" + ca + "' order by id desc";
		} else if (ca.equals("") && !mac.equals("")) {
			sql = "select * from " + tbl_userinfo + " where mac = '" + mac + "' order by id desc";
		} else if (ca.equals("") && mac.equals("") && !ip.equals("")) {
			sql = "select * from " + tbl_userinfo + " where ip = '" + ip + "' order by id desc";;
		}
		//System.out.println(sql);
		return Db.find(sql);
	}

	public boolean inuserinfo(Record record) {
		return Db.save(tbl_userinfo, record);
	}

	public boolean upuserinfo(Record record) {
		return Db.update(tbl_userinfo, record);
	}
}
