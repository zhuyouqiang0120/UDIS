package com.zens.udis.TVCrawler.services.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zens.udis.TVCrawler.services.utils.FileProcessingUtils;
import com.zens.udis.TVCrawler.services.utils.Paths;
import com.zens.udis.TVCrawler.services.utils.Unfreeze;


/**
 * 程序启动后每次进入配置页面都会用到这个类，它先检查unreachable.txt中哪些不可用的信息源距离添加日期超过两个星期，给它解冻，
 * 即将这条不可达的信息源从unreachable.txt中删掉。然后它通过比对source.txt和unreachable.txt中的信息源，将当前能用的信
 * （息）源传到页面让用户选择，并将用户选择的信息源存到本地的userXy.txt中。
 * 
 * @author Dell
 *
 */
public class ConfigParamService {

	String infoSourcePath = "";
	String sourcePath = "";
	String unreachablePath = "";
	String userXyPath = "";
	String apiPath = "";
	String overApiPath = "";
	String programesPP = ""; // 这是programesPath.txt在服务器（tomcat）中的路径。
	String api = "";
	String overApi = "";
	String programesPath = ""; // 这是programesPath.txt中的键"programesPath"所对应的值，也就是用来保存一个个节目列表文件的路径。
	ArrayList<String> sourceList = new ArrayList<String>();
	ArrayList<String> unreachableList = new ArrayList<String>();
	ArrayList<String> reachableList = new ArrayList<String>();
	ArrayList<String> unreachableNamesList = new ArrayList<String>();
	ArrayList<String> userXyNamesList = new ArrayList<String>();
	ArrayList<String> userXyList = new ArrayList<String>();
	ArrayList<Object> allReturn = new ArrayList<Object>();

	public ConfigParamService() {
		infoSourcePath = Paths.infoSourcePath;
		sourcePath = Paths.sourcePath;
		unreachablePath = Paths.unreachablePath;
		userXyPath = Paths.userXyPath;
		apiPath = Paths.apiPath;
		overApiPath = Paths.overApiPath;
		programesPP = Paths.programesPP;
	}

	/**
	 * 将目前可用的信息源放到jsp页面的option中供用户选择。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:43:24
	 * @update
	 * @param
	 * @return ArrayList<Object>
	 */
	public ArrayList<Object> setOptionsToJSP() {
		// 检查是否有可以解冻的信息源，如果有，给它解冻。
		Unfreeze unfreeze = new Unfreeze();
		unfreeze.checkAndUnfreeze(infoSourcePath);
		getAllInfoFromLocal();
		for (int i = 0; i < sourceList.size(); i++) {
			int flag = 1;
			String a = sourceList.get(i);
			for (int j = 0; j < unreachableList.size(); j++) {
				String b = unreachableList.get(j);
				if (a.equals(b)) {
					flag = 0;
				}
			}
			if (flag == 1) {
				reachableList.add(a);
			}
		}

		String[][] options = new String[(reachableList).size()][2];
		for (int i = 0; i < (reachableList).size(); i++) {
			String a = reachableList.get(i);
			String[] array = a.split(",");
			options[i][0] = a;
			options[i][1] = array[1];
		}

		allReturn.add(options);
		allReturn.add(userXyNamesList);
		allReturn.add(unreachableNamesList);
		allReturn.add(programesPath);
		allReturn.add(api);
		allReturn.add(overApi);

		return allReturn;

	}
	
	/** 
	 * 从本地文件中读取所有需要的信息并给相应的成员变量赋值。
	 * @author jhonson
	 * @create 2017年3月2日 下午6:03:01
	 * @update
	 * @param  
	 * @return void
	 */
	public void getAllInfoFromLocal() {
		getSourceList();
		getUnreachableList();
		getUserXyList();
		getUnreachableNames();
		getUserXyNames();
		getprogramesPath(); // 给programesPath赋值。
		getApi();
		getOverApi();
	}

