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
import com.zens.udis.entity.BuzzEntity;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.HttpRequest;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:22:18
 */

public class GrabBuzz {

	HttpRequest request = new HttpRequest();
	InterfaceImpl interfaceImpl = new InterfaceImpl();

	public void grabBuzz() {
		String url = ResourceBundle.getBundle("url").getString("buzzwords");
		String result = request.sendGet(url, "");// url、cookie
		// System.out.println(result);
		parserXml(result.replaceAll("&ldquo;", "").replaceAll("&rdquo;", ""));
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
			List<BuzzEntity> wlist = new ArrayList<BuzzEntity>();
			if (childList.size() != 0) {
				// interfaceImpl.deleteBuzz();
				for (Object o : childList) {
					Element e = (Element) o;
					BuzzEntity buzzEntity = new BuzzEntity();
					buzzEntity.setTitle(e.elementText("title"));
					buzzEntity.setTid(e.elementText("id"));
					buzzEntity.setFtitle(e.elementText("ftitle"));
					buzzEntity.setKeyboard(e.elementText("keyboard"));
					buzzEntity.setNewstime(e.elementText("newstime"));
					buzzEntity.setTitlepic(e.elementText("titlepic"));
					buzzEntity.setSmalltext(e.elementText("smalltext"));
					buzzEntity.setTitleurl(e.elementText("titleurl"));
					buzzEntity.setCreatetime(new Date().toLocaleString());
					buzzEntity.setRemark("sh");
					wlist.add(buzzEntity);
				}
			}

			interfaceImpl.insertBuzz(JsonKit.toJson(wlist));

		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
