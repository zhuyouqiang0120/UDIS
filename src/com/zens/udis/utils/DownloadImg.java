package com.zens.udis.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;


public class DownloadImg {
	public String Setimg(String titlepic, String savepath) {
		//String host = ResourceBundle.getBundle("url").getString("host");
		
		//String path = this.getClass().getClassLoader().getResource("/").getPath().concat("images/code.jpg");
		//String savepath = path .replace("/WEB-INF/classes/", "/").substring(1);
		
		String imgName = titlepic.substring(titlepic.lastIndexOf("/") + 1);
		String imgpath = savepath + imgName;
		
		File file = new File(imgpath);
		OutputStream os = null;
		String str = titlepic;
		InputStream is = null;
		HttpURLConnection connection = null;
		URL userver = null;

		try {
			userver = new URL(str);
			connection = (HttpURLConnection) userver.openConnection();
			connection.connect();
			is = connection.getInputStream();
			os = new FileOutputStream(file);
			int b = is.read();
			while (b != -1) {
				os.write(b);
				b = is.read();
			}
			is.close();
			os.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return imgName;
	}
	
	public String Setimg(String titlepic) {
		String host = ResourceBundle.getBundle("url").getString("host");
		String path = this.getClass().getClassLoader().getResource("/").getPath().concat("images/");
		String imgName = titlepic.substring(titlepic.lastIndexOf("/") + 1);
		String savepath = (path + imgName).replace("/WEB-INF/classes/", "/");

		File file = new File(savepath);
		OutputStream os = null;
		String str = titlepic;
		InputStream is = null;
		HttpURLConnection connection = null;
		URL userver = null;

		try {
			userver = new URL(str);
			connection = (HttpURLConnection) userver.openConnection();
			connection.connect();
			is = connection.getInputStream();
			os = new FileOutputStream(file);
			int b = is.read();
			while (b != -1) {
				os.write(b);
				b = is.read();
			}
			is.close();
			os.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		String imgpath = host + "images/" + imgName;
		return imgpath;
	}
}
