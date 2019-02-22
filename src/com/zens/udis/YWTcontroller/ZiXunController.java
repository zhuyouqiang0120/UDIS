package com.zens.udis.YWTcontroller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.zens.udis.utils.DownloadImg;
import com.zens.udis.utils.HttpRequest;
import com.zens.udis.utils.WriteFile;

/**
 * 
 * @author ZYQ(zhuyq@zensvision.com)
 * @date  2015年9月21日上午11:35:37
 */

public class ZiXunController extends Controller {

	private String getitems = "http://cms.jmtv.cn:888/cms/api/getitems.jsp";
	private String getitem = "http://cms.jmtv.cn:888/cms/api/getitem.jsp";
	private String token = "d274acc89ca57b74ffbdeaaccbd685f6";
	private JSONObject object = new JSONObject();
	private HttpRequest request = new HttpRequest();

	public void getitems() throws Exception {// 获取文章列表
		//String id = getPara("id");
		String page = getPara("page");
		String pagesize = getPara("pagesize");

		String p1 = "id=15421&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p2 = "id=15435&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p3 = "id=15434&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p4 = "id=15433&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;

		String result1 = request.sendGet(getitems, p1);
		JSONArray data1 = JSONArray.fromObject(result1);
		String result2 = request.sendGet(getitems, p2);
		JSONArray data2 = JSONArray.fromObject(result2);
		String result3 = request.sendGet(getitems, p3);
		JSONArray data3 = JSONArray.fromObject(result3);
		String result4 = request.sendGet(getitems, p4);
		JSONArray data4 = JSONArray.fromObject(result4);

		JSONArray items = JSONArray
				.fromObject("[{\"text\": \"江门新闻\",\"id\": \"15421\"},{\"text\": \"国际国内\",\"id\": \"15435\"},{\"text\": \"珠三角\",\"id\": \"15434\"},{\"text\": \"娱乐新闻\",\"id\": \"15433\"}]");
		object.element("node", "3177");
		object.element("3177", items);
		object.element("3177_0_0", data1);
		object.element("3177_1_0", data2);
		object.element("3177_2_0", data3);
		object.element("3177_3_0", data4);

		renderText(object.toString());
	}
	
	public void getzwitems() throws Exception {// 获取政务文章列表
		String page = getPara("page");
		String pagesize = getPara("pagesize");

		String p1 = "id=15483&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String result1 = request.sendGet(getitems, p1);
		JSONArray data1 = JSONArray.fromObject(result1);

		JSONArray items = JSONArray
				.fromObject("[{\"text\": \"公示公告\",\"id\": \"15483\"}]");
		object.element("node", "3177");
		
		
		object.element("3177", items);
		object.element("3177_0_0", data1);
		
		renderText(object.toString());
	}

	public void getitem() throws Exception {// 获取文章内容
		String itemid = getPara("itemid");
		String channelid = getPara("channelid");

		String params = "itemid=" + itemid + "&channelid=" + channelid + "&Token=" + token;
		String result = request.sendGet(getitem, params);

		result = result.substring(0, result.length() ).replaceAll("<.*?>", "");// 去电（）和标签
		JSONObject obj = JSONObject.fromObject(result);
		String photo = obj.getString("Photo");
		if (!photo.equals("")) {
			String url = new DownloadImg().Setimg(photo);
			result = result.replace(photo, url);
		}
		
		//object.element("data", result);
		renderJson(result);
	}
	
	
	
