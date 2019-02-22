package com.zens.udis.grab;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfinal.kit.JsonKit;
import com.zens.udis.entity.Scenicl;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GrabScenicl {
	HttpRequest hr = new HttpRequest();

	InterfaceImpl ijm = new InterfaceImpl();

	public String grapScenicl() {
		String url = ResourceBundle.getBundle("url").getString("scenicl");
		String result = hr.sendGet(url, "");
		// JSONObject object = JSONObject.fromObject(result);
		JSONArray ja = JSONArray.fromObject(result);
		List<Scenicl> wlist = new ArrayList<Scenicl>();
		if (ja.size() != 0) {
			// ijm.deleteScenicl();
			for (Object o : ja) {
				Scenicl scenicl = new Scenicl();
				JSONObject jo = (JSONObject) o;
				scenicl.setTime(jo.getString("TIME"));
				scenicl.setRank(jo.getString("RANK"));
				scenicl.setName(jo.getString("NAME"));
				scenicl.setMaxNum(jo.getString("MAX_NUM"));
				scenicl.setImage(jo.getString("IMAGE"));
				scenicl.setStartTime(jo.getString("START_TIME"));
				scenicl.setSwitch(jo.getString("SWITCH"));
				scenicl.setInitial(jo.getString("INITIAL"));
				scenicl.setCode(jo.getString("CODE"));
				scenicl.setCounty(jo.getString("COUNTY"));
				scenicl.setSSD(jo.getString("SSD"));
				scenicl.setLocation_x(jo.getString("LOCATION_X"));
				scenicl.setLocation_y(jo.getString("LOCATION_Y"));
				scenicl.setR_time(jo.getString("R_TIME"));
				scenicl.setEnd_time(jo.getString("END_TIME"));
				scenicl.setInfo(jo.getString("INFO"));
				scenicl.setNum(jo.getString("NUM"));
				scenicl.setType(jo.getString("TYPE"));
				wlist.add(scenicl);
			}

			ijm.insertScenicl(JsonKit.toJson(wlist));
		}
		return JsonKit.toJson(ja);
	}

}
