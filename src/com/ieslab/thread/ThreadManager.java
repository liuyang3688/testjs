/********************************************************************
 *	版权所有 (C) 2009-2014 积成电子股份有限公司
 *	保留所有版权
 *	
 *	作者：	侯培彬
 *	日期：	2014-4-21
 *	摘要：	线程管理类
 *  功能：      
 *
 *********************************************************************/
package com.ieslab.thread;

import com.ieslab.util.QueryConfig;
import com.ieslab.util.logger.IesLogger;


public class ThreadManager {
	private static boolean firstStartFlag = true; //线程管理标志，目的是不能重复调用此函数

	public boolean startAllThread(){
		if(!firstStartFlag){
			IesLogger.instance().runInfo("已经启动一次线程管理，不允许重复启动。");
			return true;
		}
		
		
		//实时事项获取  2015-04-07 zhangtao
		String rtEventFlag = QueryConfig.getInstance().getRtEventFlag();
		if("1".equals(rtEventFlag)){
			//（1）webproxy的ip和端口
			String webPorxyIP = QueryConfig.getInstance().getWebproxyIp();
			int webPorxyPort = Integer.parseInt(QueryConfig.getInstance().getWebproxyPort());
			//（2）启动实时事项的参数事项获取
			byte bEventType = 0;
			
			//（6）启动实时事项的风机环境受累事项获取
		//2018-1-25不再获取该类事项
//			bEventType = 10;
//			RtEventSocket rtEventSocket_fjcz = new RtEventSocket(webPorxyIP,webPorxyPort,bEventType);
//			rtEventSocket_fjcz.initRtEventDeal();
			
		}
		
		firstStartFlag = false;
		IesLogger.instance().runInfo("线程管理结束");
		return true;
		
	}
}
