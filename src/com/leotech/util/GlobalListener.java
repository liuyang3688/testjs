package com.leotech.util;

import com.leotech.rt.RtThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GlobalListener implements ServletContextListener{
	private Thread thdGenData = null;
	public void contextDestroyed(ServletContextEvent sce) {
		if (thdGenData != null) {
			thdGenData.interrupt();
			thdGenData = null;
		}
		System.out.println("线程销毁");
	}
	public void contextInitialized(ServletContextEvent sce) {
		// 启动线程
		System.out.println("线程启动");
		thdGenData = new Thread(new RtThread(), "gendata");
		thdGenData.start();
	}
}
