package com.ieslab.base.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.MapPosService;
import com.ieslab.base.service.XiangmuService;
import com.ieslab.util.GlobalPathUtil;


@Controller
@RequestMapping("mappos")
public class MapPosController {
	/**
	 * 获取厂站的框框的对应的坐标{czid, left, top}
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getAllCzPos")
	public void getAllCzPos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String xmlConfPath = GlobalPathUtil.path + "/config/pos4mainpage.xml";
		MapPosService service = new MapPosService(xmlConfPath);
		JSONArray res = service.getAllCzPos();
		response.getWriter().print(res);
	}
	
	@RequestMapping("addOrUpdateCzPos")
	public void addOrUpdateCzPos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String czid = request.getParameter("czid");
		String left = request.getParameter("left");
		String top = request.getParameter("top");
		
		if(czid == null || czid.equals("")){
			return;
		}
		if(left == null || left.equals("")){
			return;
		}
		if(top == null || top.equals("")){
			return;
		}
		
		String xmlConfPath = GlobalPathUtil.path + "/config/pos4mainpage.xml";
		MapPosService service = new MapPosService(xmlConfPath);
		service.addOrUpdatePos(czid, left, top);
	}
	
}
