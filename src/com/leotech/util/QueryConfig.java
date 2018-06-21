/********************************************************************
 *	版权所有 (C) 2009-2013 积成电子股份有限公司
 *	保留所有版权
 *	
 *	作者：	侯培彬
 *	日期：	2013-3-14
 *	摘要：	参数缓存类
 *  功能：      读取配置文件，把配置参数在此文件中缓存，此类只能读取，不能设置
 *
 *********************************************************************/

package com.leotech.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;



import org.w3c.dom.*;




import javax.xml.xpath.*;

public class QueryConfig {

	// 单例模式
	private static QueryConfig instance = null;
	private String version = "jixian"; // 版本，例如jixian
	private int csczEventSize = 1051; // 版本，例如jixian
	private int xtEventSize = 66; // 版本，例如jixian

	private String realIp = "127.0.0.1"; // 连接webdatas服务ip地址
	private String realPort = "1289"; // 连接webdatas服务端口号

	private String webproxyIp = "127.0.0.1"; // 连接webproxy服务ip地址
	private String webproxyPort = "15001"; // 连接webproxy服务端口号

	private String rtEventFlag = "0"; // 实时事项使用标志
	private String cacheNum = "10241"; // 后台缓存事项最大个数
	private String flexNum = "200"; // 前台显示事项最大个数
	
	private String hiseventAlarm = "0";
	
	private String rtdatawebservice1; //实时数据服务webService地址
	private String rtdatawebservice2; //实时数据服务webService地址
	private String topowebservice; //拓扑服务webService地址
	
	private String guzhangluboFilePath; //故障录波文件存放位置
	
	private String companyType; //公司类型标志

	public static QueryConfig getInstance() {
		if (instance == null) {
			instance = new QueryConfig();
		}
		return instance;
	}

