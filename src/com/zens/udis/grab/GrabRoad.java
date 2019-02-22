package com.zens.udis.grab;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfinal.kit.JsonKit;
import com.zens.udis.entity.RoadEntity;
import com.zens.udis.impl.InterfaceImpl;
import com.zens.udis.utils.HttpRequest;
import com.zens.udis.utils.PicUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import net.sf.json.JSONArray;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:22:18
 */

public class GrabRoad {

	HttpRequest request = new HttpRequest();

	InterfaceImpl interfaceImpl = new InterfaceImpl();

	PicUtils picUtils = new PicUtils();

	@SuppressWarnings("deprecation")
	public void grabRoad() {

		String imgpath = picUtils.getImgPath() + "road/";
		
		String url = ResourceBundle.getBundle("url").getString("road");
		String result = request.sendGet("http://shanghaicity.openservice.kankanews.com/public/road/getnowpicopen", "");// url、cookie
		System.out.println(result);
		if (!result.equals("")) {
			JSONArray jsonArray = JSONArray.fromObject(result);

			RoadEntity roadEntity = new RoadEntity();

			//imgpath = "/Users/zyq/MyWork/webroot/media/imgcache/SHLife/" + "road/";

			String strImg = JSONArray.fromObject(jsonArray.get(0)).get(0).toString();
			GenerateImage(strImg, imgpath + "city.jpg");
			roadEntity.setCity(imgpath + "city.jpg");

			strImg = JSONArray.fromObject(jsonArray.get(1)).get(0).toString();
			GenerateImage(strImg, imgpath + "velocity.jpg");
			roadEntity.setVelocity(imgpath + "velocity.jpg");

			strImg = JSONArray.fromObject(jsonArray.get(2)).get(0).toString();
			GenerateImage(strImg, imgpath + "intercity.jpg");
			roadEntity.setIntercity(imgpath + "intercity.jpg");

			roadEntity.setCreatetime(new Date().toLocaleString());
			roadEntity.setRemark("sh");

			interfaceImpl.insertRoad(JsonKit.toJson(roadEntity));

		}
	}

	public static void main(String[] args) {
		new GrabRoad().grabRoad();
	}

	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
