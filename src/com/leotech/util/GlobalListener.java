package com.leotech.util;

import com.leotech.rt.AirCondComThread;
import com.leotech.rt.DoorComThread;
import com.leotech.rt.PowerComThread;
import com.leotech.rt.WaterComThread;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GlobalListener implements ServletContextListener{
	private Thread airCondThread = null;
	private Thread powerThread = null;
	private Thread doorThread = null;
	private Thread waterThread = null;
	public void contextDestroyed(ServletContextEvent sce) {
		if (airCondThread != null) {
			airCondThread.interrupt();
			airCondThread = null;
			System.out.println("空调采集数据线程销毁");
		}
		if (powerThread != null) {
			powerThread.interrupt();
			powerThread = null;
			System.out.println("电量仪采集线程销毁");
		}
		if (doorThread != null) {
			doorThread.interrupt();
			doorThread = null;
			System.out.println("门禁采集线程销毁");
		}
		if (waterThread != null) {
			waterThread.interrupt();
			waterThread = null;
			System.out.println("漏水采集线程销毁");
		}
	}
	public void contextInitialized(ServletContextEvent sce) {
		// 启动线程
		airCondThread = new Thread(new AirCondComThread(), "airCondThread");
		airCondThread.start();
		System.out.println("空调采集线程启动");
		powerThread = new Thread(new PowerComThread(), "powerThread");
		powerThread.start();
		System.out.println("电量仪采集线程启动");
		doorThread = new Thread(new DoorComThread(), "doorThread");
		doorThread.start();
		System.out.println("门禁采集线程启动");
		waterThread = new Thread(new WaterComThread(), "waterThread");
		waterThread.start();
		System.out.println("漏水采集线程启动");
	}
}
