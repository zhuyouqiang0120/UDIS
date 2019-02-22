package com.zens.udis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * 
 * @author ZYQ(zhuyq@zensvision.com)
 * @date  2015年9月22日下午2:46:59
 */

public class WriteFile {
	public void tofile(String json) throws IOException {
		
		String path = this.getClass().getClassLoader().getResource("/").getPath().concat("json/");
		String imgName = "data.json";
		String savepath = (path + imgName).replace("/WEB-INF/classes/", "/");
		try {
			File file = new File(savepath);
			if (!file.exists()) {
				file.createNewFile();
			}else{
				file.delete();
			}

			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(json);
			out.newLine();
			out.close();
			out = null;
			file = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("WriteFile Success!");
	}
	
	public void tofile1(String json) throws IOException {
		String path = this.getClass().getClassLoader().getResource("/").getPath().concat("json/");
		String imgName = "data.json";
		String savepath = (path + imgName).replace("/WEB-INF/classes/", "/");
		
		FileOutputStream fos = new FileOutputStream(savepath);
		OutputStreamWriter  out = new OutputStreamWriter(fos, "gbk");
		out.write(json);
		out.close();
		fos.close();
		System.out.println("WriteFile Success!");
	}
}