	public void getitemstofile() throws Exception {// 获取文章列表并写入json
		//String id = getPara("id");
		String page = getPara("page");
		String pagesize = getPara("pagesize");

		String p1 = "id=15421&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p2 = "id=15435&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p3 = "id=15434&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p4 = "id=15433&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;

		String result1 = request.sendGet(getitems, p1);
		JSONArray data1 = JSONArray.fromObject(result1);
		String result2 = request.sendGet(getitems, p2);
		JSONArray data2 = JSONArray.fromObject(result2);
		String result3 = request.sendGet(getitems, p3);
		JSONArray data3 = JSONArray.fromObject(result3);
		String result4 = request.sendGet(getitems, p4);
		JSONArray data4 = JSONArray.fromObject(result4);

		JSONArray items = JSONArray
				.fromObject("[{\"text\": \"江门新闻\",\"id\": \"15421\"},{\"text\": \"国际国内\",\"id\": \"15435\"},{\"text\": \"珠三角\",\"id\": \"15434\"},{\"text\": \"娱乐新闻\",\"id\": \"15433\"}]");
		object.element("node", "3177");
		object.element("3177", items);
		object.element("3177_0_0", data1);
		object.element("3177_1_0", data2);
		object.element("3177_2_0", data3);
		object.element("3177_3_0", data4);
		object.element("3177_0_1", data1);
		object.element("3177_1_1", data2);
		object.element("3177_2_1", data3);
		object.element("3177_3_1", data4);
		
		new WriteFile().tofile(object.toString());

		renderText("SUCCESS!");
	}
	
	public void getitemstofile1() throws Exception {// 获取文章列表并写入json
		//String id = getPara("id");
		String page = getPara("page");
		String pagesize = getPara("pagesize");

		String p1 = "id=15421&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p2 = "id=15435&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p3 = "id=15434&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;
		String p4 = "id=15433&page=" + page + "&pagesize=" + pagesize + "&Token=" + token;

		String result1 = request.sendGet(getitems, p1);
		JSONArray data1 = JSONArray.fromObject(result1);
		String result2 = request.sendGet(getitems, p2);
		JSONArray data2 = JSONArray.fromObject(result2);
		String result3 = request.sendGet(getitems, p3);
		JSONArray data3 = JSONArray.fromObject(result3);
		String result4 = request.sendGet(getitems, p4);
		JSONArray data4 = JSONArray.fromObject(result4);
		
		for(int i = 0 ; i < data1.size(); i ++){
			JSONObject object = (JSONObject) data1.get(i);
			String photo = object.getString("Photo");
			if (!photo.equals("")) {
				String url = new DownloadImg().Setimg(photo);
				object.element("Photo", url);
			}
		}
		for(int i = 0 ; i < data2.size(); i ++){
			JSONObject object = (JSONObject) data2.get(i);
			String photo = object.getString("Photo");
			if (!photo.equals("")) {
				String url = new DownloadImg().Setimg(photo);
				object.element("Photo", url);
			}
		}
		for(int i = 0 ; i < data3.size(); i ++){
			JSONObject object = (JSONObject) data3.get(i);
			String photo = object.getString("Photo");
			if (!photo.equals("")) {
				String url = new DownloadImg().Setimg(photo);
				object.element("Photo", url);
			}
		}
		for(int i = 0 ; i < data4.size(); i ++){
			JSONObject object = (JSONObject) data4.get(i);
			String photo = object.getString("Photo");
			if (!photo.equals("")) {
				String url = new DownloadImg().Setimg(photo);
				object.element("Photo", url);
			}
		} 

		JSONArray items = JSONArray
				.fromObject("[{\"text\": \"江门新闻\",\"id\": \"15421\"},{\"text\": \"国际国内\",\"id\": \"15435\"},{\"text\": \"珠三角\",\"id\": \"15434\"},{\"text\": \"娱乐新闻\",\"id\": \"15433\"}]");
		object.element("node", "3177");
		object.element("3177", items);
		object.element("3177_0_0", data1);
		object.element("3177_1_0", data2);
		object.element("3177_2_0", data3);
		object.element("3177_3_0", data4);
		object.element("3177_0_1", data2);
		object.element("3177_1_1", data1);
		object.element("3177_2_1", data3);
		object.element("3177_3_1", data4);
		
		new WriteFile().tofile(object.toString());

		renderText("SUCCESS!");
	}
}
