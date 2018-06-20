package com.ieslab.shishijianshi.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ieslab.util.StringUtil;

@Controller
@RequestMapping("quyujianshi")
public class QuYuJianShiController {

	@RequestMapping("getChangzhannameByGongsi")
	public void getChangzhannameByGongsi(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String gsid = request.getParameter("gsid");
		String[] namearr = {"赛罕坝","东山","大黑山","达里","查干","西场","大水","如德","道德"};
		JSONArray arr = new JSONArray();
		for(int i = 0;i < namearr.length;i++){
			JSONObject obj = new JSONObject();
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
	
	@RequestMapping("getFengjiByChangzhan")
	public void getFengjiByChangzhan(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String czid = request.getParameter("czid");
		String[] state = {"发电","待机","限电","受累","维护","故障","离线","未知"};
		String[] sub_state = {"正常","限电","缺陷","计检","临检","电网","站内","环境", "不明", "未知"};
		String tishi = "自定义字符串";
		JSONArray arr = new JSONArray();
		for(int i = 1;i < 26;i++){
			JSONObject obj = new JSONObject();
			obj.put("id", i);
			obj.put("name", tishi);
			obj.put("number", i);
			int a = new java.util.Random().nextInt(7);
			obj.put("state", state[a]);
			obj.put("erji", a);
			switch(a){
				case 0:
					obj.put("color", "#48993d");
					obj.put("pic", "fengji_active.gif");
					obj.put("sanji", 0);
					obj.put("sanjiname", sub_state[0]);
					break;
				case 1:
					obj.put("color", "#415caa");
					obj.put("pic", "fengji.png");
					obj.put("sanji", "null");
					break;
				case 2:
					obj.put("color", "#b98518");
					obj.put("pic", "fengji.png");
					obj.put("sanji", "null");
					break;
				case 3:
					obj.put("color", "#6d3756");
					obj.put("pic", "fengji.png");
					obj.put("sanji", 5);
					obj.put("sanjiname", sub_state[5]);
					break;
				case 4:
					obj.put("color", "#e29e33");
					obj.put("pic", "fengji.png");
					obj.put("sanji", 4);
					obj.put("sanjiname", sub_state[4]);
					break;
				case 5:
					obj.put("color", "#a12b38");
					obj.put("pic", "fengji.png");
					obj.put("sanji", "null");
					break;
				case 6:
					obj.put("color", "#565656");
					obj.put("pic", "fengji.png");
					obj.put("sanji", "null");
					break;
				case 7:
					obj.put("color", "#565656");
					obj.put("pic", "fengji.png");
					obj.put("sanji", "9");
					obj.put("sanjiname", sub_state[9]);
					break;
			}
			arr.put(obj);
		}
		try {
			response.getWriter().print(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getBiandianzhan")
	public ModelAndView getBiandianzhan(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		//获取参数
        String path = request.getSession().getServletContext().getRealPath("/");
		String svgname = request.getParameter("svgname");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String webName = request.getContextPath();
		String dingweiFlag = request.getParameter("dingweiFlag");
		String xPos = request.getParameter("xPos");
		String yPos = request.getParameter("yPos");
		String bjName = request.getParameter("bjName");
		if(bjName != null && !"".equals(bjName)){
			bjName = bjName.replaceAll("jinghao", "#");
		}
		try
        {
			ModelAndView mav = new ModelAndView();
			String trueName = StringUtil.encode(svgname);
			String grapath = path + "graph/usergra/" + trueName + ".svg";
			File files = new File(grapath);
			String svgname_zh = svgname;
			if (!files.exists()) {
            	mav.addObject("error", "图形名称为："+trueName + "</br></br>图形对应中文名为：" + svgname_zh);
            	mav.setViewName("nopage");
			}
			else{
            	mav.addObject("svgname", trueName);
            	mav.addObject("svgname_zh", svgname_zh);
            	mav.addObject("width", width);
            	mav.addObject("height", height);
            	mav.addObject("dingweiFlag", dingweiFlag);
            	mav.addObject("xPos", xPos);
            	mav.addObject("yPos", yPos);
            	mav.addObject("bjName", bjName);
            	mav.setViewName("theme_dark/shishijianshi/svg");
			}
			return mav;
        }
        catch (Exception e)
        {
        	
        }
		return new ModelAndView("svgError", "svgName", svgname);
	}
}
