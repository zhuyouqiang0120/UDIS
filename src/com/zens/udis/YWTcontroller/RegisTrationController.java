package com.zens.udis.YWTcontroller;

import java.util.Iterator;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.jfinal.core.Controller;
import com.zens.udis.utils.RegDic;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.encoding.XMLType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * 
 * @author ZYQ(zhuyq@zensvision.com)
 * @date  2015年9月21日上午11:35:49
 */

public class RegisTrationController extends Controller{
	
	private String loginurl = "http://221.4.189.248/jmsyypt/ws/jmsyyptlogin?wsdl=jmsyyptlogin.wsdl";
	private String guesturl = "http://221.4.189.248/jmsyypt/ws/jmsyyptguest";
	private String namespace = "http://service.ws.jmws.wondersgroup.com/";
	private JSONObject object = new JSONObject();
	private JSONArray data = new JSONArray();
	private RegDic regDic = new RegDic();
	private static String  recode = "";
	
	public void login() throws Exception{//2.4.4.4	用户登录
		//"GD0002", "4", "gd0002", "21218cca77804d2ba1922c33e0151105"
		String jgdm = getPara("jgdm");
		String zhlx = getPara("zhlx");
		String zhm = getPara("zhm");
		String mm = getPara("mm");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(loginurl);
	
		call.setOperationName(new QName(namespace, "login"));// WSDL里面描述的接口名称
		

		//call.setUseSOAPAction(true);
		//call.setSOAPActionURI(namespace);
		
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("zhlx", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("zhm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("mm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, zhlx, zhm, mm, "" });
 
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		Element m3 = rs.element("m3");
		
		String rcode = m1.getText().equals("0") ? "error！" : m3.getText();
		recode = rcode;
		object.element("get", "login");
		object.element("inter", "登录");
		object.element("status", m1.getText());
		object.element("rcode", rcode);
		
		renderText(object.toString());
	}
	
	
	public void getStandardDep() throws Exception{//2.4.4.5	获取标准科室
		String jgdm = getPara("jgdm");
		String rcode = recode;//recode;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getStandardDep"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, "" });

		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("bzksdm", item.elementTextTrim("c1"));
			json.element("bzksmc", item.elementTextTrim("c2"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getStandardDep");
		object.element("inter", "获取标准科室");

		renderText(object.toString());
	}
	
	public void getStandardPro() throws Exception{//2.4.4.6	获取标准专科
		String jgdm = getPara("jgdm");
		String rcode = recode;
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getStandardPro"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, "" });
		

		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("bzksdm", item.elementTextTrim("c1"));
			json.element("bzksmc", item.elementTextTrim("c2"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getStandardPro");
		object.element("inter", "获取标准专科");

		renderText(object.toString());
	}
	
	public void getHosinfo() throws Exception{//2.4.4.7	获取医院信息
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String ssqx = getPara("ssqx");
		String yydm = getPara("yydm");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getHosinfo"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ssqx", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, ssqx, yydm, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("yymc", item.elementTextTrim("c2"));
			json.element("jbdm", regDic.YYJB(item.elementTextTrim("c3")));
			json.element("lbdm", regDic.YYJB(item.elementTextTrim("c4")));
			json.element("ssxzq", regDic.XZQDM(item.elementTextTrim("c5")));
			json.element("ssjz", item.elementTextTrim("c6"));
			json.element("yydz", item.elementTextTrim("c7"));
			json.element("fwsj", item.elementTextTrim("c8"));
			json.element("fwdh", item.elementTextTrim("c9"));
			json.element("cclx", item.elementTextTrim("c10"));
			json.element("lxrxm", item.elementTextTrim("c11"));
			json.element("lxrdh", item.elementTextTrim("c12"));
			json.element("lxsj", item.elementTextTrim("c13"));
			json.element("yyjj", item.elementTextTrim("c14"));
			json.element("pic", ResourceBundle.getBundle("url").getString("hosinfo")+item.elementTextTrim("c1")+".png");
			 
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getHosinfo");
		object.element("inter", "获取医院信息");
		
		renderText(object.toString());
	}
	
	public void getDepinfo() throws Exception{//2.4.4.8	获取科室信息
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String ksmc = new String(getPara("ksmc").getBytes("iso-8859-1"), "utf-8");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getDepinfo"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ksmc", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, ksmc, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("ksdm", item.elementTextTrim("c2"));
			json.element("ksmc", item.elementTextTrim("c3"));
			json.element("bzksdm", item.elementTextTrim("c4"));
			json.element("ksdz", item.elementTextTrim("c5"));
			json.element("zxdh", item.elementTextTrim("c6"));
			json.element("ksjj", item.elementTextTrim("c7"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getDepinfo");
		object.element("inter", "获取科室信息");
		
		renderText(object.toString());
	}
	
	
	public void getProfessinfo() throws Exception{//2.4.4.9	获取专科信息
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String ksdm = getPara("ksdm");
		String zkmc = new String(getPara("zkmc").getBytes("iso-8859-1"), "utf-8");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getProfessinfo"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ksdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("zkmc", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, ksdm, zkmc, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("zkdm", item.elementTextTrim("c2"));
			json.element("zkmc", item.elementTextTrim("c3"));
			json.element("ksdm", item.elementTextTrim("c4"));
			json.element("bzzkdm", item.elementTextTrim("c5"));
			json.element("zkdd", item.elementTextTrim("c6"));
			json.element("zxdh", item.elementTextTrim("c7"));
			json.element("ksjj", item.elementTextTrim("c8"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getProfessinfo");
		object.element("inter", "获取专科信息");
		
		renderText(object.toString());
	}
	
	
	public void getDocinfo() throws Exception{//2.4.4.10	获取医生信息
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String ksdm = getPara("ksdm");
		String ysxm = new String(getPara("ysxm").getBytes("iso-8859-1"), "utf-8");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getDocinfo"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ksdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ysxm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, ksdm, ysxm, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("gh", item.elementTextTrim("c2"));
			json.element("xm", item.elementTextTrim("c3"));
			json.element("xb", regDic.XB(item.elementTextTrim("c4")));
			json.element("csrq", item.elementTextTrim("c5"));
			json.element("zjlx", item.elementTextTrim("c6"));
			json.element("zjhm", item.elementTextTrim("c7"));
			json.element("szksdm", item.elementTextTrim("c8"));
			json.element("cynf", item.elementTextTrim("c9"));
			json.element("zyzw", item.elementTextTrim("c10"));
			json.element("zcdm", regDic.ZCDM(item.elementTextTrim("c11")));
			json.element("xldm", item.elementTextTrim("c12"));
			json.element("xwdm", item.elementTextTrim("c13"));
			json.element("lxdh", item.elementTextTrim("c14"));
			json.element("tc", item.elementTextTrim("c15"));
			json.element("jj", item.elementTextTrim("c16"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getDocinfo");
		object.element("inter", "获取医生信息");
		
		renderText(object.toString());
	}
	
	public void getBespOrder() throws Exception{//2.4.4.11	预约单明细查询
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String ghdh = getPara("ghdh");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getBespOrder"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ghdh", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, ghdh, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("yydh", item.elementTextTrim("c2"));
			json.element("bbdm", item.elementTextTrim("c3"));
			json.element("kmdm", item.elementTextTrim("c4"));
			json.element("yylxdm", item.elementTextTrim("c5"));
			json.element("ksdm", item.elementTextTrim("c6"));
			json.element("ksmc", item.elementTextTrim("c7"));
			json.element("zkdm", item.elementTextTrim("c8"));
			json.element("zkmc", item.elementTextTrim("c9"));
			json.element("ysgh", item.elementTextTrim("c10"));
			json.element("ysxm", item.elementTextTrim("c11"));
			json.element("yyrq", item.elementTextTrim("c12"));
			json.element("kssj", item.elementTextTrim("c13"));
			json.element("jssh", item.elementTextTrim("c14"));
			json.element("ghf", item.elementTextTrim("c15"));
			json.element("jzd", item.elementTextTrim("c16"));
			json.element("yyrxm", item.elementTextTrim("c17"));
			json.element("xb", item.elementTextTrim("c18"));
			json.element("csrq", item.elementTextTrim("c19"));
			json.element("kh", item.elementTextTrim("c20"));
			json.element("klx", item.elementTextTrim("c21"));
			json.element("sfzjlx", item.elementTextTrim("c22"));
			json.element("sfzjhm", item.elementTextTrim("c23"));
			json.element("lxdh", item.elementTextTrim("c24"));
			json.element("sjhm", item.elementTextTrim("c25"));
			json.element("yyfs", regDic.YYFS(item.elementTextTrim("c26")));
			json.element("yydzt", regDic.YYDZT(item.elementTextTrim("c27")));
			json.element("zffs", item.elementTextTrim("c28"));
			json.element("pbyxzt", regDic.PBYXZT(item.elementTextTrim("c29")));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getBespOrder");
		object.element("inter", "预约单明细查询");
		
		renderText(object.toString());
	}

	
	public void getBespOrderHis() throws Exception{//2.4.4.12	个人预约单记录查询
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String bespsdt = getPara("bespsdt");
		String bespedt = getPara("bespedt");
		int stat = getParaToInt("stat");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getBespOrderHis"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("bespsdt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("bespedt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("stat", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, bespsdt, bespedt, stat, "" });
		System.out.println(result); 
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("yydh", item.elementTextTrim("c2"));
			json.element("bbdm", item.elementTextTrim("c3"));
			json.element("kmdm", item.elementTextTrim("c4"));
			json.element("yylxdm", item.elementTextTrim("c5"));
			json.element("ksdm", item.elementTextTrim("c6"));
			json.element("ksmc", item.elementTextTrim("c7"));
			json.element("zkdm", item.elementTextTrim("c8"));
			json.element("zkmc", item.elementTextTrim("c9"));
			json.element("ysgh", item.elementTextTrim("c10"));
			json.element("ysxm", item.elementTextTrim("c11"));
			json.element("yyrq", item.elementTextTrim("c12"));
			json.element("kssj", item.elementTextTrim("c13"));
			json.element("jssh", item.elementTextTrim("c14"));
			json.element("ghf", item.elementTextTrim("c15"));
			json.element("jzd", item.elementTextTrim("c16"));
			json.element("yyrxm", item.elementTextTrim("c17"));
			json.element("xb", item.elementTextTrim("c18"));
			json.element("csrq", item.elementTextTrim("c19"));
			json.element("kh", item.elementTextTrim("c20"));
			json.element("klx", item.elementTextTrim("c21"));
			json.element("sfzjlx", item.elementTextTrim("c22"));
			json.element("sfzjhm", item.elementTextTrim("c23"));
			json.element("lxdh", item.elementTextTrim("c24"));
			json.element("sjhm", item.elementTextTrim("c25"));
			json.element("yyfs", regDic.YYFS(item.elementTextTrim("c26")));
			json.element("yydzt", regDic.YYDZT(item.elementTextTrim("c27")));
			json.element("zffs", item.elementTextTrim("c28"));
			json.element("pbyxzt", regDic.PBYXZT(item.elementTextTrim("c29")));
			json.element("lyzt", regDic.LYZT(item.elementTextTrim("c30")));
			json.element("pjzt", regDic.PJZT(item.elementTextTrim("c31")));
			json.element("pbyxzt", item.elementTextTrim("c32"));
			json.element("pbyxzt", item.elementTextTrim("c33"));
			json.element("pbyxzt", item.elementTextTrim("c34"));
			json.element("pbyxzt", item.elementTextTrim("c35"));
			json.element("pbyxzt", item.elementTextTrim("c36"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getBespOrderHis");
		object.element("inter", "个人预约单记录查询");
		
		renderText(object.toString());
	}

	public void getDoctorSche() throws Exception{//2.4.4.13	开诊医生排班查询(专家预约)
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String sdt = getPara("sdt");
		String edt = getPara("edt");
		String deptno = getPara("deptno");
		String profesno = getPara("profesno");
		String docno = getPara("docno");
		String docnm = new String(getPara("docnm").getBytes("iso-8859-1"), "utf-8");
		String km = getPara("km");
		String bb = getPara("bb");
		String sstat = getPara("sstat");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getDoctorSche"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("sdt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("edt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("deptno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("profesno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("docno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("docnm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("km", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("bb", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("sstat", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, sdt, edt, deptno, profesno, docno, docnm, km, bb, sstat, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("pcbh", item.elementTextTrim("c2"));
			json.element("bcbh", item.elementTextTrim("c3"));
			json.element("bb", regDic.BBDM(item.elementTextTrim("c4")));
			json.element("kmlx", regDic.KSZCDM(item.elementTextTrim("c5")));
			json.element("ksdm", item.elementTextTrim("c6"));
			json.element("ksmc", item.elementTextTrim("c7"));
			json.element("zkdm", item.elementTextTrim("c8"));
			json.element("zkmc", item.elementTextTrim("c9"));
			json.element("ysgh", item.elementTextTrim("c10"));
			json.element("ysxm", regDic.ZCDM(item.elementTextTrim("c11")));
			json.element("yyrq", item.elementTextTrim("c12"));
			json.element("fhsl", item.elementTextTrim("c13"));
			json.element("yysl", item.elementTextTrim("c14"));
			json.element("tc", regDic.PBYXZT(item.elementTextTrim("c15")));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getDoctorSche");
		object.element("inter", "开诊医生排班查询(专家预约)");
		
		renderText(object.toString());
	}
	
	public void getDoctorScheItem() throws Exception{//2.4.4.14	开诊医生排班明细(专家预约)
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String sdt = getPara("sdt");
		String docno = getPara("docno");
		String bb = getPara("bb");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getDoctorScheItem"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("sdt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("docno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("bb", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, sdt, docno, bb, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("pbid", item.elementTextTrim("c1"));
			json.element("yydm", item.elementTextTrim("c2"));
			json.element("pcbh", item.elementTextTrim("c3"));
			json.element("bcbh", item.elementTextTrim("c4"));
			json.element("bb", regDic.BBDM(item.elementTextTrim("c5")));
			json.element("kmlx", regDic.KSZCDM(item.elementTextTrim("c6")));
			json.element("ksdm", item.elementTextTrim("c7"));
			json.element("ksmc", item.elementTextTrim("c8"));
			json.element("zkdm", item.elementTextTrim("c9"));
			json.element("zkmc", item.elementTextTrim("c10"));
			json.element("ysgh", item.elementTextTrim("c11"));
			json.element("ysxm", item.elementTextTrim("c12"));
			json.element("yyrq", item.elementTextTrim("c13"));
			json.element("kssj", item.elementTextTrim("c14"));
			json.element("jssj", item.elementTextTrim("c15"));
			json.element("ghf", item.elementTextTrim("c16"));
			json.element("fhsl", item.elementTextTrim("c17"));
			json.element("yysl", item.elementTextTrim("c18"));
			json.element("yxzt", regDic.YXZT(item.elementTextTrim("c19")));
			json.element("yyqk", regDic.YYQK(item.elementTextTrim("c20")));
			json.element("jzmz", item.elementTextTrim("c21"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getDoctorScheItem");
		object.element("inter", "开诊医生排班明细(专家预约)");
		
		renderText(object.toString());
	}
	
	public void getProfesSche() throws Exception{//2.4.4.15	按科室进行排班查询(专家预约)
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String sdt = getPara("sdt");
		String edt = getPara("edt");
		String deptno = getPara("deptno");
		String profesno = getPara("profesno");
		String km = getPara("km");
		String bb = getPara("bb");
		String sstat = getPara("sstat");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getProfesSche"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("sdt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("edt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("deptno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("profesno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("km", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("bb", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("sstat", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, sdt, edt, deptno, profesno, km, bb, sstat, "" });
		
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydm", item.elementTextTrim("c1"));
			json.element("ksdm", item.elementTextTrim("c2"));
			json.element("ksmc", item.elementTextTrim("c3"));
			json.element("zkdm", item.elementTextTrim("c4"));
			json.element("zkmc", item.elementTextTrim("c5"));
			json.element("yyrq", item.elementTextTrim("c6"));
			json.element("fhsl", item.elementTextTrim("c7"));
			json.element("yysl", item.elementTextTrim("c8"));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getProfesSche");
		object.element("inter", "按科室进行排班查询(专家预约)");
		
		renderText(object.toString());
	}
	
	public void getProfesSche2() throws Exception{//2.4.4.16	开诊科室排班查询(科室预约)
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String yydm = getPara("yydm");
		String sdt = getPara("sdt");
		String edt = getPara("edt");
		String deptno = getPara("deptno");
		String profesno = getPara("profesno");
		String km = getPara("km");
		String bb = getPara("bb");
		String sstat = getPara("sstat");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "getProfesSche2"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("yydm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("sdt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("edt", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("deptno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("profesno", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("km", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("bb", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("sstat", XMLType.XSD_INT, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, yydm, sdt, edt, deptno, profesno, km, bb, sstat, "" });

		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("pbid", item.elementTextTrim("c1"));
			json.element("yydm", item.elementTextTrim("c2"));
			json.element("pcbh", item.elementTextTrim("c3"));
			json.element("bcbh", item.elementTextTrim("c4"));
			json.element("bb", regDic.BBDM(item.elementTextTrim("c5")));
			json.element("kmlx", regDic.KSZCDM(item.elementTextTrim("c6")));
			json.element("ksdm", item.elementTextTrim("c7"));
			json.element("ksmc", item.elementTextTrim("c8"));
			json.element("zkdm", item.elementTextTrim("c9"));
			json.element("zkmc", item.elementTextTrim("c10"));
			json.element("yyrq", item.elementTextTrim("c11"));
			json.element("ghf", item.elementTextTrim("c12"));
			json.element("fhsl", item.elementTextTrim("c13"));
			json.element("yysl", item.elementTextTrim("c14"));
			json.element("yxzt", regDic.YXZT(item.elementTextTrim("c15")));
			
			data.add(json);
		}
		object.element("data", data);
		object.element("get", "getProfesSche2");
		object.element("inter", "开诊科室排班查询(科室预约)");
		
		renderText(object.toString());
	}
	
	public void doBespOrder() throws Exception{//2.4.4.17	预约挂号
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String pbid = getPara("pbid");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "doBespOrder"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("pbid", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, pbid, "" });
	
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		Element m2 = rs.element("m2");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydh", item.elementTextTrim("c1"));
			json.element("yydm", item.elementTextTrim("c2"));
			json.element("bb", regDic.BBDM(item.elementTextTrim("c3")));
			json.element("kmlx", regDic.KSZCDM(item.elementTextTrim("c4")));
			json.element("ksdm", item.elementTextTrim("c5"));
			json.element("ksmc", item.elementTextTrim("c6"));
			json.element("zkdm", item.elementTextTrim("c7"));
			json.element("zkmc", item.elementTextTrim("c8"));
			json.element("yylx", regDic.YYLX(item.elementTextTrim("c9")));
			json.element("ysgh", item.elementTextTrim("c10"));
			json.element("ysxm", item.elementTextTrim("c11"));
			json.element("yyrq", item.elementTextTrim("c12"));
			json.element("kssj", item.elementTextTrim("c13"));
			json.element("jssj", item.elementTextTrim("c14"));
			json.element("ghf", item.elementTextTrim("c15"));
			json.element("jzdms", item.elementTextTrim("c16"));
			json.element("yyrxm", item.elementTextTrim("c17"));
			json.element("xb", regDic.XB(item.elementTextTrim("c18")));
			json.element("kh", item.elementTextTrim("c19"));
			json.element("klx", item.elementTextTrim("c20").equals("1") ? "社保" : "其他");
			json.element("lxdh", item.elementTextTrim("c21"));
			json.element("sjhm", item.elementTextTrim("c22"));
			json.element("sfzjlx", item.elementTextTrim("c23").equals("1") ? "身份证" : "其他");
			json.element("sfzjhm", item.elementTextTrim("c24"));
			json.element("yyfs", regDic.YYFS(item.elementTextTrim("c25")));
			json.element("zffs", item.elementTextTrim("c26"));
			
			data.add(json);
		}
		object.element("data", m1.getText().equals("1") ? data : m2.getText());
		object.element("get", "doBespOrder");
		object.element("inter", "预约挂号");
		
		renderText(object.toString());
	}
	
	public void doBespOrderPerson() throws Exception{//2.4.4.18	预约挂号(个人账户)
		
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String pbid = getPara("pbid");
		String xm = getPara("xm");
		String xb = getPara("xb");
		//String csrq = getPara("csrq");
		String zjlx = getPara("zjlx");
		String zjhm = getPara("zjhm");
		String sjhm = getPara("sjhm");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "doBespOrderPerson"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("pbid", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("xm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("xb", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		//call.addParameter("csrq", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("zjlx", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("zjhm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("sjhm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, pbid, xm, xb, zjlx, zjhm, sjhm, "" });
	
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		Element m2 = rs.element("m2");
		object.element("status", m1.getText());
		Iterator<?> iter = rs.elementIterator("r"); // 获取根节点下的子节点head
		while (iter.hasNext()) {
			JSONObject json = new JSONObject();
			Element item = (Element) iter.next();
			json.element("yydh", item.elementTextTrim("c1"));
			json.element("yydm", item.elementTextTrim("c2"));
			json.element("bb", regDic.BBDM(item.elementTextTrim("c3")));
			json.element("kmlx", regDic.KSZCDM(item.elementTextTrim("c4")));
			json.element("ksdm", item.elementTextTrim("c5"));
			json.element("ksmc", item.elementTextTrim("c6"));
			json.element("zkdm", item.elementTextTrim("c7"));
			json.element("zkmc", item.elementTextTrim("c8"));
			json.element("yylx", regDic.YYLX(item.elementTextTrim("c9")));
			json.element("ysgh", item.elementTextTrim("c10"));
			json.element("ysxm", item.elementTextTrim("c11"));
			json.element("yyrq", item.elementTextTrim("c12"));
			json.element("kssj", item.elementTextTrim("c13"));
			json.element("jssj", item.elementTextTrim("c14"));
			json.element("ghf", item.elementTextTrim("c15"));
			json.element("jzdms", item.elementTextTrim("c16"));
			json.element("yyrxm", item.elementTextTrim("c17"));
			json.element("xb", regDic.XB(item.elementTextTrim("c18")));
			json.element("kh", item.elementTextTrim("c19"));
			json.element("klx", item.elementTextTrim("c20").equals("1") ? "社保" : "其他");
			json.element("lxdh", item.elementTextTrim("c21"));
			json.element("sjhm", item.elementTextTrim("c22"));
			json.element("sfzjlx", item.elementTextTrim("c23").equals("1") ? "身份证" : "其他");
			json.element("sfzjhm", item.elementTextTrim("c24"));
			json.element("yyfs", regDic.YYFS(item.elementTextTrim("c25")));
			json.element("zffs", item.elementTextTrim("c26"));
			
			data.add(json);
		}
		object.element("data", m1.getText().equals("1") ? data : m2.getText());
		object.element("get", "doBespOrderPerson");
		object.element("inter", "预约挂号(个人账户)");
		
		renderText(object.toString());
	}
	
	
	public void doBespCancel() throws Exception{//2.4.4.19	预约单取消
		String jgdm = getPara("jgdm");
		String rcode = recode;
		String ghdh = getPara("ghdh");
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(guesturl);
	
		call.setOperationName(new QName(namespace, "doBespCancel"));// WSDL里面描述的接口名称
		call.addParameter("jgdm", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("rcode", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("ghdh", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.addParameter("params", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		String result = (String) call.invoke(new Object[] { jgdm, rcode, ghdh, "" });
	
		Document xmlDoc = null;
		xmlDoc = DocumentHelper.parseText(result);
		Element rs = xmlDoc.getRootElement(); // 获取根节点
		Element m1 = rs.element("m1");
		Element m2 = rs.element("m2");
		object.element("status", m1.getText());
		object.element("data",  m2.getText());
		object.element("get", "doBespCancel");
		object.element("inter", "预约单取消");
		
		renderText(object.toString());
	}
	
}
