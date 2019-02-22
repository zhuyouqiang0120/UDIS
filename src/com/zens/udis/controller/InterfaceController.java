package com.zens.udis.controller;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.udis.db.DBUtils;
import com.zens.udis.entity.NewsEntity;
import com.zens.udis.entity.SSEntity;
import com.zens.udis.entity.TrafficEntity;
import com.zens.udis.utils.HttpRequest;

/**
 * 
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年2月26日 上午11:42:45
 *
 */

public class InterfaceController extends Controller {
	
	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();
	
	ArrayList<Object> list = new ArrayList<Object>();
	
	SSEntity ssEntity = new SSEntity();
	SSEntity.UserInfo userInfo = ssEntity.new UserInfo();
	SSEntity.SSInfo ssInfo = ssEntity.new SSInfo();
	
	TrafficEntity trafficEntity = new TrafficEntity();
	
	DBUtils dbUtils = new DBUtils();
	
	public void getData() throws IOException{
		
		long time1 = System.currentTimeMillis();
		
		String params = getPara("data");
		
		JSONObject par = JSONObject.fromObject(params);
		
		String type = par.getString("type");//查询类型
		
		if(type.equals("news")){//新闻类
			//http://localhost:8080/SHCity/inter/getData?data={"type":"news","columnid":"1001","page":1,"pagesize":8}
			String columnid = par.getString("columnid");//栏目ID
			int page = par.getInt("page");////页码
			int pagesize = par.getInt("pagesize");////条数
			List<Record> newslist = dbUtils.getNews(columnid, page, pagesize);
			setNews(newslist);
		}else if(type.equals("ss")){//社保医保类
			//http://localhost:8080/SHCity/inter/getData?data={"type":"ss","sid":"422112619999999","pid":"1212121212"}
			String sid = par.getString("sid");
			String pid = par.getString("pid");
			userInfo.setSid(sid);
			userInfo.setPid(pid);
			userInfo.setName("朱佑强");	
			
			ssEntity.setUserInfo(userInfo);
			ssEntity.setSsInfo(ssInfo);
			
			list.add(ssEntity);
		}else if(type.equals("vio")){//违章类
			//http://localhost:8080/SHCity/inter/getData?data={"type":"vio","sid":"422112619999999","pid":"1212121212"}
			list.add(trafficEntity);
			
		}
		
		JSONObject news = new JSONObject();
		jsonObject.element("status", "200 OK");
		jsonObject.element("success", "1");
		jsonObject.element("action", "getData");
		news.element("servertype", type);
		
		
		news.element("data", list);
		news.element("totalResults", list.size());
		jsonObject.element("repData", news);

		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2-time1);
		
		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
	
	public void insertNews() {
		NewsEntity newsEntity = new NewsEntity();

		renderJavascript(getPara("callback") + "(" + insertNews(newsEntity)
				+ ")");
	}

	public void setNews(List<Record> newslist) {
		for (int i = 0; i < newslist.size(); i++) {
			NewsEntity newsEntity = new NewsEntity();
			newsEntity.setId((int) newslist.get(i).getColumns().get("id"));
			newsEntity.setTid((String) newslist.get(i).getColumns().get("tid"));
			newsEntity.setTitle((String) newslist.get(i).getColumns()
					.get("title"));
			newsEntity.setTime((String) newslist.get(i).getColumns()
					.get("time"));
			newsEntity.setTitlepic((String) newslist.get(i).getColumns()
					.get("titlepic"));
			newsEntity.setContents((String) newslist.get(i).getColumns()
					.get("contents"));
			newsEntity.setCreatetime((String) newslist.get(i).getColumns()
					.get("createtime"));
			newsEntity.setColumnid((int) newslist.get(i).getColumns()
					.get("columnid"));
			list.add(newsEntity);
		}
	}

	public boolean insertNews(NewsEntity newsEntity) {
		Record record = new Record().set("tid", newsEntity.getTid())
				.set("title", newsEntity.getTitle())
				.set("time", newsEntity.getTime())
				.set("titlepic", newsEntity.getTitlepic())
				.set("contents", newsEntity.getContents())
				.set("createtime", newsEntity.getCreatetime())
				.set("columnid", newsEntity.getColumnid());

		return dbUtils.insertNews(record);

	}
}
