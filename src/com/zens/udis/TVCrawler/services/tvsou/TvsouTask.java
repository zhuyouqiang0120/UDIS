package com.zens.udis.TVCrawler.services.tvsou;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.zens.udis.TVCrawler.services.utils.*;


public class TvsouTask {
	int force = 0;
	String programesPath = "";
	String url = "";
	private String usefulXy = "";
	private ArrayList<String> tvsouProgrames; // 用来存放要抓取的频道参数列表。
	private ArrayList<String> GUIDList; // 用来存放GUID的列表，其中GUID依次与guatianProgrames中要抓取的频道对应（GUID是用来命名本地文件的一个字符串）。

	public TvsouTask(String programesPath, int force, ArrayList<String> tvsouProgrames, ArrayList<String> GUIDList,
			String usefulXy) {
		this.programesPath = programesPath;
		this.force = force;
		this.usefulXy = usefulXy;
		this.tvsouProgrames = tvsouProgrames;
		this.GUIDList = GUIDList;
	}

	public void core() throws UnknownHostException, IOException, SocketTimeoutException {
		// 如果force为1，就强制刷新本地缓存（直接将本地缓存文件夹删掉）。
		if (force == 1) {
			String tvsouPath = programesPath;
			File file = new File(tvsouPath);
			if (file.exists()) {
				FileProcessingUtils fileProcessingUtils = new FileProcessingUtils();
				fileProcessingUtils.deleteFolder(tvsouPath);
			}
		}

		for (int i = 0; i < tvsouProgrames.size(); i++) {
			String GUID = GUIDList.get(i);
			String pqpd = tvsouProgrames.get(i);
			System.out.println("本次的爬取频道为：" + pqpd);
			url = usefulXy + pqpd;
			String programePath = programesPath + GUID + ".txt";

			// 判断该节目在本地是否有缓存文件，若有，则获取文件的更新时间，如果时间不是本周的某一天就将该缓存文件删掉，重新爬取该频道的信息并在本地生成缓存文件。
			File file = new File(programePath);
			if (file.exists()) {
				FileProcessingUtils FileProcessingUtils = new FileProcessingUtils();
				String modifiedTime = FileProcessingUtils.getFileLatestUpdateTime(programePath);

				// 判断这个日期是不是本周的某一天
				int flag = 0;
				DateUtils dateUtils1 = new DateUtils();
				ArrayList<String> daysList = dateUtils1.getDaysOfThisWeek();
				for (int j = 0; j < 7; j++) {
					if (modifiedTime.equals(daysList.get(j))) {
						flag = 1;
					}
				}
				if (flag == 1) {
					// 什么都不做。
					System.out.println("什么都不做！");
				}
				if (flag == 0) { // 如果flag为0，说明本地的缓存文件的最近修改时间不是本周的，即该缓存文件中的内容不是本周的，所以应该将该缓存文件删掉，重新去信息源抓取新的信息。
					file.delete();
					// 去信息源爬取。
					tvsouCrawl(url, programePath);
				}
			} else { // 说明本地根本就没有该节目的缓存文件。
				// 去信息源爬取。
				tvsouCrawl(url, programePath);
			}
		}
	}

	public void tvsouCrawl(String url, String programePath)
			throws UnknownHostException, IOException, SocketTimeoutException {

		Spider spider = new Spider();
		Document content = spider.SendGet(url); // 去tvsou爬取该频道所对应的页面中的全部源代码。

		Elements daysUrl = spider.getDaysUrl(content); // 截取出这周七天的链接。

		// 提纯链接
		ArrayList<Programe> myCctv = spider.GetRecommendations(url, daysUrl);

		// 获取本周七天的日期，放在ArrayList中。
		DateUtils dateUtils2 = new DateUtils();
		ArrayList<String> daysList = dateUtils2.getDaysOfThisWeek();

		// 根据得到的数据拼接出一周的字符串，然后将这个字符串写入本地
		FileProcessingUtils fileWriter = new FileProcessingUtils();
		String weekString = "";
		for (int i = 0; i < myCctv.size(); i++) {
			String day = daysList.get(i);
			String[] d_a_y = day.split("-");
			day = d_a_y[0] + "年" + d_a_y[1] + "月" + d_a_y[2] + "日";
			weekString = weekString + day + "\r\n\r\n";
			Programe cctvObject = myCctv.get(i);
			String dayString = cctvObject.writeString();
			weekString = weekString + dayString;
		}
		weekString = weekString + "\r\n\r\n\r\n\r\n";
		fileWriter.fileWriter_writeIntoFile(weekString, programePath, true);

	}

}
