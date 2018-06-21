package com.leotech.util;

/********************************************************************
 *	版权所有 (C) 2009 积成电子股份有限公司
 *	保留所有版权
 *
 *	作者：	侯培彬
 *	日期：	2011-1-4
 *	摘要：	日志类
 *  功能：       写入日志
 *
 *********************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class Logger {
	private File m_file = null;
	private PrintStream m_stream = null;
	private static Logger s_logger = new Logger();
	static Map<String,Logger> m_instanceMap = new HashMap<String,Logger>();
	private String m_pathName = null;

	public static Logger instance() {
		if(!m_instanceMap.containsKey("default")){
			m_instanceMap.put("default", s_logger);
		}
		return s_logger;
	}

	public static Logger instance(String name) {
		Logger log = null;
		if(!m_instanceMap.containsKey(name)){
		    log = new Logger(name);
			m_instanceMap.put(name, log);
		}
		else{
			log = m_instanceMap.get(name);
		}
		 return log;
	}

	private Logger() {
		try {
			m_pathName = GlobalPathUtil.path + "/logs/iesweb.log";
			m_file = new File(m_pathName);
			m_stream = new PrintStream(new FileOutputStream(m_file, true));
		} catch (FileNotFoundException e) {
			System.out.println("IES700-WEB服务日志文件不存在:" + e.getMessage());
		}
	}

	private Logger(String name) {
		try {
			if(null != name && !"".equals(name)){
				m_pathName = GlobalPathUtil.path + "/logs/" + name + ".log";
				m_file = new File(m_pathName);
				m_stream = new PrintStream(new FileOutputStream(m_file, true));
			}

		} catch (FileNotFoundException e) {
			System.out.println("IES700-WEB服务日志文件不存在:" + e.getMessage());
		}
	}

	@Override
	protected void finalize() {
		m_stream.close();
	}

	synchronized private void output(String msg) {
		try {
			if (m_file.exists() && m_file.length() > 2097152L)// 2M
			{
				m_stream.close();
				File file = new File(m_pathName + ".2");
				if (file.exists()) {
					file.delete();
				}
				file = new File(m_pathName + ".1");
				if (file.exists()) {
					file.renameTo(new File(m_pathName + ".2"));
				}
				m_file.renameTo(new File(m_pathName + ".1"));
				m_file = new File(m_pathName);
				m_stream = new PrintStream(new FileOutputStream(m_file, true));
			}

			m_stream.println(msg);
		} catch (FileNotFoundException e) {
			System.out.println("IES700-WEB服务日志文件不存在:" + e.getMessage());
		}
	}

	// DBG信息写入日志文件
	public void debugInfo(String msg) {
		output("DBG " + timeInfo() + " " + msg);
	}

	// RUN信息写入日志文件
	public void runInfo(String msg) {
		output("RUN " + timeInfo() + " " + msg);
	}

	// ERR信息写入日志文件
	public void errInfo(String msg) {
		output("ERR " + timeInfo() + " " + msg);
	}



	private static String timeInfo() {
		GregorianCalendar today = new GregorianCalendar();
		return DateFormat.getDateTimeInstance().format(today.getTime());
	}
}
