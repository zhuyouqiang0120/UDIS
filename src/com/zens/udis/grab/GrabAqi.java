package com.zens.udis.grab;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jfinal.kit.JsonKit;
import com.zens.udis.entity.AqiEntity;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.HttpRequest;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:22:18   20180123修改
 */

public class GrabAqi {

	HttpRequest request = new HttpRequest();
	InterfaceImpl interfaceImpl = new InterfaceImpl();

	@SuppressWarnings("deprecation")
	public void grabAqi() {
		String url = ResourceBundle.getBundle("url").getString("aqi") + "?r=" + Math.floor(Math.random() * (1000 + 1));
		String result = request.sendGet(url, "");// url、cookie

		String image = "";// 空气质量图片
		String color = "";// 颜色
		String ND = "";// 浓度
		String aq = "";// 空气质量等级
		String health = "";// 对健康的影响
		String method = "";// 建议措施
		String lblpp = "";// 首要污染物
		String lblND = "";// 污染物浓度
		String aqi24hr = "";// 过去24消失AQI
		String flbltime = "";// 空气质量预报时间
		String flblcontent = "";// 空气质量预报内容
		String lblTime = "";
		
		Document doc = Jsoup.parse(result);
		//System.out.println(doc.toString());
		Element AirBaby = doc.getElementById("AirBaby");
		String png_index = AirBaby.toString().substring(AirBaby.toString().indexOf("png")-2, AirBaby.toString().indexOf("png")-1);
		
		ND = doc.getElementById("AQI-text").text();
		lblpp = doc.getElementById("lblpp").text();
		lblND = doc.getElementById("lblpp").child(0).text();
		aqi24hr = doc.getElementById("AQI-text").text()+" "+doc.getElementById("AQ-text").text();
		lblTime = doc.getElementById("lblTime").text();
		Element forecast = doc.getElementById("panle-forecast");
		flbltime = forecast.child(0).child(0).child(1).child(0).text();
		flblcontent = forecast.child(0).child(0).child(1).child(1).text();
		
		switch (png_index) {
		case "1":
			color = "#01A765";
			aq = "优";
			image = "001.png";
			health = "空气质量令人满意，基本无空气污染";
			method = "各类人群可正常活动";
			break;
		case "2":

			color = "#FC0";
			aq = "良";
			image = "002.png";
			health = "空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响";
			method = "极少数异常敏感人群应减少户外活动";
			break;
		case "3":
			color = "#F90";
			aq = "轻度污染";
			image = "003.png";
			health = "易感人群症状有轻度加剧，健康人群出现刺激症状";
			method = "儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼";
			break;
		case "4":
			color = "#F00";
			aq = "中度污染";
			image = "004.png";
			health = "进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响";
			method = "儿童、老年人及心脏病、呼吸系统疾病患者避免长时间、高强度的户外锻炼，一般人群适量减少户外运动";
			break;
		case "5":
			color = "#C0C";
			aq = "重度污染";
			image = "005.png";
			health = "心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状";
			method = "儿童、老年人及心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
			break;
		case "6":
			color = "#900";
			aq = "严重污染";
			image = "006.png";
			health = "健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病";
			method = "儿童、老年人和病人应停留在室内，避免体力消耗，一般人群避免户外活动";
			break;
		default:

		}

		AqiEntity aqiEntity = new AqiEntity();
		aqiEntity.setImage(image);
		aqiEntity.setColor(color);
		aqiEntity.setND(ND);
		aqiEntity.setAq(aq);
		aqiEntity.setHealth(health);
		aqiEntity.setMethod(method);
		aqiEntity.setLblpp(lblpp);
		aqiEntity.setLblND(lblND);
		aqiEntity.setAqi24hr(aqi24hr);
		aqiEntity.setFlbltime(flbltime);
		aqiEntity.setFlblcontent(flblcontent);
		aqiEntity.setCreatetime(lblTime);
		aqiEntity.setRemark("sh");

		interfaceImpl.insertAqi(JsonKit.toJson(aqiEntity));
	}
	
	public static void main(String[] args) {
		String result = new HttpRequest().sendGet("http://shanghaicity.openservice.kankanews.com/public/aqi/index", "");
		Document doc = Jsoup.parse(result);
		//System.out.println(doc.toString());
		Element AirBaby = doc.getElementById("imgAirBaby");
		System.out.println(AirBaby.toString().substring(AirBaby.toString().indexOf("png")-2, AirBaby.toString().indexOf("png")-1));
		Element lblpp = doc.getElementById("lblpp");
		System.out.println(lblpp.text());
		Element lblND = doc.getElementById("lblND");
		System.out.println(lblND.text());
		System.out.println(doc.getElementById("AQI-text").text()+" "+doc.getElementById("AQ-text").text());
		System.out.println(doc.getElementById("lblTime").text());
		Element forecast = doc.getElementById("panle-forecast");
		System.out.println(forecast.child(0).child(0).child(1).child(0).text());
		System.out.println(forecast.child(0).child(0).child(1).child(1).text());
		Element thumb = doc.getElementById("slider-thumb");
		System.out.println(thumb);
	}
}
