package com.ieslab.shishijianshi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("zhengtijianshi")
public class ZhengTiJianShiController {

	@RequestMapping("zhengtizhibiao")
	public void getZhengTiZhiBiao(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String id = request.getParameter("id");
		JSONObject obj = new JSONObject();
		if(id.equals("1")){
			obj.put("yfgl", 192.26);obj.put("ycgl", 192.26);obj.put("glxe", 190.2);obj.put("sfgl", 9.40);
			obj.put("yfdl", 9.40);obj.put("sfdl", 9.40);obj.put("xddl", 9.40);obj.put("ssdl", 9.40);
		}
		if(id.equals("2")){
			obj.put("fs", 192.26);obj.put("yfgl", 192.26);obj.put("ycgl", 192.26);obj.put("glxe", 190.2);obj.put("sfgl", 9.40);
			obj.put("yfdl", 9.40);obj.put("sfdl", 9.40);obj.put("xddl", 9.40);obj.put("ssdl", 9.40);
		}
		if(id.equals("3")){
			obj.put("gzqd", 192.26);obj.put("yfgl", 192.26);obj.put("ycgl", 192.26);obj.put("glxe", 190.2);obj.put("sfgl", 9.40);
			obj.put("yfdl", 9.40);obj.put("sfdl", 9.40);obj.put("xddl", 9.40);obj.put("ssdl", 9.40);
		}
		try {
			response.getWriter().print(obj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("fengongsi")
	public void getFenGongSi(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String type = request.getParameter("type");
		JSONArray arr = new JSONArray();
		String[] namearr = {"赤峰","吉林","通辽","山东","锡盟","龙江","蒙西","甘肃","辽宁","宁夏","上海","山西","河南","陕西","西南","广东","河北"};
		for(int i = 0;i < 17;i++){
			JSONObject obj = new JSONObject();
			obj.put("sfgl", 192.26);
			obj.put("glxe", 180.20);
			obj.put("yfgl", 151.14);
			obj.put("zjrl", 100.02);
			if(type.equals("2")){
				obj.put("fs", 212.26);
			}
			if(type.equals("3")){
				obj.put("gzqd", 213.26);
			}
			obj.put("name", namearr[i]);
			obj.put("id", i);
			arr.put(obj);
		}
		try {
			response.getWriter().print(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("changzhan")
	public void getChangZhanCanShu(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String gsid = request.getParameter("gsid");
		String type = request.getParameter("type");
		JSONArray arr = new JSONArray();
		String[] namearr = {"赛罕坝","东山","大黑山","达里","查干","西场","大水","如德","道德"};
		for(int i = 0;i < 9;i++){
			JSONObject obj = new JSONObject();
			obj.put("sfgl", 192.26);
			obj.put("glxe", 180.20);
			obj.put("yfgl", 151.14);
			obj.put("zjrl", 100.02);
			if(type.equals("2")){
				obj.put("fs", 212.26);
			}
			if(type.equals("3")){
				obj.put("gzqd", 213.26);
			}
			obj.put("name", namearr[i]);
			obj.put("id", i);
			arr.put(obj);
		}
		try {
			response.getWriter().print(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
