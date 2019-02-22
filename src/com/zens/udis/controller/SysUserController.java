package com.zens.udis.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUserUtils;
import com.zens.udis.entity.User;
import com.zens.udis.utils.Lunar;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年5月6日 下午3:26:59
 */

public class SysUserController extends Controller {

	JSONObject jsonObject = new JSONObject();
	DBUserUtils dbUserUtils = new DBUserUtils();
	User user = new User();

	public void getUsers() {//deleted=0&currPage=1&pageSize=15&orderCase=
		try {
			int page = getParaToInt("currPage");
			int pageSize = getParaToInt("pageSize");
			int deleted = getParaToInt("deleted");
			String orderCase = getPara("orderCase");

			if (getPara("callback") == null) {
				renderJson(JsonKit.toJson(user.getUsersByPagin(page, pageSize, deleted, orderCase)));
			} else {
				renderJavascript(getPara("callback") + "(" + JsonKit.toJson(user.getUsersByPagin(page, pageSize, deleted, orderCase)) + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertUser(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = user.insertUser(map);
		} catch(Exception e) {
			flag = false;
		} finally {
		}
		
		renderJson("{\"success\":" + flag + "}");
	}

	public void updateUser(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = user.updateUser(map);
		} catch(Exception e) {
			flag = false;
		} finally {
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void removeUser(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = user.removeUsers(ids);
		} catch(Exception e) {
			flag = false;
		} finally {
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteUser(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = user.deleteUsers(ids);
		} catch(Exception e) {
			flag = false;
		} finally {
		}
		renderJson("{\"success\":" + flag + "}");
	}
}
