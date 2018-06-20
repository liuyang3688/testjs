package com.ieslab.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ieslab.base.service.BaseModelUpdateThread;
import com.ieslab.base.service.BujiancanshuService;
import com.ieslab.rdbdata.service_new.ParamCheckThread;
import com.ieslab.thread.ThreadManager;

public class GlobalListener implements ServletContextListener{


	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		String path = sce.getServletContext().getRealPath("/");
		GlobalPathUtil.path = path;
		GlobalPathUtil.temppath = path + "temp";
		//获取工程webName的名字
		GlobalPathUtil.webName =  this.proceeWebName(path);
		System.out.println("web应用启动");
		QueryConfig.getInstance();
		
		//初始化部件参数
		BujiancanshuService.bujiancanshu();
		System.out.println("初始化部件参数完成,长度:" + BujiancanshuService.map.size());
		
		//启动实时服务订单扫描线程
//		OrderFormCheckThread checkOrderRunnable = new OrderFormCheckThread();
//		Thread checkOrderThread = new Thread(checkOrderRunnable);
//		checkOrderThread.start();
		
//		ParamCheckThread run = new ParamCheckThread();
//		Thread checkParamThread = new Thread(run);
//		checkParamThread.start();
//		System.out.println("启动实时服务订单扫描线程完成");
		
		//启动线程管理
    	
//    	ThreadManager threadManager = new ThreadManager();
//    	threadManager.startAllThread();
//    	System.out.println("startAllThread end");
    	
    	System.out.println("启动缓存处理线程");
    	BaseModelUpdateThread updateThread = new BaseModelUpdateThread();
    	Thread baseupdateThread = new Thread(updateThread);
    	baseupdateThread.start();
    	
    	System.out.println("初始化二级颜色配置");
    	ErJiStateConfig.getInstance();
    	
	}
	 public String proceeWebName(String webRoot) {
		 String webName = "";
	        if (null != webRoot && !"".equals(webRoot)) {
	            String str[] = webRoot.replace("\\", "/").split("/");
	            if (str.length > 0) {
	                webName = str[str.length - 1];
	            }
	        }
	        return webName;
	    }

}
