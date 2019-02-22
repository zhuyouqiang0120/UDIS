package com.zens.udis.grab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.jfinal.kit.JsonKit;
import com.zens.udis.entity.WhatEntity;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.DownloadImg;
import com.zens.udis.utils.HttpRequest;
import com.zens.udis.utils.PicUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:22:18
 */

public class GrabWhat {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();
	DownloadImg downloadImg = new DownloadImg();
	PicUtils picUtils = new PicUtils();

	InterfaceImpl interfaceImpl = new InterfaceImpl();

	public void grabWhat() {
		String url = ResourceBundle.getBundle("url").getString("whaton");
		System.out.println(url);
		String result = request.sendGet(url, "");// url、cookie
		// System.out.println(result.replaceAll("&", "&amp;"));
		try {
			parserXml(result.replaceAll("&", "&amp;"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void parserXml(String fileName) {
		String imgpath = picUtils.getImgPath() + "what/";
		
		//imgpath = "/Users/zyq/MyWork/webroot/media/imgcache/SHLife/" + "what/";
		
		Document document1;
		try {
			document1 = DocumentHelper.parseText(fileName);
			// 获取根元素
			Element root = document1.getRootElement();
			// 获取所有子元素
			@SuppressWarnings("unchecked")
			List<Element> childList = root.elements();
			if (childList.size() != 0) {
				//interfaceImpl.deleteWhat();
				List<WhatEntity> wlist = new ArrayList<WhatEntity>();
				for (Object o : childList) {
					Element e = (Element) o;
					WhatEntity whatEntity = new WhatEntity();
					whatEntity.setTitle(e.elementText("title"));
					whatEntity.setTid(e.elementText("id"));
					whatEntity.setFtitle(e.elementText("ftitle"));
					whatEntity.setKeyboard(e.elementText("keyboard"));
					whatEntity.setNewstime(e.elementText("newstime"));

					String titlepic = e.elementText("titlepic");
					System.out.println(titlepic); 
					if (titlepic.indexOf("http://") == -1 && titlepic.indexOf("https://") == -1) {
						titlepic = "http://act.shanghaicity.openservice.kankanews.com" + titlepic;
					}

					String imgname = downloadImg.Setimg(titlepic, imgpath);
					whatEntity.setTitlepic(imgpath + imgname);

					whatEntity.setSmalltext(e.elementText("smalltext").replace("Phone:", "|Phone=").replace("||Phone=", "|Phone="));
					whatEntity.setTitleurl(e.elementText("titleurl"));
					whatEntity.setCreatetime(new Date().toLocaleString());
					whatEntity.setRemark("sh");
					wlist.add(whatEntity);
				}
				interfaceImpl.insertWhat(JsonKit.toJson(wlist));
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String titlepic = "/contentimg/2018/04/16/f8c49339-d4e9-463d-861f-503e0c75fb9b_0.jpg";
		System.out.println((titlepic.indexOf("http://") == -1 && titlepic.indexOf("https://") == -1));
	}
}
