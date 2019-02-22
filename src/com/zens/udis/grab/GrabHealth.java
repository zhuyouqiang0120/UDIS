package com.zens.udis.grab;

import java.util.ResourceBundle;

import com.zens.udis.utils.HttpRequest;

import net.sf.json.JSONObject;

/**
 * 
 * @author zhl
 * @mail zhuhl@zensvision.com
 * @time 2016年4月27日 上午10:34:27
 */
public class GrabHealth {
	HttpRequest hr = new HttpRequest();

	public String grabHealth(String userName, String userPwd) {
		try {
			String url = ResourceBundle.getBundle("url").getString("health");
			String headetail = ResourceBundle.getBundle("url").getString("headetail");
			String result = hr.sendPost(url, "idnum=" + userName + "&idpwd=" + userPwd);
			JSONObject jsonObject = JSONObject.fromObject(result);

			if (jsonObject.getString("errcode").equals("200")) {
				String hash = jsonObject.getString("data");
				String dd = hr.sendGet(headetail + hash, "");

				return dd;
			} else {
				return "error";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
