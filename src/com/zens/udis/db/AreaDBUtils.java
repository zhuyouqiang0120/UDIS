package com.zens.udis.db;

import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;
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

public class AreaDBUtils {
	private static final String tbl_area = "tbl_area";

	public List<Record> getWeather(int iid) {
		String sql = "select * from " + tbl_area + " where iid = " + iid + " order by id desc limit 0,1";
		return Db.find(sql);
	}

	public boolean insertWeather(Record record) {
		return Db.save(tbl_area, record);
	}

	public List<Record> getAreas(int i) {
		String sql = "select * from " + tbl_area + " where isdelete = " + i + " order by id ";
		return Db.find(sql);
	}

	public boolean insertArea(Map<String, String[]> map) {
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

		return Db.save(tbl_area, "id", record);
	}

	public boolean updateArea(Record record) {
		return Db.update(tbl_area, "id", record);
	}

	/**
	 * 表单传递参数
	 * 
	 * @param map
	 *            表单参数
	 * @return
	 */
	public boolean updateArea(Map<String, String[]> map) {
		Record record = new Record();
System.out.println(JsonKit.toJson(map));
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
		return Db.update(tbl_area, "id", record);
	}

	public boolean removeAreas(String ids) {
		if (ids.isEmpty()) {
			return false;
		}

		return Db.update("update " + tbl_area + " set isdelete = 1 where id in (" + ids + ")") > 0;
	}

	public boolean deleteAreas(String ids) {
		if (ids.isEmpty()) {
			return false;
		}

		return Db.update("delete from " + tbl_area + " where id in (" + ids + ")") > 0;
	}
}
