package com.ieslab.base.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ieslab.base.service.BujiancanshuService;
import com.ieslab.base.service.FjCSConfService;
import com.ieslab.model.FJBJCSInfo;

@Controller

@RequestMapping("Bujiancanshu")
public class BujiancanshuController {
	
	@RequestMapping("getBujiancanshu")
	public void getBujiancanshu(HttpServletRequest request , HttpServletResponse response){
		Map<String,JSONObject> bujiancanshu = BujiancanshuService.map;
		JSONArray json = new JSONArray();
		String[] id = request.getParameterValues("id");
		if(id == null){
			return;
		}
		for (int i = 0; i < id.length; i++) {
			String key = id[i];
			JSONObject canshu = bujiancanshu.get(key);
			if(canshu != null){
				json.put(canshu);
			}
		}
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getFJCS")
	public void getFJCS(HttpServletRequest request , HttpServletResponse response){
		String fengjiId = request.getParameter("fengjiId");
//		List<FJBJCSInfo> fjcsList = FjCSConfService.readCSVFile();
//		com.alibaba.fastjson.JSONArray res = new com.alibaba.fastjson.JSONArray();
//		if(fjcsList.size()!=0){
//			List<FJBJCSInfo> list = FjCSConfService.dataScreening(fjcsList, fengjiId);
//			res = FjCSConfService.sortInfo(list);
//		}
//		try {
//			response.getWriter().print(res);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
