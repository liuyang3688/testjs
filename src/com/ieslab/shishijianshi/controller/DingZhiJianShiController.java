package com.ieslab.shishijianshi.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.ieslab.rdbdata.DZQueryParam;
import com.ieslab.rdbdata.SelBJInfo;
import com.ieslab.shishijianshi.service.DingzhijianshiService;


@Controller
@RequestMapping("dingzhijianshi")
public class DingZhiJianShiController {
	
	/**
	 * 调接口c++
	 * 定制监视界面四个表格内容展示，以及增加风机
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getDingzhijianshiData")
	public void getfengdianjizu(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		int type = Integer.parseInt(request.getParameter("type")); 
		String strQYGongSiID = request.getParameter("GongSiID");
	    String strChangZhanID = request.getParameter("ChangZhanID");
	    String strXingHaoID = request.getParameter("XingHaoID");
	    String filter =request.getParameter("filter");
		String filterVal1 = request.getParameter("filterVal1");
		String filterVal2 = request.getParameter("filterVal2");
		String bjid = request.getParameter("bjid");
		
	    //组织调用接口所需要的参数
		DZQueryParam queryParam = new DZQueryParam();
		queryParam.setStrQYGongSiID(strQYGongSiID);
		queryParam.setStrChangZhanID(strChangZhanID);
		queryParam.setStrXingHaoID(strXingHaoID);
		
		//如果是有风机参数就放入SelBJInfo
		SelBJInfo info = new SelBJInfo();
		
		//筛选条件由四个二进制组成一个十进制的int型数字
		if(filter != null  && !"".equals(filter)){
			//把 filter 二进制字符串转为十进制的数字
			int shuzi = Integer.parseInt(filter,2);
			queryParam.setFilter(shuzi);
		}
		
		//发电能力table的应发占比输入的数字
		if(filterVal1 != null && !"".equals(filterVal1)){
			float newFilterVal1 = Float.parseFloat(filterVal1);
			queryParam.setFilterVal1(newFilterVal1);
		}
		
		//发电能力table的限额占比输入的数字
		if(filterVal2 !=null && !"".equals(filterVal2)){
			float newFilterVal2 = Float.parseFloat(filterVal2);
			queryParam.setFilterVal2(newFilterVal2);
		}
		
		//bjid由所有的风机id组成的字符串，用逗号隔开
		if(!"".equals(bjid) && bjid != null){
			info.setByBJType(31);
			info.setStrBJID(bjid);
		}
		queryParam.setSelBJInfo(info);
		
		DingzhijianshiService dingzhijianshi = new DingzhijianshiService();
		
		JSONArray data = new JSONArray();
		
		data = dingzhijianshi.dingzhijianshi(queryParam,type);
		try {
			if(data == null || data.size() == 0){
				response.getWriter().print(0);
			}else{
				response.getWriter().print(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