	/**
	 * 从queryParam.xml配置文件中，读取配置参数
	 */
	private QueryConfig() {
		
		String configPath = GlobalPathUtil.path
				+ "/config/queryParam.xml";
		Logger.instance().runInfo("读取配置文件开始，路径为："+ configPath);
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			File configFile = new File(configPath);
			Document configDoc = builder.parse(configFile);
			XPath xpath = XPathFactory.newInstance().newXPath();
			companyType = (String) xpath.evaluate("/params/company/type", configDoc,
					XPathConstants.STRING);
			version = (String) xpath.evaluate("/params/system/version", configDoc,
					XPathConstants.STRING);
	    	Logger.instance().runInfo("程序版本为："+ version);
		    try{	
		    	csczEventSize = Integer.valueOf((String)(xpath.evaluate("/params/system/"+version+"/csczevent", configDoc,
						XPathConstants.STRING)));
		    	xtEventSize = Integer.valueOf((String)(xpath.evaluate("/params/system/"+version+"/xtevent", configDoc,
						XPathConstants.STRING)));
		    	Logger.instance().runInfo("csczevent："+ csczEventSize);
		    	Logger.instance().runInfo("xtevent："+ xtEventSize);
			} catch (Exception err) {
				Logger.instance().runInfo("/params/system/"+version+"/csczevent 不存在");
				Logger.instance().runInfo("/params/system/"+version+"/xtevent 不存在");
				Logger.instance().runInfo("/params/system/user/pasinvalidtime 不存在");
				Logger.instance().runInfo("csczevent默认值："+ csczEventSize);
		    	Logger.instance().runInfo("xtevent默认值："+ xtEventSize);
			}
			
			
			realIp = (String) xpath.evaluate("/params/webdatas/ip", configDoc,
					XPathConstants.STRING);
			realPort = (String) xpath.evaluate("/params/webdatas/port",
					configDoc, XPathConstants.STRING);
			Logger.instance().runInfo("webdatas运行机器配置ip为："+ realIp);
	    	Logger.instance().runInfo("webdatas运行机器配置端口为："+ realPort);

			webproxyIp = (String) xpath.evaluate("/params/webproxy/ip",
					configDoc, XPathConstants.STRING);
			webproxyPort = (String) xpath.evaluate("/params/webproxy/port",
					configDoc, XPathConstants.STRING);
			Logger.instance().runInfo("webproxy运行机器配置ip为："+ webproxyIp);
	    	Logger.instance().runInfo("webproxy运行机器配置端口为："+ webproxyPort);

			rtEventFlag = (String) xpath.evaluate(
					"/params/webproxy/rtevent/enable", configDoc,
					XPathConstants.STRING);
			cacheNum = (String) xpath.evaluate(
					"/params/webproxy/rtevent/cacheNum", configDoc,
					XPathConstants.STRING);
			flexNum = (String) xpath.evaluate(
					"/params/webproxy/rtevent/flexNum", configDoc,
					XPathConstants.STRING);
			Logger.instance().runInfo("实时事项使用标志为："+ rtEventFlag);
			Logger.instance().runInfo("实时事项后台缓存数据条数为："+ rtEventFlag);
			Logger.instance().runInfo("实时事项前台缓存数据条数为："+ rtEventFlag);
			
			hiseventAlarm = (String) xpath.evaluate("/params/hisevent/alarmMode", configDoc,
					XPathConstants.STRING);

			
			rtdatawebservice1 = (String) xpath.evaluate(
					"/params/rtdatawebservice1/url", configDoc,
					XPathConstants.STRING);
			rtdatawebservice2 = (String) xpath.evaluate(
					"/params/rtdatawebservice2/url", configDoc,
					XPathConstants.STRING);
			
			topowebservice = (String) xpath.evaluate(
					"/params/topowebservice/url", configDoc,
					XPathConstants.STRING);
			
			guzhangluboFilePath = (String) xpath.evaluate(
					"/params/guzhanglubofilepath/address", configDoc,
					XPathConstants.STRING);
			
			
			Logger.instance().runInfo("实时数据服务webService地址:"+ rtdatawebservice1);
			Logger.instance().runInfo("拓扑服务webService地址:"+ topowebservice);
		} catch (Exception err) {
			Logger.instance().runInfo("读取配置文件【" + configPath + "】异常：" + err);
		}
	}


	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getRtdatawebservice1() {
		return rtdatawebservice1;
	}

	public void setRtdatawebservice1(String rtdatawebservice1) {
		this.rtdatawebservice1 = rtdatawebservice1;
	}

	public String getRtdatawebservice2() {
		return rtdatawebservice2;
	}

	public void setRtdatawebservice2(String rtdatawebservice2) {
		this.rtdatawebservice2 = rtdatawebservice2;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getCsczEventSize() {
		return csczEventSize;
	}

	public void setCsczEventSize(int csczEventSize) {
		this.csczEventSize = csczEventSize;
	}

	public int getXtEventSize() {
		return xtEventSize;
	}

	public void setXtEventSize(int xtEventSize) {
		this.xtEventSize = xtEventSize;
	}

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public String getRealPort() {
		return realPort;
	}

	public void setRealPort(String realPort) {
		this.realPort = realPort;
	}

	public String getWebproxyIp() {
		return webproxyIp;
	}

	public void setWebproxyIp(String webproxyIp) {
		this.webproxyIp = webproxyIp;
	}

	public String getWebproxyPort() {
		return webproxyPort;
	}

	public void setWebproxyPort(String webproxyPort) {
		this.webproxyPort = webproxyPort;
	}

	public String getRtEventFlag() {
		return rtEventFlag;
	}

	public void setRtEventFlag(String rtEventFlag) {
		this.rtEventFlag = rtEventFlag;
	}

	public String getCacheNum() {
		return cacheNum;
	}

	public void setCacheNum(String cacheNum) {
		this.cacheNum = cacheNum;
	}

	public String getFlexNum() {
		return flexNum;
	}

	public void setFlexNum(String flexNum) {
		this.flexNum = flexNum;
	}

	public static void setInstance(QueryConfig instance) {
		QueryConfig.instance = instance;
	}

	public String getHiseventAlarm() {
		return hiseventAlarm;
	}

	public void setHiseventAlarm(String hiseventAlarm) {
		this.hiseventAlarm = hiseventAlarm;
	}

	public String getTopowebservice() {
		return topowebservice;
	}

	public void setTopowebservice(String topowebservice) {
		this.topowebservice = topowebservice;
	}

	public String getGuzhangluboFilePath() {
		return guzhangluboFilePath;
	}

	public void setGuzhangluboFilePath(String guzhangluboFilePath) {
		this.guzhangluboFilePath = guzhangluboFilePath;
	}

}
