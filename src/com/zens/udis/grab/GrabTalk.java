package com.zens.udis.grab;

import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.jfinal.kit.JsonKit;
import com.zens.udis.entity.TalkEntity;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.HttpRequest;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:22:18
 */

public class GrabTalk {

	HttpRequest request = new HttpRequest();
	InterfaceImpl interfaceImpl = new InterfaceImpl();

	public void grabTalk() {
		String url = ResourceBundle.getBundle("url").getString("talkshanghai");
		String result = request.sendGet(url, "");// url、cookie
		parserXml(result.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("&igrave;", "")
				.replaceAll("&icirc;", "").replaceAll("&iacute;", ""));
	}

	@SuppressWarnings("deprecation")
	public void parserXml(String fileName) {

		Document document1;
		try {
			document1 = DocumentHelper.parseText(fileName);
			// 获取根元素
			Element root = document1.getRootElement();
			// 获取所有子元素
			@SuppressWarnings("unchecked")
			List<Element> childList = root.elements();
			if (childList.size() != 0) {
				// interfaceImpl.deleteTalk();
				List<TalkEntity> wlist = new ArrayList<TalkEntity>();
				for (Object o : childList) {
					Element e = (Element) o;
					TalkEntity talkEntity = new TalkEntity();
					talkEntity.setTitle(e.elementText("title"));
					talkEntity.setTid(e.elementText("id"));
					talkEntity.setFtitle(e.elementText("ftitle"));
					talkEntity.setKeyboard(e.elementText("keyboard"));
					talkEntity.setNewstime(e.elementText("newstime"));
					talkEntity.setTitlepic(e.elementText("titlepic"));
					talkEntity.setSmalltext(e.elementText("smalltext"));
					talkEntity.setTitleurl(e.elementText("titleurl"));
					talkEntity.setCreatetime(new Date().toLocaleString());
					talkEntity.setRemark("sh");
					wlist.add(talkEntity);
				}

				interfaceImpl.insertTalk(JsonKit.toJson(wlist));
			} else {
				System.out.println("aaa");
			}

		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