	/** 
	 * 	将source.txt中的信息源读取出来，放到列表sourceList中。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:56:52
	 * @update
	 * @param  
	 * @return void
	 */
	public void getSourceList() {
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		// 将本地文件中的json格式的字符串读取出来。
		String jsonString = fileProcessingUtils.readTxt(sourcePath);
		try {
			// 将json字符串转化为json对象。
			JSONObject sourceJson = new JSONObject(jsonString);
			// 从json对象中取出"source"所对应的值（一个json数组）。
			JSONArray array = sourceJson.getJSONArray("source");
			// 遍历json数组中的json对象。
			for (int i = 0; i < array.length(); i++) {
				JSONObject j = array.getJSONObject(i);
				Iterator<?> iterator = j.keys();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = j.getString(key);
					sourceList.add(value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 将unreachable.txt中的信息源读取出来，放到列表unreachableList中。 
	 * @author jhonson
	 * @create 2017年3月2日 下午5:57:56
	 * @update
	 * @param  
	 * @return void
	 */
	public void getUnreachableList() {
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		// 将本地文件中的json格式的字符串读取出来。
		String jsonString = fileProcessingUtils.readTxt(unreachablePath);
		try {
			// 将json字符串转化为json对象。
			JSONObject unreachableJson = new JSONObject(jsonString);
			// 从json对象中取出"reachable"所对应的值（一个json数组）。
			JSONArray array = unreachableJson.getJSONArray("unreachable");
			// 遍历json数组中的json对象。
			for (int i = 0; i < array.length(); i++) {
				JSONObject j = array.getJSONObject(i);
				Iterator<?> iterator = j.keys();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = j.getString(key);
					String[] a = value.split(",");
					String string = a[0] + "," + a[1];
					unreachableList.add(string);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/** 
	 * 从列表unreachableList中。得到不可达信源的名字，并放进列表unreachableNamesList中。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:59:14
	 * @update
	 * @param  
	 * @return void
	 */
	public void getUnreachableNames() {
		for (int i = 0; i < unreachableList.size(); i++) {
			String unreachable = unreachableList.get(i);
			String[] a = unreachable.split(",");
			unreachable = a[1];
			unreachableNamesList.add(unreachable);
		}

	}

	
	/** 
	 * 从本地userXy.txt中获取用户选择的信息源列表。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:58:37
	 * @update
	 * @param  
	 * @return void
	 */
	public void getUserXyList() {
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		String xyString = fileProcessingUtils.readTxt(userXyPath);
		try {
			JSONObject json = new JSONObject(xyString);
			JSONArray array = json.getJSONArray("userXy");
			// 遍历json数组中的json对象
			for (int i = 0; i < array.length(); i++) {
				JSONObject j = array.getJSONObject(i);
				Iterator<?> iterator = j.keys();
				while (iterator.hasNext()) { // 遍历取出该json对象中所有的值。
					String key = (String) iterator.next();
					String value = j.getString(key);
					userXyList.add(value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * 从本地userXy.txt中获取各个信源的名字，将这些名字放到uerXyNamesList中。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:59:39
	 * @update
	 * @param  
	 * @return void
	 */
	public void getUserXyNames() {
		for (int i = 0; i < userXyList.size(); i++) {
			String userXy = userXyList.get(i);
			String[] a = userXy.split(",");
			userXy = a[1];
			userXyNamesList.add(userXy);
		}

	}
	
	/** 
	 * 从programesPath.txt中获取(用户选择的)节目列表文件保存路径。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:59:59
	 * @update
	 * @param  
	 * @return void
	 */
	public void getprogramesPath() {
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		String s = fileProcessingUtils.readTxt(programesPP);
		try {
			JSONObject json = new JSONObject(s);
			programesPath = json.getString("programesPath");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	
	/** 
	 * 从api.txt中获取(用户选择的)频道名单获取接口。
	 * @author jhonson
	 * @create 2017年3月2日 下午6:00:26
	 * @update
	 * @param  
	 * @return void
	 */
	public void getApi() {
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		String s = fileProcessingUtils.readTxt(apiPath);
		try {
			JSONObject json = new JSONObject(s);
			api = json.getString("api");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	
	/** 
	 * 从overApi.txt中获取(用户选择的)抓取完毕后要回调的接口。
	 * @author jhonson
	 * @create 2017年3月2日 下午6:00:41
	 * @update
	 * @param  
	 * @return void
	 */
	public void getOverApi() {
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		String s = fileProcessingUtils.readTxt(overApiPath);
		try {
			JSONObject json = new JSONObject(s);
			overApi = json.getString("overApi");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 将前台传过来的信源写到本地文件。
	public void getParamFromJsp(String xy1, String xy2, String programesPath, String api, String overApi) {
		JSONObject reachableJson = new JSONObject();
		JSONObject programesPathJson = new JSONObject();
		JSONObject apiJson = new JSONObject();
		JSONObject overApiJson = new JSONObject();
		JSONArray reachableArray = new JSONArray();
		try {
			if (!xy1.equals("1")) {
				JSONObject xy1Json = new JSONObject();
				xy1Json.put("1", xy1);
				reachableArray.put(xy1Json);
			}
			if (!xy2.equals("2")) {
				JSONObject xy2Json = new JSONObject();
				xy2Json.put("2", xy2);
				reachableArray.put(xy2Json);
			}
			reachableJson.put("userXy", reachableArray);
			String str = reachableJson.toString();
			if (!xy1.equals("1") || !xy2.equals("2")) { // 如果用户一个信息源都没选，就不对userXyPath.txt文件进行操作。

				File file = new File(userXyPath);
				if (file.exists()) { // 写之前先将之前的文件删掉。
					file.delete();
				}
				FileProcessingUtils fileWriter = new FileProcessingUtils();
				fileWriter.fileWriter_writeIntoFile(str, userXyPath, true);
			}

			// 将传进来的programesPath存到programesPath.txt。
			programesPathJson.put("programesPath", programesPath);
			String programesPathStr = programesPathJson.toString();
			File file1 = new File(programesPP);
			if (file1.exists()) { // 写之前先将之前的文件删掉。
				file1.delete();
			}
			FileProcessingUtils fileWriter1 = new FileProcessingUtils();
			fileWriter1.fileWriter_writeIntoFile(programesPathStr, programesPP, true);

			// 将传进来的api存到api.txt。
			apiJson.put("api", api);
			String apiStr = apiJson.toString();
			File file2 = new File(apiPath);
			if (file2.exists()) { // 写之前先将之前的文件删掉。
				file2.delete();
			}
			FileProcessingUtils fileWriter2 = new FileProcessingUtils();
			fileWriter2.fileWriter_writeIntoFile(apiStr, apiPath, true);

			// 将传进来的overApi存到overApi.txt。
			overApiJson.put("overApi", overApi);
			String overApiStr = overApiJson.toString();
			File file3 = new File(overApiPath);
			if (file3.exists()) { // 写之前先将之前的文件删掉。
				file3.delete();
			}
			FileProcessingUtils fileWriter3 = new FileProcessingUtils();
			fileWriter3.fileWriter_writeIntoFile(overApiStr, overApiPath, true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
