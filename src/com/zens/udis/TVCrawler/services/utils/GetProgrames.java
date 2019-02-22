package com.zens.udis.TVCrawler.services.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 这个类的作用是从第三方接口获得json数据，并解析json数据，得到节目名单以及每个节目对应的
 * GUID值（一个字符串，在本体生成每个节目对应的文件时就以它命名）。 http接口中的数据如下： {"desc":"LEMON LiveChannel"
 * ,"ResponseTime":12,"data":[{"sid":"1","title":"CCTV-1","GUID":
 * "10348689678346e0b9b5fca6f5973800",
 * "definition":"HD","encryption":null,"channelno":"1","freq":"723","sreate":
 * "2431","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"2","vid":"102"},{"sid":"1","title":"CCTV-2"
 * ,"GUID":"210281fd48be49eca204541018541802",
 * "definition":"HD","encryption":null,"channelno":"2","freq":"372","sreate":
 * "2323","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"2","vid":"102"},{"sid":"1","title":"CCTV-3"
 * ,"GUID":"862e0422a48c44b9b9da2515593f46fa",
 * "definition":"HD","encryption":null,"channelno":"3","freq":"4832","sreate":
 * "3231","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"2","vid":"102"},{"sid":"2","title":"湖南卫视",
 * "GUID":"7f9d439992a646b1a256119e973d984d","definition":"HD","encryption":null
 * ,"channelno":"4","freq":"392","sreate":"3231","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":"江苏卫视",
 * "GUID":"2d21d3850bd944d6ae703db2d615d0ec","definition":"hd","encryption":null
 * ,"channelno":"5","freq":"421","sreate":"2324","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"1","title":
 * "东方卫视-HD","GUID":"a646cbf59c8f421a90b0b27b9423db8c","definition":"hd",
 * "encryption":null,"channelno":"6","freq":"4231","sreate":"3234","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"102","tsid":"2","vid":"102"},{"sid":
 * "2","title":"安徽卫视","GUID":"184f8795987f404797c357f13a9d72c6","definition":
 * "hd","encryption":null,"channelno":"7","freq":"212","sreate":"3234","bitrate"
 * :"66666","pid":"101","qam":"64qam","aid":"101","tsid":"1","vid":"102"},{"sid"
 * :"2","title":"浙江卫视","GUID":"9eab4236a14a4fe29a650f16d7526360","definition":
 * "HD","encryption":null,"channelno":"8","freq":"3122","sreate":"3231",
 * "bitrate":"6666","pid":"101","qam":"64qam","aid":"101","tsid":"1","vid":"102"
 * },{"sid":"2","title":"CCTV-4","GUID":"44bb5441338d4e138a77a9e3c9fff18b",
 * "definition":"HD","encryption":null,"channelno":"11","freq":"323","sreate":
 * "3241","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"},{"sid":"2","title":"CCTV-5","GUID":
 * "529f79f14c594e85814a49b63103ab39","definition":"HD","encryption":null,
 * "channelno":"12","freq":"43","sreate":"4232","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":"CCTV-6"
 * ,"GUID":"ad2c6d2f9cec44dc98b3e416ee689410","definition":"HD","encryption":
 * null,"channelno":"13","freq":"424","sreate":"42313","bitrate":"6666","pid":
 * "101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":
 * "CCTV-7","GUID":"6ef2353e7d9e424d9157ee7ad5d8791b","definition":"HD",
 * "encryption":null,"channelno":"14","freq":"423","sreate":"2342","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":
 * "2","title":"CCTV-8","GUID":"d92dcc818a79410e971a714c9c18d199","definition":
 * "HD","encryption":null,"channelno":"15","freq":"433","sreate":"3231",
 * "bitrate":"66666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":
 * "102"},{"sid":"2","title":"CCTV-9","GUID":"f911444696aa44128260c72281861b0e",
 * "definition":"HD","encryption":null,"channelno":"16","freq":"423","sreate":
 * "232212","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"},{"sid":"2","title":"CCTV-10","GUID":
 * "038cb259dc0844c6b6c35245b430e0bd","definition":"HD","encryption":null,
 * "channelno":"17","freq":"423","sreate":"11323","bitrate":"64545","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":
 * "CCTV-11","GUID":"5a47bb8b28cd4aa7b75302be3b226dcc","definition":"HD",
 * "encryption":null,"channelno":"18","freq":"3422","sreate":"42432","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":
 * "2","title":"CCTV-12","GUID":"64f44dbd08ec40ada0b3f80e647fe2a6","definition":
 * "HD","encryption":null,"channelno":"19","freq":"3422","sreate":"42432",
 * "bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"
 * },{"sid":"2","title":"CCTV-13","GUID":"85c86314e80847cfb2b297582e993dbe",
 * "definition":"HD","encryption":null,"channelno":"20","freq":"3422","sreate":
 * "42432","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"},{"sid":"2","title":"CCTV-14","GUID":
 * "ca293043f6314756b32b44af7aaee773","definition":"HD","encryption":null,
 * "channelno":"21","freq":"3422","sreate":"42432","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":
 * "CCTV-15","GUID":"a0288717614b437c83eb914299e8c69e","definition":"HD",
 * "encryption":null,"channelno":"22","freq":"3422","sreate":"42432","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":
 * "2","title":"CCTV-NEWS","GUID":"59151e88341f455792222123d374d381",
 * "definition":"HD","encryption":null,"channelno":"25","freq":"3422","sreate":
 * "42432","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"},{"sid":"2","title":"河南卫视","GUID":
 * "bdfa23d986204598af0cfc49eab6160f","definition":"HD","encryption":null,
 * "channelno":"27","freq":"3422","sreate":"42432","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":"上海新闻综合"
 * ,"GUID":"fac02382dd774d0fb4347d9eb9a37916","definition":"HD","encryption":
 * null,"channelno":"28","freq":"3422","sreate":"42432","bitrate":"6666","pid":
 * "101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":
 * "东方卫视","GUID":"0c8a1087cf7a475ba59ef29fcd3b3c48","definition":"HD",
 * "encryption":null,"channelno":"29","freq":"3422","sreate":"42432","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":
 * "2","title":"上海教育","GUID":"968f100955fb4bb08a49e80e90a80a65","definition":
 * "HD","encryption":null,"channelno":"30","freq":"3422","sreate":"42432",
 * "bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"
 * },{"sid":"2","title":"娱乐频道","GUID":"f4f7de0941544a3f9c1b59c00a69e391",
 * "definition":"HD","encryption":null,"channelno":"31","freq":"3422","sreate":
 * "42432","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"},{"sid":"2","title":"星尚频道","GUID":
 * "a6309120a5894a28b5a67625e94d7cc1","definition":"HD","encryption":null,
 * "channelno":"32","freq":"3422","sreate":"42432","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":"艺术人生",
 * "GUID":"5331f6d2ee3441eb92393d8b3cb7b509","definition":"HD","encryption":null
 * ,"channelno":"33","freq":"3422","sreate":"42432","bitrate":"6666","pid":"101"
 * ,"qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":"东方电影",
 * "GUID":"ed69dc9989534bbc95ea0d1691b26e98","definition":"HD","encryption":null
 * ,"channelno":"34","freq":"3422","sreate":"42432","bitrate":"6666","pid":"101"
 * ,"qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":"上海电视剧"
 * ,"GUID":"2052bc8ce7224e5396ddb409bedcfeb1","definition":"HD","encryption":
 * null,"channelno":"35","freq":"3422","sreate":"42432","bitrate":"6666","pid":
 * "101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":
 * "外语ICS","GUID":"2f62fd99f71f479589c9d6e64d7c2504","definition":"HD",
 * "encryption":null,"channelno":"36","freq":"3422","sreate":"42432","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":
 * "2","title":"纪实频道","GUID":"c8b7f1e777234a6da197cd1adc9aa13b","definition":
 * "HD","encryption":null,"channelno":"37","freq":"3422","sreate":"42432",
 * "bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"
 * },{"sid":"2","title":"哈哈少儿","GUID":"b77dba73aef24802814f28a6ba94c7e4",
 * "definition":"HD","encryption":null,"channelno":"38","freq":"3422","sreate":
 * "42432","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"},{"sid":"2","title":"第一财经","GUID":
 * "313710c3e7c243d88e73684230257dec","definition":"HD","encryption":null,
 * "channelno":"39","freq":"3422","sreate":"42432","bitrate":"6666","pid":"101",
 * "qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":"2","title":
 * "东方娱乐-HD","GUID":"97a48e1fd193462fa3b73db0086e3702","definition":"HD",
 * "encryption":null,"channelno":"40","freq":"3422","sreate":"42432","bitrate":
 * "6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"},{"sid":
 * "2","title":"新闻综合-HD","GUID":"c34fb7416fc74e119ff4f4b5b313aa1d","definition":
 * "HD","encryption":null,"channelno":"41","freq":"3422","sreate":"42432",
 * "bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1","vid":"102"
 * },{"sid":"2","title":"五星体育","GUID":"baaeca22b25542668cad4f4862e2a74b",
 * "definition":"HD","encryption":null,"channelno":"42","freq":"3422","sreate":
 * "42432","bitrate":"6666","pid":"101","qam":"64QAM","aid":"101","tsid":"1",
 * "vid":"102"}],"version":"1.0"}
 *
 */
public class GetProgrames {
	String api = "";

	public JSONArray get() {

		InitFiles initFiles = new InitFiles();
		String apiPath = initFiles.apiPath;
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		String s = fileProcessingUtils.readTxt(apiPath);
		JSONObject apijson = JSONObject.fromObject(s);
		api = apijson.getString("api");

		URL ur = null;
		BufferedReader bd = null;
		StringBuffer buffer = new StringBuffer();

		String line = null;
		try {
			ur = new URL(api); // 这个网址是成哥提供的一个http接口，在浏览器输入该地址可以看到一个json字符串。
			HttpURLConnection connection = (HttpURLConnection) ur.openConnection();
			bd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = bd.readLine()) != null) {
				buffer.append(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String programesJStr = buffer.toString();
		JSONObject infoJO = JSONObject.fromObject(programesJStr );
		JSONArray programesjA = infoJO.getJSONArray("data");

		return programesjA;

	}

}
