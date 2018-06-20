package com.ieslab.base.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.KaiGuanDao;
import com.ieslab.base.service.KaiGuanService;

@Controller
@RequestMapping("kaiguan")
public class KaiGuanController {
	/**
	 * 通过厂站获取开关信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("getKaiGuanInfoByStation")
	public void getKaiGuanInfoByStation(HttpServletRequest request , HttpServletResponse response){
		String czid = request.getParameter("czid");
		if(czid == null){
			czid = "0";
		}
		List<JSONObject> list = KaiGuanService.getKaiGuanByStation(czid);
		
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
	
	
	/**
	 * 根据开关id查询开关类型 
	 */
	@RequestMapping("getKaiguanType")
	public void getKaiguanType(HttpServletRequest request , HttpServletResponse response){
		String kgid = request.getParameter("kgids");
		
		List<JSONObject> list = KaiGuanDao.instance().getKaiGuanType(kgid);
		int msg = 0;
		for (int i = 0; i < list.size(); i++) {
			String subtype = list.get(i).getString("subtype");
			if("1".equals(subtype) || "2".equals(subtype)){
				msg = 1;
			}
		}
		
		try {
			response.getWriter().print(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
