package com.alieckxie.comparePom;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

public class TimerTest {

	@Test
	public void testTime2CleanMap(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H点m分s秒");
		Timer timer = new Timer(true);
		Date dateToStart = new Date();
		System.out.println("当前时间是：" + dateToStart);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToStart);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 5);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		dateToStart = calendar.getTime();
//		dateToStart.setSeconds(dateToStart.getSeconds() + 10);
		System.out.println("将在" + dateToStart + "开始执行");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Date date = new Date();
				System.out.println("在"+simpleDateFormat.format(date)+"秒定时清空了Map");
			}
		},dateToStart , 1000L);
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
