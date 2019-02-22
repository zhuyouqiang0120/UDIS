package com.zens.udis.grab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfinal.kit.JsonKit;
import com.zens.udis.entity.WeatherEntity;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:22:32
 */
public class GrabWeather {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();

	InterfaceImpl interfaceImpl = new InterfaceImpl();

	@SuppressWarnings("deprecation")
	public void grabWeather() {
		String url = ResourceBundle.getBundle("url").getString("weather");
		System.out.println(url);
		String result = request.sendGet("https://shanghaicity.openservice.kankanews.com/public/default/apisOpen", "");
		//System.out.println(result);
		JSONObject weat = JSONObject.fromObject(result);
		JSONArray list = JSONArray.fromObject(weat.getString("list"));
		List<WeatherEntity> wlist = new ArrayList<WeatherEntity>();
		for (Object o : list) {
			WeatherEntity weatherEntity = new WeatherEntity();
			JSONObject jb = (JSONObject) o;
			weatherEntity.setDatetime(jb.getString("datatime"));
			weatherEntity.setDirection(jb.getString("direction"));
			weatherEntity.setTempe(jb.getString("tempe"));
			weatherEntity.setSpeed(jb.getString("speed")); 
			weatherEntity.setWeather(jb.getString("weather"));
			weatherEntity.setWeatherpic(jb.getString("weatherpic"));
			String[] pic = jb.getString("pic").split("/");
			weatherEntity.setPic(pic[pic.length-1]);
			weatherEntity.setDate(jb.getString("dat"));
			weatherEntity.setWeek(jb.getString("week"));
			weatherEntity.setCreatetime(new Date().toLocaleString());
			weatherEntity.setRemark("sh");
			wlist.add(weatherEntity);
		}
		interfaceImpl.insertWeather(JsonKit.toJson(wlist));
	}
}
