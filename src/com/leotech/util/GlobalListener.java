package com.leotech.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GlobalListener implements ServletContextListener{


	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		// 启动线程
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
