package com.leotech.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.leotech.service.FengChangService;

@Controller
@RequestMapping("fengchang")
public class FengChangController {
	
	@RequestMapping("getFengchang")
	public void getFengchang(HttpServletRequest request, HttpServletResponse response) throws JSONException, UnsupportedEncodingException{
		String comid = request.getParameter("comid");
		String type = request.getParameter("type"); //0 表示全部 1表示风电   2 表示光伏
		if(type == null || type.length() == 0){
			type = "0";
		}
		String shenghanzi = request.getParameter("shenghanzi");
		JSONArray res = new JSONArray();
		List<com.alibaba.fastjson.JSONObject> list = FengChangService.findAllCZ();
		
		for(com.alibaba.fastjson.JSONObject changzhan: list){
			
			com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
			
			if((comid == null || comid.length() == 0)&& shenghanzi != null ){
				if(changzhan.get("province").equals(shenghanzi)){
					obj.put("id", changzhan.get("id"));
					obj.put("netId", changzhan.get("netid"));
					obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
					obj.put("lgt", changzhan.get("latitude"));
					obj.put("lat", changzhan.get("longtitude"));
					obj.put("province", changzhan.get("province"));
					obj.put("powercorpid", changzhan.get("powercorpid"));
					obj.put("stationtype", changzhan.get("stationtype"));
					obj.put("zlfjxmcaps", changzhan.get("zlfjxmcaps"));
					obj.put("clfjxmcaps", changzhan.get("clfjxmcaps"));
					obj.put("zlgfxmcaps", changzhan.get("zlgfxmcaps"));
					obj.put("clgfxmcaps", changzhan.get("clgfxmcaps"));
					res.add(obj);
				}
			}else{
				if(changzhan.get("powercorpid").equals(comid) || "0".equals(comid)){
					//如果查询全部的风场 就放进去
					if("0".equals(type)){
						obj.put("id", changzhan.get("id"));
						obj.put("netId", changzhan.get("netid"));
						obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
						obj.put("lgt", changzhan.get("latitude"));
						obj.put("lat", changzhan.get("longtitude"));
						obj.put("province", changzhan.get("province"));
						obj.put("powercorpid", changzhan.get("powercorpid"));
						obj.put("stationtype", changzhan.get("stationtype"));
						obj.put("zlfjxmcaps", changzhan.get("zlfjxmcaps"));
						obj.put("clfjxmcaps", changzhan.get("clfjxmcaps"));
						obj.put("zlgfxmcaps", changzhan.get("zlgfxmcaps"));
						obj.put("clgfxmcaps", changzhan.get("clgfxmcaps"));
						res.add(obj);
					}else if(changzhan.get("stationtype").equals(type) || changzhan.get("stationtype").equals("3")){ //如果不是，那就根据光伏和风电过滤
						obj.put("id", changzhan.get("id"));
						obj.put("netId", changzhan.get("netid"));
						obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
						obj.put("lgt", changzhan.get("latitude"));
						obj.put("lat", changzhan.get("longtitude"));
						obj.put("province", changzhan.get("province"));
						obj.put("powercorpid", changzhan.get("powercorpid"));
						obj.put("stationtype", changzhan.get("stationtype"));
						obj.put("zlfjxmcaps", changzhan.get("zlfjxmcaps"));
						obj.put("clfjxmcaps", changzhan.get("clfjxmcaps"));
						obj.put("zlgfxmcaps", changzhan.get("zlgfxmcaps"));
						obj.put("clgfxmcaps", changzhan.get("clgfxmcaps"));
						res.add(obj);
					}
				}
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
