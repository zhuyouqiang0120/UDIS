package com.zens.udis.TVCrawler.services.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.PathKit;
import com.zens.udis.TVCrawler.services.esk365.Esk365Task;
import com.zens.udis.TVCrawler.services.tvsou.TvsouTask;
import com.zens.udis.TVCrawler.services.utils.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CrawlService {

	int force = 0;
	ArrayList<String> userXyList = new ArrayList<String>();
	ArrayList<String> unreachableList = new ArrayList<String>();
	ArrayList<String> esk365Programes = new ArrayList<String>();
	ArrayList<String> GUIDList = new ArrayList<String>();
	ArrayList<String> tvsouProgrames = new ArrayList<String>();
	ArrayList<String> programesList = new ArrayList<String>(); // 用于抓取完毕后返回到页面
	ArrayList<Object> resultsList = new ArrayList<Object>();
	DBConfigUtils configUtils = new DBConfigUtils();
	String usefulXy;
	String usefulXyAndXyName;
	String infoSourcePath = "";
	String unreachablePath = "";
	String userXyPath = "";
	String programesPP = configUtils.getConfig("programesPath");
	String programesPath = configUtils.getConfig("programesPath");
	String overApi = configUtils.getConfig("overApi");
	String overApiPath = "";

	public CrawlService(int force) {
		this.force = force;
		infoSourcePath = Paths.infoSourcePath;
		unreachablePath = Paths.unreachablePath;
		userXyPath = Paths.userXyPath;
		overApiPath = Paths.overApiPath;
	}

	public CrawlService() {
		infoSourcePath = Paths.infoSourcePath;
		unreachablePath = Paths.unreachablePath;
		userXyPath = Paths.userXyPath;
		overApiPath = Paths.overApiPath;
	}

	/*
	 * public void initialize() {
		webRootPath = PathKit.getWebRootPath();
		infoSourcePath = webRootPath + "/data/";
		sourcePath = infoSourcePath + "xy/source.txt";
		unreachablePath = infoSourcePath + "xy/unreachable.txt";
		userXyPath = infoSourcePath + "userParam/userXy.txt";
		apiPath = infoSourcePath + "userParam/api.txt";
		overApiPath = infoSourcePath + "userParam/overApi.txt";
		programesPP = infoSourcePath + "userParam/programesPath.txt";
	}
	 * */
	
	// 这个方法用来获取各方传来的参数，并根据这些参数进行判断，调用相应的服务类去爬取信息。
	public synchronized ArrayList<Object> crawl() {

		System.out.println("projectPath是：" + infoSourcePath);
		System.out.println("unreachablePath是：" + unreachablePath);
		System.out.println("userXyPath是：" + userXyPath);

		// 从第三方接口获取要抓取的频道（JSON的形式）。
		GetProgrames getProgrames = new GetProgrames();
		JSONArray programesJA = getProgrames.get();

		// 检查是否从成哥提供的http接口获取到了要抓取的频道列表。
		System.out.println("********************************************");
		System.out.println("本次从成哥提供的http接口获取到的频道列表为：");
		for (Object programeJO : programesJA) {
			JSONObject jO = (JSONObject) programeJO;
			String title = (String) jO.get("title");
			System.out.println(title);
		}
		System.out.println("********************************************");

		System.out.println("usefulXy是：" + usefulXy);

		if (usefulXy == null) {
			System.out.println("当前用户没有选择信息源或者信息源都不可用！");
		} else if (usefulXy != null) {
			// 判断该调用哪个信息源的服务类去该信息源爬取信息。
			if (usefulXy.equals("http://www.esk365.com/tools/tv/")) {
				crawlFromEsk365(programesJA);
			}
			if (usefulXy.equals("http://www.tvsou.com/epg/")) {
				crawlFromTvsou(programesJA);
			}
		}

		// 本次所有节目抓取完毕！
		System.out.println("********************************************");
		System.out.println("本次所有节目抓取完毕！");
		System.out.println("********************************************");

		// 回调第三方http接口，告诉它本次抓取完毕。
		URL ur = null;
		BufferedReader bd = null;
		StringBuffer buffer = new StringBuffer();

		String line = null;
		try {
			System.out.println(
					"overApi是" + overApi + "（本语句打印自/tvCrawler/src/com/tvCrawl/service/common/GetProgramesList.java）");
			ur = new URL(overApi);
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
		String jsons = buffer.toString();
		System.out.println(jsons);
		return resultsList;

	}

	/**
	 * 去http://www.esk365.com/tools/tv/爬取信息
	 * 
	 * @author jhonson
	 * @create 2017年3月3日 上午10:42:51
	 * @update
	 * @param
	 * @return void
	 */
	public void crawlFromEsk365(JSONArray programesJA) {
		getProgrames("esk365", programesJA);

		Esk365Task esk365 = new Esk365Task(programesPath, force, esk365Programes, GUIDList, usefulXy);
		try {
			esk365.core();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("信源" + usefulXy + "不可达！");
			// 将这条信源加入到unreachable.txt中键"unreachable"对应的JSONArray中。
			addUnreachableXy(usefulXy);
			// 重新运行该程序
			CrawlService main = new CrawlService();
			main.crawl();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			CrawlService main = new CrawlService();
			main.crawl();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultsList.add(usefulXy);
		resultsList.add(programesList);
	}

	/**
	 * 去http://www.tvsou.com/epg/爬取信息
	 * 
	 * @author jhonson
	 * @create 2017年3月3日 上午10:44:51
	 * @update
	 * @param
	 * @return void
	 */
	public void crawlFromTvsou(JSONArray programesJA) {
		getProgrames("tvsou", programesJA);
		TvsouTask tvsou = new TvsouTask(programesPath, force, tvsouProgrames, GUIDList, usefulXy);
		try {
			tvsou.core();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("信源" + usefulXy + "不可达！");
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			CrawlService main = new CrawlService();
			main.crawl();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultsList.add(usefulXy);
		resultsList.add(programesList);

	}

	// 循环遍历userXyList中的元素与unreachableList中的元素比对，如果unreachableList中没有，将这个元素赋值给usefulXy并跳出循环。
	public void contrast() {
		// public static void main(String args[]){
		for (int i = 0; i < userXyList.size(); i++) {
			int flag = 1;
			String userXy = userXyList.get(i);
			String[] a = userXy.split(",");
			userXy = a[0];
			System.out.println("userXy" + userXy);
			for (int j = 0; j < unreachableList.size(); j++) {
				String unreachable = unreachableList.get(j);
				String[] b = unreachable.split(",");
				unreachable = b[0];
				System.out.println("unreachable" + unreachable);
				if (userXy.equals(unreachable)) {
					flag = 0;
					System.out.println("flag是" + flag);
				}
			}
			if (flag == 1) {
				System.out.println("usefulXy是" + userXy);
				usefulXy = userXy;
				usefulXyAndXyName = userXyList.get(i);
				break;
			}
		}
	}

	// 从本地userXy.txt中获取用户选择的信息源列表。
	public void getReachableXyFromUser() {
		String xyString = configUtils.getConfig("userXy");
		JSONObject json = JSONObject.fromObject(xyString);
		JSONArray array = json.getJSONArray("userXy");
		// 遍历json数组中的json对象
		for (Object o : array) {
			JSONObject jO = (JSONObject) o;
			Iterator<?> iterator = jO.keys();
			while (iterator.hasNext()) { // 遍历取出该json对象中所有的值。
				String key = (String) iterator.next();
				String value = jO.getString(key);
				userXyList.add(value);
			}
		}
	}

	// 将这条信源加入到unreachable.txt中键"unreachable"对应的JSONArray中。
	public void addUnreachableXy(String unreachableXy) {
		// 将本地文件中的json格式的字符串读取出来。
		String jsonString = configUtils.getConfig("unreachable");
		// 将json字符串转化为json对象。
		JSONObject unreachableJson = JSONObject.fromObject(jsonString);
		// 从json对象中取出"reachable"所对应的值（一个json数组）。
		JSONArray array = unreachableJson.getJSONArray("unreachable");
		DateUtils dateUtils = new DateUtils();
		String date = dateUtils.getDateToday();
		JSONObject j = new JSONObject();
		String j_value = usefulXyAndXyName + "," + date;
		j.put("unreachableXy", j_value);
		array.add(j);
		JSONObject newUnreachableJson = new JSONObject();
		newUnreachableJson.put("unreachable", array);
		String string = newUnreachableJson.toString();
		configUtils.upConfig("unreachable", string);
	}

	// 将本地unreachable.txt中的信息源读取出来，放到列表unreachableList中。
	public void getUnreachableList() {
		// 将本地文件中的json格式的字符串读取出来。
		String jsonString = configUtils.getConfig("unreachable");
		// 将json字符串转化为json对象。
		JSONObject unreachableJson = JSONObject.fromObject(jsonString);
		// 从json对象中取出"reachable"所对应的值（一个json数组）。
		JSONArray array = unreachableJson.getJSONArray("unreachable");
		// 遍历json数组中的json对象。
		for (Object o : array) {
			JSONObject jO = (JSONObject) o;
			Iterator<?> iterator = jO.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = jO.getString(key);
				String[] a = value.split(",");
				unreachableList.add(a[0]);
			}
		}
	}

	// 从programesPath.txt中获取(用户选择的)节目列表文件保存路径。
	public void getprogramesPath() {
		String s = configUtils.getConfig("programesPath");
		JSONObject json = JSONObject.fromObject(s);
		programesPath = json.getString("programesPath") + "/";
	}

	// 从overApiPath.txt中获取抓取完毕后要回调的接口。
	public void getOverApi() {
		String s = configUtils.getConfig("overApi");
		JSONObject json = JSONObject.fromObject(s);
		overApi = json.getString("overApi");

	}

	// 将节目名称列表转化为相应的信息源的节目参数列表。
	public void getProgrames(String param, JSONArray programesJA) {
		if (param.equals("esk365")) {
			HashMap<String, String> esk365Map = new HashMap<String, String>();
			esk365Map.put("CCTV-1", "?fid=2&tvid=1&channelid=1");
			esk365Map.put("CCTV-2", "?fid=2&tvid=1&channelid=3");
			esk365Map.put("CCTV-3", "?fid=2&tvid=1&channelid=4");
			esk365Map.put("湖南卫视", "?fid=3&tvid=24&channelid=46");
			esk365Map.put("江苏卫视", "?fid=3&tvid=22&channelid=44");
			esk365Map.put("东方卫视-HD", "?fid=1&tvid=11&channelid=1789");
			esk365Map.put("安徽卫视", "?fid=3&tvid=20&channelid=42");
			esk365Map.put("浙江卫视", "?fid=3&tvid=21&channelid=43");
			esk365Map.put("CCTV-4", "?fid=2&tvid=1&channelid=5");
			esk365Map.put("CCTV-5", "?fid=2&tvid=1&channelid=6");
			esk365Map.put("CCTV-6", "?fid=2&tvid=1&channelid=7");
			esk365Map.put("CCTV-7", "?fid=2&tvid=1&channelid=8");
			esk365Map.put("CCTV-8", "?fid=2&tvid=1&channelid=9");
			esk365Map.put("CCTV-9", "?fid=2&tvid=1&channelid=10");
			esk365Map.put("CCTV-10", "?fid=2&tvid=1&channelid=11");
			esk365Map.put("CCTV-11", "?fid=2&tvid=1&channelid=12");
			esk365Map.put("CCTV-12", "?fid=2&tvid=1&channelid=13");
			esk365Map.put("CCTV-13", "?fid=2&tvid=1&channelid=14");
			esk365Map.put("CCTV-14", "?fid=2&tvid=1&channelid=15");
			esk365Map.put("CCTV-15", "?fid=2&tvid=1&channelid=16");
			esk365Map.put("CCTV-NEWS", "?fid=2&tvid=1&channelid=1775");
			esk365Map.put("河南卫视", "?fid=3&tvid=16&channelid=38");
			esk365Map.put("上海新闻综合", "?fid=1&tvid=41&channelid=82");
			esk365Map.put("东方卫视", "?fid=1&tvid=11&channelid=81");
			esk365Map.put("上海教育", "?fid=1&tvid=109&channelid=198");
			esk365Map.put("娱乐频道", "?fid=1&tvid=41&channelid=88");
			esk365Map.put("星尚频道", "?fid=1&tvid=41&channelid=84");
			esk365Map.put("艺术人生", "?fid=1&tvid=41&channelid=89");
			esk365Map.put("东方电影", "?fid=1&tvid=108&channelid=505");
			esk365Map.put("上海电视剧", "?fid=1&tvid=41&channelid=85");
			esk365Map.put("外语ICS", "?fid=1&tvid=41&channelid=90");
			esk365Map.put("纪实频道", "?fid=1&tvid=41&channelid=87");
			esk365Map.put("哈哈少儿", "?fid=1&tvid=41&channelid=92");
			esk365Map.put("第一财经", "?fid=1&tvid=41&channelid=83");
			esk365Map.put("东方娱乐-HD", "");
			esk365Map.put("新闻综合-HD", "");
			esk365Map.put("五星体育", "?fid=1&tvid=41&channelid=86");
			for (Object programeJO : programesJA) {
				JSONObject jO = (JSONObject) programeJO;
				String title = (String) jO.get("title");
				String pqpd = esk365Map.get(title);
				if (!pqpd.equals("")) {
					esk365Programes.add(pqpd);
					String GUID = (String) jO.get("GUID");
					GUIDList.add(GUID);
					programesList.add(title);
				}
			}

		}

		if (param.equals("tvsou")) {
			HashMap<String, String> tvsouMap = new HashMap<String, String>();
			tvsouMap.put("CCTV-1", "cctv-1");
			tvsouMap.put("CCTV-2", "cctv-2");
			tvsouMap.put("CCTV-3", "cctv-3");
			tvsouMap.put("湖南卫视", "hunandianshitai-hnws");
			tvsouMap.put("江苏卫视", "jiangsudianshitai-jsws");
			tvsouMap.put("东方卫视-HD", "dfws_hd");
			tvsouMap.put("安徽卫视", "ahws");
			tvsouMap.put("浙江卫视", "zhejiangdianshitai-zjws");
			tvsouMap.put("CCTV-4", "cctv-4");
			tvsouMap.put("CCTV-5", "cctv-5");
			tvsouMap.put("CCTV-6", "cctv-6");
			tvsouMap.put("CCTV-7", "cctv-7");
			tvsouMap.put("CCTV-8", "cctv-8");
			tvsouMap.put("CCTV-9", "cctv-9");
			tvsouMap.put("CCTV-10", "cctv-10");
			tvsouMap.put("CCTV-11", "cctv-11");
			tvsouMap.put("CCTV-12", "cctv-12");
			tvsouMap.put("CCTV-13", "cctv-13");
			tvsouMap.put("CCTV-14", "cctv-14");
			tvsouMap.put("CCTV-15", "cctv-15");
			tvsouMap.put("CCTV-NEWS", "cctv-news");
			tvsouMap.put("河南卫视", "henandianshitai-hnws");
			tvsouMap.put("上海新闻综合", "shanghaidianshitai-xinwenzonghepindao");
			tvsouMap.put("东方卫视", "dfws");
			tvsouMap.put("上海教育", "shanghaijiaoyudianshitai-jiaoyupindao");
			tvsouMap.put("娱乐频道", "");
			tvsouMap.put("星尚频道", "xingshangpindao");
			tvsouMap.put("艺术人生", "yishurenwenpindao");
			tvsouMap.put("东方电影", "dongfangdianyingpindao-dianyingpindao");
			tvsouMap.put("上海电视剧", "shanghaidianshitai-dianshijupindao");
			tvsouMap.put("外语ICS", "ics");
			tvsouMap.put("纪实频道", "jishipindao");
			tvsouMap.put("哈哈少儿", "shanghaidianshitai-shaoerpindao");
			tvsouMap.put("第一财经", "caijingpindao");
			tvsouMap.put("东方娱乐-HD", "");
			tvsouMap.put("新闻综合-HD", "");
			tvsouMap.put("五星体育", "wuxingtiyupindao");

			for (Object programeJO : programesJA) {
				JSONObject jO = (JSONObject) programeJO;
				String title = (String) jO.get("title");
				String pqpd = tvsouMap.get(title);
				if (!pqpd.equals("")) {
					tvsouProgrames.add(pqpd);
					String GUID = (String) jO.get("GUID");
					GUIDList.add(GUID);
					programesList.add(title);
				}
			}
		}
	}
}
