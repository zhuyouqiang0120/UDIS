package com.zens.udis.TVCrawler.services.utils;

import java.io.File;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Unfreeze {
	
	//检查unreachable.txt中哪些不可用的信息源距离添加日期超过两个星期，给它解冻，即将这条不可达的信息源从unreachable.txt中删掉。
	public void checkAndUnfreeze(String infoSourcePath){
		
		String unreachablePath = infoSourcePath + "xy/unreachable.txt";
		FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
		//将本地文件中的json格式的字符串读取出来。
		String jsonString = fileProcessingUtils.readTxt(unreachablePath);
		//将json字符串转化为json对象。
		JSONObject unreachableJson;
		try {
			unreachableJson = new JSONObject(jsonString);
			//从json对象中取出"reachable"所对应的值（一个json数组）。
			JSONArray array = unreachableJson.getJSONArray("unreachable");
			JSONArray newArray = new JSONArray();
			JSONObject newUnreachableJson = new JSONObject();
			//遍历每一个不可达的信息源。
			for(int i=0;i<array.length();i++){
				JSONObject j = array.getJSONObject(i);
				Iterator<?> iterator = j.keys();
				while(iterator.hasNext()){
				    String	key = (String) iterator.next();
				    String   value = j.getString(key);
				    //获取登记该信息源的日期。
				    String[] a = value.split(",");
				    String oldDate = a[2];
					//获取今天的日期。
				    DateUtils dateUtils1 = new DateUtils();
					String newDate = dateUtils1.getDateToday();
					//将两个日期进行比对，如果相距超过两周，就将该不可达的信息源解冻（从array中删掉）。
					DateUtils dateUtils2 = new DateUtils();
					int days = dateUtils2.getDaysBetween(newDate,oldDate);
					if(days<14){
						JSONObject stillBeUnreachable = new JSONObject();
						stillBeUnreachable.put(a[0],value);
						newArray.put(stillBeUnreachable);						
					}
				}				
			}			
			newUnreachableJson.put("unreachable",newArray);
			String string = newUnreachableJson.toString();
			//将原unreachable.txt删掉，并生成新的。
			File file = new File(unreachablePath);
			file.delete();
			FileProcessingUtils fileWriter = new FileProcessingUtils();
			fileWriter.fileWriter_writeIntoFile(string,unreachablePath, true);		
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
	}
	

}
