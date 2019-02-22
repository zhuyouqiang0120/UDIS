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

public class DBUtils {
	// private static final String tbl_column = "tbl_column";
	private static final String tbl_news = "tbl_news";
	private static final String tbl_scenicl = "tbl_scenicl";
	private static final String tbl_talkshanghai = "tbl_talkshanghai";
	private static final String tbl_buzzwords = "tbl_buzzwords";
	private static final String tbl_whaton = "tbl_whaton";
	private static final String tbl_data = "tbl_data";
	private static final String tbl_picip = "tbl_picip";

	public List<Record> getNews(String columnid, int page, int pagesize) {
		String sql = "select * from " + tbl_news + " where columnid = '" + columnid + "'  limit " + page * pagesize
				+ "," + pagesize;

		return Db.find(sql);
	}

	public List<Record> getWeather(int iid) {
		String sql = "select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1";
		return Db.find(sql);
	}

	public boolean insertWeather(Record record) {
		try {
			Db.save(tbl_data, record);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return true;//Db.save(tbl_data, record);
	}

	public boolean insertNews(Record record) {
		// Db.save(tbl_column, record);
		return Db.save(tbl_data, record);
	}

	public List<Record> getAqi(int iid) {
		String sql = "select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1";
		return Db.find(sql);
	}

	public boolean insertAqi(Record record) {
		return Db.save(tbl_data, record);
	}

	public int deleteScienil() {
		return Db.update("delete from " + tbl_scenicl);
	}

	public boolean insertScenicl(Record record) {
		return Db.save(tbl_data, record);
	}

	public List<Record> getScienicl(int iid) {
		return Db.find("select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1");
	}

	public int deleteTalk() {
		return Db.update("delete from " + tbl_talkshanghai);
	}

	public boolean insertTalk(Record record) {
		return Db.save(tbl_data, record);
	}

	public List<Record> getTalk(int iid) {
		return Db.find("select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1");
	}

	public int deleteBuzz() {
		return Db.update("delete from " + tbl_buzzwords);
	}

	public boolean insertBuzz(Record record) {
		return Db.save(tbl_data, record);
	}

	public List<Record> getBuzz(int iid) {
		return Db.find("select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1");
	}

	public int deleteWhat() {
		return Db.update("delete from " + tbl_whaton);
	}

	public boolean insertWhat(Record record) {
		return Db.save(tbl_data, record);
	}

	public List<Record> getWhat(int iid) {
		return Db.find("select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1");
	}

	public boolean insertRoad(Record record) {
		return Db.save(tbl_data, record);
	}

	public List<Record> getRoad(int iid) {
		return Db.find("select * from " + tbl_data + " where iid = " + iid + " order by id desc limit 0,1");
	}

	public List<Record> getPicIP() {
		// TODO Auto-generated method stub
		return Db.find("select * from " + tbl_picip + " limit 0,1");
	}

	public List<Record> getSearch(String tag) {
		return Db.find("select * from tbl_search_data where tag = '" + tag + "'");
	}

	public boolean insertSearch(Record record) {
		return Db.save("tbl_search_data", record);
	}

	public boolean updateSearch(Record record) {
		return Db.update("tbl_search_data", record);
	}

	public List<Record> getStatus() {
		return Db.find("select * from tbl_status limit 0,1");
	}

	public int updateStatus(String tag) {
		return Db.update("update tbl_status set status = '" + tag + "'");
	}
}
