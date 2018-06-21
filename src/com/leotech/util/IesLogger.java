package com.leotech.util;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * @author Li Hao
 */
public class IesLogger {

	private Logger logger = null;
	
	private static IesLogger iesLogger = null;
	
	public IesLogger(){
		logger = Logger.getLogger(IesLogger.class);
	}
	
	public static IesLogger getInstance(){
		if(iesLogger == null){
			iesLogger = new IesLogger();
		}
		return iesLogger;
	}
	
	public void info(String info){
		logger.info(info);
	}
	
	public void error(String info){
		logger.error(info);
	}
}
