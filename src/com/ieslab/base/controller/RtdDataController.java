package com.ieslab.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.rdbdata.RdbwebservicePortType;
import com.ieslab.rdbdata.service_new.ParamInfo;
import com.ieslab.rdbdata.service_new.ParamManager;
import com.ieslab.rdbdata.service_new.RdbWebServiceUtil;


@Controller
@RequestMapping("rtdata")
public class RtdDataController {
	private RdbwebservicePortType rtdService = null;
	/**
	 * 根据三段式获取实时数据
	 * 三段式格式 bjlxid,csid,bjid;bjlxid,csid,bjid;bjlxid,csid,,,,,,
	 * @param request
	 * @param response
	 */
	@RequestMapping("getRtData")
	public void getRtData(HttpServletRequest request , HttpServletResponse response){
		
		String sds = request.getParameter("sds");
		
		if(sds == null || sds.length() == 0){
			return;
		}
		
		JSONArray res = new JSONArray();
		String sdsArr[] = sds.split(";");
		
		/****************以下是新的方法，取消code，直接使用map存放三段式********************************/
		
		//目前的刷数方式是只刷新当前浏览tab的数据，其他的是不会刷新的，这就导致了当很长时间没有打开其他的tab时，再次打开时刷数会返回0
		//这是因为超时的tab页的数据因为超时访问被删除了
		//在这里增加一个判断，如果这次请求的全部的三段式，其中有不存在当前缓存map中，那么直接去请求webService
		
		boolean isFlag = false;
		for(String sds_t: sdsArr){
			ParamInfo info = ParamManager.getInstance().getParam(sds_t);
			if(info == null){
				isFlag = true;
				break;
			}
		}
		if(isFlag){
			//首先把要请求的内容给添加进入
			ParamManager.getInstance().addParams(sds);
			if(rtdService == null){
				rtdService = RdbWebServiceUtil.getRdbWebService();
			}
			try {
				String rtdData = rtdService.getRealData(sds);
				//把结果整理成json
				JSONArray arr = JSONArray.parseArray(rtdData);
				//遍历key，获取结果的value值并设置
				for(int i = 0; i < arr.size(); i++){
					JSONObject obj = (JSONObject) arr.get(i);
					String value = "0";
					ParamInfo info = ParamManager.getInstance().getParam(sdsArr[i]);
					try {
						if(info != null){
							info.setValue(obj.getString("val"));
						}
						value = new String(obj.getString("val").getBytes("ISO-8859-1"),"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					res.add(value);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				rtdService = RdbWebServiceUtil.getRdbWebService();
			}
		}else{
			//添加进入之后，系统自动启动刷数线程，直接拿数据
			for(String sds_t: sdsArr){
				String value = "0";
				ParamInfo info = ParamManager.getInstance().getParam(sds_t);
				if(info != null){
					value = info.getValue();
					try {
						value = new String(value.getBytes("ISO-8859-1"),"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				res.add(value);
			}
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
