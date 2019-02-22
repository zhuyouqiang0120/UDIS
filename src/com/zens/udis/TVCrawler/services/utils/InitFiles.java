package com.zens.udis.TVCrawler.services.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.kit.PathKit;

/**
 * 判断本地有无初始信息文件，若有则什么都不做，若没有，则生成。
 * @author  Jhonson
 * @email   xzc@zensvision.com
 * @create  2017年3月2日下午5:03:10
 * @version 1.0 
 */
public class InitFiles {
	public String infoSourcePath = null; //这个路径是tomcat中该项目文件夹下的data文件夹。
	public String sourcePath = null; //这个路径的文本文件用来保存所有的信息源。
	public String unreachablePath = null; //这个路径的文本文件用来保存不可达的信息源。
	public String userXyPath = ""; //这个路径的文本文件用来保存用户选择的信息源。
	public String apiPath = ""; //这个路径的文本文件用来保存“获取频道名单的http接口”。
	public String overApiPath = ""; //这个路径的文本文件用来保存“抓取完毕后回调的http接口”。
	public String programesPP = null; //这个路径的文本文件用来保存用户在配置页面输入的抓取节目所保存的路径。
	
	public InitFiles(){ //这是该类的构造方法，用来给上面定义的变量赋初值。用这个类名实例化对象的时候会自动调用了这个方法。		
		infoSourcePath = Paths.infoSourcePath;
		sourcePath = Paths.sourcePath;
		unreachablePath = Paths.unreachablePath;
		userXyPath = Paths.userXyPath;
		apiPath = Paths.apiPath;
		overApiPath = Paths.overApiPath;
		programesPP = Paths.programesPP;		
	}
	
	public void Initialize(){

		
		
		
	}
	
	/** 
	 * 判断本地sourcePath路径下是否存在source.txt。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:19:56
	 * @update
	 * @param  
	 * @return void
	 */
	public void initializeSourceTxt() {
		File file = new File(sourcePath);
		//若不存在，在本地按sourcePath路径生成source.txt。
		if (!file.exists()){
			JSONObject sourceJson = new JSONObject();
			JSONArray sourceJsonArray = new JSONArray();
			JSONObject tvsouJson = new JSONObject();
			JSONObject esk365Json = new JSONObject();
			sourceJsonArray.put(tvsouJson);
			sourceJsonArray.put(esk365Json);
			try {
				tvsouJson.put("1", "http://www.tvsou.com/epg/,tvsou");
				esk365Json.put("2", "http://www.esk365.com/tools/tv/,esk365");
				sourceJson.put("source", sourceJsonArray);
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}		
			String sourceString = sourceJson.toString();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(sourceString,sourcePath, true);		
		}
	}
	
	/**
	 * 判断本地unreachablePath路径下是否存在unreachable.txt。 
	 * @author jhonson
	 * @create 2017年3月2日 下午5:21:52
	 * @update
	 * @param  
	 * @return void
	 */
	public void initializeUnreachableTxt() {
		File file = new File(unreachablePath);
		//若不存在，在本地按unreachablePath路径生成unreachable.txt。
		if(!file.exists()){
			JSONObject unreachableJson = new JSONObject();
			JSONArray unreachableJsonArray = new JSONArray();					
			try {
				unreachableJson.put("unreachable", unreachableJsonArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}			
			String unreachableString = unreachableJson.toString();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(unreachableString,unreachablePath, true);		
		}
	}
	
	/** 
	 * 判断本地programesPP路径下是否存在programesPath.txt。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:23:21
	 * @update
	 * @param  
	 * @return void
	 */
	public void initializeProgramesPathTxt() {		
		File file = new File(programesPP);
		//若不存在，在本地按programesPP路径生成programesPath.txt。
		if(!file.exists()){
			JSONObject programesPathJson = new JSONObject();				
			try {
				programesPathJson.put("programesPath", "D:/warning/programes"); //字符串中的\要用\\来表示。
			} catch (JSONException e) {
				e.printStackTrace();
			}		
			String programesPathString = programesPathJson.toString();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(programesPathString,programesPP, true);
		}
	}
	
	/** 
	 * 判断本地userXyPath路径下是否存在userXy.txt。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:24:45
	 * @update
	 * @param  
	 * @return void
	 */
	public void initializeUserXyTxt() {	
		File file = new File(userXyPath);
		//若不存在，在本地按userXyPath路径生成userXyPath.txt。
		if(!file.exists()){
			JSONObject userXyJson = new JSONObject();
			JSONArray userXyJsonArray = new JSONArray();
			try {
				userXyJson.put("userXy", userXyJsonArray); //字符串中的\要用\\来表示。
			} catch (JSONException e) {
				e.printStackTrace();
			}			
			String userXyString = userXyJson.toString();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(userXyString,userXyPath, true);					
		}
	}
	
	/** 
	 * 判断本地apiPath路径下是否存在api.txt。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:26:09
	 * @update
	 * @param  
	 * @return void
	 */
	public void initializeApiTxt() {
		File file = new File(apiPath);
		//若不存在，在本地按apiPath路径生成api.txt。
		if(!file.exists()){
			JSONObject apiJson = new JSONObject();				
			try {
				apiJson.put("api", "http://192.168.0.51:8080/LEMON/data/liveChannels"); //字符串中的\要用\\来表示。
			} catch (JSONException e) {
				e.printStackTrace();
			}			
			String apiString = apiJson.toString();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(apiString,apiPath, true);
		}
	}
	
	/** 
	 * 判断本地overApiPath路径下是否存在api.txt。
	 * @author jhonson
	 * @create 2017年3月2日 下午5:27:56
	 * @update
	 * @param  
	 * @return void
	 */
	public void initializeOverApiTxt() {		
		File file = new File(overApiPath);
		//若不存在，在本地按overApiPath路径生成overApi.txt。
		if(!file.exists()){
			JSONObject overApiJson = new JSONObject();				
			try {
				overApiJson.put("overApi", "http://192.168.0.51:8080/LEMON/data/epg/syncFinished"); //字符串中的\要用\\来表示。
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			String overApiString = overApiJson.toString();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(overApiString,overApiPath, true);
		}
	}

}
