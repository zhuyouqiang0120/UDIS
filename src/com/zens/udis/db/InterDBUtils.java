package com.zens.udis.db;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.utils.StringUtil;
import com.zens.udis.utils.TimeUtil;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @2016年3月1日 下午3:09:58
 *
 */

public class InterDBUtils {
	private static final String tbl_interface = "tbl_interface";
	private static final String tbl_data = "tbl_data";

	public List<Record> getWeather(int iid) {
		String sql = "select * from " + tbl_interface + " where iid = " + iid + " order by id desc limit 0,1";
		return Db.find(sql);
	}

	public boolean insertWeather(Record record) {
		return Db.save(tbl_interface, record);
	}

	public List<Record> getInters(int aid) {
		String sql = "";
		if (aid == 0) {
			sql = "select * from " + tbl_interface + " where isdelete = 0 order by id ";
		} else {
			sql = "select * from " + tbl_interface + " where isdelete = 0 and aid = " + aid + " order by id ";
		}
		return Db.find(sql);
	}

	public boolean insertInter(Map<String, String[]> map) {
		Record record = new Record();

		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];

			if (!key.equals("id") && StringUtil.isAvailable(value)) {
				record.set(key, value);
			}

			if (key.equals("createtime")) {
				record.set("createtime", TimeUtil.getCurrTime());
			}

				record.set("updatetime", TimeUtil.getCurrTime());
		}
		return Db.save(tbl_interface, "id", record);
	}

	public boolean updateInter(Record record) {
		return Db.update(tbl_interface, "id", record);
	}

	/**
	 * 表单传递参数
	 * 
	 * @param map
	 *            表单参数
	 * @return
	 */
	public boolean updateInter(Map<String, String[]> map) {
		Record record = new Record();

		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];

			if (StringUtil.isAvailable(value)) {
				record.set(key, value);
			}

			if (key.equals("updatetime")) {
				record.set("updatetime", TimeUtil.getCurrTime());
			}
		}
		return Db.update(tbl_interface, "id", record);
	}

	public boolean removeInters(String ids) {
		if (ids.isEmpty()) {
			return false;
		}
		return Db.update("update " + tbl_interface + " set isdelete = 1 where id in (" + ids + ")") > 0;
	}

	public boolean deleteInters(String ids) {
		if (ids.isEmpty()) {
			return false;
		}
		return Db.update("delete from " + tbl_interface + " where id in (" + ids + ")") > 0;
	}

	public List<Record> getData(int iid) {
		String sql = "select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1";
		return Db.find(sql);
	}
}
