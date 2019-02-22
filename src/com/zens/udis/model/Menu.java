package com.zens.udis.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class Menu{
	private static final String tableName = "tbl_menu";
	
	public List<Record> getMenuList(){
		return Db.find("select * from " + tableName); 
	}
}
