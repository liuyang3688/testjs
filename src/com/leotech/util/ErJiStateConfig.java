package com.leotech.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class ErJiStateConfig {
	private static ErJiStateConfig instance = null;
	/*private String fadian = "#48993d"; 
	private String xiandian = "#b98518";
	private String guzhang = "#a12b38";
	private String weihu = "#e29e33";
	private String shoulei = "#6d3756";
	private String daiji = "#91a2d4";
	private String weizhi = "#565656";
	private String lixian = "#565656";*/
	private String daifeng = "#D72424";
	private String fadian = "#F4B50A";
	private String tingji = "#55B416";
	private String weihu = "#0DC4AC";
	private String shoulei = "#0D27EC";
	private String qita = "#D829D2";

	public static ErJiStateConfig getInstance() {
		if (instance == null) {
			instance = new ErJiStateConfig();
		}
		return instance;
	}
	/**
	 * 从erjistatusconfig.xml配置文件中，读取配置参数
	 */
	private ErJiStateConfig() {
		
		String configPath = GlobalPathUtil.path+ "/config/erjistatusconfig.xml";
		Logger.instance().runInfo("读取配置文件开始，路径为："+ configPath);
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			File configFile = new File(configPath);
			Document configDoc = builder.parse(configFile);
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			/*fadian = (String) xpath.evaluate("/params/statuscolor/fadian", configDoc,XPathConstants.STRING);
			xiandian = (String) xpath.evaluate("/params/statuscolor/xiandian", configDoc,XPathConstants.STRING);
			guzhang = (String) xpath.evaluate("/params/statuscolor/fadian", configDoc,XPathConstants.STRING);
			weihu = (String) xpath.evaluate("/params/statuscolor/weihu", configDoc,XPathConstants.STRING);
			shoulei = (String) xpath.evaluate("/params/statuscolor/shoulei", configDoc,XPathConstants.STRING);
			daiji = (String) xpath.evaluate("/params/statuscolor/daiji", configDoc,XPathConstants.STRING);
			weizhi = (String) xpath.evaluate("/params/statuscolor/weizhi", configDoc,XPathConstants.STRING);
			lixian = (String) xpath.evaluate("/params/statuscolor/lixian", configDoc,XPathConstants.STRING);*/
			
			daifeng = (String) xpath.evaluate("/params/statuscolor/daifeng", configDoc,XPathConstants.STRING);
			fadian = (String) xpath.evaluate("/params/statuscolor/fadian", configDoc,XPathConstants.STRING);
			tingji = (String) xpath.evaluate("/params/statuscolor/tingji", configDoc,XPathConstants.STRING);
			weihu = (String) xpath.evaluate("/params/statuscolor/weihu", configDoc,XPathConstants.STRING);
			shoulei = (String) xpath.evaluate("/params/statuscolor/shoulei", configDoc,XPathConstants.STRING);
			qita = (String) xpath.evaluate("/params/statuscolor/qita", configDoc,XPathConstants.STRING);
			
			/*IesLogger.instance().runInfo("二级状态发电颜色为："+ fadian);
			IesLogger.instance().runInfo("二级状态限电颜色为："+ xiandian);
			IesLogger.instance().runInfo("二级状态故障颜色为："+ guzhang);
			IesLogger.instance().runInfo("二级状态维护颜色为："+ weihu);
			IesLogger.instance().runInfo("二级状态受累颜色为："+ shoulei);
			IesLogger.instance().runInfo("二级状态待机颜色为："+ daiji);
			IesLogger.instance().runInfo("二级状态未知颜色为："+ weizhi);
			IesLogger.instance().runInfo("二级状态离线颜色为："+ lixian);*/
			
			Logger.instance().runInfo("二级状态待风颜色为："+ daifeng);
			Logger.instance().runInfo("二级状态发电颜色为："+ fadian);
			Logger.instance().runInfo("二级状态停机颜色为："+ tingji);
			Logger.instance().runInfo("二级状态维护颜色为："+ weihu);
			Logger.instance().runInfo("二级状态受累颜色为："+ shoulei);
			Logger.instance().runInfo("二级状态其他颜色为："+ qita);

		} catch (Exception err) {
			Logger.instance().runInfo("读取配置文件【" + configPath + "】异常：" + err);
		}
	}
	public String getDaifeng() {
		return daifeng;
	}
	public void setDaifeng(String daifeng) {
		this.daifeng = daifeng;
	}
	public String getFadian() {
		return fadian;
	}
	public void setFadian(String fadian) {
		this.fadian = fadian;
	}
	public String getTingji() {
		return tingji;
	}
	public void setTingji(String tingji) {
		this.tingji = tingji;
	}
	public String getWeihu() {
		return weihu;
	}
	public void setWeihu(String weihu) {
		this.weihu = weihu;
	}
	public String getShoulei() {
		return shoulei;
	}
	public void setShoulei(String shoulei) {
		this.shoulei = shoulei;
	}
	public String getQita() {
		return qita;
	}
	public void setQita(String qita) {
		this.qita = qita;
	}
	/*public String getFadian() {
		return fadian;
	}
	public void setFadian(String fadian) {
		this.fadian = fadian;
	}
	public String getXiandian() {
		return xiandian;
	}
	public void setXiandian(String xiandian) {
		this.xiandian = xiandian;
	}
	public String getGuzhang() {
		return guzhang;
	}
	public void setGuzhang(String guzhang) {
		this.guzhang = guzhang;
	}
	public String getWeihu() {
		return weihu;
	}
	public void setWeihu(String weihu) {
		this.weihu = weihu;
	}
	public String getShoulei() {
		return shoulei;
	}
	public void setShoulei(String shoulei) {
		this.shoulei = shoulei;
	}
	public String getDaiji() {
		return daiji;
	}
	public void setDaiji(String daiji) {
		this.daiji = daiji;
	}
	public String getWeizhi() {
		return weizhi;
	}
	public void setWeizhi(String weizhi) {
		this.weizhi = weizhi;
	}
	public String getLixian() {
		return lixian;
	}
	public void setLixian(String lixian) {
		this.lixian = lixian;
	}*/

}
