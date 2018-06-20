package com.ieslab.base.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.JiDianXianService;


@Controller
@RequestMapping("jidianxian")
public class JiDianXianController {
	/**
	 * 根据厂站ID获取集电线
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getJDXByCzid")
	public void getJDXByCzid(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String czid = request.getParameter("czid");
		List<JSONObject> list = JiDianXianService.findAllByCzId(czid);
		try {
			response.getWriter().print(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据项目ID获取集电线
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getJDXByXmid")
	public void getJDXByXmid(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String xmid = request.getParameter("xmid");
		List<JSONObject> list = JiDianXianService.findAllByXmId(xmid);
		try {
			response.getWriter().print(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 通过多个过滤条件过滤集电线
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getJDXByFilter")
	public void getJDXByFilter(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String xmid = request.getParameter("xmid");
		String czid = request.getParameter("czid");
		String gsid = request.getParameter("gsid");
		if(gsid == null || gsid.length() == 0){
			gsid = "0";
		}
		if(czid == null || czid.length() == 0){
			czid = "0";
		}
		if(xmid == null || xmid.length() == 0){
			xmid = "0";
		}
		List<JSONObject> list = JiDianXianService.findAllByFilter(czid, xmid,gsid);
		try {
			response.getWriter().print(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
