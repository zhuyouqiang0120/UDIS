package com.zens.udis.grab;

import java.util.ResourceBundle;

import com.zens.udis.utils.HttpRequest;


/**
 * 
 * @author zhl
 * @mail zhuhl@zensvision.com
 * @time 2016年4月27日 上午10:34:27
 */
public class GrabSocial {
	HttpRequest hr = new HttpRequest();

	public String grabSocial(String userName, String userPwd) {
		try {
			String url = ResourceBundle.getBundle("url").getString("social") + "?idnum=" + userName + "&idpwd="
					+ userPwd;
			String result = hr.sendPost(url, "");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
