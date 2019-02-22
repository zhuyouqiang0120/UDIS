package com.zens.udis.utils;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zens.udis.grab.GrabAqi;
import com.zens.udis.grab.GrabBuzz;
import com.zens.udis.grab.GrabRoad;
import com.zens.udis.grab.GrabScenicl;
import com.zens.udis.grab.GrabTalk;
import com.zens.udis.grab.GrabWeather;
import com.zens.udis.grab.GrabWhat;

/**
 * 定时任务
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月5日 下午5:37:25
 */

public class UDISJob implements Job {

	@SuppressWarnings("deprecation")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		new GrabAqi().grabAqi();
		System.out.println("执行AQI  " + new Date().toLocaleString());
		new GrabScenicl().grapScenicl();
		System.out.println("执行Scenicl  " + new Date().toLocaleString());
		new GrabWeather().grabWeather();
		System.out.println("执行Weather  " + new Date().toLocaleString());
		new GrabTalk().grabTalk();
		System.out.println("执行GrabTalk  " + new Date().toLocaleString());
		new GrabBuzz().grabBuzz();
		System.out.println("执行GrabBuzz  " + new Date().toLocaleString());
		new GrabWhat().grabWhat();
		System.out.println("执行GrabWhat  " + new Date().toLocaleString());
		new GrabRoad().grabRoad();
		System.out.println("执行GrabRoad  " + new Date().toLocaleString());
	}
}