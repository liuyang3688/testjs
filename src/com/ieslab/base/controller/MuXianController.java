package com.ieslab.base.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.MuXianService;

@Controller
@RequestMapping("muxian")
public class MuXianController {
	/**
	 * 通过厂站获取母线信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("getMuXianInfoByStation")
	public void getMuXianInfoByStation(HttpServletRequest request , HttpServletResponse response){
		String czid = request.getParameter("czid");
		if(czid == null){
			czid = "0";
		}
		List<JSONObject> list = MuXianService.getMuXianByStation(czid);
		
		JSONArray res = new JSONArray();
		if(list != null){
			for(JSONObject obj: list){
				res.add(obj);
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
